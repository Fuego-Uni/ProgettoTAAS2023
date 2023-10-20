package com.progetto_tass.auth_service.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private OAuth2LoginSuccessHandler OAuth2LoginSuccessHandler;
 
  @Value("$frontend.url")
  private String frontendUrl;

 @Bean
 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
   
   http
     .authorizeRequests(auth ->{
         auth.requestMatchers("/" ).permitAll();
         auth.anyRequest().authenticated();
     })
     .oauth2Login(oauth2 -> oauth2
             //.loginPage("/login").permitAll()
         .successHandler(OAuth2LoginSuccessHandler));

     
    return http.build();

 }
 @Bean
 CorsConfiguration corsConfiguration() {
     CorsConfiguration corsConfig = new CorsConfiguration();
     corsConfig.addAllowedOrigin(frontendUrl);
     corsConfig.addAllowedMethod("*");
     corsConfig.addAllowedHeader("*");
     corsConfig.setAllowCredentials(true);
     UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
     urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfig);
     return corsConfig;
 }
} 
