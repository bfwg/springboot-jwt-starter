package com.bfwg.security;

import com.bfwg.service.UserService;
import io.jsonwebtoken.Claims;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String jwtToken = request.getHeader(tokenHeader);

        // authToken.startsWith("Bearer ")
        // String authToken = header.substring(7);

        if (jwtToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtTokenUtil.validateToken(jwtToken)) {

                String username = jwtTokenUtil.getClaims(jwtToken).getSubject();
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // hardcoded for now
                // if we pass USER_ROLE, Authenticated will always be true;
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                logger.info("USER AUTHENTICATED " + authentication.toString());

                // set Security Context Authentication
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }
}
