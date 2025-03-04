package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import utils.MyDb;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import okhttp3.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class addreclamation_controller {

    private int userId; // ID dynamique de l'utilisateur récupéré depuis la table user
    private String userRole; // Rôle de l'utilisateur (admin, client, mécanicien)
    private String username; // Nom d'utilisateur pour identification
    private static final int TITRE_MAX_LENGTH = 100;
    private static final int CONTENU_MIN_LENGTH = 10;
    private static final int CONTENU_MAX_LENGTH = 1000;
    private profileController dashboardController;

    public void setDashboardController(profileController dashboardController) {
        this.dashboardController = dashboardController;
    }

    @FXML
    private TextField objetTextField;
    @FXML
    private TextArea reclamationTextArea;
    @FXML
    private Label erreurObjet;
    @FXML
    private Label erreurReclamation;
    @FXML
    private Button correctButton;

    // Éléments pour la messagerie
    @FXML private TextArea messageInput;
    @FXML private ListView<String> messageList;
    @FXML private TextArea responseDisplay; // Pour afficher les réponses de l’admin

    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private WebSocketClient webSocketClient;
    private int currentReclamationId = -1; // Stocke l’ID de la réclamation initiale
    private String initialTitre = ""; // Stocke le titre initial pour le rendre fixe

    @FXML
    public void initialize() {
        // Récupérer dynamiquement l'ID de l'utilisateur connecté via loginuserController
        loadUserInfo();

        // Initialiser la connexion WebSocket pour le client
        connectWebSocketForChat();

        // Ajouter un écouteur pour vérifier si le champ "Objet" est vide en temps réel
        objetTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().isEmpty() && currentReclamationId == -1) { // Vérifier uniquement avant l’envoi
                erreurObjet.setText("Ce champ est obligatoire");
                objetTextField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            } else {
                erreurObjet.setText("");
                objetTextField.setStyle("");
                if (newValue.length() > TITRE_MAX_LENGTH) {
                    erreurObjet.setText("Maximum " + TITRE_MAX_LENGTH + " caractères");
                    objetTextField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                } else if (!newValue.matches("^[a-zA-Z0-9 àâäéèêëîïôöùûüç,.'!?-]+$")) {
                    erreurObjet.setText("Caractères spéciaux non autorisés");
                    objetTextField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                }
            }
        });

        // Ajouter un écouteur pour vérifier si le champ "Réclamation" est vide en temps réel
        reclamationTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().isEmpty() && currentReclamationId == -1) { // Vérifier uniquement avant l’envoi
                erreurReclamation.setText("Ce champ est obligatoire");
                reclamationTextArea.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            } else {
                erreurReclamation.setText("");
                reclamationTextArea.setStyle("");
                if (newValue.length() < CONTENU_MIN_LENGTH) {
                    erreurReclamation.setText("Minimum " + CONTENU_MIN_LENGTH + " caractères");
                    reclamationTextArea.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                } else if (newValue.length() > CONTENU_MAX_LENGTH) {
                    erreurReclamation.setText("Maximum " + CONTENU_MAX_LENGTH + " caractères");
                    reclamationTextArea.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                }
            }
        });
    }

    // Méthode pour récupérer dynamiquement les informations de l'utilisateur depuis la table user
    private void loadUserInfo() {
        // Récupérer l'ID de l'utilisateur connecté via loginuserController
        int loggedInId = loginuserController.loggedInUserID ; // Appel à la méthode statique pour obtenir l'ID

        String sql = "SELECT id, role, username FROM user WHERE id = ?"; // Utiliser l'ID pour récupérer les informations

        try (Connection conn = MyDb.getInstance().getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, loggedInId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("id");
                userRole = rs.getString("role");
                username = rs.getString("username"); // Récupérer le username depuis la base de données
                System.out.println("Utilisateur connecté - ID: " + userId + ", Rôle: " + userRole + ", Username: " + username);
            } else {
                showError("Erreur", "Utilisateur non trouvé dans la base de données.");
                userId = -1; // Valeur par défaut si l'utilisateur n'est pas trouvé
            }
        } catch (SQLException e) {
            showError("Erreur", "Erreur lors de la récupération des informations utilisateur : " + e.getMessage());
            userId = -1;
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEnvoyerReclamation() {
        clearErrors();

        if (userId == -1) {
            showError("Erreur", "Utilisateur non identifié. Veuillez vous connecter.");
            return;
        }

        String titre = objetTextField.getText().trim();
        String contenu = reclamationTextArea.getText().trim();
        boolean isValid = true;

        // Vérification stricte pour le champ "Objet" (titre)
        if (titre.isEmpty()) {
            erreurObjet.setText("Ce champ est obligatoire");
            objetTextField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            isValid = false;
        } else {
            objetTextField.setStyle("");
            if (titre.length() > TITRE_MAX_LENGTH) {
                erreurObjet.setText("Maximum " + TITRE_MAX_LENGTH + " caractères");
                isValid = false;
            } else if (!titre.matches("^[a-zA-Z0-9 àâäéèêëîïôöùûüç,.'!?-]+$")) {
                erreurObjet.setText("Caractères spéciaux non autorisés");
                isValid = false;
            }
        }

        // Vérification pour le champ "Réclamation"
        if (contenu.isEmpty()) {
            erreurReclamation.setText("Ce champ est obligatoire");
            reclamationTextArea.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            isValid = false;
        } else {
            reclamationTextArea.setStyle("");
            if (contenu.length() < CONTENU_MIN_LENGTH) {
                erreurReclamation.setText("Minimum " + CONTENU_MIN_LENGTH + " caractères");
                isValid = false;
            } else if (contenu.length() > CONTENU_MAX_LENGTH) {
                erreurReclamation.setText("Maximum " + CONTENU_MAX_LENGTH + " caractères");
                isValid = false;
            }
        }

        if (isValid) {
            // Stocker le titre initial avant l’envoi
            initialTitre = titre;

            // Insérer la réclamation dans la base de données avec l'ID dynamique de l'utilisateur
            int idRec = insererReclamation(titre, contenu);

            // Stocker l’ID de la réclamation initiale
            currentReclamationId = idRec;

            // Rendre uniquement le champ "Objet" fixe, rempli avec sa valeur initiale, et non modifiable après l’envoi
            if (idRec != -1) {
                objetTextField.setDisable(true);
                objetTextField.setText(initialTitre); // Assurer que le titre reste rempli avec sa valeur initiale
                objetTextField.setStyle("-fx-opacity: 1; -fx-background-color: #f0f0f0;"); // Style pour indiquer qu’il est verrouillé
            }

            // Afficher uniquement le contenu avec le préfixe "Client :"
            if (webSocketClient != null && webSocketClient.isOpen()) {
                String message = "Client : " + contenu;
                webSocketClient.send("[Client] Réclamation - ID: " + idRec + ", Titre: " + titre + ", Contenu: " + contenu + ", Utilisateur: " + username);
                messageList.getItems().add(message); // Afficher uniquement "Client : [contenu]"
                responseDisplay.appendText(message + "\n"); // Afficher dans le TextArea avec le même format
                System.out.println("Réclamation envoyée par le client: " + message);
            } else {
                showError("Erreur", "Connexion WebSocket non établie pour envoyer la réclamation");
            }
        }
    }

    @FXML
    private void handleCorrectReclamation() {
        String titre = objetTextField.getText().trim();
        String contenu = reclamationTextArea.getText().trim();

        if (titre.isEmpty() && contenu.isEmpty()) {
            showAlert("Erreur", "Veuillez entrer un titre ou une réclamation avant de corriger.", Alert.AlertType.WARNING);
            return;
        }

        if (!titre.isEmpty() && currentReclamationId == -1) { // Permettre la correction uniquement avant l’envoi
            correctWithGemini(titre, true);
        }
        if (!contenu.isEmpty()) { // Permettre la correction à tout moment pour le contenu
            correctWithGemini(contenu, false);
        }
    }

    private void correctWithGemini(String text, boolean isTitle) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=AIzaSyBovR0DquNLAyKmSjnYxN6BD1FgxckdHPU";

        String prompt = isTitle
                ? "Corrigez uniquement ce titre (max 100 caractères) : " + text
                : "Corrigez uniquement ce texte pour une réclamation (max 1000 caractères) : " + text;

        java.util.Map<String, Object> messagePart = new java.util.HashMap<>();
        messagePart.put("text", prompt);

        java.util.Map<String, Object> contentPart = new java.util.HashMap<>();
        contentPart.put("parts", new Object[]{messagePart});

        java.util.Map<String, Object> requestBody = new java.util.HashMap<>();
        requestBody.put("contents", new Object[]{contentPart});

        try {
            String jsonPayload = objectMapper.writeValueAsString(requestBody);

            RequestBody body = RequestBody.create(jsonPayload, MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    JsonNode rootNode = objectMapper.readTree(responseBody);
                    String correctedText = rootNode.path("candidates")
                            .get(0)
                            .path("content")
                            .path("parts")
                            .get(0)
                            .path("text")
                            .asText();

                    if (isTitle && currentReclamationId == -1) { // Permettre la correction uniquement avant l’envoi pour le titre
                        objetTextField.setText(correctedText);
                    } else { // Permettre la correction à tout moment pour le contenu
                        reclamationTextArea.setText(correctedText);
                    }
                } else {
                    showAlert("Erreur", "Échec de la correction : " + response.code(), Alert.AlertType.ERROR);
                    System.out.println("Request failed: " + response.code());
                }
            }
        } catch (IOException e) {
            showAlert("Erreur", "Erreur technique lors de la correction : " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private int insererReclamation(String titre, String contenu) {
        String sql = "INSERT INTO reclamation (titre, contenu, id, datecreation) VALUES (?, ?, ?, NOW())";
        int generatedId = -1;

        try (Connection conn = MyDb.getInstance().getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, titre);
            pstmt.setString(2, contenu);
            pstmt.setInt(3, userId); // Utilise l'ID dynamique de l'utilisateur

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1); // Récupérer l’ID généré (par exemple, id_rec)
                }
                showAlert("Succès", "Réclamation envoyée avec succès!", Alert.AlertType.INFORMATION);
                // Ne pas réinitialiser immédiatement pour garder les champs visibles
            }

        } catch (SQLException e) {
            showAlert("Erreur", "Erreur technique : " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
        return generatedId;
    }

    private void clearErrors() {
        erreurObjet.setText("");
        erreurReclamation.setText("");
        objetTextField.setStyle("");
        reclamationTextArea.setStyle("");
    }

    private void clearFields() {
        objetTextField.setText(""); // Réinitialiser le champ "Objet"
        reclamationTextArea.clear();
        clearErrors();
        currentReclamationId = -1; // Réinitialiser l’ID après effacement
        initialTitre = ""; // Réinitialiser le titre initial
        objetTextField.setDisable(false); // Réactiver le champ "Objet" pour une nouvelle saisie
        objetTextField.setStyle(""); // Réinitialiser le style
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Platform.runLater(() -> {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    // Méthodes pour la messagerie simple avec Java-WebSocket
    private void connectWebSocketForChat() {
        try {
            webSocketClient = new WebSocketClient(new URI("ws://localhost:8081/client")) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Platform.runLater(() -> {
                        showInfo("Connexion WebSocket établie en tant que Client (Utilisateur: " + username + ")");
                        webSocketClient.send("Client connecté - Utilisateur: " + username);
                    });
                }

                @Override
                public void onMessage(String message) {
                    Platform.runLater(() -> {
                        if (message.startsWith("[Admin] Réponse - ")) {
                            String response = message.replace("[Admin] Réponse - ID: ", "").replace(", Réponse: ", ": ").replace("]", "");
                            int responseId = extractIdFromMessage(message); // Extraire l’ID de la réponse
                            messageList.getItems().add("Admin: " + response); // Afficher en format bulle (gris pour l’admin)
                            responseDisplay.setText("Admin: " + response); // Afficher dans le TextArea
                            System.out.println("Réponse reçue par le client: " + message);
                        } else if (message.startsWith("[Admin] ")) {
                            // Réponses simples de l’admin (avec l’ID de la réclamation initiale)
                            String response = message.replace("[Admin] ", "");
                            int responseId = extractIdFromMessage(response); // Extraire l’ID si présent
                            if (responseId != -1) {
                                messageList.getItems().add("Admin: " + response); // Afficher en format bulle (gris pour l’admin)
                                responseDisplay.appendText("Admin: " + response + "\n"); // Ajouter au TextArea
                                System.out.println("Message simple reçu par le client: " + message);
                            }
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
            System.out.println("Client WebSocket connecté à ws://localhost:8081/client");
        } catch (URISyntaxException e) {
            showError("Erreur", "URL WebSocket invalide: " + e.getMessage());
            System.out.println("Erreur de connexion WebSocket: " + e.getMessage());
        }
    }

    @FXML
    private void sendChatMessage() {
        if (webSocketClient != null && webSocketClient.isOpen() && messageInput != null) {
            String message = messageInput.getText().trim();
            if (!message.isEmpty() && currentReclamationId != -1) {
                String chatMessage = "Client : " + message;
                webSocketClient.send("Client: " + message + " (Réclamation ID: " + currentReclamationId + ", Utilisateur: " + username + ")");
                messageList.getItems().add(chatMessage); // Afficher uniquement "Client : [contenu]"
                responseDisplay.appendText(chatMessage + "\n"); // Ajouter au TextArea avec le même format
                messageInput.clear();
            } else {
                showError("Erreur", "Aucune réclamation initiale n’a été envoyée. Envoyez une réclamation d’abord.");
            }
        } else {
            showError("Erreur", "Connexion WebSocket non établie");
        }
    }

    private int extractIdFromMessage(String message) {
        // Extraire l’ID de la réclamation à partir du message, par exemple "[Admin] Réponse - ID: 126, Réponse: ..." ou "Client: message (Réclamation ID: 126)"
        int idStart = message.indexOf("Réclamation ID: ") + 14;
        if (idStart > 13) {
            int idEnd = message.indexOf(")", idStart);
            if (idEnd > idStart) {
                try {
                    return Integer.parseInt(message.substring(idStart, idEnd).trim());
                } catch (NumberFormatException e) {
                    System.out.println("Erreur d’extraction de l’ID : " + e.getMessage());
                    return -1;
                }
            }
        }
        // Si aucun ID trouvé dans ce format, vérifier le format des réclamations/réponses
        idStart = message.indexOf("ID: ") + 4;
        int idEnd = message.indexOf(",", idStart);
        if (idStart > 3 && idEnd > idStart) {
            try {
                return Integer.parseInt(message.substring(idStart, idEnd).trim());
            } catch (NumberFormatException e) {
                System.out.println("Erreur d’extraction de l’ID : " + e.getMessage());
                return -1;
            }
        }
        return -1;
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
}