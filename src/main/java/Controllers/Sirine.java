package Controllers;
import java.io.IOException;
import java.sql.PreparedStatement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import service.AvisService;
import models.avis;
import utils.MyDb;
import javafx.scene.control.Button;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;

public class Sirine {

    @FXML
    private TextField note;

    @FXML
    private TextArea commentaire;

    @FXML
    private DatePicker date;

    @FXML
    private Button creer;

    @FXML
    private Button modifier;
    @FXML
    private TextField iduser;
    @FXML
    private Button list;
    @FXML
    private Button like;
    @FXML
    private TextField valeur;
    private int likeCount = 0;

    @FXML
    private TextField idvoiture;

    @FXML
    private Button supprimer;

    private AvisService avisService = new AvisService();

    @FXML
    private void initialize() {
// Action pour le bouton "list"
        list.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/list.fxml"));
                Parent root = loader.load();

                // Vérifie que le bouton a bien une scène avant d'appeler getWindow()
                if (list.getScene() != null) {
                    Stage stage = (Stage) list.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } else {
                    System.out.println("Erreur : le bouton n'est pas attaché à une scène.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Action pour le bouton "Créer"
        creer.setOnAction(event -> {
            try {
                if (validateFields()) {
                    createAvis();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Action pour le bouton "Modifier"
        modifier.setOnAction(event -> {
            try {
                if (validateFields()) {
                    updateAvis();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Action pour le bouton "Supprimer"
        supprimer.setOnAction(event -> {
            try {
                if (validateFields()) {
                    deleteAvis();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });



    }

    private boolean validateFields() {
        // Vérifier si tous les champs sont remplis
        if (note.getText().isEmpty() || commentaire.getText().isEmpty() || date.getValue() == null ||
                iduser.getText().isEmpty() || idvoiture.getText().isEmpty()) {
            showError("Erreur", "Tous les champs doivent être remplis !");
            return false;
        }
        return true;
    }
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void showSuccessAvis() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("avis a été ajouté avec succès !");
        alert.showAndWait();
    }
    private void showSuccessUpdateAvis() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("avis a été modifié avec succès !");
        alert.showAndWait();
    }
    private void showSuccessDeleteAvis() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("avis a été supprimé !");
        alert.showAndWait();
    }


    private void createAvis() throws Exception {
        // Récupération des valeurs depuis les champs de texte
        int noteValue = Integer.parseInt(note.getText());
        String commentaireValue = commentaire.getText();
        Date dateAvis = Date.valueOf(date.getValue());

        // Valeurs existantes pour id (Utilisateur) et id_v (Voiture)
        int idUser = 3;  // Remplacer par l'ID réel de l'utilisateur
        int idVoiture = 12;  // Remplacer par l'ID réel de la voiture

        // Création de l'objet avis avec les données récupérées
        avis newAvis = new avis(noteValue, commentaireValue, dateAvis, idUser, idVoiture);

        // Appel à la fonction de création pour insérer les données dans la base de données
        avisService.create(newAvis);
        System.out.println("Avis créé avec succès !");
        showSuccessAvis();

    }







    @FXML


    private void updateAvis() throws SQLException {
        // Récupérer l'ID de l'utilisateur et l'ID de la voiture à partir des champs de texte
        int idUser = Integer.parseInt(iduser.getText());  // ID de l'utilisateur
        int idVoiture = Integer.parseInt(idvoiture.getText());  // ID de la voiture

        // Connexion à la base de données
        Connection connection = MyDb.getInstance().getConn();

        // Requête pour récupérer l'avis en fonction de l'ID de l'utilisateur et de la voiture
        String query = "SELECT * FROM avis WHERE id = ? AND id_v = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idUser);
            stmt.setInt(2, idVoiture);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // L'avis existe, on peut le mettre à jour
                // Récupérer les anciennes valeurs
                int oldNote = rs.getInt("note");
                String oldCommentaire = rs.getString("commentaire");

                // Affichage pour débogage (ancien et nouveau)
                System.out.println("Ancienne note: " + oldNote);
                System.out.println("Ancien commentaire: " + oldCommentaire);

                // Initialiser les champs à vide
                note.clear();  // Vider le champ note
                commentaire.clear();  // Vider le champ commentaire

                // Garder les valeurs ID intactes
                iduser.setText(String.valueOf(idUser));  // Garder l'ID de l'utilisateur
                idvoiture.setText(String.valueOf(idVoiture));  // Garder l'ID de la voiture

                // Afficher les anciennes valeurs pour info
                note.setPromptText(String.valueOf(oldNote)); // Vous pouvez afficher la note initiale en tant que texte de l'invite
                commentaire.setPromptText(oldCommentaire);  // Même pour le commentaire initial

            } else {
                // Si aucun résultat n'est trouvé
                System.out.println("Aucun avis trouvé pour cet utilisateur (id: " + idUser + ") et cette voiture (id: " + idVoiture + ").");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'avis pour modification : " + e.getMessage());
        }

        // Vous n'avez pas besoin de faire la mise à jour tant que l'utilisateur n'a pas modifié 'note' ou 'commentaire'
        // Mais vous pouvez ajouter un contrôle pour cela, et si les champs sont remplis, vous effectuez la mise à jour

        // Appel de la méthode de mise à jour seulement si les nouveaux champs sont remplis
        modifier.setOnAction(event -> {
            try {
                // Si les champs sont remplis, faire la mise à jour
                if (!note.getText().isEmpty() && !commentaire.getText().isEmpty()) {
                    // Récupérer les nouvelles valeurs des champs
                    int noteValue = Integer.parseInt(note.getText());
                    String commentaireValue = commentaire.getText();
                    Date dateAvis = Date.valueOf(date.getValue()); // Nouvelle date

                    // Requête de mise à jour
                    String updateQuery = "UPDATE avis SET note = ?, commentaire = ?, dateavis = ? WHERE id = ? AND id_v = ?";

                    try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                        updateStmt.setInt(1, noteValue);
                        updateStmt.setString(2, commentaireValue);
                        updateStmt.setDate(3, dateAvis);
                        updateStmt.setInt(4, idUser);
                        updateStmt.setInt(5, idVoiture);

                        // Exécution de la mise à jour
                        int rowsAffected = updateStmt.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Avis mis à jour avec succès !");
                        } else {
                            System.out.println("Aucun avis n'a été mis à jour.");
                        }
                    }

                } else {
                    System.out.println("Veuillez remplir tous les champs avant de soumettre.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la mise à jour de l'avis : " + e.getMessage());
            }
        });

        showSuccessUpdateAvis();
    }





    private void deleteAvis() throws Exception {
        try {
            // Récupérer idUser et idVoiture à partir des champs de texte
            int idUser = Integer.parseInt(iduser.getText());  // Id de l'utilisateur
            int idVoiture = Integer.parseInt(idvoiture.getText());  // Id de la voiture associée

            // Requête SQL pour supprimer l'avis en fonction de idUser et idVoiture
            String query = "DELETE FROM avis WHERE id = ? AND id_v = ?";

            // Connexion à la base de données
            Connection connection = MyDb.getInstance().getConn();

            // Exécution de la requête de suppression
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, idUser);  // Set idUser
                stmt.setInt(2, idVoiture);  // Set idVoiture

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Avis de l'utilisateur avec idUser = " + idUser + " et idVoiture = " + idVoiture + " supprimé !");
                } else {
                    System.out.println("Aucun avis trouvé pour cet utilisateur et cette voiture.");
                }
            }

        } catch (NumberFormatException e) {
            // Si l'utilisateur entre une valeur non valide (non un entier)
            System.out.println("Entrée invalide : assurez-vous que idUser et idVoiture sont des entiers.");
        } catch (SQLException e) {
            // Gestion des erreurs SQL
            System.out.println("Erreur lors de la suppression de l'avis : " + e.getMessage());
        } catch (Exception e) {
            // Gestion générique des erreurs
            System.out.println("Erreur inconnue : " + e.getMessage());
        }
        showSuccessDeleteAvis();
    }






}
