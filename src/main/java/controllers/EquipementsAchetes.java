package controllers;

import javafx.scene.control.Label;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import models.Lignecommande;

import java.util.List;

public class EquipementsAchetes {
    @FXML
    private VBox equipementsContainer;

    public void initData(List<Lignecommande> ligneCommandes) {
        for (Lignecommande ligneCommande : ligneCommandes) {
            // Créer un HBox pour chaque équipement
            HBox hbox = new HBox(10);
            hbox.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0;");

            // Ajouter le nom de l'équipement
            Label nomLabel = new Label("Équipement ID: " + ligneCommande.getId_e());
            nomLabel.setFont(new Font("Arial", 14));

            // Ajouter la quantité
            Label quantiteLabel = new Label("Quantité: " + ligneCommande.getQuantite());
            quantiteLabel.setFont(new Font("Arial", 14));

            // Ajouter le prix unitaire
            Label prixLabel = new Label("Prix: " + ligneCommande.getPrix_unitaire() + " DT");
            prixLabel.setFont(new Font("Arial", 14));

            // Ajouter les labels à l'HBox
            hbox.getChildren().addAll(nomLabel, quantiteLabel, prixLabel);

            // Ajouter l'HBox au VBox
            equipementsContainer.getChildren().add(hbox);
        }
    }
}
