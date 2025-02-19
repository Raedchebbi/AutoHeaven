package controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class NavigateController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private void loadAddVoitureRemorque() {
        loadInterface("AddVoitureRemorquage.fxml");
    }

    @FXML
    private void loadAddReservation() {
        loadInterface("AddReservation.fxml");
    }

    @FXML
    private void loadViewVoitureRemorque() {
        loadInterface("ViewVoitureRemorquage.fxml");
    }

    @FXML
    private void loadViewReservation() {
        loadInterface("ViewReservation.fxml");
    }

    @FXML
    private void loadAddServiceRemorquage() {
        loadInterface("AddServiceRemorquage.fxml");
    }

    @FXML
    private void loadViewServiceRemorquage() {
        loadInterface("AfficherServiceRemorquage.fxml");
    }

    private void loadInterface(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/" + fxmlFile));
            Parent newInterface = loader.load();
            borderPane.setCenter(newInterface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}