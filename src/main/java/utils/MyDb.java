package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDb {
    private static final String URL = "jdbc:mysql://localhost:3306/pidev";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Ajuste selon ton mot de passe MySQL
    private Connection conn;
    private static MyDb instance;

    private MyDb() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion à la base de données réussie !");
        } catch (SQLException e) {
            System.err.println("Échec de la connexion initiale : " + e.getMessage());
            throw new RuntimeException("La base de données est indisponible : " + e.getMessage());
        }
    }

    public static MyDb getInstance() {
        if (instance == null) {
            instance = new MyDb();
        }
        return instance;
    }

    public Connection getConn() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Nouvelle connexion établie.");
            }
        } catch (SQLException e) {
            System.err.println("Échec de la reconnection : " + e.getMessage());
            throw new RuntimeException("Impossible d'établir la connexion à la base de données : " + e.getMessage());
        }
        return conn;
    }
}