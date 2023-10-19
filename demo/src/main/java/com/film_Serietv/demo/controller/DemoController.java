package com.film_Serietv.demo.controller;

import java.net.http.HttpRequest;
import java.util.Enumeration;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;


@RestController
@RequestMapping("/demo")
public class DemoController {

    

    @GetMapping("/gfg")
    public ResponseEntity<String> getAnonymous(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName() + ": " + cookie.getValue());
            }
        }
        return ResponseEntity.ok(cookies.toString());
    }
}