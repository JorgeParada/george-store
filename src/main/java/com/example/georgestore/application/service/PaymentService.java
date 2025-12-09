package com.example.georgestore.application.service;

import com.example.georgestore.infrastructure.persistence.entity.*;
import com.example.georgestore.infrastructure.persistence.repository.OrderRepository;
import com.example.georgestore.infrastructure.persistence.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentEntity processPayment(Long orderId) {

        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (order.getStatus() == OrderStatus.PAID) {
            throw new IllegalStateException("Order is already paid.");
        }

        var previousPayments = paymentRepository.findByOrderId(orderId);
        boolean hasApprovedPayment = previousPayments.stream()
                .anyMatch(p -> p.getStatus() == PaymentStatus.APPROVED);

        if (hasApprovedPayment) {
            throw new IllegalStateException("Order already has an approved payment.");
        }

        boolean approved = new Random().nextInt(100) < 70;

        PaymentEntity payment = new PaymentEntity();
        payment.setOrderId(orderId);
        payment.setAmount(order.getTotal());
        payment.setStatus(approved ? PaymentStatus.APPROVED : PaymentStatus.DECLINED);

        paymentRepository.save(payment);

        if (approved) {
            order.setStatus(OrderStatus.PAID);
        } else {
            order.setStatus(OrderStatus.FAILED);
        }

        orderRepository.save(order);

        return payment;
    }
}
