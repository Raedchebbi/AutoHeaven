package services;

import models.Equipement;
import models.EquipementAffichage;
import utils.MyDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;


public class EquipementService implements CrudEquipement<Equipement,EquipementAffichage> {
    Connection conn;

    public EquipementService() {
        this.conn = MyDb.getInstance().getConn();
    }


    @Override
    public void create(Equipement obj, int quantite, double prixvente) throws Exception {

        //String sql2 = "select last_insert_id()";
        String sqlEquipement = "insert into equipement(nom, description, image , reference ,marque) values (?,?,?,?,?)";
        String sqlStock = "insert into stock(id, quantite, prixvente) values (?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sqlEquipement, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, obj.getNom());
        ps.setString(2, obj.getDescription());
        ps.setString(3, obj.getImage());
        ps.setString(4, obj.getReference());
        ps.setString(5, obj.getMarque());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            int id = rs.getInt(1);
            //System.out.println(id);
            ps = conn.prepareStatement(sqlStock);
            ps.setInt(1, id);
            ps.setInt(2, quantite);
            ps.setDouble(3, prixvente);

            ps.executeUpdate();


        }

        //Statement stmt = conn.createStatement();
        //  stmt.executeUpdate(sql);

    }

    @Override
    public void update(Equipement obj, int quantite, double prixvente) throws Exception {
        String sqlEquipement = "update Equipement set nom = ?,description = ?,image = ?,reference = ?,marque = ? where id = ? ";
        String sqlStock = "update Stock SET quantite = ?, prixvente = ? where id = ?";
        PreparedStatement stmt = conn.prepareStatement(sqlEquipement);
        stmt.setString(1, obj.getNom());
        stmt.setString(2, obj.getDescription());
        stmt.setString(3, obj.getImage());
        stmt.setString(4, obj.getReference());
        stmt.setString(5, obj.getMarque());
        stmt.setInt(6, obj.getId());
        stmt.executeUpdate();
        stmt = conn.prepareStatement(sqlStock);
        stmt.setInt(1, quantite);
        stmt.setDouble(2, prixvente);
        stmt.setInt(3, obj.getId());
        stmt.executeUpdate();


    }

    @Override
    public void delete(int id) throws Exception {
        String sqlStock = "delete from stock where id = ?";
        String sqlEquipement = "delete from equipement where id = ?";

        PreparedStatement stmtStock = conn.prepareStatement(sqlStock);
        PreparedStatement stmtEquip = conn.prepareStatement(sqlEquipement);
        stmtStock.setInt(1, id);
        stmtStock.executeUpdate();

        stmtEquip.setInt(1, id);
        stmtEquip.executeUpdate();


    }


    @Override
    public void updateQuantite(int id, int quantite) throws Exception {
        String sqlStock = "UPDATE stock SET quantite = quantite - ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sqlStock);
        stmt.setInt(1, quantite);
        stmt.setInt(2, id);
        stmt.executeUpdate();
    }

    @Override
    public List<EquipementAffichage> getAll() throws Exception {
        String sql = "select e.id ,e.nom ,e.description ,e.image ,e.reference ,e.marque, s.quantite , s.prixvente from equipement e join stock s on e.id = s.id";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<EquipementAffichage> equipements = new ArrayList<>();
        while (rs.next()) {
            equipements.add(new EquipementAffichage(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("description"),
                    rs.getString("image"),
                    rs.getString("reference"),
                    rs.getString("marque"),
                    rs.getInt("quantite"),
                    rs.getDouble("prixvente")));


        }
        return equipements;

    }

    @Override
    public Equipement getEquipementById(int id) throws Exception {
        String sql = "select * from equipement where id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Equipement(rs.getInt("id"), rs.getString("nom"), rs.getString("description"), rs.getString("image"), rs.getString("reference"), rs.getNString("marque"));
        } else {
            throw new Exception("Equipement not found with id: " + id);
        }
    }
    public List<Equipement> rechercherEquipement(String critere) throws Exception {
        String sql = "SELECT * FROM equipement WHERE nom LIKE ? OR reference LIKE ? OR marque LIKE ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        String critereRecherche = "%" + critere + "%";
        stmt.setString(1, critereRecherche);
        stmt.setString(2, critereRecherche);
        stmt.setString(3, critereRecherche);
        try (ResultSet rs = stmt.executeQuery()) {
            List<Equipement> equipements = new ArrayList<>();
            while (rs.next()) {
                Equipement equipement = new Equipement(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getString("image"),
                        rs.getString("reference"),
                        rs.getString("marque")
                );
                equipements.add(equipement);
            }
            return equipements;
        }
    }

    @Override
    public Map<String ,Integer> eqRef() throws Exception {
        String sql ="SELECT id, reference  FROM equipement" ;
                PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        Map<String,Integer> equipements = new HashMap<>();
        while (rs.next()) {
            equipements.put(rs.getString("reference"),rs.getInt("id"));

        }
        return equipements;
    }

}

