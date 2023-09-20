package com.pluralsight.springbootcrudwebapp.services;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RegistrationServiceImpl implements RegistrationService{
    private static final String email_pattern="^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String password_pattern="^(?=.*[0-9])(?=.*[A-Z]).{8,}$";
    @Override
    public boolean validateEmail(String emailId) {
        Pattern pattern=Pattern.compile(email_pattern);
        Matcher matcher=pattern.matcher(emailId);
        return matcher.matches();
    }

    @Override
    public boolean validatePassword(String password) {
        Pattern pattern=Pattern.compile(password_pattern);
        Matcher matcher=pattern.matcher(password);
        return matcher.matches();
    }
}
