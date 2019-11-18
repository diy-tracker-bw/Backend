package com.lambdaschool.usermodel.services;


import com.lambdaschool.usermodel.models.Project;
import com.lambdaschool.usermodel.models.User;

import java.util.List;

public interface ProjectService {

    Project save(Project project);

    Project update(Project project, long id);

    void delete(long id);

    List<Project> listAllProjects();

//    List<Project> listAllProjectsByUser(User user);

    Project findProjectById(long id);

    List<Project> findByUserName(String username);

//    Project findByProjectName(String name);
}
