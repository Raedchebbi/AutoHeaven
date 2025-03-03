package controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import models.Commande;
import models.Lignecommande;
import models.Panier;
import services.CommandeService;
import services.PanierService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HistoriqueCommande implements Initializable {

    @FXML
    private VBox area;

    @FXML
    private FontAwesomeIconView back;

    @FXML
    private WebView webView;
    private profileController dashboardController;

    Commande commande;
    public void setDashboardController(profileController dashboardController) {
        this.dashboardController = dashboardController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialisation de HistoriqueCommande");
        try {
            reloadcom();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void reloadcom() throws Exception {
        area.getChildren().clear();

        CommandeService ps = new CommandeService();
        List<Commande> obs = ps.getAllByidU(loginuserController.loggedInUserID);
        System.out.println("Nombre de commandes chargées : " + obs.size());

        for (Commande e : obs) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/historiqueComItem.fxml"));
                Parent item = loader.load();
                HistoriqueComItem ie = loader.getController();
                ie.initData(e);
                ie.setListCommandeController(this);

                area.getChildren().add(item);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void showEquipementsAchetes(List<Lignecommande> ligneCommandes) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EquipementsAchetes.fxml"));
        Parent root = loader.load();

        EquipementsAchetes controller = loader.getController();
        controller.initData(ligneCommandes);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Équipements Achetés");
        stage.show();
    }



    public void showPaymentPage(String checkoutUrl) {
        webView.getEngine().load(checkoutUrl);
        webView.setVisible(true);


        webView.getEngine().locationProperty().addListener((obs, oldUrl, newUrl) -> {
            if (newUrl.contains("pay/success")) {
                afficherAlerte("Paiement réussi", "Votre paiement a été traité avec succès.", Alert.AlertType.INFORMATION);
                webView.setVisible(false);
            } else if (newUrl.contains("pay/cancel")) {
                afficherAlerte("Paiement annulé", "Votre paiement a été annulé.", Alert.AlertType.WARNING);
                webView.setVisible(false);
            }
        });
    }


    private void afficherAlerte(String titre, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleBackAction() throws IOException {
       /* FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListEquipementClient.fxml"));
        Parent root = loader.load();
        Scene currentScene = back.getScene();
        currentScene.setRoot(root);*/
        if (dashboardController != null) {
            dashboardController.loadListEquipementClientForm();  // Appeler une méthode dans Dashboard1 pour charger ListEquipementClient.fxml
        }
    }
}