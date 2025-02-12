package services;

import models.categorie;
import models.voiture;
import utils.MyDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class categorieService implements Crud<categorie> {
    Connection conn;

    public categorieService() {
        this.conn = MyDb.getInstance().getConn();
    }

    @Override
    public void create(categorie obj) throws Exception {
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
    public void update(categorie obj) throws Exception {
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
    public void delete(categorie obj) throws Exception {
        String sql = "DELETE FROM categorie WHERE id_c = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, obj.getId_c());
        stmt.executeUpdate();
    }

    @Override
    public List<categorie> getAll() throws Exception {
        String sql = "SELECT * FROM categorie";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<categorie> categories = new ArrayList<>();
        while (rs.next()) {
            categorie cat = new categorie();
            cat.setId_c(rs.getInt("id_c"));
            cat.setType(rs.getString("type"));
            cat.setType_carburant(rs.getString("type_carburant"));
            cat.setType_utilisation(rs.getString("type_utilisation"));
            cat.setNbr_porte(rs.getInt("nbr_porte"));
            cat.setTransmission(rs.getString("transmission"));
            categories.add(cat);
        }
        return categories;
    }
}
