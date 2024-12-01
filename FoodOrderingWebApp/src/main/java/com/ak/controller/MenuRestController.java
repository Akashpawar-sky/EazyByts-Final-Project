package com.ak.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ak.entity.MenuItem;
import com.ak.repository.MenuItemRepository;

@RestController
@RequestMapping("/api/menu")
public class MenuRestController {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @GetMapping("/menu")
    public List<MenuItem> getMenuItems() {
        System.out.println("Fetching menu items...");
        return menuItemRepository.findAll();
    }

}

