package com.bfwg.web;

import com.bfwg.dto.DefaultUserDetails;

public class UserResponse {

    public String username;

    public String firstName;


    public String email;

    public String phoneNumber;

    public boolean enabled;

    public String role;

    public UserResponse(){}

    public UserResponse(DefaultUserDetails userDetails){
        username = userDetails.getUsername();
        firstName = userDetails.getUsername();
        phoneNumber = userDetails.getPhoneNumber();
        email = userDetails.getEmail();
        role = userDetails.getRole();
        enabled = userDetails.isEnabled();
    }
}
