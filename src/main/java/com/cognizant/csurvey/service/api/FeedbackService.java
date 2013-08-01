package com.cognizant.csurvey.service.api;

import java.util.List;

import com.cognizant.csurvey.model.Feature;
import com.cognizant.csurvey.model.Feedback;
import com.cognizant.csurvey.model.User;

public interface FeedbackService {
	List<Feedback> getFeeedbacksByFeature(Feature feature);
	
	List<Feedback> getFeedbacksByFeatureLiking(Feature feature, boolean like);
	
	Feedback getFeedbackByFeature(Feature feature, User user);

	Feedback getFeebackById(String id);

	void saveFeedback(Feedback feedback);

	void deleteFeedback(String id);

	void likeFeedback(String id);

	void dislikeFeedback(String id);
	
	Long getTotalFeedbackCount();
}
