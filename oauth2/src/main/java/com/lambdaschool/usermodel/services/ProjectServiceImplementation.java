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
            return projectRepository.save(project);
    }

    @Override
    public Project update(Project oldProject, long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Project updatedProject = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project id " + id + " not found"));

        if (projectRepository.findById(id).get().getUser().getUsername().equalsIgnoreCase(authentication.getName()))
        {
            if (oldProject.getPhotoUrl()!= null)
            {
                updatedProject.setPhotoUrl(oldProject.getPhotoUrl());
            }

            if (oldProject.getProjectName() != null)
            {
                updatedProject.setProjectName(oldProject.getProjectName());
            }

            if (oldProject.getDescription() != null)
            {
                updatedProject.setDescription(oldProject.getDescription());
            }

            return projectRepository.save(updatedProject);
        }
        else
        {
            throw new ResourceNotFoundException("Current project's id does not belong to current user.");
        }
    }

    @Override
    public void delete(long id) {
        if (projectRepository.findById(id).isPresent()) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (projectRepository.findById(id).get().getUser().getUsername().equalsIgnoreCase(authentication.getName()))
            {
                projectRepository.deleteById(id);
            }
            else
            {
                throw new ResourceNotFoundException("Delete Failed. Only the user that made this project may delete this.");
            }
        }
        else
        {
            throw new ResourceNotFoundException("Project id " + id + " not found");
        }
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
