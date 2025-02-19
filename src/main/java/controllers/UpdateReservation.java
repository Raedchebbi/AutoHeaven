package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import services.ReservationService;

public class UpdateReservation {

    @FXML
    private TextField reservationIdField;
    @FXML
    private TextField clientNameField;
    @FXML
    private DatePicker reservationDatePicker;
    @FXML
    private TextField statusField;

    @FXML
    private void handleUpdateReservation() {
        String reservationId = reservationIdField.getText();
        String clientName = clientNameField.getText();
        String reservationDate = reservationDatePicker.getValue() != null ? reservationDatePicker.getValue().toString() : "";
        String status = statusField.getText();

        if (reservationId.isEmpty() || clientName.isEmpty() || reservationDate.isEmpty() || status.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis !");
            return;
        }

        ReservationService reservationService = new ReservationService();

// Dans handleUpdateReservation()

        // Affichage d'un message de confirmation
        showAlert("Succès", "Réservation mise à jour avec succès !");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}