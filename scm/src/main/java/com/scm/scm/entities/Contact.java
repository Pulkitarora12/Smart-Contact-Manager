package com.scm.scm.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    private String id;

    @Column(name = "contact_name", nullable = false, length = 255)
    private String name;

    @Column(unique = true, nullable = false, length = 255)
    private String email;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 1000)
    private String address;

    @Column(length=1000)
    private String description;

    
    private boolean favorite = false;
    
    private String websiteLink;
    private String linkedinLink;
    private String profilePicture;

    private String cloudinaryImagePublicId;

    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy="contact", cascade=CascadeType.ALL, orphanRemoval=true, fetch = FetchType.EAGER)
    private List<SocialLink> socialLinks = new ArrayList<>();
}
