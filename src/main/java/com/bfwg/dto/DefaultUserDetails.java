package com.bfwg.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.*;

@Builder
@Getter
public class DefaultUserDetails implements UserDetails {

    public void setPassword(String password) {
        this.password = password;
    }

    private  String password;
    private  String id;
    private  String email;
    private  String firstname;
    private  String lastname;
    private  String role;
    private  String username;
    private  boolean enabled;
    private long lastPasswordRestTime;
    private  String phoneNumber;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        return Arrays.asList(authority);
    }


//    @Override //this is the key method for jwt
//    public String getUsername() {
//        return email;
//    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public Date getLastPasswordResetDate() {
        return Date.from(Instant.ofEpochMilli(lastPasswordRestTime));
    }

}
