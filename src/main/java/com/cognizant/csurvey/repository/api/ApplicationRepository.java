package com.cognizant.csurvey.repository.api;

import java.util.List;

import com.cognizant.csurvey.model.Application;
import com.cognizant.csurvey.model.ApplicationStats;
import com.cognizant.csurvey.model.User;

public interface ApplicationRepository extends GenericRepository<Application>{
	List<ApplicationStats> findApplicationStats();
	void saveUser(Application application, User user);
}
