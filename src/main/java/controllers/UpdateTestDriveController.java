package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.ResTestDrive;
import models.User;
import models.Voiture;
import services.ResTestDriveService;
import services.UserService;
import services.VoitureService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class UpdateTestDriveController {

    @FXML
    private ComboBox<String> cbUser;
    @FXML
    private ComboBox<String> cbVehicle;
    @FXML
    private DatePicker dpDate;
    @FXML
    private Text errorMessage;
    @FXML
    private Text successMessage;
    @FXML
    private Button btnBack;

    private ResTestDriveService testDriveService = new ResTestDriveService();
    private UserService userService = new UserService();
    private VoitureService vehicleService = new VoitureService();
    private ResTestDrive testDrive;

    @FXML
    public void initialize() {
        loadUsers();
        loadVehicles();
    }

    public void setTestDrive(ResTestDrive testDrive) {
        this.testDrive = testDrive;
        populateFields();
    }

    private void populateFields() {
        try {
            cbUser.setValue(testDrive.getId_u() + " - " + userService.getById(testDrive.getId_u()).getUsername());
        } catch (Exception e) {
            errorMessage.setText("Erreur lors du chargement de l'utilisateur : " + e.getMessage());
        }
        try {
            cbVehicle.setValue(testDrive.getId_v() + " - " + vehicleService.getById(testDrive.getId_v()).getMarque());
        } catch (Exception e) {
            errorMessage.setText("Erreur lors du chargement du véhicule : " + e.getMessage());
        }
        dpDate.setValue(LocalDate.parse(testDrive.getDate()));
    }

    private void loadUsers() {
        try {
            List<User> users = userService.getAll();
            for (User user : users) {
                cbUser.getItems().add(user.getId() + " - " + user.getUsername());
            }
        } catch (Exception e) {
            errorMessage.setText("Erreur lors du chargement des utilisateurs : " + e.getMessage());
        }
    }

    private void loadVehicles() {
        try {
            List<Voiture> vehicles = vehicleService.getAll();
            for (Voiture vehicle : vehicles) {
                cbVehicle.getItems().add(vehicle.getId_v() + " - " + vehicle.getMarque() + " - Disponible : " + vehicle.getDisponibilite());
            }
        } catch (Exception e) {
            errorMessage.setText("Erreur lors du chargement des véhicules : " + e.getMessage());
        }
    }

    @FXML
    public void updateTestDrive() {
        successMessage.setText("");
        errorMessage.setText("");
        successMessage.setVisible(false);

        // Vérification de l'utilisateur
        String user = cbUser.getValue();
        if (user == null) {
            errorMessage.setText("Veuillez sélectionner un utilisateur.");
            return;
        }

        // Vérification du véhicule
        String vehicle = cbVehicle.getValue();
        if (vehicle == null) {
            errorMessage.setText("Veuillez sélectionner un véhicule.");
            return;
        }

        LocalDate date = dpDate.getValue();
        if (date == null) {
            errorMessage.setText("Veuillez sélectionner une date de format : JJ/MM/AAAA.");
            return;
        }
        if (!date.isAfter(LocalDate.now())) {
            errorMessage.setText("La date doit être supérieure à aujourd'hui !");
            return;
        }

        try {
            int userId = Integer.parseInt(user.split(" - ")[0]);
            int vehicleId = Integer.parseInt(vehicle.split(" - ")[0]);

            testDrive.setId_u(userId);
            testDrive.setId_v(vehicleId);
            testDrive.setDate(date.toString());
            testDrive.setStatus("en_cours_de_traitement");

            testDriveService.update(testDrive);
            successMessage.setText("Test Drive mis à jour avec succès !");
            successMessage.setVisible(true);

        } catch (Exception e) {
            errorMessage.setText("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }

    @FXML
    public void cancelAction() {
        clearFields();
    }

    @FXML
    public void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewTestDrive.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de ViewTestDrive.fxml");
        }
    }

    private void clearFields() {
        cbUser.setValue(null);
        cbVehicle.setValue(null);
        dpDate.setValue(null);
        errorMessage.setText("");
        successMessage.setText("");
    }
}