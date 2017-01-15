package com.bfwg;

import com.bfwg.model.Authority;
import com.bfwg.model.User;
import com.bfwg.repository.UserRepository;
import com.bfwg.security.auth.TokenBasedAuthentication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;

import org.junit.After;
import org.junit.Before;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;


@RunWith( SpringJUnit4ClassRunner.class )
@SpringApplicationConfiguration( classes = Application.class )
@WebIntegrationTest
@Transactional
@ActiveProfiles( "test" )
public abstract class AbstractTest {

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected ObjectMapper objectMapper;

	@Before
	public final void beforeAbstractTest() {
		securityContext = Mockito.mock( SecurityContext.class );
		SecurityContextHolder.setContext( securityContext );
	}

	@After
	public final void afterAbstractTest() {
		SecurityContextHolder.clearContext();
	}

	protected SecurityContext securityContext;

	protected void mockAuthenticatedUser( User user ) {
		mockAuthentication( new TokenBasedAuthentication( user ) );
	}

	private void mockAuthentication( TokenBasedAuthentication auth ) {
		auth.setAuthenticated( true );
		Mockito.when( securityContext.getAuthentication() ).thenReturn( auth );
	}

    protected User buildTestAnonUser() {
        User user = new User();
        user.setUsername("user");
        return user;
    }

	protected User buildTestUser() {

		User user = new User();
		Authority userAuthority = new Authority();
		userAuthority.setName("ROLE_USER");
		List<Authority> userAuthorities = new ArrayList<>();
		userAuthorities.add(userAuthority);
		user.setUsername("user");
		user.setAuthorities(userAuthorities);
		return user;
	}


    protected User buildTestAdmin() {
        Authority userAuthority = new Authority();
        Authority adminAuthority = new Authority();
        userAuthority.setName("ROLE_USER");
        adminAuthority.setName("ROLE_ADMIN");
        List<Authority> adminAuthorities = new ArrayList<>();
        adminAuthorities.add(userAuthority);
        adminAuthorities.add(adminAuthority);
        User admin = new User();
        admin.setUsername("admin");
        admin.setAuthorities(adminAuthorities);
        return admin;
    }


}