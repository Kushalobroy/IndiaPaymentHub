package com.example.indiapaymenthub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "admin/dashboard"; 
    }
}


