package com.ak.service;


import com.ak.entity.MenuItem;
import com.ak.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    // Method to find a MenuItem by its ID
    public MenuItem findById(Long id) {
        Optional<MenuItem> menuItem = menuItemRepository.findById(id);
        if (menuItem.isPresent()) {
            return menuItem.get();
        } else {
            throw new RuntimeException("Menu item not found with ID: " + id);
        }
    }

    // Add other business logic methods as needed
}
