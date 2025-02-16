package mg.itu.java.model;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.sql.Timestamp;

public class Vol{
	String id_vol;
	LocalDateTime date_vol;
	Ville ville ;
	Avion avion ;

	public Vol(){
	}

	public Vol(String id_vol, LocalDateTime date_vol, Ville ville, Avion avion) {
		this.id_vol = id_vol;
		this.date_vol = date_vol;
		this.ville = ville;
		this.avion = avion;
	}

	public String getId_vol() {
		return this.id_vol;
	}
	public String getLibelle(){
		return ville.getNom() + " - " + avion.getModel() + " - " + date_vol;
	}
	public LocalDateTime getDate_vol() {
		return this.date_vol;
	}

	public Ville getVille() {
		return this.ville;
	}

	public Avion getAvion() {
		return this.avion;
	}

	public void setId_vol(String newId_vol) {
		this.id_vol = newId_vol;
	}

	public void setDate_vol(String date_vol) {
		if (date_vol != null && !date_vol.isEmpty()) {
			try {
				DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				this.date_vol = LocalDateTime.parse(date_vol, formatter1);
			} catch (DateTimeParseException e) {
				try {
					DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
					this.date_vol = LocalDateTime.parse(date_vol, formatter2);
				} catch (DateTimeParseException e2) {
					throw new IllegalArgumentException(
							"Format de date invalide. Formats acceptés : 'yyyy-MM-dd HH:mm:ss' ou 'yyyy-MM-dd'T'HH:mm'");
				}
			}
		}
	}

	public void setVille(Ville ville) {
		this.ville = ville;
	}

	public void setAvion(Avion avion) {
		this.avion = avion;
	}

	public void insert(Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO vol (date_vol,id_ville,id_avion) VALUES (?,?,?) RETURNING id_vol";
            statement = connection.prepareStatement(query);
			System.out.println(getDate_vol());
            statement.setTimestamp(1, Timestamp.valueOf(getDate_vol()));
            statement.setString(2, this.ville.getId_ville());
            statement.setString(3, this.avion.getId_avion());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                this.id_vol = resultSet.getString("id_vol");
            }
            System.out.println("Données Vol insérées avec succès");
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
            String query = "UPDATE vol SET date_vol = ?, id_ville = ?, id_avion = ? WHERE id_vol = ?";
            statement = connection.prepareStatement(query);
            statement.setTimestamp(1, Timestamp.valueOf(getDate_vol()));
            statement.setString(2, this.ville.getId_ville());
            statement.setString(3, this.avion.getId_avion());
            statement.setString(4, getId_vol());
            statement.executeUpdate();
            System.out.println("Données Vol mises à jour avec succès");
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

	public static void delete(String id_vol,Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM vol WHERE id_vol = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id_vol);
            statement.executeUpdate();
            System.out.println("Données Vol supprimées avec succès");
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression de Vol avec ID id_vol: " + e.getMessage());
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

	public static List<Vol> getAll(Connection connection) throws Exception {
		List<Vol> liste = new ArrayList<Vol>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM vol";
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Vol instance = new Vol();
				instance.setId_vol(resultSet.getString("id_vol"));
				instance.setDate_vol(resultSet.getString("date_vol"));
				Ville ville = Ville.getById(resultSet.getString("id_ville"),connection);
				instance.setVille(ville);
				Avion avion = Avion.getById(resultSet.getString("id_avion"),connection);
				instance.setAvion(avion);
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
	public static Vol getById(String id,Connection connection) throws Exception {
		Vol instance = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM vol WHERE id_vol = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance = new Vol();
				instance.setId_vol(resultSet.getString("id_vol"));
				instance.setDate_vol(resultSet.getString("date_vol"));
				Ville ville = Ville.getById(resultSet.getString("id_ville"),connection);
				instance.setVille(ville);
				Avion avion = Avion.getById(resultSet.getString("id_avion"),connection);
				instance.setAvion(avion);
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
	public static List<Vol> multiCritere(Connection connection, LocalDateTime dateDebut, 
		LocalDateTime dateFin, String idVille, String idAvion) throws Exception {
		List<Vol> liste = new ArrayList<Vol>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			StringBuilder query = new StringBuilder("SELECT * FROM vol WHERE 1=1");
			List<Object> params = new ArrayList<>();
			
			// Ajouter les conditions si les paramètres ne sont pas null
			if (dateDebut != null) {
				query.append(" AND date_vol >= ?");
				params.add(Timestamp.valueOf(dateDebut));
			}
			
			if (dateFin != null) {
				query.append(" AND date_vol <= ?");
				params.add(Timestamp.valueOf(dateFin));
			}
			
			if (idVille != null && !idVille.trim().isEmpty()) {
				query.append(" AND id_ville = ?");
				params.add(idVille);
			}
			
			if (idAvion != null && !idAvion.trim().isEmpty()) {
				query.append(" AND id_avion = ?");
				params.add(idAvion);
			}
			
			// Ajouter ORDER BY date_vol pour avoir un résultat ordonné
			query.append(" ORDER BY date_vol");
			
			statement = connection.prepareStatement(query.toString());
			
			// Définir les paramètres
			for (int i = 0; i < params.size(); i++) {
				statement.setObject(i + 1, params.get(i));
			}
			
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				Vol instance = new Vol();
				instance.setId_vol(resultSet.getString("id_vol"));
				instance.setDate_vol(resultSet.getString("date_vol"));
				Ville ville = Ville.getById(resultSet.getString("id_ville"), connection);
				instance.setVille(ville);
				Avion avion = Avion.getById(resultSet.getString("id_avion"), connection);
				instance.setAvion(avion);
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
