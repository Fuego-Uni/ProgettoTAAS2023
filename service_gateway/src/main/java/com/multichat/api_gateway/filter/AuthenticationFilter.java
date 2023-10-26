package com.multichat.api_gateway.filter;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import com.multichat.api_gateway.GoogleUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
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
                //header contains token or not
                if (exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                    System.out.println("Token: " + token);
                    String URL = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + token;
                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL, String.class);

                    GoogleUserInfo user = gson.fromJson(responseEntity.getBody(), GoogleUserInfo.class);
//                    append token to header
                    exchange.getRequest().mutate().header(HttpHeaders.AUTHORIZATION, user.email);


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
