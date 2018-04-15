package com.bfwg.service;

import com.bfwg.dto.DefaultUserDetails;
import com.bfwg.remote.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * UserDetailsService is required by Spring Security
 */
public interface UserService extends UserDetailsService {
    DefaultUserDetails findById(String id);
    DefaultUserDetails findByUsername(String username);
    List<DefaultUserDetails> findAll ();
    void create(DefaultUserDetails user);
    void update(DefaultUserDetails user);
    void changePassword(String old, String newPwd);

    DefaultUserDetails findByEmail(String email);
}
