package com.notflix.servicestorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.notflix.servicestorage.ImageUtil;
import com.notflix.servicestorage.Utils;
import com.notflix.servicestorage.entity.ImageEntity;
import com.notflix.servicestorage.messages.RabbitMessageSender;
import com.notflix.servicestorage.repository.ImageRepository;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/storage")
public class ProfilePictureController {
  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  RabbitMessageSender rabbitMessageSender;

  @GetMapping("/pfp")
  public ResponseEntity<?> getPfp(HttpServletRequest request) {
    String user = request.getHeader("Authorization");

    System.out.println("Getting profile picture for user: " + user);

    List<ImageEntity> images = imageRepository.findByUser(user);

    // find profile picture in list
    ImageEntity pfp = null;

    for (ImageEntity image : images) {
      if (image.getName().equals("profile")) {
        pfp = image;
      }
    }

    if (pfp != null) {
      System.out.println("Profile picture found for user: " + user);
      return ResponseEntity.status(HttpStatus.OK).body(ImageUtil.decompressImage(pfp.getImageData()));
    } else {
      System.out.println("Profile picture not found for user: " + user);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile picture not found.");
    }
  }

  @PostMapping("/pfp")
  public ResponseEntity<?> postPfp(HttpServletRequest request, @RequestParam("image") MultipartFile file) throws IOException {
    String user = request.getHeader("Authorization");

    System.out.println("Uploading profile picture for user: " + user);

    List<ImageEntity> images = imageRepository.findByUser(user);

    // find profile picture in list
    ImageEntity pfp = null;

    for (ImageEntity image : images) {
      if (image.getName().equals("profile")) {
        pfp = image;
      }
    }

    List<String> friends = Utils.getRequestWithAuth("http://service-auth:8081/user/friend/all", user);
    friends.add(user);

    if (pfp != null) {
      // update profile picture
      System.out.println("Updating profile picture for user: " + user);
      pfp.setImageData(ImageUtil.compressImage(file.getBytes()));
      imageRepository.save(pfp);
    } else {
      // create profile picture
      System.out.println("Creating profile picture for user: " + user);
      pfp = new ImageEntity("profile", user, ImageUtil.compressImage(file.getBytes()));
      imageRepository.save(pfp);
    }

    rabbitMessageSender.sendNotification("pfp-updated", user, friends.toArray(new String[0]));

    return ResponseEntity.status(HttpStatus.OK).body("Profile picture updated.");
  }

  @GetMapping("/pfp/friend")
  public ResponseEntity<?> getFriendPfp(HttpServletRequest request, @RequestParam("user") String friend) {
    String user = request.getHeader("Authorization");

    // check if friend is actually a friend
    List<String> friends = Utils.getRequestWithAuth("http://service-auth:8081/user/friend/all", user);

    if (!friends.contains(friend)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not a friend.");
    }

    List<ImageEntity> images = imageRepository.findByUser(friend);

    // find profile picture in list
    ImageEntity pfp = null;

    for (ImageEntity image : images) {
      if (image.getName().equals("profile")) {
        pfp = image;
      }
    }

    if (pfp != null) {
      return ResponseEntity.status(HttpStatus.OK).body(ImageUtil.decompressImage(pfp.getImageData()));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile picture not found.");
    }
  }
}
