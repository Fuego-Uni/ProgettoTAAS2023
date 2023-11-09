package com.notflix.serviceauth.controller;

import java.util.Optional;
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
@RequestMapping("/auth")
public class AuthController {
  Gson gson = new Gson();

  @Autowired
  public UserEntityRepository userEntityRepository;

  @PostMapping("/authenticate")
  public ResponseEntity<String> authenticate(HttpServletRequest request, @RequestBody AuthRequest userdata) {
    System.out.println(userdata);
    String email = userdata.email;
    Optional<UserEntity> user = userEntityRepository.findByEmail(email);

    if (user.isEmpty()) {
      var newUser = new UserEntity();
      newUser.setEmail(email);
      newUser.setName(userdata.name);
      newUser.setRole(UserRole.ROLE_USER);
      userEntityRepository.save(newUser);
    }
  
    return ResponseEntity.ok("User authenticated successfully");
  }
}