package com.ak.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ak.entity.CartItem;
import com.ak.entity.MenuItem;
import com.ak.repository.CartItemRepository;
import com.ak.repository.MenuItemRepository;
import com.ak.repository.UserRepository;
import com.ak.service.MenuItemService;

import jakarta.servlet.http.HttpSession;

@RestController

public class CartController {

    @Autowired
    private CartItemRepository cartItemRepository;
   
    @Autowired
    private  MenuItemService menuItemService;
    
    

    @Autowired
    private  MenuItemRepository menuItemRepository;
    

    @Autowired
    private UserRepository userRepository;

    /**
     * Add item to cart
     */
    @PostMapping("/add")
    public String addToCart(@RequestParam Long menuItemId, @RequestParam int quantity, HttpSession session) {
        // Retrieve the cart from the session
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // Fetch menu item details
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        // Add the item to the cart
        CartItem cartItem = new CartItem(null, menuItem, quantity); // User is null for now
        cart.add(cartItem);
        session.setAttribute("cart", cart);

        return "Item added to cart!";
    }

    /**
     * View cart items
     */
    @GetMapping("/view")
    public List<CartItem> viewCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            return new ArrayList<>();
        }
        return cart;
    }

    /**
     * Update cart item quantity
     */
    @PostMapping("/update")
    public String updateCart(@RequestParam Long menuItemId, @RequestParam int quantity, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            for (CartItem item : cart) {
                if (item.getMenuItem().getId().equals(menuItemId)) {
                    item.setQuantity(quantity);
                    break;
                }
            }
        }
        return "Cart updated!";
    }

    /**
     * Remove item from cart
     */
    @PostMapping("/removeFromCart")
    @ResponseBody
    public String removeFromCart(@RequestParam Long menuItemId, HttpSession session) {
        // Retrieve the cart from the session
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            // Remove the item matching the menuItemId
            cart.removeIf(item -> item.getMenuItem().getId().equals(menuItemId));
            session.setAttribute("cart", cart); // Update the session
        }
        return "Item removed from cart!";
    }


    /**
     * Get total cart value
     */
    @GetMapping("/total")
    public double getCartTotal(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            return 0.0;
        }
        return cart.stream().mapToDouble(item -> item.getQuantity() * item.getMenuItem().getPrice()).sum();
    }
    @PostMapping("/addToCart")
    @ResponseBody // To return only the response status for AJAX
    public String addToCart(@RequestBody Map<String, Object> requestData, HttpSession session) {
        Long menuItemId = Long.valueOf(requestData.get("menuItemId").toString());
        int quantity = Integer.parseInt(requestData.get("quantity").toString());

        // Retrieve the cart from the session or create a new one
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // Check if the item is already in the cart and update its quantity
        boolean itemExists = false;
        for (CartItem cartItem : cart) {
            if (cartItem.getMenuItem().getId().equals(menuItemId)) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                itemExists = true;
                break;
            }
        }

        // If the item is not already in the cart, add it
        if (!itemExists) {
            MenuItem menuItem = menuItemService.findById(menuItemId);
            CartItem newItem = new CartItem(null, menuItem, quantity); // Assuming user is null for now
            cart.add(newItem);
        }

        // Save the updated cart back to the session
        session.setAttribute("cart", cart);

        return "Item added to cart!";
    }

}