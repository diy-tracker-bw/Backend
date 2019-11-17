package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.exceptions.ResourceNotFoundException;
import com.lambdaschool.usermodel.models.Project;
import com.lambdaschool.usermodel.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class ProjectServiceImplementation implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Project save(Project project) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (project.getUser().getUsername().equalsIgnoreCase(authentication.getName()))
        {
            return projectRepository.save(project);
        }
        else
        {
            throw new ResourceNotFoundException((authentication.getName() + " Couldn't make changes"));
        }
    }

    @Override
    public Project update(Project project, long id) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<Project> returnAllProjects() {
        return null;
    }

    @Override
    public List<Project> returnProjectsByOwner(String username) {
        return null;
    }

    @Override
    public Project findProjectById(long id) {
        return null;
    }

    @Override
    public Project findByProjectName(String name) {
        return null;
    }
}
