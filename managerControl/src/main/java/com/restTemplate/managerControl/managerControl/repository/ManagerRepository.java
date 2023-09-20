package com.restTemplate.managerControl.managerControl.repository;

import com.restTemplate.managerControl.managerControl.models.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager,Long> {

    List<Manager> findByManagerId(Long managerId);
}
