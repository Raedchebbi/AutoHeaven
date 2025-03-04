package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;
import models.Voiture;
import models.Categorie;
import services.CategorieService;
import services.VoitureService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class Listvoiture implements Initializable {
    @FXML private VBox chatbotContainer;


    @FXML
    private Button sortButton;

    @FXML
    private VBox voitureVBox;
    @FXML
    private TextField searchTextField;

    @FXML
    private Button filterButton;
    @FXML
    private ComboBox<String> typeComboBox, carburantComboBox,
            disponibiliteComboBox ,couleurComboBox;

    private ContextMenu categoryMenu = new ContextMenu();
    private Popup filterPopup = new Popup();
    @FXML
    private VBox filterPanel;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAllVoitures();
        loadCategoryMenu();
        setupFilterPopup();
        loadCategoryFilters();
        sortButton.setOnAction(event -> sortVoituresByMarque());

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

    private void loadCategoryMenu() {
        CategorieService categorieService = new CategorieService();
        MenuItem allItem = new MenuItem("Toutes");
        allItem.setOnAction(event -> loadAllVoitures());
        categoryMenu.getItems().add(allItem);

        try {
            categorieService.getAll().forEach(cat -> {
                MenuItem menuItem = new MenuItem(cat.getType());
                menuItem.setOnAction(event -> filterByCategory(cat.getType()));
                categoryMenu.getItems().add(menuItem);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void filterByCategory(String categoryType) {
        voitureVBox.getChildren().clear();
        VoitureService voitureService = new VoitureService();

        try {
            voitureService.getAll().stream()
                    .filter(voiture -> {
                        CategorieService categorieService = new CategorieService();
                        Categorie categorie = categorieService.getCategorieById(voiture.getId_c());
                        return categorie.getType().equals(categoryType);
                    })
                    .forEach(voiture -> voitureVBox.getChildren().add(createVoitureBox(voiture)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleSearch() {
        String query = searchTextField.getText().toLowerCase();

        voitureVBox.getChildren().clear();

        try {
            VoitureService voitureService = new VoitureService();

            voitureService.getAll().stream()
                    .filter(voiture -> voiture.getMarque().toLowerCase().contains(query))
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
        HBox.setHgrow(spacer, Priority.ALWAYS);

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

    private void setupFilterPopup() {
        Button applyButton = new Button("Appliquer");
        Button clearButton = new Button("Clear");
        applyButton.setOnAction(event -> applyFilters());
        clearButton.setOnAction(event -> clearFilters());
        HBox buttonContainer = new HBox(10, applyButton, clearButton);
        buttonContainer.setAlignment(Pos.CENTER);

        filterPanel = new VBox(10);
        filterPanel.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-width: 1px; -fx-padding: 15; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 4);");

        filterPanel.getChildren().addAll(
                new Label("Type"), typeComboBox,
                new Label("Carburant"), carburantComboBox,
                new Label("Couleur"), couleurComboBox,
                new Label("Disponibilité"), disponibiliteComboBox,
                buttonContainer
        );

        StackPane container = new StackPane(filterPanel);
        filterPopup.getContent().add(container);
        filterPopup.setAutoHide(true);
    }

    @FXML
    private void toggleFilterPanel() {
        if (filterPopup.isShowing()) {
            filterPopup.hide();
        } else {
            showFilterPopup();
        }
    }

    private void showFilterPopup() {
        filterPopup.show(filterButton.getScene().getWindow(),
                filterButton.localToScreen(0, 0).getX() - filterPanel.getPrefWidth(),
                filterButton.localToScreen(0, 0).getY() + filterButton.getHeight());
    }

    @FXML
    private void applyFilters() {
        System.out.println("Applying filters...");

        voitureVBox.getChildren().clear();

        try {
            VoitureService voitureService = new VoitureService();
            CategorieService categorieService = new CategorieService();

            voitureService.getAll().stream()
                    .filter(voiture -> {
                        Categorie categorie = categorieService.getCategorieById(voiture.getId_c());

                        boolean matchesType = typeComboBox.getValue() == null || typeComboBox.getValue().equals(categorie.getType());
                        boolean matchesCarburant = carburantComboBox.getValue() == null || carburantComboBox.getValue().equals(categorie.getType_carburant());
                        boolean matchesDisponibilite = disponibiliteComboBox.getValue() == null || disponibiliteComboBox.getValue().equals(voiture.getDisponibilite());
                        boolean matchesCouleur = couleurComboBox.getValue() == null || couleurComboBox.getValue().equals(voiture.getCouleur());
                        return matchesType && matchesCarburant && matchesDisponibilite && matchesCouleur;
                    })
                    .forEach(voiture -> {
                        voitureVBox.getChildren().add(createVoitureBox(voiture));
                    });

            System.out.println("Filter applied successfully! Cars displayed: " + voitureVBox.getChildren().size());

            // Close the filter popup after applying
            filterPopup.hide();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadCategoryFilters() {
        VoitureService voitureService = new VoitureService();
        CategorieService categorieService = new CategorieService();
        try {
            typeComboBox.getItems().clear();
            carburantComboBox.getItems().clear();
            disponibiliteComboBox.getItems().clear();
            couleurComboBox.getItems().clear();

            categorieService.getAll().forEach(categorie -> {
                if (!typeComboBox.getItems().contains(categorie.getType())) {
                    typeComboBox.getItems().add(categorie.getType());
                }
                if (!carburantComboBox.getItems().contains(categorie.getType_carburant())) {
                    carburantComboBox.getItems().add(categorie.getType_carburant());
                }
            });
            voitureService.getAll().forEach(voiture -> {
                if (!disponibiliteComboBox.getItems().contains(voiture.getDisponibilite())) {
                    disponibiliteComboBox.getItems().add(voiture.getDisponibilite());
                }
                if (!couleurComboBox.getItems().contains(voiture.getCouleur())) {
                    couleurComboBox.getItems().add(voiture.getCouleur());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void clearFilters() {
        typeComboBox.setValue(null);
        carburantComboBox.setValue(null);
        disponibiliteComboBox.setValue(null);
        couleurComboBox.setValue(null);
        loadAllVoitures();
    }

    @FXML
    private void sortVoituresByMarque() {
        voitureVBox.getChildren().clear();

        try {
            VoitureService voitureService = new VoitureService();
            voitureService.getAll().stream()
                    .sorted((v1, v2) -> v1.getMarque().compareToIgnoreCase(v2.getMarque())) // Sort alphabetically
                    .forEach(voiture -> voitureVBox.getChildren().add(createVoitureBox(voiture)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void openChatbot() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/chatbot.fxml"));
            Parent root = loader.load();

            Stage chatbotStage = new Stage();
            chatbotStage.setTitle("Car Chatbot");
            chatbotStage.setScene(new Scene(root, 400, 500));
            chatbotStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void toggleChatbot() {
        boolean isVisible = chatbotContainer.getOpacity() > 0;
        chatbotContainer.setOpacity(isVisible ? 0 : 1);
    }
}
