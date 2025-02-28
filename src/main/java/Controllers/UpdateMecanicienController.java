package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.ResMecanicien;
import models.User;
import services.ResMecanicienService;
import services.UserService;

import java.io.IOException;
import java.time.LocalDate;

public class UpdateMecanicienController {

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
    private ResMecanicien resMecanicien;

    @FXML
    public void initialize() {
        populateUserComboBox();
        populateMecComboBox();
        successMessage.setText("");
        errorMessage.setText("");
    }

    public void setResMecanicien(ResMecanicien rdv) throws Exception {
        this.resMecanicien = rdv;
        populateFields();
    }

    private void populateFields() throws Exception {
        cbUser.setValue(resMecanicien.getId_u() + " - " + userService.getById(resMecanicien.getId_u()).getUsername());
        cbMec.setValue(resMecanicien.getId_mec() + " - " + userService.getById(resMecanicien.getId_mec()).getUsername());
        adresseField.setText(resMecanicien.getAdresse());
        noteField.setText(resMecanicien.getNote());
        dateField.setValue(LocalDate.parse(resMecanicien.getDate().toString()));
    }

    private void populateUserComboBox() {
        try {
            ObservableList<String> userNames = FXCollections.observableArrayList();
            for (User user : userService.getAllClients()) {
                userNames.add(user.getNom() + " " + user.getPrenom() + " (ID: " + user.getId() + ")");
            }
            cbUser.setItems(userNames);
        } catch (Exception e) {
            errorMessage.setText("Erreur lors du chargement des utilisateurs : " + e.getMessage());
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
            errorMessage.setText("Erreur lors du chargement des mécaniciens : " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdate() {
        errorMessage.setText("");
        successMessage.setText("");

        try {
            // Vérification des champs obligatoires
            if (cbUser.getValue() == null) {
                errorMessage.setText("Utilisateur est requis !");
                return;
            }
            if (cbMec.getValue() == null) {
                errorMessage.setText("Mécanicien est requis !");
                return;
            }
            if (adresseField.getText().isEmpty()) {
                errorMessage.setText("Adresse est requise !");
                return;
            }
            if (dateField.getValue() == null) {
                errorMessage.setText("Veuillez sélectionner une date  de format : JJ/MM/AAAA.");
                return;
            }

            int userId = Integer.parseInt(cbUser.getValue().split(" - ")[0]);
            int mecId = Integer.parseInt(cbMec.getValue().split(" - ")[0]);
            String adresse = adresseField.getText();
            String note = noteField.getText();
            LocalDate date = dateField.getValue();

            // Vérification si la date est supérieure à aujourd'hui
            if (!date.isAfter(LocalDate.now())) {
                errorMessage.setText("La date doit être supérieure à aujourd'hui !");
                return;
            }

            resMecanicien.setId_u(userId);
            resMecanicien.setId_mec(mecId);
            resMecanicien.setAdresse(adresse);
            resMecanicien.setNote(note);
            resMecanicien.setDate(java.sql.Date.valueOf(date));
            resMecanicien.setStatus("en_cours_de_traitement");

            resMecanicienService.update(resMecanicien);
            successMessage.setText("Rendez-vous mis à jour avec succès !");
        } catch (Exception e) {
            errorMessage.setText("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }

    @FXML
    private void cancelAction() {
        clearFields();
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewMecanicienRDV.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de ViewMecanicienRDV.fxml");
        }
    }

    private void clearFields() {
        cbUser.setValue(null);
        cbMec.setValue(null);
        adresseField.clear();
        noteField.clear();
        dateField.setValue(null);
        errorMessage.setText("");
        successMessage.setText("");
    }
}