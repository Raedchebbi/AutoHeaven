package services;

import models.Reclamation;
import utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReclamationService {

    private final Connection conn;

    public ReclamationService() {
        this.conn = MyDb.getInstance().getConn();
    }

    // Méthode pour récupérer toutes les réclamations
    public List<Reclamation> getAll() throws Exception {
        String sql = "SELECT id_rec, titre, contenu, status, datecreation, id FROM reclamation";
        List<Reclamation> reclamations = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idRec = rs.getInt("id_rec");
                String titre = rs.getString("titre");
                String contenu = rs.getString("contenu");
                String status = rs.getString("status");
                Date dateCreation = rs.getDate("datecreation");
                int userId = rs.getInt("id");

                Reclamation reclamation = new Reclamation(idRec, titre, contenu, status, dateCreation.toLocalDate(), userId);
                reclamations.add(reclamation);
            }
        } catch (SQLException e) {
            throw new Exception("❌ Erreur lors de la récupération des réclamations : " + e.getMessage(), e);
        }

        return reclamations;
    }

    // Méthode pour ajouter une réclamation
    public void create(Reclamation reclamation) throws Exception {
        String sql = "INSERT INTO reclamation (titre, contenu, status, datecreation, id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, reclamation.getTitre());
            stmt.setString(2, reclamation.getContenu());
            stmt.setString(3, reclamation.getStatus());
            stmt.setDate(4, Date.valueOf(reclamation.getDateCreation()));
            stmt.setInt(5, reclamation.getIdUser());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Réclamation créée avec succès !");
            }
        } catch (SQLException e) {
            throw new Exception("❌ Erreur lors de la création de la réclamation : " + e.getMessage(), e);
        }
    }

    // Méthode pour mettre à jour une réclamation
    public void update(Reclamation reclamation) throws Exception {
        String sql = "UPDATE reclamation SET titre = ?, contenu = ?, status = ?, datecreation = ?, id = ? WHERE id_rec = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, reclamation.getTitre());
            stmt.setString(2, reclamation.getContenu());
            stmt.setString(3, reclamation.getStatus());
            stmt.setDate(4, Date.valueOf(reclamation.getDateCreation()));
            stmt.setInt(5, reclamation.getIdUser());
            stmt.setInt(6, reclamation.getIdRec());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Réclamation mise à jour avec succès !");
            }
        } catch (SQLException e) {
            throw new Exception("❌ Erreur lors de la mise à jour de la réclamation : " + e.getMessage(), e);
        }
    }

    // Méthode pour supprimer une réclamation
    public void delete(Reclamation reclamation) throws Exception {
        String sql = "DELETE FROM reclamation WHERE id_rec = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reclamation.getIdRec());

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Réclamation supprimée avec succès !");
            }
        } catch (SQLException e) {
            throw new Exception("❌ Erreur lors de la suppression de la réclamation : " + e.getMessage(), e);
        }
    }

    // Méthode pour récupérer toutes les réclamations avec les informations utilisateur
    public List<Reclamation> getAllWithUserDetails() throws Exception {
        String sqlReclamation = "SELECT id_rec, titre, contenu, status, datecreation, id FROM reclamation";
        List<Reclamation> reclamations = new ArrayList<>();

        try (PreparedStatement stmtReclamation = conn.prepareStatement(sqlReclamation);
             ResultSet rsReclamation = stmtReclamation.executeQuery()) {

            while (rsReclamation.next()) {
                int reclamationId = rsReclamation.getInt("id_rec");
                String titre = rsReclamation.getString("titre");
                String contenu = rsReclamation.getString("contenu");
                String status = rsReclamation.getString("status");
                Date dateCreation = rsReclamation.getDate("datecreation");
                int userId = rsReclamation.getInt("id");

                String sqlUser = "SELECT cin, nom, prenom, tel, email FROM user WHERE id = ?";
                try (PreparedStatement stmtUser = conn.prepareStatement(sqlUser)) {
                    stmtUser.setInt(1, userId);
                    try (ResultSet rsUser = stmtUser.executeQuery()) {
                        if (rsUser.next()) {
                            String cin = rsUser.getString("cin");
                            String nom = rsUser.getString("nom");
                            String prenom = rsUser.getString("prenom");
                            String tel = rsUser.getString("tel");
                            String email = rsUser.getString("email");

                            Reclamation rec = new Reclamation(
                                    reclamationId,
                                    titre,
                                    contenu,
                                    status,
                                    dateCreation.toLocalDate(),
                                    userId,
                                    cin,
                                    nom,
                                    prenom,
                                    tel,
                                    email
                            );
                            reclamations.add(rec);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new Exception("❌ Erreur lors de la récupération des réclamations et des informations utilisateur : " + e.getMessage(), e);
        }

        return reclamations;
    }
}
