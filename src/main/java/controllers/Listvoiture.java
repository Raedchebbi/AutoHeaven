package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    @FXML
    private Label kilometrageLabel, prixLabel;


    @FXML
    private VBox voitureVBox;
    @FXML
    private TextField searchTextField;
    @FXML
    private ImageView filterImageView;
    @FXML
    private Button filterButton;
    @FXML
    private ComboBox<String> typeComboBox, carburantComboBox, utilisationComboBox, nbrPorteComboBox,
            transmissionComboBox, disponibiliteComboBox, couleurComboBox;
    @FXML
    private Slider kilometrageSlider, prixSlider;
    private ContextMenu categoryMenu = new ContextMenu();
    private Popup filterPopup = new Popup();
    @FXML
    private VBox filterPanel;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAllVoitures();
        loadCategoryMenu();
        setupFilterPopup();
        loadCategoryFilters();
        setupSliders();
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
        clearButton.setOnAction(event -> clearFilters());
        filterPanel = new VBox(10);
        filterPanel.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-width: 1px; -fx-padding: 15; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 4);");

        kilometrageSlider = new Slider(0, 300000, 150000);
        kilometrageSlider.setShowTickMarks(true);
        kilometrageSlider.setShowTickLabels(true);

        prixSlider = new Slider(0, 100000, 50000);
        prixSlider.setShowTickMarks(true);
        prixSlider.setShowTickLabels(true);

        filterPanel.getChildren().addAll(
                new Label("Type"), typeComboBox,
                new Label("Carburant"), carburantComboBox,
                new Label("Utilisation"), utilisationComboBox,
                new Label("Nombre de portes"), nbrPorteComboBox,
                new Label("Transmission"), transmissionComboBox,
                new Label("Disponibilité"), disponibiliteComboBox,
                new Label("Couleur"), couleurComboBox,
                new Label("Kilométrage"), kilometrageSlider,
                new Label("Prix"), prixSlider,
                applyButton,
                clearButton
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
        System.out.println("Apply Filters Button Clicked!");

        voitureVBox.getChildren().clear();

        try {
            VoitureService voitureService = new VoitureService();
            voitureService.getAll().stream()
                    .filter(voiture -> {
                        CategorieService categorieService = new CategorieService();
                        Categorie categorie = categorieService.getCategorieById(voiture.getId_c());

                        boolean matchesType = typeComboBox.getValue() == null || typeComboBox.getValue().equals(categorie.getType());
                        boolean matchesCarburant = carburantComboBox.getValue() == null || carburantComboBox.getValue().equals(categorie.getType_carburant());
                        boolean matchesUtilisation = utilisationComboBox.getValue() == null || utilisationComboBox.getValue().equals(categorie.getType_utilisation());
                        boolean matchesTransmission = transmissionComboBox.getValue() == null || transmissionComboBox.getValue().equals(categorie.getTransmission());
                        boolean matchesNbrPorte = nbrPorteComboBox.getValue() == null || nbrPorteComboBox.getValue().equals(categorie.getNbr_porte());
                        boolean matchesDisponibilite = disponibiliteComboBox.getValue() == null || disponibiliteComboBox.getValue().equals(voiture.getDisponibilite());
                        boolean matchesCouleur = couleurComboBox.getValue() == null || couleurComboBox.getValue().equals(voiture.getCouleur());
                        boolean matchesKilometrage = voiture.getKilometrage() <= kilometrageSlider.getValue();
                        boolean matchesPrix = voiture.getPrix() <= prixSlider.getValue();

                        return matchesType && matchesCarburant && matchesUtilisation && matchesTransmission && matchesNbrPorte && matchesDisponibilite &&  matchesKilometrage && matchesCouleur && matchesPrix;
                    })
                    .forEach(voiture -> voitureVBox.getChildren().add(createVoitureBox(voiture)));


            filterPanel.setVisible(false);
            filterPanel.setManaged(false);


            voitureVBox.layout();
            System.out.println("Filter Applied and UI Updated!");

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
            utilisationComboBox.getItems().clear();
            transmissionComboBox.getItems().clear();
            nbrPorteComboBox.getItems().clear();
            disponibiliteComboBox.getItems().clear();
            couleurComboBox.getItems().clear();
            kilometrageSlider.setValue(kilometrageSlider.getMin());
            prixSlider.setValue(prixSlider.getMin());

            categorieService.getAll().forEach(categorie -> {
                if (!typeComboBox.getItems().contains(categorie.getType())) {
                    typeComboBox.getItems().add(categorie.getType());
                }
                if (!carburantComboBox.getItems().contains(categorie.getType_carburant())) {
                    carburantComboBox.getItems().add(categorie.getType_carburant());
                }
                if (!utilisationComboBox.getItems().contains(categorie.getType_utilisation())) {
                    utilisationComboBox.getItems().add(categorie.getType_utilisation());
                }
                if (!transmissionComboBox.getItems().contains(categorie.getTransmission())) {
                    transmissionComboBox.getItems().add(categorie.getTransmission());
                }
                if (!nbrPorteComboBox.getItems().contains(categorie.getNbr_porte())) {
                    nbrPorteComboBox.getItems().add(String.valueOf(categorie.getNbr_porte()));
                }
            });
            voitureService.getAll().forEach(voiture -> {
                if (!disponibiliteComboBox.getItems().contains(voiture.getDisponibilite())) {
                    disponibiliteComboBox.getItems().add(String.valueOf(voiture.getDisponibilite()));
                }
                if (!couleurComboBox.getItems().contains(voiture.getCouleur())) {
                    couleurComboBox.getItems().add(String.valueOf(voiture.getCouleur()));
                }
            });

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    private void clearFilters() {

        typeComboBox.setValue(null);
        carburantComboBox.setValue(null);
        utilisationComboBox.setValue(null);
        transmissionComboBox.setValue(null);
        nbrPorteComboBox.setValue(null);
        disponibiliteComboBox.setValue(null);
        couleurComboBox.setValue(null);


        kilometrageSlider.setValue(kilometrageSlider.getMin());
        prixSlider.setValue(prixSlider.getMin());
        loadAllVoitures();
    }
    private void setupSliders() {

        kilometrageSlider.setMin(0);
        kilometrageSlider.setMax(500000);
        kilometrageSlider.setValue(250000);
        kilometrageSlider.setShowTickLabels(true);
        kilometrageSlider.setShowTickMarks(true);
        kilometrageSlider.setMajorTickUnit(100000);
        kilometrageSlider.setMinorTickCount(0);
        kilometrageSlider.setSnapToTicks(true);
        kilometrageSlider.setBlockIncrement(10000);

        kilometrageLabel.setText(String.format("%,d km", (int) kilometrageSlider.getValue()));
        kilometrageSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            kilometrageLabel.setText(String.format("%,d km", newVal.intValue()));
        });


        prixSlider.setMin(0);
        prixSlider.setMax(200000);
        prixSlider.setValue(100000);
        prixSlider.setShowTickLabels(true);
        prixSlider.setShowTickMarks(true);
        prixSlider.setMajorTickUnit(50000);
        prixSlider.setMinorTickCount(0);
        prixSlider.setSnapToTicks(true);
        prixSlider.setBlockIncrement(5000);

        prixLabel.setText(String.format("%,d TND", (int) prixSlider.getValue()));
        prixSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            prixLabel.setText(String.format("%,d TND", newVal.intValue()));
        });
    }

}
