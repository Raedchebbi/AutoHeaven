package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDb {
    private final String url = "jdbc:mysql://localhost:3306/pidev";
    private final String user = "root";
    private final String password = "";
    private Connection conn;
    private static MyDb instance;

    // Singleton
    public static MyDb getInstance() {
        if (instance == null) {
            instance = new MyDb();
        }
        return instance;
    }

    // Retourne une connexion active ou en recrée une si nécessaire
    public Connection getConn() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Nouvelle connexion établie.");
            }
        } catch (SQLException e) {
            System.err.println("Échec de la connexion : " + e.getMessage());
            throw new RuntimeException("Impossible d'établir la connexion à la base de données.");
        }
        return conn;
    }

    private MyDb() {
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion à la base de données réussie !");
        } catch (SQLException e) {
            System.err.println("Échec de la connexion initiale : " + e.getMessage());
            throw new RuntimeException("La base de données est indisponible.");
        }
    }
}