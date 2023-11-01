package com.notflix.servicegateway.messages;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;

@Configuration
public class RabbitMessageSender {
  private static final Gson gson = new Gson();

  @Autowired RabbitTemplate template;

  /**
   * Send a notification to the notification service
   * @param route The route to send the notification to (e.g. "group.create")
   */
  public void sendNotification(String route, String message, String data, String... users) {
    NotificationMessage notificationMessage = new NotificationMessage(users, message, data);

    if(route == null || route.isEmpty()) route = "default";

    String routingKey = "notification." + route;

    System.out.println("Sending notification to " + routingKey);

    this.template.convertAndSend(Config.notflixExchange().getName(), routingKey, gson.toJson(notificationMessage));
  }
}