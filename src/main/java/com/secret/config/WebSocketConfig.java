package com.secret.config;

import com.secret.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/**
 * @author Hai
 * @date 2020/6/16 - 23:31
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Value("${ws.allowedOrigins:*}")
  private String allowedOrigins;

  @Value("${ws.endpoint:/webSocketServer}")
  private String endpoint;
  /**
   * 注册stomp站点
   * @param registry
   */
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {

    registry.addEndpoint(endpoint).setAllowedOrigins("http://127.0.0.1:5501").withSockJS();

  }

  /**
   * 配置信息代理
   */
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    // 订阅Broker名称
    registry.enableSimpleBroker("/queue", "/topic");
    // 全局使用的消息前缀（客户端订阅路径上会体现出来）
    registry.setApplicationDestinationPrefixes("/app");
    // 点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
    registry.setUserDestinationPrefix("/user/");
  }

  /**
   * 配置客户端入站通道拦截器
   */
  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.setInterceptors(createUserInterceptor());

  }


  /*将客户端渠道拦截器加入spring ioc容器*/
  @Bean
  public UserInterceptor createUserInterceptor() {
    return new UserInterceptor();
  }


  @Override
  public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
    registration.setMessageSizeLimit(500 * 1024 * 1024);
    registration.setSendBufferSizeLimit(1024 * 1024 * 1024);
    registration.setSendTimeLimit(200000);
  }
}
