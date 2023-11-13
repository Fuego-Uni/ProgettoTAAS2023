package com.notflix.servicereviews.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Optional;
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

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Enumeration;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.core.JsonProcessingException;

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
    int voteInt = Integer.parseInt(vote);
    ReviewEntity review = new ReviewEntity(user, film, voteInt, note);

    // save
    reviewEntityRepo.save(review);

    return request.getHeader("Authorization");
  }

@GetMapping("/get")
public ResponseEntity<String> getReviews(@RequestParam Long filmId) {


    List<ReviewEntity> reviews = reviewEntityRepo.findByFilmFilmId(filmId);
    // TODO: filter for friends

    
    String reviewJson = "";
    if (!reviews.isEmpty()) {
        // Initialize the proxy objects
        for (ReviewEntity review : reviews) {
            Hibernate.initialize(review.getUser());
            Hibernate.initialize(review.getFilm());
        }
        // Convert the ReviewEntity object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        try {
            reviewJson = objectMapper.writeValueAsString(reviews);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Return the JSON as the body of the response
        return new ResponseEntity<>(reviewJson, HttpStatus.OK);
    } else {
        // Return a 404 Not Found status code if the review is not found
        return new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);
    }
  }
}
