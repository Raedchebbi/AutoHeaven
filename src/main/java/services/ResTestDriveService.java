package services;

import models.ResTestDrive;
import utils.MyDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ResTestDriveService implements Crud<ResTestDrive> {
    Connection conn;

    public ResTestDriveService() {
        this.conn = MyDb.getInstance().getConn();
    }

    @Override
    public void create(ResTestDrive obj) throws Exception {
        String sql = "INSERT INTO res_testdrive (id_res, id_v) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, obj.getId_res());
        stmt.setInt(2, obj.getId_v());

        int rowsInserted = stmt.executeUpdate();
        if (rowsInserted > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                obj.setId_res_td(generatedKeys.getInt(1));
            }
        }
    }

    @Override
    public void update(ResTestDrive obj) throws Exception {
        String sql = "UPDATE res_testdrive SET id_res = ?, id_v = ? WHERE id_res_td = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, obj.getId_res());
        stmt.setInt(2, obj.getId_v());
        stmt.setInt(3, obj.getId_res_td());

        stmt.executeUpdate();
    }

    @Override
    public void delete(ResTestDrive obj) throws Exception {
        String sql = "DELETE FROM res_testdrive WHERE id_res_td = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, obj.getId_res_td());
        stmt.executeUpdate();
    }

    @Override
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM res_testdrive WHERE id_res_td = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    @Override
    public List<ResTestDrive> getAll() throws Exception {
        List<ResTestDrive> reservations = new ArrayList<>();
        String sql = "SELECT * FROM res_testdrive";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            ResTestDrive res = new ResTestDrive(
                    rs.getInt("id_res_td"),
                    rs.getInt("id_res"),
                    rs.getInt("id_v")
            );
            reservations.add(res);
        }
        return reservations;
    }

    public ResTestDrive getById(int id) throws Exception {
        String sql = "SELECT * FROM res_testdrive WHERE id_res_td = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new ResTestDrive(
                    rs.getInt("id_res_td"),
                    rs.getInt("id_res"),
                    rs.getInt("id_v")
            );
        }
        return null;
    }
}