package com.scm.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.scm.entities.User;
import com.scm.scm.helper.Helper;
import com.scm.scm.service.UserService;

@ControllerAdvice
public class RootController {

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void AddLoggedInUser(Model model, Authentication authentication) {

        if (authentication == null) {
            return;
        }
        
        //fetching email when logged in
        String username = Helper.getEmailOfLoggedInUser(authentication);
        
        //fetching data from db once fetched email

        User user = userService.getUserByEmail(username);
        System.out.println(user.getName());
        System.out.println(user.getEmail());

        model.addAttribute("loggedInUser", user);
    }
}
