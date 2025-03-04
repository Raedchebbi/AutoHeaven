package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.avis;
import controllers.TrendChart;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger le fichier FXML pour le graphique
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TrendChart.fxml"));
            BorderPane root = loader.load();

            // Récupérer le contrôleur pour configurer les données
            TrendChart trendChartController = loader.getController();

            // Créer une liste d'exemples d'avis pour différentes voitures
            List<avis> avisList = new ArrayList<>();
            avisList.add(new avis(5, "Très bon véhicule !", new java.sql.Date(1234567890000L), 1, 1));
            avisList.add(new avis(3, "Bon véhicule mais un peu cher.", new java.sql.Date(1234577890000L), 2, 2));
            avisList.add(new avis(4, "Confort et performance au top.", new java.sql.Date(1234587890000L), 3, 3));
            avisList.add(new avis(2, "Je m'attendais à mieux.", new java.sql.Date(1234597890000L), 4, 4));

            // Convertir la liste en ObservableList
            ObservableList<avis> avisObservableList = FXCollections.observableArrayList(avisList);

            // Appeler setChartData() pour ajouter les données au graphique
            trendChartController.setChartData(avisObservableList);

            // Ajouter le graphique au BorderPane de la scène
            root.setCenter(trendChartController.getLineChart());

            // Créer la scène et l'afficher
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setTitle("Analyse des Tendances des Avis par Voiture");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

