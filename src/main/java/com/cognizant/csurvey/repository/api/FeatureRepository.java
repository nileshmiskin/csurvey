package com.cognizant.csurvey.repository.api;

import com.cognizant.csurvey.model.Feature;

public interface FeatureRepository extends GenericRepository<Feature>{
	Feature findFeatureByName(String name);
	Feature getActiveFeature();
}
