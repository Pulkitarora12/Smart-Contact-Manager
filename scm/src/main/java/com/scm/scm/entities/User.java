package com.scm.scm.entities;

import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Builder;

@Entity
@Data
@Builder
public class User {

    @Id
    private String userId;

    @Column(name = "user_name", nullable = false, length = 255)
    private String name;

    @Column(unique = true, nullable = false, length = 255)
    private String email;

    @Column(length = 255)
    private String password;

    @Column(columnDefinition = "TEXT")
    private String about;

    @Column(columnDefinition = "TEXT")
    private String profileLink;

    @Column(length = 20)
    private String phoneNumber;

    private boolean enabled = false;
    private boolean emailVerified = false;
    private boolean phoneVerified = false;

    // How did user logged in
    private Providers provider;
    private String providerURL;

    @OneToMany(mappedBy="user", cascade=CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
    private List<Contact> contacts = new ArrayList<>();

}