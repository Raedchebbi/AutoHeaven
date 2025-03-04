package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.avis;
import services.AvisService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListController {
    @FXML
    private ListView<avis> listViewAvis;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button stat;  // Assure-toi que le fx:id="stat" est bien présent dans le fichier FXML

    private AvisService avisService;
    private List<avis> avisList;

    public ListController() {
        avisService = new AvisService();
    }

    @FXML
    public void initialize() {
        // Vérifier si le bouton stat est initialisé correctement
        if (stat != null) {
            stat.setOnAction(event -> goToTrendChart(event));
        } else {
            System.out.println("Le bouton stat n'est pas initialisé !");
        }

        loadAvis();  // Charger la liste des avis

        // Configurer les cellules de la ListView
        listViewAvis.setCellFactory(param -> new ListCell<avis>() {
            @Override
            protected void updateItem(avis avis, boolean empty) {
                super.updateItem(avis, empty);
                if (empty || avis == null) {
                    setText(null);
                } else {
                    setText("Note: " + avis.getNote() + "\nCommentaire: " + avis.getCommentaire() + "\nDate: " + avis.getDateavis());
                    if (avis.getNote() < 3) {
                        setStyle("-fx-text-fill: red;");
                    } else {
                        setStyle("-fx-text-fill: green;");
                    }
                }
            }
        });
    }

    @FXML
    private void goToTrendChart(ActionEvent event) {
        try {
            // Charger la vue TrendChart.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TrendChart.fxml"));
            AnchorPane root = loader.load();

            // Récupérer le contrôleur de TrendChart
            TrendChart trendChartController = loader.getController();

            // Vérifier que la liste d'avis est bien récupérée
            if (avisList != null && !avisList.isEmpty()) {
                trendChartController.setChartData(avisList);
            } else {
                System.out.println("Aucun avis trouvé !");
            }

            // Afficher la nouvelle scène
            Stage stage = new Stage();
            stage.setTitle("Trend Chart");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAvis() {
        try {
            avisList = avisService.getAll();  // Utiliser List<avis> au lieu d'ObservableList
            listViewAvis.getItems().setAll(avisList);  // Ajouter les avis dans la ListView
            searchButton.setOnAction(event -> rechercherAvis());
            searchField.textProperty().addListener((observable, oldValue, newValue) -> rechercherAvis());
        } catch (SQLException e) {
            System.out.println("Erreur lors du chargement des avis: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void rechercherAvis() {
        String searchText = searchField.getText().toLowerCase();  // Récupérer le texte de la recherche et le convertir en minuscules
        List<avis> filteredList = new ArrayList<>();  // Liste temporaire pour filtrer

        // Si le champ de recherche est vide, afficher tous les avis
        if (searchText.isEmpty()) {
            filteredList = avisList;
        } else {
            // Sinon, filtrer les avis en fonction du texte de recherche
            for (avis avis : avisList) {
                // Vérifier si le commentaire ou la note contient le texte recherché (en minuscules)
                if (avis.getCommentaire().toLowerCase().contains(searchText) ||
                        String.valueOf(avis.getNote()).contains(searchText)) {
                    filteredList.add(avis);
                }
            }
        }

        // Mettre à jour la liste affichée dans la ListView
        listViewAvis.getItems().setAll(filteredList);
    }

    @FXML
    private void handleShowStats() {
        try {
            // Charger le fichier trend_chart.fxml depuis resources
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TrendChart.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle fenêtre (Stage) pour afficher les statistiques
            Stage stage = new Stage();
            stage.setTitle("Statistiques des Avis");
            stage.setScene(new Scene(root, 900, 700)); // Taille de la fenêtre
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement du graphique.");
        }
    }
}
