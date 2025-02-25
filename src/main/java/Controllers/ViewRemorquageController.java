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
import javafx.stage.Stage;
import models.ResRemorquage;
import services.ResRemorquageService;
import services.UserService; // Assuming you have a UserService to get user details
import services.CamionRemorquageService; // Assuming you have a VoitureService to get vehicle details
import models.User; // Assuming you have a User model
import models.CamionRemorquage; // Assuming you have a Voiture model

import java.io.IOException;
import java.sql.Date;
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
    private UserService userService = new UserService(); // Instantiate UserService
    private CamionRemorquageService camionService = new CamionRemorquageService();

    @FXML
    public void initialize() {
        loadRemorquages();
        setupSearch();
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
            User user = userService.getById(remorquage.getId_u()); // Get user by ID
            String userName = (user != null) ? user.getUsername() : "Unknown"; // Handle missing user
            Label userLabel = new Label(userName + " (ID: " + remorquage.getId_u() + ")");
            userLabel.setPrefWidth(200.0);

            // Retrieve Voiture details
            CamionRemorquage camion = camionService.getById(remorquage.getId_cr()); // Get vehicle by ID
            String agenceName = (camion != null) ? camion.getNomAgence() : "Unknown"; // Handle missing vehicle
            Label camionLabel = new Label(agenceName + " (ID: " + remorquage.getId_cr() + ")");
            camionLabel.setPrefWidth(200.0);

            Label dateLabel = new Label(remorquage.getDate().toString());
            dateLabel.setPrefWidth(150.0);

            hbox.getChildren().addAll(pointRamassageLabel, pointDepotLabel, userLabel, camionLabel, dateLabel);
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
            System.err.println("Erreur lors du chargement de Navigator.fxml");
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
            System.err.println("Erreur lors du chargement de AddRemorquage.fxml");
        }
    }

    private void setupSearch() {
        // Implement search functionality here, if needed.
    }
}