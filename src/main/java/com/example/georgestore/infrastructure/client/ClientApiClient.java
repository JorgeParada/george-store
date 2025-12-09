package com.example.georgestore.infrastructure.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ClientApiClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean existsClient(Long clientId) {

        try {
            String token = (String) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getCredentials();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<String> res =
                    restTemplate.exchange(
                            "http://localhost:8080/clients/" + clientId,
                            HttpMethod.GET,
                            entity,
                            String.class
                    );

            return res.getStatusCode().is2xxSuccessful();

        } catch (Exception e) {
            return false;
        }
    }
}
