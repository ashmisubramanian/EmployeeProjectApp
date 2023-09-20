package com.pluralsight.springbootcrudwebapp.services;

import com.pluralsight.springbootcrudwebapp.models.ManagerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ManagerServiceImpl implements ManagerService{

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.url1}")
    private String baseUrl1;

    @Value("${api.url2}")
    private String baseUrl2;

    @Override
    public ResponseEntity<ManagerRequest> createManager(Long managerId, String firstName, String lastName) {
        ManagerRequest managerRequest=new ManagerRequest(managerId,firstName,lastName);
        ResponseEntity<ManagerRequest> managerResponse = restTemplate.postForEntity(
                baseUrl2+"/v1/managers", managerRequest, ManagerRequest.class);
        ManagerRequest createdManager = managerResponse.getBody();
        return ResponseEntity.ok(createdManager);
    }
}
