package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.exceptions.ResourceNotFoundException;
import com.lambdaschool.usermodel.models.Project;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "projectService")
public class ProjectServiceImplementation implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Project save(Project project) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        if (project.getUser()
                .getUsername()
                .equalsIgnoreCase(authentication.getName()))
        {
            return projectRepository.save(project);
        } else
        {
            throw new ResourceNotFoundException((authentication.getName() + "not authorized to make change"));
        }
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

            if (oldProject.getInstructions() != null)
            {
                updatedProject.setInstructions(oldProject.getInstructions());
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
    public List<Project> listAllProjects() {
        List<Project> projects = new ArrayList<>();

        projectRepository.findAll().iterator().forEachRemaining(projects::add);

        return projects;
    }

//    @Override
//    public List<Project> listAllProjectsByUser(User user) {
//
//        return projectRepository.findProjectsByUser(user);
//
//    }

    @Override
    public Project findProjectById(long id) {
        return projectRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Project id " + id + " not found"));
    }
//
//    @Override
//    public Project findByProjectName(String name) {
//        return null;
//    }
}
