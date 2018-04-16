package com.bfwg.security.auth;

import com.bfwg.config.TokenHelper;
import com.bfwg.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by fan.jin on 2016-10-19.
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    private TokenHelper tokenHelper;

    private UserService userService;

    public TokenAuthenticationFilter(TokenHelper tokenHelper, UserService userService) {
        this.tokenHelper = tokenHelper;
        this.userService = userService;
    }


    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        String username;
        String authToken = tokenHelper.getToken(request);

        if (authToken != null) {
            // get username from token
            username = tokenHelper.getUsernameFromToken(authToken);
            if (username != null) {
                // get user
                UserDetails userDetails = userService.findByUsername(username);
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