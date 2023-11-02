package com.notflix.servicenotifications.socket;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.google.gson.Gson;

public class SocketInterceptor implements HandshakeInterceptor {
  Gson gson = new Gson();

  @Override
  public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
    String token = request.getURI().getQuery();

    // System.out.println("Token: '" + token + "'");

    String URL = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + token;
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL, String.class);

    GoogleUserInfo user = gson.fromJson(responseEntity.getBody(), GoogleUserInfo.class);

    // System.out.println("User: " + user.email);

    attributes.put("email", user.email);

    return true;
  }

  @Override
  public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception arg3) {
  }
}
