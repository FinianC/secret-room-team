package com.secret.interceptor;

import com.secret.model.dto.User;
import com.secret.utils.UserLoginUtils;
import com.secret.utils.WebSocketUser;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExtHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        String token = serverHttpRequest.getHeaders().get("sec-websocket-protocol").get(0);
        UserLoginUtils.getUserInfo(token);
        ConcurrentHashMap<String, User> concurrentHashMap = WebSocketUser.getConcurrentHashMap();
        concurrentHashMap.computeIfAbsent(token, key -> new User(token,null) );
        ((ServletServerHttpResponse) serverHttpResponse).getServletResponse().setHeader("Sec-WebSocket-Protocol",token);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
