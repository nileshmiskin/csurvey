package com.cognizant.csurvey.repository.api;

import java.util.List;

import org.bson.types.ObjectId;

import com.cognizant.csurvey.model.Feature;
import com.cognizant.csurvey.model.AggregateFeedbackStats;
import com.cognizant.csurvey.model.Feedback;
import com.cognizant.csurvey.model.User;

public interface FeedbackRepository extends GenericRepository<Feedback>{
	List<Feedback> findByFeature(Feature feature);
	List<Feedback> findByFeatureLiking(Feature feature, boolean like);
	Feedback findByFeatureForUser(Feature feature, User user);
	void saveLike(String feedbackId, boolean like);
	void update(Feedback fd, ObjectId id);
	Long getLikeCount(Feature feature);
	Long getFeedbackCount(Feature feature);
	List<AggregateFeedbackStats> findAggregateFeedbackStats();
	Long findTotalFeedbackCount();
}
