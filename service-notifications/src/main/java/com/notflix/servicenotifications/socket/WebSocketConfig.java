package com.notflix.servicenotifications.socket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
  public static final MessageHandler messageHandler = new MessageHandler();
  public static final SocketInterceptor socketInterceptor = new SocketInterceptor();

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
    webSocketHandlerRegistry
      .addHandler(messageHandler, "/notification/socket")
      .addInterceptors(socketInterceptor)
      .setAllowedOrigins("*");
  }
}
