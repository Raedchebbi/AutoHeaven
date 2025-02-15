package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.Reclamation;
import services.ReclamationService;

import java.util.List;

public class ReclamationController {
    @FXML
    private VBox vboxReclamations;  // VBox pour contenir les HBox

    private final ReclamationService reclamationService = new ReclamationService();

    public void initialize() {
        loadReclamations();
    }

    public void loadReclamations() {
        try {
            // Ajouter un header HBox avec les titres des colonnes
            HBox headerHBox = new HBox(20);
            headerHBox.getChildren().addAll(
                    new Text("Titre"), new Text("Contenu"),
                    new Text("Status"), new Text("Date"),
                    new Text("Nom"), new Text("Email"), new Text("Tel")
            );
            vboxReclamations.getChildren().add(headerHBox);

            // Récupérer les réclamations depuis la BD
            List<Reclamation> reclamations = reclamationService.getAll();

            for (Reclamation rec : reclamations) {
                HBox hbox = afficherReclamation(rec);
                vboxReclamations.getChildren().add(hbox);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HBox afficherReclamation(Reclamation rec) {
        HBox hbox = new HBox(20);

        Text titreText = new Text(rec.getTitre());
        Text contenuText = new Text(rec.getContenu());
        Text statusText = new Text(rec.getStatus());
        Text dateText = new Text(rec.getDateCreation().toString());
        Text nomText = new Text(rec.getNom());
        Text emailText = new Text(rec.getEmail());
        Text telText = new Text(rec.getTel());

        hbox.getChildren().addAll(titreText, contenuText, statusText, dateText, nomText, emailText, telText);
        return hbox;
    }
}
