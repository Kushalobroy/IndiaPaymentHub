package com.example.indiapaymenthub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
    @RequestMapping("/admin/addUser")
    public String addUser() {
        return "admin/addUser";
    }
}
