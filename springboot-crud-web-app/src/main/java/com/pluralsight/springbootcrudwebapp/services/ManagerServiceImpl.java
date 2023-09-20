package com.pluralsight.springbootcrudwebapp.services;

import com.pluralsight.springbootcrudwebapp.models.ManagerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ManagerServiceImpl implements ManagerService{

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public ResponseEntity<ManagerRequest> createManager(Long managerId, String firstName, String lastName) {
        ManagerRequest managerRequest=new ManagerRequest(managerId,firstName,lastName);
        ResponseEntity<ManagerRequest> managerResponse = restTemplate.postForEntity(
                "http://localhost:8081/api/v1/managers/saveManager", managerRequest, ManagerRequest.class);
        ManagerRequest createdManager = managerResponse.getBody();
        return ResponseEntity.ok(createdManager);
    }
}
