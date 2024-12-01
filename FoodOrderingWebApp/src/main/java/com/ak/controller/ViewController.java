package com.ak.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ak.entity.CartItem;
import com.ak.entity.MenuItem;

import jakarta.servlet.http.HttpSession;
@Controller
public class ViewController {

    @GetMapping("/")
    public String showHomePage() {
        return "index"; // Renders templates/index.html
    }

//    @GetMapping("/cart")
//    public String showCartPage() {
//        return "cart"; // Renders templates/cart.html
//    }
    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        // Retrieve the cart from the session
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // Calculate the total price of items in the cart
        double total = cart.stream()
                .mapToDouble(item -> item.getQuantity() * item.getMenuItem().getPrice())
                .sum();

        // Add data to the model
        model.addAttribute("cartItems", cart);
        model.addAttribute("total", total);

        return "cart"; // Render cart.html
    }

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        // Retrieve the cart from the session
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // Calculate the total price
        double total = cart.stream()
                .mapToDouble(item -> item.getQuantity() * item.getMenuItem().getPrice())
                .sum();

        // Get user email as the "Name"
        String userEmail = (String) session.getAttribute("userEmail"); // Replace with actual user email logic

        // Add data to the model
        model.addAttribute("cartItems", cart);
        model.addAttribute("total", total);
        model.addAttribute("userEmail", userEmail);

        return "checkout";
    }

}