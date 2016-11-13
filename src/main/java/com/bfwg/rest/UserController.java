package com.bfwg.rest;

import com.bfwg.model.User;
import com.bfwg.security.SecurityUtility;
import com.bfwg.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by fan.jin on 2016-10-15.
 */

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    SecurityUtility securityUtility;

    @RequestMapping( method = GET, value = "/user/{userId}" )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> loadById( @PathVariable Long userId ) {
        return Optional.ofNullable( this.userService.findById( userId ) )
                .map( u -> new ResponseEntity<>( u, HttpStatus.OK ) )
                .orElse( new ResponseEntity<>( HttpStatus.NOT_FOUND ) );
    }

    @RequestMapping( method = GET, value= "/user/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> loadAll() {
        return Optional.ofNullable( this.userService.findAll() )
                .map( u -> new ResponseEntity<>( u, HttpStatus.OK ) )
                .orElse( new ResponseEntity<>( HttpStatus.NOT_FOUND ) );
    }

    @RequestMapping( method = GET, value = "/whoami" )
    public ResponseEntity<User> loadMe() {
        User user = securityUtility.getAuthenticationPrinciple();
        return Optional.ofNullable( this.userService.findByUsername( user.getUsername() ) )
                .map( u -> new ResponseEntity<>( u, HttpStatus.OK ) )
                .orElse( new ResponseEntity<>( HttpStatus.I_AM_A_TEAPOT) );
    }
}
