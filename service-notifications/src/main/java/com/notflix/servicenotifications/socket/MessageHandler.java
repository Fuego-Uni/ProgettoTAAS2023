package com.notflix.servicenotifications.socket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;

public class MessageHandler extends TextWebSocketHandler {
  private static final Gson gson = new Gson();

  List<WebSocketSession> webSocketSessions = Collections.synchronizedList(new ArrayList<WebSocketSession>());

  Map<String, WebSocketSession> UsernameToSocket = Collections.synchronizedMap(new HashMap<String, WebSocketSession>());
  Map<WebSocketSession, String> SocketToUsername = Collections.synchronizedMap(new HashMap<WebSocketSession, String>());

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    System.out.println("Connection established: " + session.getId());
    super.afterConnectionEstablished(session);
    webSocketSessions.add(session);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    System.out.println("Connection closed: " + session.getId());
    super.afterConnectionClosed(session, status);

    String username = SocketToUsername.get(session);

    if(username != null) { UsernameToSocket.remove(username); }
    SocketToUsername.remove(session);

    webSocketSessions.remove(session);
  }

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> _message) throws Exception {
    super.handleMessage(session, _message);

    System.out.println("Message received: " + _message.getPayload());

    SocketMessage message = gson.fromJson((String) _message.getPayload(), SocketMessage.class);

    switch (message.message) {
      case "register-client":
        assignUsername(session, message.data);
        break;
    
      default:
        break;
    }

    System.out.println("Message received: " + message);
  }

  public void sendMessageToUsers(String username, SocketMessage message) {
    WebSocketSession session = UsernameToSocket.get(username);

    if(session != null) {
      try {
        session.sendMessage(new org.springframework.web.socket.TextMessage(gson.toJson(message)));
      } catch (Exception e) {
        System.out.println("Error sending message to user: " + e.getMessage());
      }
    }
  }

  public void sendMessageToUsers(String[] usernames, SocketMessage message) {
    for (String username : usernames) {
      sendMessageToUsers(username, message);
    }
  }

  public void sendMessageToAll(SocketMessage message) {
    for (WebSocketSession session : webSocketSessions) {
      try {
        session.sendMessage(new org.springframework.web.socket.TextMessage(gson.toJson(message)));
      } catch (Exception e) {
        System.out.println("Error sending message to user: " + e.getMessage());
      }
    }
  }

  private void assignUsername(WebSocketSession session, String username) {
     System.out.println("Assigning username: " + username);
    
    UsernameToSocket.put(username, session);
    SocketToUsername.put(session, username);
  }
}
