package mg.itu.java.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class Reservation_classe{
	Reservation reservation ;
	Classe classe ;
	int nombre;
	Type_siege type_siege ;
	

	public Type_siege getType_siege() {
		return type_siege;
	}

	public void setType_siege(Type_siege type_siege) {
		this.type_siege = type_siege;
	}

	public Reservation_classe(){
	}

	public Reservation_classe(Reservation reservation, Classe classe, int nombre) {
		this.reservation = reservation;
		this.classe = classe;
		this.nombre = nombre;
	}

	public Reservation getReservation() {
		return this.reservation;
	}

	public Classe getClasse() {
		return this.classe;
	}

	public int getNombre() {
		return this.nombre;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public void setNombre(int newNombre) {
		this.nombre = newNombre;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO reservation_classe (id_classe,nombre,id_reservation,id_Type_siege) VALUES (?,?,?,?) RETURNING id_reservation";
            statement = connection.prepareStatement(query);
            statement.setString(1, this.classe.getId_classe());
            statement.setInt(2, getNombre());
			statement.setString(3,getReservation().getId_reservation());
			statement.setString(4,getType_siege().getId_type_siege());
            resultSet = statement.executeQuery();
            System.out.println("Données Reservation_classe insérées avec succès");
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
            String query = "DELETE FROM reservation_classe WHERE id_reservation = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id_reservation);
            statement.executeUpdate();
            System.out.println("Données Reservation_classe supprimées avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de Reservation_classe avec ID id_reservation: " + e.getMessage());
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
	public static List<Reservation_classe> getAll(Connection connection) throws Exception {
		List<Reservation_classe> liste = new ArrayList<Reservation_classe>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM reservation_classe";
			statement = connection.prepareStatement(query);
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

	public static List<Reservation_classe> getAllByReservation(String id_reservation,Connection connection) throws Exception {
		List<Reservation_classe> liste = new ArrayList<Reservation_classe>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM reservation_classe where id_reservation = ?";
			statement = connection.prepareStatement(query);
            statement.setString(1, id_reservation);
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

 	@Override
	public String toString() {
		return "Reservation_classe [reservation=" + reservation + ", classe=" + classe + ", nombre=" + nombre
				+ ", type_siege=" + type_siege + "]";
	}
}