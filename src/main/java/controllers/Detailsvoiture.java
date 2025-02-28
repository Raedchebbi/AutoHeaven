package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import models.Categorie;
import models.Voiture;
import java.io.File;
import java.io.IOException;

public class Detailsvoiture {

    @FXML
    private Label typeLabel;
    @FXML
    private Label typeCarburantLabel;
    @FXML
    private Label typeUtilisationLabel;
    @FXML
    private Label nbrPorteLabel;
    @FXML
    private Label transmissionLabel;
    @FXML
    private ImageView imageView;

    public void setDetails(Categorie categorie, Voiture voiture) {
        typeLabel.setText("Type: " + categorie.getType());
        typeCarburantLabel.setText("Type Carburant: " + categorie.getType_carburant());
        typeUtilisationLabel.setText("Type Utilisation: " + categorie.getType_utilisation());
        nbrPorteLabel.setText("Nombre de Portes: " + categorie.getNbr_porte());
        transmissionLabel.setText("Transmission: " + categorie.getTransmission());

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
        Stage stage = (Stage) imageView.getScene().getWindow();
        stage.close();

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
