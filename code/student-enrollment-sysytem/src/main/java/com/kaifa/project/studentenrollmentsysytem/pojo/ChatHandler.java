package com.kaifa.project.studentenrollmentsysytem.pojo;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

public class ChatHandler extends TextWebSocketHandler {

    private Map<String, WebSocketSession> sessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 从 WebSocketSession 的属性中获取用户 ID
        String userId = (String) session.getAttributes().get("userId");
        if (userId != null) {
            sessions.put(userId, session);
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 解析消息，假设消息格式为 "username:message"
        String[] parts = message.getPayload().split(":", 3);
        String userId = (String) session.getAttributes().get("userId");
        if (parts.length == 3 && "to".equals(parts[0])) {
            String toUser = parts[1];
            String msg = userId + ":" + parts[2];
            WebSocketSession toSession = sessions.get(toUser);
            if (toSession != null && toSession.isOpen()) {
                toSession.sendMessage(new TextMessage(msg));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 移除断开的会话
        sessions.values().remove(session);
    }
}

