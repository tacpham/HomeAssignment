package com.abc.customerservice.web.rest;

import com.abc.customerservice.infrastructure.repository.ContactRepository;
import com.abc.customerservice.infrastructure.repository.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer-service")
public class CustomerRestApi {

    private CustomerRepository customerRepository;
    private ContactRepository contactRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomerRestApi(CustomerRepository customerRepository, ContactRepository contactRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.customerRepository = customerRepository;
        this.contactRepository = contactRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/findByUsername/{username}")
    public ResponseEntity<?> authenticateUser(@PathVariable String username) {
        return ResponseEntity.ok(customerRepository.findByUsername(username));
    }
}
