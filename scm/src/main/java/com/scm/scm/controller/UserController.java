package com.scm.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    
    //user dashboard page
    @GetMapping("/dashboard") 
    public String userDashboard() {
        return "user/dashboard";
    }

    //user profile page
    @GetMapping("/profile")
    public String userProfile() {
        return "user/profile";
    }
    
    //user add contactpage


    //user view conatacts page


    //user edit contact page


    //user delete contact page
}
