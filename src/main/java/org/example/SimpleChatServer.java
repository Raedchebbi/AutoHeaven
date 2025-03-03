package org.example;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

public class SimpleChatServer extends WebSocketServer {

    private final Set<WebSocket> clients = new HashSet<>();

    public SimpleChatServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        clients.add(conn);
        String user = conn.getRemoteSocketAddress().getAddress().getHostAddress();
        String role = handshake.getResourceDescriptor().equals("/client") ? "Client" : "Admin";
        conn.setAttachment(role); // Stocker le rôle dans l'attachment
        broadcastToAll("[" + role + " " + user + "] a rejoint la discussion");
        System.out.println("Nouvelle connexion: " + role + " - " + user + " (Resource: " + handshake.getResourceDescriptor() + ")");
    }

    private void broadcastToAll(String s) {
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        clients.remove(conn);
        String user = conn.getRemoteSocketAddress().getAddress().getHostAddress();
        String role = (String) conn.getAttachment(); // Récupérer le rôle stocké
        broadcastToAll("[" + role + " " + user + "] a quitté la discussion");
        System.out.println("Connexion fermée: " + role + " - " + user);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        String user = conn.getRemoteSocketAddress().getAddress().getHostAddress();
        String role = (String) conn.getAttachment(); // Récupérer le rôle stocké

        System.out.println("Message reçu de " + role + " (" + user + "): " + message);

        if (message.startsWith("[Client] Réclamation - ")) {
            broadcastToRole(conn, "Admin", message); // Diffuser la réclamation uniquement à l’admin
            System.out.println("Réclamation diffusée aux Admins: " + message);
        } else if (message.startsWith("[Admin] Réponse - ")) {
            broadcastToRole(conn, "Client", message); // Diffuser la réponse uniquement au client
            System.out.println("Réponse diffusée aux Clients: " + message);
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
        if (conn != null) {
            clients.remove(conn);
            System.out.println("Erreur de connexion: " + ex.getMessage());
        }
    }

    @Override
    public void onStart() {
        System.out.println("Serveur WebSocket démarré sur ws://localhost:8081");
    }

    public void broadcast(String message) {
        for (WebSocket client : clients) {
            client.send(message);
        }
    }

    private void broadcastToRole(WebSocket sender, String targetRole, String message) {
        int recipients = 0;
        for (WebSocket client : clients) {
            if (client != sender) {
                String role = (String) client.getAttachment();
                if (role != null && role.equals(targetRole)) {
                    client.send(message);
                    recipients++;
                    System.out.println("Message envoyé à " + targetRole + " (" + client.getRemoteSocketAddress().getAddress().getHostAddress() + "): " + message);
                }
            }
        }
        if (recipients == 0) {
            System.out.println("Aucun destinataire trouvé pour le rôle " + targetRole + " pour le message: " + message);
        }
    }

    public static void main(String[] args) {
        int port = 8081;
        SimpleChatServer server = new SimpleChatServer(new InetSocketAddress("localhost", port));
        server.start();
    }
}