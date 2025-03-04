package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.stage.StageStyle;

import java.io.IOException;

public class Mainfx extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Charger le fichier FXML avec le nom exact (ajusté selon votre fichier réel)
        Parent root = FXMLLoader.load(getClass().getResource("/dashboard.fxml"));
        primaryStage.setTitle("PiDev - Gestion des Réclamations (Admin)");

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        // Utiliser les dimensions du FXML pour cohérence
      //  primaryStage.setScene(new Scene(root, 1300, 800));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
