package com.example.georgestore.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GeorgeStore API")
                        .version("1.0")
                        .description("API documentation for GeorgeStore microservices")
                        .contact(new Contact()
                                .name("Jorge Parada Developer")
                                .email("jorge2001.parada@gmail.com")
                        )
                );
    }
}
