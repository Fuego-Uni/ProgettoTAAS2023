package com.progetto_tass.auth_service.controller;

import java.net.http.HttpRequest;
import java.util.Enumeration;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.converters.Auto;
import com.progetto_tass.auth_service.entity.UserEntity;
import com.progetto_tass.auth_service.entity.UserRole;
import com.progetto_tass.auth_service.repository.UserEntityRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;


@RestController
@RequestMapping("/auth")
public class DemoController {

    @Autowired
    public UserEntityRepository userEntityRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest authrequest) {
        String email = authrequest.email ;
        String name = authrequest.name ;
        
        var user = new UserEntity();
        user.setEmail(email);
        user.setName(name);
        user.setRole(UserRole.ROLE_USER);
        
        userEntityRepository.save(user);
        
        
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