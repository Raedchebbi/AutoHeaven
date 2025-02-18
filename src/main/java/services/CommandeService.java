package services;

import models.Commande;
import models.EquipementAffichage;
import models.Lignecommande;
import models.Panier;
import utils.MyDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

public class CommandeService implements CrudCommande<Commande> {
    Connection conn;
    private LigneCommandeService ligneCommandeService = new LigneCommandeService();
    private PanierService panierService = new PanierService();

    public CommandeService() {
        this.conn = MyDb.getInstance().getConn();
    }

    @Override
    public int create(int id) throws Exception {
        String sql = "INSERT INTO commande (id, date_com, status, montant_total) VALUES (?, NOW(), 'en attente', 0)";
        int idCommande = -1;


        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        stmt.setInt(1, id);
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            idCommande = rs.getInt(1);
        }


        if (idCommande > 0) {
            List<Panier> panier = panierService.getAll(id);
            double total = 0;

            for (Panier item : panier) {
                double prixUnitaire = getPrixEquipement(item.getId_e());
                total += prixUnitaire * item.getQuantite();
                Lignecommande l = new Lignecommande(item.getQuantite(), prixUnitaire, item.getId_e(), idCommande);
                ligneCommandeService.create(l);
            }

            // Mettre à jour le montant total de la commande
            update(idCommande, total);

            // Vider le panier
            panierService.delete(id);
        }


        return idCommande;

    }


    @Override
    public void update(int id, double montantTotal) throws Exception {
        String sql = "UPDATE commande SET montant_total = ? WHERE id_com = ?";


        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setDouble(1, montantTotal);
        stmt.setInt(2, id);
        stmt.executeUpdate();


    }

    @Override
    public double getPrixEquipement(int id_e) throws Exception {
        String sql = "SELECT prixvente FROM stock WHERE id = ?";
        double prix = 0;


        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, id_e);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            prix = rs.getDouble("prixvente");
        }

        return prix;
    }

    @Override
    public List<Commande> getAllByidU(int id) throws Exception {
        String sql = "SELECT * FROM commande WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        List<Commande> coms = new ArrayList<>();
        while (rs.next()) {
            coms.add(new Commande(
                    rs.getInt("id_com"),
                    rs.getTimestamp("date_com").toLocalDateTime(),
                    rs.getString("status"),
                    rs.getDouble("montant_total"),
                    rs.getInt("id")
            ));
        }
        return coms;
    }

    @Override
    public Commande getCommande(int id) throws Exception {
        String sql = "SELECT * FROM commande WHERE id_com = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Commande(
                    rs.getInt("id_com"),
                    rs.getTimestamp("date_com").toLocalDateTime(),
                    rs.getString("status"),
                    rs.getDouble("montant_total"),
                    rs.getInt("id")
            );
        } else {
            throw new Exception("Commande non trouvée avec l'id : " + id);
        }
    }

    @Override
    public List<Commande> getAll(int id) throws Exception {
        String sql = "SELECT * FROM commande";
        PreparedStatement stmt = conn.prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();

        List<Commande> coms = new ArrayList<>();
        while (rs.next()) {
            coms.add(new Commande(
                    rs.getInt("id_com"),
                    rs.getTimestamp("date_com").toLocalDateTime(),
                    rs.getString("status"),
                    rs.getDouble("montant_total"),
                    rs.getInt("id")
            ));
        }
        return coms;
    }

}