package mg.itu.java.model;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Reservation{
	String id_reservation;
	LocalDateTime date_reservation;
	double prix;
	Type_siege type_siege ;
	Vol vol ;
	Utilisateur utilisateur ;

	public Reservation(){
	}

	public Reservation(String id_reservation, LocalDateTime date_reservation, double prix, Type_siege type_siege, Vol vol, Utilisateur utilisateur) {
		this.id_reservation = id_reservation;
		this.date_reservation = date_reservation;
		this.prix = prix;
		this.type_siege = type_siege;
		this.vol = vol;
		this.utilisateur = utilisateur;
	}

	public String getId_reservation() {
		return this.id_reservation;
	}



	public double getPrix() {
		return this.prix;
	}

	public Type_siege getType_siege() {
		return this.type_siege;
	}

	public Vol getVol() {
		return this.vol;
	}

	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	public void setId_reservation(String newId_reservation) {
		this.id_reservation = newId_reservation;
	}



	public void setPrix(double newPrix) {
		this.prix = newPrix;
	}

	public void setType_siege(Type_siege type_siege) {
		this.type_siege = type_siege;
	}

	public void setVol(Vol vol) {
		this.vol = vol;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO reservation (date_reservation,id_type_siege,id_vol,id_utilisateur) VALUES (?,?,?,?) RETURNING id_reservation";
            statement = connection.prepareStatement(query);
			statement.setTimestamp(1, Timestamp.valueOf(getDate_reservation()));
            statement.setString(2, this.type_siege.getId_type_siege());
            statement.setString(3, this.vol.getId_vol());
            statement.setString(4, this.utilisateur.getId_utilisateur());
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
				Type_siege type_siege = Type_siege.getById(resultSet.getString("id_type_siege"),connection);
				instance.setType_siege(type_siege);
				Vol vol = Vol.getById(resultSet.getString("id_vol"),connection);
				instance.setVol(vol);
				Utilisateur utilisateur = Utilisateur.getById(resultSet.getString("id_utilisateur"),connection);
				instance.setUtilisateur(utilisateur);
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
				Type_siege type_siege = Type_siege.getById(resultSet.getString("id_type_siege"),connection);
				instance.setType_siege(type_siege);
				Vol vol = Vol.getById(resultSet.getString("id_vol"),connection);
				instance.setVol(vol);
				Utilisateur utilisateur = Utilisateur.getById(resultSet.getString("id_utilisateur"),connection);
				instance.setUtilisateur(utilisateur);
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

	public LocalDateTime getDate_reservation() {
		return date_reservation;
	}

	public void setDate_reservation(String date_reservation) {
		if (date_reservation != null && !date_reservation.isEmpty()) {
			try {
				DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				this.date_reservation = LocalDateTime.parse(date_reservation, formatter1);
			} catch (DateTimeParseException e) {
				try {
					DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
					this.date_reservation = LocalDateTime.parse(date_reservation, formatter2);
				} catch (DateTimeParseException e2) {
					throw new IllegalArgumentException(
							"Format de date invalide. Formats acceptés : 'yyyy-MM-dd HH:mm:ss' ou 'yyyy-MM-dd'T'HH:mm'");
				}
			}
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
				Type_siege type_siege = Type_siege.getById(resultSet.getString("id_type_siege"),connection);
				instance.setType_siege(type_siege);
				Vol vol = Vol.getById(resultSet.getString("id_vol"),connection);
				instance.setVol(vol);
				Utilisateur utilisateur = Utilisateur.getById(resultSet.getString("id_utilisateur"),connection);
				instance.setUtilisateur(utilisateur);
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
	
}
