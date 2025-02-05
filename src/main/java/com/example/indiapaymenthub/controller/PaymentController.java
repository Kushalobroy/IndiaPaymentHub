package com.example.indiapaymenthub.controller;

import com.example.indiapaymenthub.model.*;
import com.example.indiapaymenthub.service.*;
import com.example.indiapaymenthub.repository.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.indiapaymenthub.service.*;

import java.util.*;
import com.razorpay.Order;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/admin/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RazorpayService razorpayService;

    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping("/create")
    public String createPayment(@RequestParam String userId, @RequestParam String assignedById,
    @RequestParam Double amount, @RequestParam String gateway, RedirectAttributes redirectAttributes) {
        try {
             
             // Create Razorpay Order
             Order order = razorpayService.createRazorpayOrder(amount, "order_rcptid_" + System.currentTimeMillis());
 
             // Save payment in DB
             Payment payment = new Payment();
             payment.setUserId(userId);
             payment.setAssignedById(assignedById);
             payment.setAmount(amount);
             payment.setStatus("PENDING");
             payment.setGateway(gateway);
             payment.setGatewayOrderId(order.get("id"));
 

            paymentRepository.save(payment);
            redirectAttributes.addFlashAttribute("success", "Payment created successfully!");
            return "redirect:/admin/users-assign";
            
        } catch (RazorpayException e) {
            throw new RuntimeException("Payment creation failed: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public List<Payment> getUserPayments(@PathVariable String userId) {
        return paymentService.getPaymentsByUserId(userId);
    }

    @PostMapping("/process/{orderId}")
    public String processPayment(@PathVariable Long orderId, @RequestBody Map<String, String> paymentDetails) {
        return paymentService.processPayment(orderId, paymentDetails);
    }


    
}