package com.bfwg.security.auth;

/**
 * Created by fan.jin on 2016-11-07.
 */
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfwg.model.UserTokenState;
import com.bfwg.model.User;
import com.bfwg.security.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${jwt.expires_in}")
    private Long EXPIRES_IN;

	@Autowired
    TokenUtils tokenUtils;

	@Autowired
	ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication ) throws IOException, ServletException {
		clearAuthenticationAttributes(request);
		User user = (User)authentication.getPrincipal();
        // build jwt token
        String jws = tokenUtils.generateToken( user.getUsername() );
		UserTokenState userTokenState = new UserTokenState(jws, EXPIRES_IN);
		// Create token JSON response
		String jwtResponse = objectMapper.writeValueAsString( userTokenState );
		response.setContentType("application/json");
		response.getWriter().write( jwtResponse );
	}
}
