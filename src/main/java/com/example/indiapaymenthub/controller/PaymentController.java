package com.example.indiapaymenthub.controller;

import com.example.indiapaymenthub.model.*;
import com.example.indiapaymenthub.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/assign")
    public Payment assignPayment(@RequestBody Payment payment) {
        return paymentService.assignPayment(payment);
    }

    @GetMapping("/user/{userId}")
    public List<Payment> getUserPayments(@PathVariable Long userId) {
        return paymentService.getPaymentsByUserId(userId);
    }

    @PostMapping("/process/{paymentId}")
    public String processPayment(@PathVariable Long paymentId) {
        return paymentService.processPayment(paymentId);
    }
}