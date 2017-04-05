package com.bfwg.service;

import com.bfwg.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

/**
 * Created by fan.jin on 2017-04-04.
 */
public class UserServiceTest extends AbstractTest {

    @Autowired
    UserService userService;

    @Test(expected = AccessDeniedException.class)
    public void testFindAllWithoutUser() throws AccessDeniedException {
        userService.findAll();
    }

    @Test(expected = AccessDeniedException.class)
    public void testFindAllWithUser() throws AccessDeniedException {
        mockAuthenticatedUser(buildTestUser());
        userService.findAll();
    }

    @Test
    public void testFindAllWithAdmin() throws AccessDeniedException {
        mockAuthenticatedUser(buildTestAdmin());
        userService.findAll();
    }

    @Test(expected = AccessDeniedException.class)
    public void testFindByIdWithoutUser() throws AccessDeniedException {
        userService.findById(1L);
    }

    @Test(expected = AccessDeniedException.class)
    public void testFindByIdWithUser() throws AccessDeniedException {
        mockAuthenticatedUser(buildTestUser());
        userService.findById(1L);
    }

    @Test
    public void testFindByIdWithAdmin() throws AccessDeniedException {
        mockAuthenticatedUser(buildTestAdmin());
        userService.findById(1L);
    }


    @Test(expected = AccessDeniedException.class)
    public void testFindByUsernameWithoutUser() throws AccessDeniedException {
        userService.findByUsername("user");
    }

    @Test
    public void testFindByUsernameWithUser() throws AccessDeniedException {
        mockAuthenticatedUser(buildTestUser());
        userService.findByUsername("user");
    }

    @Test
    public void testFindByUsernameWithAdmin() throws AccessDeniedException {
        mockAuthenticatedUser(buildTestAdmin());
        userService.findByUsername("user");
    }

}
