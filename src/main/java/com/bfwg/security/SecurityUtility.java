package com.bfwg.security;

import com.bfwg.model.User;
import com.bfwg.security.auth.AuthenticationProvider;
import com.bfwg.security.auth.TokenBasedAuthentication;
import org.springframework.security.core.Authentication;

/**
 * Created by fan.jin on 2016-11-11.
 */


public class SecurityUtility {

    AuthenticationProvider provider;

    public SecurityUtility( AuthenticationProvider provider ) {
        this.provider = provider;
    }

    /**
     * Gets the current request's authentication object.
     *
     * @return The authentication object, null is not-authenticated.
     */
    public TokenBasedAuthentication getAuthenticationObject() {
        Authentication authentication = provider.getAuthentication();
        if ( authentication instanceof TokenBasedAuthentication ) {
            return (TokenBasedAuthentication) authentication;
        } else {
            return null;
        }
    }

    /**
     * Gets the principle for the current request. This will be a user in our case.
     *
     * @return The principle object.
     */
    public User getAuthenticationPrinciple() {
        TokenBasedAuthentication auth = getAuthenticationObject();
        return ( auth == null ? null : (User)auth.getPrincipal() );
    }

    /**
     * Gets the current request's token
     */
    public String getToken() {
        String token;
        TokenBasedAuthentication auth = getAuthenticationObject();
        if ( auth == null ) {
            return null;
        } else {
            token = auth.getToken();
        }
        return token;
    }

}
