package com.bfwg.service;

import com.bfwg.config.DeviceProvider;
import com.bfwg.config.TokenHelper;
import com.bfwg.dto.Token;
import com.bfwg.web.request.LoginRequest;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * This service performs Authentication, and uses the UserService to do so.
 * The UserService knows nothing about authentication.
 */
public class AuthenticationService {

    final UserService userService;

    final TokenHelper tokenHelper;

    final AuthenticationManager authenticationManager;


    public AuthenticationService(UserService userService, TokenHelper tokenHelper, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.tokenHelper = tokenHelper;
        this.authenticationManager = authenticationManager;
    }

    public Token authenticate(LoginRequest loginRequest, Device device) {

        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.username,
                loginRequest.password
            )
        );

        // Inject into security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // token creation
        UserDetails user = (UserDetails) authentication.getPrincipal();

        String jws = tokenHelper.generateToken(user.getUsername(), device);
        int expiresIn = tokenHelper.getExpiredIn(device);

        return new Token(jws, expiresIn);
    }

    public Token refreshExistingToken(String authToken, Device device) {

        // TODO check user password last update
        String refreshedToken = tokenHelper.refreshToken(authToken, device);
        int expiresIn = tokenHelper.getExpiredIn(device);
        return new Token(refreshedToken, expiresIn);
    }

    public void changePassword(String oldPassword, String newPassword) {
        userService.changePassword(oldPassword,newPassword);
    }
}
