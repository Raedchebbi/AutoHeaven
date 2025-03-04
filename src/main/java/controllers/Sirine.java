package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import services.AvisService;
import models.avis;
import utils.MyDb;

public class Sirine {

    @FXML private TextArea commentaire;
    @FXML private DatePicker date;
    @FXML private Button creer, modifier, supprimer, list;
    @FXML private TextField iduser, idvoiture;
    @FXML private Button star1, star2, star3;
    @FXML private Label note;
    private AvisService avisService = new AvisService();
    private List<Button> stars;
    private int noteValue = 0;

    // Liste des mots interdits
    private List<String> badWords = Arrays.asList("arnaque", "escroquerie", "fraude", "spam", "scam", "phishing", "escroc", "escroquer",
            "tromperie", "mensonge", "arnaqueur", "fraudeur", "contrefaçon", "faux", "illégal",
            "piratage", "hameçonnage", "publicité mensongère", "clickbait", "faux site", "virus", "malware");

    @FXML
    private void initialize() {
        stars = List.of(star1, star2, star3);
        for (Button star : stars) {
            star.setOnMouseClicked(this::handleStarClick);
        }

        list.setOnAction(event -> openListView());
        creer.setOnAction(event -> {
            if (validateFields()) {
                createAvis();
            }
        });

        modifier.setOnAction(event -> {
            if (validateFields()) {
                updateAvis();
            }
        });

        supprimer.setOnAction(event -> {
            if (validateFields()) {
                deleteAvis();
            }
        });
    }

    @FXML
    private void handleStarClick(MouseEvent event) {
        Button clickedStar = (Button) event.getSource();
        noteValue = stars.indexOf(clickedStar) + 1;
        for (int i = 0; i < stars.size(); i++) {
            stars.get(i).setStyle(i < noteValue ? "-fx-background-color: gold;" : "-fx-background-color: lightgray;");
        }
        note.setText(String.valueOf(noteValue));
    }

    private boolean validateFields() {
        if (note.getText().isEmpty() || commentaire.getText().isEmpty() || date.getValue() == null ||
                iduser.getText().isEmpty() || idvoiture.getText().isEmpty()) {
            showError("Erreur", "Tous les champs doivent être remplis !");
            return false;
        }

        // Vérifier si le commentaire contient des mots interdits
        if (containsBadWords(commentaire.getText())) {
            showError("Erreur", "Le commentaire contient des mots interdits.");
            return false;
        }

        return true;
    }

    private boolean containsBadWords(String text) {
        // Vérifie si le texte contient l'un des mots interdits
        for (String badWord : badWords) {
            if (text.toLowerCase().contains(badWord.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openListView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/list.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) list.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String generateAutoResponse(String commentaire) {
        // Vérifier si le commentaire contient des mots positifs ou négatifs
        if (commentaire.toLowerCase().contains("excellent") || commentaire.toLowerCase().contains("super") ||
                commentaire.toLowerCase().contains("parfait") || commentaire.toLowerCase().contains("très satisfait")) {
            // Réponse pour un commentaire positif
            return "Merci beaucoup pour votre retour positif ! Nous sommes ravis que vous ayez eu une bonne expérience.";
        } else if (commentaire.toLowerCase().contains("mauvais") || commentaire.toLowerCase().contains("déçu") ||
                commentaire.toLowerCase().contains("problème") || commentaire.toLowerCase().contains("échec")) {
            // Réponse pour un commentaire négatif
            return "Nous sommes désolés que vous ayez eu une mauvaise expérience. Nous nous excusons pour cela et nous vous invitons à nous contacter pour résoudre ce problème.";
        } else {
            // Réponse neutre pour les commentaires neutres
            return "Merci pour votre avis ! Nous apprécions vos retours.";
        }
    }
    private void createAvis() {
        try {
            // Vérification des mots interdits avant de créer l'avis
            if (containsBadWords(commentaire.getText())) {
                showError("Erreur", "Le commentaire contient des mots interdits (spam, arnaque, etc.).");
                return; // Ne pas créer l'avis si des mots interdits sont présents
            }

            // Génération d'une réponse automatique
            String autoResponse = generateAutoResponse(commentaire.getText());

            // Affichage de la réponse sous forme de pop-up (Alert)
            showAutoResponsePopup(autoResponse);

            int noteVal = Integer.parseInt(note.getText());
            String commentaireValue = commentaire.getText();
            Date dateAvis = Date.valueOf(date.getValue());
            int idUser = Integer.parseInt(iduser.getText());
            int idVoiture = Integer.parseInt(idvoiture.getText());

            avis newAvis = new avis(noteVal, commentaireValue, dateAvis, idUser, idVoiture);
            avisService.create(newAvis);
            showSuccess("Avis ajouté avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur", "Impossible d'ajouter l'avis.");
        }
    }

    private void showAutoResponsePopup(String response) {
        // Créer un pop-up avec un style personnalisé
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Réponse Automatique");
        alert.setHeaderText(null);  // Pas de texte dans l'en-tête
        alert.setContentText(response);  // Texte de la réponse automatique

        // Style personnalisé
        alert.getDialogPane().setStyle("-fx-background-color: lightblue; -fx-font-size: 14px; -fx-font-weight: bold;");

        // Optionnel : Ajouter un bouton d'action
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        alert.showAndWait();
    }



    private void updateAvis() {
        try {
            int idUser = Integer.parseInt(iduser.getText());
            int idVoiture = Integer.parseInt(idvoiture.getText());
            Connection connection = MyDb.getInstance().getConn();

            String query = "UPDATE avis SET note = ?, commentaire = ?, dateavis = ? WHERE id = ? AND id_v = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, Integer.parseInt(note.getText()));
                stmt.setString(2, commentaire.getText());
                stmt.setDate(3, Date.valueOf(date.getValue()));
                stmt.setInt(4, idUser);
                stmt.setInt(5, idVoiture);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    showSuccess("Avis mis à jour avec succès !");
                } else {
                    showError("Erreur", "Aucun avis trouvé pour cet utilisateur et cette voiture.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Erreur", "Impossible de modifier l'avis.");
        }
    }

    private void deleteAvis() {
        try {
            int idUser = Integer.parseInt(iduser.getText());
            int idVoiture = Integer.parseInt(idvoiture.getText());
            Connection connection = MyDb.getInstance().getConn();

            String query = "DELETE FROM avis WHERE id = ? AND id_v = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, idUser);
                stmt.setInt(2, idVoiture);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    showSuccess("Avis supprimé avec succès !");
                } else {
                    showError("Erreur", "Aucun avis trouvé pour cet utilisateur et cette voiture.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Erreur", "Impossible de supprimer l'avis.");
        }
    }
}
