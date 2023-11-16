package com.notflix.servicechat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.notflix.servicechat.Entity.FilmEntity;
import com.notflix.servicechat.Entity.ReviewEntity;
import com.notflix.servicechat.Entity.UserEntity;
import com.notflix.servicechat.messages.RabbitMessageSender;
import com.notflix.servicechat.repo.ChatEntityRepo;
import com.notflix.servicechat.repo.FilmEntityRepo;
import com.notflix.servicechat.repo.ReviewEntityRepo;
import com.notflix.servicechat.repo.UserEntityRepository;

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
@RequestMapping("/chat")
public class ChatController {
  Gson gson = new Gson();

  @Autowired
  RabbitMessageSender rabbitMessageSender;

  @Autowired
  ReviewEntityRepo reviewEntityRepo;

  @Autowired
  FilmEntityRepo filmEntityRepo;

  @Autowired
  UserEntityRepository userEntityRepository;

  @Autowired
  ChatEntityRepo chatEntityRepo;

  /* @PostMapping("/add")
  public String hello(HttpServletRequest request, @RequestBody String body) {
    
  } */

/* @GetMapping("/get")
public ResponseEntity<String> getReviews(@RequestParam Long filmId) {
 
   
  } */
}
