package com.pluralsight.springbootcrudwebapp.models;


import lombok.Builder;

@Builder
public class LoginResponse {
    private String accessToken;
    public LoginResponse() {
        // No-argument constructor
    }

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }


    public String getAccessToken() {
        return accessToken;
    }
}
