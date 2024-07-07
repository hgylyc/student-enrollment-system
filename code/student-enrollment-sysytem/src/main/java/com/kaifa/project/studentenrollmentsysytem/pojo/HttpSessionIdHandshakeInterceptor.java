package com.kaifa.project.studentenrollmentsysytem.pojo;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.server.HandshakeInterceptor;
import java.util.Map;

public class HttpSessionIdHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            HttpSession session = servletRequest.getSession();
            System.out.println(session.getAttribute("shakehands"));
            System.out.println(session.getAttribute("username"));
            System.out.println(session.getAttribute("role"));
            String userId = (String) session.getAttribute("username"); // 获取用户 ID
            String role = (String) session.getAttribute("role"); // 获取用户角色
            attributes.put("userId", userId); // 将用户 ID 添加到 WebSocket 会话的属性中
            attributes.put("role", role); // 将角色添加到 WebSocket 会话的属性中
            //attributes.put("userId", "20221409"); // 将用户 ID 添加到 WebSocket 会话的属性中
            //attributes.put("role", "student"); // 将角色添加到 WebSocket 会话的属性中
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    }
}