package services;

import models.TestDrive;
import utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestDriveService extends ReservationService<TestDrive> {
    private Connection conn;

    public TestDriveService() {
        this.conn = MyDb.getInstance().getConn();
    }

    @Override
    public void create(TestDrive reservation) throws Exception {
        String sql = "INSERT INTO reservation_testdrive (id, date_res, type, id_v) VALUES (?, ?, ?, ?)";

        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, reservation.getId());
        stmt.setDate(2, new java.sql.Date(reservation.getDate_res().getTime()));
        stmt.setString(3, reservation.getType());
        stmt.setInt(4, reservation.getId_v());
        stmt.executeUpdate();

        int rowsInserted = stmt.executeUpdate();
        System.out.println("Reservation TestDrive Ajoutee.");

        if (rowsInserted > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                reservation.setId(generatedId);
                System.out.println("Reservation TestDrive ajoutee avec ID : " + reservation.getId());
            }
        } else {
            throw new Exception("Échec de l'insertion du reservation testdrive !.");
        }
    }

    @Override
    public void update(TestDrive reservation) throws Exception {
        String query = "UPDATE reservation_testdrive SET id = ?, date_res = ?, type = ?, id_v = ? WHERE id_res = ?";
        try (PreparedStatement pst = MyDb.getInstance().getConn().prepareStatement(query)) {
            pst.setInt(1, reservation.getId());
            pst.setDate(2, new java.sql.Date(reservation.getDate_res().getTime()));
            pst.setString(4, reservation.getType());
            pst.setInt(3, reservation.getId_v());
            pst.setInt(5, reservation.getId_res());
            pst.executeUpdate();
            System.out.println("Reservation TestDrive Modifiee.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error Modification Reservation TestDrive.");
        }
    }

    @Override
    public List<TestDrive> getAll() throws Exception {
        List<TestDrive> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservation_testdrive";
        try (Statement stmt = MyDb.getInstance().getConn().createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                reservations.add(new TestDrive(
                        rs.getInt("id_res"),
                        rs.getInt("id"),
                        rs.getDate("date_res"),
                        rs.getString("type"),
                        rs.getInt("id_v")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error Recherche Reservation TestDrive.");
        }
        return reservations;
    }

    @Override
    public TestDrive getById(int id) throws Exception {
        String query = "SELECT * FROM reservation_testdrive WHERE id_res = ?";
        try (PreparedStatement pst = MyDb.getInstance().getConn().prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new TestDrive(
                        rs.getInt("id_res"),
                        rs.getInt("id"),
                        rs.getDate("date_res"),
                        rs.getString("type"),
                        rs.getInt("id_v")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error Recherche avec ID Reservation TestDrive.");
        }
        return null;
    }
}