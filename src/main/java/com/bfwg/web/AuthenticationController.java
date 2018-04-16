package com.bfwg.web;

import com.bfwg.config.DeviceProvider;
import com.bfwg.config.TokenHelper;
import com.bfwg.dto.PasswordChangeRequest;
import com.bfwg.dto.Token;
import com.bfwg.service.AuthenticationService;
import com.bfwg.web.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    private DeviceProvider deviceProvider;

    @Autowired
    TokenHelper tokenHelper;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest loginRequest, Device device) throws AuthenticationException {

        Token jwt = authenticationService.authenticate(loginRequest, device);
        return ResponseEntity.ok(jwt);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public ResponseEntity<?> refreshAuthenticationToken(HttpServletRequest request, Principal principal) {

        String authToken = tokenHelper.getToken(request);
        Device device = deviceProvider.getCurrentDevice(request);

        Token token = new Token();
        if (authToken == null || principal == null) {
            return ResponseEntity.accepted().body(token);
        }

        token = authenticationService.refreshExistingToken(authToken, device);
        return ResponseEntity.ok(token);
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) {
        authenticationService.changePassword(passwordChangeRequest.password, passwordChangeRequest.newPassword);
        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        return ResponseEntity.accepted().body(result);
    }
}