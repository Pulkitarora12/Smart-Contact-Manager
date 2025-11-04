package com.scm.scm.service.impl;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.scm.controller.ResourceNotFoundException;
import com.scm.scm.entities.User;
import com.scm.scm.helper.AppConstants;
import com.scm.scm.repositories.PageRepository;
import com.scm.scm.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final PageRepository pageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    @Override
    public User saveUser(User user) {

        //user id is meant to be generated before saving

        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);

        //encoding password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        //set user roles
        user.setRoleList(List.of(AppConstants.ROLE_USER));

        return pageRepository.save(user); // Logic to save user
    }

    @Override
    public Optional<User> getUserById(String id) {
        
        return pageRepository.findById(id); // Logic to get user by ID
    }

    @Override
    public Optional<User> updateUser(User user) {
        
        //fetch user from database
        User user2 =  pageRepository.findById(user.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        //update user 
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfileLink(user.getProfileLink());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderURL(user.getProviderURL());

        //save to database
        User save = pageRepository.save(user2);
        return Optional.ofNullable(save);  //returns null if empty
    }

    @Override
    public void deleteUser(String id) {
        //fetch
        User user2 = pageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        //delete
        pageRepository.delete(user2); 
    }

    @Override
    public boolean isUserExists(String userId) {
        //fetch user
        User user2 = pageRepository.findById(userId).orElse(null);

        return user2 != null;
    }

    @Override
    public boolean isUserExistsByEmail(String email) {
        User user2 = pageRepository.findByEmail(email).orElse(null);

        return user2 != null;
    }

    @Override
    public List<User> getAllUsers() {
        return pageRepository.findAll(); 
    }
}
