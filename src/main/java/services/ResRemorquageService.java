package services;

import models.ResRemorquage;
import utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResRemorquageService implements Crud<ResRemorquage> {
    Connection conn;

    public ResRemorquageService() {
        this.conn = MyDb.getInstance().getConn();
    }

    @Override
    public void create(ResRemorquage obj) throws Exception {
        String sql = "INSERT INTO res_remorquage (id_u, id_cr, point_ramassage, point_depot, date, status) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, obj.getId_u());
        stmt.setInt(2, obj.getId_cr());
        stmt.setString(3, obj.getPoint_ramassage());
        stmt.setString(4, obj.getPoint_depot());
        stmt.setDate(5, obj.getDate());  // Set the date
        stmt.setString(6, obj.getStatus()); // Ajouter le statut

        int rowsInserted = stmt.executeUpdate();
        if (rowsInserted > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                obj.setId_rem(generatedKeys.getInt(1));
            }
        }
    }

    @Override
    public void update(ResRemorquage obj) throws Exception {
        String sql = "UPDATE res_remorquage SET id_u = ?, id_cr = ?, point_ramassage = ?, point_depot = ?, date = ?, status = ? WHERE id_rem = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, obj.getId_u());
        stmt.setInt(2, obj.getId_cr());
        stmt.setString(3, obj.getPoint_ramassage());
        stmt.setString(4, obj.getPoint_depot());
        stmt.setDate(5, obj.getDate());  // Set the date
        stmt.setString(6, obj.getStatus()); // Ajouter le statut
        stmt.setInt(7, obj.getId_rem());

        stmt.executeUpdate();
    }

    @Override
    public void updateStatus(int idRec, String newStatus) throws SQLException {

    }

    @Override
    public void delete(ResRemorquage obj) throws Exception {
        String sql = "DELETE FROM res_remorquage WHERE id_rem = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, obj.getId_rem());
        stmt.executeUpdate();
    }

    @Override
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM res_remorquage WHERE id_rem = ?";
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
                    rs.getInt("id_rem"),
                    rs.getInt("id_u"),
                    rs.getInt("id_cr"),
                    rs.getString("point_ramassage"),
                    rs.getString("point_depot"),
                    rs.getDate("date"),  // Get the date
                    rs.getString("status") // Récupérer le statut
            );
            reservations.add(res);
        }
        return reservations;
    }

    public ResRemorquage getById(int id) throws Exception {
        String sql = "SELECT * FROM res_remorquage WHERE id_rem = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new ResRemorquage(
                    rs.getInt("id_rem"),
                    rs.getInt("id_u"),
                    rs.getInt("id_cr"),
                    rs.getString("point_ramassage"),
                    rs.getString("point_depot"),
                    rs.getDate("date"),  // Get the date
                    rs.getString("status") // Récupérer le statut
            );
        }
        return null;
    }
}