package com.ak.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ak.entity.User;
import com.ak.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // Displays the registration form
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        // Check if email already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            model.addAttribute("errorMessage", "Email already exists!");
            return "register"; // Stay on the same page with error message
        }
        // Save user to the database
        userRepository.save(user);
        model.addAttribute("successMessage", "Registration successful! Please login.");
        return "login"; // Redirect to login page with a success message
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        return "login"; // Displays the login form
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user != null) {
            session.setAttribute("loggedInUser", user.getUsername()); // Store email in session
            return "redirect:/"; // Redirect to the main page
        }
        model.addAttribute("errorMessage", "Invalid credentials. Please try again.");
        return "login"; // Return to login page with error message
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate(); // Clear the session
        return "redirect:/"; // Redirect to the main page
    }
}
