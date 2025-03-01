package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.CamionRemorquage;
import services.CamionRemorquageService;

import java.io.IOException;
import java.time.LocalDate;

public class AddCamionRemorquageController {

    @FXML
    private TextField tfNomAgence;

    @FXML
    private ComboBox<String> cbModele;

    @FXML
    private TextField tfAnnee;

    @FXML
    private TextField tfNumTel;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnCancel;

    @FXML
    private Text errorMessage; // For error messages

    @FXML
    private Text successMessage; // For success messages

    private CamionRemorquageService camionRemorquageService = new CamionRemorquageService();

    @FXML
    private void addCamionRemorquage() {
        errorMessage.setText("");
        successMessage.setText("");
        successMessage.setVisible(false);
        errorMessage.setVisible(false);

        // Vérification du nom de l'agence
        String nomAgence = tfNomAgence.getText();
        if (nomAgence.isEmpty()) {
            errorMessage.setText("Le nom de l'agence est requis !");
            errorMessage.setVisible(true);
            return;
        }

        // Vérification du modèle
        String modele = cbModele.getValue();
        if (modele == null) {
            errorMessage.setText("Le modèle est requis !");
            errorMessage.setVisible(true);
            return;
        }

        // Vérification de l'année
        String anneeText = tfAnnee.getText();
        if (anneeText.isEmpty()) {
            errorMessage.setText("L'année est requise !");
            errorMessage.setVisible(true);
            return;
        }

        int annee;
        try {
            annee = Integer.parseInt(anneeText);
        } catch (NumberFormatException e) {
            errorMessage.setText("Veuillez entrer une année valide !");
            errorMessage.setVisible(true);
            return;
        }

        int currentYear = LocalDate.now().getYear();
        if (annee < 1900 || annee > currentYear) {
            errorMessage.setText("Veuillez entrer une année valide :\n(1900 à " + currentYear + ") !");
            errorMessage.setVisible(true);
            return;
        }

        String numTel = tfNumTel.getText();
        if (numTel.isEmpty()) {
            errorMessage.setText("Le numéro de téléphone est requis !");
            errorMessage.setVisible(true);
            return;
        }
        if (!numTel.matches("\\d{8}")) {
            errorMessage.setText("Le numéro de téléphone doit contenir\nexactement 8 chiffres !");
            errorMessage.setVisible(true);
            return;
        }

        CamionRemorquage camion = new CamionRemorquage();
        camion.setNomAgence(nomAgence);
        camion.setModele(modele);
        camion.setAnnee(annee);
        camion.setNum_tel(numTel);
        camion.setStatut("Disponible"); // Valeur par défaut

        try {
            camionRemorquageService.create(camion);
            successMessage.setText("Camion Remorquage ajouté avec succès !");
            successMessage.setVisible(true);
            clearFields();
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage.setText("Erreur lors de l'ajout du Camion Remorquage.");
            errorMessage.setVisible(true);
        }
    }

    @FXML
    private void cancelAction() {
        clearFields(); // Efface les champs
        errorMessage.setText(""); // Efface les messages d'erreur
        successMessage.setText(""); // Efface les messages de succès
        successMessage.setVisible(false); // Cache le message de succès
        errorMessage.setVisible(false); // Cache le message d'erreur
    }

    private void clearFields() {
        tfNomAgence.clear();
        cbModele.setValue(null);
        tfAnnee.clear();
        tfNumTel.clear();
    }

    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewCamionRemorquage.fxml"));
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
}