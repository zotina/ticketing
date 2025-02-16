package mg.itu.java.model;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class Avionsiege{
	Avion avion ;
	Type_siege type_siege ;
	int nbr_siege;

	public Avionsiege(){
	}

	public Avionsiege(Avion avion, Type_siege type_siege, int nbr_siege) {
		this.avion = avion;
		this.type_siege = type_siege;
		this.nbr_siege = nbr_siege;
	}

	public Avion getAvion() {
		return this.avion;
	}

	public Type_siege getType_siege() {
		return this.type_siege;
	}

	public int getNbr_siege() {
		return this.nbr_siege;
	}

	public void setAvion(Avion avion) {
		this.avion = avion;
	}

	public void setType_siege(Type_siege type_siege) {
		this.type_siege = type_siege;
	}

	public void setNbr_siege(int newNbr_siege) {
		this.nbr_siege = newNbr_siege;
	}

	public static List<Avionsiege> getAll(Connection connection) throws Exception {
		List<Avionsiege> liste = new ArrayList<Avionsiege>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM avionsiege";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Avionsiege instance = new Avionsiege();
				Avion avion = Avion.getById(resultSet.getString("id_avion"),connection);
				instance.setAvion(avion);
				Type_siege type_siege = Type_siege.getById(resultSet.getString("id_type_siege"),connection);
				instance.setType_siege(type_siege);
				instance.setNbr_siege(resultSet.getInt("nbr_siege"));
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
}
