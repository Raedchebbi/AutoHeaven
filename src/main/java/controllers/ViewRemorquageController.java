package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.ResRemorquage;
import services.ResRemorquageService;
import services.UserService;
import services.CamionRemorquageService;
import models.User;
import models.CamionRemorquage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ViewRemorquageController {

    @FXML
    private ComboBox<String> searchCriteria; // Ajout du ComboBox

    @FXML
    private TextField search_id;

    @FXML
    private VBox remorquage_container;

    @FXML
    private Button add_btn;

    @FXML
    private Button back_btn;

    private ResRemorquageService remorquageService = new ResRemorquageService();
    private UserService userService = new UserService();
    private CamionRemorquageService camionService = new CamionRemorquageService();
    private List<ResRemorquage> remorquageList; // Liste originale pour filtrage

    private dashboardController dashboardController;

    @FXML
    public void initialize() {
        loadRemorquages();
        setupSearch(); // Configurer la recherche
    }

    public void setDashboardController(dashboardController controller) {
        this.dashboardController = controller;
    }

    private void loadRemorquages() {
        try {
            remorquageList = remorquageService.getAll(); // Stocke la liste originale
            populateRemorquageContainer(remorquageList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateRemorquageContainer(List<ResRemorquage> remorquageList) throws Exception {
        remorquage_container.getChildren().clear();
        for (ResRemorquage remorquage : remorquageList) {
            HBox hbox = new HBox();
            hbox.setSpacing(20);

            Label pointRamassageLabel = new Label(remorquage.getPoint_ramassage());
            pointRamassageLabel.setPrefWidth(120.0);

            Label pointDepotLabel = new Label(remorquage.getPoint_depot());
            pointDepotLabel.setPrefWidth(120.0);

            User user = userService.getById(remorquage.getId_u());
            String userName = (user != null) ? user.getUsername() : "Unknown";
            Label userLabel = new Label(userName + " (ID: " + remorquage.getId_u() + ")");
            userLabel.setPrefWidth(120.0);

            CamionRemorquage camion = camionService.getById(remorquage.getId_cr());
            String camionName = (camion != null) ? camion.getNomAgence() : "Unknown";
            Label camionLabel = new Label(camionName + " (ID: " + remorquage.getId_cr() + ")");
            camionLabel.setPrefWidth(150.0);

            Label dateLabel = new Label(remorquage.getDate().toString());
            dateLabel.setPrefWidth(100.0);

            Label statusLabel = new Label(remorquage.getStatus());
            statusLabel.setPrefWidth(100.0);

            HBox actionButtons = new HBox(5);

            // Confirmation and rejection buttons for "en_cours_de_traitement"
            if ("en_cours_de_traitement".equals(remorquage.getStatus())) {
                Button confirmButton = new Button("Confirmer");
                confirmButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                confirmButton.setOnAction(e -> {
                    try {
                        remorquage.setStatus("confirmee");
                        remorquageService.update(remorquage);
                        loadRemorquages();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

                Button rejectButton = new Button("Rejeter");
                rejectButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                rejectButton.setOnAction(e -> {
                    try {
                        remorquage.setStatus("rejetee");
                        remorquageService.update(remorquage);
                        loadRemorquages();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

                actionButtons.getChildren().addAll(confirmButton, rejectButton);
            }

            // Update and delete buttons for "confirmee" or "rejetee"
            if ("confirmee".equals(remorquage.getStatus()) || "rejetee".equals(remorquage.getStatus())) {
                Button updateButton = new Button("Modifier");
                updateButton.setOnAction(e -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateRemorquage.fxml"));
                        Parent root = loader.load();

                        UpdateRemorquageController updateController = loader.getController();
                        updateController.setRemorquage(remorquage);

                        Stage stage = (Stage) actionButtons.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                });

                Button deleteButton = new Button("Supprimer");
                deleteButton.setOnAction(e -> confirmDeleteRemorquage(remorquage));

                actionButtons.getChildren().addAll(updateButton, deleteButton);
            }

            hbox.getChildren().addAll(pointRamassageLabel, pointDepotLabel, userLabel, camionLabel, dateLabel, statusLabel, actionButtons);
            remorquage_container.getChildren().add(hbox);
        }
    }

    private void setupSearch() {
        search_id.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                filterRemorquages(newValue);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void filterRemorquages(String searchTerm) throws Exception {
        String selectedCriteria = searchCriteria.getValue(); // Récupérer la valeur sélectionnée
        List<ResRemorquage> filteredList = remorquageList.stream()
                .filter(remorquage -> {
                    switch (selectedCriteria) {
                        case "Point Ramassage":
                            return remorquage.getPoint_ramassage().toLowerCase().contains(searchTerm.toLowerCase());
                        case "Point Depot":
                            return remorquage.getPoint_depot().toLowerCase().contains(searchTerm.toLowerCase());
                        case "Utilisateur":
                            User user = null;
                            try {
                                user = userService.getById(remorquage.getId_u());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            return user != null && user.getUsername().toLowerCase().contains(searchTerm.toLowerCase());
                        case "Nom Agence":
                            CamionRemorquage camion = null;
                            try {
                                camion = camionService.getById(remorquage.getId_cr());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            return camion != null && camion.getNomAgence().toLowerCase().contains(searchTerm.toLowerCase());
                        default:
                            return false;
                    }
                })
                .collect(Collectors.toList());

        populateRemorquageContainer(filteredList);
    }

    private void confirmDeleteRemorquage(ResRemorquage remorquage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de Suppression");
        alert.setHeaderText("Vous êtes sur le point de supprimer un remorquage.");
        alert.setContentText("Êtes-vous sûr de vouloir continuer ?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                deleteRemorquage(remorquage);
            }
        });
    }

    private void deleteRemorquage(ResRemorquage remorquage) {
        try {
            remorquageService.delete(remorquage.getId_rem());
            loadRemorquages();
        } catch (Exception e) {
            e.printStackTrace();
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
        }
    }

    @FXML
    private void goToAddRemorquage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddRemorquage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) add_btn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displaySuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}