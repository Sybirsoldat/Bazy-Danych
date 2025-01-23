package com.example.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login"; // This points to a login.html file in templates
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // This points to a login.html file in templates
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // After login, this template will load
    }

    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "logout"; // A simple page confirming logout
    }
}
