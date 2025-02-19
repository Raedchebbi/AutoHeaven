package websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/chat")
public class WebSocketServer {
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("🔵 Nouvelle connexion WebSocket : " + session.getId());
        sendMessageToAll("👤 Un nouvel utilisateur a rejoint le chat !");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("📩 Message reçu de " + session.getId() + ": " + message);
        sendMessageToAll("💬 " + message);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("🔴 Connexion fermée : " + session.getId());
        sendMessageToAll("👤 Un utilisateur a quitté le chat.");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("⚠️ Erreur WebSocket avec la session " + session.getId() + ": " + throwable.getMessage());
    }

    private void sendMessageToAll(String message) {
        synchronized (sessions) {
            for (Session session : sessions) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
