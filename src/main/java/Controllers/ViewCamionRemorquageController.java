package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.CamionRemorquage;
import services.CamionRemorquageService;

import java.io.IOException;
import java.util.List;

public class ViewCamionRemorquageController {

    @FXML
    private TextField search_id;

    @FXML
    private VBox camion_container;

    @FXML
    private Button add_btn;

    @FXML
    private Button back_btn;

    private CamionRemorquageService camionService = new CamionRemorquageService();

    @FXML
    public void initialize() {
        loadCamions();
        setupSearch();
    }

    private void loadCamions() {
        try {
            List<CamionRemorquage> camionList = camionService.getAll();
            populateCamionContainer(camionList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateCamionContainer(List<CamionRemorquage> camionList) {
        camion_container.getChildren().clear();
        for (CamionRemorquage camion : camionList) {
            HBox hbox = new HBox();
            hbox.setSpacing(20);

            Label nomAgenceLabel = new Label(camion.getNomAgence());
            nomAgenceLabel.setPrefWidth(180.0);

            Label modeleLabel = new Label(camion.getModele());
            modeleLabel.setPrefWidth(139.0);

            Label anneeLabel = new Label(String.valueOf(camion.getAnnee()));
            anneeLabel.setPrefWidth(98.0);

            Label numTelLabel = new Label(camion.getNum_tel());
            numTelLabel.setPrefWidth(154.0);

            Label statutLabel = new Label(camion.getStatut());
            statutLabel.setPrefWidth(180.0);

            Button updateButton = new Button("Modifier");
            updateButton.setOnAction(e -> openUpdateInterface(camion));
            Button deleteButton = new Button("Supprimer");
            deleteButton.setOnAction(e -> deleteCamion(camion));

            HBox actionButtons = new HBox(10, updateButton, deleteButton);

            hbox.getChildren().addAll(nomAgenceLabel, modeleLabel, anneeLabel, numTelLabel, statutLabel, actionButtons);
            camion_container.getChildren().add(hbox);
        }
    }

    private void openUpdateInterface(CamionRemorquage camion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateCamionRemorquage.fxml"));
            Parent root = loader.load();

            UpdateCamionRemorquageController updateController = loader.getController();

            updateController.setCamion(camion);

            Stage stage = (Stage) add_btn.getScene().getWindow(); // Récupérer la scène actuelle
            stage.setScene(new Scene(root));
            stage.show();

            System.out.println("Opening update interface for: " + camion.getNomAgence());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading UpdateCamionRemorquage.fxml");
        }
    }

    private void deleteCamion(CamionRemorquage camion) {
        try {
            camionService.delete(camion.getId_cr());
            loadCamions();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error deleting camion: " + camion.getNomAgence());
        }
    }

    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Navigator.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) back_btn.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de Navigator.fxml");
        }
    }

    @FXML
    private void goToAddCamion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddCamionRemorquage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) add_btn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de AddCamionRemorquage.fxml");
        }
    }

    private void setupSearch() {

    }
}