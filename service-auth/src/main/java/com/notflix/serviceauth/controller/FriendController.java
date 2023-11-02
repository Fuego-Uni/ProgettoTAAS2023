package com.notflix.serviceauth.controller;

import java.util.Optional;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.notflix.serviceauth.entity.UserEntity;
import com.notflix.serviceauth.entity.UserRole;
import com.notflix.serviceauth.messages.RabbitMessageSender;
import com.notflix.serviceauth.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {
  Gson gson = new Gson();

  @Autowired
  public UserEntityRepository userEntityRepository;
  @Autowired
  public RabbitMessageSender rabbitMessageSender;

  @PostMapping("/add")
  public ResponseEntity<String> addFriend(HttpServletRequest request, @RequestBody String body) {
    JsonObject bodyJson = gson.fromJson(body, JsonObject.class);

    String user = request.getHeader("Authorization");
    String friend = bodyJson.get("friend").getAsString();

    if (user == null || friend == null) {
      return ResponseEntity.badRequest().body(gson.toJson("Missing user or friend"));
    }

    if (user.equals(friend)) {
      return ResponseEntity.badRequest().body(gson.toJson("Cannot add yourself as a friend"));
    }

    UserEntity userEntity = userEntityRepository.findByEmail(user)
        .orElseThrow(() -> new RuntimeException("User not found"));
    UserEntity friendEntity = userEntityRepository.findByEmail(friend)
        .orElseThrow(() -> new RuntimeException("Friend not found"));

    userEntity.getFriends().add(friendEntity);
    userEntityRepository.save(userEntity);

    rabbitMessageSender.sendNotification(null, "friend-added", friend, user);
    rabbitMessageSender.sendNotification(null, "added-as-friend", user, friend);

    return ResponseEntity.ok().body(gson.toJson("Success"));
  }

  @GetMapping("/all")
  public ResponseEntity<String> getAllFriends(HttpServletRequest request) {
    String user = request.getHeader("Authorization");
    if (user == null) {
      return ResponseEntity.badRequest().body(gson.toJson("Missing user"));
    }

    UserEntity userEntity = userEntityRepository.findByEmail(user)
        .orElseThrow(() -> new RuntimeException("User not found"));
    return ResponseEntity.ok().body(gson.toJson(userEntity.getFriends()));
  }

  @PostMapping("/remove")
  public ResponseEntity<String> deleteFriend(HttpServletRequest request, @RequestBody String body) {
    JsonObject bodyJson = gson.fromJson(body, JsonObject.class);

    String user = request.getHeader("Authorization");
    String friend = bodyJson.get("friend").getAsString();

    if (user == null || friend == null) {
      return ResponseEntity.badRequest().body(gson.toJson("Missing user or friend"));
    }

    if (user.equals(friend)) {
      return ResponseEntity.badRequest().body(gson.toJson("Cannot delete yourself as a friend"));
    }

    UserEntity userEntity = userEntityRepository.findByEmail(user)
        .orElseThrow(() -> new RuntimeException("User not found"));
    UserEntity friendEntity = userEntityRepository.findByEmail(friend)
        .orElseThrow(() -> new RuntimeException("Friend not found"));

    userEntity.getFriends().remove(friendEntity);
    userEntityRepository.save(userEntity);

    return ResponseEntity.ok().body(gson.toJson("Delete - Success"));
  }

}