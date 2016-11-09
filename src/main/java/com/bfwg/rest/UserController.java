package com.bfwg.rest;

import com.bfwg.model.User;
import com.bfwg.security.AuthenticationRequest;
import com.bfwg.security.JwtUtil;
import com.bfwg.service.impl.JpaUserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by fan.jin on 2016-10-15.
 */

@RestController
public class UserController {

    @Autowired
    private JpaUserService userService;

    @Autowired
    JwtUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @RequestMapping( method = GET, value = "/user/{id}" )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> loadById( @PathVariable Long id ) {
        return Optional.ofNullable( this.userService.findById( id ) )
                .map( u -> new ResponseEntity<>( u, HttpStatus.OK ) )
                .orElse( new ResponseEntity<>( HttpStatus.NOT_FOUND ) );
    }

    @RequestMapping( method = GET, value= "/allUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> loadAll(Authentication auth) {
        return Optional.ofNullable( this.userService.findAll() )
                .map( u -> new ResponseEntity<>( u, HttpStatus.OK ) )
                .orElse( new ResponseEntity<>( HttpStatus.NOT_FOUND ) );
    }

    @RequestMapping( method = GET, value = "/whoami" )
    public ResponseEntity<User> loadMe(HttpServletRequest request) {
        String username = jwtTokenUtil.getClaims(request.getHeader( tokenHeader )).getSubject();
        return Optional.ofNullable( this.userService.findByUsername( username ) )
                .map( u -> new ResponseEntity<>( u, HttpStatus.OK ) )
                .orElse( new ResponseEntity<>( HttpStatus.I_AM_A_TEAPOT) );
    }
}
