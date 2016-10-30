package com.bfwg.service;

import com.bfwg.model.User;

/**
 * Created by fan.jin on 2016-10-15.
 */
public interface UserService {
    public User findByUsername(String username);
}
