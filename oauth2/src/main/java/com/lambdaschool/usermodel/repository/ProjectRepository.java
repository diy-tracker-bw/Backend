package com.lambdaschool.usermodel.repository;

import com.lambdaschool.usermodel.models.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {

}