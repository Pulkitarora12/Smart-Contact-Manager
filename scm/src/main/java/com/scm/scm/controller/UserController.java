package com.scm.scm.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.scm.entities.User;
import com.scm.scm.helper.Helper;
import com.scm.scm.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    
    //user dashboard page
    @GetMapping("/dashboard") 
    public String userDashboard() {
        return "user/dashboard";
    }

    //user profile page
    @GetMapping("/profile")
    public String userProfile(Model model, Authentication authentication) {
        return "user/profile";
    }
    
    //user add contactpage


    //user view conatacts page


    //user edit contact page


    //user delete contact page

    @GetMapping("/feedback")
    public String feedback() {
        return "user/feedback";
    }
}
