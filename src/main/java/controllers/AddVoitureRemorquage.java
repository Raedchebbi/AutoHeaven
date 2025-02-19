package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import utils.MyDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddVoitureRemorquage {

    @FXML
    private TextField AnneeVR;

    @FXML
    private Button ButtonAjouterVR;

    @FXML
    private TextField ModeleVR;

    @FXML
    private TextField NumAgence;

    @FXML
    private TextField StatusVR;

    @FXML
    private void handleButtonAjouterVR() {
        String modele = ModeleVR.getText().trim();
        String annee = AnneeVR.getText().trim();
        String numAgence = NumAgence.getText().trim();
        String status = StatusVR.getText().trim();

        // Validation des champs
        if (modele.isEmpty() || annee.isEmpty() || numAgence.isEmpty() || status.isEmpty()) {
            showAlert("Erreur", "Tous les champs sont obligatoires", Alert.AlertType.ERROR);
            return;
        }

        try {
            insererVoitureRemorquage(modele, annee, numAgence, status);
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur technique: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void insererVoitureRemorquage(String modele, String annee, String numAgence, String status) throws SQLException {
        String sql = "INSERT INTO voiture_remorquage (modele, annee, num_agence, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = MyDb.getInstance().getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, modele);
            pstmt.setString(2, annee);
            pstmt.setString(3, numAgence);
            pstmt.setString(4, status);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Succès", "Voiture de remorquage ajoutée avec succès!", Alert.AlertType.INFORMATION);
                clearFields();
            }
        }
    }

    private void clearFields() {
        ModeleVR.clear();
        AnneeVR.clear();
        NumAgence.clear();
        StatusVR.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}