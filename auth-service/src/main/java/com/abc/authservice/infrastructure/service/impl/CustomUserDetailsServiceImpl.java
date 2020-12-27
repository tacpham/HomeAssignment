package com.abc.authservice.infrastructure.service.impl;

import com.abc.authservice.domain.dto.CustomerProjection;
import com.abc.authservice.infrastructure.service.CustomUserDetailsService;
import com.abc.authservice.infrastructure.service.feign.CustomServiceProxy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service("customUserDetailsService")
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private CustomServiceProxy customServiceProxy;

    public CustomUserDetailsServiceImpl(CustomServiceProxy customServiceProxy) {
        this.customServiceProxy = customServiceProxy;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomerProjection customer = customServiceProxy.findByUsername(username);
        if (customer == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(customer.getUsername(), customer.getPassword(), Collections.emptyList());
    }
}
