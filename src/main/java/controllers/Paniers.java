package controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import models.*;
import services.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Paniers implements Initializable {

    @FXML
    private VBox contenu;
    @FXML
    private FontAwesomeIconView back;
    @FXML
    private WebView webView;
    @FXML
    private AnchorPane anc2;

    @FXML
    private AnchorPane anc;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StripeConfig.initialize();
        try {
            reloadpaniers();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void reloadpaniers() throws Exception {
        contenu.getChildren().clear();

        PanierService ps = new PanierService();
        List<Panier> obs = ps.getAll(5129);
        if (obs.size()==0) {
            anc2.setVisible(true);
        }
        else {

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
            }
        }}
    public void handleCommande() throws Exception {
        PanierService ps = new PanierService();
        EquipementService es = new EquipementService();
        StockService ss = new StockService();
        CommandeService cs = new CommandeService();
        List<Panier> paniers = ps.getAll(5129);
        Equipement e =new Equipement();

        if (paniers.isEmpty()) {
            showErrorPopup("Votre panier est vide.");
            return;
        }

        double total = 0;

        for (Panier panier : paniers) {
            int quantiteDisponible = ss.getStockById(panier.getId_e()).getQuantite();
            if (quantiteDisponible < panier.getQuantite()) {
                showErrorPopup("Stock insuffisant pour l'équipement ID: " + panier.getId_e());
                return;
            }
            e=es.getEquipementById(panier.getId_e());

            Stock stock = ss.getStockById(panier.getId_e());

            total += stock.getPrixvente() * panier.getQuantite();
        }

        for (Panier panier : paniers) {
            es.updateQuantite(panier.getId_e(), panier.getQuantite());
        }
        //cs.create(38);
        //int commandeId = cs.create(38);
        //Commande commande = cs.getCommande(commandeId);

        long montantCents = (long) (total * 100);
        String checkoutUrl = CheckoutService.createCheckoutSession(montantCents, "usd");

        showPaymentPage(checkoutUrl);
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

    public void showPaymentPage(String checkoutUrl) {
        anc.setVisible(true);
        webView.getEngine().load(checkoutUrl);
        //webView.setVisible(true);


        webView.getEngine().locationProperty().addListener((obs, oldUrl, newUrl) -> {
            if (newUrl.contains("pay/success")) {
                try {
                    CommandeService cs = new CommandeService();
                    cs.create(5129);
                    afficherAlerte("Paiement réussi", "Votre paiement a été traité avec succès.", Alert.AlertType.INFORMATION);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                webView.setVisible(false);
            } else if (newUrl.contains("pay/cancel")) {
                try {
                   // CommandeService cs = new CommandeService();
                  //  cs.deleteCommande(id);

                    afficherAlerte("Paiement annulé", "Votre paiement a été annulé.", Alert.AlertType.WARNING);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                webView.setVisible(false);
            }
        });
    }


    private void afficherAlerte(String titre, String message, Alert.AlertType type) throws IOException {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        redirectToPaniers();
    }


}

