package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AddTestDriveController {

    @FXML
    private ComboBox<String> cbUser;

    @FXML
    private ComboBox<String> cbVehicle;
    @FXML
    private DatePicker dpDate;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnBack;
    @FXML
    private Text errorMessage;
    @FXML
    private Text successMessage;

    private ResTestDriveService resTestDriveService = new ResTestDriveService();
    private UserService userService = new UserService();
    private VoitureService voitureService = new VoitureService();

    @FXML
    public void initialize() {
        populateUserComboBox();
        populateVehicleComboBox();
        successMessage.setText("");
    }

    private void populateUserComboBox() {
        try {
            List<User> users = userService.getAll();
            ObservableList<String> userNames = FXCollections.observableArrayList();
            for (User user : users) {
                userNames.add(user.getNom() + " " + user.getPrenom() + " (ID: " + user.getId() + ")");
            }
            cbUser.setItems(userNames);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading users: " + e.getMessage());
        }
    }

    private void populateVehicleComboBox() {
        try {
            List<Voiture> vehicles = voitureService.getAll();
            ObservableList<String> vehicleNames = FXCollections.observableArrayList();
            for (Voiture vehicle : vehicles) {
                vehicleNames.add(vehicle.getMarque() + " (Disponible : " + vehicle.getDisponibilite() + ")");
            }
            cbVehicle.setItems(vehicleNames);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading vehicles: " + e.getMessage());
        }
    }

    @FXML
    private void addTestDrive() {
        try {
            String selectedUser = cbUser.getValue();
            if (selectedUser == null) {
                errorMessage.setText("User est requis !");
                successMessage.setText("");
                return;
            }
            int userId = extractIdFromComboBox(selectedUser);

            String selectedVehicle = cbVehicle.getValue();
            if (selectedVehicle == null) {
                errorMessage.setText("Vehicle est requis !");
                successMessage.setText("");
                return;
            }
            int vehicleId = extractIdFromComboBox(selectedVehicle);

            Voiture selectedVoiture = voitureService.getById(vehicleId);
            if (selectedVoiture == null || !selectedVoiture.getDisponibilite().equalsIgnoreCase("oui")) {
                errorMessage.setText("La voiture doit être disponible !");
                successMessage.setText("");
                return;
            }

            LocalDate selectedDate = dpDate.getValue();
            if (selectedDate == null || !selectedDate.isAfter(LocalDate.now())) {
                errorMessage.setText("La date doit être supérieure à aujourd'hui !");
                successMessage.setText("");
                return;
            }
            String formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            String status = "en_cours_de_traitement";

            ResTestDrive resTestDrive = new ResTestDrive();
            resTestDrive.setId_u(userId);
            resTestDrive.setId_v(vehicleId);
            resTestDrive.setDate(formattedDate);
            resTestDrive.setStatus(status);

            resTestDriveService.create(resTestDrive);

            successMessage.setText("Réservation de l'essai routier ajoutée avec succès !");
            errorMessage.setText("");

            cbUser.setValue(null);
            cbVehicle.setValue(null);
            dpDate.setValue(null);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error adding Test Drive Reservation: " + e.getMessage());
        }
    }

    private int extractIdFromComboBox(String comboBoxValue) {
        String idString = comboBoxValue.substring(comboBoxValue.lastIndexOf("(") + 4, comboBoxValue.lastIndexOf(")"));
        return Integer.parseInt(idString.trim());
    }

    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewTestDrive.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading navigator interface.");
        }
    }

    @FXML
    private void cancelAction() {
        clearFields();
        errorMessage.setText("");
        successMessage.setText("");
    }

    private void clearFields() {
        cbUser.setValue(null);
        cbVehicle.setValue(null);
        dpDate.setValue(null);
    }
}