package com.bfwg.security.auth;

import com.bfwg.model.User;
import com.bfwg.security.JwtUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    JwtUtil jwtTokenUtil;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;


    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = request.getHeader(tokenHeader);

        // authToken.startsWith("Bearer ")
        // String authToken = header.substring(7);

        if (token != null) {
            try {
                if (jwtTokenUtil.validateToken(token)) {

                    String username = jwtTokenUtil.getClaims(token).getSubject();
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    TokenBasedAuthentication authentication = new TokenBasedAuthentication( userDetails );

                    authentication.setToken( token );
                    authentication.setAuthenticated( true );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                AuthenticationException authException = new InsufficientAuthenticationException( e.getMessage(), e );
                authenticationEntryPoint.commence( request, response, authException );
                return;
            }
        }

        chain.doFilter(request, response);
    }

    /**
     * Created by fan.jin on 2016-11-07.
     */
    @Component
    public static class UnauthenticationEntryPoint implements AuthenticationEntryPoint {

        @Override
        public void commence(HttpServletRequest request,
                             HttpServletResponse response,
                             AuthenticationException authException) throws IOException {
            // This is invoked when user tries to access a secured REST resource without supplying any credentials
            // We should just send a 401 Unauthorized response because there is no 'login page' to redirect to
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized, bad token");
        }
    }
}
