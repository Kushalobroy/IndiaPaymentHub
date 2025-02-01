package com.example.indiapaymenthub.service;

import com.example.indiapaymenthub.model.*;

import com.example.indiapaymenthub.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment assignPayment(Payment payment) {
        payment.setStatus("PENDING");
        return paymentRepository.save(payment);
    }

    public List<Payment> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId);
    }

    public String processPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        if (payment != null && "PENDING".equals(payment.getStatus())) {
            // Integrate with Razorpay or other gateways
            payment.setStatus("COMPLETED");
            paymentRepository.save(payment);
            return "Payment successful";
        }
        return "Payment failed or already processed";
    }
}