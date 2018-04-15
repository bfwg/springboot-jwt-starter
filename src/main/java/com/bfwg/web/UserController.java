package com.bfwg.web;

import com.bfwg.converters.DefaultUserDetailsConverter;
import com.bfwg.dto.DefaultUserDetails;
import com.bfwg.remote.UserEntity;
import com.bfwg.web.request.RegisterRequest;
import com.bfwg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static com.bfwg.converters.DefaultUserDetailsConverter.from;
import static com.bfwg.converters.DefaultUserDetailsConverter.toUserResponseList;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by fan.jin on 2016-10-15.
 */

@RestController
@RequestMapping( value = "/api", produces = MediaType.APPLICATION_JSON_VALUE )
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping( method = GET, value = "/user/{userId}" )
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse loadById(@PathVariable String userId ) {
        DefaultUserDetails user = userService.findById( userId );
        return new UserResponse(user);
    }

    @RequestMapping( method = GET, value= "/user/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> loadAll() {
        List<DefaultUserDetails> userDetailsList = userService.findAll();
        return toUserResponseList(userDetailsList);

    }


    /*
     *  We are not using userService.findByUsername here(we could),
     *  so it is good that we are making sure that the user has role "ROLE_USER"
     *  to access this endpoint.
     */
    @RequestMapping("/whoami")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public UserResponse user(Principal user) {
        DefaultUserDetails userDetails = userService.findByUsername(user.getName());
        return new UserResponse(userDetails);
    }

    @PostMapping("/user/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody RegisterRequest registerRequest) {

        //validate the userRequest.
        DefaultUserDetails newUser = from(registerRequest);
        userService.create(newUser);
    }


}
