package com.notflix.serviceauth.controller;

import java.util.List;
import java.util.Optional;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.notflix.serviceauth.Utils;
import com.notflix.serviceauth.entity.UserEntity;
import com.notflix.serviceauth.messages.RabbitMessageSender;
import com.notflix.serviceauth.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
  Gson gson = new Gson();

  @Autowired
  public UserEntityRepository userEntityRepository;
  @Autowired
  public RabbitMessageSender rabbitMessageSender;

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

  @PostMapping("/update")
  public ResponseEntity<String> updateUser(
    HttpServletRequest request,
    @RequestParam(name = "name", required = false) String name,
    @RequestParam(name = "email", required = false) String email,
    @RequestParam(name = "image", required = false) MultipartFile profile
  ) {
    String userEmail = request.getHeader("Authorization");
    Optional<UserEntity> user = userEntityRepository.findByEmail(userEmail);

    if (user.isEmpty()) {
      return new ResponseEntity<>("User not found", HttpStatus.OK);
    }

    if (name != null) {
      user.get().setName(name);
    }

    if (email != null) {
      // EMAIL CANNOT BE CHANGED
      // user.get().setEmail(email);
    }

    if (profile != null) {
      String url = "http://service-storage:8085/storage/pfp";
      // make a request to the storage service to upload the image, multipart/form-data

      RestTemplate restTemplate = new RestTemplate();

      MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
      body.add("image", profile.getResource());

      RequestEntity<?> requestEntity = RequestEntity
        .post(url)
        .header(HttpHeaders.AUTHORIZATION, userEmail)
        .header(HttpHeaders.CONTENT_TYPE, "multipart/form-data")
        .body(body);
      
      ParameterizedTypeReference<Long> responseType = new ParameterizedTypeReference<Long>() {};

      restTemplate.exchange(
        url,
        HttpMethod.POST,
        requestEntity,
        responseType
      );
    }

    userEntityRepository.save(user.get());

    List<String> friends = Utils.getRequestWithAuth("http://service-auth:8081/user/friend/all", userEmail);
    friends.add(userEmail);

    rabbitMessageSender.sendNotification("user-updated", userEmail, friends.toArray(new String[0]));

    return new ResponseEntity<>("User updated", HttpStatus.OK);
  }
}