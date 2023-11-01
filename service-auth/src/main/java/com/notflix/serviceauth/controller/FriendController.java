package com.notflix.serviceauth.controller;

import java.util.Optional;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import com.notflix.serviceauth.entity.UserEntity;
import com.notflix.serviceauth.entity.UserRole;
import com.notflix.serviceauth.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

  @PostMapping("/add")
  public ResponseEntity<String> addFriend(HttpServletRequest request, @RequestBody AuthRequest friend) {
    // main
    // String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    String header = request.getHeader("Authorization");
    System.out.println("header : " + header );
    try{
      Optional<UserEntity> user = userEntityRepository.findByEmail(header);
      Optional<UserEntity> friendUser = userEntityRepository.findByEmail(friend.email);
      user.get().getFriends().add(friendUser.get());
      userEntityRepository.save(user.get());
      return ResponseEntity.ok().body(gson.toJson("Success"));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(gson.toJson("Error"));
    }
  }
}