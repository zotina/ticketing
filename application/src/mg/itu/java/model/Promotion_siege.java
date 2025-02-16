package mg.itu.java.model;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class Promotion_siege{
	Type_siege type_siege ;
	Promotion promotion ;
	int nbr_siege;
	double valeur;

	public Promotion_siege(){
	}

	public Promotion_siege(Type_siege type_siege, Promotion promotion, int nbr_siege, double valeur) {
		this.type_siege = type_siege;
		this.promotion = promotion;
		this.nbr_siege = nbr_siege;
		this.valeur = valeur;
	}

	public Type_siege getType_siege() {
		return this.type_siege;
	}

	public Promotion getPromotion() {
		return this.promotion;
	}

	public int getNbr_siege() {
		return this.nbr_siege;
	}


	public void setType_siege(Type_siege type_siege) {
		this.type_siege = type_siege;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public void setNbr_siege(int newNbr_siege) {
		this.nbr_siege = newNbr_siege;
	}

	
	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO promotion_siege (id_promotion,nbr_siege,promotion,id_Type_siege) VALUES (?,?,?,?) RETURNING id_type_siege";
            statement = connection.prepareStatement(query);
            statement.setString(1, this.promotion.getId_promotion());
            statement.setInt(2, getNbr_siege());
            statement.setDouble(3, getValeur());
			statement.setString(4, this.type_siege.getId_type_siege());
            resultSet = statement.executeQuery();
            System.out.println("Données Promotion_siege insérées avec succès");
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

	public static List<Promotion_siege> getAll(Connection connection) throws Exception {
		List<Promotion_siege> liste = new ArrayList<Promotion_siege>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM promotion_siege";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Promotion_siege instance = new Promotion_siege();
				Type_siege type_siege = Type_siege.getById(resultSet.getString("id_type_siege"),connection);
				instance.setType_siege(type_siege);
				Promotion promotion = Promotion.getById(resultSet.getString("id_promotion"),connection);
				instance.setPromotion(promotion);
				instance.setNbr_siege(resultSet.getInt("nbr_siege"));
				instance.setValeur(resultSet.getDouble("promotion"));
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

	public double getValeur() {
		return valeur;
	}

	public void setValeur(double valeur) {
		this.valeur = valeur;
	}
}
