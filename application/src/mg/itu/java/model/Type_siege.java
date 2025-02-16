package mg.itu.java.model;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class Type_siege{
	String id_type_siege;
	String libelle;

	public Type_siege(){
	}

	public Type_siege(String id_type_siege, String libelle) {
		this.id_type_siege = id_type_siege;
		this.libelle = libelle;
	}

	public String getId_type_siege() {
		return this.id_type_siege;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setId_type_siege(String newId_type_siege) {
		this.id_type_siege = newId_type_siege;
	}

	public void setLibelle(String newLibelle) {
		this.libelle = newLibelle;
	}

	public static List<Type_siege> getAll(Connection connection) throws Exception {
		List<Type_siege> liste = new ArrayList<Type_siege>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM type_siege";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Type_siege instance = new Type_siege();
				instance.setId_type_siege(resultSet.getString("id_type_siege"));
				instance.setLibelle(resultSet.getString("libelle"));
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
	public static Type_siege getById(String id,Connection connection) throws Exception {
		Type_siege instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM type_siege WHERE id_type_siege = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Type_siege();
				instance.setId_type_siege(resultSet.getString("id_type_siege"));
				instance.setLibelle(resultSet.getString("libelle"));
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
