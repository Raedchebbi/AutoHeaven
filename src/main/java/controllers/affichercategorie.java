package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import models.Categorie;
import services.CategorieService;
import java.util.List;

public class affichercategorie {

    @FXML
    private VBox categoriesContainer; // VBox to hold category entries

    private CategorieService categorieService = new CategorieService();

    @FXML
    public void initialize() {
        loadCategories();
    }

    private void loadCategories() {
        categoriesContainer.getChildren().clear(); // Clear old data

        List<Categorie> categories;
        try {
            categories = categorieService.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (Categorie categorie : categories) {
            HBox categoryRow = new HBox(10); // HBox for a single category (spacing: 10)
            categoryRow.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1 0; -fx-padding: 5;");

            Label typeLabel = new Label(categorie.getType());
            typeLabel.setPrefWidth(100);

            Label carburantLabel = new Label(categorie.getType_carburant());
            carburantLabel.setPrefWidth(100);

            Label utilisationLabel = new Label(categorie.getType_utilisation());
            utilisationLabel.setPrefWidth(100);

            Label transmissionLabel = new Label(categorie.getTransmission());
            transmissionLabel.setPrefWidth(100);

            Label portesLabel = new Label(String.valueOf(categorie.getNbr_porte()));
            portesLabel.setPrefWidth(100);

            // Add labels with separators between columns
            categoryRow.getChildren().addAll(
                    typeLabel, new Separator(),
                    carburantLabel, new Separator(),
                    utilisationLabel, new Separator(),
                    transmissionLabel, new Separator(),
                    portesLabel
            );

            categoriesContainer.getChildren().add(categoryRow); // Add row to VBox
        }
    }

    @FXML
    private void handleRefresh() {
        loadCategories();
    }
}
