package com.pluralsight.springbootcrudwebapp.services;

import com.pluralsight.springbootcrudwebapp.models.ManagerRequest;
import org.springframework.http.ResponseEntity;

public interface ManagerService {
    public ResponseEntity<ManagerRequest> createManager(Long managerId,String firstName,String lastName);
}
