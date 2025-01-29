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
    @RequestMapping("/")
    public String home() {
        return "index";
    }
    
   
    @RequestMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; 
    }
    @RequestMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/index"; 
    }
    @RequestMapping("/admin/addUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "admin/addUser";
    }

    @Autowired
    private UserService userService;

    @RequestMapping("/admin/users")
    public String getUsers(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }
}


