package controllers;

import javafx.scene.control.Label;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import models.Lignecommande;
import services.EquipementService;

import java.util.List;

public class EquipementsAchetes {
    @FXML
    private VBox equipementsContainer;
    EquipementService es = new EquipementService();

    public void initData(List<Lignecommande> ligneCommandes) throws Exception {
        for (Lignecommande ligneCommande : ligneCommandes) {

            HBox hbox = new HBox(10);
            hbox.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0;");



            Label nomLabel = new Label("Équipement: " +es.getEquipementById( ligneCommande.getId_e()).getNom());
            nomLabel.setFont(new Font("Arial", 14));


            Label quantiteLabel = new Label("Quantité: " + ligneCommande.getQuantite());
            quantiteLabel.setFont(new Font("Arial", 14));


            Label prixLabel = new Label("Prix: " + ligneCommande.getPrix_unitaire() + " DT");
            prixLabel.setFont(new Font("Arial", 14));


            hbox.getChildren().addAll(nomLabel, quantiteLabel, prixLabel);


            equipementsContainer.getChildren().add(hbox);
        }
    }
}
