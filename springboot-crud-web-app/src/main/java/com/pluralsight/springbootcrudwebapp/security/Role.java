package com.pluralsight.springbootcrudwebapp.security;

public enum Role {
    ROLE_ADMIN,
    ROLE_USER;

    public String getName() {
        return this.name();
    }
}
