package com.progettotaas.reviewservice.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class reviewController {
 @GetMapping("/hello")
 public String hello() {
  return "Hello World!";
 }

}
