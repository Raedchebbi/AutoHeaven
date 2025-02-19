package services;

import models.Reservation;
import utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private Connection conn;

    public ReservationService() {
        this.conn = MyDb.getInstance().getConn();
    }

    // Méthode pour créer une nouvelle réservation
    public void create(Reservation reservation) throws Exception {
        String sql = "INSERT INTO reservation (date_res, status, id_v, id) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setDate(1, new java.sql.Date(reservation.getDate_res().getTime()));
        stmt.setString(2, reservation.getStatus());
        stmt.setInt(3, reservation.getId_v());
        stmt.setInt(4, reservation.getId());

        int rowsInserted = stmt.executeUpdate();

        if (rowsInserted > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                reservation.setId_r(generatedKeys.getInt(1));
                System.out.println("Réservation ajoutée avec ID : " + reservation.getId_r());
            }
        } else {
            throw new Exception("Échec de l'insertion de la réservation.");
        }
    }

    // Méthode pour mettre à jour une réservation
    public void update(Reservation reservation) throws Exception {
        String sql = "UPDATE reservation SET date_res = ?, status = ?, id_v = ?, id = ? WHERE id_r = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDate(1, new java.sql.Date(reservation.getDate_res().getTime()));
        stmt.setString(2, reservation.getStatus());
        stmt.setInt(3, reservation.getId_v());
        stmt.setInt(4, reservation.getId());
        stmt.setInt(5, reservation.getId_r());

        stmt.executeUpdate();
    }

    // Méthode pour supprimer une réservation
    public void delete(int id_r) throws Exception {
        String sql = "DELETE FROM reservation WHERE id_r = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id_r);
        stmt.executeUpdate();
    }

    // Méthode pour récupérer une réservation par son ID
    public Reservation getById(int id_r) throws Exception {
        String sql = "SELECT * FROM reservation WHERE id_r = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id_r);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new Reservation(
                    rs.getInt("id_r"),
                    rs.getDate("date_res"),
                    rs.getString("status"),
                    rs.getInt("id_v"),
                    rs.getInt("id")
            );
        }
        return null; // Aucune réservation trouvée
    }

    // Méthode pour récupérer toutes les réservations
    public List<Reservation> getAll() throws Exception {
        String sql = "SELECT * FROM reservation";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Reservation> reservations = new ArrayList<>();

        while (rs.next()) {
            Reservation reservation = new Reservation(
                    rs.getInt("id_r"),
                    rs.getDate("date_res"),
                    rs.getString("status"),
                    rs.getInt("id_v"),
                    rs.getInt("id")
            );
            reservations.add(reservation);
        }
        return reservations;
    }
}