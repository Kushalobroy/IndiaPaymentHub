package com.example.indiapaymenthub.controller;

import com.example.indiapaymenthub.model.User;
import com.example.indiapaymenthub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/api/admin") // Base URL for all endpoints in this controller
public class AdminController {

    @Autowired
    private UserService userService;
    
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
                    return "redirect:/dashboard"; // Redirect regular user
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
    

    // Create a new user
    @PostMapping("/add-user")
    public String createUser(@RequestParam String name, 
    @RequestParam String email, @RequestParam String phoneNumber, @RequestParam LocalDate dateOfBirth,@RequestParam String address, 
    @RequestParam String password, 
    RedirectAttributes redirectAttributes) {
        try {
            User newUser = new User();
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setPhoneNumber(phoneNumber); 
            newUser.setDateOfBirth(dateOfBirth); 
            newUser.setAddress(address);      
            newUser.setPassword(password);

            // Save the user via the service
            userService.createUser(newUser);

            // Add success message as a flash attribute
            redirectAttributes.addFlashAttribute("success", "User Added successfully!");

            return "redirect:/admin/addUser"; // Return the saved user with 200 OK
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding user.");
            return "redirect:/admin/addUser"; // Return 400 Bad Request for errors
        }
    }

    
}
