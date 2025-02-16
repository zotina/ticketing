package mg.itu.java.model;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class Avion{
	String id_avion;
	Date date_fabrication;
	String model;

	public Avion(){
	}

	public Avion(String id_avion, Date date_fabrication, String model) {
		this.id_avion = id_avion;
		this.date_fabrication = date_fabrication;
		this.model = model;
	}

	public String getId_avion() {
		return this.id_avion;
	}

	public Date getDate_fabrication() {
		return this.date_fabrication;
	}

	public String getModel() {
		return this.model;
	}

	public void setId_avion(String newId_avion) {
		this.id_avion = newId_avion;
	}

	public void setDate_fabrication(Date newDate_fabrication) {
		this.date_fabrication = newDate_fabrication;
	}

	public void setModel(String newModel) {
		this.model = newModel;
	}

	public static List<Avion> getAll(Connection connection) throws Exception {
		List<Avion> liste = new ArrayList<Avion>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM avion";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Avion instance = new Avion();
				instance.setId_avion(resultSet.getString("id_avion"));
				instance.setDate_fabrication(resultSet.getDate("date_fabrication"));
				instance.setModel(resultSet.getString("model"));
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
	
	public static Avion getById(String id,Connection connection) throws Exception {
		Avion instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM avion WHERE id_avion = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Avion();
				instance.setId_avion(resultSet.getString("id_avion"));
				instance.setDate_fabrication(resultSet.getDate("date_fabrication"));
				instance.setModel(resultSet.getString("model"));
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
