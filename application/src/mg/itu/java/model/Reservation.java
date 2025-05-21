package mg.itu.java.model;

import java.util.List;

import mg.itu.java.util.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Reservation{
	private String id_reservation;
	private LocalDateTime date_reservation;
	private double prix;
	private Utilisateur utilisateur ;
	private Vol vol;
	
	
	public Vol getVol() {
		return vol;
	}

	public void setVol(Vol vol) {
		this.vol = vol;
	}

	public Reservation(){
	}

	public Reservation(String id_reservation, LocalDateTime date_reservation, double prix, Type_siege type_siege, Vol vol, Utilisateur utilisateur) {
		this.id_reservation = id_reservation;
		this.date_reservation = date_reservation;
		this.prix = prix;
		this.utilisateur = utilisateur;
	}

	public String getId_reservation() {
		return this.id_reservation;
	}

	public LocalDateTime getDate_reservation() {
		return this.date_reservation;
	}

	public double getPrix() {
		return this.prix;
	}

	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	public void setId_reservation(String newId_reservation) {
		this.id_reservation = newId_reservation;
	}

	public void setDate_reservation(String date_reservation) {
		this.date_reservation =DateUtil.parser(date_reservation);
	}

	public void setPrix(double newPrix) {
		this.prix = newPrix;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO reservation (date_reservation,id_utilisateur,id_vol) VALUES (?,?,?) RETURNING id_reservation";
            statement = connection.prepareStatement(query);
            statement.setTimestamp(1, Timestamp.valueOf(getDate_reservation()));
            statement.setString(2, this.utilisateur.getId_utilisateur());
			statement.setString(3, this.vol.getId_vol());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id_reservation = resultSet.getString("id_reservation");
            }
            System.out.println("Données Reservation insérées avec succès");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

	public static void delete(String id_reservation,Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM reservation WHERE id_reservation = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id_reservation);
            statement.executeUpdate();
            System.out.println("Données Reservation supprimées avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de Reservation avec ID id_reservation: " + e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

	public static List<Reservation> getAll(Connection connection) throws Exception {
		List<Reservation> liste = new ArrayList<Reservation>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM reservation";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Reservation instance = new Reservation();
				instance.setId_reservation(resultSet.getString("id_reservation"));
				instance.setDate_reservation(resultSet.getString("date_reservation"));
				instance.setPrix(resultSet.getDouble("prix"));
				Utilisateur utilisateur = Utilisateur.getById(resultSet.getString("id_utilisateur"),connection);
				instance.setUtilisateur(utilisateur);
				Vol vol = Vol.getById(resultSet.getString("id_vol"), connection);
				instance.setVol(vol);
				liste.add(instance);
			}
			return liste;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (resultSet != null) try { resultSet.close(); } catch (SQLException e) {}
			if (statement != null) try { statement.close(); } catch (SQLException e) {}
		}
	}
	public static Reservation getById(String id,Connection connection) throws Exception {
		Reservation instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM reservation WHERE id_reservation = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Reservation();
				instance.setId_reservation(resultSet.getString("id_reservation"));
				instance.setDate_reservation(resultSet.getString("date_reservation"));
				instance.setPrix(resultSet.getDouble("prix"));
				Utilisateur utilisateur = Utilisateur.getById(resultSet.getString("id_utilisateur"),connection);
				instance.setUtilisateur(utilisateur);
				Vol vol = Vol.getById(resultSet.getString("id_vol"), connection);
				instance.setVol(vol);
			}
			return instance;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (resultSet != null) try { resultSet.close(); } catch (SQLException e) {}
			if (statement != null) try { statement.close(); } catch (SQLException e) {}
		}
	}

	public static List<Reservation> getByUser(String u ,Connection connection) throws Exception {
		List<Reservation> liste = new ArrayList<Reservation>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM reservation where id_utilisateur = ? ";
			statement = connection.prepareStatement(query);
			statement.setString(1, u);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Reservation instance = new Reservation();
				instance.setId_reservation(resultSet.getString("id_reservation"));
				instance.setDate_reservation(resultSet.getString("date_reservation"));
				instance.setPrix(resultSet.getDouble("prix"));
				Utilisateur utilisateur = Utilisateur.getById(resultSet.getString("id_utilisateur"),connection);
				instance.setUtilisateur(utilisateur);
				Vol vol = Vol.getById(resultSet.getString("id_vol"), connection);
				instance.setVol(vol);
				liste.add(instance);
			}
			return liste;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (resultSet != null) try { resultSet.close(); } catch (SQLException e) {}
			if (statement != null) try { statement.close(); } catch (SQLException e) {}
		}
	}
	
	public List<Reservation_classe> getClassById(Connection connection) throws Exception {
		List<Reservation_classe> liste = new ArrayList<Reservation_classe>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM reservation_classe where id_reservation = ? ";
			statement = connection.prepareStatement(query);
			statement.setString(1, this.id_reservation);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Reservation_classe instance = new Reservation_classe();
				Reservation reservation = Reservation.getById(resultSet.getString("id_reservation"),connection);
				instance.setReservation(reservation);
				Classe classe = Classe.getById(resultSet.getString("id_classe"),connection);
				instance.setClasse(classe);
				instance.setNombre(resultSet.getInt("nombre"));
				liste.add(instance);
			}
			return liste;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (resultSet != null) try { resultSet.close(); } catch (SQLException e) {}
			if (statement != null) try { statement.close(); } catch (SQLException e) {}
		}
	}
	public static boolean getVolsEnRetard(Reservation reservation ,Connection conn) throws Exception {
		Vol vol = Vol.getById(reservation.getVol().getId_vol(), conn);
		LocalDateTime dateReservation = reservation.getDate_reservation();
		Critere_reservation critere = Critere_reservation.getbyDate(dateReservation, conn);

		long heuresLimite = (long) critere.getHeur();
		LocalDateTime limite = vol.getDate_vol().minusHours(heuresLimite);

		System.out.println("date limite reservation = "+limite.toString());
		System.out.println("date de reservatiom = "+dateReservation.toString());
		
		if (limite.isBefore(dateReservation)) {
			return true;
		}

        return false;
    }
	public static boolean getRetardAnnulation(Reservation reservation, Connection conn) throws Exception {
		Vol vol = Vol.getById(reservation.getVol().getId_vol(), conn);
		LocalDateTime dateReservation = reservation.getDate_reservation();
		Annulation_reservation annulation_reservation = Annulation_reservation.getbyDate(dateReservation, conn);

		long heuresLimite = (long) annulation_reservation.getHeur();
		LocalDateTime limite = vol.getDate_vol().minusHours(heuresLimite);

		System.out.println("date limite reservation = "+limite.toString());
		System.out.println("date de reservatiom = "+dateReservation.toString());
		
		if (limite.isBefore(dateReservation)) {
			
			return true;
		}
        return false;
    }
	
}
