package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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

public class AddTestDriveController {

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

    @FXML
    public void initialize() {
        loadUsers();
        loadVehicles();
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
    public void addTestDrive() {
        errorMessage.setText("");
        successMessage.setText("");

        String user = cbUser.getValue();
        if (user == null) {
            errorMessage.setText("Veuillez sélectionner un utilisateur.");
            return;
        }

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

            Voiture selectedVehicle = vehicleService.getById(vehicleId);
            if (selectedVehicle != null && selectedVehicle.getDisponibilite().equalsIgnoreCase("non")) {
                errorMessage.setText("La voiture n'est pas disponible.");
                return;
            }

            ResTestDrive testDrive = new ResTestDrive();
            testDrive.setId_u(userId);
            testDrive.setId_v(vehicleId);
            testDrive.setDate(date.toString());
            testDrive.setStatus("en_cours_de_traitement");

            testDriveService.create(testDrive);
            successMessage.setText("Réservation de test drive ajoutée avec succès !");
            successMessage.setVisible(true);

            clearFields();
        } catch (Exception e) {
            errorMessage.setText("Erreur lors de l'ajout : " + e.getMessage());
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
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de l'interface de navigation.");
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