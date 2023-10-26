package com.notflix.serviceauth.controller;

import com.google.gson.Gson;
import com.notflix.serviceauth.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/auth")
public class AuthController {
    Gson gson = new Gson();

    @Autowired
    public UserEntityRepository userEntityRepository;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(HttpServletRequest request, @RequestBody String userdata) {
//        check if the user exists in the database

        System.out.println("userdata: " + userdata);

        return ResponseEntity.ok("User authenticated successfully");
//        String email = userdata.email;
//        Optional<UserEntity> user = userEntityRepository.findByEmail(email);
//
//        if (user.isEmpty()) {
//            var newUser = new UserEntity();
//            newUser.setEmail(email);
//            newUser.setName(userdata.name);
//            newUser.setRole(UserRole.ROLE_USER);
//            userEntityRepository.save(newUser);
//        }
//
//        return ResponseEntity.ok("User authenticated successfully");
    }

//    @PostMapping("/register")
//    public ResponseEntity<String> register(@RequestBody AuthRequest authrequest) {
//        String email = authrequest.email ;
//        String name = authrequest.name ;
//
//        var user = new UserEntity();
//        user.setEmail(email);
//        user.setName(name);
//        user.setRole(UserRole.ROLE_USER);
//
//        userEntityRepository.save(user);
//
//
//        return ResponseEntity.ok("User registered successfully");
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(HttpServletRequest request) {
//        Enumeration<String> headerNames = request.getHeaderNames();
//        boolean hasAuthorization = false;
//        while (headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            if (headerName.equalsIgnoreCase("Authorization")) {
//                hasAuthorization = true;
//                break;
//            }
//        }
//        if (hasAuthorization) {
//            return ResponseEntity.ok("Login");
//        } else {
//            return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("Authorization header not found");
//        }
//    }

}