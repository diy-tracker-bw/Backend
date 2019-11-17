package com.lambdaschool.usermodel.services;


import com.lambdaschool.usermodel.models.Project;

import java.util.List;

public interface ProjectService {

    Project save(Project project);

    Project update(Project project, long id);

    void delete(long id);

    List<Project> returnAllProjects();

    List<Project> returnProjectsByOwner(String username);

    Project findProjectById(long id);

    Project findByProjectName(String name);
}
