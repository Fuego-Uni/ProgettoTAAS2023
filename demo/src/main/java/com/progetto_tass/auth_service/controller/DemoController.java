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

    @GetMapping("/getToken")
    public ResponseEntity<String> getToken(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder headers = new StringBuilder();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.append(headerName).append(": ").append(request.getHeader(headerName)).append("\n");
        }
        return ResponseEntity.ok().body(headers.toString());
    }

}