package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Detail_equipement.fxml"));
                Parent root = loader.load();


                DetailEquipement controller = loader.getController();
                controller.initData(equipement);


                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Erreur de la vue Detail_equipement.fxml", e);
            }
        });






    }


}
