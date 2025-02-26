package websocket;

import javax.websocket.*;
import java.net.URI;
import java.util.function.Consumer;

@ClientEndpoint
public class WebSocketClient {
    private Session session;
    private Consumer<String> messageHandler;

    public WebSocketClient(String uri, Consumer<String> messageHandler) {
        this.messageHandler = messageHandler;
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("✅ Connecté au serveur WebSocket.");
    }

    @OnMessage
    public void onMessage(String message) {
        if (messageHandler != null) {
            messageHandler.accept(message);
        }
    }

    public void sendMessage(String message) {
        if (session != null && session.isOpen()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
