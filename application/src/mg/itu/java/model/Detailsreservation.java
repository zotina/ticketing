package mg.itu.java.model;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class Detailsreservation{
	String id_detailsreservation;
	String nom;
	int age;
	String cin;
	Reservation reservation ;

	public Detailsreservation(){
	}

	public Detailsreservation(String id_detailsreservation, String nom, int age, String cin, Reservation reservation) {
		this.id_detailsreservation = id_detailsreservation;
		this.nom = nom;
		this.age = age;
		this.cin = cin;
		this.reservation = reservation;
	}

	public String getId_detailsreservation() {
		return this.id_detailsreservation;
	}

	public String getNom() {
		return this.nom;
	}

	public int getAge() {
		return this.age;
	}

	public String getCin() {
		return this.cin;
	}

	public Reservation getReservation() {
		return this.reservation;
	}

	public void setId_detailsreservation(String newId_detailsreservation) {
		this.id_detailsreservation = newId_detailsreservation;
	}

	public void setNom(String newNom) {
		this.nom = newNom;
	}

	public void setAge(int newAge) {
		this.age = newAge;
	}

	public void setCin(String newCin) {
		this.cin = newCin;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO detailsreservation (nom,age,cin,id_reservation) VALUES (?,?,?,?) RETURNING id_detailsreservation";
            statement = connection.prepareStatement(query);
            statement.setString(1, getNom());
            statement.setInt(2, getAge());
            statement.setString(3, getCin());
            statement.setString(4, this.reservation.getId_reservation());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id_detailsreservation = resultSet.getString("id_detailsreservation");
            }
            System.out.println("Données Detailsreservation insérées avec succès");
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
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

	public void update(Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "UPDATE detailsreservation SET nom = ?, age = ?, cin = ?, id_reservation = ? WHERE id_detailsreservation = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, getNom());
            statement.setInt(2, getAge());
            statement.setString(3, getCin());
            statement.setString(4, this.reservation.getId_reservation());
            statement.setString(5, getId_detailsreservation());
            statement.executeUpdate();
            System.out.println("Données Detailsreservation mises à jour avec succès");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

	public static void delete(String id_detailsreservation,Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM detailsreservation WHERE id_detailsreservation = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id_detailsreservation);
            statement.executeUpdate();
            System.out.println("Données Detailsreservation supprimées avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de Detailsreservation avec ID id_detailsreservation: " + e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

	public static List<Detailsreservation> getAll(Connection connection) throws Exception {
		List<Detailsreservation> liste = new ArrayList<Detailsreservation>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM detailsreservation";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Detailsreservation instance = new Detailsreservation();
				instance.setId_detailsreservation(resultSet.getString("id_detailsreservation"));
				instance.setNom(resultSet.getString("nom"));
				instance.setAge(resultSet.getInt("age"));
				instance.setCin(resultSet.getString("cin"));
				Reservation reservation = Reservation.getById(resultSet.getString("id_reservation"),connection);
				instance.setReservation(reservation);
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
	public static Detailsreservation getById(String id,Connection connection) throws Exception {
		Detailsreservation instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM detailsreservation WHERE id_detailsreservation = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Detailsreservation();
				instance.setId_detailsreservation(resultSet.getString("id_detailsreservation"));
				instance.setNom(resultSet.getString("nom"));
				instance.setAge(resultSet.getInt("age"));
				instance.setCin(resultSet.getString("cin"));
				Reservation reservation = Reservation.getById(resultSet.getString("id_reservation"),connection);
				instance.setReservation(reservation);
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
