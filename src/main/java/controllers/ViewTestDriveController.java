package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.ResTestDrive;
import models.User;
import models.Voiture;
import services.ResTestDriveService;
import services.UserService;
import services.VoitureService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ViewTestDriveController {

    @FXML
    private ComboBox<String> searchCriteria; // Ajout du ComboBox

    @FXML
    private TextField search_id;

    @FXML
    private VBox testdrive_container;

    @FXML
    private Button add_btn;

    @FXML
    private Button back_btn;

    @FXML
    private Text successMessage;

    private ResTestDriveService testDriveService = new ResTestDriveService();
    private UserService userService = new UserService();
    private VoitureService voitureService = new VoitureService();
    private List<ResTestDrive> testDriveList; // Liste originale pour filtrage

    @FXML
    public void initialize() {
        loadTestDrives();
        setupSearch(); // Configurer la recherche
        successMessage.setVisible(false);
    }

    private void loadTestDrives() {
        try {
            testDriveList = testDriveService.getAll(); // Stocke la liste originale
            populateTestDriveContainer(testDriveList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateTestDriveContainer(List<ResTestDrive> testDriveList) throws Exception {
        testdrive_container.getChildren().clear();
        for (ResTestDrive testDrive : testDriveList) {
            HBox hbox = new HBox();
            hbox.setSpacing(20);

            User user = userService.getById(testDrive.getId_u());
            String username = (user != null) ? user.getUsername() : "Unknown";
            Label userLabel = new Label(username + " (ID: " + testDrive.getId_u() + ")");
            userLabel.setPrefWidth(170.0);

            Voiture voiture = voitureService.getById(testDrive.getId_v());
            String marque = (voiture != null) ? voiture.getMarque() : "Unknown";
            Label vehicleLabel = new Label(marque + " (ID: " + testDrive.getId_v() + ")");
            vehicleLabel.setPrefWidth(170.0);

            Label dateLabel = new Label(testDrive.getDate());
            dateLabel.setPrefWidth(120.0);

            Label statusLabel = new Label(testDrive.getStatus());
            statusLabel.setPrefWidth(170.0);

            hbox.getChildren().addAll(userLabel, vehicleLabel, dateLabel, statusLabel);

            if (testDrive.getStatus().equals("en_cours_de_traitement")) {
                Button confirmButton = new Button("Confirmer");
                confirmButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                confirmButton.setOnAction(e -> {
                    try {
                        testDrive.setStatus("confirmee");
                        testDriveService.update(testDrive);
                        successMessage.setText("Test Drive confirmé avec succès !");
                        successMessage.setVisible(true);
                        loadTestDrives();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

                Button rejectButton = new Button("Rejeter");
                rejectButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                rejectButton.setOnAction(e -> {
                    try {
                        testDrive.setStatus("rejetee");
                        testDriveService.update(testDrive);
                        successMessage.setText("Test Drive rejeté avec succès !");
                        successMessage.setVisible(true);
                        loadTestDrives();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

                hbox.getChildren().addAll(confirmButton, rejectButton);
            }

            if (testDrive.getStatus().equals("confirmee") || testDrive.getStatus().equals("rejetee")) {
                Button updateButton = new Button("Modifier");
                updateButton.setOnAction(e -> openUpdateInterface(testDrive));

                Button deleteButton = new Button("Supprimer");
                deleteButton.setOnAction(e -> confirmDeleteTestDrive(testDrive));

                hbox.getChildren().addAll(updateButton, deleteButton);
            }
            testdrive_container.getChildren().add(hbox);
        }
    }

    private void setupSearch() {
        search_id.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                filterTestDrives(newValue);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void filterTestDrives(String searchTerm) throws Exception {
        String selectedCriteria = searchCriteria.getValue(); // Récupérer la valeur sélectionnée
        List<ResTestDrive> filteredList = testDriveList.stream()
                .filter(testDrive -> {
                    if (selectedCriteria != null) {
                        switch (selectedCriteria) {
                            case "Utilisateur":
                                User user = null;
                                try {
                                    user = userService.getById(testDrive.getId_u());
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                return (user != null && user.getUsername().toLowerCase().contains(searchTerm.toLowerCase()));
                            case "Véhicule":
                                Voiture voiture = null;
                                try {
                                    voiture = voitureService.getById(testDrive.getId_v());
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                return (voiture != null && voiture.getMarque().toLowerCase().contains(searchTerm.toLowerCase()));
                        }
                    }
                    return false; // Si aucun critère n'est sélectionné
                })
                .collect(Collectors.toList());

        populateTestDriveContainer(filteredList);
    }

    private void confirmDeleteTestDrive(ResTestDrive testDrive) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de Suppression");
        alert.setHeaderText("Vous êtes sur le point de supprimer un Test Drive.");
        alert.setContentText("Êtes-vous sûr de vouloir continuer ?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                deleteTestDrive(testDrive);
            }
        });
    }

    private void deleteTestDrive(ResTestDrive testDrive) {
        try {
            testDriveService.delete(testDrive.getId_td());
            successMessage.setText("Test Drive supprimé avec succès !");
            successMessage.setVisible(true);
            loadTestDrives();
        } catch (Exception ex) {
            ex.printStackTrace();
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
    private void goToAddTestDrive() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddTestDrive.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) add_btn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de AddTestDrive.fxml");
        }
    }

    private void openUpdateInterface(ResTestDrive testDrive) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateTestDrive.fxml"));
            Parent root = loader.load();

            UpdateTestDriveController updateController = loader.getController();
            updateController.setTestDrive(testDrive);

            Stage stage = (Stage) add_btn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

            System.out.println("Ouverture de l'interface de mise à jour pour le Test Drive ID: " + testDrive.getId_td());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de UpdateTestDrive.fxml");
        }
    }
}