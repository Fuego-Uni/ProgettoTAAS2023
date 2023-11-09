package com.notflix.servicegateway.filter;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import com.notflix.servicegateway.GoogleUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

  @Autowired
  private RouteValidator validator;

  public AuthenticationFilter() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    Gson gson = new Gson();

    return ((exchange, chain) -> {
      System.out.println("inside Auth filter...!");
      if (validator.isSecured.test(exchange.getRequest())) {
        System.out.println("secured api...!");
        // header contains token or not
        if (exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
          String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
          System.out.println("Token: " + token);

          String URL1 = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + token;
          RestTemplate restTemplate1 = new RestTemplate();
          ResponseEntity<String> responseEntity1 = restTemplate1.getForEntity(URL1, String.class);

          GoogleUserInfo user = gson.fromJson(responseEntity1.getBody(), GoogleUserInfo.class);
          // append token to header
          exchange.getRequest().mutate().header(HttpHeaders.AUTHORIZATION, user.email);
          
          // service-auth
          String URL2 = "http://service-auth:8081/auth/authenticate";
          RestTemplate restTemplate2 = new RestTemplate();
          AuthRequest authRequest = new AuthRequest(user.email, user.name);
          //String authRequestJson = gson.toJson(authRequest);
          System.out.println("================= vado in auth service =================");
          ResponseEntity<String> responseEntity2 = restTemplate2.postForEntity(URL2,
          authRequest, String.class);
            
          System.out.println("Response: " + responseEntity2.getBody());
        } else {
          throw new RuntimeException("missing cookie header");
        }
      } else {
        System.out.println("open api...!");
      }
      return chain.filter(exchange);
    });
  }

  public static class Config {

  }
}
