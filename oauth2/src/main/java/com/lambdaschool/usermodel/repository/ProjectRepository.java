package com.lambdaschool.usermodel.repository;

import com.lambdaschool.usermodel.models.Project;
import com.lambdaschool.usermodel.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long> {

    List<Project>findProjectsByUser(User user);

//    void deleteById(Long id);

}