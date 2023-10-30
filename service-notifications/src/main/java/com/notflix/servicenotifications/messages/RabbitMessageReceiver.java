package com.notflix.servicenotifications.messages;

import com.notflix.servicenotifications.socket.SocketMessage;
import com.notflix.servicenotifications.socket.WebSocketConfig;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;

import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitMessageReceiver {
  private static final Gson gson = new Gson();

  @Bean Queue notificationQueue() { return new Queue("notification_queue"); }

  @Bean Binding groupUpdateBinding() { return BindingBuilder.bind(notificationQueue()).to(Config.notflixExchange()).with("notification.group.update");        }

  @RabbitListener(queues = "notification_queue")
  public void notificationMessageReceive(String _message) {
    System.out.println("Received notification message: " + _message);

    try {
      NotificationMessage notificationMessage = gson.fromJson(_message, NotificationMessage.class);
  
      String data = notificationMessage.getData();
      String[] users = notificationMessage.getUsers();
  
      SocketMessage socketMessage = new SocketMessage("notification", data);
  
      WebSocketConfig.messageHandler.sendMessageToUsers(users, socketMessage);
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
