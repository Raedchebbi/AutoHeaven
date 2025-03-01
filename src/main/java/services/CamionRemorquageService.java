package services;

import models.CamionRemorquage;
import utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CamionRemorquageService implements Crud<CamionRemorquage> {
    Connection conn;

    public CamionRemorquageService() {
        this.conn = MyDb.getInstance().getConn();
    }

    /*@Override
    public void create(CamionRemorquage obj) throws Exception {
        String sql = "INSERT INTO camion_remorquage (nom_agence, modele, annee, num_tel, statut) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, obj.getNomAgence());
        stmt.setString(2, obj.getModele());
        stmt.setInt(3, obj.getAnnee());
        stmt.setString(4, obj.getNum_tel());
        stmt.setString(5, obj.getStatut());

        int rowsInserted = stmt.executeUpdate();
        if (rowsInserted > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                obj.setId_cr(generatedKeys.getInt(1));
            }
        }
    }*/

    @Override
    public void create(CamionRemorquage obj) throws Exception {
        String sql = "INSERT INTO camion_remorquage (nom_agence, modele, annee, num_tel, statut) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        // Debug: Print the SQL query and parameters
        System.out.println("Executing SQL: " + sql);
        System.out.println("Parameters: " + obj.getNomAgence() + ", " + obj.getModele() + ", " + obj.getAnnee() + ", " + obj.getNum_tel() + ", " + obj.getStatut());

        stmt.setString(1, obj.getNomAgence());
        stmt.setString(2, obj.getModele());
        stmt.setInt(3, obj.getAnnee());
        stmt.setString(4, obj.getNum_tel());
        stmt.setString(5, obj.getStatut());

        int rowsInserted = stmt.executeUpdate();
        if (rowsInserted > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                obj.setId_cr(generatedKeys.getInt(1));
            }
            System.out.println("Camion Remorquage added to the database.");
        } else {
            System.out.println("Failed to add Camion Remorquage to the database.");
        }
    }

    @Override
    public void update(CamionRemorquage obj) throws Exception {
        String sql = "UPDATE camion_remorquage SET nom_agence = ?, modele = ?, annee = ?, num_tel = ?, statut = ? WHERE id_cr = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, obj.getNomAgence());
        stmt.setString(2, obj.getModele());
        stmt.setInt(3, obj.getAnnee());
        stmt.setString(4, obj.getNum_tel());
        stmt.setString(5, obj.getStatut());
        stmt.setInt(6, obj.getId_cr());

        stmt.executeUpdate();
    }

    @Override
    public void updateStatus(int idRec, String newStatus) throws SQLException {

    }

    @Override
    public void delete(CamionRemorquage obj) throws Exception {
        String sql = "DELETE FROM camion_remorquage WHERE id_cr = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, obj.getId_cr());
        stmt.executeUpdate();
    }

    @Override
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM camion_remorquage WHERE id_cr = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    @Override
    public List<CamionRemorquage> getAll() throws Exception {
        List<CamionRemorquage> camions = new ArrayList<>();
        String sql = "SELECT * FROM camion_remorquage";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            CamionRemorquage camion = new CamionRemorquage(
                    rs.getInt("id_cr"),
                    rs.getString("nom_agence"),
                    rs.getString("modele"),
                    rs.getInt("annee"),
                    rs.getString("num_tel"),
                    rs.getString("statut")
            );
            camions.add(camion);
        }
        return camions;
    }

    public CamionRemorquage getById(int id) throws Exception {
        String sql = "SELECT * FROM camion_remorquage WHERE id_cr = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new CamionRemorquage(
                    rs.getInt("id_cr"),
                    rs.getString("nom_agence"),
                    rs.getString("modele"),
                    rs.getInt("annee"),
                    rs.getString("num_tel"),
                    rs.getString("statut")
            );
        }
        return null;
    }
}