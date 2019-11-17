package com.lambdaschool.usermodel.controllers;

import com.lambdaschool.usermodel.logging.Loggable;
import com.lambdaschool.usermodel.models.Project;
import com.lambdaschool.usermodel.services.ProjectService;
import com.lambdaschool.usermodel.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Loggable
@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @GetMapping(value = "/projects",
                produces = {"application/json"})
    public ResponseEntity<?> listAllProjects(HttpServletRequest request)
    {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        List<Project> allProjects = projectService.listAllProjects();
        
        return new ResponseEntity<>(allProjects, HttpStatus.OK);
    }
}
