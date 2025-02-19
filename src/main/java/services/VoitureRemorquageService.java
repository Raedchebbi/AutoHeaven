package services;

import models.VoitureRemorquage;
import utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoitureRemorquageService {
    private Connection conn;

    public VoitureRemorquageService() {
        this.conn = MyDb.getInstance().getConn();
    }

    public void create(VoitureRemorquage voiture) throws Exception {
        String sql = "INSERT INTO voiture_remorquage (modele, annee, num_agence, statut) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, voiture.getModele());
        stmt.setInt(2, voiture.getAnnee());
        stmt.setString(3, voiture.getNum_agence());
        stmt.setString(4, voiture.getStatut());

        int rowsInserted = stmt.executeUpdate();

        if (rowsInserted > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                voiture.setId_vr(generatedKeys.getInt(1));
                System.out.println("Voiture ajoutee avec ID : " + voiture.getId_vr());
            }
        } else {
            throw new Exception("Echec de l'insertion de la voiture.");
        }
    }

    public void update(VoitureRemorquage voiture) throws Exception {
        String sql = "UPDATE voiture_remorquage SET modele = ?, annee = ?, num_agence = ?, statut = ? WHERE id_vr = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, voiture.getModele());
        stmt.setInt(2, voiture.getAnnee());
        stmt.setString(3, voiture.getNum_agence());
        stmt.setString(4, voiture.getStatut());
        stmt.setInt(5, voiture.getId_vr());

        stmt.executeUpdate();
    }

    public void delete(int id_vr) throws Exception {
        String sql = "DELETE FROM voiture_remorquage WHERE id_vr = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id_vr);
        stmt.executeUpdate();
    }

    public VoitureRemorquage getById(int id_vr) throws Exception {
        String sql = "SELECT * FROM voiture_remorquage WHERE id_vr = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id_vr);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new VoitureRemorquage(
                    rs.getInt("id_vr"),
                    rs.getString("modele"),
                    rs.getInt("annee"),
                    rs.getString("num_agence"),
                    rs.getString("statut")
            );
        }
        return null;
    }

    public List<VoitureRemorquage> getAll() throws Exception {
        String sql = "SELECT * FROM voiture_remorquage";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<VoitureRemorquage> voitures = new ArrayList<>();

        while (rs.next()) {
            VoitureRemorquage voiture = new VoitureRemorquage(
                    rs.getInt("id_vr"),
                    rs.getString("modele"),
                    rs.getInt("annee"),
                    rs.getString("num_agence"),
                    rs.getString("statut")
            );
            voitures.add(voiture);
        }
        return voitures;
    }
}