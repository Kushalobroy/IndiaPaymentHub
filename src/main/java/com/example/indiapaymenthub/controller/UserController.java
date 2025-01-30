package com.example.indiapaymenthub.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
@Controller
@RequestMapping("/user")
public class UserController {
    
    @GetMapping("/dashboard")
    public String userDashboard(HttpSession session) {
        return "/dashboard";
    }
    
}
