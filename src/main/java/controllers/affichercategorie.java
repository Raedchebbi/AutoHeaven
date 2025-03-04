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
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import models.Categorie;
import services.CategorieService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class affichercategorie {

    @FXML
    private VBox categoriesContainer;

    @FXML
    private AnchorPane rootContainer;

    private CategorieService categorieService = new CategorieService();
    private dashboardController dashboardController;

    public void setDashboardController(dashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    @FXML
    public void initialize() {
        loadCategories();
    }

    private void loadCategories() {
        categoriesContainer.getChildren().clear();

        List<Categorie> categories;
        try {
            categories = categorieService.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (Categorie categorie : categories) {
            HBox categoryRow = new HBox(15); // Increased spacing for better visibility
            categoryRow.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1 0; -fx-padding: 10;");
            categoryRow.setPrefWidth(1000); // Increased HBox width
            categoryRow.setMaxWidth(Double.MAX_VALUE); // Allow it to expand


            Label id_cLabel = new Label (String.valueOf(categorie.getId_c()));
            id_cLabel.setPrefWidth(150);
            HBox.setHgrow(id_cLabel, Priority.ALWAYS);

            Label typeLabel = new Label(categorie.getType());
            typeLabel.setPrefWidth(150);
            HBox.setHgrow(typeLabel, Priority.ALWAYS);

            Label carburantLabel = new Label(categorie.getType_carburant());
            carburantLabel.setPrefWidth(150);
            HBox.setHgrow(carburantLabel, Priority.ALWAYS);

            Label utilisationLabel = new Label(categorie.getType_utilisation());
            utilisationLabel.setPrefWidth(150);
            HBox.setHgrow(utilisationLabel, Priority.ALWAYS);

            Label transmissionLabel = new Label(categorie.getTransmission());
            transmissionLabel.setPrefWidth(150);
            HBox.setHgrow(transmissionLabel, Priority.ALWAYS);

            Label portesLabel = new Label(String.valueOf(categorie.getNbr_porte()));
            portesLabel.setPrefWidth(100);
            HBox.setHgrow(portesLabel, Priority.ALWAYS);

            // Modify button
            Button modifyButton = new Button("Modifier");
            modifyButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
            modifyButton.setPrefWidth(120);
            modifyButton.setOnAction(event -> handleModifyCategorie(categorie));

            // Delete button
            Button deleteButton = new Button("Supprimer");
            deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            deleteButton.setPrefWidth(120);
            deleteButton.setOnAction(event -> confirmDeleteCategorie(categorie));

            // Add all elements to the row
            categoryRow.getChildren().addAll(
                    id_cLabel, new Separator(),
                    typeLabel, new Separator(),
                    carburantLabel, new Separator(),
                    utilisationLabel, new Separator(),
                    transmissionLabel, new Separator(),
                    portesLabel, new Separator(),
                    modifyButton, new Separator(),
                    deleteButton
            );

            categoriesContainer.getChildren().add(categoryRow);
        }
    }

    private void confirmDeleteCategorie(Categorie categorie) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment supprimer cette catégorie ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            handleDeleteCategorie(categorie);
        }
    }

    private void handleDeleteCategorie(Categorie categorie) {
        try {
            categorieService.delete(categorie.getId_c());
            loadCategories();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleModifyCategorie(Categorie categorie) {
        try {
            // Load the modification view for the category
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifiercategorie.fxml"));
            AnchorPane modificationView = loader.load();

            // Get the controller and pass the Categorie object to it
            modifiercategorie modifierController = loader.getController();
            modifierController.setCategorie(categorie);

            // Replace the current content with the modification view
            rootContainer.getChildren().setAll(modificationView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleRefresh() {
        loadCategories();
    }
}
