package com.progetto_tass.auth_service.controller;

import java.net.http.HttpRequest;
import java.util.Enumeration;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;


@RestController
@RequestMapping("/auth")
public class DemoController {

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        
        
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        boolean hasAuthorization = false;
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (headerName.equalsIgnoreCase("Authorization")) {
                hasAuthorization = true;
                break;
            }
        }
        if (hasAuthorization) {
            return ResponseEntity.ok("Login");
        } else {
            return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("Authorization header not found");
        }
    }

}