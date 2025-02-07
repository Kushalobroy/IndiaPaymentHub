package com.example.indiapaymenthub.controller;

import com.example.indiapaymenthub.model.User;
import com.example.indiapaymenthub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;
import com.example.indiapaymenthub.repository.*;
import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@Controller
@RequestMapping("/admin") // Base URL for all endpoints in this controller
public class AdminController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @RequestMapping("/dashboard")
    public String adminDashboard(Model model) {
        long totalAdmins = userRepository.countByUserType("ADMIN");
        long totalUsers = userRepository.countByUserType("USER");
        long totalPendingPaymentAmount = paymentRepository.sumAmountByStatus("PENDING");
        long totalCompletedPaymentAmount = paymentRepository.sumAmountByStatus("COMPLETED");

        model.addAttribute("totalAdmins", totalAdmins);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalPendingPaymentAmount", totalPendingPaymentAmount);
        model.addAttribute("totalCompletedPaymentAmount", totalCompletedPaymentAmount);
        return "admin/index"; 
    }
    @RequestMapping("/addUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "admin/addUser";
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
    
    @RequestMapping("/users")
    public String getUsers(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }
    @RequestMapping("/users-assign")
    public String requestMethodName(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);

        return "admin/users-assign";
    }
    @RequestMapping("/assign-payment")
    public String requestMethodName(Model model, Long userId) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "admin/assign-payment";
    }
    
}
