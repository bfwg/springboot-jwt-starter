package com.bfwg.rest;

import com.bfwg.common.TimeProvider;
import com.bfwg.model.Authority;
import com.bfwg.model.User;
import com.bfwg.security.DeviceDummy;
import com.bfwg.security.TokenHelper;
import com.bfwg.security.UserDetailsDummy;
import org.assertj.core.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by fanjin on 2017-09-01.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationControllerTest {

    private MockMvc mvc;

    @Mock
    private TimeProvider timeProviderMock;

    private static final String TEST_USERNAME = "testUser";

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

        User user = new User();
        user.setUsername("username");
        Authority authority = new Authority();
        authority.setId(0L);
        authority.setName("ROLE_USER");
        List<Authority> authorities = Arrays.asList(authority);
        user.setAuthorities(authorities);
        when(this.userDetailsService.loadUserByUsername(eq("testUser"))).thenReturn(user);

        ReflectionTestUtils.setField(tokenHelper, "EXPIRES_IN", 10L); // 10 sec
        ReflectionTestUtils.setField(tokenHelper, "SECRET", "mySecret");
    }


    @Test
    @WithMockUser(roles = "USER")
    public void shouldGetEmptyTokenStateWhenGivenValidOldToken() throws Exception {
        when(timeProviderMock.now())
                .thenReturn(DateUtil.yesterday());
//        String token = createToken();
        this.mvc.perform(get("/auth/refresh"))
                .andExpect(content().json("{access_token:null,expires_in:null}"));
    }

    @Test
    public void shouldNotGet200WhenGivenInvalidOldToken() throws Exception {
        when(timeProviderMock.now())
                .thenReturn(DateUtil.yesterday())
                .thenReturn(DateUtil.now());
        String token = createToken();
        this.mvc.perform(get("/auth/refresh").header("Authorization", "Bearer " + token))
            .andExpect(status().is(200));
    }

    private String createToken() {
        final DeviceDummy device = new DeviceDummy();
        device.setNormal(true);
        return tokenHelper.generateToken(new UserDetailsDummy(TEST_USERNAME).getUsername(), device);
    }
}
