package mg.itu.java.controller;

import java.sql.Connection;
import java.time.LocalDateTime;

import framework.ModelView;
import framework.Annotation.Url;
import framework.Annotation.Param;
import framework.Annotation.Controller;
import mg.itu.java.database.Connexion;
import mg.itu.java.model.Annulation_reservation;
import mg.itu.java.model.Avion;
import mg.itu.java.model.Critere_reservation;
import mg.itu.java.model.Reservation;
import mg.itu.java.model.Type_siege;
import mg.itu.java.model.Utilisateur;
import mg.itu.java.model.Ville;
import mg.itu.java.model.Vol;
import mg.itu.java.model.Vol_siege;
import framework.Session;

import framework.Annotation.Post;

@Controller
public class ReservationController {
    @Url("/reservation_form")
    public ModelView form() {
        ModelView modelview = new ModelView("./page/reservation/formulaireReservation.jsp");
        Connection conn = new Connexion().connect_to_postgres();
        try {
            modelview.add("volList", Vol.getAll(conn));
            modelview.add("typeSiegeList", Type_siege.getAll(conn));
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
    public ModelView insert(@Param("reservation") Reservation reservation, @Param("session") Session session) {
        ModelView modelview = new ModelView();
        Connection conn = new Connexion().connect_to_postgres();
        try {
            Critere_reservation critere = Critere_reservation.getbyDate(reservation.getDate_reservation(), conn);
            Vol vol = Vol.getById(reservation.getVol().getId_vol(), conn);
            
            long hoursToAdd = (long) critere.getHeur(); 
    
            LocalDateTime volDateMoinHeure = vol.getDate_vol().minusHours(hoursToAdd);
            
            if (volDateMoinHeure.isAfter(reservation.getDate_reservation())) {
                reservation.setUtilisateur((Utilisateur)session.get("user"));
                reservation.insert(conn);
                modelview.add("message", "Réservation réussie");
            } else {
                modelview.add("message", "heur de reservaltion au vol "+vol.getId_vol() + " depasse");
            }
    
            modelview.add("volList", Vol.getAll(conn));
            modelview.add("typeSiegeList", Type_siege.getAll(conn));
            modelview.setUrl("./page/reservation/formulaireReservation.jsp");
    
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
        try {
            Reservation  reservation = Reservation.getById(id_reservation, conn);

            Annulation_reservation critere = Annulation_reservation.getbyDate(reservation.getDate_reservation(), conn);
            Vol vol = Vol.getById(reservation.getVol().getId_vol(), conn);
            
            long hoursToAdd = (long) critere.getHeur(); 
    
            LocalDateTime volDateMoinHeure = vol.getDate_vol().minusHours(hoursToAdd);
            
            if (volDateMoinHeure.isAfter(reservation.getDate_reservation())) {
                Reservation.delete(id_reservation, conn);
                modelview.add("message", "annulation réussie");
            } else {
                modelview.add("message", "Impossible d annuler la reservation du vol "+vol.getId_vol() +" heur d annulation depasse");
            }

            modelview.add("reservationList", Reservation.getByUser(((Utilisateur)session.get("user")).getId_utilisateur(), conn));
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
