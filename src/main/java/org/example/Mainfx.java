package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Mainfx extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AfficherReclamationController.fxml")); // Charge l'interface admin
        primaryStage.setTitle("PiDev - Gestion des Réclamations (Admin)");
        primaryStage.setScene(new Scene(root, 1250, 750)); // Ajuste les dimensions pour AfficherReclamation.fxml
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}