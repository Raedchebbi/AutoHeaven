package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import models.EquipementAffichage;

public class EquipItemClient {
    @FXML
    private AnchorPane equip_area;

    @FXML
    private ImageView image;

    @FXML
    private Label nom;

    @FXML
    private Label prix;
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



    }

}
