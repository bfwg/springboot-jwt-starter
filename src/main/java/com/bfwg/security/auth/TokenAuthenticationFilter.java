package com.bfwg.security.auth;

import com.bfwg.security.JwtUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
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

    @Autowired
    JwtUtil jwtTokenUtil;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;


    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader("Authorization");
        String authToken = null;
        if (header != null && header.startsWith("Bearer ")) {
            authToken = header.substring(7);
        }

        if (authToken != null) {
            try {
                // get username from token
                String username = jwtTokenUtil.getClaims(authToken).getSubject();
                // get user
                UserDetails userDetails = userDetailsService.loadUserByUsername( username );
                // create authentication
                TokenBasedAuthentication authentication = new TokenBasedAuthentication( userDetails );

                authentication.setToken( authToken );
                authentication.setAuthenticated( true );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                AuthenticationException authEx;
                if ( e instanceof AuthenticationException ) {
                    authEx = (AuthenticationException) e;
                } else {
                    // here we might get JWT exceptions like malformed, expired etc
                    authEx = new InsufficientAuthenticationException( e.getMessage(), e );
                }
                authenticationEntryPoint.commence( request, response, authEx );
                return;
            }
        }

        chain.doFilter(request, response);
    }

}
