package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.Voiture;
import services.VoitureService;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Listvoiture {

    @FXML
    private VBox voitureVBox;

    @FXML
    private TextField searchTextField;

    public void loadVoitures(List<Voiture> voitures) {
        voitureVBox.getChildren().clear();

        for (Voiture voiture : voitures) {
            HBox voitureBox = new HBox(10);
            voitureBox.setStyle("-fx-padding: 5; -fx-border-color: #ccc; -fx-border-width: 1px; -fx-background-color: #f9f9f9;");

            ImageView imageView = new ImageView();
            File imageFile = new File(voiture.getImage());
            if (imageFile.exists()) {
                imageView.setImage(new Image(imageFile.toURI().toString()));
                imageView.setFitWidth(100);
                imageView.setFitHeight(70);
            }

            voitureBox.getChildren().addAll(imageView,
                    new Label(voiture.getMarque()),
                    new Label(voiture.getDescription()),
                    new Label(String.format("%,.2f", voiture.getPrix()) + " €"));

            Button detailsButton = new Button("Details");
            detailsButton.setOnAction(event -> openDetailsVoiture(voiture));

            voitureBox.getChildren().add(detailsButton);

            voitureVBox.getChildren().add(voitureBox);
        }
    }

    @FXML
    private void handleSearch() {
        String query = searchTextField.getText().toLowerCase();

        voitureVBox.getChildren().clear();

        try {
            VoitureService voitureService = new VoitureService();

            voitureService.getAll().stream()
                    .filter(voiture -> voiture.getMarque().toLowerCase().contains(query) ||
                            voiture.getDescription().toLowerCase().contains(query))
                    .forEach(voiture -> {
                        HBox voitureBox = createVoitureBox(voiture);
                        voitureVBox.getChildren().add(voitureBox);
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HBox createVoitureBox(Voiture voiture) {
        HBox voitureBox = new HBox(10);
        voitureBox.setStyle("-fx-padding: 5; -fx-border-color: #ccc; -fx-border-width: 1px; -fx-background-color: #f9f9f9;");

        ImageView imageView = new ImageView();
        File imageFile = new File(voiture.getImage());
        if (imageFile.exists()) {
            imageView.setImage(new Image(imageFile.toURI().toString()));
            imageView.setFitWidth(100);
            imageView.setFitHeight(70);
        }

        voitureBox.getChildren().addAll(imageView,
                new Label(voiture.getMarque()),
                new Label(voiture.getDescription()),
                new Label(String.format("%,.2f", voiture.getPrix()) + " €"));

        Button detailsButton = new Button("Details");
        detailsButton.setOnAction(event -> openDetailsVoiture(voiture));
        voitureBox.getChildren().add(detailsButton);

        return voitureBox;
    }

    private void openDetailsVoiture(Voiture voiture) {
        try {
            System.out.println("Opening details for voiture: " + voiture.getMarque());  // Debug print

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsvoiture.fxml"));
            Parent root = loader.load();

            Detailsvoiture detailsController = loader.getController();
            detailsController.setVoitureDetails(voiture);

            Stage stage = (Stage) voitureVBox.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
