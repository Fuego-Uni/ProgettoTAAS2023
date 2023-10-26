package com.notflix.servicereviews.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
public class reviewController {
  @GetMapping("/hello")
  public String hello(HttpServletRequest request) {
      return request.getHeader("Authorization");
  }
}
