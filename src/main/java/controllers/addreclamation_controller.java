package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import utils.MyDb;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import okhttp3.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class addreclamation_controller {

    private static final int USER_ID = 3;  // L'ID de l'utilisateur client
    private static final int TITRE_MAX_LENGTH = 100;
    private static final int CONTENU_MIN_LENGTH = 10;
    private static final int CONTENU_MAX_LENGTH = 1000;

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

    @FXML
    private void handleEnvoyerReclamation() {
        clearErrors();

        String titre = objetTextField.getText().trim();
        String contenu = reclamationTextArea.getText().trim();
        boolean isValid = true;

        if (titre.isEmpty()) {
            erreurObjet.setText("Ce champ est obligatoire");
            isValid = false;
        } else if (titre.length() > TITRE_MAX_LENGTH) {
            erreurObjet.setText("Maximum " + TITRE_MAX_LENGTH + " caractères");
            isValid = false;
        } else if (!titre.matches("^[a-zA-Z0-9 àâäéèêëîïôöùûüç,.'!?-]+$")) {
            erreurObjet.setText("Caractères spéciaux non autorisés");
            isValid = false;
        }

        if (contenu.isEmpty()) {
            erreurReclamation.setText("Ce champ est obligatoire");
            isValid = false;
        } else if (contenu.length() < CONTENU_MIN_LENGTH) {
            erreurReclamation.setText("Minimum " + CONTENU_MIN_LENGTH + " caractères");
            isValid = false;
        } else if (contenu.length() > CONTENU_MAX_LENGTH) {
            erreurReclamation.setText("Maximum " + CONTENU_MAX_LENGTH + " caractères");
            isValid = false;
        }

        if (isValid) {
            insererReclamation(titre, contenu); // Pas de traduction, seulement le texte original corrigé si nécessaire
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

        if (!titre.isEmpty()) {
            correctWithGemini(titre, true);
        }
        if (!contenu.isEmpty()) {
            correctWithGemini(contenu, false);
        }
    }

    private void correctWithGemini(String text, boolean isTitle) {
        OkHttpClient client = new OkHttpClient();
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
            ObjectMapper objectMapper = new ObjectMapper();
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

                    if (isTitle) {
                        objetTextField.setText(correctedText);
                    } else {
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

    private void insererReclamation(String titre, String contenu) {
        String sql = "INSERT INTO reclamation (titre, contenu, id) VALUES (?, ?, ?)";

        try (Connection conn = MyDb.getInstance().getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, titre);
            pstmt.setString(2, contenu);
            pstmt.setInt(3, USER_ID);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Succès", "Réclamation envoyée avec succès!", Alert.AlertType.INFORMATION);
                clearFields();
            }

        } catch (SQLException e) {
            showAlert("Erreur", "Erreur technique : " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void clearErrors() {
        erreurObjet.setText("");
        erreurReclamation.setText("");
    }

    private void clearFields() {
        objetTextField.clear();
        reclamationTextArea.clear();
        clearErrors();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}