package services;

import models.voiture;
import utils.MyDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class voitureService implements Crud<voiture> {
    Connection conn;

    public voitureService() {
        this.conn = MyDb.getInstance().getConn();
    }

    @Override
    public void create(voiture obj) throws Exception {
        String sql = "INSERT INTO voiture (kilometrage, marque, description, couleur, prix) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, obj.getKilometrage());
        stmt.setString(2, obj.getMarque());
        stmt.setString(3, obj.getDescription());
        stmt.setString(4, obj.getCouleur());
        stmt.setFloat(5, obj.getPrix());

        stmt.executeUpdate();
    }

    @Override
    public void update(voiture obj) throws Exception {
        String sql = "UPDATE voiture SET kilometrage = ?, marque = ?, description = ?, couleur = ?, prix = ? WHERE id_v = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, obj.getKilometrage());
        stmt.setString(2, obj.getMarque());
        stmt.setString(3, obj.getDescription());
        stmt.setString(4, obj.getCouleur());
        stmt.setFloat(5, obj.getPrix());
        stmt.setInt(6, obj.getId_v());
        stmt.executeUpdate();
    }
 //stmt.setInt(7, obj.getId_v());
    @Override
    public void delete(voiture obj) throws Exception {
        String sql = "DELETE FROM voiture WHERE id_v = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, obj.getId_v());
        stmt.executeUpdate();
    }

    @Override
    public List<voiture> getAll() throws Exception {
        String sql = "SELECT * FROM voiture";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<voiture> voitures = new ArrayList<>();
        while (rs.next()) {
            voiture voiture = new voiture();
            voiture.setId_v(rs.getInt("id_v")); // Ajout de l'ID voiture
            voiture.setKilometrage(rs.getInt("kilometrage"));
            voiture.setMarque(rs.getString("marque"));
            voiture.setDescription(rs.getString("description"));
            voiture.setCouleur(rs.getString("couleur"));
            voiture.setPrix(rs.getFloat("prix"));
            voitures.add(voiture);
        }
        return voitures;
    }
}