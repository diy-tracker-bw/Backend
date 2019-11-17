package com.lambdaschool.usermodel.repository;

import com.lambdaschool.usermodel.models.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long> {

    List<Project>findProjectsByUser(String username);

}