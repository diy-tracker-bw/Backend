package com.lambdaschool.usermodel.controllers;

import com.lambdaschool.usermodel.logging.Loggable;
import com.lambdaschool.usermodel.models.Project;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.services.ProjectService;
import com.lambdaschool.usermodel.services.UserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Loggable
@RestController
@RequestMapping("/projects")
@Api(tags = {"ProjectEndPoints"})
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

//    @GetMapping(value = "/projects/",
//                produces = {"application/json"},
//                consumes = {"application/json"})
//    public ResponseEntity<?> getProjectsByUser(HttpServletRequest request, @RequestBody User user)
//    {
//        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
//
//        List<Project> usersProjects = projectService.listAllProjectsByUser(user);
//        return new ResponseEntity<>(usersProjects, HttpStatus.OK);
//    }

    @GetMapping(value = "/project/{id}",
                produces = {"application/json"})
    public ResponseEntity<?> getProjectById(HttpServletRequest request, @PathVariable long id)
    {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        Project project = projectService.findProjectById(id);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping(value = "/projects/{username}",
            produces = {"application/json"})
    public ResponseEntity<?> findProjectsByUserName(HttpServletRequest request, @PathVariable String username)
    {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        List<Project> userProjects = projectService.findByUserName(username);
        return new ResponseEntity<>(userProjects, HttpStatus.OK);
    }

    @PostMapping(value = "/project",
                consumes = {"application/json"},
                produces = {"application/json"})
    public ResponseEntity<?> addNewProject(HttpServletRequest request, @Valid @RequestBody Project newProject) throws URISyntaxException
    {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        newProject =projectService.save(newProject);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newProjectURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/projects")
                .buildAndExpand(newProject.getProjectId())
                .toUri();
        responseHeaders.setLocation(newProjectURI);
        return new ResponseEntity<>("Successfully added new project", responseHeaders, HttpStatus.CREATED);

    }

    @PutMapping(value = "/project/like/{projectId}")
    public ResponseEntity<?> addLike(HttpServletRequest request,
                                           @PathVariable long projectId)
    {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        projectService.addLike(projectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/project/{projectId}")
    public ResponseEntity<?> updateProject(HttpServletRequest request,
                                           @RequestBody Project updateProject,
                                           @PathVariable long projectId)
    {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        projectService.update(updateProject, projectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/project/{id}")
    public ResponseEntity<?> deleteProjectById(HttpServletRequest request, @PathVariable long id)
    {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        projectService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
