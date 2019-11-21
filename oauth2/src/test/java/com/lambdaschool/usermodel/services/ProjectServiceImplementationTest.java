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

    }

    @Test
    public void addLike() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void findByUserName() {
    }



    @Test
    public void findProjectById() {

    }
}