package com.example.indiapaymenthub.controller;

import com.example.indiapaymenthub.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.indiapaymenthub.model.User;

import java.util.List;
@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String home() {
        return "index";
    }
    @RequestMapping("/login")
    public String login() {
        return "index";
    }
    
   
    @RequestMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; 
    }
    @PostMapping("/login")
    public String loginUser(@RequestParam String email, 
                            @RequestParam String password, 
                            RedirectAttributes redirectAttributes, 
                            HttpSession session) {
        try {
            // Find user by email
            User user = userService.getUserByEmail(email);
    
            // Check if user exists and password matches
            if (user != null && user.getPassword().equals(password)) {
                // Store user details in session
                session.setAttribute("loggedInUser", user);
                session.setAttribute("userType", user.getUserType()); // Store user type
    
                redirectAttributes.addFlashAttribute("success", "Login successful!");
    
                // Redirect based on user role
                if ("ADMIN".equalsIgnoreCase(user.getUserType())) {
                    return "redirect:/admin/dashboard"; // Redirect admin
                } else {
                    return "redirect:/user/dashboard"; // Redirect regular user
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Invalid email or password.");
                return "redirect:/login"; // Redirect back to login page on failure
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred during login.");
            return "redirect:/login"; 
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Destroy the session
        return "redirect:/login";
    }
    @GetMapping("/bad-request")
    public String badRequest() {
        return "401";
    }

    

}


