package controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
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
import models.Equipement;
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
    @FXML
    private FontAwesomeIconView back;
    private profileController dashboardController;  // Référence au Dashboard1

    public void setDashboardController(profileController dashboardController) {
        this.dashboardController = dashboardController;
    }

    EquipementAffichage equipement;
    public void initData(EquipementAffichage equipement) {
        this.equipement = equipement;
        int initial =1;
        nom.setText(equipement.getNom());
        prix.setText(String.valueOf(equipement.getPrixvente()));
        ref.setText(equipement.getReference());
        marque.setText(equipement.getMarque());
        image.setImage(new Image(equipement.getImage()));
        disponibilite.setText(equipement.getQuantite() > 0 ? "In Stock" : "Out of Stock");
        desc.setText(equipement.getDescription());
        input.setText(String.valueOf(initial));




    }
    @FXML

    private void handleAddAction() throws Exception {try {
        int value = Integer.parseInt(input.getText());
        input.setText(String.valueOf(value + 1));
    } catch (NumberFormatException e) {
        System.err.println("la valeur du champ n'est pas un entier valide.");
    }

        // PanierService es = new PanierService();



    }
    @FXML
    private void handleSousAction() throws Exception {try {
        int value = Integer.parseInt(input.getText());
        if (value > 1) {
            input.setText(String.valueOf(value -1));}
    } catch (NumberFormatException e) {
        System.err.println("la valeur du champ n'est pas un entier valide.");
    }}
    @FXML
    private void handleAddToCart() throws Exception {
        int id =equipement.getId();
        int idu =loginuserController.loggedInUserID;
        PanierService ps = new PanierService();

        Panier panier = new Panier(Integer.parseInt(input.getText()),idu,id);
        if (Integer.parseInt(input.getText()) <= equipement.getQuantite())

            ps.create(panier);
        showSuccessPopup();

        //  redirectToEquipDetail(equipement);

    }
    private void showSuccessPopup() throws IOException {
        if (Integer.parseInt(input.getText()) > equipement.getQuantite()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Stock insuffisant !");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("Votre produit a été ajouté avec succès au panier!");
        alert.showAndWait();
        redirectToEquipDetail(equipement ,cart.getScene());
    }

    /* private void redirectToEquipDetail(EquipementAffichage equipement, Scene currentScene) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/Detail_equipement.fxml"));
         Parent root = loader.load();
         DetailEquipement controller = loader.getController();
         controller.initData(equipement);


         currentScene.setRoot(root);
     }*/
    private void redirectToEquipDetail(EquipementAffichage equipement, Scene currentScene) throws IOException {
        System.out.println("Redirecting to EquipDetail. DashboardController: " + (dashboardController != null ? "not null" : "null"));
        if (dashboardController != null) {
            dashboardController.loadDetailsForm(equipement);
        } else {
            System.err.println("DashboardController is null in DetailEquipement!");
            // Gestion de secours (optionnel) : charger Detail_equipement.fxml sans Dashboard1
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/controllers/Detail_equipement.fxml"));
            Parent root = loader.load();
            DetailEquipement controller = loader.getController();
            controller.initData(equipement);
            currentScene.setRoot(root);
        }
    }

    /* @FXML
     private void handleBackAction() throws IOException {

         FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListEquipementClient.fxml"));
         Parent root = loader.load();
         Scene currentScene = back.getScene();
         currentScene.setRoot(root);
     }*/
    @FXML
    private void handleBackAction() throws IOException {
        if (dashboardController != null) {
            dashboardController.loadListEquipementClientForm(); // Recharger ListEquipementClient dans pan_form
            dashboardController.switchForm(new ActionEvent(dashboardController.getPan(), null)); // Assurez-vous que pan_form est visible
        } else {
            System.err.println("DashboardController is null in DetailEquipement!");
            // Gestion de secours si dashboardController est null (optionnel)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/controllers/ListEquipementClient.fxml"));
            Parent root = loader.load();
            Scene currentScene = back.getScene();
            currentScene.setRoot(root);
        }
    }



}

