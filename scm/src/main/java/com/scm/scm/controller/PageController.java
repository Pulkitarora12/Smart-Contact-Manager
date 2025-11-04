package com.scm.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.scm.scm.config.AppProperties;
import com.scm.scm.entities.User;
import com.scm.scm.form.UserForm;
import com.scm.scm.service.impl.UserServiceImpl;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PageController {

    private UserServiceImpl userService;
    private AppProperties appProperties;
    private String defaultProfilePic;

    @Autowired
    public PageController(UserServiceImpl userService, AppProperties appProperties) {
        this.userService = userService; 
        this.appProperties = appProperties;
        this.defaultProfilePic = appProperties.getDefaultProfilePic();
    }

    @GetMapping("/")
    public String redirectHome() {
        return "home";
    }

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

    //this is showing login page
    @GetMapping("/login")
    public String login() {
        return "login"; // This will render login.html from templates
    }

    //registeration page
    @GetMapping("/register")
    public String register(Model model, HttpSession session) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        String message = (String) session.getAttribute("message");
        if (message != null) {
            model.addAttribute("message", message);
            session.removeAttribute("message"); // This clears it for next refresh
        }
        return "register"; // This will render register.html from templates
    }

    //processing registeration
    @PostMapping("/do-register")
    public String processRegister(@Valid UserForm userForm,BindingResult result,HttpSession session) {
        
        //fetch data from form
        System.out.println(userForm);

        //validate form data
        if (result.hasErrors()) {
            return "register"; // no need to redirect just return to same page
        }

        //save to database
        // User user = User  we will not use builder as it will not save default values
        //             .builder()
        //             .name(userForm.getName())
        //             .email(userForm.getEmail())
        //             .password(userForm.getPassword())
        //             .about(userForm.getAbout())
        //             .phoneNumber(userForm.getPhoneNumber())
        //             .profileLink(defaultProfilePic)
        //             .build();
        
        //creating user object and saving manually
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfileLink(defaultProfilePic);
        user.setEnabled(false);

        User savedUser = userService.saveUser(user);

        //message that info is saved
        session.setAttribute("message", "User registered successfully!");


        //redirect to login page
        return "redirect:/register"; 
    }
}

