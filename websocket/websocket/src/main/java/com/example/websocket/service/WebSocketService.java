package com.example.websocket.service;

import com.example.websocket.model.Email;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Service
public class WebSocketService extends TextWebSocketHandler {

    private WebSocketSession session;

    public void sendEmailStatusNotification(Email email, String statusMessage) {
        if (session != null && session.isOpen()) {
            try {
                String message = "Email to " + email.getRecipients() + " status: " + statusMessage;
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.session = session;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        this.session = null;
    }
}
