package com.bfwg.rest;

import com.bfwg.model.Authority;
import com.bfwg.model.User;
import com.bfwg.request.UserRequest;
import com.bfwg.security.TokenHelper;
import com.bfwg.security.auth.JwtAuthenticationRequest;
import com.bfwg.service.UserService;
import com.bfwg.service.impl.CustomUserDetailsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by fanjin on 2017-09-01.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginControllerTest {

    static final Logger log = LoggerFactory.getLogger(LoginControllerTest.class);
    private MockMvc mvc;

    @Autowired
    private TokenHelper tokenHelper;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationController authenticationController;


    @Autowired
    private WebApplicationContext context;

    String jwt;
    String jwtAccessPart;
    int jwtExpiry;
    UserDetails userWithRole;

    @Before
    public void setup() {

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

//        buildUserWithRole();

//        when(this.userDetailsService.loadUserByUsername(eq("testUser"))).thenReturn(userWithRole);
//        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(tokenHelper, "EXPIRES_IN", 100); // 100 sec
        ReflectionTestUtils.setField(tokenHelper, "MOBILE_EXPIRES_IN", 200); // 200 sec
        ReflectionTestUtils.setField(tokenHelper, "SECRET", "queenvictoria");
    }


    @Test
    public void shouldRegisterThenLogin() throws Exception {

        regsiterNewUser();

        shouldLoginAndGetJwt();
    }

    public void regsiterNewUser() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.email = "w@w.com";
        userRequest.firstname="will";
        userRequest.password="123";

        String userAsJson = convertToJson(userRequest);

        MvcResult resultActions = this.mvc.perform(
                post("/api/user/register").contentType(MediaType.APPLICATION_JSON).content(userAsJson)).andReturn();

        assertTrue(resultActions.getResponse().getStatus() == HttpServletResponse.SC_CREATED);

        userWithRole  = userDetailsService.loadUserByUsername("w@w.com");

    }


    public void shouldLoginAndGetJwt() throws Exception {
        JwtAuthenticationRequest loginRequest = new JwtAuthenticationRequest();
        loginRequest.setUsername("w@w.com");
        loginRequest.setPassword("123");

        String jsonLoginRequest = convertToJson(loginRequest);

         MvcResult resultActions = this.mvc.perform(
                post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(jsonLoginRequest)).andReturn();

        assertTrue(resultActions.getResponse().getStatus() == HttpServletResponse.SC_OK);

        extractTokenAndExpiry(resultActions);
        validateJwt();
    }

    private void validateJwt() {
        assertTrue(jwtAccessPart.matches("^([\\w-\\.]+){1}"));
        assertTrue(jwtExpiry ==100);
    }



    protected void extractTokenAndExpiry(MvcResult result) throws UnsupportedEncodingException {
        jwt = result.getResponse().getContentAsString();
        jwtAccessPart = JsonPath.read(jwt, "$.access_token");
        jwtExpiry = JsonPath.read(jwt, "$.expires_in");
    }

    private static String convertToJson(Object object) throws JsonProcessingException {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

//    void buildUserWithRole(){
//        userWithRole = new User();
//        userWithRole.setEnabled(true);
//        userWithRole.setUsername("testUser");
//        userWithRole.setPassword("123");
//
//        Authority authority = new Authority();
//        authority.setId(0L);
//        authority.setName("ROLE_USER");
//        List<Authority> authorities = Arrays.asList(authority);
//        userWithRole.setAuthorities(authorities);
//        userWithRole.setLastPasswordResetDate(new Timestamp(DateUtil.yesterday().getTime()));
//    }



}
