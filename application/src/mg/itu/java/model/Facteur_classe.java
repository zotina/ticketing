package mg.itu.java.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class Facteur_classe{
	String id_facteur_classe;
	double facteur;
	Classe classe ;

	public Facteur_classe(){
	}

	public Facteur_classe(String id_facteur_classe, double facteur, Classe classe) {
		this.id_facteur_classe = id_facteur_classe;
		this.facteur = facteur;
		this.classe = classe;
	}

	public String getId_facteur_classe() {
		return this.id_facteur_classe;
	}

	public double getFacteur() {
		return this.facteur;
	}

	public Classe getClasse() {
		return this.classe;
	}

	public void setId_facteur_classe(String newId_facteur_classe) {
		this.id_facteur_classe = newId_facteur_classe;
	}

	public void setFacteur(double newFacteur) {
		this.facteur = newFacteur;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO facteur_classe (facteur,id_classe) VALUES (?,?) RETURNING id_facteur_classe";
            statement = connection.prepareStatement(query);
            statement.setDouble(1, getFacteur());
            statement.setString(2, this.classe.getId_classe());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id_facteur_classe = resultSet.getString("id_facteur_classe");
            }
            System.out.println("Données Facteur_classe insérées avec succès");
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
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
}
