package com.notflix.servicestorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.notflix.servicestorage.ImageUtil;
import com.notflix.servicestorage.entity.ImageEntity;
import com.notflix.servicestorage.messages.RabbitMessageSender;
import com.notflix.servicestorage.repository.ImageRepository;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@RestController
@RequestMapping("/storage")
public class ImageStorageController {
  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  RabbitMessageSender rabbitMessageSender;

  @PostMapping("add")
  public ResponseEntity<?> addImage(HttpServletRequest request, @RequestParam("file") MultipartFile file, @RequestParam("name") String name) {
    String email = request.getHeader("Authorization");

    System.out.println("Uploading image for user: " + email);

    if (file.isEmpty()) {
      System.out.println("File is empty");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
    }

    try {
      ImageEntity image = new ImageEntity();
      image.setName(name);
      image.setUser(email);
      image.setImageData(ImageUtil.compressImage(file.getBytes()));
      ImageEntity imageEntity = imageRepository.save(image);
      System.out.println("Saved image with ID: " + imageEntity.getId());
      return ResponseEntity.ok(imageEntity.getId().toString());
    } catch (IOException e) {
      System.out.println("Error saving image");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving image");
    }
  }

  @GetMapping("get")
  public ResponseEntity<?> getImage(HttpServletRequest request, @RequestParam("id") Long id) {
    String email = request.getHeader("Authorization");

    if (id == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID is empty");
    }

    ImageEntity image = imageRepository.findById(id).orElse(null);

    if (image == null) {
      return ResponseEntity.status(401).body("Image not found");
    }

    return ResponseEntity.ok(ImageUtil.decompressImage(image.getImageData()));
  }
}
