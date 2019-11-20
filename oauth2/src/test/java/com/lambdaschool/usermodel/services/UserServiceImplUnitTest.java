
package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.exceptions.ResourceFoundException;
import com.lambdaschool.usermodel.exceptions.ResourceNotFoundException;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

/**
 * I am testing UserServiceImpl so want 100% in UserServiceImpl
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplUnitTest
{
    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void B_findUserById()
    {
        assertEquals("admin",
                userService.findUserById(4)
                        .getUsername());
    }


    @Test(expected = ResourceNotFoundException.class)
    public void DA_notFoundDelete()
    {
        userService.delete(100);
        assertEquals(4,
                userService.findAll(Pageable.unpaged())
                        .size());
    }

    @Test
    public void E_findByUsername()
    {
        assertEquals("admin",
                userService.findByName("admin")
                        .getUsername());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void AA_findByUsernameNotfound()
    {
        assertEquals("admin",
                userService.findByName("turtle")
                        .getUsername());
    }


    @Test(expected = ResourceNotFoundException.class)
    public void HA_deleteUserRoleRoleNotFound()
    {
        userService.deleteUserRole(7,
                50);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void HB_deleteUserRoleUserNotFound()
    {
        userService.deleteUserRole(50,
                2);
    }



    @Test(expected = ResourceNotFoundException.class)
    public void IC_addUserRoleRoleNotFound()
    {
        userService.addUserRole(7,
                50);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void ID_addUserRoleUserNotFound()
    {
        userService.addUserRole(50,
                2);
    }

}