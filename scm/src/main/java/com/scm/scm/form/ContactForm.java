package com.scm.scm.form;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactForm {
    
    @NotBlank(message = "Name is required")
    @Size(min=3, message = "Name must be at least 3 characters long")
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;
    
    @Size(min=8, max=12, message="Invalid Phone Number")
    @Pattern(regexp="^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;
    
    @Size(min=3, max=1000, message="Invalid Address")
    private String address;
    

    private String description;

    private boolean favourite;
    
    private String websiteLink;
    
    private String linkedinLink;
    
    //annotation that will validate file size and resolution
    private MultipartFile profilePicture;

}

