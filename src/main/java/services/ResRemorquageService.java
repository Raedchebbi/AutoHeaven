package services;

import models.ResRemorquage;
import utils.MyDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ResRemorquageService implements Crud<ResRemorquage> {
    Connection conn;

    public ResRemorquageService() {
        this.conn = MyDb.getInstance().getConn();
    }

    @Override
    public void create(ResRemorquage obj) throws Exception {
        String sql = "INSERT INTO res_remorquage (id_res, id_cr, pickup_location, dropoff_location) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, obj.getId_res());
        stmt.setInt(2, obj.getId_cr());
        stmt.setString(3, obj.getPickup_location());
        stmt.setString(4, obj.getDropoff_location());

        int rowsInserted = stmt.executeUpdate();
        if (rowsInserted > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                obj.setId_res_remo(generatedKeys.getInt(1));
            }
        }
    }

    @Override
    public void update(ResRemorquage obj) throws Exception {
        String sql = "UPDATE res_remorquage SET id_res = ?, id_cr = ?, pickup_location = ?, dropoff_location = ? WHERE id_res_remo = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, obj.getId_res());
        stmt.setInt(2, obj.getId_cr());
        stmt.setString(3, obj.getPickup_location());
        stmt.setString(4, obj.getDropoff_location());
        stmt.setInt(5, obj.getId_res_remo());

        stmt.executeUpdate();
    }

    @Override
    public void delete(ResRemorquage obj) throws Exception {
        String sql = "DELETE FROM res_remorquage WHERE id_res_remo = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, obj.getId_res_remo());
        stmt.executeUpdate();
    }

    @Override
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM res_remorquage WHERE id_res_remo = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    @Override
    public List<ResRemorquage> getAll() throws Exception {
        List<ResRemorquage> reservations = new ArrayList<>();
        String sql = "SELECT * FROM res_remorquage";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            ResRemorquage res = new ResRemorquage(
                    rs.getInt("id_res_remo"),
                    rs.getInt("id_res"),
                    rs.getInt("id_cr"),
                    rs.getString("pickup_location"),
                    rs.getString("dropoff_location")
            );
            reservations.add(res);
        }
        return reservations;
    }

    public ResRemorquage getById(int id) throws Exception {
        String sql = "SELECT * FROM res_remorquage WHERE id_res_remo = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new ResRemorquage(
                    rs.getInt("id_res_remo"),
                    rs.getInt("id_res"),
                    rs.getInt("id_cr"),
                    rs.getString("pickup_location"),
                    rs.getString("dropoff_location")
            );
        }
        return null;
    }
}