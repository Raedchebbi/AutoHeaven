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
import models.ResMecanicien;
import models.User;
import services.ResMecanicienService;
import services.UserService;

import java.io.IOException;
import java.util.List;

public class ViewMecanicienRDVController {

    @FXML
    private TextField search_id;

    @FXML
    private VBox mecanicien_container;

    @FXML
    private Button add_btn;

    @FXML
    private Button back_btn;

    private ResMecanicienService mecanicienService = new ResMecanicienService();
    private UserService userService = new UserService();

    @FXML
    public void initialize() {
        loadMecanicienRDVs();
        setupSearch();
    }

    private void loadMecanicienRDVs() {
        try {
            List<ResMecanicien> mecanicienList = mecanicienService.getAll();
            populateMecanicienContainer(mecanicienList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateMecanicienContainer(List<ResMecanicien> mecanicienList) throws Exception {
        mecanicien_container.getChildren().clear();
        for (ResMecanicien rdv : mecanicienList) {
            HBox hbox = new HBox();
            hbox.setSpacing(20);

            Label adresseLabel = new Label(rdv.getAdresse());
            adresseLabel.setPrefWidth(180.0);

            Label noteLabel = new Label(rdv.getNote());
            noteLabel.setPrefWidth(180.0);

            // Retrieve User details
            User user = userService.getById(rdv.getId_u()); // Get user by ID
            String userName = (user != null) ? user.getUsername() : "Unknown"; // Handle null case
            Label userLabel = new Label(userName + " (ID: " + rdv.getId_u() + ")");
            userLabel.setPrefWidth(150.0);

            // Retrieve Mécanicien details (using UserService)
            User mecanicien = userService.getById(rdv.getId_mec()); // Get mechanic by ID
            String mecanicienName = (mecanicien != null) ? mecanicien.getUsername() : "Unknown"; // Handle null case
            Label mecanicienLabel = new Label(mecanicienName + " (ID: " + rdv.getId_mec() + ")");
            mecanicienLabel.setPrefWidth(150.0);

            Label dateLabel = new Label(rdv.getDate().toString());
            dateLabel.setPrefWidth(150.0);

            hbox.getChildren().addAll(adresseLabel, noteLabel, userLabel, mecanicienLabel, dateLabel);
            mecanicien_container.getChildren().add(hbox);
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
    private void goToAddMecanicienRDV() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddMecanicienRDV.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) add_btn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de AddMecanicienRDV.fxml");
        }
    }

    private void setupSearch() {
        // Implement search functionality here, if needed.
    }
}