package com.example.indiapaymenthub.controller;

import com.example.indiapaymenthub.model.*;
import com.example.indiapaymenthub.service.*;
import com.example.indiapaymenthub.repository.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.indiapaymenthub.service.*;

import com.example.indiapaymenthub.repository.UserRepository;

import java.util.List;
import com.razorpay.Order;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RazorpayService razorpayService;

    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping("/create")
    public Payment createPayment(@RequestParam Long userId, @RequestParam Long assignedById,@RequestParam Double amount) {
        try {
             
             // Create Razorpay Order
             Order order = razorpayService.createRazorpayOrder(amount, "order_rcptid_" + System.currentTimeMillis());
 
             // Save payment in DB
             Payment payment = new Payment();
             payment.setUserId(userId);
             payment.setAssignedById(assignedById);
             payment.setAmount(amount);
             payment.setStatus("PENDING");
             payment.setGateway("RAZORPAY");
             payment.setGatewayOrderId(order.get("id"));
 

            return paymentRepository.save(payment);
        } catch (RazorpayException e) {
            throw new RuntimeException("Payment creation failed: " + e.getMessage());
        }
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