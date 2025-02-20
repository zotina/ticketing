package mg.itu.java.model;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class Promotion{
	String id_promotion;
	Date date_promotion;
	Vol vol ;

	public Promotion(){
	}

	public Promotion(String id_promotion, Date date_promotion, Vol vol) {
		this.id_promotion = id_promotion;
		this.date_promotion = date_promotion;
		this.vol = vol;
	}

	public String getId_promotion() {
		return this.id_promotion;
	}

	public Date getDate_promotion() {
		return this.date_promotion;
	}

	public Vol getVol() {
		return this.vol;
	}

	public void setId_promotion(String newId_promotion) {
		this.id_promotion = newId_promotion;
	}

	public void setDate_promotion(Date newDate_promotion) {
		this.date_promotion = newDate_promotion;
	}

	public void setVol(Vol vol) {
		this.vol = vol;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO promotion (date_promotion,id_vol) VALUES (?,?) RETURNING id_promotion";
            statement = connection.prepareStatement(query);
            statement.setDate(1, getDate_promotion());
            statement.setString(2, this.vol.getId_vol());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id_promotion = resultSet.getString("id_promotion");
            }
			
			Vol vol = Vol.getById(this.vol.getId_vol(), connection);
			vol.setEnPromotion(true);
			vol.update(connection);

            System.out.println("Données Promotion insérées avec succès");
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

	public static List<Promotion> getAll(Connection connection) throws Exception {
		List<Promotion> liste = new ArrayList<Promotion>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM promotion";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Promotion instance = new Promotion();
				instance.setId_promotion(resultSet.getString("id_promotion"));
				instance.setDate_promotion(resultSet.getDate("date_promotion"));
				Vol vol = Vol.getById(resultSet.getString("id_vol"),connection);
				instance.setVol(vol);
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
	
	public static Promotion getById(String id,Connection connection) throws Exception {
		Promotion instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM promotion WHERE id_promotion = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Promotion();
				instance.setId_promotion(resultSet.getString("id_promotion"));
				instance.setDate_promotion(resultSet.getDate("date_promotion"));
				Vol vol = Vol.getById(resultSet.getString("id_vol"),connection);
				instance.setVol(vol);
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
