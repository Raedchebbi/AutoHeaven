package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class mainFXvoiture extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            URL fxmlLocation = getClass().getResource("/navigate.fxml");

            if (fxmlLocation == null) {
                throw new IOException("FXML file '/addvoiture.fxml' not found. Check the path.");
            }

            Parent root = FXMLLoader.load(fxmlLocation);
            primaryStage.setTitle("Gestion Voitures");
            primaryStage.setScene(new Scene(root, 1200, 600));

            primaryStage.setResizable(true);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
