package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import utils.MyDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class addvoiture {

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
    private TextField disponibleTextField;
    @FXML
    private TextField idCategorieTextField;
    @FXML
    private Button ajouterButton;

    @FXML
    private void handleAjouterVoiture() {
        String marque = marqueTextField.getText().trim();
        String description = descriptionTextField.getText().trim();
        String kilometrage = kilometrageTextField.getText().trim();
        String couleur = couleurTextField.getText().trim();
        String prix = prixTextField.getText().trim();
        String image = imageTextField.getText().trim();
        String disponibilite = disponibleTextField.getText().trim().toLowerCase();
        String idCategorie = idCategorieTextField.getText().trim();

        if (marque.isEmpty() || description.isEmpty() || kilometrage.isEmpty() ||
                couleur.isEmpty() || prix.isEmpty() || image.isEmpty() ||
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

            insererVoiture(marque, description, km, couleur, carPrice, image, categoryId, disponibilite);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Kilométrage, Prix et ID Catégorie doivent être des nombres valides", Alert.AlertType.ERROR);
        }
    }

    private void insererVoiture(String marque, String description, int kilometrage, String couleur,
                                double prix, String image, int idCategorie, String disponibilite) {
        String sql = "INSERT INTO voiture (marque, description, kilometrage, couleur, prix, image,id_c,disponibilite) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = MyDb.getInstance().getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, marque);
            pstmt.setString(2, description);
            pstmt.setInt(3, kilometrage);
            pstmt.setString(4, couleur);
            pstmt.setDouble(5, prix);
            pstmt.setString(6, image);
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
        imageTextField.clear();
        disponibleTextField.clear();
        idCategorieTextField.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
