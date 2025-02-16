package mg.itu.java.model;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.Date;

public class Critere_reservation{
	String id_reservation;
	double heur;
	Date date_changement;

	public Critere_reservation(){
	}

	public Critere_reservation(String id_reservation, double heur, Date date_changement) {
		this.id_reservation = id_reservation;
		this.heur = heur;
		this.date_changement = date_changement;
	}

	public String getId_reservation() {
		return this.id_reservation;
	}

	public double getHeur() {
		return this.heur;
	}

	public Date getDate_changement() {
		return this.date_changement;
	}

	public void setId_reservation(String newId_reservation) {
		this.id_reservation = newId_reservation;
	}

	public void setHeur(double newHeur) {
		this.heur = newHeur;
	}

	public void setDate_changement(Date newDate_changement) {
		this.date_changement = newDate_changement;
	}

	
	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO critere_reservation (heur,date_changement) VALUES (?,?) RETURNING id_reservation";
            statement = connection.prepareStatement(query);
            statement.setDouble(1, getHeur());
            statement.setDate(2, getDate_changement());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id_reservation = resultSet.getString("id_reservation");
            }
            System.out.println("Données Critere_reservation insérées avec succès");
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
	
	public static List<Critere_reservation> getAll(Connection connection) throws Exception {
		List<Critere_reservation> liste = new ArrayList<Critere_reservation>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM critere_reservation";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Critere_reservation instance = new Critere_reservation();
				instance.setId_reservation(resultSet.getString("id_reservation"));
				instance.setHeur(resultSet.getDouble("heur"));
				instance.setDate_changement(resultSet.getDate("date_changement"));
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

	public static Critere_reservation getbyDate(LocalDateTime date,Connection connection) throws Exception {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM critere_reservation where date_changement <= ? order  by date_changement DESC limit 1";
			statement = connection.prepareStatement(query);
			statement.setTimestamp(1, Timestamp.valueOf(date));
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Critere_reservation instance = new Critere_reservation();
				instance.setId_reservation(resultSet.getString("id_reservation"));
				instance.setHeur(resultSet.getDouble("heur"));
				instance.setDate_changement(resultSet.getDate("date_changement"));
				return instance;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (resultSet != null) try { resultSet.close(); } catch (SQLException e) {}
			if (statement != null) try { statement.close(); } catch (SQLException e) {}
		}
	}
}
