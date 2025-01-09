package com.bfwg.service;

import java.util.List;

import com.bfwg.model.User;

/**
 * Created by fan.jin on 2016-10-15.
 */
public interface UserService {
	User findById(Long id);

	User findByUsername(String username);

	List<User> findAll();

	void changePassword(String oldPassword, String newPassword);
}
