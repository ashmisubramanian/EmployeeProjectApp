package com.pluralsight.springbootcrudwebapp.controller;

import com.pluralsight.springbootcrudwebapp.models.Login;
import com.pluralsight.springbootcrudwebapp.models.Registration;
import com.pluralsight.springbootcrudwebapp.repositories.RegistrationRepository;
import com.pluralsight.springbootcrudwebapp.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/register")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private RegistrationRepository registrationRepository;

    private final PasswordEncoder passwordEncoder;

    public RegistrationController(PasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
    }

    @PostMapping("/registerForNew")
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
        registration.setPassword(passwordEncoder.encode(registration.getPassword()));
        registrationRepository.save(registration);
        return  ResponseEntity.ok("Successfully saved");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginValidation(@RequestBody Login login){
        Registration user=registrationRepository.findByUserName(login.getUserName());
        if(user!=null&&passwordEncoder.matches(login.getPassword(),user.getPassword())){
            return ResponseEntity.ok("Login Successful");
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Login");
        }
    }
}
