package com.notflix.servicechat.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.notflix.servicechat.Utils;
import com.notflix.servicechat.Entity.ChatEntity;
import com.notflix.servicechat.Entity.MessageEntity;
import com.notflix.servicechat.messages.RabbitMessageSender;
import com.notflix.servicechat.repo.ChatEntityRepo;
import com.notflix.servicechat.repo.MessageEntityRepo;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {
  Gson gson = new Gson();

  @Autowired
  RabbitMessageSender rabbitMessageSender;

  @Autowired
  ChatEntityRepo chatEntityRepo;

  @Autowired
  MessageEntityRepo messageEntityRepo;

  /**
   * Adds a message to a chat.
   * 
   * @param body The request body containing the message and chat ID.
   * @return The response entity indicating the success or failure of the
   *         operation.
   */
  @PostMapping("/add/message")
  public ResponseEntity<String> hello(HttpServletRequest request, @RequestBody String body) {
    String email = request.getHeader("Authorization");

    JsonObject bodyJson = gson.fromJson(body, JsonObject.class);

    String message = bodyJson.get("message").getAsString();

    Long chatId = bodyJson.get("chatId").getAsLong();

    Optional<ChatEntity> chat = chatEntityRepo.findById(chatId);

    if (chat.isEmpty()) { return ResponseEntity.status(400).body("Chat not found"); }

    if (!chat.get().getUser1().equals(email) && !chat.get().getUser2().equals(email)) {
      return ResponseEntity.status(401).body("Unauthorized");
    }

    MessageEntity messageEntity = new MessageEntity();

    messageEntity.setChatId(chat.get());
    messageEntity.setContent(message);
    messageEntity.setEmail(email);
    messageEntity.setTimestamp(new java.util.Date());

    messageEntityRepo.save(messageEntity);

    rabbitMessageSender.sendNotification("new-message", chat.get().getId().toString(), chat.get().getUser1(), chat.get().getUser2());

    return ResponseEntity.ok().body("Message sent");
  }

  /**
   * Gets all messages for a chat.
   * 
   * @param chat_id The ID of the chat to get messages for.
   * @return The response entity containing the messages.
   */
  @GetMapping("/get/message")
  public ResponseEntity<String> getMessage(HttpServletRequest request, @RequestParam Long chat) {
    String user = request.getHeader("Authorization");

    Optional<ChatEntity> chatEntity = chatEntityRepo.findById(chat);

    if (chatEntity.isEmpty()) {
      return ResponseEntity.status(400).body("Chat not found");
    }

    if (!chatEntity.get().getUser1().equals(user) && !chatEntity.get().getUser2().equals(user)) {
      return ResponseEntity.status(401).body("Unauthorized");
    }

    List<MessageEntity> messages = chatEntity.get().getMessages();

    JsonArray messagesJson = new JsonArray();

    for (MessageEntity message : messages) {
      JsonObject messageJson = new JsonObject();
      messageJson.addProperty("content", message.getContent());
      messageJson.addProperty("email", message.getEmail());
      messageJson.addProperty("timestamp", message.getTimestamp().toString());
      messagesJson.add(messageJson);
    }

    return ResponseEntity.ok().body(messagesJson.toString());
  }

  /**
   * creates a chat between two users.
   * 
   * @param body The request body containing the email of the user to add a chat
   *             with.
   * @return The response entity indicating the success or failure of the
   *         operation.
   */
  @PostMapping("/add")
  public ResponseEntity<String> addChat(HttpServletRequest request, @RequestBody String body) {
    String email = request.getHeader("Authorization");

    JsonObject bodyJson = gson.fromJson(body, JsonObject.class);
    String user = bodyJson.get("email").getAsString();

    String currentUser = email;
    String otherUser = user;

    if (currentUser == null) { return ResponseEntity.badRequest().body("User not found"); }
    if (otherUser == null) { return ResponseEntity.badRequest().body("User not found"); }

    System.out.println("currentUser: " + currentUser);
    System.out.println("otherUser: " + otherUser);

    Boolean exists = Utils.getRequestWithAuth("http://service-auth:8081/user/exists?email=" + otherUser, currentUser);

    if(!exists) { return ResponseEntity.badRequest().body("User not found"); }

    Optional<ChatEntity> chat = chatEntityRepo.findByUsers(currentUser, otherUser);
    if (chat.isPresent()) {
      return ResponseEntity.badRequest().body("Chat already exists");
    }

    ChatEntity chatEntity = new ChatEntity();

    if (true && currentUser != null && otherUser != null) {
      chatEntity.setUser1(email);
      chatEntity.setUser2(user);
      chatEntityRepo.save(chatEntity);
    }

    rabbitMessageSender.sendNotification("new-chat", chatEntity.getId().toString(), currentUser, otherUser);

    return ResponseEntity.ok().body(chatEntity.getId().toString());
  }

  /**
   * Gets the chats for a user.
   * 
   * @param body The request body containing the email of the user to get a chat
   *             with.
   * @return The response entity indicating the success or failure of the
   *         operation.
   */
  @GetMapping("/get")
  public ResponseEntity<String> getChat(HttpServletRequest request) {
    String email = request.getHeader("Authorization");

    List<ChatEntity> chat = chatEntityRepo.findByUser1OrUser2(email);

    JsonArray chats = new JsonArray();

    for (ChatEntity chatEntity : chat) {
      JsonObject chatJson = new JsonObject();

      chatJson.addProperty("id", chatEntity.getId());
      chatJson.addProperty("user1", chatEntity.getUser1());
      chatJson.addProperty("user2", chatEntity.getUser2());
      
      chats.add(chatJson);
    }

    return ResponseEntity.ok().body(chats.toString());
  }
}
