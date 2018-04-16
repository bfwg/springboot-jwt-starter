package com.bfwg.web.request;


import org.hibernate.validator.constraints.Email;

public class RegisterRequest {
    public String firstname;
    public String lastname;
    public String username;

    @Email
    public String email;

    public String password;
    public String phoneNumber;

    //they get a role, enabled
}
