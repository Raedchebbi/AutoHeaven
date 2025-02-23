package service;

import models.avis;
import utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvisService implements Crud<avis> {
    private Connection conn;


    public AvisService() {


        // Supposons que tu utilises une classe MyDb pour gérer la connexion
        this.conn = MyDb.getInstance().getConn();  // Connexion obtenue depuis MyDb


    }

    @Override
    public void create(avis obj) throws Exception {
        String sql = "INSERT INTO avis (note, commentaire, dateavis, id , id_v) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, obj.getNote());
            stmt.setString(2, obj.getCommentaire());
            stmt.setDate(3, obj.getDateavis());
            stmt.setInt(4, obj.getIdUser());
            stmt.setInt(5, obj.getIdVoiture());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création de l'avis : " + e.getMessage());
        }
    }

    @Override
    public void update(avis obj) throws SQLException {
        // Requête SQL pour mettre à jour l'avis en fonction de l'ID de l'utilisateur
        String sql = "UPDATE avis SET note = ?, commentaire = ?, dateavis = ?, id_v = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Définir les paramètres pour la requête SQL
            stmt.setInt(1, obj.getNote());          // Note
            stmt.setString(2, obj.getCommentaire()); // Commentaire
            stmt.setDate(3, obj.getDateavis());      // Date de l'avis
            stmt.setInt(4, obj.getIdVoiture());     // ID de la voiture
            stmt.setInt(5, obj.getIdUser());        // ID de l'utilisateur (condition WHERE)

            // Exécuter la requête de mise à jour
            int rowsUpdated = stmt.executeUpdate();

            // Vérifier si l'avis a été mis à jour
            if (rowsUpdated > 0) {
                System.out.println("Avis mis à jour avec succès pour idUser = " + obj.getIdUser());
            } else {
                System.out.println("Aucun avis trouvé pour l'idUser = " + obj.getIdUser());
            }
        } catch (SQLException e) {
            // Gérer les erreurs SQL
            System.out.println("Erreur lors de la mise à jour de l'avis : " + e.getMessage());
        }
    }


    @Override
    public void delete(int idUser) throws SQLException {
        // Requête SQL pour supprimer un avis basé sur l'id_user
        String sql = "DELETE FROM avis WHERE id = ?";  // Remplace 'id_user' par le bon nom de la colonne dans ta table

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Définir l'idUser comme paramètre pour la suppression
            stmt.setInt(1, idUser);

            // Exécution de la requête de suppression
            int rowsDeleted = stmt.executeUpdate();

            // Vérifier si l'avis a été supprimé
            if (rowsDeleted > 0) {
                System.out.println("Avis supprimé avec succès pour l'utilisateur avec idUser = " + idUser);
            } else {
                System.out.println("Aucun avis trouvé pour l'utilisateur avec idUser = " + idUser);
            }
        } catch (SQLException e) {
            // Gestion des erreurs SQL
            System.out.println("Erreur lors de la suppression de l'avis : " + e.getMessage());
        }
    }

    @Override
    public List<avis> getAll() throws Exception {
        String sql = "SELECT * FROM avis";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            List<avis> avisList = new ArrayList<>();
            while (rs.next()) {
                avis a = new avis();
                a.setId_a(rs.getInt("id_a"));
                a.setNote(rs.getInt("note"));
                a.setCommentaire(rs.getString("commentaire"));
                a.setDateavis(rs.getDate("dateavis"));
                a.setIdUser(rs.getInt("id"));
                a.setIdVoiture(rs.getInt("id_v"));
                avisList.add(a);
            }
            return avisList;
        }
    }
}
