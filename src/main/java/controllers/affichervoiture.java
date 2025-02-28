package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import models.Voiture;
import services.VoitureService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class affichervoiture {

    @FXML
    private VBox voitureContainer;

    @FXML
    private AnchorPane mainContainer; // Added this to reference the parent container

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
            Label disponibiliteLabel = createLabel(voiture.getDisponibilite(), 60);

            // Load image
            ImageView imageView = createImageView(voiture.getImage(), 100, 70);

            Button modifyButton = new Button("Modifier");
            styleButton(modifyButton, "blue");
            modifyButton.setOnAction(event -> handleModifyVoiture(voiture));

            Button deleteButton = new Button("Supprimer");
            styleButton(deleteButton, "red");
            deleteButton.setOnAction(event -> confirmDeleteVoiture(voiture));

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            voitureRow.getChildren().addAll(
                    marqueLabel, descriptionLabel, kilometrageLabel,
                    couleurLabel, prixLabel, imageView, disponibiliteLabel,
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

    private ImageView createImageView(String imagePath, double width, double height) {
        ImageView imageView = new ImageView();
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
            }
        }
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);
        return imageView;
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

    private void handleModifyVoiture(Voiture voiture) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifiervoiture.fxml"));
            AnchorPane modificationView = loader.load();

            // Get controller and pass the voiture object
            modifiervoiture modifierController = loader.getController();
            modifierController.setVoiture(voiture);

            // Replace the current content with modification view
            mainContainer.getChildren().setAll(modificationView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRefresh() {
        loadVoitures();
    }
}
