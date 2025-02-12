package services;

import models.Adresse;
import utils.MyDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdresseService implements Crud<Adresse> {
    Connection conn;

    public AdresseService() {
        this.conn = MyDb.getInstance().getConn();
    }

    @Override
    public void create(Adresse adresse) throws Exception {
        String sql = "INSERT INTO adresse (rue, ville, code_postale, nom_batiment, numero_porte, user_id) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, adresse.getRue());
        stmt.setString(2, adresse.getVille());
        stmt.setString(3, adresse.getCodePostal());
        stmt.setString(4, adresse.getNomBatiment());
        stmt.setString(5, adresse.getNumeroPorte());
        stmt.setInt(6, adresse.getUserId());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            adresse.setId_ad(rs.getInt(1));
        }

        System.out.println("Adresse ajoutée avec succès !");
    }


    @Override
    public void update(Adresse adresse) throws Exception {
        String sql = "UPDATE adresse SET rue = ?, ville = ?, code_postale = ?, nom_batiment = ?, numero_porte = ? WHERE id_ad = ? AND user_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, adresse.getRue());
        stmt.setString(2, adresse.getVille());
        stmt.setString(3, adresse.getCodePostal());
        stmt.setString(4, adresse.getNomBatiment());
        stmt.setString(5, adresse.getNumeroPorte());
        stmt.setInt(6, adresse.getId_ad());
        stmt.setInt(7, adresse.getUserId());

        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("");
        } else {
            throw new Exception(" Adresse non trouvée ou permission refusée.");
        }
    }

    @Override
    public void delete(int obj) throws Exception {

    }



    @Override
    public List<Adresse> getAll() throws Exception {
        String sql = "SELECT * FROM adresse";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Adresse> adresses = new ArrayList<>();

        while (rs.next()) {
            Adresse adresse = new Adresse();
            adresse.setId_ad(rs.getInt("id_ad"));
            adresse.setRue(rs.getString("rue"));
            adresse.setVille(rs.getString("ville"));
            adresse.setCodePostal(rs.getString("code_postale"));
            adresse.setNomBatiment(rs.getString("nom_batiment"));
            adresse.setNumeroPorte(rs.getString("numero_porte"));
            adresse.setUserId(rs.getInt("user_id"));
            adresses.add(adresse);
        }
        return adresses;
    }

}
