package com.notflix.servicereviews.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.notflix.servicereviews.Utils;
import com.notflix.servicereviews.entity.MediaEntity;
import com.notflix.servicereviews.entity.ReviewEntity;
import com.notflix.servicereviews.messages.RabbitMessageSender;
import com.notflix.servicereviews.repo.MediaEntityRepo;
import com.notflix.servicereviews.repo.ReviewEntityRepo;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
  MediaEntityRepo mediaEntityRepo;

  // post a review
  @PostMapping("/add")
  public String postReview(
    HttpServletRequest request,
    @RequestParam int mediaId,
    @RequestParam int vote,
    @RequestParam String note,
    @RequestParam String type
  ) {
    // get email from AUTHORIZATION header
    String user = request.getHeader("Authorization");

    // check if media exists
    Optional<MediaEntity> media = mediaEntityRepo.findByIdAndType(mediaId, type);

    // TODO: check if the id is a media or a series, etc...

    MediaEntity newMedia;

    // if there is no media already with this id, create one
    if (media.isEmpty()) {
      // add media to db
      newMedia = new MediaEntity(mediaId, type);
      newMedia = mediaEntityRepo.save(newMedia);
    } else {
      newMedia = media.get();
    }

    // find review from the same user of this media
    Optional<ReviewEntity> reviewEntity = reviewEntityRepo.findByIdandEmail(newMedia.getId(), user);

    ReviewEntity review;

    String message;

    if (reviewEntity.isEmpty()) {
      // create new review
      review = new ReviewEntity(user, newMedia, vote, note);
      System.out.println("Review created");
      message = "review-added";
    } else {
      // update review
      review = reviewEntity.get();
      review.setVote(vote);
      review.setNote(note);
      System.out.println("Review updated");
      message = "review-updated";
    }

    // save
    reviewEntityRepo.save(review);

    // friend is a list of user objects, {email: "email", name: "name"}
    List<String> friends = Utils.getRequestWithAuth("http://service-auth:8081/user/friend/all", user);
    
    for (int i = 0; i < friends.size(); i++) {
      System.out.println(friends.get(i));
    }

    friends.add(user);
    String[] friendsArray = friends.toArray(new String[0]);

    rabbitMessageSender.sendNotification(message, review.getId().toString(), friendsArray);

    return "Review added";
  }

  // get reviews of a media from friends
  @GetMapping("/friends")
  public ResponseEntity<String> getFriendReviews(
    HttpServletRequest request,
    @RequestParam int mediaId,
    @RequestParam String type
  ) {
    String user = request.getHeader("Authorization");

    System.out.println("getFriendReviews, mediaId: " + mediaId + ", type: " + type);

    List<ReviewEntity> reviews = reviewEntityRepo.findByMediaIdAndType(mediaId, type);

    System.out.println("getFriendReviews, found " + reviews.size() + " reviews");

    // filter reviews by friends
    List<String> friends = Utils.getRequestWithAuth("http://service-auth:8081/user/friend/all", user);

    friends.add(user);

    for (int i = 0; i < reviews.size(); i++) {
      if (!friends.contains(reviews.get(i).getUser())) {
        reviews.remove(i);
        i--;
      }
    }

    System.out.println("getFriendReviews, found " + reviews.size() + " reviews after filtering");

    if (!reviews.isEmpty()) {
      JsonArray reviewsJson = new JsonArray();

      for (ReviewEntity review : reviews) {
        System.out.println(review.toString());

        JsonObject reviewJson = new JsonObject();
        reviewJson.addProperty("vote", review.getVote());
        reviewJson.addProperty("note", review.getNote());
        reviewJson.addProperty("user", review.getUser());

        reviewsJson.add(reviewJson);
      }

      System.out.println(reviewsJson.toString());

      return new ResponseEntity<>(reviewsJson.toString(), HttpStatus.OK);
    } else {
      System.out.println("Review not found");
      
      // Return empty array
      return new ResponseEntity<>("[]", HttpStatus.OK);
    }
  }

  // get all media with a review from friends
  @GetMapping("/media")
  public ResponseEntity<String> getMediaWithReview(
    HttpServletRequest request,
    @RequestParam("type") String type
  ) {
    System.out.println("getMediaWithReview");
    String user = request.getHeader("Authorization");

    // get friends
    List<String> friends = Utils.getRequestWithAuth("http://service-auth:8081/user/friend/all", user);

    // get reviews
    List<ReviewEntity> reviews = reviewEntityRepo.findAll();

    // filter reviews by friends
    for (int i = 0; i < reviews.size(); i++) {
      if (!friends.contains(reviews.get(i).getUser())) {
        reviews.remove(i);
        i--;
      }
    }

    // get media
    List<MediaEntity> media = new ArrayList<>();

    for (ReviewEntity review : reviews) {
      if (!media.contains(review.getMedia())) {
        media.add(review.getMedia());
      }
    }

    // filter media by type
    for (int i = 0; i < media.size(); i++) {
      if (!media.get(i).getType().equals(type)) {
        media.remove(i);
        i--;
      }
    }

    if (!media.isEmpty()) {
      JsonArray mediaJson = new JsonArray();

      for (MediaEntity mediaEntity : media) {
        JsonObject mediaJsonObj = new JsonObject();
        mediaJsonObj.addProperty("id", mediaEntity.getMediaId());

        mediaJson.add(mediaJsonObj);
      }

      System.out.println(mediaJson.toString());

      return new ResponseEntity<>(mediaJson.toString(), HttpStatus.OK);
    } else {
      System.out.println("Media not found");
      
      // Return empty array
      return new ResponseEntity<>("[]", HttpStatus.OK);
    }
  }
}
