package com.bfwg.rest;

import com.bfwg.model.User;
import com.bfwg.security.TokenHelper;
import com.bfwg.security.UserDetailsDummy;
import io.jsonwebtoken.ExpiredJwtException;
import org.joda.time.DateTimeUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by fanjin on 2017-09-01.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationControllerTest {

    private MockMvc mvc;

    @Autowired
    private TokenHelper tokenHelper;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        DateTimeUtils.setCurrentMillisSystem();
        User user = new User();
        user.setUsername("username");
        when(this.userDetailsService.loadUserByUsername(eq("test-user"))).thenReturn(user);
    }


    @Test
    public void shouldGet200WhenGivenValidOldToken() throws Exception {

        String token = tokenHelper.generateToken(new UserDetailsDummy("test-user").getUsername());
        this.mvc.perform(get("/auth/refresh").header("Authorization", "Bearer " + token))
                .andExpect(status().is(200));

    }

    @Test(expected = ExpiredJwtException.class)
    public void shouldNotGet200WhenGivenInvalidOldToken() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(1L); // set time back to 1970
        String token = tokenHelper.generateToken(new UserDetailsDummy("test-user").getUsername());
        DateTimeUtils.setCurrentMillisSystem(); // back to now
        ResultActions action = null;
        this.mvc.perform(get("/auth/refresh").header("Authorization", "Bearer " + token));
    }

}
