package com.pluralsight.springbootcrudwebapp.services;

import com.pluralsight.springbootcrudwebapp.models.Project;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjectsUsingJPAQL();
}
