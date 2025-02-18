package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Voiture;
import services.VoitureService;

public class modifiervoiture {

    @FXML
    private TextField marqueTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField kilometrageTextField;
    @FXML
    private TextField couleurTextField;
    @FXML
    private TextField prixTextField;
    @FXML
    private TextField imageTextField;
    @FXML
    private TextField disponibiliteTextField;
    @FXML
    private TextField idCategorieTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private final VoitureService voitureService = new VoitureService();
    private Voiture voiture;

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
        if (voiture != null) {
            marqueTextField.setText(voiture.getMarque());
            descriptionTextField.setText(voiture.getDescription());
            kilometrageTextField.setText(String.valueOf(voiture.getKilometrage()));
            couleurTextField.setText(voiture.getCouleur());
            prixTextField.setText(String.valueOf(voiture.getPrix()));
            imageTextField.setText(voiture.getImage());
            disponibiliteTextField.setText(voiture.getDisponibilite());
            idCategorieTextField.setText(String.valueOf(voiture.getId_c()));
        }
    }

    private boolean validateFields() {
        if (marqueTextField.getText().trim().isEmpty() || descriptionTextField.getText().trim().isEmpty() ||
                kilometrageTextField.getText().trim().isEmpty() || couleurTextField.getText().trim().isEmpty() ||
                prixTextField.getText().trim().isEmpty() || imageTextField.getText().trim().isEmpty() ||
                disponibiliteTextField.getText().trim().isEmpty() || idCategorieTextField.getText().trim().isEmpty()) {
            showErrorAlert("Veuillez remplir tous les champs.");
            return false;
        }

        if (!disponibiliteTextField.getText().trim().equalsIgnoreCase("oui") &&
                !disponibiliteTextField.getText().trim().equalsIgnoreCase("non")) {
            showErrorAlert("La disponibilité doit être 'oui' ou 'non'.");
            return false;
        }

        try {
            Integer.parseInt(kilometrageTextField.getText().trim());
            Double.parseDouble(prixTextField.getText().trim());
            Integer.parseInt(idCategorieTextField.getText().trim());
        } catch (NumberFormatException e) {
            showErrorAlert("Kilométrage, Prix et ID Catégorie doivent être des nombres valides.");
            return false;
        }

        return true;
    }

    @FXML
    private void handleSaveModification() {
        if (!validateFields()) {
            return;
        }

        if (voiture == null || voiture.getId_v() == 0) {
            showErrorAlert("Erreur : Voiture invalide ou ID manquant.");
            return;
        }

        voiture.setMarque(marqueTextField.getText().trim());
        voiture.setDescription(descriptionTextField.getText().trim());
        voiture.setKilometrage(Integer.parseInt(kilometrageTextField.getText().trim()));
        voiture.setCouleur(couleurTextField.getText().trim());
        voiture.setPrix(Double.parseDouble(prixTextField.getText().trim()));
        voiture.setImage(imageTextField.getText().trim());
        voiture.setDisponibilite(disponibiliteTextField.getText().trim().toLowerCase());
        voiture.setId_c(Integer.parseInt(idCategorieTextField.getText().trim()));

        try {
            voitureService.update(voiture);
            showSuccessAlert();
            closeWindow();
        } catch (Exception e) {
            showErrorAlert("Erreur lors de la mise à jour de la voiture : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }


    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mise à jour réussie");
        alert.setHeaderText(null);
        alert.setContentText("La voiture a été mise à jour avec succès !");
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
