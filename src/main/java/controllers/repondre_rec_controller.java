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
            if (saveMessageToDatabase() && updateStatusInDatabase()) {
                if (onSuccessCallback != null) onSuccessCallback.run(); // Rafraîchir l'affichage principal
                closeWindow();
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

    private boolean saveMessageToDatabase() {
        // Correction : Ajout de 'datemessage' avec NOW()
        String query = "INSERT INTO messagerie (message, receiver, id_rec, datemessage) VALUES (?, 'admin', ?, NOW())";

        try (Connection conn = MyDb.getInstance().getConn();
             PreparedStatement ps = conn.prepareStatement(query)) {

            // Vérification de la connexion
            if (conn == null || conn.isClosed()) {
                showAlert("Erreur SQL", "Connexion fermée. Impossible d'enregistrer.");
                return false;
            }

            System.out.println("Connexion active. Enregistrement du message...");

            // Ajout des valeurs à la requête
            ps.setString(1, reponseField.getText().trim());
            ps.setInt(2, reclamationId);

            // Exécution de la requête et retour du succès
            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Message enregistré avec succès !");
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
                System.out.println("Statut mis à jour avec succès !");
                return true;
            } else {
                showAlert("Erreur", "Aucune réclamation trouvée pour mise à jour.");
            }

        } catch (SQLException e) {
            showAlert("Erreur SQL", "Échec de la mise à jour du statut : " + e.getMessage());
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
