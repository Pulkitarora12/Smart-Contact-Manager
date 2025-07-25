package com.scm.scm.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.scm.scm.entities.User;

public interface UserService {
    
    User saveUser(User user);
    
    Optional<User> getUserById(String id);
    
    Optional<User> updateUser(User user);
    
    void deleteUser(String id);
    
    boolean isUserExists(String userId);
    
    boolean isUserExistsByEmail(String email);
    
    List<User> getAllUsers();

}
