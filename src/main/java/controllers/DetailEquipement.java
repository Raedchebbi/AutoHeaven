package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import models.EquipementAffichage;


public class DetailEquipement {
    @FXML
    private Button add;

    @FXML
    private Button cart;

    @FXML
    private Text desc;

    @FXML
    private Label disponibilite;

    @FXML
    private ImageView image;

    @FXML
    private TextField input;

    @FXML
    private Label marque;

    @FXML
    private Label nom;

    @FXML
    private Label prix;

    @FXML
    private Label ref;

    @FXML
    private Button sous;

    EquipementAffichage equipement;
    public void initData(EquipementAffichage equipement) {
        this.equipement = equipement;
        nom.setText(equipement.getNom());
        prix.setText(String.valueOf(equipement.getPrixvente()));
        ref.setText(equipement.getReference());
        marque.setText(equipement.getMarque());
        image.setImage(new Image(equipement.getImage()));
        disponibilite.setText(equipement.getQuantite() > 0 ? "In Stock" : "Out of Stock");
        desc.setText(equipement.getDescription());



    }
}
