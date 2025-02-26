package websocket;

import org.glassfish.tyrus.server.Server;

public class WebSocketLauncher {
    private Server server;

    public WebSocketLauncher() {
        this.server = new Server("localhost", 8081, "/chat", null, WebSocketServer.class);
    }

    public void start() {
        try {
            server.start();
            System.out.println("✅ Serveur WebSocket démarré sur ws://localhost:8081/chat");
            System.out.println("Appuyez sur Entrée pour arrêter le serveur...");
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    public void stop() {
        if (server != null) {
            server.stop();
            System.out.println("🛑 Serveur WebSocket arrêté.");
        }
    }

    public static void main(String[] args) {
        new WebSocketLauncher().start();
    }
}
