package websocket;

import org.glassfish.tyrus.server.Server;

public class WebSocketLauncher {
    private Server server;

    public WebSocketLauncher() {
        // Démarrer le serveur WebSocket sur ws://localhost:8081/chat
        this.server = new Server("localhost", 8081, "/chat", null, WebSocketServer.class);
    }

    public void start() {
        try {
            server.start();
            System.out.println("✅ Serveur WebSocket démarré sur ws://localhost:8081/chat");
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du démarrage du WebSocket Server : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void stop() {
        if (server != null) {
            server.stop();
            System.out.println("🛑 Serveur WebSocket arrêté.");
        }
    }

    public static void main(String[] args) {
        WebSocketLauncher launcher = new WebSocketLauncher();
        launcher.start();
    }
}
