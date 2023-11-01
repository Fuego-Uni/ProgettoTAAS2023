package com.notflix.servicereviews.controller;

import com.notflix.servicereviews.messages.RabbitMessageSender;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
public class reviewController {
  @Autowired
  RabbitMessageSender rabbitMessageSender;

  @GetMapping("/hello")
  public String hello(HttpServletRequest request) {
    rabbitMessageSender.sendNotification("default", "Hello", "Hello World");
    return request.getHeader("Authorization");
  }
}
