package com.notflix.servicegateway.messages;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
  @Bean public static DirectExchange multichatExchange() { return new DirectExchange("multichat-exchange"); }
}
