package mg.itu.java.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Role{
	String id_role;
	String libelle;

	public Role(){
	}

	public Role(String id_role, String libelle) {
		this.id_role = id_role;
		this.libelle = libelle;
	}

	public String getId_role() {
		return this.id_role;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setId_role(String newId_role) {
		this.id_role = newId_role;
	}

	public void setLibelle(String newLibelle) {
		this.libelle = newLibelle;
	}

	public static Role getById(String id,Connection connection) throws Exception {
		Role instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM role WHERE id_role = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Role();
				instance.setId_role(resultSet.getString("id_role"));
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
