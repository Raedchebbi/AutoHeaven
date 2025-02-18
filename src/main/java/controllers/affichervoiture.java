package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Voiture;
import services.VoitureService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class affichervoiture {

    @FXML
    private VBox voitureContainer;

    private VoitureService voitureService = new VoitureService();

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
            HBox voitureRow = new HBox(10);
            voitureRow.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1 0; -fx-padding: 5;");

            Label marqueLabel = new Label(voiture.getMarque());
            marqueLabel.setPrefWidth(100);

            Label prixLabel = new Label(String.valueOf(voiture.getPrix()));
            prixLabel.setPrefWidth(80);

            Label kilometrageLabel = new Label(String.valueOf(voiture.getKilometrage()));
            kilometrageLabel.setPrefWidth(100);

            Label couleurLabel = new Label(voiture.getCouleur());
            couleurLabel.setPrefWidth(80);

            // Modifier Button
            Button modifyButton = new Button("Modifier");
            modifyButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
            modifyButton.setPrefWidth(100);
           // modifyButton.setOnAction(event -> handleModifyVoiture(voiture));

            // Supprimer Button with confirmation dialog
            Button deleteButton = new Button("Supprimer");
            deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            deleteButton.setPrefWidth(100);
            deleteButton.setOnAction(event -> confirmDeleteVoiture(voiture));

            voitureRow.getChildren().addAll(
                    marqueLabel, new Separator(),
                    prixLabel, new Separator(),
                    kilometrageLabel, new Separator(),
                    couleurLabel, new Separator(),
                    modifyButton, new Separator(),
                    deleteButton
            );

            voitureContainer.getChildren().add(voitureRow);
        }
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
            voitureService.delete(voiture.getId_v());  // Make sure delete method is implemented in VoitureService
            loadVoitures(); // Reload the list of voitures after deletion
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //private void handleModifyVoiture(Voiture voiture) {
     //   FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifiervoiture.fxml")); // Make sure this FXML exists
      //  AnchorPane root = null;
      //  try {
       //     root = loader.load();
       // } catch (IOException e) {
        //    throw new RuntimeException(e);
       // }

       // ModifierVoiture modifierController = loader.getController();
       // modifierController.setVoiture(voiture);

       // Stage stage = new Stage();
       // stage.setTitle("Modifier la voiture");
       // stage.setScene(new Scene(root));
       // stage.show();
    //}

    @FXML
    private void handleRefresh() {
        loadVoitures(); // Refresh the voiture list when the button is clicked
    }
}
