package com.bfwg.web.request;


import org.hibernate.validator.constraints.Email;

public class LoginRequest {
    @Email
    public String username; //must be an email, so we can guarantee uniqueness.
    public String password;
}
