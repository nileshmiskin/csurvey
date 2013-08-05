package com.cognizant.csurvey.repository.api;

import java.util.List;

import com.cognizant.csurvey.model.Application;
import com.cognizant.csurvey.model.ApplicationStats;

public interface ApplicationRepository extends GenericRepository<Application>{
	List<ApplicationStats> findApplicationStats();
}
