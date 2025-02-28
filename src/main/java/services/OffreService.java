package services;

import models.Offre;
import utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffreService implements Crud<Offre> {
    Connection conn;

    public OffreService() {
        this.conn = MyDb.getInstance().getConn();
    }

    @Override
    public void create(Offre offre) throws Exception {
        // Fetch the image URL for the equipment before inserting
        String imageUrl = getImageUrlForEquipement(offre.getId_equipement());

        // Use the fetched image URL in the insert statement
        String sql = "INSERT INTO offre (type_offre, description, taux_reduction, date_debut, date_fin, id_equipement, image) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, offre.getType_offre());
        stmt.setString(2, offre.getDescription());
        stmt.setDouble(3, offre.getTaux_reduction());
        stmt.setString(4, offre.getDate_debut());
        stmt.setString(5, offre.getDate_fin());
        stmt.setInt(6, offre.getId_equipement());
        stmt.setString(7, imageUrl);  // Set the image URL here
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            offre.setId_offre(rs.getInt(1));
        }

        System.out.println("Offre ajoutée avec succès !");
    }


    @Override
    public void update(Offre offre) throws Exception {
        // Fetch the image URL for the equipment before updating
        String imageUrl = getImageUrlForEquipement(offre.getId_equipement());

        // Use the fetched image URL in the update statement
        String sql = "UPDATE offre SET type_offre = ?, description = ?, taux_reduction = ?, date_debut = ?, date_fin = ?, id_equipement = ?, image = ? WHERE id_offre = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, offre.getType_offre());
        stmt.setString(2, offre.getDescription());
        stmt.setDouble(3, offre.getTaux_reduction());
        stmt.setString(4, offre.getDate_debut());
        stmt.setString(5, offre.getDate_fin());
        stmt.setInt(6, offre.getId_equipement());
        stmt.setString(7, imageUrl);  // Set the image URL here
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
        String sql = "SELECT o.* FROM offre o";
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

            // Retrieve the image URL from the equipement table based on id_equipement
            String imageUrl = getImageUrlForEquipement(offre.getId_equipement());
            offre.setImage(imageUrl);

            offres.add(offre);
        }
        return offres;
    }

    public String getImageUrlForEquipement(int idEquipement) {
        String imageUrl = null;

        // Establish a database connection
        MyDb connectNow = new MyDb();
        Connection connectDB = connectNow.getConn();

        try {
            // Prepare the query
            String query = "SELECT image FROM equipement WHERE id= ?";
            PreparedStatement preparedStatement = connectDB.prepareStatement(query);
            preparedStatement.setInt(1, idEquipement);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Retrieve the image URL from the result set
            if (resultSet.next()) {
                imageUrl = resultSet.getString("image");


            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return imageUrl;
    }

}