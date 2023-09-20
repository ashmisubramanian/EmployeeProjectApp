package com.pluralsight.springbootcrudwebapp.controller;

import com.pluralsight.springbootcrudwebapp.models.Employee;
import com.pluralsight.springbootcrudwebapp.models.EmployeeProjectReport;
import com.pluralsight.springbootcrudwebapp.models.ManagerRequest;
import com.pluralsight.springbootcrudwebapp.models.Project;
import com.pluralsight.springbootcrudwebapp.repositories.EmployeeRepository;
import com.pluralsight.springbootcrudwebapp.repositories.ProjectRepository;
import com.pluralsight.springbootcrudwebapp.repositories.PromotionRepository;
import com.pluralsight.springbootcrudwebapp.services.EmployeeService;
import com.pluralsight.springbootcrudwebapp.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private WebClient webClient;

    @GetMapping("/showEmployees")
    public @ResponseBody
    List<Employee>
    getEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    @GetMapping("/findEmployee/{id}")
    public @ResponseBody Optional<Employee> findEmployeeById(@PathVariable Long id){
        Optional<Employee> employee= employeeRepository.findById(id);
        return employee;
    }

    @PostMapping("/saveEmployee")
    public ResponseEntity<String> saveEmployeeWithProject(@RequestBody Employee employee){
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
                for (Project project: employee.getProjects()){
                    Long managerId=project.getManagerId();
                    String title=project.getTitle();
                    Employee emp=employeeRepository.getReferenceById(managerId);
                    ResponseEntity<ManagerRequest> managerResponse = managerService.createManager(managerId,emp.getFirstName(),emp.getLastName());
                }
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

    @DeleteMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable Long id){
        List<Project> projectsWithManagerId= projectRepository.findByManagerId(id);
        if(!projectsWithManagerId.isEmpty()){
            return "There are projects that contain employee with Id "+id+" as manager, so first update project or delete those projects";
        }
        else {
            employeeRepository.deleteById(id);
            return "Employee with Id "+id+" deleted";
        }
    }

    @GetMapping("/getAllEmployeesUsingJPAQL")
    public List<Employee> getAllEmployeesUsingJPAQL(){
        return employeeService.getAllEmployeesUsingJPAQL();
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

    //Get mapping using RestTemplate
    @GetMapping("/getManager/{managerId}")
    public ResponseEntity<ManagerRequest> getManager(@PathVariable Long managerId) {
        ResponseEntity<List<ManagerRequest>> managerResponse = restTemplate.exchange(
                "http://localhost:8081/api/v1/managers/findManager/" + managerId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ManagerRequest>>() {}
        );


        List<ManagerRequest> managers = managerResponse.getBody();

        if (managers != null && !managers.isEmpty()) {
            ManagerRequest manager = managers.get(0);
            return ResponseEntity.ok(manager);
        } else {
            return ResponseEntity.notFound().build();
        }


    }

}
