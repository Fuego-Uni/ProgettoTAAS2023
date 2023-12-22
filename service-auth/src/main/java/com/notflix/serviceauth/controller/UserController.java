package com.notflix.serviceauth.controller;

import java.util.Optional;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.notflix.serviceauth.entity.UserEntity;
import com.notflix.serviceauth.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
  Gson gson = new Gson();

  @Autowired
  public UserEntityRepository userEntityRepository;

  @GetMapping("/get")
  public ResponseEntity<String> getUser(HttpServletRequest request) {
    String email = request.getHeader("Authorization");
    Optional<UserEntity> user = userEntityRepository.findByEmail(email);

    System.out.println("User: " + user);

    if (user.isEmpty()) {
      System.out.println("User not found");
      return new ResponseEntity<>("User not found", HttpStatus.OK);
    }

    JsonObject userJson = new JsonObject();
    userJson.addProperty("email", user.get().getEmail());
    userJson.addProperty("name", user.get().getName());
    userJson.addProperty("role", user.get().getRole().toString());
    
    return ResponseEntity.ok(userJson.toString());
  }

  @GetMapping("/exists")
  public ResponseEntity<Boolean> userExists(HttpServletRequest request, @RequestParam String email) {
    System.out.println("Checking if user exists: " + email);
    
    Optional<UserEntity> user = userEntityRepository.findByEmail(email);

    if (user.isEmpty()) {
      return new ResponseEntity<>(false, HttpStatus.OK);
    }

    return new ResponseEntity<>(true, HttpStatus.OK);
  }
}