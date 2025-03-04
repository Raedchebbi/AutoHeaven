package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import utils.MyDb;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

public class repondre_rec_controller {

    @FXML
    private TextField reclamationTitre;
    @FXML
    private TextArea reclamationContent;
    @FXML
    private TextArea responseTextArea;
    @FXML private ListView<String> messageList; // Pour la messagerie
    @FXML private TextArea responseDisplay; // Pour afficher les réclamations et réponses

    private int reclamationId;
    private Runnable onSuccessCallback;
    private WebSocketClient webSocketClient; // Pour la messagerie en temps réel

    public void setReclamationId(int idRec) {
        this.reclamationId = idRec;
        System.out.println("Réclamation ID défini pour l’admin: " + idRec); // Ajout pour débogage
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
    public void initialize() {
        // Initialiser la connexion WebSocket pour l'administrateur
        connectWebSocketForChat();
    }

    @FXML
    private void handleEnvoyerReponse() {
        if (validateInput()) {
            String response = responseTextArea.getText().trim();
            if (!response.isEmpty()) {
                // Envoyer via WebSocket (messagerie simple)
                if (webSocketClient != null && webSocketClient.isOpen()) {
                    String message = "[Admin] Réponse - ID: " + reclamationId + ", Réponse: " + response;
                    webSocketClient.send(message); // Envoyer la réponse complète avec ID
                    messageList.getItems().add("Vous: " + response); // Afficher en format bulle (gris pour l’admin)
                    responseDisplay.appendText("Votre réponse (ID " + reclamationId + "): " + response + "\n"); // Afficher dans le TextArea
                    System.out.println("Réponse envoyée par l’admin: " + message);
                    // Envoyer aussi un message simple pour la discussion continue
                    webSocketClient.send("Admin: " + response); // Message simple pour la discussion
                } else {
                    showError("Erreur", "Connexion WebSocket non établie pour l'envoi du message");
                }

                // Conserver les fonctionnalités existantes (persistance), mais ne pas mettre à jour le statut ici
                if (saveMessageToDatabase(response)) {
                    showSuccessAlert();
                }
                responseTextArea.clear();
            }
        }
    }

    @FXML
    public void sendMessage(ActionEvent actionEvent) {
        if (webSocketClient != null && webSocketClient.isOpen() && responseDisplay != null) { // Utilisation de responseDisplay comme champ de saisie
            String message = responseDisplay.getText().trim();
            if (!message.isEmpty()) {
                webSocketClient.send("Admin: " + message); // Envoyer un message simple sans ID
                messageList.getItems().add("Vous: " + message); // Afficher en format bulle (gris pour l’admin)
                responseDisplay.clear(); // Effacer le champ après envoi
            }
        } else {
            showError("Erreur", "Connexion WebSocket non établie");
        }
    }

    // Nouvelle méthode pour fermer manuellement le formulaire et mettre à jour le statut
    @FXML
    private void closeForm() {
        if (onSuccessCallback != null) {
            // Mettre à jour le statut de la réclamation à "repondu" dans la base de données avant de fermer
            if (updateStatusInDatabase()) {
                System.out.println("Statut de la réclamation " + reclamationId + " mis à jour à 'repondu'");
            } else {
                showError("Erreur", "Échec de la mise à jour du statut de la réclamation " + reclamationId);
            }
            onSuccessCallback.run(); // Fermer le formulaire et actualiser AfficherReclamationController
        }
    }

    private boolean validateInput() {
        if (responseTextArea.getText().trim().isEmpty()) {
            showAlert("Erreur", "Le champ réponse est obligatoire");
            return false;
        }
        return true;
    }

    private boolean saveMessageToDatabase(String message) {
        String query = "INSERT INTO messagerie (message, sender, id_rec, datemessage, receiver) VALUES (?, ?, ?, NOW(), ?)";

        try (Connection conn = MyDb.getInstance().getConn();
             PreparedStatement ps = conn.prepareStatement(query)) {

            if (conn == null || conn.isClosed()) {
                showAlert("Erreur SQL", "Connexion fermée. Impossible d'enregistrer.");
                return false;
            }

            ps.setString(1, message);
            ps.setString(2, "admin");
            ps.setInt(3, reclamationId);
            ps.setString(4, "client");

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            showAlert("Erreur SQL", "Échec de l'enregistrement du message : " + e.getMessage());
            return false;
        }
    }

    private boolean updateStatusInDatabase() {
        String query = "UPDATE reclamation SET status = 'repondu' WHERE id_rec = ?";

        try (Connection conn = MyDb.getInstance().getConn();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, reclamationId);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            showAlert("Erreur SQL", "Échec de la mise à jour du statut : " + e.getMessage());
            return false;
        }
    }

    private void showAlert(String title, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    public void showSuccessAlert() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText("Réponse envoyée avec succès !");
            alert.setContentText("Le formulaire reste ouvert pour continuer la discussion. Cliquez sur 'Fermer' pour quitter.");
            alert.showAndWait();
        });
    }

    private void showInfo(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    private void showError(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    // Méthodes pour la messagerie simple avec Java-WebSocket
    private void connectWebSocketForChat() {
        try {
            webSocketClient = new WebSocketClient(new URI("ws://localhost:8081/admin")) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Platform.runLater(() -> {
                        showInfo("Connexion WebSocket établie en tant qu'Admin");
                        webSocketClient.send("Admin connecté");
                    });
                }

                @Override
                public void onMessage(String message) {
                    Platform.runLater(() -> {
                        if (message.startsWith("[Client] Réclamation - ")) {
                            // Extraire les informations du message
                            String[] parts = message.split(", ");
                            String id = parts[0].replace("[Client] Réclamation - ID: ", "");
                            String titre = parts[1].replace("Titre: ", "");
                            String contenu = parts[2].replace("Contenu: ", "");

                            // Afficher le message dans la messagerie admin
                            messageList.getItems().add("Client: " + contenu);
                            responseDisplay.appendText("Client: " + contenu + "\n");
                            System.out.println("Réclamation reçue par l’admin: " + message);
                        }
                    });
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Platform.runLater(() -> showInfo("Connexion WebSocket fermée: " + reason));
                }

                @Override
                public void onError(Exception ex) {
                    Platform.runLater(() -> showError("Erreur WebSocket", ex.getMessage()));
                }
            };
            webSocketClient.connect();
            System.out.println("Admin WebSocket connecté à ws://localhost:8081/admin");
        } catch (URISyntaxException e) {
            showError("Erreur", "URL WebSocket invalide: " + e.getMessage());
            System.out.println("Erreur de connexion WebSocket: " + e.getMessage());
        }
    }
}