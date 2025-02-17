package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class voitureController {

    @FXML
    private TextField marqueField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField kilometrageField;

    @FXML
    private TextField couleurField;

    @FXML
    private TextField prixField;

    @FXML
    private TextField imageField;

    @FXML
    private CheckBox disponibleCheck;

    @FXML
    private CheckBox nonDisponibleCheck;

    @FXML
    private Button ajouterButton;

    @FXML
    private void ajouterVoiture() {
        String marque = marqueField.getText();
        String description = descriptionField.getText();
        String kilometrage = kilometrageField.getText();
        String couleur = couleurField.getText();
        String prix = prixField.getText();
        String image = imageField.getText();
        boolean disponible = disponibleCheck.isSelected();


        if (marque.isEmpty() || description.isEmpty() || kilometrage.isEmpty() || couleur.isEmpty() || prix.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs obligatoires.");
            return;
        }


        System.out.println("Voiture ajoutée : " + marque + ", " + description + ", " + kilometrage + ", " + couleur + ", " + prix + ", " + (disponible ? "Disponible" : "Non disponible"));

        showAlert("Succès", "Voiture ajoutée avec succès !");


        marqueField.clear();
        descriptionField.clear();
        kilometrageField.clear();
        couleurField.clear();
        prixField.clear();
        imageField.clear();
        disponibleCheck.setSelected(false);
        nonDisponibleCheck.setSelected(false);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleDisponibiliteSelection() {
        if (disponibleCheck.isSelected()) {
            nonDisponibleCheck.setSelected(false);
        }
        if (nonDisponibleCheck.isSelected()) {
            disponibleCheck.setSelected(false);
        }
    }
}
