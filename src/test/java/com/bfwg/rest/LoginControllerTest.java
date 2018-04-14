package com.bfwg.rest;

import com.bfwg.request.LoginRequest;
import com.bfwg.request.RegisterRequest;
import com.bfwg.security.TokenHelper;
import com.bfwg.security.auth.JwtAuthenticationRequest;
import com.bfwg.service.impl.CustomUserDetailsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by fanjin on 2017-09-01.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginControllerTest {

    static final Logger log = LoggerFactory.getLogger(LoginControllerTest.class);
    MockMvc mvc;

    @Autowired
    TokenHelper tokenHelper;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    WebApplicationContext context;



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

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.email = "w@w.com";
        registerRequest.password="123";
        registerRequest.firstname="john";
        registerRequest.lastname="smith";
        registerRequest.phoneNumber="07770497730";

        String userAsJson = convertToJson(registerRequest);

        MvcResult resultActions = this.mvc.perform(
                        post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userAsJson))
                .andReturn();

        assertTrue(resultActions.getResponse().getStatus() == HttpServletResponse.SC_CREATED);

        userWithRole  = userDetailsService.loadUserByUsername("w@w.com");

    }


    public void shouldLoginAndGetJwt() throws Exception {
        JwtAuthenticationRequest loginRequest = new JwtAuthenticationRequest();
        loginRequest.setUsername("w@w.com");
        loginRequest.setPassword("123");

        String jsonLoginRequest = convertToJson(loginRequest);

         MvcResult resultActions = mvc.perform(
                post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLoginRequest))
                 .andReturn();

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

    private static String convertToJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

}
