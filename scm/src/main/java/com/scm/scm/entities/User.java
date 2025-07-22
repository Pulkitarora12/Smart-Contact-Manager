package com.scm.scm.entities;

import java.util.*;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder 
@NoArgsConstructor
@AllArgsConstructor 
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

    @Enumerated(value=EnumType.STRING)
    private Providers provider = Providers.SELF;
    private String providerURL;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Contact> contacts = new ArrayList<>();
}
