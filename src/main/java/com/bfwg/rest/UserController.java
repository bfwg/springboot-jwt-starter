package com.bfwg.rest;

import com.bfwg.model.User;
import com.bfwg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private UserService userService;

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

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}
