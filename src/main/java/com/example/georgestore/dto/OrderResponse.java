package com.example.georgestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

import com.example.georgestore.infrastructure.persistence.entity.OrderStatus;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private Long clientId;
    private BigDecimal total;
    private OrderStatus status;
    private List<OrderItemResponse> items;
}
