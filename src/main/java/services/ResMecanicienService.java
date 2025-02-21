package services;

import models.ResMecanicien;
import utils.MyDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ResMecanicienService implements Crud<ResMecanicien> {
    Connection conn;

    public ResMecanicienService() {
        this.conn = MyDb.getInstance().getConn();
    }

    @Override
    public void create(ResMecanicien obj) throws Exception {
        String sql = "INSERT INTO res_mecanicien (id_res, id_mec, note) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, obj.getId_res());
        stmt.setInt(2, obj.getId_mec());
        stmt.setString(3, obj.getNote());

        int rowsInserted = stmt.executeUpdate();
        if (rowsInserted > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                obj.setId_res_m(generatedKeys.getInt(1));
            }
        }
    }

    @Override
    public void update(ResMecanicien obj) throws Exception {
        String sql = "UPDATE res_mecanicien SET id_res = ?, id_mec = ?, note = ? WHERE id_res_m = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, obj.getId_res());
        stmt.setInt(2, obj.getId_mec());
        stmt.setString(3, obj.getNote());
        stmt.setInt(4, obj.getId_res_m());

        stmt.executeUpdate();
    }

    @Override
    public void delete(ResMecanicien obj) throws Exception {
        String sql = "DELETE FROM res_mecanicien WHERE id_res_m = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, obj.getId_res_m());
        stmt.executeUpdate();
    }

    @Override
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM res_mecanicien WHERE id_res_m = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    @Override
    public List<ResMecanicien> getAll() throws Exception {
        List<ResMecanicien> reservations = new ArrayList<>();
        String sql = "SELECT * FROM res_mecanicien";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            ResMecanicien res = new ResMecanicien(
                    rs.getInt("id_res_m"),
                    rs.getInt("id_res"),
                    rs.getInt("id_mec"),
                    rs.getString("note")
            );
            reservations.add(res);
        }
        return reservations;
    }

    public ResMecanicien getById(int id) throws Exception {
        String sql = "SELECT * FROM res_mecanicien WHERE id_res_m = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new ResMecanicien(
                    rs.getInt("id_res_m"),
                    rs.getInt("id_res"),
                    rs.getInt("id_mec"),
                    rs.getString("note")
            );
        }
        return null;
    }
}