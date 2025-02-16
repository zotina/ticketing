package mg.itu.java.model;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class Ville{
	String id_ville;
	String nom;

	public Ville(){
	}

	public Ville(String id_ville, String nom) {
		this.id_ville = id_ville;
		this.nom = nom;
	}

	public String getId_ville() {
		return this.id_ville;
	}

	public String getNom() {
		return this.nom;
	}

	public void setId_ville(String newId_ville) {
		this.id_ville = newId_ville;
	}

	public void setNom(String newNom) {
		this.nom = newNom;
	}

	public static List<Ville> getAll(Connection connection) throws Exception {
		List<Ville> liste = new ArrayList<Ville>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM ville";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Ville instance = new Ville();
				instance.setId_ville(resultSet.getString("id_ville"));
				instance.setNom(resultSet.getString("nom"));
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
	
	public static Ville getById(String id,Connection connection) throws Exception {
		Ville instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM ville WHERE id_ville = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Ville();
				instance.setId_ville(resultSet.getString("id_ville"));
				instance.setNom(resultSet.getString("nom"));
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
