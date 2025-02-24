package controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import services.EquipementService;
import services.PanierService;
import services.StockService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Paniers implements Initializable {

    @FXML
    private VBox contenu;
    @FXML
    private FontAwesomeIconView back;
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
        List<Panier> obs = ps.getAll(38);

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
            PanierService ps = new PanierService();
            EquipementService es = new EquipementService();
            StockService ss = new StockService();

            List<Panier> paniers = ps.getAll(38); // Récupère tous les paniers pour l'utilisateur avec l'id 3


            for (Panier panier : paniers) {
                int quantiteDisponible = ss.getStockById(panier.getId_e()).getQuantite();
                if (quantiteDisponible < panier.getQuantite()) {
                    showErrorPopup("Quantité insuffisante pour l'équipement: " + panier.getId_e());
                    return;
                }
            }


            cs.create(38);

            for (Panier panier : paniers) {
                es.updateQuantite(panier.getId_e(), panier.getQuantite());
            }

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
    @FXML
    private void handleBackAction() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListEquipementClient.fxml"));
        Parent root = loader.load();
        Scene currentScene = back.getScene();
        currentScene.setRoot(root);
    }
    private void showErrorPopup(String message) throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}

