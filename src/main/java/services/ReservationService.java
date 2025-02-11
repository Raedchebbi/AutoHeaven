package services;

import models.Reservation;
import utils.MyDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements Crud<Reservation> {
    Connection conn;

    public ReservationService() {
        this.conn = MyDb.getInstance().getConn();
    }

    @Override
    public void create(Reservation obj) throws Exception {
        String sql = "INSERT INTO reservation (date_res, status, id_v, id) VALUES (?, ?, ?, ?)";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setDate(1, new java.sql.Date(obj.getDate_res().getTime()));
        
        stmt.setString(2, obj.getStatus());
        stmt.setInt(3, obj.getId_v());
        stmt.setInt(4, obj.getId());
    }

    @Override
    public void update(Reservation obj) throws Exception {
        String sql = "UPDATE reservation SET date_res = ?, status = ?, id_v = ?, id = ? WHERE id_r = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDate(1, new java.sql.Date(obj.getDate_res().getTime()));
        stmt.setString(2, obj.getStatus());
        stmt.setInt(3, obj.getId_v());
        stmt.setInt(4, obj.getId());
        stmt.setInt(5, obj.getId_r());
        stmt.executeUpdate();
    }

    @Override
    public void delete(Reservation obj) throws Exception {
        String sql = "DELETE FROM reservation WHERE id_r = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, obj.getId_r());
        stmt.executeUpdate();
    }

    @Override
    public List<Reservation> getAll() throws Exception {
        String sql = "SELECT * FROM reservation";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Reservation> reservations = new ArrayList<>();
        while (rs.next()) {
            Reservation reservation = new Reservation();
            reservation.setId_r(rs.getInt("id_r"));
            reservation.setDate_res(rs.getDate("date_res"));
            reservation.setStatus(rs.getString("status"));
            reservation.setId_v(rs.getInt("id_v"));
            reservation.setId(rs.getInt("id"));
            reservations.add(reservation);
        }
        return reservations;
    }

    public Reservation getById(int id_r) throws Exception {
        String sql = "SELECT * FROM reservation WHERE id_r = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id_r);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Reservation reservation = new Reservation();
            reservation.setId_r(rs.getInt("id_r"));
            reservation.setDate_res(rs.getDate("date_res"));
            reservation.setStatus(rs.getString("status"));
            reservation.setId_v(rs.getInt("id_v"));
            reservation.setId(rs.getInt("id"));
            return reservation;
        }
        return null;
    }
}