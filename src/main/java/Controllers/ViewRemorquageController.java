package controllers;

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
import models.ResRemorquage;
import services.ResRemorquageService;
import services.UserService;
import services.CamionRemorquageService;
import models.User;
import models.CamionRemorquage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewRemorquageController {

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

    @FXML
    public void initialize() {
        loadRemorquages();
    }

    private void loadRemorquages() {
        try {
            List<ResRemorquage> remorquageList = remorquageService.getAll();
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
            pointRamassageLabel.setPrefWidth(200.0);

            Label pointDepotLabel = new Label(remorquage.getPoint_depot());
            pointDepotLabel.setPrefWidth(200.0);

            // Retrieve User details
            User user = userService.getById(remorquage.getId_u());
            String userName = (user != null) ? user.getUsername() : "Unknown";
            Label userLabel = new Label(userName + " (ID: " + remorquage.getId_u() + ")");
            userLabel.setPrefWidth(200.0);

            // Retrieve Camion details
            CamionRemorquage camion = camionService.getById(remorquage.getId_cr());
            String camionName = (camion != null) ? camion.getNomAgence() : "Unknown";
            Label camionLabel = new Label(camionName + " (ID: " + remorquage.getId_cr() + ")");
            camionLabel.setPrefWidth(200.0);

            Label dateLabel = new Label(remorquage.getDate().toString());
            dateLabel.setPrefWidth(150.0);

            Label statusLabel = new Label(remorquage.getStatus());
            statusLabel.setPrefWidth(150.0);

            // Action buttons
            HBox actionButtons = new HBox(5);

            // Add confirmation and rejection buttons for "en_cours_de_traitement"
            if ("en_cours_de_traitement".equals(remorquage.getStatus())) {
                Button confirmButton = new Button("Confirmer");
                confirmButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                confirmButton.setOnAction(e -> {
                    try {
                        remorquage.setStatus("Confirmé");
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
                        remorquage.setStatus("Rejeté");
                        remorquageService.update(remorquage);
                        loadRemorquages();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

                actionButtons.getChildren().addAll(confirmButton, rejectButton);
            }

            // Add update and delete buttons for "Confirmé" or "Rejeté"
            if ("Confirmé".equals(remorquage.getStatus()) || "Rejeté".equals(remorquage.getStatus())) {
                Button updateButton = new Button("Modifier");
                updateButton.setOnAction(e -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateRemorquage.fxml"));
                        Parent root = loader.load();

                        UpdateCamionRemorquageController updateController = loader.getController();
                        updateController.setCamion(camion); // Passer le camion

                        Stage stage = (Stage) actionButtons.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                Button deleteButton = new Button("Supprimer");
                deleteButton.setOnAction(e -> {
                    try {
                        remorquageService.delete(remorquage.getId_rem());
                        loadRemorquages();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

                actionButtons.getChildren().addAll(updateButton, deleteButton);
            }

            hbox.getChildren().addAll(pointRamassageLabel, pointDepotLabel, userLabel, camionLabel, dateLabel, statusLabel, actionButtons);
            remorquage_container.getChildren().add(hbox);
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

    private void setupSearch() {
        search_id.textProperty().addListener((observable, oldValue, newValue) -> {
            filterRemorquages(newValue);
        });
    }

    private void filterRemorquages(String searchTerm) {
        try {
            List<ResRemorquage> remorquageList = remorquageService.getAll();
            List<ResRemorquage> filteredList = new ArrayList<>();

            for (ResRemorquage remorquage : remorquageList) {
                // Récupérer le camion pour obtenir le nom de l'agence
                CamionRemorquage camion = camionService.getById(remorquage.getId_cr());
                String nomAgence = (camion != null) ? camion.getNomAgence() : "";

                // Vérifier si le nom de l'agence contient le terme de recherche
                if (nomAgence.toLowerCase().contains(searchTerm.toLowerCase())) {
                    filteredList.add(remorquage);
                }
            }

            populateRemorquageContainer(filteredList); // Mettre à jour l'affichage avec la liste filtrée
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}