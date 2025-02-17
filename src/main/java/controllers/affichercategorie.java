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
import models.Categorie;
import services.CategorieService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class affichercategorie {

    @FXML
    private VBox categoriesContainer;

    private CategorieService categorieService = new CategorieService();

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
            HBox categoryRow = new HBox(10);
            categoryRow.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1 0; -fx-padding: 5;");

            Label typeLabel = new Label(categorie.getType());
            typeLabel.setPrefWidth(80);

            Label carburantLabel = new Label(categorie.getType_carburant());
            carburantLabel.setPrefWidth(80);

            Label utilisationLabel = new Label(categorie.getType_utilisation());
            utilisationLabel.setPrefWidth(80);

            Label transmissionLabel = new Label(categorie.getTransmission());
            transmissionLabel.setPrefWidth(80);

            Label portesLabel = new Label(String.valueOf(categorie.getNbr_porte()));
            portesLabel.setPrefWidth(60);

            // Modifier Button
            Button modifyButton = new Button("Modifier");
            modifyButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
            modifyButton.setPrefWidth(100);
            modifyButton.setOnAction(event -> handleModifyCategorie(categorie));

            // Supprimer Button with confirmation dialog
            Button deleteButton = new Button("Supprimer");
            deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            deleteButton.setPrefWidth(100);
            deleteButton.setOnAction(event -> confirmDeleteCategorie(categorie));


            categoryRow.getChildren().addAll(
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifiercategorie.fxml"));
        AnchorPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        modifiercategorie modifierController = loader.getController();
        modifierController.setCategorie(categorie);

        Stage stage = new Stage();
        stage.setTitle("Modifier la catégorie");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleRefresh() {
        loadCategories();
    }
}
