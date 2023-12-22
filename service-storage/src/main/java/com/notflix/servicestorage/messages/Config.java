package com.notflix.servicestorage.messages;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
  @Bean public static DirectExchange notflixExchange() { return new DirectExchange("notflix-exchange"); }
}
