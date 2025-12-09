package com.example.georgestore.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient fakeStoreWebClient() {
        return WebClient.builder()
                .baseUrl("https://fakestoreapi.com")
                .build();
    }
}
