package mg.itu.java.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


import framework.ModelView;
import framework.Annotation.Url;
import framework.Annotation.Param;
import framework.Annotation.Controller;
import mg.itu.java.database.Connexion;
import mg.itu.java.dto.ReservationDTO;
import mg.itu.java.model.Classe;
import mg.itu.java.model.DetailsReservation;
import mg.itu.java.model.Reservation;
import mg.itu.java.model.ReservationAvecClasse;
import mg.itu.java.model.Reservation_classe;
import mg.itu.java.model.Type_siege;
import mg.itu.java.model.Utilisateur;
import mg.itu.java.model.Vol;
import framework.Session;

import framework.Annotation.Post;
import framework.Annotation.RestApi;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import java.io.ByteArrayOutputStream;


@Controller
public class ReservationController {
    
    @Url("/ajout_panier")
    @Post
    public ModelView ajout_panier(@Param("reservation") ReservationDTO reservation, 
                                @Param("reservation_classe") Reservation_classe[] reservation_classe,
                                @Param("session") Session session) {
        try {
            Connection conn = new Connexion().connect_to_postgres();

            List<ReservationAvecClasse> panier = (List<ReservationAvecClasse>) session.get("panier");
            if (panier == null) {
                panier = new ArrayList<>();
            }

            reservation.setType_siege(Type_siege.getById(reservation.getType_siege().getId_type_siege(), conn));
            for (Reservation_classe rc : reservation_classe) {
                rc.setClasse(Classe.getById(rc.getClasse().getId_classe(), conn));
                rc.setType_siege(reservation.getType_siege());
            }

            ReservationAvecClasse.merge(panier, reservation, reservation_classe);

            session.add("panier", panier);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return form(session);
    }

    @Url("/reservation_form")
    public ModelView form(@Param("session") Session session) {
        ModelView modelview = new ModelView("./page/reservation/formulaireReservation.jsp");
        Connection conn = new Connexion().connect_to_postgres();
        try {
            modelview.add("volList", Vol.getAll(conn));
            modelview.add("typeSiegeList", Type_siege.getAll(conn));
            modelview.add("classeList", Classe.getAll(conn));
            List<ReservationAvecClasse> panier = (List<ReservationAvecClasse>) session.get("panier");
            modelview.add("panier", panier);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return modelview;
    }

    @Url("/reservation_insert")
    @Post
    public ModelView insert(@Param("reservation") Reservation reservation,@Param("session") Session session) {
        ModelView modelview = new ModelView();
        Connection conn = new Connexion().connect_to_postgres();
        try {
 
            List<ReservationAvecClasse> panier = (List<ReservationAvecClasse>) session.get("panier");
            if (!Reservation.getVolsEnRetard(reservation,conn)) {             
                reservation.setUtilisateur((Utilisateur)session.get("user"));
                
                conn.setAutoCommit(false);
                
                reservation.insert(conn);
                for (ReservationAvecClasse reservationAvecClasse : panier) {
                    for (Reservation_classe reservation_classe2 : reservationAvecClasse.getReservationClasse()) {
                        if (reservation_classe2 != null) {
                            reservation_classe2.setReservation(reservation);
                            reservation_classe2.insert(conn);
                        }
                    }
                }

                conn.commit();
                
                session.delete("panier");
                return list(session);

            } else {
                ModelView view = form(session);
                
                view.add("message", "heur de reservaltion au vol depasse");
                return view;    
            }

    
            
    
        } catch (Exception e) {
            e.printStackTrace();
            modelview.setUrl("./page/reservation/formulaireReservation.jsp");
            modelview.add("message", "Une erreur est survenue lors de l'insertion");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return modelview;
    }
    
    @Url("/reservation_list")
    public ModelView list(@Param("session") Session session) {
        ModelView modelview = new ModelView("./page/reservation/reservation.jsp");
        Connection conn = new Connexion().connect_to_postgres();
        try {
            modelview.add("reservationList", Reservation.getByUser(((Utilisateur)session.get("user")).getId_utilisateur(), conn));
            modelview.add("reservationClasseList", Reservation_classe.getAll(conn));
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return modelview;
    }
    
    @Url("/detail")
    public ModelView detail(@Param("id_reservation") String id_reservation,@Param("session") Session session) {
        ModelView modelview = new ModelView("./page/reservation/detail.jsp");
        Connection conn = new Connexion().connect_to_postgres();
        try {
            
            modelview.add("panier", ReservationAvecClasse.getByReservation(id_reservation,conn));
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return modelview;
    }

    @Url("/anul_reservation")
    public ModelView annulation(@Param("session") Session session,@Param("id_reservation") String id_reservation) {
        ModelView modelview = new ModelView("./page/reservation/reservation.jsp");
        Connection conn = new Connexion().connect_to_postgres();
        Reservation reservation =null;
        try {
            reservation = Reservation.getById(id_reservation, conn);
            if (!Reservation.getRetardAnnulation(reservation,conn)) {
                Reservation.delete(id_reservation, conn);
                return list(session);

            } else {
                ModelView view = list(session);
                
                view.add("message", "Impossible d annuler la reservation heur d annulation depasse");
                return view;    
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return modelview;
    }
    

    @Url("/export_pdf")
    @RestApi
    public ModelView exportPdf(@Param("reservationId") String reservationId) {
        ModelView modelview = new ModelView();
        Connection conn = new Connexion().connect_to_postgres();
        try {
            Reservation reservation = Reservation.getById(reservationId, conn);
            if (reservation == null) {
                modelview.add("success", false);
                modelview.add("response", "Réservation non trouvée");
                return modelview;
            }

            // Fetch details
            List<DetailsReservation> details = reservation.getDetails(conn);

            // Generate PDF
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Add reservation info
            document.add(new Paragraph("Détails de la Réservation")
                    .setFontSize(16).setBold());
            document.add(new Paragraph("ID Réservation: " + reservation.getId_reservation()));
            document.add(new Paragraph("Date de Réservation: " + reservation.getDate_reservation()));
            document.add(new Paragraph("Prix: " + reservation.getPrix() + " €"));
            document.add(new Paragraph("Vol: " + reservation.getVol().getId_vol()));

            // Add details table
            document.add(new Paragraph("\nDétails des Passagers").setFontSize(14).setBold());
            Table table = new Table(new float[]{200, 100, 200});
            table.addHeaderCell(new Cell().add(new Paragraph("Nom")));
            table.addHeaderCell(new Cell().add(new Paragraph("Âge")));
            table.addHeaderCell(new Cell().add(new Paragraph("Passeport")));

            for (DetailsReservation detail : details) {
                table.addCell(new Cell().add(new Paragraph(detail.getNom())));
                table.addCell(new Cell().add(new Paragraph(detail.getAge() + " ans")));
                table.addCell(new Cell().add(new Paragraph(detail.getPassport() != null ? "Fichier téléchargé" : "Aucun fichier")));
            }

            document.add(table);
            document.close();

            // Add PDF data and metadata to ModelView
            modelview.add("success", true);
            modelview.add("data", Base64.getEncoder().encodeToString(baos.toByteArray()));
            modelview.add("contentType", "application/pdf");
            modelview.add("filename", "reservation_RES-1.pdf");
            modelview.add("response", "PDF généré avec succès");
        } catch (Exception e) {
            e.printStackTrace();
            modelview.add("response", e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return modelview;
    }
}