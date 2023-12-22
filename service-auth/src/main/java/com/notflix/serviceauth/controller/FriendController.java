package com.notflix.serviceauth.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.notflix.serviceauth.entity.UserEntity;
import java.util.List;

import com.notflix.serviceauth.messages.RabbitMessageSender;
import com.notflix.serviceauth.repository.UserEntityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user/friend")
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
    friendEntity.getFriends().add(userEntity);
    userEntityRepository.save(userEntity);
    userEntityRepository.save(friendEntity);

    rabbitMessageSender.sendNotification("friend-added", friend, user, friend);

    return ResponseEntity.ok().body(gson.toJson("Success"));
  }

  @GetMapping("/all")
  public ResponseEntity<List<String>> getAllFriends(HttpServletRequest request) {
    String user = request.getHeader("Authorization");
    if (user == null) {
      System.out.println("Missing user");
      return ResponseEntity.status(400).body(null);
    }
    
    List<UserEntity> friends = userEntityRepository.findByEmail(user)
        .orElseThrow(() -> new RuntimeException("User not found"))
        .getFriends();

    System.out.println("Friends: " + friends);

    List<String> friendsEmails = friends.stream().map(UserEntity::getEmail).toList();

    return ResponseEntity.ok().body(friendsEmails);
  }

  @GetMapping("/info")
  public ResponseEntity<String> getFriendInfo(HttpServletRequest request, @RequestParam String email) {
    String user = request.getHeader("Authorization");
    
    String friend = email;

    if (user == null || friend == null) {
      return ResponseEntity.badRequest().body(gson.toJson("Missing user or friend"));
    }

    // get friend info
    UserEntity friendEntity = userEntityRepository.findByEmail(friend)
        .orElseThrow(() -> new RuntimeException("Friend not found"));

    // check if friend is in user's friend list
    List<UserEntity> friends = userEntityRepository.findByEmail(user)
        .orElseThrow(() -> new RuntimeException("User not found"))
        .getFriends();

    if (!friends.contains(friendEntity)) {
      return ResponseEntity.status(400).body(gson.toJson("Friend not found"));
    }

    JsonObject friendJson = new JsonObject();
    friendJson.addProperty("email", friendEntity.getEmail());
    friendJson.addProperty("name", friendEntity.getName());

    return ResponseEntity.ok().body(gson.toJson(friendJson));
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
    friendEntity.getFriends().remove(userEntity);
    userEntityRepository.save(userEntity);
    userEntityRepository.save(friendEntity);

    rabbitMessageSender.sendNotification("friend-deleted", friend, user, friend);

    return ResponseEntity.ok().body(gson.toJson("Delete - Success"));
  }
}