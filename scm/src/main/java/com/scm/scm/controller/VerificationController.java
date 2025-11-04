package com.scm.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.scm.entities.User;
import com.scm.scm.service.UserService;

import jakarta.servlet.http.HttpSession;

    import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/auth")
public class VerificationController {

    @Autowired
    private UserService userService;

    public VerificationController() {
        System.out.println("VerificationController detected");
    }

    @GetMapping("/register")
    public String register(Model model, HttpSession session) {
        
        return "register"; // returns register.html template
    }

    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        
        return "login";
    }
    
    @GetMapping("/verify-email")
    public String verifyEmail(
        @RequestParam String token,
        HttpSession session,
        Model model
    ) {

        System.out.println("Verification completed");

        User user = userService.getUserByEmailToken(token);

        if (user != null) {

            if (user.getEmailToken().equals(token)) {

                user.setEmailVerified(true);
                user.setEnabled(true);
                userService.updateUser(user);

                session.setAttribute("message", "User Verification successful!");

                String message = (String) session.getAttribute("message");
                if (message != null) {
                    model.addAttribute("message", message);
                    session.removeAttribute("message"); // This clears it for next refresh

                }

                return "success_page";
            }

            session.setAttribute("message", "Verification Failed!");

            String message = (String) session.getAttribute("message");
            
            if (message != null) {
                model.addAttribute("message", message);
                session.removeAttribute("message"); // This clears it for next refresh
            }

            return "error_page";

        }

        session.setAttribute("message", "Verification Failed!");

        String message = (String) session.getAttribute("message");
        if (message != null) {
            model.addAttribute("message", message);
            session.removeAttribute("message"); // This clears it for next refresh
        }

        return "error_page";

    }

}
