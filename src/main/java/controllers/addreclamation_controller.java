package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import utils.MyDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class addreclamation_controller {

    // ID utilisateur statique pour les tests (ID = 3)
    private static final int USER_ID = 3;

    @FXML
    private TextField objetTextField;   // Correspond à "titre"
    @FXML
    private TextArea reclamationTextArea; // Correspond à "contenu"

    @FXML
    private void handleEnvoyerReclamation() {
        String titre = objetTextField.getText().trim();
        String contenu = reclamationTextArea.getText().trim();

        if (titre.isEmpty() || contenu.isEmpty()) {
            showAlert("Erreur", "Remplissez tous les champs.", Alert.AlertType.ERROR);
            return;
        }

        insererReclamation(titre, contenu);
    }

    private void insererReclamation(String titre, String contenu) {
        String sql = "INSERT INTO reclamation (titre, contenu, id) VALUES (?, ?, ?)";

        try (Connection conn = MyDb.getInstance().getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, titre);
            pstmt.setString(2, contenu);
            pstmt.setInt(3, USER_ID); // ID utilisateur statique (3)

            pstmt.executeUpdate();

            showAlert("Succès", "Réclamation enregistrée !", Alert.AlertType.INFORMATION);
            clearFields();

        } catch (SQLException e) {
            showAlert("Erreur", "Échec : " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        objetTextField.clear();
        reclamationTextArea.clear();
    }
}