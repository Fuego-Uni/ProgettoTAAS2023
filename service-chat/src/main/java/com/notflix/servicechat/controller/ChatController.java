package com.notflix.servicechat.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.notflix.servicechat.Entity.ChatEntity;
import com.notflix.servicechat.Entity.FilmEntity;
import com.notflix.servicechat.Entity.MessageEntity;
import com.notflix.servicechat.Entity.ReviewEntity;
import com.notflix.servicechat.Entity.UserEntity;
import com.notflix.servicechat.messages.RabbitMessageSender;
import com.notflix.servicechat.repo.ChatEntityRepo;
import com.notflix.servicechat.repo.FilmEntityRepo;
import com.notflix.servicechat.repo.MessageEntityRepo;
import com.notflix.servicechat.repo.ReviewEntityRepo;
import com.notflix.servicechat.repo.UserEntityRepository;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.apache.hc.core5.http.Message;
import org.hibernate.Hibernate;
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
  ReviewEntityRepo reviewEntityRepo;

  @Autowired
  FilmEntityRepo filmEntityRepo;

  @Autowired
  UserEntityRepository userEntityRepository;

  @Autowired
  ChatEntityRepo chatEntityRepo;

  @Autowired
  MessageEntityRepo messageEntityRepo;

  @PostMapping("/add/message")
  public ResponseEntity<String> hello(HttpServletRequest request, @RequestBody String body) {
    String email = request.getHeader("Authorization");

    JsonObject bodyJson = gson.fromJson(body, JsonObject.class);
    String message = bodyJson.get("message").toString();
    String chatIdString = bodyJson.get("chatId").getAsString();

    System.out.println("==============" + chatIdString);
    System.out.println("==============" + message);

    Optional<UserEntity> user = userEntityRepository.findByEmail(email);
    // search chat
    Optional<ChatEntity> chat = chatEntityRepo.findById(Long.parseLong(chatIdString));
    if (chat == null) {
      return ResponseEntity.badRequest().body("Chat not found");
    }
    if (user == null) {
      return ResponseEntity.badRequest().body("User not found");
    }
    if (user.isPresent() && chat.isPresent()) {
      MessageEntity messageEntity = new MessageEntity();
      messageEntity.setChatId(chat.get());
      messageEntity.setMessage(message);
      messageEntity.setUser(user.get());
      messageEntity.setTimestamp(new java.util.Date());
      System.out.println("================SALVO NEL DB=====================" + messageEntity.toString());
      messageEntityRepo.save(messageEntity);
      return ResponseEntity.ok().body("Message sent");
    }else{
      return ResponseEntity.badRequest().body("Message not sent");
    }
    /* rabbitMessageSender.sendMessage(gson.toJson(body), "chat"); */

    
  }

  @GetMapping("/get/message")
  public ResponseEntity<String> getMessage(HttpServletRequest request,  @RequestParam String chat_id) {

    String chatIdString = chat_id;
    System.out.println("-=========================" + chatIdString);
    Optional<ChatEntity> chat = chatEntityRepo.findById(Long.parseLong(chatIdString));
    if (!chat.isPresent()) {
      return ResponseEntity.badRequest().body("Chat not found");
    }

    List<MessageEntity> messages = messageEntityRepo.findAllByChat_Id(chat.get().getId());
    List<MessageType> messageString = new ArrayList<MessageType>();
    for (MessageEntity message : messages) {
      System.out.println("MESSAGGIO");
      Hibernate.initialize(message);
      MessageType messageString1 = new MessageType(message.getMessage(), message.getChatId().getId().toString(),
          message.getUser().getEmail(), message.getUser().getName(), message.getTimestamp());
      messageString.add(messageString1);
    }
    System.out.println("===================qui==================");
  
    for( MessageType message : messageString){
      System.out.println(message.toString());
    }
    JsonObject responseJson = new JsonObject();
    responseJson.addProperty("message", "Messages found");
    responseJson.addProperty("messages", gson.toJson(messageString));
    System.out.println("===RESPONSE JSON=======" + responseJson);
    return ResponseEntity.ok().body(gson.toJson(responseJson));
  }

  @PostMapping("/add/chat")
  public ResponseEntity<String> addChat(HttpServletRequest request, @RequestBody String body) {
    String email = request.getHeader("email");

    JsonObject bodyJson = gson.fromJson(body, JsonObject.class);
    String user = bodyJson.get("email").getAsString();

    Optional<UserEntity> user1 = userEntityRepository.findByEmail(email);
    Optional<UserEntity> user2 = userEntityRepository.findByEmail(user);
    ChatEntity chatEntity = new ChatEntity();
    if (user1 == null) {
      return ResponseEntity.badRequest().body("User not found");
    }
    if (user2 == null) {
      return ResponseEntity.badRequest().body("User not found");
    }
    if (user1.isPresent() && user2.isPresent()) {
      chatEntity.setUser1(user1.get());
      chatEntity.setUser2(user2.get());
      chatEntityRepo.save(chatEntity);
    }

    /* rabbitMessageSender.sendMessage(gson.toJson(body), "chat"); */
    if (chatEntity != null) {
      JsonObject responseJson = new JsonObject();
      responseJson.addProperty("message", "Chat added");
      responseJson.addProperty("chatId", chatEntity.getId().toString());

      return ResponseEntity.ok().body(gson.toJson(responseJson));
    } else {
      return ResponseEntity.badRequest().body("Chat could not be created");
    }

  }

  
  @GetMapping("/get/chat")
  public ResponseEntity<String> getChat(HttpServletRequest request) {
    String email = request.getHeader("Authorization");
    System.out.println("------------" + email);
    Optional<UserEntity> user = userEntityRepository.findByEmail(email);
    Optional<ChatEntity> chat = chatEntityRepo.findByUser1_Id(user.get().getId());
    Optional<ChatEntity> chat1 = chatEntityRepo.findByUser2_Id(user.get().getId());
    if (!chat.isPresent() && !chat1.isPresent()) {
      return ResponseEntity.badRequest().body("Chat not found");
    }
    if(chat.isPresent() && chat1.isPresent()){
      JsonObject responseJson = new JsonObject();
      responseJson.addProperty("message", "Chat found");
      responseJson.addProperty("chat", chat.get().getId().toString());
      return ResponseEntity.ok().body(gson.toJson(responseJson));
    }
    if(chat.isPresent()){
      JsonObject responseJson = new JsonObject();
      responseJson.addProperty("message", "Chat found");
      responseJson.addProperty("chat", chat.get().getId().toString());
      return ResponseEntity.ok().body(gson.toJson(responseJson));
    }
    if(chat1.isPresent()){
      JsonObject responseJson = new JsonObject();
      responseJson.addProperty("message", "Chat found");
      responseJson.addProperty("chat", chat1.get().getId().toString());
      return ResponseEntity.ok().body(gson.toJson(responseJson));
    }
    return ResponseEntity.ok().body("Chat found");
  }
}
