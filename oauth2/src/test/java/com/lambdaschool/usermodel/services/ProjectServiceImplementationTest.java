package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.models.Project;
import com.lambdaschool.usermodel.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProjectServiceImplementationTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void AlistAllProjects() {
        assertEquals(0, projectService.findByUserName("admin").size());
        assertEquals(2, projectService.findByUserName("patrick123").size());
        assertEquals(1, projectService.findByUserName("kevin").size());
    }

    @Test
    public void Bsave() {
        User userAdmin = userService.findByName("admin");
        Project newProject = new Project("Sample Name", "Sample Instructions", "Sample photo",
                50, userAdmin);

        projectService.save(newProject);
        assertEquals(1, projectService.findByUserName("admin").size());
        assertEquals("Sample Name", projectService.findByUserName("admin").get(0).getProjectname());
        assertEquals("Sample Instructions", projectService.findByUserName("admin").get(0).getInstructions());
        assertEquals(50, projectService.findByUserName("admin").get(0).getLikes());
        assertEquals("Sample photo", projectService.findByUserName(("admin")).get(0).getPhotoUrl());
    }

    @Test
    public void YaddLike() {
        projectService.addLike(6);
        assertEquals(182, projectService.findProjectById(6).getLikes());
    }

    @Test
    @WithMockUser(username = "patrick123", roles = {"USER", "ADMIN"})
    public void update() {
        Project newProject = new Project();
        newProject.setProjectname("Updated Project Name");
        newProject.setInstructions("Updated Instructions");
        newProject.setPhotoUrl("Updated Photo URL");

        assertEquals("Updated Project Name", projectService.update(newProject, 6).getProjectname());
        assertEquals("Updated Instructions", projectService.update(newProject, 6).getInstructions());
        assertEquals("Updated Photo URL", projectService.update(newProject, 6).getPhotoUrl());
    }

    @Test
    @WithMockUser(username = "patrick123", roles = {"USER", "ADMIN"})
    public void Zdelete() {
        projectService.delete(6);
        assertEquals(1, projectService.findByUserName("patrick123").size());
    }

    @Test
    public void findByUserName() {
        assertEquals(2, projectService.findByUserName("patrick123").size());
    }

    @Test
    public void findProjectById() {
        assertEquals(181, projectService.findProjectById(6).getLikes());
    }
}