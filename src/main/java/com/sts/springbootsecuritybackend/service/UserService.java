package com.sts.springbootsecuritybackend.service;

import java.util.List;


import com.sts.springbootsecuritybackend.domain.User;

public interface UserService{
	User findByUsername(String username);
	
	List<User> getAllUsers();
}
