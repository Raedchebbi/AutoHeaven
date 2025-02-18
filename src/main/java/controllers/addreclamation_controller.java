package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import utils.MyDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class addreclamation_controller {

    private static final int USER_ID = 3;
    private static final int TITRE_MAX_LENGTH = 100;
    private static final int CONTENU_MIN_LENGTH = 10;
    private static final int CONTENU_MAX_LENGTH = 1000;

    @FXML
    private TextField objetTextField;
    @FXML
    private TextArea reclamationTextArea;
    @FXML
    private Label erreurObjet;
    @FXML
    private Label erreurReclamation;

    @FXML
    private void handleEnvoyerReclamation() {
        clearErrors();

        String titre = objetTextField.getText().trim();
        String contenu = reclamationTextArea.getText().trim();
        boolean isValid = true;

        // Validation Objet
        if (titre.isEmpty()) {
            erreurObjet.setText("Ce champ est obligatoire");
            isValid = false;
        } else if (titre.length() > TITRE_MAX_LENGTH) {
            erreurObjet.setText("Maximum " + TITRE_MAX_LENGTH + " caractères");
            isValid = false;
        } else if (!titre.matches("^[a-zA-Z0-9 àâäéèêëîïôöùûüç,.'!?-]+$")) {
            erreurObjet.setText("Caractères spéciaux non autorisés");
            isValid = false;
        }

        // Validation Réclamation
        if (contenu.isEmpty()) {
            erreurReclamation.setText("Ce champ est obligatoire");
            isValid = false;
        } else if (contenu.length() < CONTENU_MIN_LENGTH) {
            erreurReclamation.setText("Minimum " + CONTENU_MIN_LENGTH + " caractères");
            isValid = false;
        } else if (contenu.length() > CONTENU_MAX_LENGTH) {
            erreurReclamation.setText("Maximum " + CONTENU_MAX_LENGTH + " caractères");
            isValid = false;
        }

        if (isValid) {
            insererReclamation(titre, contenu);
        }
    }

    private void insererReclamation(String titre, String contenu) {
        String sql = "INSERT INTO reclamation (titre, contenu, id) VALUES (?, ?, ?)";

        try (Connection conn = MyDb.getInstance().getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, titre);
            pstmt.setString(2, contenu);
            pstmt.setInt(3, USER_ID);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Succès", "Réclamation envoyée avec succès!", Alert.AlertType.INFORMATION);
                clearFields();
            }

        } catch (SQLException e) {
            showAlert("Erreur", "Erreur technique: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void clearErrors() {
        erreurObjet.setText("");
        erreurReclamation.setText("");
    }

    private void clearFields() {
        objetTextField.clear();
        reclamationTextArea.clear();
        clearErrors();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}