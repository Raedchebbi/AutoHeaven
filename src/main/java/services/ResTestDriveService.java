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
        String sql = "INSERT INTO res_testdrive (id_u, id_v, date, status) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, obj.getId_u());
        stmt.setInt(2, obj.getId_v());
        stmt.setString(3, obj.getDate());
        stmt.setString(4, obj.getStatus());

        stmt.executeUpdate();
    }

    @Override
    public void update(ResTestDrive obj) throws Exception {
        String sql = "UPDATE res_testdrive SET id_u = ?, id_v = ?, date = ?, status = ? WHERE id_td = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, obj.getId_u());
        stmt.setInt(2, obj.getId_v());
        stmt.setString(3, obj.getDate());
        stmt.setString(4, obj.getStatus());
        stmt.setInt(5, obj.getId_td());

        stmt.executeUpdate();
    }

    @Override
    public void delete(ResTestDrive obj) throws Exception {
        String sql = "DELETE FROM res_testdrive WHERE id_td = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, obj.getId_td());
        stmt.executeUpdate();
    }

    @Override
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM res_testdrive WHERE id_td = ?";
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
                    rs.getInt("id_td"),
                    rs.getInt("id_u"),
                    rs.getInt("id_v"),
                    rs.getString("date"),
                    rs.getString("status")
            );
            reservations.add(res);
        }
        return reservations;
    }

    public ResTestDrive getById(int id) throws Exception {
        String sql = "SELECT * FROM res_testdrive WHERE id_td = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new ResTestDrive(
                    rs.getInt("id_td"),
                    rs.getInt("id_u"),
                    rs.getInt("id_v"),
                    rs.getString("date"),
                    rs.getString("status")
            );
        }
        return null;
    }
}
