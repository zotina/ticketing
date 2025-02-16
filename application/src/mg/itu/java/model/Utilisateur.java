package mg.itu.java.model;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import framework.Annotation.Email;
import framework.Annotation.NotNull;
import framework.Annotation.Size;

public class Utilisateur{
	String id_utilisateur;

	@Email(message="format email invalid")
	String email;

	@NotNull @Size(min=6,message="Le mdp doit être au moins 6 chiffres.")
	String mdp;

	String nom;
	
	Role role ;

	public Utilisateur(){
	}

	public Utilisateur(String id_utilisateur, String email, String mdp, String nom, Role role) {
		this.id_utilisateur = id_utilisateur;
		this.email = email;
		this.mdp = mdp;
		this.nom = nom;
		this.role = role;
	}

	public String getId_utilisateur() {
		return this.id_utilisateur;
	}

	public String getEmail() {
		return this.email;
	}

	public String getMdp() {
		return this.mdp;
	}

	public String getNom() {
		return this.nom;
	}

	public Role getRole() {
		return this.role;
	}

	public void setId_utilisateur(String newId_utilisateur) {
		this.id_utilisateur = newId_utilisateur;
	}

	public void setEmail(String newEmail) {
		this.email = newEmail;
	}

	public void setMdp(String newMdp) {
		this.mdp = newMdp;
	}

	public void setNom(String newNom) {
		this.nom = newNom;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO utilisateur (email,mdp,nom,id_role) VALUES (?,?,?,(select id_role from Role where libelle = 'admin')) RETURNING id_utilisateur";
            statement = connection.prepareStatement(query);
            statement.setString(1, getEmail());
            statement.setString(2, getMdp());
            statement.setString(3, getNom());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id_utilisateur = resultSet.getString("id_utilisateur");
            }
            System.out.println("Données Utilisateur insérées avec succès");
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

	public static Utilisateur getById(String id,Connection connection) throws Exception {
		Utilisateur instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM utilisateur WHERE id_utilisateur = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Utilisateur();
				instance.setId_utilisateur(resultSet.getString("id_utilisateur"));
				instance.setEmail(resultSet.getString("email"));
				instance.setMdp(resultSet.getString("mdp"));
				instance.setNom(resultSet.getString("nom"));
				Role role = Role.getById(resultSet.getString("id_role"),connection);
				instance.setRole(role);
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

	public static Utilisateur authentification(String email,String mdp,Connection connection,String role_name) throws Exception {
		Utilisateur instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM utilisateur WHERE email = ? and mdp = ? ";
			statement = connection.prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, mdp);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				Role role = Role.getById(resultSet.getString("id_role"),connection);
				instance = new Utilisateur();
				instance.setId_utilisateur(resultSet.getString("id_utilisateur"));
				instance.setEmail(resultSet.getString("email"));
				instance.setMdp(resultSet.getString("mdp"));
				instance.setNom(resultSet.getString("nom"));
				instance.setRole(role);

				System.out.println(role_name);
				System.out.println(role.getLibelle());
				if(role_name!= null &&  !role_name.equals("")  && !role_name.equalsIgnoreCase(role.getLibelle()) )
					return null;
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
