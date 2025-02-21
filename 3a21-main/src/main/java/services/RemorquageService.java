package services;

import models.Remorquage;
import utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RemorquageService extends ReservationService<Remorquage> {

    @Override
    public void create(Remorquage reservation) throws Exception {
        String query = "INSERT INTO reservation_remorquage (id, date_res, type, id_cr, pickup_location, dropoff_location) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = MyDb.getInstance().getConn().prepareStatement(query)) {
            pst.setInt(1, reservation.getId());
            pst.setDate(2, new java.sql.Date(reservation.getDate_res().getTime()));
            pst.setString(3, reservation.getType());
            pst.setInt(4,reservation.getId_cr());
            pst.setString(4, reservation.getPickup_location());
            pst.setString(5, reservation.getDropoff_location());
            pst.executeUpdate();
            System.out.println("Reservation Remorquage Ajoutee.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error Creation Reservation Remorquage.");
        }
    }

    @Override
    public void update(Remorquage reservation) throws Exception {
        String query = "UPDATE reservation_remorquage SET id = ?, date_res = ?, type = ?, id_cr = ?, pickup_location = ?, dropoff_location = ? WHERE id_res = ?";
        try (PreparedStatement pst = MyDb.getInstance().getConn().prepareStatement(query)) {
            pst.setInt(1, reservation.getId());
            pst.setDate(2, new java.sql.Date(reservation.getDate_res().getTime()));
            pst.setString(3, reservation.getType());
            pst.setInt(4, reservation.getId_cr());
            pst.setString(5, reservation.getPickup_location());
            pst.setString(6, reservation.getDropoff_location());
            pst.setInt(7, reservation.getId_res());
            pst.executeUpdate();
            System.out.println("Reservation Remorquage Modifiee.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error Modification Reservation Remorquage.");
        }
    }

    @Override
    public List<Remorquage> getAll() throws Exception {
        List<Remorquage> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservation_remorquage";
        try (Statement stmt = MyDb.getInstance().getConn().createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                reservations.add(new Remorquage(
                        rs.getInt("id_res"),
                        rs.getInt("id"),
                        rs.getDate("date_res"),
                        rs.getString("type"),
                        rs.getInt("id_cr"),
                        rs.getString("pickup_location"),
                        rs.getString("dropoff_location")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error Recherche Reservation Remorquage.");
        }
        return reservations;
    }

    @Override
    public Remorquage getById(int id) throws Exception {
        String query = "SELECT * FROM reservation_remorquage WHERE id_res = ?";
        try (PreparedStatement pst = MyDb.getInstance().getConn().prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Remorquage(
                        rs.getInt("id_res"),
                        rs.getInt("id"),
                        rs.getDate("date_res"),
                        rs.getString("type"),
                        rs.getInt("id_cr"),
                        rs.getString("pickup_location"),
                        rs.getString("dropoff_location")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error Recherche avec ID Reservation Remorquage.");
        }
        return null;
    }
}
