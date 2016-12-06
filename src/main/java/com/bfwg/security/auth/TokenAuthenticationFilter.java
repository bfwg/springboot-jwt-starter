package com.bfwg.security.auth;

import com.bfwg.security.TokenUtils;
import org.apache.catalina.security.SecurityUtil;
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
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by fan.jin on 2016-10-19.
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Value("${jwt.cookie_name}")
    private String AUTH_COOKIE;

    @Autowired
    TokenUtils jwtTokenUtil;

    @Autowired
    UserDetailsService userDetailsService;

    private String getToken( HttpServletRequest request ) {
        /**
         *  Getting the token from Cookie store
         */
        Cookie authCookie = getCookieValueByName( request, AUTH_COOKIE );
        if ( authCookie != null ) {
            return authCookie.getValue();
        }
        /**
         *  Getting the token from Authentication header
         *  e.g Bearer your_token
         */
        String authHeader = request.getHeader("Authorization");
        if ( authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }
    /**
     * Find a specific HTTP cookie in a request.
     *
     * @param request
     *            The HTTP request object.
     * @param name
     *            The cookie name to look for.
     * @return The cookie, or <code>null</code> if not found.
     */
    protected Cookie getCookieValueByName(HttpServletRequest request, String name) {
        if (request.getCookies() == null) {
            return null;
        }
        for (int i = 0; i < request.getCookies().length; i++) {
            if (request.getCookies()[i].getName().equals(name)) {
                return request.getCookies()[i];
            }
        }
        return null;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String authToken = getToken( request );
        // get username from token
        String username = jwtTokenUtil.getUsernameFromToken( authToken );
        if ( username != null ) {
            // get user
            UserDetails userDetails = userDetailsService.loadUserByUsername( username );
            // create authentication
            TokenBasedAuthentication authentication = new TokenBasedAuthentication( userDetails );

            authentication.setToken( authToken );
            authentication.setAuthenticated( true );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

}
