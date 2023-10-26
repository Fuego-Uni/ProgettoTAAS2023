package com.notflix.servicegateway.messages;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;

@Configuration
public class RabbitMessageSender {
  private static final Gson gson = new Gson();

  @Autowired RabbitTemplate template;
  
  // ==================== auth service ====================
//  public ApiResponse authServiceLogin(String username, String password) {
//    AuthData authData = new AuthData(username, password);
//
//    Object reply = this.template.convertSendAndReceive(Config.multichatExchange().getName(), "auth.login", gson.toJson(authData));
//
//    return gson.fromJson((String) reply, ApiResponse.class);
//  }
  
  // ==================== notification service ====================
  /**
   * Send a notification to the notification service
   * @param route The route to send the notification to (e.g. "group.create")
   * @param users The users to send the notification to
   * @param data The data to send with the notification
   */
//  public void sendNotification(String route, String[] users, String data) {
//    NotificationMessage notificationMessage = new NotificationMessage(users, data);
//
//    if(route == null || route.isEmpty()) route = "default";
//
//    String routingKey = "notification." + route;
//
//    System.out.println("Sending notification to " + routingKey);
//
//    this.template.convertAndSend(Config.multichatExchange().getName(), routingKey, gson.toJson(notificationMessage));
//  }

  // ==================== group service ====================
//  public String[] getGroupUsers(Long groupid) {
//    Object reply = this.template.convertSendAndReceive(Config.multichatExchange().getName(), "group.info.users", groupid);
//
//    return gson.fromJson((String) reply, String[].class);
//  }
}