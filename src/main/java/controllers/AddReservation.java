package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import utils.MyDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddReservation {

    @FXML
    private Button ajouterButton;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField userIdTextField;

    @FXML
    private TextField voitureIdTextField;

    @FXML
    void handleAjouterReservation(ActionEvent event) {

    }

    @FXML
    private void handleAjouterReservation() {
        String userId = userIdTextField.getText().trim();
        String voitureId = voitureIdTextField.getText().trim();
        String date = datePicker.getValue() != null ? datePicker.getValue().toString() : "";

        // Validation des champs
        if (userId.isEmpty() || voitureId.isEmpty() || date.isEmpty()) {
            showAlert("Erreur", "Tous les champs sont obligatoires", Alert.AlertType.ERROR);
            return;
        }

        try {
            insererReservation(userId, voitureId, date);
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur technique: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void clearFields() {
        userIdTextField.clear();
        voitureIdTextField.clear();
        datePicker.setValue(null);
    }

    private void insererReservation(String userId, String voitureId, String date) throws SQLException {
        String sql = "INSERT INTO reservation (user_id, voiture_id, date_reservation) VALUES (?, ?, ?)";

        try (Connection conn = MyDb.getInstance().getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            pstmt.setString(2, voitureId);
            pstmt.setString(3, date);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Succès", "Réservation ajoutée avec succès!", Alert.AlertType.INFORMATION);
                clearFields();
            }
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
