package com.bfwg.security.auth;

import com.bfwg.security.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by fan.jin on 2017-10-20.
 */

@Component
public class CsrfProtectionRequestMatcher implements RequestMatcher {

    @Autowired
    TokenHelper tokenHelper;

    @Override
    public boolean matches(HttpServletRequest request) {
        String authHeader = tokenHelper.getAuthHeaderFromHeader( request );
        HashSet<ArrayList> allowedMethods = new HashSet(Arrays.asList(new String[]{"GET", "HEAD", "TRACE", "OPTIONS"}));
        if (!allowedMethods.contains(request.getMethod())) {
                    /*
                     Ignoring CSRF if a bearer token appears in the header of the request and
                     the there are no cookies in the header.
                     This will indicate that the request is not coming from a browser.
                     */
            if (request.getCookies() == null &&
                    authHeader != null &&
                    authHeader.startsWith("Bearer ")) {
                return false;
            } else {
                // CSRF for everything else
                return true;
            }
        } else {
            return false;
        }
    }
}
