package controllers;


import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.google.gson.JsonParser;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Chabot {
    @FXML
    private TextArea chatArea;
    @FXML
    private VBox chatBox;

    @FXML
    private ScrollPane chatScrollPane;


    @FXML
    private TextField inputField;

    @FXML
    private void sendMessage() {
        String userMessage = inputField.getText();
        if (!userMessage.isEmpty()) {
            addMessage(userMessage, "user-message");
            inputField.clear();

            // Simuler une réponse du bot (Remplace cette ligne par l'appel réel à Gemini)
            String botResponse = getGeminiResponse(userMessage);
            addMessage(botResponse, "bot-message");
        }
    }

    private void addMessage(String text, String styleClass) {
        HBox messageContainer = new HBox();
        messageContainer.setSpacing(10);

        Label messageLabel = new Label(text);
        messageLabel.getStyleClass().add(styleClass);
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(300); // Pour éviter que le texte soit trop large

        if (styleClass.equals("user-message")) {
            messageContainer.getChildren().add(messageLabel);
            messageContainer.setAlignment(Pos.CENTER_RIGHT);
        } else {
            messageContainer.getChildren().add(messageLabel);
            messageContainer.setAlignment(Pos.CENTER_LEFT);
        }

        chatBox.getChildren().add(messageContainer);

        // Scroll vers le bas après l'ajout du message
        chatScrollPane.vvalueProperty().bind(chatBox.heightProperty());
    }

    /*private String getGeminiResponse(String userMessage) {
        // Remplacez par votre clé API Gemini
        String apiKey = "AIzaSyAVNeAswc-Fp84gAB6flKY__lv-b1VpuN4";
        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey;

        try {
            // Créer la requête HTTP
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Corps de la requête
            String jsonInputString = "{\"contents\":[{\"parts\":[{\"text\":\"" + userMessage + "\"}]}]}";
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Lire la réponse
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                // Extraire la réponse du chatbot
                String jsonResponse = response.toString();
                // Parsez la réponse JSON pour extraire le texte généré
                // Exemple simple (vous devrez peut-être utiliser une bibliothèque JSON comme Gson ou Jackson)
                String botResponse = jsonResponse.split("\"text\":\"")[1].split("\"")[0];
                return botResponse;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Désolé, une erreur s'est produite.";
        }
    }*/
    private String getGeminiResponse(String userMessage) {
        // Remplacez par votre clé API Gemini
        String apiKey = "AIzaSyAVNeAswc-Fp84gAB6flKY__lv-b1VpuN4";
        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + apiKey;

        try {
            // Créer la requête HTTP
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Corps de la requête
            String jsonInputString = "{\"contents\":[{\"parts\":[{\"text\":\"" + userMessage + "\"}]}]}";
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Lire la réponse
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                // Afficher la réponse JSON pour le débogage
                String jsonResponse = response.toString();
                System.out.println("Réponse JSON reçue : " + jsonResponse);

                // Analyser la réponse JSON avec Gson
                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
                JsonArray candidates = jsonObject.getAsJsonArray("candidates");
                if (candidates != null && candidates.size() > 0) {
                    JsonObject firstCandidate = candidates.get(0).getAsJsonObject();
                    JsonObject content = firstCandidate.getAsJsonObject("content");
                    JsonArray parts = content.getAsJsonArray("parts");
                    if (parts != null && parts.size() > 0) {
                        JsonObject firstPart = parts.get(0).getAsJsonObject();
                        return firstPart.get("text").getAsString();
                    }
                }
                return "Désolé, je n'ai pas pu comprendre la réponse.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Désolé, une erreur s'est produite.";
        }
    }
}
