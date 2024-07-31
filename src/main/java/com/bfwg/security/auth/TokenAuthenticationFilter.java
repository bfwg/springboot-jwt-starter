package com.bfwg.security.auth;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bfwg.security.TokenHelper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Created by fan.jin on 2016-10-19.
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private final Log logger = LogFactory.getLog(this.getClass());

	private TokenHelper tokenHelper;

	private UserDetailsService userDetailsService;

	public TokenAuthenticationFilter(TokenHelper tokenHelper, UserDetailsService userDetailsService) {
		this.tokenHelper = tokenHelper;
		this.userDetailsService = userDetailsService;
	}

	@Override
	public void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		String username;
		String authToken = tokenHelper.getToken(request);

		if (authToken != null) {
			// get username from token
			username = tokenHelper.getUsernameFromToken(authToken);
			if (username != null) {
				// get user
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				if (tokenHelper.validateToken(authToken, userDetails)) {
					// create authentication
					TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
					authentication.setToken(authToken);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}
		chain.doFilter(request, response);
	}

}