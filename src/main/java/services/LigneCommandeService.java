package services;

import models.Lignecommande;
import utils.MyDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LigneCommandeService implements CrudLigneCommande<Lignecommande>{

    Connection conn;
    public LigneCommandeService() {
        this.conn = MyDb.getInstance().getConn();

    }
    @Override
    public void create(Lignecommande obj) throws Exception {
        String sql = "INSERT INTO LigneCommande (quantite, prix_unitaire,id_e, idc) VALUES (?, ?, ?, ?)";


        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1,obj.getQuantite());
        stmt.setDouble(2,obj.getPrix_unitaire());
        stmt.setInt(3, obj.getId_e());
        stmt.setInt(4,obj.getIdc());
        stmt.executeUpdate();



    }

    @Override
    public List<Lignecommande> getAll(int id) throws Exception {
        List<Lignecommande> ligneCommande = new ArrayList<>();
        String sql = "SELECT * FROM LigneCommande where idc=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1,id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Lignecommande l = new Lignecommande();
            l.setId_l(rs.getInt("id_l"));
            l.setId_e(rs.getInt("id_e"));
            l.setQuantite(rs.getInt("quantite"));
            l.setPrix_unitaire(rs.getInt("prix_unitaire"));
            l.setIdc(rs.getInt("id_c"));
            ligneCommande.add(l);

        }
        return ligneCommande;
    }

    @Override
    public Lignecommande getById(int id) throws Exception {
        String sql = "SELECT * FROM lignecommande WHERE id_l = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Lignecommande(
                    rs.getInt("id_l"),
                    rs.getInt("quantite"),
                    rs.getDouble("prix_unitaire"),
                    rs.getInt("id_e"),
                    rs.getInt("idc")
            );
        } else {
            throw new Exception("Ligne de commande non trouvée avec l'id_l : " + id);
        }
    }


}