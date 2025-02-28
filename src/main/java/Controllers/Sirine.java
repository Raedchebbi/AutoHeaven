package Controllers;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import javafx.scene.layout.HBox;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.avis;
import service.AvisService;
import utils.MyDb;

public class Sirine {
    private static final List<String> MOTS_INTERDITS = Arrays.asList(
            "arnaque", "escroc", "nul", "arnaqueur", "fake", "arnaquer",
            "insulte", "idiot", "inacceptable", "arnaqueuse", "banni", "inadmissible"
    );

    @FXML private TextField  iduser, idvoiture;
    @FXML private TextArea commentaire;
    @FXML private DatePicker date;
    @FXML private Button creer, modifier, supprimer, list;
    @FXML private Label reponse;
    @FXML private Label note;  // Si c'est un Label

    @FXML private HBox hboxStars;
    @FXML private Button star1;

    @FXML private Button star2;

    @FXML private Button star3;

    @FXML private Button star4;
    private List<Button> stars;

    private final AvisService avisService = new AvisService();



    @FXML
    private void initialize() {
        System.out.println("Initialisation du contrôleur...");

        // Initialisation des boutons pour les actions
        list.setOnAction(event -> ouvrirListeAvis());
        creer.setOnAction(event -> creerAvis());
        modifier.setOnAction(event -> modifierAvis());
        supprimer.setOnAction(event -> confirmerSuppression());

        // Initialisation de la liste des étoiles
        stars = Arrays.asList(star1, star2, star3, star4);

        // Ensure buttons are not null before setting the event handlers
        if (stars != null) {
            for (Button star : stars) {
                if (star != null) {
                    star.setOnMouseClicked(this::handleStarClick);
                }
            }
        } else {
            System.out.println("Les boutons d'étoiles n'ont pas été initialisés correctement.");
        }

        // Initialiser la note avec une valeur par défaut de 0 si vide
        int initialRating = 0;
        if (note != null && !note.getText().isEmpty()) {
            try {
                initialRating = Integer.parseInt(note.getText());
            } catch (NumberFormatException e) {
                System.out.println("La note n'est pas valide, utilisation de la note par défaut 0.");
            }
        }

        // Mettre à jour l'affichage des étoiles en fonction de la note initiale
        updateStars(initialRating);
    }


    private void setupStarRating() {
        // Boucle pour gérer les clics sur chaque étoile
        for (int i = 0; i < stars.size(); i++) {
            final int rating = i + 1;
            stars.get(i).setOnAction(event -> updateStars(rating));  // Met à jour la note quand une étoile est cliquée
        }
    }

    private void updateStars(int rating) {
        if (stars != null && !stars.isEmpty()) {
            for (int i = 0; i < stars.size(); i++) {
                Button star = stars.get(i);
                if (star != null) {
                    if (i < rating) {
                        star.getStyleClass().add("selected-star");
                    } else {
                        star.getStyleClass().remove("selected-star");
                    }
                }
            }
        }
    }

    private void handleStarClick(MouseEvent event) {
        Button clickedStar = (Button) event.getSource();
        int rating = stars.indexOf(clickedStar) + 1;
        updateStars(rating);
    }

    private void ouvrirListeAvis() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/list.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) list.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleStarClick(javafx.scene.input.MouseEvent event) {
        Button clickedStar = (Button) event.getSource();
        int starIndex = stars.indexOf(clickedStar) + 1; // Détermine l'index de l'étoile cliquée (1 à 4)

        // Met à jour la valeur de la note dans le champ texte
        note.setText(String.valueOf(starIndex));

        // Met à jour l'affichage des étoiles
        updateStars(starIndex);
    }

    private void genererReponseAutomatique(String commentaire) {
        if (commentaire.isEmpty()) {
            reponse.setText("");
            return;
        }

        if (contientMotsInterdits(commentaire) || estSpam(commentaire) || contientLien(commentaire)) {
            reponse.setText("Votre commentaire semble inapproprié.");
            return;
        }

        // Liste de mots positifs
        List<String> motsPositifs = Arrays.asList("super", "excellent", "top", "magnifique", "satisfait", "bien", "merci", "adoré", "bravo");

        // Vérifier si le commentaire contient des mots positifs
        boolean estPositif = motsPositifs.stream().anyMatch(commentaire.toLowerCase()::contains);

        if (estPositif) {
            reponse.setText("Merci pour votre avis positif ! 😊");
        } else {
            reponse.setText("Nous sommes désolés que votre expérience n’ait pas été satisfaisante. Nous allons nous améliorer !");
        }
    }

    private boolean estSpam(String commentaire) {
        if (commentaire.length() < 5 || commentaire.length() > 300) return true;

        String[] words = commentaire.split("\\s+");
        for (int i = 0; i < words.length - 2; i++) {
            if (words[i].equalsIgnoreCase(words[i + 1]) && words[i].equalsIgnoreCase(words[i + 2])) {
                return true;
            }
        }

        long countUpper = commentaire.chars().filter(Character::isUpperCase).count();
        long totalLetters = commentaire.chars().filter(Character::isLetter).count();
        return totalLetters > 0 && (countUpper / (double) totalLetters) > 0.7;
    }

    private boolean contientMotsInterdits(String commentaire) {
        String regex = "\\b(" + String.join("|", MOTS_INTERDITS) + ")\\b";
        return Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(commentaire).find();
    }

    private boolean contientLien(String commentaire) {
        return Pattern.compile("(http|www\\.)\\S+", Pattern.CASE_INSENSITIVE).matcher(commentaire).find();
    }

    private boolean isCommentValid(String commentaire) {
        if (contientMotsInterdits(commentaire) || estSpam(commentaire) || contientLien(commentaire)) {
            showError("Commentaire refusé", "Votre commentaire est inapproprié ou non conforme.");
            return false;
        }
        return true;
    }

    private boolean validateFields() {
        if (note.getText().isEmpty() || commentaire.getText().isEmpty() || date.getValue() == null ||
                iduser.getText().isEmpty() || idvoiture.getText().isEmpty()) {
            showError("Erreur", "Tous les champs doivent être remplis !");
            return false;
        }
        return true;
    }

    private void creerAvis() {
        if (!validateFields()) return;

        try {
            int noteValue = Integer.parseInt(note.getText());
            String commentaireValue = commentaire.getText();
            Date dateAvis = Date.valueOf(date.getValue());
            int idUser = Integer.parseInt(iduser.getText());
            int idVoiture = Integer.parseInt(idvoiture.getText());

            if (!isCommentValid(commentaireValue)) return;

            avis newAvis = new avis(noteValue, commentaireValue, dateAvis, idUser, idVoiture);

            avisService.create(newAvis);
            genererReponseAutomatique(commentaireValue);

            showSuccess("Avis ajouté avec succès !");
        } catch (Exception e) {
            showError("Erreur", "Impossible d'ajouter l'avis.");
            e.printStackTrace();
        }
    }

    private void modifierAvis() {
        // Valider les champs avant d'exécuter la logique
        if (!validateFields()) return;

        // Déclarez et initialisez la connexion
        try (Connection connection = MyDb.getInstance().getConn()) {
            // Vérifiez que la connexion est valide avant d'exécuter
            if (connection == null || connection.isClosed()) {
                showError("Erreur", "La connexion à la base de données est fermée.");
                return;
            }

            // Récupérez les valeurs des champs de l'interface utilisateur
            int idUser = Integer.parseInt(iduser.getText());
            int idVoiture = Integer.parseInt(idvoiture.getText());
            int noteValue = Integer.parseInt(note.getText());
            String commentaireValue = commentaire.getText();
            Date dateAvis = Date.valueOf(date.getValue());

            // Valider le commentaire
            if (!isCommentValid(commentaireValue)) {
                showError("Erreur", "Le commentaire n'est pas valide.");
                return;
            }

            // Créez et exécutez la requête de mise à jour
            String updateQuery = "UPDATE avis SET note = ?, commentaire = ?, dateavis = ? WHERE id = ? AND id_v = ?";
            try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                updateStmt.setInt(1, noteValue);
                updateStmt.setString(2, commentaireValue);
                updateStmt.setDate(3, dateAvis);
                updateStmt.setInt(4, idUser);
                updateStmt.setInt(5, idVoiture);

                // Exécutez la mise à jour
                int rowsAffected = updateStmt.executeUpdate();
                if (rowsAffected > 0) {
                    // Si des lignes ont été affectées, l'avis a été modifié avec succès
                    showSuccess("Avis modifié avec succès !");
                } else {
                    // Si aucune ligne n'a été affectée, cela signifie que l'avis n'existe pas
                    showError("Erreur", "Aucun avis trouvé avec les identifiants fournis.");
                }
            }
        } catch (SQLException e) {
            // Gérer les erreurs SQL
            showError("Erreur", "Impossible de modifier l'avis.");
            e.printStackTrace();
        }
    }


    private void confirmerSuppression() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment supprimer cet avis ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                supprimerAvis();
            }
        });
    }

    private void supprimerAvis() {
        // Valider les champs avant d'exécuter la logique
        if (!validateFields()) return;

        // Déclarez et initialisez la connexion
        try (Connection connection = MyDb.getInstance().getConn()) {
            // Vérifiez que la connexion est valide avant d'exécuter
            if (connection == null || connection.isClosed()) {
                showError("Erreur", "La connexion à la base de données est fermée.");
                return;
            }

            // Récupérez les valeurs des champs de l'interface utilisateur
            int idUser = Integer.parseInt(iduser.getText());
            int idVoiture = Integer.parseInt(idvoiture.getText());

            // Créez et exécutez la requête de suppression
            String deleteQuery = "DELETE FROM avis WHERE id = ? AND id_v = ?";
            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
                deleteStmt.setInt(1, idUser);
                deleteStmt.setInt(2, idVoiture);

                // Exécutez la suppression
                int rowsAffected = deleteStmt.executeUpdate();
                if (rowsAffected > 0) {
                    // Si des lignes ont été affectées, l'avis a été supprimé avec succès
                    showSuccess("Avis supprimé avec succès !");
                } else {
                    // Si aucune ligne n'a été affectée, cela signifie que l'avis n'existe pas
                    showError("Erreur", "Aucun avis trouvé avec les identifiants fournis.");
                }
            }
        } catch (SQLException e) {
            // Gérer les erreurs SQL
            showError("Erreur", "Impossible de supprimer l'avis.");
            e.printStackTrace();
        }
    }


    private void showSuccess(String message) {
        new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK).showAndWait();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
