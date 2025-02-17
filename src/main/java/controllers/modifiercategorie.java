package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Categorie;
import services.CategorieService;

public class modifiercategorie {

    @FXML
    private TextField typeField;
    @FXML
    private TextField carburantField;
    @FXML
    private TextField utilisationField;
    @FXML
    private TextField transmissionField;
    @FXML
    private TextField portesField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private final CategorieService categorieService = new CategorieService();
    private Categorie categorie;


    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
        if (categorie != null) {
            typeField.setText(categorie.getType());
            carburantField.setText(categorie.getType_carburant());
            utilisationField.setText(categorie.getType_utilisation());
            transmissionField.setText(categorie.getTransmission());
            portesField.setText(String.valueOf(categorie.getNbr_porte()));
        }
    }

    @FXML
    private void handleSaveModification() {
        if (!validateFields()) {
            return;
        }

        if (categorie == null || categorie.getId_c() == 0) {
            showErrorAlert("Erreur : Catégorie invalide ou ID manquant.");
            return;
        }


        System.out.println("Updating category with ID: " + categorie.getId_c());


        categorie.setType(typeField.getText().trim());
        categorie.setType_carburant(carburantField.getText().trim());
        categorie.setType_utilisation(utilisationField.getText().trim());
        categorie.setTransmission(transmissionField.getText().trim());

        try {
            categorie.setNbr_porte(Integer.parseInt(portesField.getText().trim()));
        } catch (NumberFormatException e) {
            showErrorAlert("Le nombre de portes doit être un nombre valide.");
            return;
        }

        try {
            categorieService.update(categorie); // No return value needed
            showSuccessAlert();
            closeWindow();
        } catch (Exception e) {
            showErrorAlert("Erreur lors de la mise à jour de la catégorie : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow(); // Close the window without saving
    }


    private boolean validateFields() {
        if (typeField.getText().trim().isEmpty() || carburantField.getText().trim().isEmpty() ||
                utilisationField.getText().trim().isEmpty() || transmissionField.getText().trim().isEmpty() ||
                portesField.getText().trim().isEmpty()) {
            showErrorAlert("Veuillez remplir tous les champs.");
            return false;
        }

        try {
            Integer.parseInt(portesField.getText().trim()); // Check if it's a valid integer
        } catch (NumberFormatException e) {
            showErrorAlert("Le nombre de portes doit être un nombre valide.");
            return false;
        }

        return true;
    }


    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mise à jour réussie");
        alert.setHeaderText(null);
        alert.setContentText("La catégorie a été mise à jour avec succès !");
        alert.showAndWait();
    }


    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}

