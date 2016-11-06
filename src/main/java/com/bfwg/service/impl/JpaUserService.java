package com.bfwg.service.impl;

import com.bfwg.model.User;
import com.bfwg.model.UserRepository;
import com.bfwg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fan.jin on 2016-10-15.
 */

@Service
public class JpaUserService implements UserService {
    @Autowired
    private UserRepository userRepository;

    public User findById( Long id ) throws UsernameNotFoundException {
        User u = userRepository.findOne( id );
        return u;
    }
    @Override
    public User findByUsername( String username ) throws UsernameNotFoundException {
        User u = userRepository.findByUsername( username );
        return u;
    }

    public List<User> findAll() {
        List<User> result = userRepository.findAll();
        return result;
    }
}
