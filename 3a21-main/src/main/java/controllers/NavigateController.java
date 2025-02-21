package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class NavigateController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private void loadAddVoitureRemorque() {
        loadInterface("/AddVoitureRemorquage.fxml");
    }

    @FXML
    private void loadAddReservation() {
        loadInterface("/AddReservation.fxml");
    }

    @FXML
    private void loadViewVoitureRemorque() {
        loadInterface("/ViewVoitureRemorquage.fxml");
    }

    @FXML
    private void loadViewReservation() {
        loadInterface("/ViewReservation.fxml");
    }

    @FXML
    private void loadAddServiceRemorquage() {
        loadInterface("/AddServiceRemorquage.fxml");
    }

    @FXML
    private void loadViewServiceRemorquage() {
        loadInterface("/AfficherServiceRemorquage.fxml");
    }

    private void loadInterface(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newInterface = loader.load();
            borderPane.getScene().setRoot(newInterface);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}