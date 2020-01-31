package com.sts.springbootsecuritybackend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sts.springbootsecuritybackend.domain.User;
import com.sts.springbootsecuritybackend.repository.UserRepository;
import com.sts.springbootsecuritybackend.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}


	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

}
