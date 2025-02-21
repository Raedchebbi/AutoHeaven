package services;

import models.Categorie;
import services.Crud;
import utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieService implements Crud<Categorie> {
    Connection conn;

    public CategorieService() {
        this.conn = MyDb.getInstance().getConn();
    }

    @Override
    public void create(Categorie obj) throws Exception {
        String sql = "INSERT INTO categorie (type, type_carburant, type_utilisation, nbr_porte, transmission) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, obj.getType());
        stmt.setString(2, obj.getType_carburant());
        stmt.setString(3, obj.getType_utilisation());
        stmt.setInt(4, obj.getNbr_porte());
        stmt.setString(5, obj.getTransmission());
        stmt.executeUpdate();
    }

    @Override
    public void update(Categorie obj) throws Exception {
        String sql = "UPDATE categorie SET type = ?, type_carburant = ?, type_utilisation = ?, nbr_porte = ?, transmission = ? WHERE id_c = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, obj.getType());
        stmt.setString(2, obj.getType_carburant());
        stmt.setString(3, obj.getType_utilisation());
        stmt.setInt(4, obj.getNbr_porte());
        stmt.setString(5, obj.getTransmission());
        stmt.setInt(6, obj.getId_c());
        stmt.executeUpdate();
    }

    @Override
    public void delete(Categorie obj) throws Exception {

    }

    @Override
    public void delete(int id_c) throws Exception {
        String checkSql = "SELECT id_c FROM categorie WHERE id_c = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkSql);
        checkStmt.setInt(1, id_c);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next()) {
            String sql = "DELETE FROM categorie WHERE id_c = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id_c);
            int deleted = stmt.executeUpdate();

            if (deleted > 0) {
                System.out.println("✅ Catégorie supprimée avec succès !");
            } else {
                System.out.println("❌ Échec de la suppression de la catégorie.");
            }
        } else {
            throw new Exception("❌ La catégorie avec l'ID " + id_c + " n'existe pas.");
        }
    }

    @Override
    public List<Categorie> getAll() throws Exception {
        String sql = "SELECT * FROM categorie";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Categorie> categories = new ArrayList<>();

        while (rs.next()) {
            Categorie cat = new Categorie(
                    rs.getInt("id_c"),
                    rs.getString("type"),
                    rs.getString("type_carburant"),
                    rs.getString("type_utilisation"),
                    rs.getInt("nbr_porte"),
                    rs.getString("transmission")
            );
            categories.add(cat);
        }
        return categories;
    }
}