package com.pluralsight.springbootcrudwebapp.controllerTest;

import com.pluralsight.springbootcrudwebapp.models.Employee;
import com.pluralsight.springbootcrudwebapp.models.Project;
import com.pluralsight.springbootcrudwebapp.repositories.EmployeeRepository;
import com.pluralsight.springbootcrudwebapp.repositories.ProjectRepository;
import com.pluralsight.springbootcrudwebapp.controller.ProjectController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProjectControllerTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListAllProjects() {
        // Arrange
        List<Project> projectList = new ArrayList<>();
        projectList.add(new Project());
        projectList.add(new Project());
        when(projectRepository.findAll()).thenReturn(projectList);

        // Act
        List<Project> result = projectController.listall();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    public void testCreateProject() {
        // Arrange
        Project project = new Project();
        project.setTitle("Test Project");
        project.setManagerId(1L);

        Employee manager = new Employee();
        manager.setId(1L);
        manager.setFirstName("vishal");
        manager.setLastName("p");
        manager.setEmail("vishal@gmail.com");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(manager));
        when(projectRepository.save(project)).thenReturn(project);

        // Act
        ResponseEntity<String> response = projectController.create(project);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Project created with title: Test Project", response.getBody());
    }

    @Test
    public void testCreateProjectWithNonExistentManager() {
        // Arrange
        Project project = new Project();
        project.setManagerId(2L); // Manager with ID 2 does not exist in the mock

        when(employeeRepository.findById(2L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = projectController.create(project);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Manager with ID 2 does not exist.", response.getBody());
    }
}

