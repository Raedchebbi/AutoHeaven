package controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import models.Commande;
import models.Lignecommande;
import netscape.javascript.JSObject;
import services.CheckoutService;
import services.CommandeService;
import services.LigneCommandeService;
import services.StripeConfig;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HistoriqueComItem {
    @FXML
    private Label date;

    @FXML
    private FontAwesomeIconView detail;

    @FXML
    private Label prix;

    @FXML
    private Label status;


    @FXML
    private WebView webView;
    private Commande commande;
    private HistoriqueCommande HisCon;

    public void setListCommandeController(HistoriqueCommande HisCon) {
        this.HisCon = HisCon;
    }

    public void initData(Commande commande) throws Exception {
        this.commande = commande;
        CommandeService cs = new CommandeService();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        date.setText(commande.getDate_com().format(formatter));
        prix.setText(String.valueOf(commande.getMontant_total()));
        status.setText(commande.getStatus());

        switch (commande.getStatus().toLowerCase()) {
            case "traitée":
                status.getStyleClass().add("status-accepted");

                break;
            case "en attente":
                status.getStyleClass().add("status-pending");
                break;

            default:
                break;
        }

        detail.setOnMouseClicked(event -> {
            try {
                handleDetailClick(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });




    }

    private void handleDetailClick(MouseEvent event) throws Exception {

        LigneCommandeService ligneCommandeService = new LigneCommandeService();
        List<Lignecommande> ligneCommandes = new ArrayList<>();
        System.out.println(ligneCommandeService.getAllByIDC(commande.getId_com()));
        for (Lignecommande l : ligneCommandeService.getAllByIDC(commande.getId_com())) {
            ligneCommandes.add(l);
        }

        HisCon.showEquipementsAchetes(ligneCommandes);
    }



    private void afficherAlerte(String titre, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                webView.getEngine().load("");
            }
        });
    }



}