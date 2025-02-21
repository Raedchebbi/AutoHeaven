package services;

import models.Mecanicien;
import utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MecanicienService extends ReservationService<Mecanicien> {

    @Override
    public void create(Mecanicien reservation) throws Exception {
        String query = "INSERT INTO reservation_mecanicien (id, date_res, type, id_mec, note) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = MyDb.getInstance().getConn().prepareStatement(query)) {
            pst.setInt(1, reservation.getId());
            pst.setDate(2, new java.sql.Date(reservation.getDate_res().getTime()));
            pst.setString(3, reservation.getType());
            pst.setInt(4, reservation.getId_mec());
            pst.setString(5, reservation.getNote());
            pst.executeUpdate();
            System.out.println("Reservation Mecanicien Ajoutee.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error Creation Reservation Mecanicien .");
        }
    }

    @Override
    public void update(Mecanicien reservation) throws Exception {
        String query = "UPDATE reservation_mecanicien SET id = ?, date_res = ?, type = ?, id_mec = ?, note = ? WHERE id_res = ?";
        try (PreparedStatement pst = MyDb.getInstance().getConn().prepareStatement(query)) {
            pst.setInt(1, reservation.getId());
            pst.setDate(2, new java.sql.Date(reservation.getDate_res().getTime()));
            pst.setString(3, reservation.getType());
            pst.setInt(4, reservation.getId_mec());
            pst.setString(5, reservation.getNote());
            pst.setInt(6, reservation.getId_res());
            pst.executeUpdate();
            System.out.println("Reservation Mecanicien Modifiee.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error Modification Reservation Mecanicien.");
        }
    }

    @Override
    public List<Mecanicien> getAll() throws Exception {
        List<Mecanicien> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservation_mecanicien";
        try (Statement stmt = MyDb.getInstance().getConn().createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                reservations.add(new Mecanicien(
                        rs.getInt("id_res"),
                        rs.getInt("id"),
                        rs.getDate("date_res"),
                        rs.getString("type"),
                        rs.getInt("id_mec"),
                        rs.getString("note")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error Recherche Reservation Mecanicien.");
        }
        return reservations;
    }

    @Override
    public Mecanicien getById(int id) throws Exception {
        String query = "SELECT * FROM reservation_mecanicien WHERE id_res = ?";
        try (PreparedStatement pst = MyDb.getInstance().getConn().prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Mecanicien(
                        rs.getInt("id_res"),
                        rs.getInt("id"),
                        rs.getDate("date_res"),
                        rs.getString("type"),
                        rs.getInt("id_mec"),
                        rs.getString("note")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error Recherche avec ID Reservation Mecanicien.");
        }
        return null;
    }
}
