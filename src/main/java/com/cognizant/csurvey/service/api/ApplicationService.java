package com.cognizant.csurvey.service.api;

import java.util.List;

import com.cognizant.csurvey.model.Application;
import com.cognizant.csurvey.model.ApplicationStats;

public interface ApplicationService {
	public List<ApplicationStats> getApplicationStats();
	public List<Application> getAllApplications();
	public Application getApplicationById(String applicationId);
	public Application getApplicationByName(String name);
}
