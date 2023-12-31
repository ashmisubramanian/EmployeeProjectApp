package com.pluralsight.springbootcrudwebapp.controller;


import com.pluralsight.springbootcrudwebapp.models.Project;
import com.pluralsight.springbootcrudwebapp.repositories.EmployeeRepository;
import com.pluralsight.springbootcrudwebapp.repositories.ProjectRepository;
import com.pluralsight.springbootcrudwebapp.services.EmployeeService;
import com.pluralsight.springbootcrudwebapp.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {
   @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ProjectService projectService;


    public ProjectRepository getProjectRepository() {
        return projectRepository;
    }

    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public List<Project> listall(){return projectRepository.findAll();}

    @GetMapping(params = "id")
    public @ResponseBody Optional<Project> findManagerById(@RequestParam(name = "id") Long id){
        Optional<Project> manager= projectRepository.findById(id);
        return manager;
    }


    /*@PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody Project project){
        Long mid=project.getManagerId();
        Optional<Employee> manager = employeeRepository.findById(mid);
        Employee emp= manager.get();
        if (manager.isPresent()) {

            //project.setEmployee(emp);
            // Manager exists, save the project
            Project savedProject=projectRepository.save(project);
            return ResponseEntity.ok("Project created with title: " + savedProject.getTitle());
        } else {
            // Manager does not exist, return an error response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Manager with ID " + mid + " does not exist.");
        }

    }*/



    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Project updateProject){
        Optional<Project> projectPresent= projectRepository.findById(id);

        if(projectPresent.isPresent()){
            Project project=projectPresent.get();
            project.setTitle(updateProject.getTitle());
            project.setManagerId(updateProject.getManagerId());
            projectRepository.saveAndFlush(project);
            return ResponseEntity.ok("Project with id "+id+" updated successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Project with ID " + id + " does not exist.");
        }
    }



    @DeleteMapping("/{id}")
    public String deleteProject(@PathVariable Long id){
        Optional<Project> projects=projectRepository.findById(id);
        if(projects.isPresent()){
            projectRepository.deleteById(id);
            return "Project with Id "+id+" deleted";
        }
        else {
            return "Project with Id "+id+" doesn't exist";
        }
    }

    @GetMapping(params = "managerId")
    public @ResponseBody List<Project> findManagerByManagerId(@RequestParam(name = "managerId") Long managerId){
        List<Project> manager= projectRepository.findByManagerId(managerId);
        return manager;
    }


    @GetMapping("/getAllProjectsUsingJPAQL")
    public List<Project> getAllProjectsUsingJPAQL(){
        return projectService.getAllProjectsUsingJPAQL();
    }


}
