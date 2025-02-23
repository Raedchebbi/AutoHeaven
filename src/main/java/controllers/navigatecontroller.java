package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class navigatecontroller {

    @FXML
    private BorderPane borderPane;


    @FXML
    private void loadAddVoiture() {
        loadView("/addvoiture.fxml");
    }

    @FXML
    private void loadAddCategorie() {
        loadView("/addcategorie.fxml");
    }

    @FXML
    private void loadAfficherVoiture() {
        loadView("/affichervoiturei.fxml");
    }

    @FXML
    private void loadAfficherCategorie() {
        loadView("/affichercategorie.fxml");
    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();
            borderPane.setCenter(view);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la vue : " + fxmlPath);
            e.printStackTrace();
        }
    }
}