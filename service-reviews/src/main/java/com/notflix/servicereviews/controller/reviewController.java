package com.notflix.servicereviews.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.notflix.servicereviews.Utils;
import com.notflix.servicereviews.Entity.FilmEntity;
import com.notflix.servicereviews.Entity.ReviewEntity;
import com.notflix.servicereviews.messages.RabbitMessageSender;
import com.notflix.servicereviews.repo.FilmEntityRepo;
import com.notflix.servicereviews.repo.ReviewEntityRepo;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/review")
public class ReviewController {
  Gson gson = new Gson();

  @Autowired
  RabbitMessageSender rabbitMessageSender;

  @Autowired
  ReviewEntityRepo reviewEntityRepo;

  @Autowired
  FilmEntityRepo filmEntityRepo;

  @PostMapping("/add")
  public String postReview(HttpServletRequest request, @RequestParam int mediaId, @RequestParam int vote, @RequestParam String note) {
    // get email from AUTHORIZATION header
    String user = request.getHeader("Authorization");

    // check if film exists
    Optional<FilmEntity> film = filmEntityRepo.findById(mediaId);

    // TODO: check if the id is a film or a series, etc...

    if (film.isEmpty()) {
      // add film to db
      FilmEntity newFilm = new FilmEntity(mediaId);
      filmEntityRepo.save(newFilm);
    }

    // find review from the same user
    Optional<ReviewEntity> reviewEntity = reviewEntityRepo.findByFilmIdAndUserEmail(mediaId, user);

    ReviewEntity review;

    if (reviewEntity.isEmpty()) {
      // create new review
      review = new ReviewEntity(user, film.get(), vote, note);
      System.out.println("Review created");
    } else {
      // update review
      review = reviewEntity.get();
      review.setVote(vote);
      review.setNote(note);
      System.out.println("Review updated");
    }

    // save
    reviewEntityRepo.save(review);

    List<String> friends = Utils.getRequestWithAuth("http://service-auth:8081/friend/all", user);

    friends.add(user);
    String[] friendsArray = friends.toArray(new String[0]);

    rabbitMessageSender.sendNotification("review-added", review.getId().toString(), friendsArray);

    return "Review added";
  }

  @GetMapping("/friends")
  public ResponseEntity<String> getFriendReviews(HttpServletRequest request, @RequestParam int mediaId) {
    String user = request.getHeader("Authorization");

    // TODO: check if the id is a film or a series, etc...
    List<ReviewEntity> reviews = reviewEntityRepo.findByFilmId(mediaId);

    // filter reviews by friends
    List<String> friends = Utils.getRequestWithAuth("http://service-auth:8081/friend/all", user);
    friends.add(user);

    for (int i = 0; i < reviews.size(); i++) {
      if (!friends.contains(reviews.get(i).getUser())) {
        reviews.remove(i);
        i--;
      }
    }

    if (!reviews.isEmpty()) {
      JsonArray reviewsJson = new JsonArray();

      for (ReviewEntity review : reviews) {
        JsonObject reviewJson = new JsonObject();
        reviewJson.addProperty("vote", review.getVote());
        reviewJson.addProperty("note", review.getNote());
        reviewJson.addProperty("user", review.getUser());

        reviewsJson.add(reviewJson);
      }

      System.out.println(reviewsJson.toString());

      return new ResponseEntity<>(reviewsJson.toString(), HttpStatus.OK);
    } else {
      // Return a 404 Not Found status code if the review is not found
      System.out.println("Review not found");
      
      return new ResponseEntity<>("Review not found", HttpStatus.FORBIDDEN);
    }
  }
}
