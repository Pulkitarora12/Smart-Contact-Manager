package com.scm.scm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scm.scm.repositories.PageRepository;

@Service
public class SecurityCustomDetailService implements UserDetailsService{

    @Autowired
    private PageRepository pageRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        //apne user ko load karana hai

        return pageRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

    }
    
}
