package com.multichat.api_gateway.filter;

import com.google.common.net.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;
    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
                return ((exchange, chain) -> {
                    System.out.printf("inside Auth filter...!");
                    if (validator.isSecured.test(exchange.getRequest())) {
                        System.out.println("secured api...!");
                        //header contains token or not
                        if (exchange.getRequest().getHeaders().containsKey(HttpHeaders.COOKIE)) {
                            org.springframework.http.HttpHeaders headers = exchange.getRequest().getHeaders();
                            boolean containsSessionId = false;
                            for (String cookie : headers.get(HttpHeaders.COOKIE)) {
                                if (cookie.contains("SESSIONID")) {
                                    containsSessionId = true;
                                    break;
                                }
                            }
                            if (!containsSessionId) {
                                throw new RuntimeException("missing SESSIONID cookie");
                            }
                        } else {
                            throw new RuntimeException("missing cookie header");
                        }
                        try {
                            //REST call to AUTH service             
                            //template.getForObject("http://IDENTITY-SERVICE//validate?token" + authHeader, String.class);
                            //jwtUtil.validateToken(authHeader);
                        } catch (Exception e) {
                            System.out.println("invalid access...!");
                            throw new RuntimeException("unauthorized access to application");
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
