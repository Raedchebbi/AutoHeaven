package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDb {
    private final String url = "jdbc:mysql://localhost:3306/pidev"; // Remplace 'pidev' par le nom de ta base de données
    private final String user = "root"; // Ton nom d'utilisateur
    private final String password = ""; // Ton mot de passe
    private Connection conn;
    private static MyDb instance;

    private MyDb() {
        try {
            // Essaie d'établir la connexion
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connexion à la base de données établie avec succès !");
        } catch (SQLException e) {
            // Si une erreur de connexion survient, affiche un message d'erreur
            System.err.println("❌ Erreur de connexion à la base de données : " + e.getMessage());
        }
    }

    public static MyDb getInstance() {
        if (instance == null) {
            instance = new MyDb();
        }
        return instance;
    }

    public Connection getConn() {
        if (conn == null) {
            System.err.println("⚠️ Attention : La connexion à la base de données est NULL !");
        }
        return conn;
    }
}
