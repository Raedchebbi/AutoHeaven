package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.EquipementAffichage;

import java.io.IOException;

public class EquipItemClient {
    @FXML
    private AnchorPane equip_area;

    @FXML
    private ImageView image;

    @FXML
    private Label nom;

    @FXML
    private Label prix;
    private ListEquipement listEquipementController;
    private profileController dashboardController;  // Référence au Dashboard1

    public void setDashboardController(profileController dashboardController) {
        System.out.println("Setting DashboardController in EquipItemClient: " + (dashboardController != null ? "not null" : "null"));
        this.dashboardController = dashboardController;
    }

    public void setListEquipementController(ListEquipement listEquipementController) {
        this.listEquipementController = listEquipementController;
    }
    EquipementAffichage equipement;
    public void initData(EquipementAffichage equipement) {
        this.equipement = equipement;
        //reference.setText(equipement.getReference());
        nom.setText(equipement.getNom());
        //  marque.setText(equipement.getMarque());
        prix.setText(String.valueOf(equipement.getPrixvente()));
        System.out.println(equipement.getImage());
        // quantite.setText(String.valueOf(equipement.getQuantite()));
        Image image_p = new Image(equipement.getImage());
        System.out.println(equipement.getImage());
        image.setImage(image_p);
        equip_area.setOnMouseClicked(event -> {
          /*  try {
                if (dashboardController != null) {
                    dashboardController.loadDetailsForm(equipement); // Utiliser la méthode de Dashboard1
                } else {
                    System.err.println("DashboardController is null in EquipItemClient!");
                }*/
            try {
                if (dashboardController != null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Detail_equipement.fxml"));
                    try {
                        Parent root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    DetailEquipement controller = loader.getController();
                    controller.initData(equipement);
                    controller.setDashboardController(dashboardController); // Passer dashboardController à DetailEquipement

                    dashboardController.loadDetailsForm(equipement); // Utiliser loadDetailsForm de Dashboard1
                } else {
                    System.err.println("DashboardController is null in EquipItemClient!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Erreur lors de l’affichage des détails de l’équipement", e);
            }
        });





    }


}
