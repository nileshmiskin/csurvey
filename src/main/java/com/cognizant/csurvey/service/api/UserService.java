package com.cognizant.csurvey.service.api;

import com.cognizant.csurvey.model.User;


public interface UserService {
	User getUserByUsername(String username);
	void save(User user);
}
