package com.bfwg.security.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fan.jin on 2016-12-05.
 */
public class JwtLogoutHandler implements LogoutHandler {

    @Value("${jwt.cookie_name}")
    private String AUTH_COOKIE;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Cookie cookie = new Cookie( AUTH_COOKIE, null ); // Not necessary, but saves bandwidth.
        cookie.setPath( "/" );
        cookie.setHttpOnly( true );
        cookie.setMaxAge( 0 ); // Don't set to -1 or it will become a session cookie!
        response.addCookie(cookie);
    }
}
