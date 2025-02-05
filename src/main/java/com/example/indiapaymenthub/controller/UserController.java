package com.example.indiapaymenthub.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import com.example.indiapaymenthub.model.Payment;
import com.example.indiapaymenthub.model.User;
import com.example.indiapaymenthub.repository.PaymentRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private PaymentRepository PaymentRepository;

    @GetMapping("/dashboard")
    public String userDashboard(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            Long userId = loggedInUser.getId();
            List<Payment> payments = PaymentRepository.findByUserId(String.valueOf(userId));
            model.addAttribute("payments", payments);
        }
        model.addAttribute("user", loggedInUser);
        return "/dashboard";
        
    }
    
}
