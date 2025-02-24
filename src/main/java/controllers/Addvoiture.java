package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import utils.MyDb;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Addvoiture {

    public Button imageButton;
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
    private TextField disponibleTextField;
    @FXML
    private TextField idCategorieTextField;
    @FXML
    private ImageView imageView;

    private File selectedFile;

    @FXML
    private void handleImageSelection() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
        }
    }

    @FXML
    private void handleAjouterVoiture() {
        String marque = marqueTextField.getText().trim();
        String description = descriptionTextField.getText().trim();
        String kilometrage = kilometrageTextField.getText().trim();
        String couleur = couleurTextField.getText().trim();
        String prix = prixTextField.getText().trim();
        String disponibilite = disponibleTextField.getText().trim().toLowerCase();
        String idCategorie = idCategorieTextField.getText().trim();

        String imagePath = selectedFile != null ? selectedFile.getAbsolutePath() : "";

        if (marque.isEmpty() || description.isEmpty() || kilometrage.isEmpty() ||
                couleur.isEmpty() || prix.isEmpty() || imagePath.isEmpty() ||
                disponibilite.isEmpty() || idCategorie.isEmpty()) {
            showAlert("Erreur", "Tous les champs sont obligatoires", Alert.AlertType.ERROR);
            return;
        }

        if (!disponibilite.equals("oui") && !disponibilite.equals("non")) {
            showAlert("Erreur", "Disponibilité doit être 'oui' ou 'non'", Alert.AlertType.ERROR);
            return;
        }

        try {
            int km = Integer.parseInt(kilometrage);
            double carPrice = Double.parseDouble(prix);
            int categoryId = Integer.parseInt(idCategorie);

            insererVoiture(marque, description, km, couleur, carPrice, imagePath, categoryId, disponibilite);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Kilométrage, Prix et ID Catégorie doivent être des nombres valides", Alert.AlertType.ERROR);
        }
    }

    private void insererVoiture(String marque, String description, int kilometrage, String couleur,
                                double prix, String imagePath, int idCategorie, String disponibilite) {
        String sql = "INSERT INTO voiture (marque, description, kilometrage, couleur, prix, image, id_c, disponibilite) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = MyDb.getInstance().getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, marque);
            pstmt.setString(2, description);
            pstmt.setInt(3, kilometrage);
            pstmt.setString(4, couleur);
            pstmt.setDouble(5, prix);
            pstmt.setString(6, imagePath);
            pstmt.setInt(7, idCategorie);
            pstmt.setString(8, disponibilite);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Succès", "Voiture ajoutée avec succès!", Alert.AlertType.INFORMATION);
                clearFields();
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur technique: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void clearFields() {
        marqueTextField.clear();
        descriptionTextField.clear();
        kilometrageTextField.clear();
        couleurTextField.clear();
        prixTextField.clear();
        disponibleTextField.clear();
        idCategorieTextField.clear();
        imageView.setImage(null);
        selectedFile = null;
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
