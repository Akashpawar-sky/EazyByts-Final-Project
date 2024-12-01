package com.ak.controller;

import com.ak.entity.MenuItem;
import com.ak.repository.MenuItemRepository;
import com.ak.service.MenuItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Controller
public class MenuController {

    @Autowired
    private MenuItemRepository menuItemRepository;
    
   

    @GetMapping("/menu")
    public String showMenuPage(Model model) {
        // Fetch menu items from the database (you might already have this logic)
        List<MenuItem> menuItems = menuItemRepository.findAll();
        model.addAttribute("menuItems", menuItems);
        return "menu"; // This renders the `menu.html` template
    }
}

