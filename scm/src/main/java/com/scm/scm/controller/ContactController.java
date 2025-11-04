package com.scm.scm.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.scm.entities.Contact;
import com.scm.scm.entities.User;
import com.scm.scm.form.ContactForm;
import com.scm.scm.helper.AppConstants;
import com.scm.scm.helper.Helper;
import com.scm.scm.service.ContactService;
import com.scm.scm.service.ImageService;
import com.scm.scm.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



@Controller
@RequestMapping("/user/contact")
public class ContactController {

    Logger logger = LoggerFactory.getLogger("ContactController.class");

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;
    
    //get mapping is used to open the view on clicking add contact
    @GetMapping("/add")
    public String addContact(Model model, HttpSession session) {
        
        //adding contact form to model to further process
        ContactForm form = new ContactForm();
        model.addAttribute("contactForm", form);

        String message = (String) session.getAttribute("message");
        if (message != null) {
            model.addAttribute("message", message);
            session.removeAttribute("message"); // This clears it for next refresh
        }

        return "user/add_contact";
    }
    
    //post mapping is used to submit the form
    @PostMapping("/add")
    public String saveContact(@ModelAttribute @Valid ContactForm contactForm,
                            BindingResult result,
                            Authentication authentication,
                            HttpSession session,
                            Model model) {

        if (result.hasErrors()) {

            result.getAllErrors().forEach((error) -> logger.info(error.toString()));
            model.addAttribute("contactForm", contactForm);
            model.addAttribute("message","Please resolve the errors"); //because error is not redirecting so its not a new session hence adding to same model

            return "user/add_contact";
        }

        //even after redirecting we will have the user
        String email = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(email);
        Contact contact = new Contact();

        //fetching image 
        logger.info("fetching image details : {}", contactForm.getProfilePicture().getOriginalFilename());

        String filename = UUID.randomUUID().toString();

        String fileURL = imageService.uploadImage(contactForm.getProfilePicture(), filename);

        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setFavorite(contactForm.isFavourite());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedinLink(contactForm.getLinkedinLink());
        contact.setCloudinaryImagePublicId(filename);
        contact.setUser(user);

        if (fileURL == null) {
            contact.setProfilePicture(null);
        } else {
            contact.setProfilePicture(fileURL);
        }

        contactService.save(contact);

        session.setAttribute("message","You have successfully added a new contact");


        return "redirect:/user/contact/add";
    }

    //view contacts
    @GetMapping("")
    public String viewContacts(
        @RequestParam(value="page", defaultValue="0") int page,
        @RequestParam(value="size", defaultValue=AppConstants.PAGE_SIZE + "") int size,
        @RequestParam(value="sortBy", defaultValue="name") String sortBy,
        @RequestParam(value="direction", defaultValue="asc") String direction,
        Model model, Authentication authentication) {

        //load all the user contacts
        //1. getting email of logged in user
        String username = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("Fetching contacts for user: {}", username);
        
        //2.fetching user object by email
        User user = userService.getUserByEmail(username);
        logger.info("User found: {}", user != null ? user.getUserId() : "null");
        
        //3.fetching list of all the contacts for the user
        Page<Contact> pageContact = contactService.getByUser(user, page, size, sortBy, direction);
        logger.info("Contacts found: {}, Total pages: {}", pageContact.getTotalElements(), pageContact.getTotalPages());

        //4.adding list to the model to fetch at frontend
        model.addAttribute("pageContact", pageContact); 
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE); 
        

        return "user/contact";
    }

    //search handler

    @GetMapping("/search")
    public String searchHandler(@RequestParam("field") String field,
                                @RequestParam("keyword") String keyword,
                                @RequestParam(value="page", defaultValue="0") int page,
                                @RequestParam(value="size", defaultValue=AppConstants.PAGE_SIZE + "") int size,
                                @RequestParam(value="sortBy", defaultValue="name") String sortBy,
                                @RequestParam(value="direction", defaultValue="asc") String direction,
                                Model model, Authentication authentication) {

        logger.info("field {} keyword {}", field, keyword);

        Page<Contact> pageContact = null;

        var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        if (field.equalsIgnoreCase("name")) {
            pageContact = contactService.searchByName(keyword, size, page, sortBy, direction, user);
        } else if (field.equalsIgnoreCase("email")) {
            pageContact = contactService.searchByEmail(keyword, size, page, sortBy, direction, user);
        } else {
           pageContact = contactService.searchByPhoneNumber(keyword, size, page, sortBy, direction, user); 
        }

        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE); 

        model.addAttribute("field", field);
        model.addAttribute("keyword", keyword);

        return "user/search";
    }

    @GetMapping("/delete/{id}")
    public String deleteContact(@PathVariable String id) {

        contactService.delete(id);
        logger.info("contact deleted {}", id);

        return "redirect:/user/contact";
    }

    @GetMapping("/view/{contactId}")
    public String updateContact(@PathVariable String contactId, Model model) {

        var contact = contactService.getById(contactId); 

        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavourite(contact.isFavorite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedinLink(contact.getLinkedinLink());

        model.addAttribute("contactForm", contactForm); 
        model.addAttribute("contactId", contactId); 

        return "user/update_contact_view";

    }

    @PostMapping("/update/{contactId}")
    public String updateContact(@ModelAttribute @Valid ContactForm contactForm,
                            @PathVariable String contactId,
                            BindingResult result,
                            Authentication authentication,
                            HttpSession session,
                            Model model) {

        if (result.hasErrors()) {

            result.getAllErrors().forEach((error) -> logger.info(error.toString()));
            model.addAttribute("contactForm", contactForm);
            model.addAttribute("message","Please resolve the errors"); //because error is not redirecting so its not a new session hence adding to same model

            return "user/add_contact";
        }

        //even after redirecting we will have the user
        String email = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(email);
        Contact contact = new Contact();

        //fetching image 
        logger.info("fetching image details : {}", contactForm.getProfilePicture().getOriginalFilename());

        String filename = UUID.randomUUID().toString();

        String fileURL = imageService.uploadImage(contactForm.getProfilePicture(), filename);

        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setFavorite(contactForm.isFavourite());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedinLink(contactForm.getLinkedinLink());
        contact.setCloudinaryImagePublicId(filename);
        contact.setUser(user);

        if (fileURL == null) {
            contact.setProfilePicture(null);
        } else {
            contact.setProfilePicture(fileURL);
        }

        deleteContact(contactId);

        contactService.save(contact);

        System.out.println(contactId);

        session.setAttribute("message","You have successfully updated a contact");
        logger.info("contact updated");

        return "user/contact";
    }
}


