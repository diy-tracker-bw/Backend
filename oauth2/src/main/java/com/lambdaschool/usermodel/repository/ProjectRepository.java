package com.lambdaschool.usermodel.repository;

import com.lambdaschool.usermodel.models.Project;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.view.ProjectsByUserId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long> {

//    @Query(value = "SELECT * FROM PROJECTS WHERE userid = :userid", nativeQuery = true)
//    List<Project> findProjectsByUserId(long userid);

    List<Project>findAllByUser_Username(String name);

//    Project findProjectByProjectId(long id);

//    List<Project> findProjectsByUser(User user);

//    void deleteById(Long id);

}