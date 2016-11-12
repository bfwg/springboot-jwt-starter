package com.bfwg.rest;

import com.bfwg.model.Token;
import com.bfwg.model.UserRepository;
import com.bfwg.security.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by fan.jin on 2016-10-16.
 */

@RestController
public class AuthenticationController {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Token> login(HttpServletRequest request)  {
        // Security check
        System.out.println(request.getParameter("username"));
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getParameter("username"),
                    request.getParameter("password")
            )
        );

//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//            authenticationRequest.getUsername(),
//            authenticationRequest.getPassword()
//        );
//
//        // set Security Context Authentication
//        SecurityContextHolder.getContext().setAuthentication(authentication);


//        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        return new ResponseEntity<>( new Token(jwtTokenUtil.generateToken( request.getParameter("username") )), HttpStatus.OK );
    }

    @RequestMapping("/test-parse")
    public Claims testParse(HttpServletRequest request) {
        String jwt = request.getHeader(tokenHeader);
        return jwtTokenUtil.getClaims((jwt));
    }
}
