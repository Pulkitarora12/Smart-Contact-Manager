package com.scm.scm.entities;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Contact {

    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    @Column(length=1000)
    private String description;
    private boolean favorite = false;
    
    private String websiteLink;
    private String linkedinLink;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy="contact", cascade=CascadeType.ALL, orphanRemoval=true, fetch = FetchType.EAGER)
    private List<SocialLink> socialLinks = new ArrayList<>();
}
