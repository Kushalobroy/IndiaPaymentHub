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

    public String processPayment() {
        
        return "Payment failed or already processed";
    }
    
    
}