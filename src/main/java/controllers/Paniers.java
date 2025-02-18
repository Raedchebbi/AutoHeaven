package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import models.EquipementAffichage;
import models.Panier;
import services.CommandeService;
import services.PanierService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Paniers implements Initializable {

    @FXML
    private VBox contenu;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            reloadpaniers();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void reloadpaniers() throws Exception {
        contenu.getChildren().clear();

        PanierService ps = new PanierService();
        List<Panier> obs = ps.getAll(3);

        for (Panier e : obs) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/PanierItem.fxml"));
                Parent item = loader.load();
                PanierItem ie = loader.getController();
                ie.initData(e);
                ie.setListEquipementController(this);

                contenu.getChildren().add(item);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }}
        public void handleCommande() throws Exception {

            CommandeService cs = new CommandeService();
            cs.create(3);
            showSuccessPopup();
            redirectToPaniers();

        }
        private void showSuccessPopup() throws IOException {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Votre commande est passée attendez la confirmation de admin!");
            alert.showAndWait();
            // redirectToEquipDetail();
        }

        private void redirectToPaniers() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Paniers.fxml"));
            Parent root = loader.load();
            Scene scene = contenu.getScene();
            scene.setRoot(root);
        }

    }

