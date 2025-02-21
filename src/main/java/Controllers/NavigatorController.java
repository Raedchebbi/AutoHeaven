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
    private Button btnCamionRemorquage;

    @FXML
    private Button btnResTestDrive;

    @FXML
    private Button btnResRemorquage;

    @FXML
    private Button btnResMecanicien;

    @FXML
    private void goToCamionRemorquage() {
        loadInterface("/views/camionRemorquage.fxml");
    }

    @FXML
    private void goToResTestDrive() {
        loadInterface("/views/resTestDrive.fxml");
    }

    @FXML
    private void goToResRemorquage() {
        loadInterface("/views/resRemorquage.fxml");
    }

    @FXML
    private void goToResMecanicien() {
        loadInterface("/views/resMecanicien.fxml");
    }

    private void loadInterface(String fxmlPath) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML
            Scene scene = new Scene(root);

            // Get the current stage (window)
            Stage stage = (Stage) btnCamionRemorquage.getScene().getWindow();

            // Set the new scene to the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading interface: " + fxmlPath);
        }
    }
}