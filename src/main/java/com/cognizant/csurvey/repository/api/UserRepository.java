package com.cognizant.csurvey.repository.api;

import com.cognizant.csurvey.model.User;


public interface UserRepository extends GenericRepository<User>{
	User findByUsername(String username);
}
