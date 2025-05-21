package mg.itu.java.model;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class Classe{
	String id_classe;
	int min_age;
	int max_age;

	public Classe(){
	}

	public Classe(String id_classe, int min_age, int max_age) {
		this.id_classe = id_classe;
		this.min_age = min_age;
		this.max_age = max_age;
	}

	public String getLibelle(){
		if(max_age <= 17)
			return min_age + " - " + max_age;
		else 
			return min_age + " et plus";
	}
	public String getId_classe() {
		return this.id_classe;
	}

	public int getMin_age() {
		return this.min_age;
	}

	public int getMax_age() {
		return this.max_age;
	}

	public void setId_classe(String newId_classe) {
		this.id_classe = newId_classe;
	}

	public void setMin_age(int newMin_age) {
		this.min_age = newMin_age;
	}

	public void setMax_age(int newMax_age) {
		this.max_age = newMax_age;
	}

	public static List<Classe> getAll(Connection connection) throws Exception {
		List<Classe> liste = new ArrayList<Classe>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM classe";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Classe instance = new Classe();
				instance.setId_classe(resultSet.getString("id_classe"));
				instance.setMin_age(resultSet.getInt("min_age"));
				instance.setMax_age(resultSet.getInt("max_age"));
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
	
	public static Classe getById(String id,Connection connection) throws Exception {
		Classe instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM classe WHERE id_classe = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Classe();
				instance.setId_classe(resultSet.getString("id_classe"));
				instance.setMin_age(resultSet.getInt("min_age"));
				instance.setMax_age(resultSet.getInt("max_age"));
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
