package com.pluralsight.springbootcrudwebapp.controller;

import com.pluralsight.springbootcrudwebapp.models.Login;
import com.pluralsight.springbootcrudwebapp.models.LoginResponse;
import com.pluralsight.springbootcrudwebapp.models.Registration;
import com.pluralsight.springbootcrudwebapp.repositories.RegistrationRepository;
import com.pluralsight.springbootcrudwebapp.security.JwtIssuer;
import com.pluralsight.springbootcrudwebapp.security.Role;
import com.pluralsight.springbootcrudwebapp.security.UserPrincipal;
import com.pluralsight.springbootcrudwebapp.services.RegistrationService;
import com.pluralsight.springbootcrudwebapp.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private RegistrationRepository registrationRepository;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    private JwtIssuer jwtIssuer;


    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    private ResponseEntity<String> registerForNew(@RequestBody Registration registration){

        if(!registrationService.validatePassword(registration.getPassword())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password validation Failure.(Password should contain atleast one number,one uppercase and minimum 8 characters)");
        }
        else if(!registrationService.validateEmail(registration.getEmailId())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email validation Failure.");
        }
        //if(registrationRepository.findByPassword(passwordEncoder.encode(registration.getPassword()))){}
        Registration existingUser=registrationRepository.findByUserName(registration.getUserName());
        if(existingUser!=null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already existing username, use a valid one.");
        }
        System.out.println(registration.getRoles());
        System.out.println(registration.getUsername());
        registration.setPassword(passwordEncoder.encode(registration.getPassword()));
        registrationRepository.save(registration);
        return  ResponseEntity.ok("Successfully saved");
    }
    @PostMapping("/login")
    public LoginResponse loginValidation(@RequestBody @Validated Login login){
        var authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUserName(),login.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal=(UserPrincipal)authentication.getPrincipal();
        System.out.println("in reg before role:"+principal.getAuthorities());
        var roles=principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        System.out.println("In reg role:"+roles);
        var token=jwtIssuer.issue(principal.getUserId(), principal.getUsername(), principal.getPassword(),roles);
        return LoginResponse.builder()
                .accessToken(token)
                .build();
    }
}
