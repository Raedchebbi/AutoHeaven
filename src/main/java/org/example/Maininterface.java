package org.example;

import Controllers.ListController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Controllers.Sirine;  // Assure-toi d'importer le bon package pour Sirine

import javax.imageio.IIOParam;

public class Maininterface extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Chargement de l'interface FXML
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sirine.fxml"));
        //  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/list.fxml"));
        AnchorPane root = fxmlLoader.load();

        // Récupérer le contrôleur de l'interface
       Sirine sirineController = fxmlLoader.getController();

        //  ListController controller = fxmlLoader.getController();
        // Configuration de la scène
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Application JavaFX");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
