package com.scm.scm.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.scm.controller.ResourceNotFoundException;
import com.scm.scm.entities.Contact;
import com.scm.scm.entities.User;
import com.scm.scm.repositories.ContactRepository;
import com.scm.scm.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService {


    @Autowired
    private ContactRepository repo;

    @Override
    public Contact save(Contact contact) {
        
        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return repo.save(contact);
    }

    @Override
    public Contact update(Contact contact) {

        return new Contact();
    }

    @Override
    public List<Contact> getAll() {
        return repo.findAll();
    }

    @Override
    public Contact getById(String id) {
        return repo.findById(id).orElseThrow( () -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void delete(String id) {
        Contact contact = repo.findById(id).orElseThrow( () -> new ResourceNotFoundException("User not found"));
        

        repo.delete(contact);
    }

    @Override
    public List<Contact> getByUserId(String userId) {
        
        return repo.findByUserId(userId);
    }

    @Override
    public Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction) {
        
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page, size, sort); 

        return repo.findByUser(user, pageable);  
    }

    @Override
    public Page<Contact> searchByName(String name, int size, int page, String sortBy, String direction, User user) {

        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page, size, sort); 
        
        return repo.findByUserAndNameContaining(user, name, pageable);
    }

    @Override
    public Page<Contact> searchByEmail(String email, int size, int page, String sortBy, String direction, User user) {

        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        
        var pageable = PageRequest.of(page, size, sort); 
        
        return repo.findByUserAndEmailContaining(user, email, pageable);
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumber, int size, int page, String sortBy, String direction, User user) {
        
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page, size, sort); 
        
        return repo.findByUserAndPhoneNumberContaining( user, phoneNumber, pageable);
    }
    
    
}
