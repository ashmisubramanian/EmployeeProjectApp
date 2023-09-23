package com.pluralsight.springbootcrudwebapp.services;

import com.pluralsight.springbootcrudwebapp.models.Registration;
import com.pluralsight.springbootcrudwebapp.repositories.RegistrationRepository;
import com.pluralsight.springbootcrudwebapp.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private RegistrationRepository registrationRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Registration user = registrationRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

       /* List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())) // Use getName() to get role name
                .collect(Collectors.toList());*/
        /*List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> {
                    String roleName = role.getName();
                    System.out.println("Role Name impl: " + roleName); // Add this line for debugging
                    return new SimpleGrantedAuthority(roleName);
                })
                .collect(Collectors.toList());*/
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> {
                    String roleName = role.getName();

                    // Remove the "ROLE_" prefix
                    if (roleName.startsWith("ROLE_")) {
                        roleName = roleName.substring("ROLE_".length());
                    }
                    System.out.println("Role Name impl: " + roleName);
                    return new SimpleGrantedAuthority(roleName);
                })
                .collect(Collectors.toList());
        return new User(user.getUserName(), user.getPassword(), authorities);
    }

}
