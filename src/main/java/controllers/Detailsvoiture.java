package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import models.Voiture;
import java.io.File;
import java.io.IOException;

public class Detailsvoiture {

    @FXML
    private Label marqueLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label prixLabel;
    @FXML
    private ImageView imageView;

    public void setVoitureDetails(Voiture voiture) {
        marqueLabel.setText(voiture.getMarque());
        descriptionLabel.setText(voiture.getDescription());
        prixLabel.setText(String.format("%,.2f", voiture.getPrix()) + " TND");

        File imageFile = new File(voiture.getImage());
        if (imageFile.exists()) {
            Image image = new Image(imageFile.toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(300);
            imageView.setFitHeight(200);
        }
    }

    @FXML
    private void handleBack() {
        // Go back to the previous scene (Listvoiture)
        Stage stage = (Stage) imageView.getScene().getWindow();
        stage.close();  // Close the current stage

        // Load the Listvoiture scene
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/listvoiture.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1200, 600);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
