package com.pluralsight.springbootcrudwebapp.services;

import com.pluralsight.springbootcrudwebapp.security.Role;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserDetailsService {
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
