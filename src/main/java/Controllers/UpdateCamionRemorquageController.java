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

public class UpdateCamionRemorquageController {

    @FXML
    private TextField tfNomAgence;

    @FXML
    private ComboBox<String> cbModele;

    @FXML
    private TextField tfAnnee;

    @FXML
    private TextField tfNumTel;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnBack;

    @FXML
    private Text errorMessage; // Pour les messages d'erreur

    @FXML
    private Text successMessage; // Pour les messages de succès

    private CamionRemorquageService camionRemorquageService = new CamionRemorquageService();
    private CamionRemorquage camion;

    @FXML
    public void initialize() {
        // Initialiser les combobox et les champs
        cbModele.getItems().addAll("Standard", "Grande Taille");
    }

    public void setCamion(CamionRemorquage camion) {
        this.camion = camion;
        tfNomAgence.setText(camion.getNomAgence());
        cbModele.setValue(camion.getModele());
        tfAnnee.setText(String.valueOf(camion.getAnnee()));
        tfNumTel.setText(camion.getNum_tel());
    }

    @FXML
    private void updateCamionRemorquage() {
        errorMessage.setText("");
        successMessage.setText("");
        successMessage.setVisible(false);
        errorMessage.setVisible(false);

        // Vérification des champs
        String nomAgence = tfNomAgence.getText();
        if (nomAgence.isEmpty()) {
            errorMessage.setText("Le nom de l'agence est requis !");
            errorMessage.setVisible(true);
            return;
        }

        String modele = cbModele.getValue();
        if (modele == null) {
            errorMessage.setText("Le modèle est requis !");
            errorMessage.setVisible(true);
            return;
        }

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

        String numTel = tfNumTel.getText();
        if (numTel.isEmpty()) {
            errorMessage.setText("Le numéro de téléphone est requis !");
            errorMessage.setVisible(true);
            return;
        }
        if (!numTel.matches("\\d{8}")) {
            errorMessage.setText("Le numéro de téléphone doit contenir exactement 8 chiffres !");
            errorMessage.setVisible(true);
            return;
        }

        camion.setNomAgence(nomAgence);
        camion.setModele(modele);
        camion.setAnnee(annee);
        camion.setNum_tel(numTel);

        try {
            camionRemorquageService.update(camion);
            successMessage.setText("Camion Remorquage mis à jour avec succès !");
            successMessage.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage.setText("Erreur lors de la mise à jour du Camion Remorquage.");
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
            System.err.println("Erreur lors du chargement de ViewCamionRemorquage.fxml");
        }
    }
}