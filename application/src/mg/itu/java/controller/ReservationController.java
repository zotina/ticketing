package mg.itu.java.controller;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import framework.ModelView;
import framework.Annotation.Url;
import framework.Annotation.Param;
import framework.Annotation.Controller;
import mg.itu.java.database.Connexion;
import mg.itu.java.dto.ReservationDTO;
import mg.itu.java.model.Annulation_reservation;
import mg.itu.java.model.Classe;
import mg.itu.java.model.Critere_reservation;
import mg.itu.java.model.Reservation;
import mg.itu.java.model.ReservationAvecClasse;
import mg.itu.java.model.Reservation_classe;
import mg.itu.java.model.Type_siege;
import mg.itu.java.model.Utilisateur;
import mg.itu.java.model.Vol;
import framework.Session;

import framework.Annotation.Post;

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
        List<String> retard = new ArrayList<String>();
        Reservation reservation =null;
        try {
            List<ReservationAvecClasse> panier = ReservationAvecClasse.getByReservation(id_reservation,conn);
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
    
}