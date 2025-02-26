package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.ResTestDrive;
import models.User; // Assuming you have a User model
import models.Voiture; // Assuming you have a Voiture model
import services.ResTestDriveService;
import services.UserService; // Assuming you have a UserService to get user details
import services.VoitureService; // Assuming you have a VoitureService to get vehicle details

import java.io.IOException;
import java.util.List;

public class ViewTestDriveController {

    @FXML
    private TextField search_id;

    @FXML
    private VBox testdrive_container;

    @FXML
    private Button add_btn;

    @FXML
    private Button back_btn;

    @FXML
    private Text successMessage; // Message de succès

    private ResTestDriveService testDriveService = new ResTestDriveService();
    private UserService userService = new UserService(); // Instantiate UserService
    private VoitureService voitureService = new VoitureService(); // Instantiate VoitureService

    @FXML
    public void initialize() {
        loadTestDrives();
        setupSearch();
        successMessage.setVisible(false); // Cache le message de succès au démarrage
    }

    private void loadTestDrives() {
        try {
            List<ResTestDrive> testDriveList = testDriveService.getAll();
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

            // Display User: username (ID: id_u)
            User user = userService.getById(testDrive.getId_u()); // Get user by ID
            String username = (user != null) ? user.getUsername() : "Unknown"; // Handle null case
            Label userLabel = new Label(username + " (ID: " + testDrive.getId_u() + ")");
            userLabel.setPrefWidth(170.0);

            // Display Véhicule: marque (ID: id_v)
            Voiture voiture = voitureService.getById(testDrive.getId_v()); // Get vehicle by ID
            String marque = (voiture != null) ? voiture.getMarque() : "Unknown"; // Handle null case
            Label vehicleLabel = new Label(marque + " (ID: " + testDrive.getId_v() + ")");
            vehicleLabel.setPrefWidth(170.0);

            Label dateLabel = new Label(testDrive.getDate());
            dateLabel.setPrefWidth(120.0);

            Label statusLabel = new Label(testDrive.getStatus());
            statusLabel.setPrefWidth(170.0);

            hbox.getChildren().addAll(userLabel, vehicleLabel, dateLabel, statusLabel);

            // Add buttons for status "en_cours_de_traitement"
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
                updateButton.setOnAction(e -> openUpdateInterface(testDrive)); // Appel de la méthode pour ouvrir l'interface de mise à jour

                Button deleteButton = new Button("Supprimer");
                deleteButton.setOnAction(e -> {
                    try {
                        testDriveService.delete(testDrive.getId_td());
                        successMessage.setText("Test Drive supprimé avec succès !");
                        successMessage.setVisible(true);
                        loadTestDrives();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

                hbox.getChildren().addAll(updateButton, deleteButton);
            }
            testdrive_container.getChildren().add(hbox);
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
            // Charger le fichier FXML pour l'interface de mise à jour
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateTestDrive.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de mise à jour
            UpdateTestDriveController updateController = loader.getController();

            // Passer le test drive à l'interface de mise à jour
            updateController.setTestDrive(testDrive);

            // Changer la scène pour afficher l'interface de mise à jour
            Stage stage = (Stage) add_btn.getScene().getWindow(); // Récupérer la scène actuelle
            stage.setScene(new Scene(root));
            stage.show();

            System.out.println("Ouverture de l'interface de mise à jour pour le Test Drive ID: " + testDrive.getId_td());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de UpdateTestDrive.fxml");
        }
    }

    private void setupSearch() {
        // Implement search functionality here, if needed.
    }
}