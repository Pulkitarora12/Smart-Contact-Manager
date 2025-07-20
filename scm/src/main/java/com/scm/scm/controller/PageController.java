package com.scm.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("name", "Pulkit Arora");
        model.addAttribute("Github", "https://github.com/Pulkitarora12/Smart-Contact-Manager");
        return "home"; // This will render home.html from templates
    }

    @GetMapping("/about")
    public String about() {
        return "about"; // This will render about.html from templates
    }

    @GetMapping("/services")
    public String service() {
        return "services"; // This will render service.html from templates
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact"; // This will render contact.html from templates
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // This will render login.html from templates
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // This will render register.html from templates
    }
}

