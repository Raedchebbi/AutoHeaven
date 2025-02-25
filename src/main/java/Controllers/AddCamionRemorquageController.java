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

        try {
            String nomAgence = tfNomAgence.getText();
            String modele = cbModele.getValue();
            String anneeText = tfAnnee.getText();
            String numTel = tfNumTel.getText();

            if (nomAgence.isEmpty() || modele == null || anneeText.isEmpty() || numTel.isEmpty()) {
                errorMessage.setText("Tous les champs doivent être remplis !");
                errorMessage.setVisible(true);
                return;
            }

            int annee = Integer.parseInt(anneeText);
            int currentYear = LocalDate.now().getYear();
            if (annee < 1900 || annee > currentYear) {
                errorMessage.setText("Veuillez entrer une année valide (1900 à " + currentYear + ") !");
                errorMessage.setVisible(true);
                return;
            }

            // Validate phone number
            if (!numTel.matches("\\d{8}")) {
                errorMessage.setText("Numéro de téléphone doit contenir exactement 8 chiffres !");
                errorMessage.setVisible(true);
                return;
            }

            // Create the camion object and add it
            CamionRemorquage camion = new CamionRemorquage();
            camion.setNomAgence(nomAgence);
            camion.setModele(modele);
            camion.setAnnee(annee);
            camion.setNum_tel(numTel);
            camion.setStatut("Disponible"); // Default value

            camionRemorquageService.create(camion);

            successMessage.setText("Camion Remorquage ajouté avec succès !");
            successMessage.setVisible(true);

            clearFields();

        } catch (NumberFormatException e) {
            errorMessage.setText("Veuillez entrer des valeurs numériques valides !");
            errorMessage.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage.setText("Erreur lors de l'ajout du Camion Remorquage.");
            errorMessage.setVisible(true);
        }
    }

    @FXML
    private void cancelAction() {
        clearFields(); // Clear the fields
        errorMessage.setText(""); // Clear error messages
        successMessage.setText(""); // Clear success messages
        successMessage.setVisible(false); // Hide success message
        errorMessage.setVisible(false); // Hide error message
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