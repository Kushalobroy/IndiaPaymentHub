package com.example.indiapaymenthub.controller;

import com.example.indiapaymenthub.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.indiapaymenthub.model.User;
import com.example.indiapaymenthub.repository.*;
import java.util.List;
import java.util.Optional;
@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

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
                            HttpSession session, Model model) {
        try {
            // Find user by email
            User user = userService.getUserByEmail(email);
    
            // Check if user exists and password matches
            if (user != null && user.getPassword().equals(password)) {
                // Store user details in session
                session.setAttribute("loggedInUser", user);
                session.setAttribute("userType", user.getUserType()); // Store user type
                session.setAttribute("assignedById", user.getId());
                redirectAttributes.addFlashAttribute("success", "Login successful!");
              
                if ("ADMIN".equalsIgnoreCase(user.getUserType())) {
                    return "redirect:/admin/dashboard"; // Redirect admin
                } else {
                    return "redirect:/user/dashboard"; // Redirect regular user
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Invalid email or password.");
                return "redirect:/login"; 
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred during login.");
            return "redirect:/login"; 
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
         // Destroy the session
        redirectAttributes.addFlashAttribute("success", "Logged Out successfully !");
        return "redirect:/login";
    }
    @GetMapping("/bad-request")
    public String badRequest() {
        return "401";
    }
    @GetMapping("/privacy-policy")
    public String privacyPolicy() {
        return "privacy-policy";
    }
    @GetMapping("/terms-and-conditions")
    public String termsConditions(){
        return "terms-conditions";
    }  
    @GetMapping("/forgot-password")  
    public String forgotPassword(){
        return "forgot-password";
    }
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email, RedirectAttributes redirectAttributes) {
        userService.sendPasswordResetEmail(email);
        redirectAttributes.addAttribute("success", "Forgot Password Mail Send Successfully !");
        return "forgot-password";
    }
    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam String token, Model model) {
        Optional<User> userOpt = userRepository.findByResetToken(token);  
        if (userOpt.isPresent()) {
            model.addAttribute("token", token);
            return "reset-password"; // Redirect to reset-password.html
        }
        return "error";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword, RedirectAttributes redirectAttributes) {
        boolean success = userService.resetPassword(token, newPassword);
        if (success) {
            redirectAttributes.addFlashAttribute("success", "Password changed successfully!");
            return "redirect:/login"; // Redirect to login page after success
        } 
        redirectAttributes.addFlashAttribute("error", "Invalid or expired token.");
        return "redirect:/forgot-password";
        
    } 

}


