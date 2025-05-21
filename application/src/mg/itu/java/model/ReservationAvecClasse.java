package mg.itu.java.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mg.itu.java.dto.ReservationDTO;

public class ReservationAvecClasse {
    private ReservationDTO reservation;
    private Reservation_classe[] reservationClasse;

    public ReservationAvecClasse(ReservationDTO reservation, Reservation_classe[] reservationClasse) {
        this.reservation = reservation;
        this.reservationClasse = reservationClasse;
    }

    public ReservationDTO getReservation() {
        return reservation;
    }

    public Reservation_classe[] getReservationClasse() {
        return reservationClasse;
    }

    public void setReservation(ReservationDTO reservation) {
        this.reservation = reservation;
    }

    public void setReservationClasse(Reservation_classe[] reservationClasse) {
        this.reservationClasse = reservationClasse;
    }

    
    public static void merge(List<ReservationAvecClasse> panier, ReservationDTO nouvelleReservation, Reservation_classe[] nouvellesClasses) {
        
        for (ReservationAvecClasse item : panier) {
            ReservationDTO existante = item.getReservation();
            if (existante.getType_siege().getId_type_siege().equals(nouvelleReservation.getType_siege().getId_type_siege())) {

                
                for (Reservation_classe nouvelleClasse : nouvellesClasses) {
                    boolean found = false;
                    for (Reservation_classe existanteClasse : item.getReservationClasse()) {
                        if (existanteClasse.getClasse().getId_classe().equals(nouvelleClasse.getClasse().getId_classe())) {
                            
                            existanteClasse.setNombre(existanteClasse.getNombre() + nouvelleClasse.getNombre());
                            found = true;
                            break;
                        }
                    }
                    
                    if (!found) {
                        item.setReservationClasse(ajouterClasse(item.getReservationClasse(), nouvelleClasse));
                    }
                }

                return; 
            }
        }

        
        panier.add(new ReservationAvecClasse(nouvelleReservation, nouvellesClasses));
    }

    
    private static Reservation_classe[] ajouterClasse(Reservation_classe[] original, Reservation_classe aAjouter) {
        Reservation_classe[] nouveau = new Reservation_classe[original.length + 1];
        System.arraycopy(original, 0, nouveau, 0, original.length);
        nouveau[original.length] = aAjouter;
        return nouveau;
    }
    public static List<ReservationAvecClasse> getByReservation(String id_reservation, Connection conn) throws Exception {
        List<ReservationAvecClasse> result = new ArrayList<>();
        String sql = "SELECT DISTINCT id_type_siege FROM reservation_classe WHERE id_reservation = ?";
        String sql1 = "SELECT * FROM reservation_classe WHERE id_type_siege = ? AND id_reservation = ?";

        try (
            PreparedStatement pst = conn.prepareStatement(sql);
        ) {
            pst.setString(1, id_reservation);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String idTypeSiege = rs.getString("id_type_siege");

                try (
                    PreparedStatement pst1 = conn.prepareStatement(sql1);
                ) {
                    pst1.setString(1, idTypeSiege);
                    pst1.setString(2, id_reservation);
                    ResultSet rs1 = pst1.executeQuery();

                    List<Reservation_classe> reservationClasses = new ArrayList<>();
                    Classe classe = null;
                    Type_siege typeSiege = null;

                    while (rs1.next()) {
                        
                        Reservation_classe rc = new Reservation_classe();

                        
                        classe = Classe.getById(rs1.getString("id_classe"), conn);

                        
                        typeSiege = Type_siege.getById(rs1.getString("id_type_siege"), conn);

                        
                        Reservation reservation = Reservation.getById(rs1.getString("id_reservation"),conn);

                        
                        rc.setClasse(classe);
                        rc.setReservation(reservation);
                        rc.setType_siege(typeSiege);
                        rc.setNombre(rs1.getInt("nombre"));

                        reservationClasses.add(rc);
                    }

                    if (!reservationClasses.isEmpty()) {
                        ReservationDTO dto = new ReservationDTO(classe, typeSiege);
                        Reservation_classe[] classesArray = reservationClasses.toArray(new Reservation_classe[0]);
                        result.add(new ReservationAvecClasse(dto, classesArray));
                    }
                }
            }
        }

        return result;
    }

}