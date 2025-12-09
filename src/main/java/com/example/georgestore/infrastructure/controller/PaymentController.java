package com.example.georgestore.infrastructure.controller;

import com.example.georgestore.application.service.PaymentService;
import com.example.georgestore.dto.PaymentRequest;
import com.example.georgestore.infrastructure.persistence.entity.PaymentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentEntity> pay(@RequestBody PaymentRequest req) {
        return ResponseEntity.ok(paymentService.processPayment(req.getOrderId()));
    }
}
