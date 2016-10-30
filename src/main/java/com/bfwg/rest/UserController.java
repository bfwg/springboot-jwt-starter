package com.bfwg.rest;

import com.bfwg.model.User;
import com.bfwg.service.impl.JpaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by fan.jin on 2016-10-15.
 */

@RestController
public class UserController {

    private JpaUserService userService;

    @Autowired
    UserController( JpaUserService userService ) {
        this.userService = userService;
    }

    @RequestMapping( method = GET, value = "/user/{username}" )
    public ResponseEntity<User> loadUser( @PathVariable String username ) {
        return Optional.ofNullable( this.userService.findByUsername( username ) )
                .map( u -> new ResponseEntity<>( u, HttpStatus.OK ) )
                .orElse( new ResponseEntity<>( HttpStatus.FORBIDDEN) );
    }

}
