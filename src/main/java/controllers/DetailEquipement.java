package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import models.EquipementAffichage;
import models.Panier;
import services.EquipementService;
import services.PanierService;

import java.io.IOException;


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
        input.setText(String.valueOf(1));




    }
    @FXML

    private void handleAddAction() throws Exception {try {
        int value = Integer.parseInt(input.getText());
        input.setText(String.valueOf(value + 1));
    } catch (NumberFormatException e) {
        System.err.println("Erreur : la valeur du champ n'est pas un entier valide.");
    }

       // PanierService es = new PanierService();



    }
    @FXML
    private void handleSousAction() throws Exception {try {
        int value = Integer.parseInt(input.getText());
        if (value > 0) {
        input.setText(String.valueOf(value -1));}
    } catch (NumberFormatException e) {
        System.err.println("Erreur : la valeur du champ n'est pas un entier valide.");
    }}
    @FXML
    private void handleAddToCart() throws Exception {
        int id =equipement.getId();
        int idu =3;
        PanierService ps = new PanierService();
        Panier panier = new Panier(Integer.parseInt(input.getText()),idu,id);
        ps.create(panier);
        showSuccessPopup();
        redirectToEquipDetail();

    }
    private void showSuccessPopup() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("Votre produit a été ajouté avec succès au panier!");
        alert.showAndWait();
       // redirectToEquipDetail();
    }

    private void redirectToEquipDetail() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Detail_equipement.fxml"));
        Parent root = loader.load();
        Scene scene = cart.getScene();
        scene.setRoot(root);
    }


}
