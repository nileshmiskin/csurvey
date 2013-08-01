package com.cognizant.csurvey.service.api;

import java.util.List;

import com.cognizant.csurvey.model.AggregateFeedbackStats;
import com.cognizant.csurvey.model.Feature;
import com.cognizant.csurvey.model.FeedbackStats;
import com.mongodb.gridfs.GridFSDBFile;

public interface FeatureService {
	List<Feature> getAllFeatures();
	Feature getFeatureByName(String name);
	void saveFeature(Feature feature);
	void deleteFeature(String id);
	GridFSDBFile getFeatureImage(String imageName);
	Feature getActiveFeature();
	FeedbackStats getFeatureStats(Feature feature);
	List<AggregateFeedbackStats> getAggergateFeedbackStats();
	Feature getById(String id);
}
