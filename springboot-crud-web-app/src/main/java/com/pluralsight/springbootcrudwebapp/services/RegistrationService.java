package com.pluralsight.springbootcrudwebapp.services;

public interface RegistrationService {

    boolean validateEmail(String emailId);
    boolean validatePassword(String password);
}
