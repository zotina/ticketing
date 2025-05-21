package mg.itu.java.model;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class Vol_siege{
	Type_siege type_siege ;
	Vol vol ;
	double prix;

	public Vol_siege(){
	}

	public Vol_siege(Type_siege type_siege, Vol vol, double prix) {
		this.type_siege = type_siege;
		this.vol = vol;
		this.prix = prix;
	}

	public Type_siege getType_siege() {
		return this.type_siege;
	}

	public Vol getVol() {
		return this.vol;
	}

	public double getPrix() {
		return this.prix;
	}

	public void setType_siege(Type_siege type_siege) {
		this.type_siege = type_siege;
	}

	public void setVol(Vol vol) {
		this.vol = vol;
	}

	public void setPrix(double newPrix) {
		this.prix = newPrix;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO vol_siege (id_vol,prix,id_Type_siege) VALUES (?,?,?) RETURNING id_type_siege";
            statement = connection.prepareStatement(query);
            statement.setString(1, this.vol.getId_vol());
            statement.setDouble(2, getPrix());
			statement.setString(3, getType_siege().getId_type_siege());
            resultSet = statement.executeQuery();
            System.out.println("Données Vol_siege insérées avec succès");
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

	public void update(Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "UPDATE vol_siege SET id_vol = ?, prix = ?, id_type_siege = ?  WHERE id_type_siege = ? and id_vol = ? ";
            statement = connection.prepareStatement(query);
            statement.setString(1, this.vol.getId_vol());
            statement.setDouble(2, getPrix());
            statement.setString(3, getType_siege().getId_type_siege());
			statement.setString(4, getType_siege().getId_type_siege());
			statement.setString(5, this.vol.getId_vol());
            statement.executeUpdate();
            System.out.println("Données Vol_siege mises à jour avec succès");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
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

	public static void delete(String id_type_siege,Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM vol_siege WHERE id_type_siege = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id_type_siege);
            statement.executeUpdate();
            System.out.println("Données Vol_siege supprimées avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de Vol_siege avec ID id_type_siege: " + e.getMessage());
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

	public static List<Vol_siege> getAll(Connection connection) throws Exception {
		List<Vol_siege> liste = new ArrayList<Vol_siege>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM vol_siege";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Vol_siege instance = new Vol_siege();
				Type_siege type_siege = Type_siege.getById(resultSet.getString("id_type_siege"),connection);
				instance.setType_siege(type_siege);
				Vol vol = Vol.getById(resultSet.getString("id_vol"),connection);
				instance.setVol(vol);
				instance.setPrix(resultSet.getDouble("prix"));
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
	
	public static Vol_siege getById(String id,Connection connection) throws Exception {
		Vol_siege instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM vol_siege WHERE id_type_siege = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Vol_siege();
				Type_siege type_siege = Type_siege.getById(resultSet.getString("id_type_siege"),connection);
				instance.setType_siege(type_siege);
				Vol vol = Vol.getById(resultSet.getString("id_vol"),connection);
				instance.setVol(vol);
				instance.setPrix(resultSet.getDouble("prix"));
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

	public static List<Vol_siege> getByVolId(String id_vol,Connection connection) throws Exception {
		List<Vol_siege> liste = new ArrayList<Vol_siege>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Vol_siege instance = null;
		try {
			String query = "SELECT * FROM vol_siege where id_vol = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id_vol);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				instance = new Vol_siege();
				Type_siege type_siege = Type_siege.getById(resultSet.getString("id_type_siege"),connection);
				instance.setType_siege(type_siege);
				Vol vol = Vol.getById(resultSet.getString("id_vol"),connection);
				instance.setVol(vol);
				instance.setPrix(resultSet.getDouble("prix"));
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