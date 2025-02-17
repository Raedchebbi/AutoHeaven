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

    // ✅ Récupérer toutes les réclamations
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

    // ✅ Ajouter une réclamation
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

    // ✅ Mettre à jour une réclamation
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

    // ✅ Supprimer une réclamation
    public void delete(int idRec) throws Exception {
        String sql = "DELETE FROM reclamation WHERE id_rec = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idRec);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Réclamation supprimée avec succès !");
            } else {
                System.out.println("⚠️ Aucune réclamation trouvée avec cet ID !");
            }
        } catch (SQLException e) {
            throw new Exception("❌ Erreur lors de la suppression de la réclamation : " + e.getMessage(), e);
        }
    }

    // ✅ Récupérer toutes les réclamations avec les informations utilisateur
    public List<Reclamation> getAllWithUserDetails() throws Exception {
        String sql = "SELECT r.id_rec, r.titre, r.contenu, r.status, r.datecreation, " +
                "u.cin, u.nom, u.prenom, u.tel, u.email, r.id AS user_id " +
                "FROM reclamation r " +
                "LEFT JOIN user u ON r.id = u.id";

        List<Reclamation> reclamations = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idRec = rs.getInt("id_rec");
                String titre = rs.getString("titre");
                String contenu = rs.getString("contenu");
                String status = rs.getString("status");
                Date dateCreation = rs.getDate("datecreation");
                int userId = rs.getInt("user_id");

                // Gestion des valeurs null
                String cin = rs.getString("cin") != null ? rs.getString("cin") : "N/A";
                String nom = rs.getString("nom") != null ? rs.getString("nom") : "N/A";
                String prenom = rs.getString("prenom") != null ? rs.getString("prenom") : "N/A";
                String tel = rs.getString("tel") != null ? rs.getString("tel") : "N/A";
                String email = rs.getString("email") != null ? rs.getString("email") : "N/A";

                Reclamation rec = new Reclamation(
                        idRec, titre, contenu, status, dateCreation.toLocalDate(), userId, cin, nom, prenom, tel, email
                );
                reclamations.add(rec);
            }
        } catch (SQLException e) {
            throw new Exception("❌ Erreur lors de la récupération des réclamations et des informations utilisateur : " + e.getMessage(), e);
        }

        return reclamations;
    }

    // ✅ Récupérer toutes les réclamations sans détails utilisateur
    public List<Reclamation> getAllReclamations() {
        List<Reclamation> reclamations = new ArrayList<>();
        String sql = "SELECT id_rec, titre, contenu, status, datecreation, id FROM reclamation";

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
            System.err.println("❌ Erreur lors de la récupération des réclamations : " + e.getMessage());
        }

        return reclamations;
    }

    // ✅ Supprimer une réclamation par ID
    public void deleteById(int idRec) throws Exception {
        String sql = "DELETE FROM reclamation WHERE id_rec = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idRec);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Réclamation supprimée avec succès !");
            } else {
                System.out.println("⚠️ Aucune réclamation trouvée avec cet ID !");
            }
        } catch (SQLException e) {
            throw new Exception("❌ Erreur lors de la suppression de la réclamation : " + e.getMessage(), e);
        }
    }
}