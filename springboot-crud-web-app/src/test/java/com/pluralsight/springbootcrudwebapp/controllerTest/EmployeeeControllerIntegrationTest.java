package com.pluralsight.springbootcrudwebapp.controllerTest;

import com.pluralsight.springbootcrudwebapp.models.Employee;
import com.pluralsight.springbootcrudwebapp.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeeControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;


    @Test
    public void testShowNewEmployeeForm() throws Exception {
        Model model;

        mockMvc.perform(MockMvcRequestBuilders.get("/showNewEmployeeForm"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("new_employee"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("employee"));
    }

    @Test
    public void testShowFormForUpdate() throws Exception {
        long employeeId = 1L;

        Employee mockEmployee = new Employee();
        mockEmployee.setId(employeeId);
        mockEmployee.setFirstName("John");
        mockEmployee.setLastName("Doe");

        when(employeeService.getEmployeeById(employeeId)).thenReturn(mockEmployee);

        Model model;

        mockMvc.perform(MockMvcRequestBuilders.get("/showFormForUpdate/{id}", employeeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("update_employee"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("employee"));
    }


    @Test
    public void testDeleteEmployee() throws Exception{
        long employeeIdToDelete = 5L;

        doNothing().when(employeeService).deleteEmployeeById(employeeIdToDelete);

        mockMvc.perform(MockMvcRequestBuilders.get("/deleteEmployee/{id}", employeeIdToDelete))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }
}
/*
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testDeleteProduct() {
        // Arrange: Create a product in the database
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(19.99);
        productRepository.save(product);
        Long productId = product.getId();

        // Act: Send a POST request to delete the product
        String deleteUrl = "http://localhost:" + port + "/products/" + productId + "/delete";
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(deleteUrl, null, Void.class);

        // Assert: Verify the response and that the product has been deleted
        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode()); // Expect a redirect
        assertNull(productRepository.findById(productId).orElse(null));
    }
}

 */