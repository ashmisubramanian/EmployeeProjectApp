package com.pluralsight.springbootcrudwebapp.repositories;

import com.pluralsight.springbootcrudwebapp.models.Employee;
import com.pluralsight.springbootcrudwebapp.models.EmployeeProjectReport;
import com.pluralsight.springbootcrudwebapp.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("select e from Employee e where e.firstName='Bob'")
    List<Employee> getAllEmployeesUsingJPAQL();

    @Query("select new com.pluralsight.springbootcrudwebapp.models.EmployeeProjectReport(e.firstName, e.lastName, p.title) from Employee e join e.projects p")
    List<EmployeeProjectReport> getAllEmployeesNameTitleUsingJPAQL();


}
