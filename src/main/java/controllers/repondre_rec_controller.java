package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import utils.MyDb;

import java.sql.*;

public class repondre_rec_controller {

    @FXML
    private TextField reclamationTitre;
    @FXML
    private TextArea reclamationContent;
    @FXML
    private TextArea responseTextArea; // Corrigé pour corresponds à repondrereclamation.fxml

    private int reclamationId;
    private Runnable onSuccessCallback;

    public void setReclamationId(int idRec) {
        this.reclamationId = idRec;
    }

    public void setReclamationTitre(String titre) {
        reclamationTitre.setText(titre);
    }

    public void setReclamationContent(String content) {
        reclamationContent.setText(content);
    }

    public void setOnSuccessCallback(Runnable callback) {
        this.onSuccessCallback = callback;
    }

    @FXML
    private void handleEnvoyerReponse() {
        if (validateInput()) {
            if (saveMessageToDatabase() && updateStatusInDatabase()) {
                showSuccessAlert();
                if (onSuccessCallback != null) onSuccessCallback.run(); // Refresh the main view and hide the form
            }
        }
    }

    private boolean validateInput() {
        if (responseTextArea.getText().trim().isEmpty()) { // Corrigé pour utiliser responseTextArea
            showAlert("Erreur", "Le champ réponse est obligatoire");
            return false;
        }
        return true;
    }

    private boolean saveMessageToDatabase() {
        String query = "INSERT INTO messagerie (message, sender, id_rec, datemessage, receiver) VALUES (?, ?, ?, NOW(), ?)";

        try (Connection conn = MyDb.getInstance().getConn();
             PreparedStatement ps = conn.prepareStatement(query)) {

            if (conn == null || conn.isClosed()) {
                showAlert("Erreur SQL", "Connexion fermée. Impossible d'enregistrer.");
                return false;
            }

            ps.setString(1, responseTextArea.getText().trim()); // Utilise responseTextArea
            ps.setString(2, "admin"); // sender est 'admin' selon ton code
            ps.setInt(3, reclamationId);
            ps.setString(4, "client"); // receiver est 'client' par défaut (ajustable selon ton besoin)

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            } else {
                showAlert("Erreur", "Aucune ligne insérée.");
            }

        } catch (SQLException e) {
            showAlert("Erreur SQL", "Échec de l'enregistrement du message : " + e.getMessage());
        }
        return false;
    }

    private boolean updateStatusInDatabase() {
        String query = "UPDATE reclamation SET status = 'repondu' WHERE id_rec = ?";

        try (Connection conn = MyDb.getInstance().getConn();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, reclamationId);
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                return true;
            } else {
                showAlert("Erreur", "Aucune réclamation trouvée pour mise à jour.");
            }

        } catch (SQLException e) {
            showAlert("Erreur SQL", "Échec de la mise à jour du statut : " + e.getMessage());
        }
        return false;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText("Réponse envoyée avec succès !");
        alert.setContentText("");
        alert.showAndWait();
    }
}