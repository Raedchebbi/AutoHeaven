package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Voiture;
import services.VoitureService;

import java.io.File;

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
    private TextField disponibiliteTextField;
    @FXML
    private TextField idCategorieTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button imageButton;
    @FXML
    private ImageView imageView;
    @FXML
    private BorderPane borderPane;


    private final VoitureService voitureService = new VoitureService();
    private Voiture voiture;
    private String selectedImagePath = null;

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
        if (voiture != null) {
            marqueTextField.setText(voiture.getMarque());
            descriptionTextField.setText(voiture.getDescription());
            kilometrageTextField.setText(String.valueOf(voiture.getKilometrage()));
            couleurTextField.setText(voiture.getCouleur());
            prixTextField.setText(String.valueOf(voiture.getPrix()));
            disponibiliteTextField.setText(voiture.getDisponibilite());
            idCategorieTextField.setText(String.valueOf(voiture.getId_c()));

            if (voiture.getImage() != null && !voiture.getImage().isEmpty()) {
                Image image = new Image("file:" + voiture.getImage());
                imageView.setImage(image);
                selectedImagePath = voiture.getImage();
            }
        }
    }



    private boolean validateFields() {
        if (marqueTextField.getText().trim().isEmpty() || descriptionTextField.getText().trim().isEmpty() ||
                kilometrageTextField.getText().trim().isEmpty() || couleurTextField.getText().trim().isEmpty() ||
                prixTextField.getText().trim().isEmpty() || disponibiliteTextField.getText().trim().isEmpty() ||
                idCategorieTextField.getText().trim().isEmpty()) {
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

    private boolean isModified() {
        if (voiture == null) return false;

        return !voiture.getMarque().equals(marqueTextField.getText().trim()) ||
                !voiture.getDescription().equals(descriptionTextField.getText().trim()) ||
                voiture.getKilometrage() != Integer.parseInt(kilometrageTextField.getText().trim()) ||
                !voiture.getCouleur().equals(couleurTextField.getText().trim()) ||
                voiture.getPrix() != Double.parseDouble(prixTextField.getText().trim()) ||
                !voiture.getDisponibilite().equalsIgnoreCase(disponibiliteTextField.getText().trim()) ||
                voiture.getId_c() != Integer.parseInt(idCategorieTextField.getText().trim()) ||
                (selectedImagePath != null && !selectedImagePath.equals(voiture.getImage()));
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

        if (!isModified()) {
            showErrorAlert("Aucune modification détectée.");
            return;
        }

        voiture.setMarque(marqueTextField.getText().trim());
        voiture.setDescription(descriptionTextField.getText().trim());
        voiture.setKilometrage(Integer.parseInt(kilometrageTextField.getText().trim()));
        voiture.setCouleur(couleurTextField.getText().trim());
        voiture.setPrix(Double.parseDouble(prixTextField.getText().trim()));
        voiture.setDisponibilite(disponibiliteTextField.getText().trim().toLowerCase());
        voiture.setId_c(Integer.parseInt(idCategorieTextField.getText().trim()));

        if (selectedImagePath != null) {
            voiture.setImage(selectedImagePath);
        }

        try {
            voitureService.update(voiture);
            showSuccessAlert();
           // closeWindow();
        } catch (Exception e) {
            showErrorAlert("Erreur lors de la mise à jour de la voiture : " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    private void handleImageSelection() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(imageButton.getScene().getWindow());

        if (selectedFile != null) {
            selectedImagePath = selectedFile.getAbsolutePath();
            Image image = new Image("file:" + selectedImagePath);
            imageView.setImage(image);
        }
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
