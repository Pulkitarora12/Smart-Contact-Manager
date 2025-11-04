package com.scm.scm.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scm.scm.entities.User;
import com.scm.scm.repositories.PageRepository;

@Service
public class SecurityCustomDetailService implements UserDetailsService{

    @Autowired
    private PageRepository pageRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = pageRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        User user = optionalUser.get();

        // Add this check to block login if email not verified
        if (!user.isEnabled()) {
            throw new DisabledException("Please verify your email before logging in.");
        }

        return user;
    }
    
}
