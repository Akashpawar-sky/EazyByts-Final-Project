package com.ak.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String showHomePage() {
        return "index"; // Renders templates/index.html
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // Renders templates/register.html
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Renders templates/login.html
    }

    @GetMapping("/cart")
    public String showCartPage() {
        return "cart"; // Renders templates/cart.html
    }
    @GetMapping("/menu")
    public String showMenuPage() {
        return "menu"; // Renders templates/cart.html
    }
    
}
