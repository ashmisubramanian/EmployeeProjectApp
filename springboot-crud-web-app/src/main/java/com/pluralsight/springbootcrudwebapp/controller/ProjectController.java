package com.pluralsight.springbootcrudwebapp.controller;


import com.pluralsight.springbootcrudwebapp.models.Employee;
import com.pluralsight.springbootcrudwebapp.models.EmployeeProjectReport;
import com.pluralsight.springbootcrudwebapp.models.Project;
import com.pluralsight.springbootcrudwebapp.repositories.EmployeeRepository;
import com.pluralsight.springbootcrudwebapp.repositories.ProjectRepository;
import com.pluralsight.springbootcrudwebapp.services.EmployeeService;
import com.pluralsight.springbootcrudwebapp.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

    @GetMapping("/showProjects")
    public List<Project> listall(){return projectRepository.findAll();}

    @GetMapping("/showEmployees")
    public @ResponseBody
    List<Employee>
    getEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
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
    @PostMapping("/saveEmployee")
    public ResponseEntity<String>saveEmployeeWithProject(@RequestBody Employee employee){
        Long empId=employee.getId();
        int trueCount=0;
        if (!employee.getProjects().isEmpty()){
            int projectCount = employee.getProjects().size();
            System.out.println("Number of projects: " + projectCount);
            for(int i=0;i<projectCount;i++){
                Project firstProject = employee.getProjects().get(i);
                Long mid=firstProject.getManagerId();
                Optional<Employee> manager = employeeRepository.findById(mid);
                if (manager.isPresent()|| mid.equals(empId)) {
                    trueCount++;
                }
                else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Manager with ID " + mid + " does not exist.");
                }
            }
            List<String> projectResponses = new ArrayList<>();
            if (trueCount==projectCount){
                employeeRepository.save(employee);
            }
        }
        else {
            employeeRepository.save(employee);
        }
        return ResponseEntity.ok("Employee added successfully    successfully");
        //return ResponseEntity.ok("Projects created with title: " + employee.getProjects());
    }

    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Employee updateEmployee){
        Optional<Employee> employeePresent= employeeRepository.findById(id);

        if(employeePresent.isPresent()){
            Employee employee=employeePresent.get();
            employee.setFirstName(updateEmployee.getFirstName());
            employee.setLastName(updateEmployee.getLastName());
            employee.setEmail(updateEmployee.getEmail());
            employee.setProjects(updateEmployee.getProjects());
            employeeRepository.saveAndFlush(employee);
            return ResponseEntity.ok("Employee with id "+id+" updated successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee with ID " + id + " does not exist.");
        }
    }

    @PutMapping("/updateProject/{id}")
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

    /*@DeleteMapping("/deleteEmployee/{id}")
    public void delete(@PathVariable Long id){
        employeeRepository.deleteById(id);

    }*/

    @GetMapping("/getAllEmployeesUsingJPAQL")
    public List<Employee> getAllEmployeesUsingJPAQL(){
        return employeeService.getAllEmployeesUsingJPAQL();
    }
    @GetMapping("/getAllProjectsUsingJPAQL")
    public List<Project> getAllProjectsUsingJPAQL(){
        return projectService.getAllProjectsUsingJPAQL();
    }

    @GetMapping("/getAllEmployeesNameTitleUsingJPAQL")
    public List<EmployeeProjectReport> getAllEmployeesNameTitleUsingJPAQL(){
        return employeeService.getAllEmployeesNameTitleUsingJPAQL();
    }

    @GetMapping("/employeeNameStartWith")
    @ResponseBody public List<Employee> getAllNameStartWith(){
        List<Employee> employees=employeeService.getAllEmployees();
        return employees.stream().filter(emp->emp.getFirstName().toLowerCase().startsWith("b")).collect(Collectors.toList());
    }

    @GetMapping("/employeeFirstNameLastName")
    @ResponseBody
    public List<String> getAllTitle()
    {
        List<Employee> employees = employeeService.getAllEmployees();
        List<String> firstNameLastName = employees.stream().filter(emp->emp.getFirstName().toLowerCase().startsWith("b")).map(employee -> employee.getFirstName()+" "+employee.getLastName()).collect(Collectors.toList());
        return firstNameLastName;
    }
}
