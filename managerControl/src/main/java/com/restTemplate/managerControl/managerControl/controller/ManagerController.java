package com.restTemplate.managerControl.managerControl.controller;

import com.restTemplate.managerControl.managerControl.models.Manager;
import com.restTemplate.managerControl.managerControl.models.ProjectRequest;
import com.restTemplate.managerControl.managerControl.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/managers")
public class ManagerController {
    @Autowired
    private ManagerRepository managerRepository;


    @Autowired
    private WebClient webClient;

    @Value("${api.url1}")
    private String baseUrl1;

    @Value("${api.url2}")
    private String baseUrl2;

    @GetMapping
    public @ResponseBody List<Manager> getManagers(){
        List<Manager> managers=managerRepository.findAll();
        return managers;
    }

    @GetMapping(params = "id")
    public @ResponseBody Optional<Manager> findManagerById(@RequestParam(name = "id") Long id){
        Optional<Manager> manager= managerRepository.findById(id);
        System.out.println(manager);
        return manager;
    }
    @GetMapping(params = "managerId")
    public @ResponseBody List<Manager> findManagerByManagerId(@RequestParam(name = "managerId") Long managerId){
        List<Manager> manager= managerRepository.findByManagerId(managerId);
        System.out.println(manager);
        return manager;
    }
    @PostMapping
    public Manager saveManager(@RequestBody Manager manager){
        List<Manager> manager1=managerRepository.findByManagerId(manager.getManagerId());
        if(manager1.isEmpty()){
            return managerRepository.save(manager);
            //return ResponseEntity.ok("Manager with id "+manager.getManagerId()+" added Successfully.");
        }
        return manager;
    }


    @GetMapping("/showAllProjectsManagedBy/{managerId}")
    public Flux<ProjectRequest> getmanager(@PathVariable Long managerId){
        return webClient.get()
                .uri(baseUrl1+"/v1/projects/getProjectsByManagerId/" + managerId)
                .retrieve().bodyToFlux(ProjectRequest.class);
    }
}
