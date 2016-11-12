package com.bfwg.security.auth;

/**
 * Created by fan.jin on 2016-11-07.
 */
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfwg.model.Token;
import com.bfwg.model.User;
import com.bfwg.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	JwtUtil jwtTokenUtil;

	@Autowired
	ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication ) throws IOException, ServletException {

		clearAuthenticationAttributes(request);

		User user = (User)authentication.getPrincipal();
		// Create token
		String token = objectMapper.writeValueAsString(new Token(jwtTokenUtil.generateToken( user.getUsername() )));
		response.setContentType("application/json");
		response.getWriter().write(token);
	}
}
