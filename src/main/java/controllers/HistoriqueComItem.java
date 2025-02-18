package controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import models.Commande;
import models.Panier;
import services.CommandeService;
import services.EquipementService;
import services.StockService;

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




    }



}
