package com.secret.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author Hai
 * @date 2020/6/16 - 23:31
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Value("${ws.allowedOrigins:*}")
  private String allowedOrigins;

  @Value("${ws.endpoint:/ws/secret}")
  private String endpoint;
  /**
   * 注册stomp站点
   * @param registry
   */
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {

    registry.addEndpoint(endpoint).setAllowedOrigins(allowedOrigins).withSockJS();

  }

  /**
   * 注册拦截"/topic","/queue"的消息
   * @param registry
   */
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker("/topic");

  }
}
