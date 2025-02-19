package websocket;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.OnClose;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.ContainerProvider;
import java.net.URI;

@ClientEndpoint
public class WebSocketClient {
    private Session session;
    private MessageListener messageListener;

    public WebSocketClient(String uri, MessageListener listener) {
        this.messageListener = listener;
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
        System.out.println("Connecté au serveur WebSocket");
    }

    @OnMessage
    public void onMessage(String message) {
        if (messageListener != null) {
            messageListener.onMessageReceived(message);
        }
    }

    @OnClose
    public void onClose() {
        System.out.println("Connexion WebSocket fermée");
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

    public interface MessageListener {
        void onMessageReceived(String message);
    }
}
