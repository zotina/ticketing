package mg.itu.java.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connexion {
	private final String DRIVER = "org.postgresql.Driver";
	private final String URL = "jdbc:postgresql://localhost:5432/ticketing";
	private final String USER = "postgres";
	private final String PASSWORD = "zotina";

	public Connection connect_to_postgres() {
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void close(Statement statement, Connection connection) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			System.err.println("Erreur lors de la fermeture du Statement : " + e.getMessage());
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
		}
	}

	public void close(ResultSet resultSet, Statement statement, Connection connection) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			System.err.println("Erreur lors de la fermeture du ResultSet : " + e.getMessage());
		}
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			System.err.println("Erreur lors de la fermeture du Statement : " + e.getMessage());
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
		}
	}
}

