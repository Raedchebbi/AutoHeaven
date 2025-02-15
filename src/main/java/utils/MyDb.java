package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDb {
    private String url = "jdbc:mysql://localhost:3306/pidev";  // URL de la base de données
    private String user = "root";  // Utilisateur
    private String password = "";  // Mot de passe
    private Connection conn;
    private static MyDb instance;

    // Méthode pour obtenir l'instance unique de MyDb (Pattern Singleton)
    public static MyDb getInstance() {
        if (instance == null) {
            instance = new MyDb();
        }
        return instance;
    }

    // Méthode pour obtenir la connexion à la base de données
    public Connection getConn() {
        return conn;
    }

    // Constructeur privé pour initialiser la connexion à la base de données
    private MyDb() {
        try {
            // Établit la connexion à la base de données avec les informations fournies
            this.conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection established");  // Message de confirmation
        } catch (SQLException e) {
            System.out.println(e.getMessage());  // Affiche le message d'erreur en cas d'exception
        }
    }
}
