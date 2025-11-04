package com.scm.scm.repositories;

import java.util.*;
import org.springframework.stereotype.Repository;

import com.scm.scm.entities.Contact;
import com.scm.scm.entities.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String> {
    
    //FIND THE CONTACTS BY USER

    Page<Contact> findByUser(User user, Pageable pageable);

    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    List<Contact> findByUserId(@Param("userId") String userId);

    Page<Contact> findByUserAndNameContaining(User user, String nameKeyword, Pageable pageable);
    Page<Contact> findByUserAndEmailContaining(User user, String emailKeyword, Pageable pageable);
    Page<Contact> findByUserAndPhoneNumberContaining(User user, String phoneNumber, Pageable pageable);
}
