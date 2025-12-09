package com.example.georgestore.application.service;

import com.example.georgestore.domain.model.Product;
import com.example.georgestore.dto.ProductResponse;
import com.example.georgestore.infrastructure.client.ProductApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductApiClient productApiClient;

    public List<ProductResponse> getAll() {
        return productApiClient.getAllProducts()
                .stream()
                .map(p -> new ProductResponse(
                        p.getId(),
                        p.getTitle(),
                        p.getDescription(),
                        p.getPrice(),
                        p.getCategory(),
                        p.getImage()
                )).toList();
    }

    public ProductResponse getById(Long id) {

        Product p = productApiClient.getProductById(id);

        return new ProductResponse(
                p.getId(),
                p.getTitle(),
                p.getDescription(),
                p.getPrice(),
                p.getCategory(),
                p.getImage()
        );
    }
}
