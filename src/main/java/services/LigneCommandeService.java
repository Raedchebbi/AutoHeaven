package services;

import models.Lignecommande;
import models.User;
import utils.MyDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LigneCommandeService implements CrudLigneCommande<Lignecommande> {

    Connection conn;

    public LigneCommandeService() {
        this.conn = MyDb.getInstance().getConn();

    }

    @Override
    public void create(Lignecommande obj) throws Exception {
        String sql = "INSERT INTO LigneCommande (quantite, prix_unitaire,id_e, idc) VALUES (?, ?, ?, ?)";


        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, obj.getQuantite());
        stmt.setDouble(2, obj.getPrix_unitaire());
        stmt.setInt(3, obj.getId_e());
        stmt.setInt(4, obj.getIdc());
        stmt.executeUpdate();


    }

    @Override
    public List<Lignecommande> getAllByClient(int id) throws Exception {
        List<Lignecommande> ligneCommande = new ArrayList<>();
        String sql = "SELECT * FROM LigneCommande where idc=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
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
    public List<Lignecommande> getAllByIDC(int id) throws Exception {
        List<Lignecommande> ligneCommande = new ArrayList<>();
        String sql = "SELECT * FROM lignecommande where idc=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Lignecommande l = new Lignecommande();
            l.setId_l(rs.getInt("id_l"));
            l.setId_e(rs.getInt("id_e"));
            l.setQuantite(rs.getInt("quantite"));
            l.setPrix_unitaire(rs.getInt("prix_unitaire"));
            l.setIdc(rs.getInt("idc"));
            ligneCommande.add(l);

        }
        return ligneCommande;
    }

    @Override
    public List<Lignecommande> getByIDE(int id) throws Exception {
        String sql = "SELECT * FROM LigneCommande where id_e=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        List<Lignecommande> ligneCommande = new ArrayList<>();
        while (rs.next()) {
            Lignecommande l = new Lignecommande();
            l.setId_l(rs.getInt("id_l"));
            l.setId_e(rs.getInt("id_e"));
            l.setQuantite(rs.getInt("quantite"));
            l.setPrix_unitaire(rs.getInt("prix_unitaire"));
            l.setIdc(rs.getInt("idc"));
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

    @Override
    public User getByID(int id) throws Exception {
        String query = "SELECT * FROM user WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new User(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getInt("tel"), rs.getString("email"), rs.getString("password"), rs.getString("role"), rs.getString("adresse"), rs.getString("username"),rs.getString("photo_profile"),rs.getString("ban"));

    } else {
            throw new Exception("Ligne de commande non trouvée avec l'id_l : " + id);
        }}

    @Override
    public int somEq(int idc, int id_e) throws Exception {
        String sql ="select SUM(quantite) as sum from LigneCommande where idc = ? and id_e = ?";
        PreparedStatement stmt =conn.prepareStatement(sql);
        stmt.setInt(1, idc);
        stmt.setInt(2, id_e);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("sum");

        }
        return 0;
    }

    @Override
    public Map<String, Integer> getTop5Equipements() throws Exception {
        String sql = "SELECT e.nom, SUM(c.quantite) AS total_vendu " +
                "FROM lignecommande c " +
                "JOIN equipement e ON c.id_e = e.id " +
                "GROUP BY e.nom " +
                "ORDER BY total_vendu DESC " +
                "LIMIT 5";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        Map<String, Integer> topEquipements = new LinkedHashMap<>();
        while (rs.next()) {
            topEquipements.put(rs.getString("nom"), rs.getInt("total_vendu"));
        }
        return topEquipements;
    }


}