package com.notflix.servicereviews.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.notflix.servicereviews.Entity.FilmEntity;
import com.notflix.servicereviews.Entity.ReviewEntity;
import com.notflix.servicereviews.Entity.UserEntity;
import com.notflix.servicereviews.messages.RabbitMessageSender;
import com.notflix.servicereviews.repo.FilmEntityRepo;
import com.notflix.servicereviews.repo.ReviewEntityRepo;
import com.notflix.servicereviews.repo.UserEntityRepository;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Enumeration;

@RestController
@RequestMapping("/review")
public class reviewController {
  Gson gson = new Gson();

  @Autowired
  RabbitMessageSender rabbitMessageSender;

  @Autowired
  ReviewEntityRepo reviewEntityRepo;

  @Autowired
  FilmEntityRepo filmEntityRepo;

  @Autowired
  UserEntityRepository userEntityRepository;

  @PostMapping("/add")
  public String hello(HttpServletRequest request, @RequestBody String body) {
    // cicle all headers and print all header in the request
    System.out.println("headers:");
    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String header = headerNames.nextElement();
      System.out.println(header + ": " + request.getHeader(header));
    }
    JsonObject bodyJson = gson.fromJson(body, JsonObject.class);
    String filmId = bodyJson.get("filmId").getAsBigInteger().toString();
    String vote = bodyJson.get("vote").getAsString();
    String note = bodyJson.get("note").getAsString();
    System.out.println("filmId: " + filmId);
    System.out.println("vote: " + vote);
    System.out.println("note: " + note);

    String email = request.getHeader("Authorization");
    
    // check if film exists
    if (filmEntityRepo.findById(Integer.parseInt(filmId)).isEmpty()) {
      System.out.println("film not found");
      // add film to db
      FilmEntity film = new FilmEntity(Integer.parseInt(filmId));
      filmEntityRepo.save(film);
    }
      var film = filmEntityRepo.findById(Integer.parseInt(filmId)).get();

      System.out.println("film found");
      // get email from AUTHORIZATION header
      System.out.println("email: " + email);
      UserEntity user = userEntityRepository.findByEmail(email).get();
      System.out.println("user: " + user);

      // create a new review
      ReviewEntity review = new ReviewEntity(user, film, 5, "Great movie!");

      // save
      reviewEntityRepo.save(review);
    

    return request.getHeader("Authorization");
  }
}
