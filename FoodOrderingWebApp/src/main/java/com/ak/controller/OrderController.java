package com.ak.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ak.entity.CartItem;
import com.ak.entity.Order;
import com.ak.entity.OrderItem;
import com.ak.entity.User;
import com.ak.repository.CartItemRepository;
import com.ak.repository.OrderRepository;
import com.ak.repository.UserRepository;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/place")
    public String placeOrder(@RequestParam Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        if (cartItems.isEmpty()) {
            return "Cart is empty!";
        }

        double totalPrice = cartItems.stream()
                .mapToDouble(item -> item.getQuantity() * item.getMenuItem().getPrice())
                .sum();

        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> new OrderItem(null, item.getMenuItem(), item.getQuantity()))
                .collect(Collectors.toList());

        Order order = new Order(user, orderItems, totalPrice);
        orderRepository.save(order);

        cartItemRepository.deleteAll(cartItems); // Clear the cart after order placement
        return "Order placed successfully! Total: $" + totalPrice;
    }

    @GetMapping("/{userId}")
    public List<Order> getUserOrders(@PathVariable Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
