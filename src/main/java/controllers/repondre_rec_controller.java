package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.MyDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class repondre_rec_controller {

    @FXML private TextField titreField;
    @FXML private TextArea contenuField;
    @FXML private TextArea reponseField;

    private int reclamationId;
    private Runnable onSuccessCallback;

    public void setReclamationId(int idRec) {
        this.reclamationId = idRec;
    }

    public void setReclamationTitre(String titre) {
        titreField.setText(titre);
    }

    public void setReclamationContent(String content) {
        contenuField.setText(content);
    }

    public void setOnSuccessCallback(Runnable callback) {
        this.onSuccessCallback = callback;
    }

    @FXML
    private void handleEnvoyerReponse() {
        if (validateInput()) {
            if (updateStatusInDatabase()) {
                closeWindow();
                if (onSuccessCallback != null) onSuccessCallback.run();
            }
        }
    }

    private boolean validateInput() {
        if (reponseField.getText().trim().isEmpty()) {
            showAlert("Erreur", "Le champ réponse est obligatoire");
            return false;
        }
        return true;
    }

    private boolean updateStatusInDatabase() {
        String query = "UPDATE reclamation SET status = 'traité' WHERE id_rec = ?";

        try (Connection conn = MyDb.getInstance().getConn();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, reclamationId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            showAlert("Erreur SQL", "Échec de la mise à jour : " + e.getMessage());
        }
        return false;
    }

    private void closeWindow() {
        Stage stage = (Stage) reponseField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}