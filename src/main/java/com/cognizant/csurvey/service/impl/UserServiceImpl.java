package com.cognizant.csurvey.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.csurvey.model.User;
import com.cognizant.csurvey.repository.api.UserRepository;
import com.cognizant.csurvey.service.api.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepository userRepository;

	@Override
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}
}
