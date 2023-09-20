package com.pluralsight.springbootcrudwebapp.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@AllArgsConstructor
@NoArgsConstructor
public class ManagerRequest {
    private Long id;
    private Long managerId;
    private String firstName;
    private String lastName;

    //private String title;

    /*public ManagerRequest() {
        // Default constructor
    }*/

    public ManagerRequest(Long managerId, String firstName, String lastName) {
        this.managerId = managerId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

   /* public ManagerRequest(Long id, Long managerId, String firstName, String lastName,String title) {
        this.id = id;
        this.managerId = managerId;
        this.firstName = firstName;
        this.lastName = lastName;
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /*public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }*/
/*private static final String GET_MANAGERS_ENDPOINT_URL = "http://localhost:8080/api/v1/managers/showManagers";
    private static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        ManagerRequest managerRequest=new ManagerRequest();
        managerRequest.getManagers();
    }

    private void getManagers() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity< String > entity = new HttpEntity < String > ("parameters", headers);

        ResponseEntity< String > result = restTemplate.exchange(GET_MANAGERS_ENDPOINT_URL, HttpMethod.GET, entity,
                String.class);

        System.out.println(result);
    }*/

}
