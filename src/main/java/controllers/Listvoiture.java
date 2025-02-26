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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.Voiture;
import models.Categorie ;
import services.CategorieService;
import services.VoitureService;
import java.io.File;
import java.io.IOException;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class Listvoiture implements Initializable {

    @FXML
    private VBox voitureVBox;

    @FXML
    private TextField searchTextField;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAllVoitures();
    }

    private void loadAllVoitures() {
        voitureVBox.getChildren().clear();

        try {
            VoitureService voitureService = new VoitureService();
            voitureService.getAll().forEach(voiture -> {
                HBox voitureBox = createVoitureBox(voiture);
                voitureVBox.getChildren().add(voitureBox);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String query = searchTextField.getText().toLowerCase();

        voitureVBox.getChildren().clear();

        try {
            VoitureService voitureService = new VoitureService();

            voitureService.getAll().stream()
                    .filter(voiture -> voiture.getMarque().toLowerCase().contains(query)) // Only filter by marque
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

        Label marqueLabel = new Label(voiture.getMarque());
        Label descriptionLabel = new Label(voiture.getDescription());
        Label prixLabel = new Label(String.format("%,.2f", voiture.getPrix()) + " TND");

        CategorieService categorieService = new CategorieService();
        Categorie categorie = categorieService.getCategorieById(voiture.getId_c());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS); // Allows the spacer to expand and push elements apart


        Button detailsButton = new Button("Details");
        detailsButton.setOnAction(event -> openDetails(categorie, voiture));

        voitureBox.getChildren().addAll(imageView, marqueLabel, descriptionLabel, prixLabel, spacer, detailsButton);

        return voitureBox;
    }

    private void openDetails(Categorie categorie, Voiture voiture) {
        try {
            System.out.println("Opening details for category: " + categorie.getType());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/detailsvoiture.fxml"));
            Parent root = loader.load();

            Detailsvoiture detailsController = loader.getController();
            detailsController.setDetails(categorie, voiture);

            Stage stage = (Stage) voitureVBox.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
