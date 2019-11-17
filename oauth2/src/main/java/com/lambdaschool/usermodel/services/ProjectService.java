package com.lambdaschool.usermodel.services;


import com.lambdaschool.usermodel.models.Project;

import java.util.List;

public interface ProjectService {

    Project save(Project project);

    Project update(Project project, long id);

    void delete(long id);

    List<Project> listAllProjects();

    List<Project> listAllProjectsByUsername(String username);

    Project findProjectById(long id);

//    Project findByProjectName(String name);
}
