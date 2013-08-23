package com.cognizant.csurvey.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.csurvey.model.Application;
import com.cognizant.csurvey.model.ApplicationStats;
import com.cognizant.csurvey.repository.api.ApplicationRepository;
import com.cognizant.csurvey.service.api.ApplicationService;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	ApplicationRepository applicationRepository;
	
	@Override
	public List<ApplicationStats> getApplicationStats() {
		return applicationRepository.findApplicationStats();
	}

	@Override
	public List<Application> getAllApplications() {
		return applicationRepository.findAll();
	}

	@Override
	public Application getApplicationById(String applicationId) {
		return applicationRepository.findById(applicationId);
	}

	@Override
	public Application getApplicationByName(String name) {
		return applicationRepository.findByName(name);
	}

}
