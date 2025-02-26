package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import websocket.WebSocketClient;

public class admin {
    @FXML
    private TextArea messageArea;
    @FXML
    private TextField inputField;

    private WebSocketClient webSocketClient;

    public void initialize() {
        webSocketClient = new WebSocketClient("ws://localhost:8081/chat", message -> {
            messageArea.appendText("Client: " + message + "\n");
        });
    }

    @FXML
    private void sendMessage() {
        String message = inputField.getText();
        if (!message.isEmpty()) {
            webSocketClient.sendMessage("Admin: " + message);
            messageArea.appendText("Moi: " + message + "\n");
            inputField.clear();
        }
    }
}
