package com.sts.springbootsecuritybackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sts.springbootsecuritybackend.domain.User;
import com.sts.springbootsecuritybackend.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;
	

	@GetMapping("/users")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_SUPERADMIN') ")
	public List<User> getAllUsers()
	{
		
		
		return this.userService.getAllUsers();
	}
	
}
