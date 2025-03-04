package controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import models.avis;
import java.util.List;

public class TrendChart {

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private CategoryAxis xAxis; // Utilisation correcte de CategoryAxis pour les dates en String

    @FXML
    private NumberAxis yAxis;

    public void setChartData(List<avis> avisList) {
        System.out.println("setChartData a été appelée avec " + (avisList != null ? avisList.size() : 0) + " avis.");

        if (avisList == null || avisList.isEmpty()) {
            System.out.println("Erreur: Aucune donnée à afficher");
            return;
        }

        // Configurer les axes
        yAxis.setLabel("Note");
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(5);
        yAxis.setTickUnit(1);

        xAxis.setLabel("Date"); // Assurer un bon label

        // Création de la série pour le graphique
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Avis des clients");

        // Ajout des données au graphique
        for (avis a : avisList) {
            if (a.getDateavis() != null) {
                String dateStr = a.getDateavis().toString(); // Convertir la date en String
                System.out.println("Ajout de la note " + a.getNote() + " pour la date " + dateStr);
                series.getData().add(new XYChart.Data<>(dateStr, a.getNote()));
            } else {
                System.out.println("Erreur: Avis avec date null détecté !");
            }
        }

        // Mise à jour du graphique
        lineChart.getData().clear();
        lineChart.getData().add(series);
        System.out.println("Données ajoutées au graphique. Taille: " + series.getData().size());
    }

    public Node getLineChart() {
        return lineChart;
    }
}
