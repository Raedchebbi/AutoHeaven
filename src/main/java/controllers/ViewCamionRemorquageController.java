package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.CamionRemorquage;
import services.CamionRemorquageService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<CamionRemorquage> camionList;

    private dashboardController dashboardController;

    @FXML
    public void initialize() {
        loadCamions();
        setupSearch();
    }

    public void setDashboardController(dashboardController controller) {
        this.dashboardController = controller;
    }

    private void loadCamions() {
        try {
            camionList = camionService.getAll();
            populateCamionContainer(camionList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateCamionContainer(List<CamionRemorquage> camions) {
        camion_container.getChildren().clear();
        for (CamionRemorquage camion : camions) {
            HBox hbox = new HBox();
            hbox.setSpacing(20);

            Label nomAgenceLabel = new Label(camion.getNomAgence());
            nomAgenceLabel.setPrefWidth(150.0);
            Label modeleLabel = new Label(camion.getModele());
            modeleLabel.setPrefWidth(150.0);
            Label anneeLabel = new Label(String.valueOf(camion.getAnnee()));
            anneeLabel.setPrefWidth(100.0);
            Label numTelLabel = new Label(camion.getNum_tel());
            numTelLabel.setPrefWidth(120.0);
            Label statutLabel = new Label(camion.getStatut());
            statutLabel.setPrefWidth(100.0);

            Button updateButton = new Button("Modifier");
            updateButton.setOnAction(e -> openUpdateInterface(camion));
            Button deleteButton = new Button("Supprimer");
            deleteButton.setOnAction(e -> confirmDeleteCamion(camion));

            hbox.getChildren().addAll(nomAgenceLabel, modeleLabel, anneeLabel, numTelLabel, statutLabel, updateButton, deleteButton);
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

    private void confirmDeleteCamion(CamionRemorquage camion) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de Suppression");
        alert.setHeaderText("Vous êtes sur le point de supprimer un camion.");
        alert.setContentText("Êtes-vous sûr de vouloir continuer ?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                deleteCamion(camion);
            }
        });
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

    /*@FXML
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
    }*/

    @FXML
    private void goToAddCamion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddCamionRemorquage.fxml"));
            Parent addCamionRoot = loader.load();

            // Ajoutez l'interface d'ajout de camion dans le conteneur principal de votre tableau de bord
            // Par exemple, si vous avez un AnchorPane dans le dashboard pour cela, l'ajoutez ici
            AnchorPane mainContainer = (AnchorPane) dashboardController.getMainContainer(); // Assurez-vous d'avoir une méthode pour accéder au conteneur
            mainContainer.getChildren().clear(); // Effacer le contenu précédent
            mainContainer.getChildren().add(addCamionRoot); // Ajouter la nouvelle interface

            System.out.println("Ajout de l'interface d'ajout de camion.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de AddCamionRemorquage.fxml");
        }
    }

    private void setupSearch() {
        search_id.textProperty().addListener((observable, oldValue, newValue) -> {
            filterCamions(newValue);
        });
    }

    private void filterCamions(String query) {
        List<CamionRemorquage> filteredList = camionList.stream()
                .filter(camion -> camion.getNomAgence().toLowerCase().contains(query.toLowerCase()) ||
                        camion.getModele().toLowerCase().contains(query.toLowerCase()) ||
                        String.valueOf(camion.getAnnee()).contains(query) ||
                        camion.getNum_tel().contains(query) ||
                        camion.getStatut().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());

        populateCamionContainer(filteredList);
    }
}