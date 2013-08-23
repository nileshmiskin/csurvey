package com.cognizant.csurvey.repository.api;

import java.util.List;

import com.cognizant.csurvey.model.Application;
import com.cognizant.csurvey.model.Feature;

public interface FeatureRepository extends GenericRepository<Feature>{
	Feature findFeatureByName(String name);
	Feature getActiveFeature();
	Feature getActiveFeature(Application application);
	List<Feature> findByAppication(Application application);
}
