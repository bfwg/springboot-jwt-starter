package com.bfwg.rest;

import com.bfwg.AbstractTest;
import com.bfwg.model.Authority;
import com.bfwg.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fan.jin on 2017-01-13.
 */
public class UserControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mockMvc;;

    private MediaType applicationJsonMediaType = new MediaType( MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype() );

    @Test(expected=Exception.class)
    public void testUserEndpointFail() throws Exception {
        this.mockMvc.perform( get( "/user" ) );
    }

    @Test
    public void testUserEndpointSuccess() throws Exception {
        mockAuthenticatedUser(buildTestUser());
        this.mockMvc.perform( get( "/user" ) );
    }

    @Test(expected=Exception.class)
    public void testAdminEndpointFail() throws Exception {
        mockAuthenticatedUser(buildTestUser());
        this.mockMvc.perform( get( "/user/all" ) );
    }

    @Test
    public void testAdminEndpointSuccess() throws Exception {
        mockAuthenticatedUser(buildTestAdmin());
        this.mockMvc.perform( get( "/user/all" ) )
                .andExpect( status().isOk() )
                .andDo( print() );
    }


}
