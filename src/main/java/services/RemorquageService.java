package services;

import models.Remorquage;
import utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RemorquageService implements Crud<Remorquage> {
    private final Connection conn;

    public RemorquageService() {
        this.conn = MyDb.getInstance().getConnection();
    }

    @Override
    public void create(Remorquage obj) throws Exception {
        String sql = "INSERT INTO remorquage (id, id_v, date_demande, adresse, destination, statut, prix) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, obj.getUser().getId());
            //stmt.setInt(2, obj.getVoiture().getId_v());
            stmt.setInt(2, obj.getId_v());
            stmt.setTimestamp(3, new Timestamp(obj.getDate_demande().getTime()));
            stmt.setString(4, obj.getAdresse());
            stmt.setString(5, obj.getDestination());
            stmt.setString(6, "en_attente");
            stmt.setDouble(7, obj.getPrix());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        obj.setId_rm(generatedKeys.getInt(1));
                        System.out.println("Demande de remorquage ajoutée avec ID : " + obj.getId_rm());
                    }
                }
            } else {
                throw new Exception("Échec de l'insertion de la demande de remorquage.");
            }
        }
    }

    @Override
    public void update(Remorquage obj) throws Exception {
        String sql = "UPDATE remorquage SET statut = ?, date_fin = ? WHERE id_rm = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getStatut());
            stmt.setTimestamp(2, new Timestamp(obj.getDate_fin().getTime()));
            stmt.setInt(3, obj.getId_rm());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Remorquage obj) throws Exception {
        String sql = "DELETE FROM remorquage WHERE id_rm = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, obj.getId_rm());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Remorquage> getAll() throws Exception {
        List<Remorquage> remorquages = new ArrayList<>();
        String sql = "SELECT * FROM remorquage";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Remorquage remorquage = new Remorquage();
                remorquage.setId_rm(rs.getInt("id_rm"));
                remorquage.setUser(UserService.getById(rs.getInt("id")));
                //remorquage.setVoiture(voitureService.getById(rs.getInt("id_v")));
                remorquage.setId_v(rs.getInt("id_v"));
                remorquage.setDate_demande(rs.getTimestamp("date_demande"));
                remorquage.setAdresse(rs.getString("adresse"));
                remorquage.setDestination(rs.getString("destination"));
                remorquage.setStatut(rs.getString("statut"));
                remorquage.setPrix(rs.getDouble("prix"));
                remorquage.setDate_fin(rs.getTimestamp("date_fin"));

                remorquages.add(remorquage);
            }
        }
        return remorquages;
    }

    public Remorquage getById(int id_rm) throws Exception {
        String sql = "SELECT * FROM remorquage WHERE id_rm = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_rm);
            ResultSet rs = stmt.executeQuery();
            UserService userService = new UserService();
            //VoitureService voitureService = new VoitureService();

            if (rs.next()) {
                Remorquage remorquage = new Remorquage();
                remorquage.setId_rm(rs.getInt("id_rm"));
                userService.getById(rs.getInt("id"));
                remorquage.setId_v(rs.getInt("id_v"));
                //voitureService.getById(rs.getInt("id_v")),
                remorquage.setDate_demande(rs.getTimestamp("date_demande"));
                remorquage.setAdresse(rs.getString("adresse"));
                remorquage.setDestination(rs.getString("destination"));
                remorquage.setStatut(rs.getString("statut"));
                remorquage.setPrix(rs.getDouble("prix"));
                remorquage.setDate_fin(rs.getTimestamp("date_fin"));

                return remorquage;
            }
        }
        return null;
    }
}