package tn.esprit.reclamations.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import tn.esprit.reclamations.websocket.ReclamationWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ReclamationWebSocketHandler reclamationWebSocketHandler;

    public WebSocketConfig(ReclamationWebSocketHandler reclamationWebSocketHandler) {
        this.reclamationWebSocketHandler = reclamationWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(reclamationWebSocketHandler, "/ws/reclamations")
                .setAllowedOrigins("*");
    }
}
