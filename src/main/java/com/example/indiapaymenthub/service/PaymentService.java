package com.example.indiapaymenthub.service;

import com.example.indiapaymenthub.model.*;

import com.example.indiapaymenthub.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment assignPayment(Payment payment) {
        payment.setStatus("PENDING");
        return paymentRepository.save(payment);
    }

    public List<Payment> getPaymentsByUserId(String userId) {
        return paymentRepository.findByUserId(userId);
    }

    public String processPayment(Long paymentId, Map<String, String> paymentDetails) {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
    
        if (payment != null && "PENDING".equals(payment.getStatus())) {
            // Extract payment details from the map
            payment.setGatewayPaymentId(paymentDetails.get("gateway_payment_id"));
            payment.setGatewaySignature(paymentDetails.get("gateway_signature"));
            payment.setStatus(paymentDetails.get("status"));  // e.g., "COMPLETED"
    
            paymentRepository.save(payment);
            return "Payment successful";
        }
        return "Payment failed or already processed";
    }
    
    
}