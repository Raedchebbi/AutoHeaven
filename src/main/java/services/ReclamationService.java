package services;

import models.Reclamation;
import services.Crud;
import utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReclamationService implements Crud<Reclamation> {
    private Connection conn;

    public ReclamationService() {
        this.conn = MyDb.getInstance().getConn();
    }

    @Override
    public void create(Reclamation obj) throws Exception {
        String sql = "INSERT INTO reclamation (titre, contenu, status, datecreation, id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, obj.getTitre());
        stmt.setString(2, obj.getContenu());
        stmt.setString(3, obj.getStatus());
        stmt.setDate(4, Date.valueOf(obj.getDateCreation()));
        stmt.setInt(5, obj.getId());
        stmt.executeUpdate();
    }

    @Override
    public void update(Reclamation obj) throws Exception {
        String sql = "UPDATE reclamation SET titre = ?, contenu = ?, status = ?, datecreation = ? WHERE id_rec = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, obj.getTitre());
        stmt.setString(2, obj.getContenu());
        stmt.setString(3, obj.getStatus());
        stmt.setDate(4, Date.valueOf(obj.getDateCreation()));
        stmt.setInt(5, obj.getIdRec());
        stmt.executeUpdate();
    }

    @Override
    public void delete(Reclamation obj) throws Exception {
        // Vérifier si la réclamation existe avant de la supprimer
        String checkRecSql = "SELECT id_rec FROM reclamation WHERE id_rec = ?";
        PreparedStatement checkRecStmt = conn.prepareStatement(checkRecSql);
        checkRecStmt.setInt(1, obj.getIdRec());  // Utiliser getIdRec()
        ResultSet rs = checkRecStmt.executeQuery();

        if (rs.next()) {
            // La réclamation existe, suppression des messages associés
            String sqlMessagerie = "DELETE FROM messagerie WHERE id_rec = ?";
            PreparedStatement stmtMessagerie = conn.prepareStatement(sqlMessagerie);
            stmtMessagerie.setInt(1, obj.getIdRec());  // Utiliser getIdRec()
            int messagerieDeleted = stmtMessagerie.executeUpdate();

            // Afficher le nombre de messages supprimés
            if (messagerieDeleted > 0) {
                System.out.println("✅ " + messagerieDeleted + " message(s) lié(s) à la réclamation supprimé(s).");
            } else {
                System.out.println("⚠️ Aucun message trouvé pour cette réclamation.");
            }

            // Suppression de la réclamation
            String sqlReclamation = "DELETE FROM reclamation WHERE id_rec = ?";
            PreparedStatement stmtReclamation = conn.prepareStatement(sqlReclamation);
            stmtReclamation.setInt(1, obj.getIdRec());  // Utiliser getIdRec()
            int reclamationDeleted = stmtReclamation.executeUpdate();

            if (reclamationDeleted > 0) {
                System.out.println("✅ Réclamation supprimée !");
            } else {
                System.out.println("❌ La réclamation n'a pas pu être supprimée.");
            }
        } else {
            throw new Exception("❌ La réclamation avec l'ID " + obj.getIdRec() + " n'existe pas.");
        }
    }

    @Override
    public List<Reclamation> getAll() throws Exception {
        String sql = "SELECT * FROM reclamation";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Reclamation> reclamations = new ArrayList<>();
        while (rs.next()) {
            Reclamation rec = new Reclamation(
                    rs.getInt("id_rec"),
                    rs.getString("titre"),
                    rs.getString("contenu"),
                    rs.getString("status"),
                    rs.getDate("datecreation").toLocalDate(),
                    rs.getInt("id")
            );
            reclamations.add(rec);
        }
        return reclamations;
    }
}
