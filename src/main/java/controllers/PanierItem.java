package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import models.Equipement;
import models.EquipementAffichage;
import models.Panier;
import services.EquipementService;
import services.PanierService;
import services.StockService;

import java.io.IOException;


public class PanierItem {
    @FXML
    private ImageView image;

    @FXML
    private Text nom;
    @FXML
    private TextField input;
    @FXML
    private Button Add;

    @FXML
    private Button Sous;

    @FXML
    private Label quantite;



    @FXML
    private Label prix;
    private Panier panier;
    Equipement equip;
    private Paniers paniersCon;
    public void setListEquipementController(Paniers paniersCon) {
        this.paniersCon = paniersCon;
    }

    public void initData(Panier panier) throws Exception {
        this.panier = panier;
        EquipementService es = new EquipementService();
        StockService ss = new StockService();

        equip = es.getEquipementById(panier.getId_e());
        nom.setText(equip.getNom());
        prix.setText(String.valueOf(ss.getStockById(equip.getId()).getPrixvente()));
        quantite.setText(String.valueOf(ss.getStockById(equip.getId()).getQuantite()));
        image.setImage(new Image(equip.getImage()));

    }

    @FXML

    private void handleAddAction() throws Exception {
        try {
            int value = Integer.parseInt(input.getText());
            input.setText(String.valueOf(value + 1));
            panier.setQuantite(value +1);
        } catch (NumberFormatException e) {
            System.err.println("la valeur du champ n'est pas un entier valide.");
        }

        // PanierService es = new PanierService();


    }

    @FXML
    private void handleSousAction() throws Exception {
        try {
            int value = Integer.parseInt(input.getText());
            if (value > 0) {
                input.setText(String.valueOf(value - 1));
                panier.setQuantite(value - 1);
            }
        } catch (NumberFormatException e) {
            System.err.println("la valeur du champ n'est pas un entier valide.");
        }


    }
    @FXML
    private void handleDeleteAction() throws Exception {
        if (panier != null) {

            PanierService ps = new PanierService();
            ps.delete(panier.getId());


            if (paniersCon != null) {

                paniersCon.reloadpaniers();
            } else {
                System.out.println("paniersCon est null ");
            }
        } else {
            System.out.println("panier est null !");
        }
    }
}
