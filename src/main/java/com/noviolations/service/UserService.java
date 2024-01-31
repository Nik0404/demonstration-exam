package com.noviolations.service;

import com.noviolations.model.User;

public interface UserService {

	public User findUserById(Long userId) throws Exception;

}
