package com.scm.scm.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.scm.entities.User;


@Repository
public interface PageRepository extends JpaRepository<User, String> {
    // Define any custom query methods if needed

    Optional<User> findByEmail(String email);
    
    Optional<User> findByEmailAndPassword(String email, String password);
}
