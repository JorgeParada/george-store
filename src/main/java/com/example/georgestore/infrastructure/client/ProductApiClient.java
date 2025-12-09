package com.example.georgestore.infrastructure.client;

import com.example.georgestore.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductApiClient {

    private final WebClient fakeStoreWebClient;

    public List<Product> getAllProducts() {
        return fakeStoreWebClient.get()
                .uri("/products")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Product>>() {})
                .block();
    }

    public Product getProductById(Long id) {
        return fakeStoreWebClient.get()
                .uri("/products/{id}", id)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }
}
