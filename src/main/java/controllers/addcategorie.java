package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import utils.MyDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class addcategorie {

    @FXML
    private TextField typeTextField;
    @FXML
    private TextField typeCarburantTextField;
    @FXML
    private TextField typeUtilisationTextField;
    @FXML
    private TextField transmissionTextField;
    @FXML
    private TextField nombrePortesTextField;
    @FXML
    private Button ajouterButton;
    private dashboardController dashboardController;

    public void setDashboardController(dashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    @FXML
    private void handleAjouterCategorie() {
        String type = typeTextField.getText().trim();
        String typeCarburant = typeCarburantTextField.getText().trim();
        String typeUtilisation = typeUtilisationTextField.getText().trim();
        String transmission = transmissionTextField.getText().trim();
        String nombrePortes = nombrePortesTextField.getText().trim();

        if (type.isEmpty() || typeCarburant.isEmpty() || typeUtilisation.isEmpty() ||
                transmission.isEmpty() || nombrePortes.isEmpty()) {
            showAlert("Erreur", "Tous les champs sont obligatoires", Alert.AlertType.ERROR);
            return;
        }

        try {
            int portes = Integer.parseInt(nombrePortes);
            insererCategorie(type, typeCarburant, typeUtilisation, transmission, portes);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le nombre de portes doit être un entier", Alert.AlertType.ERROR);
        }
    }

    private void insererCategorie(String type, String typeCarburant, String typeUtilisation,
                                  String transmission, int nombrePortes) {
        String sql = "INSERT INTO categorie (type, type_carburant, type_utilisation, nbr_porte ,transmission) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = MyDb.getInstance().getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, type);
            pstmt.setString(2, typeCarburant);
            pstmt.setString(3, typeUtilisation);
            pstmt.setInt(4, nombrePortes);
            pstmt.setString(5, transmission);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Succès", "Catégorie ajoutée avec succès!", Alert.AlertType.INFORMATION);
                clearFields();
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur technique: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void clearFields() {
        typeTextField.clear();
        typeCarburantTextField.clear();
        typeUtilisationTextField.clear();
        transmissionTextField.clear();
        nombrePortesTextField.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
