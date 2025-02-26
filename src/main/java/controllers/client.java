package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import websocket.WebSocketClient;

public class client {
    @FXML
    private TextArea messageArea;
    @FXML
    private TextField inputField;

    private WebSocketClient webSocketClient;

    public void initialize() {
        webSocketClient = new WebSocketClient("ws://localhost:8081/chat", message -> {
            messageArea.appendText("Admin: " + message + "\n");
        });
    }

    @FXML
    private void sendMessage() {
        String message = inputField.getText();
        if (!message.isEmpty()) {
            webSocketClient.sendMessage("Client: " + message);
            messageArea.appendText("Moi: " + message + "\n");
            inputField.clear();
        }
    }
}
