package services;

import models.Voiture;
import utils.MyDb;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoitureService implements Crud<Voiture> {
    private Connection conn;

    public VoitureService() {
        this.conn = MyDb.getInstance().getConn();
    }

    @Override
    public void create(Voiture obj) throws Exception {
        String sql = "INSERT INTO voiture (marque, description, kilometrage, couleur, prix, image, id_c, disponibilite) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, obj.getMarque());
        stmt.setString(2, obj.getDescription());
        stmt.setInt(3, obj.getKilometrage());
        stmt.setString(4, obj.getCouleur());
        stmt.setDouble(5, obj.getPrix());
        stmt.setString(6, obj.getImage());
        stmt.setInt(7, obj.getId_c());
        stmt.setString(8, obj.getDisponibilite());

        int rowsInserted = stmt.executeUpdate();
        System.out.println("Nouvelle voiture ajoutée : " + obj.getMarque());

        if (rowsInserted > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                obj.setId_v(generatedId);
                System.out.println("Voiture ajoutée avec ID : " + obj.getId_v());
            }
        } else {
            throw new Exception("Échec de l'insertion de la voiture.");
        }
    }

    @Override
    public void update(Voiture obj) throws Exception {
        String sql = "UPDATE voiture SET marque = ?, description = ?, kilometrage = ?, couleur = ?, prix = ?, image = ?, id_c = ?, disponibilite = ? WHERE id_v = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, obj.getMarque());
        stmt.setString(2, obj.getDescription());
        stmt.setInt(3, obj.getKilometrage());
        stmt.setString(4, obj.getCouleur());
        stmt.setDouble(5, obj.getPrix());
        stmt.setString(6, obj.getImage());
        stmt.setInt(7, obj.getId_c());
        stmt.setString(8, obj.getDisponibilite());
        stmt.setInt(9, obj.getId_v());

        stmt.executeUpdate();
    }

    @Override
    public void delete(int id_v) throws Exception {
        String sql = "DELETE FROM voiture WHERE id_v = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id_v);
        stmt.executeUpdate();
    }

    @Override
    public List<Voiture> getAll() throws Exception {
        String sql = "SELECT * FROM voiture";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Voiture> voitures = new ArrayList<>();

        while (rs.next()) {
            Voiture voiture = new Voiture(
                    rs.getInt("id_v"),
                    rs.getString("marque"),
                    rs.getString("description"),
                    rs.getInt("kilometrage"),
                    rs.getString("couleur"),
                    rs.getDouble("prix"),
                    rs.getString("image"),
                    rs.getInt("id_c"),
                    rs.getString("disponibilite")
            );
            voitures.add(voiture);
        }
        return voitures;
    }
}
