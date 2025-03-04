package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import services.GeminiClient;

import java.io.IOException;

public class Chatbot {
    @FXML private TextArea inputField;
    @FXML private TextArea outputField;
    @FXML private Button generateButton;

    private final GeminiClient geminiClient = new GeminiClient();

    @FXML
    public void initialize() {
        generateButton.setOnAction(event -> generateResponse());
    }

    private void generateResponse() {
        String prompt = inputField.getText().trim();
        if (prompt.isEmpty()) {
            outputField.appendText("User: (No input provided)\n");
            return;
        }

        outputField.appendText("User: " + prompt + "\n");

        try {
            String response = geminiClient.generateText(prompt);
            outputField.appendText("Bot: " + response + "\n\n");
        } catch (IOException e) {
            outputField.appendText("Bot: Error: " + e.getMessage() + "\n");
        }

        inputField.clear();
    }

}