package com.ak.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ak.entity.CartItem;
import com.ak.entity.MenuItem;
import com.ak.entity.User;
import com.ak.repository.CartItemRepository;
import com.ak.repository.MenuItemRepository;
import com.ak.repository.UserRepository;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public String addToCart(@RequestParam Long userId, @RequestParam Long menuItemId, @RequestParam int quantity) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() -> new RuntimeException("Menu item not found"));

        CartItem cartItem = new CartItem(user, menuItem, quantity);
        cartItemRepository.save(cartItem);
        return "Item added to cart";
    }

    @GetMapping("/{userId}/total")
    public double getCartTotal(@PathVariable Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        return cartItems.stream().mapToDouble(item -> item.getQuantity() * item.getMenuItem().getPrice()).sum();
    }
}

