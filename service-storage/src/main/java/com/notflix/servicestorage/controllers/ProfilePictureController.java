package com.notflix.servicestorage.controllers;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.notflix.servicestorage.Utils;
import com.notflix.servicestorage.messages.RabbitMessageSender;
import com.notflix.servicestorage.storage.StorageFileNotFoundException;
import com.notflix.servicestorage.storage.StorageService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/storage")
public class ProfilePictureController {
  @Autowired
  RabbitMessageSender rabbitMessageSender;

	private final StorageService storageService;

	@Autowired
	public ProfilePictureController(StorageService storageService) {
		this.storageService = storageService;
	}

  @GetMapping("/pfp")
  public ResponseEntity<Resource> getUserProfile(HttpServletRequest request) throws IOException {
    String user = request.getHeader("Authorization");

    Stream<Path> file_stream = storageService.loadAll(user);

    Path profilePicture = null;

    for (Path file : file_stream.toList()) {
      if (file.getFileName().toString().startsWith("profile.")) {
        profilePicture = file;
        break;
      }
    }

    if (profilePicture == null) {
      throw new StorageFileNotFoundException("Profile picture not found.");
    }

    Resource file = storageService.loadAsResource(profilePicture.getFileName().toString(), user);

    if (file == null) {
      return ResponseEntity.status(400).body(null);
    } else {
      return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
          "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
  }

  @GetMapping("/pfp/friend")
  public ResponseEntity<Resource> getFriendProfile(HttpServletRequest request, @RequestParam("user") String friend) throws IOException {
    String user = request.getHeader("Authorization");

    Stream<Path> files = storageService.loadAll(friend);
    List<String> friends = Utils.getRequestWithAuth("http://service-auth:8081/user/friend/all", user);

    Path profilePicture = null;

    if (friends.contains(friend)) {
      for (Path file : files.toList()) {
        System.out.println(file.getFileName().toString());
        if (file.getFileName().toString().startsWith("profile.")) {
          profilePicture = file;
          break;
        }
      }
    } else {
      // return unauthorized
      System.out.println("Unauthorized");
      return ResponseEntity.status(401).body(null);
    }

    if (profilePicture == null) {
      // return empty body
      System.out.println("Profile picture not found.");
      return ResponseEntity.status(400).body(null);
    }

    Resource file = storageService.loadAsResource(profilePicture.getFileName().toString(), friend);

    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }

  @PostMapping("/pfp")
  public String handleFileUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
    String user = request.getHeader("Authorization");

    // change the filename to profile.<extension>
    String filename = "profile." + file.getOriginalFilename().split("\\.")[1];

    storageService.store(file, user, filename);

    System.out.println("File uploaded: " + file.getOriginalFilename());

    // send notification to friends and self
    List<String> friends = Utils.getRequestWithAuth("http://service-auth:8081/user/friend/all", user);
    friends.add(user);

    rabbitMessageSender.sendNotification("pfp-updated", user, friends.toArray(new String[0]));

    return "file uploaded";
  }

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}
}