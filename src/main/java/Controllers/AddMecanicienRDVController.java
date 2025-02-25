package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.ResMecanicien;
import models.User;
import javafx.scene.text.Text;
import services.ResMecanicienService;
import services.UserService;

import java.io.IOException;
import java.time.LocalDate;

public class AddMecanicienRDVController {

    @FXML
    private ComboBox<String> cbUser;
    @FXML
    private ComboBox<String> cbMec;
    @FXML
    private TextField adresseField;
    @FXML
    private TextArea noteField;
    @FXML
    private DatePicker dateField;

    @FXML
    private Button btnBack;

    @FXML
    private Text errorMessage;
    @FXML
    private Text successMessage;

    private ResMecanicienService resMecanicienService = new ResMecanicienService();
    private UserService userService = new UserService();

    @FXML
    public void initialize() {
        populateUserComboBox();
        populateMecComboBox();
        successMessage.setText("");
        errorMessage.setText("");
    }

    private void populateUserComboBox() {
        try {
            ObservableList<String> userNames = FXCollections.observableArrayList();
            for (User user : userService.getAllClients()) {
                userNames.add(user.getNom() + " " + user.getPrenom() + " (ID: " + user.getId() + ")");
            }
            cbUser.setItems(userNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateMecComboBox() {
        try {
            ObservableList<String> mecNames = FXCollections.observableArrayList();
            for (User mec : userService.getAllMechanics()) {
                mecNames.add(mec.getNom() + " " + mec.getPrenom() + " (ID: " + mec.getId() + ")");
            }
            cbMec.setItems(mecNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdd() {
        errorMessage.setText("");
        successMessage.setText("");

        try {
            if (cbUser.getValue() == null) {
                errorMessage.setText("Utilisateur est requis !");
                return;
            }
            int userId = extractIdFromComboBox(cbUser.getValue());

            if (cbMec.getValue() == null) {
                errorMessage.setText("Mécanicien est requis !");
                return;
            }
            int mecId = extractIdFromComboBox(cbMec.getValue());

            String adresse = adresseField.getText();
            if (adresse.isEmpty()) {
                errorMessage.setText("Adresse est requise !");
                return;
            }

            String note = noteField.getText();

            LocalDate date = dateField.getValue();
            if (date == null || !date.isAfter(LocalDate.now())) {
                errorMessage.setText("La date doit être supérieure à aujourd'hui !");
                return;
            }

            ResMecanicien resMecanicien = new ResMecanicien();
            resMecanicien.setId_u(userId);
            resMecanicien.setId_mec(mecId);
            resMecanicien.setAdresse(adresse);
            resMecanicien.setNote(note);
            resMecanicien.setDate(java.sql.Date.valueOf(date));

            resMecanicienService.create(resMecanicien);
            successMessage.setText("Rendez-vous ajouté avec succès !");
            clearFields();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de l'ajout : " + e.getMessage());
            alert.showAndWait();
        }
    }

    private int extractIdFromComboBox(String comboBoxValue) {
        String idString = comboBoxValue.substring(comboBoxValue.lastIndexOf("(") + 4, comboBoxValue.lastIndexOf(")"));
        return Integer.parseInt(idString.trim());
    }

    private void clearFields() {
        cbUser.setValue(null);
        cbMec.setValue(null);
        adresseField.clear();
        noteField.clear();
        dateField.setValue(null);
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewMecanicienRDV.fxml"));
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
}