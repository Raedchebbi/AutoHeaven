package controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import models.Commande;
import models.Lignecommande;
import models.Panier;
import services.CommandeService;
import services.EquipementService;
import services.LigneCommandeService;
import services.StockService;

import java.util.ArrayList;
import java.util.List;

public class HistoriqueComItem {
    @FXML
    private Label date;

    @FXML
    private FontAwesomeIconView detail;

    @FXML
    private Label prix;

    @FXML
    private Label status;
    private Commande commande;
    private HistoriqueCommande HisCon;
    public void setListCommandeController(HistoriqueCommande HisCon) {
        this.HisCon = HisCon;
    }

    public void initData(Commande commande) throws Exception {
        this.commande = commande;
       CommandeService cs = new CommandeService();
       date.setText(String.valueOf(commande.getDate_com()));
       prix.setText(String.valueOf(commande.getMontant_total()));
       status.setText(commande.getStatus());
        switch (commande.getStatus().toLowerCase()) {
            case "confirmee":
                status.getStyleClass().add("status-accepted");
                break;
            case "en attente":
                status.getStyleClass().add("status-pending");
                break;
            case "annulee":
                status.getStyleClass().add("status-cancelled");
                break;
            default:
                // Si le statut n'est pas reconnu, ne pas appliquer de style
                break;
        }
        detail.setOnMouseClicked(event -> {
            try {
                handleDetailClick(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });





    }

    private void handleDetailClick(MouseEvent event) throws Exception {
        // Récupérer les équipements achetés pour cette commande
        LigneCommandeService ligneCommandeService = new LigneCommandeService();
        List<Lignecommande> ligneCommandes = new ArrayList<>();
        System.out.println(ligneCommandeService.getAllByIDC(commande.getId_com()));
        for (Lignecommande l :ligneCommandeService.getAllByIDC(commande.getId_com())){
            ligneCommandes.add(l);

        }



        HisCon.showEquipementsAchetes(ligneCommandes);
    }

}
