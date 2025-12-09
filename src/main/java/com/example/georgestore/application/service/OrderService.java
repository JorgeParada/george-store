package com.example.georgestore.application.service;

import com.example.georgestore.infrastructure.persistence.repository.OrderRepository;
import com.example.georgestore.domain.exception.NoOrdersFoundException;
import com.example.georgestore.dto.*;
import com.example.georgestore.infrastructure.client.ClientApiClient;
import com.example.georgestore.infrastructure.client.ProductApiClient;
import com.example.georgestore.infrastructure.persistence.entity.OrderEntity;
import com.example.georgestore.infrastructure.persistence.entity.OrderItemEntity;
import com.example.georgestore.infrastructure.persistence.entity.OrderStatus;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductApiClient productApiClient;
    private final ClientApiClient clientApiClient;

    public OrderResponse create(OrderRequest req) {

        if (!clientApiClient.existsClient(req.getClientId()))
            throw new IllegalArgumentException("Client does not exist");

        OrderEntity order = new OrderEntity();
        order.setClientId(req.getClientId());
        order.setCreatedAt(Instant.now());
        order.setStatus(OrderStatus.PENDING);
        
        List<OrderItemEntity> items = req.getItems().stream().map(itemReq -> {
            var product = productApiClient.getProductById(itemReq.getProductId());

            var item = OrderItemEntity.builder()
                    .productId(product.getId())
                    .title(product.getTitle())
                    .price(product.getPrice())
                    .quantity(itemReq.getQuantity())
                    .order(order)
                    .build();

            return item;
        }).toList();

        order.setItems(items);

        BigDecimal total = items.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotal(total);

        orderRepository.save(order);

        return new OrderResponse(
                order.getId(),
                order.getClientId(),
                order.getTotal(),
                order.getStatus(),
                items.stream().map(i ->
                        new OrderItemResponse(
                                i.getProductId(),
                                i.getTitle(),
                                i.getPrice(),
                                i.getQuantity()
                        )).toList()
        );
    }

    public OrderResponse getById(Long id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        return new OrderResponse(
                order.getId(),
                order.getClientId(),
                order.getTotal(),
                order.getStatus(),
                order.getItems().stream().map(i ->
                        new OrderItemResponse(
                                i.getProductId(),
                                i.getTitle(),
                                i.getPrice(),
                                i.getQuantity()
                        )).toList()
        );
    }

    public List<OrderResponse> getAll() {

    var orders = orderRepository.findAll();

    if (orders.isEmpty()) {
        throw new NoOrdersFoundException("There are no orders yet");
    }

    return orders.stream().map(o ->
            new OrderResponse(
                    o.getId(),
                    o.getClientId(),
                    o.getTotal(),
                    o.getStatus(),
                    o.getItems().stream().map(i ->
                            new OrderItemResponse(
                                    i.getProductId(),
                                    i.getTitle(),
                                    i.getPrice(),
                                    i.getQuantity()
                            )
                    ).toList()
            )
    ).toList();
}

}
