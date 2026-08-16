package tn.esprit.reclamations.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class ReclamationWebSocketHandler extends TextWebSocketHandler {

    private final ConcurrentHashMap<String, WebSocketSession> clientSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String clientId = extractClientId(session);
        if (clientId != null) {
            clientSessions.put(clientId, session);
            System.out.println("🔌 Client connecté: " + clientId);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String clientId = extractClientId(session);
        if (clientId != null) {
            clientSessions.remove(clientId);
            System.out.println("🔌 Client déconnecté: " + clientId);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("📩 Message reçu: " + payload);
    }

    public void notifyClient(String clientId, String message) {
        WebSocketSession session = clientSessions.get(clientId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
                System.out.println("📤 Notification envoyée au client " + clientId);
            } catch (Exception e) {
                System.err.println("❌ Erreur envoi notification: " + e.getMessage());
            }
        }
    }

    public void notifyAllClients(String message) {
        clientSessions.forEach((clientId, session) -> {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                    System.out.println("📤 Notification envoyée à tous les clients");
                } catch (Exception e) {
                    System.err.println("❌ Erreur envoi notification: " + e.getMessage());
                }
            }
        });
    }

    private String extractClientId(WebSocketSession session) {
        String uri = session.getUri().toString();
        if (uri.contains("clientId=")) {
            return uri.substring(uri.indexOf("clientId=") + 9);
        }
        return null;
    }
}
