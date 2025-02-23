package services;

import models.Offre;
import utils.MyDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OffreService implements Crud<Offre> {
    Connection conn;

    public OffreService() {
        this.conn = MyDb.getInstance().getConn();
    }

    @Override
    public void create(Offre offre) throws Exception {
        String sql = "INSERT INTO offre (type_offre, description, taux_reduction, date_debut, date_fin, id_equipement, image) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, offre.getType_offre());
        stmt.setString(2, offre.getDescription());
        stmt.setDouble(3, offre.getTaux_reduction());
        stmt.setString(4, offre.getDate_debut());
        stmt.setString(5, offre.getDate_fin());
        stmt.setInt(6, offre.getId_equipement());
        stmt.setString(7, offre.getImage());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            offre.setId_offre(rs.getInt(1));
        }

        System.out.println("Offre ajoutée avec succès !");
    }

    @Override
    public void update(Offre offre) throws Exception {
        String sql = "UPDATE offre SET type_offre = ?, description = ?, taux_reduction = ?, date_debut = ?, date_fin = ?, id_equipement = ?, image = ? WHERE id_offre = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, offre.getType_offre());
        stmt.setString(2, offre.getDescription());
        stmt.setDouble(3, offre.getTaux_reduction());
        stmt.setString(4, offre.getDate_debut());
        stmt.setString(5, offre.getDate_fin());
        stmt.setInt(6, offre.getId_equipement());
        stmt.setString(7, offre.getImage());
        stmt.setInt(8, offre.getId_offre());

        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Offre mise à jour avec succès !");
        } else {
            throw new Exception("Offre non trouvée.");
        }
    }

    @Override
    public void delete(Offre offre) throws Exception {
        String sql = "DELETE FROM offre WHERE id_offre = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, offre.getId_offre());

        int rowsDeleted = stmt.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Offre supprimée avec succès !");
        } else {
            throw new Exception("Offre non trouvée.");
        }
    }

    @Override
    public List<Offre> getAll() throws Exception {
        String sql = "SELECT * FROM offre";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Offre> offres = new ArrayList<>();

        while (rs.next()) {
            Offre offre = new Offre();
            offre.setId_offre(rs.getInt("id_offre"));
            offre.setType_offre(rs.getString("type_offre"));
            offre.setDescription(rs.getString("description"));
            offre.setTaux_reduction(rs.getDouble("taux_reduction"));
            offre.setDate_debut(rs.getString("date_debut"));
            offre.setDate_fin(rs.getString("date_fin"));
            offre.setId_equipement(rs.getInt("id_equipement"));
            offre.setImage(rs.getString("image"));
            offres.add(offre);
        }
        return offres;
    }
}