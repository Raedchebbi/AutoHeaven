package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import models.Voiture;
import services.VoitureService;

import java.util.List;
import java.util.Optional;

public class affichervoiture {

    @FXML
    private VBox voitureContainer;

    private final VoitureService voitureService = new VoitureService();

    @FXML
    public void initialize() {
        loadVoitures();
    }

    private void loadVoitures() {
        voitureContainer.getChildren().clear();

        List<Voiture> voitures;
        try {
            voitures = voitureService.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (Voiture voiture : voitures) {
            HBox voitureRow = new HBox(15);
            voitureRow.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1 0; -fx-padding: 10;");
            voitureRow.setPrefHeight(50);

            Label marqueLabel = createLabel(voiture.getMarque(), 100);
            Label descriptionLabel = createLabel(voiture.getDescription(), 150);
            Label kilometrageLabel = createLabel(String.valueOf(voiture.getKilometrage()), 80);
            Label couleurLabel = createLabel(voiture.getCouleur(), 80);
            Label prixLabel = createLabel(String.valueOf(voiture.getPrix()), 80);
            Label imageLabel = createLabel(voiture.getImage(), 200);
            Label disponibiliteLabel = createLabel(voiture.getDisponibilite(), 60);

            // Buttons with fixed width
            Button modifyButton = new Button("Modifier");
            styleButton(modifyButton, "blue");

            Button deleteButton = new Button("Supprimer");
            styleButton(deleteButton, "red");
            deleteButton.setOnAction(event -> confirmDeleteVoiture(voiture));

            // Spacer to push buttons to the right
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            voitureRow.getChildren().addAll(
                    marqueLabel, descriptionLabel, kilometrageLabel,
                    couleurLabel, prixLabel, imageLabel, disponibiliteLabel,
                    spacer, modifyButton, deleteButton
            );

            voitureContainer.getChildren().add(voitureRow);
        }
    }

    private Label createLabel(String text, double width) {
        Label label = new Label(text);
        label.setPrefWidth(width);
        label.setWrapText(true);
        return label;
    }

    private void styleButton(Button button, String color) {
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white;");
        button.setPrefWidth(110);
        button.setMinWidth(110);
    }

    private void confirmDeleteVoiture(Voiture voiture) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment supprimer cette voiture ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            handleDeleteVoiture(voiture);
        }
    }

    private void handleDeleteVoiture(Voiture voiture) {
        try {
            voitureService.delete(voiture.getId_v());
            loadVoitures();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRefresh() {
        loadVoitures();
    }
}
