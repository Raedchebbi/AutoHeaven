package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import models.Reclamation;
import services.ReclamationService;

import java.io.IOException;
import java.util.List;

public class Mainfx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamationController.fxml"));
            Parent root = loader.load();

            // Vérification du type de root
            if (!(root instanceof VBox)) {
                throw new IllegalStateException("Le fichier FXML doit contenir un VBox à la racine.");
            }

            VBox vbox = (VBox) root;

            // Création d'un ScrollPane pour afficher la liste des réclamations
            ScrollPane scrollPane = new ScrollPane();
            VBox contentBox = new VBox(10); // Ajout d'un VBox pour organiser les réclamations
            scrollPane.setContent(contentBox);
            scrollPane.setFitToWidth(true);

            // Récupération des réclamations avec les informations utilisateur
            ReclamationService service = new ReclamationService();
            List<Reclamation> reclamations = service.getAllWithUserDetails();

            // Ajouter dynamiquement les réclamations dans l'interface
            for (Reclamation reclamation : reclamations) {
                HBox hbox = new HBox(15);
                hbox.getChildren().addAll(
                        new Label(reclamation.getTitre()),
                        new Label(reclamation.getContenu()),
                        new Label(reclamation.getStatus()),
                        new Label(reclamation.getDateCreation().toString()),
                        new Label(reclamation.getNom()),
                        new Label(reclamation.getTel()),
                        new Label(reclamation.getEmail())
                );
                contentBox.getChildren().add(hbox);
            }

            // Ajouter le ScrollPane dans le VBox principal
            vbox.getChildren().add(scrollPane);

            // Création de la scène et affichage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Ajouter Réclamation");
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier FXML : " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Une erreur est survenue : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
