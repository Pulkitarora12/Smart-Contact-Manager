package com.scm.scm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.scm.scm.service.EmailService;

@RestController
@RequestMapping("/user/feedback")
public class FeedbackController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/submit")
    public RedirectView submitFeedback(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam(required = false) String rating,
            @RequestParam(required = false) String suggestion) {
        
        String message = String.format(
            "New Feedback Received:\nName: %s\nEmail: %s\nRating: %s\nSuggestion: %s",
            name, email, rating, suggestion
        );

        System.out.println(message);

        emailService.sendEmail("vrindabindal1212@gmail.com", "New Feedback Recieved", message);

        return new RedirectView("/user/feedback");
    }
}
