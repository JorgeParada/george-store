package com.example.georgestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderItemResponse {
    private Long productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
