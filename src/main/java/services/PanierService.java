package services;

import models.Equipement;
import models.Panier;
import utils.MyDb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class PanierService implements CrudPanier<Panier>{
    Connection conn;
    public PanierService(){
        this.conn = MyDb.getInstance().getConn();
    }



    @Override
    public void create(Panier obj) throws Exception {

        String sql = "insert into panier (quantite,id,id_e) values ('" +
                1 + "','" + obj.getId() + "','" +
                obj.getId_e() + "')";
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);

    }

    @Override
    public void update(Panier obj) throws Exception {
        String sql = "UPDATE panier SET quantite = quantite + 1 WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, obj.getId()); // Supposant que id est un entier
            stmt.executeUpdate();
        }
    }


    @Override
    public void delete(int id) throws Exception {
        String sql = "delete from panier where id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1,id);
        stmt.executeUpdate();

    }

    @Override
    public List<Panier> getAll(int id) throws Exception {
        String sql = "select * from panier where id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        List<Panier> paniers = new ArrayList<>();
        while (rs.next()) {

            Panier obj = new Panier();
            obj.setId(rs.getInt("id"));
            obj.setQuantite(rs.getInt("quantite"));
            obj.setId_e(rs.getInt("id_e"));

            paniers.add(obj);
        }
        return paniers;
    }

    @Override
    public Equipement getEquipementId(int id) throws Exception {
        String sql = "select * from equipement where id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Equipement(rs.getInt("id"), rs.getString("nom"), rs.getString("description"), rs.getString("image"),rs.getString("reference"),rs.getString("marque"));
        }
        else {
            throw new Exception("Equipement not found with id: " + id);
        }

    }
}

