package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import models.Voiture;
import models.VoitureRemorquage;
import services.VoitureRemorquageService;
import services.VoitureService;

import java.util.List;

public class ViewVoitureRemorquage {

    @FXML
    private VBox voituresContainer;
    @FXML
    private Button refreshButton;

    private VoitureRemorquageService voitureRemorquageService;

    @FXML
    public void initialize() {
        voitureRemorquageService = new VoitureRemorquageService();
        loadVoitures();
    }

    @FXML
    private void handleRefresh() {
        loadVoitures();
    }

    private void loadVoitures() {
        // Clear previous entries
        voituresContainer.getChildren().clear();

        try {
            List<VoitureRemorquage> voitures = voitureRemorquageService.getAll();
            for (VoitureRemorquage voiture : voitures) {
                HBox voitureEntry = new HBox(10);

                Label idLabel = new Label("ID: " + voiture.getId_vr());
                Label modeleLabel = new Label("Model: " + voiture.getModele());
                Label anneeLabel = new Label("Année: " + voiture.getAnnee());

                voitureEntry.getChildren().addAll(idLabel, modeleLabel, anneeLabel);

                voituresContainer.getChildren().add(voitureEntry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}