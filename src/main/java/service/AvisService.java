package service;

import models.avis;
import utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvisService implements Crud<avis> {
    private Connection conn;

    // Constructeur pour initialiser la connexion
    public AvisService() {
        this.conn = MyDb.getInstance().getConn();
        if (this.conn == null) {
            System.err.println("⚠️ Connexion à la base de données échouée dans AvisService !");
        }
    }

    @Override
    public void create(avis obj) throws Exception {
        String sql = "INSERT INTO avis (note, commentaire, dateavis, id, id_v) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Définir les valeurs de l'avis à insérer
            stmt.setInt(1, obj.getNote());
            stmt.setString(2, obj.getCommentaire());
            stmt.setDate(3, obj.getDateavis());
            stmt.setInt(4, obj.getIdUser());  // ID utilisateur
            stmt.setInt(5, obj.getIdVoiture());  // ID voiture
            stmt.executeUpdate();  // Exécution de la requête
            System.out.println("✅ Avis créé avec succès !");
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la création de l'avis : " + e.getMessage());
        }
    }

    @Override
    public void update(avis obj) throws SQLException {
        String sql = "UPDATE avis SET note = ?, commentaire = ?, dateavis = ?, id_v = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Définir les paramètres pour la mise à jour
            stmt.setInt(1, obj.getNote());
            stmt.setString(2, obj.getCommentaire());
            stmt.setDate(3, obj.getDateavis());
            stmt.setInt(4, obj.getIdVoiture());
            stmt.setInt(5, obj.getIdUser());
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("✅ Avis mis à jour avec succès !");
            } else {
                System.out.println("⚠️ Aucun avis trouvé pour la mise à jour.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la mise à jour de l'avis : " + e.getMessage());
        }
    }

    @Override
    public void delete(int idUser) throws SQLException {
        String sql = "DELETE FROM avis WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUser);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("✅ Avis supprimé avec succès !");
            } else {
                System.out.println("⚠️ Aucun avis trouvé pour la suppression.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la suppression de l'avis : " + e.getMessage());
        }
    }

    @Override
    public List<avis> getAll() throws Exception {
        if (!isConnectionValid()) {
            System.err.println("❌ Connexion fermée, impossible d'effectuer l'opération.");
            return new ArrayList<>();  // Retourne une liste vide en cas de connexion fermée
        }

        String sql = "SELECT * FROM avis";
        List<avis> avisList = new ArrayList<>();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                avis a = new avis();
                a.setId_a(rs.getInt("id_a"));  // Utilise id_a si c'est la colonne correcte
                a.setNote(rs.getInt("note"));
                a.setCommentaire(rs.getString("commentaire"));
                a.setDateavis(rs.getDate("dateavis"));
                a.setIdUser(rs.getInt("id"));
                a.setIdVoiture(rs.getInt("id_v"));
                avisList.add(a);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération des avis : " + e.getMessage());
            return new ArrayList<>();
        }
        return avisList;
    }

    private boolean isConnectionValid() {
        try {
            // Vérifie si la connexion est ouverte et valide
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            // Si une exception est levée lors de la vérification, cela signifie que la connexion est invalide
            System.err.println("❌ Erreur lors de la vérification de la connexion : " + e.getMessage());
            return false;
        }
    }

}
