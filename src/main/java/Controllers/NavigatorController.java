package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigatorController {

    @FXML
    private Button btnViewCamionRemorquage;
    @FXML
    private Button btnViewTestDrive;
    @FXML
    private Button btnViewRemorquage;
    @FXML
    private Button btnViewMecanicienRDV;

    @FXML
    private void goToViewCamionRemorquage() {
        loadInterface("/ViewCamionRemorquage.fxml");
    }
    @FXML
    private void goToViewTestDrive() {
        loadInterface("/ViewTestDrive.fxml");
    }
    @FXML
    private void goToViewRemorquage() {
        loadInterface("/ViewRemorquage.fxml");
    }
    @FXML
    private void goToViewMecanicienRDV() {
        loadInterface("/ViewMecanicienRDV.fxml");
    }

    private void loadInterface(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) btnViewCamionRemorquage.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading interface: " + fxmlPath);
        }
    }

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}