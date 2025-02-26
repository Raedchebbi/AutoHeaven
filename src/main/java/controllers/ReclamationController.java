package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Reclamation;
import services.ReclamationService;

import java.time.LocalDateTime;

public class ReclamationController {
    @FXML
    private TextField txtTitre;
    @FXML
    private TextArea txtContenu;
    @FXML
    private TextArea txtContenuTraduit;
    @FXML
    private ComboBox<String> cmbStatus;
    @FXML
    private Button btnAjouter;

    private final ReclamationService service = new ReclamationService();

    @FXML
    private void ajouterReclamation() {
        try {
            String titre = txtTitre.getText();
            String contenu = txtContenu.getText();
            String contenuTraduit = txtContenuTraduit.getText();
            String status = cmbStatus.getValue();

            Reclamation reclamation = new Reclamation(titre, contenu, contenuTraduit, status, LocalDateTime.now(), 1);
            service.create(reclamation);
            afficherMessage("Réclamation ajoutée avec succès !");
        } catch (Exception e) {
            afficherMessage("Erreur : " + e.getMessage());
        }
    }

    private void afficherMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
