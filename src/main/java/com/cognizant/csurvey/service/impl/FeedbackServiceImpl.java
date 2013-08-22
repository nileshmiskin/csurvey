package com.cognizant.csurvey.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.csurvey.model.Feature;
import com.cognizant.csurvey.model.Feedback;
import com.cognizant.csurvey.model.User;
import com.cognizant.csurvey.repository.api.ApplicationRepository;
import com.cognizant.csurvey.repository.api.FeedbackRepository;
import com.cognizant.csurvey.service.api.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;
	
	@Autowired
	private ApplicationRepository applicationRepository;

	@Override
	public Feedback getFeebackById(String id) {
		return feedbackRepository.findById(id);
	}

	@Override
	public void saveFeedback(Feedback feedback) {
		Feedback fd = feedbackRepository.findByFeatureForUser(
				feedback.getFeature(), feedback.getUser());
		if (fd == null) {
			feedbackRepository.save(feedback);
			fd = feedbackRepository.findByFeatureForUser(
					feedback.getFeature(), feedback.getUser());
			applicationRepository.saveUser(feedback.getFeature().getApplication(), feedback.getUser());
		} else {
			feedbackRepository.update(feedback, fd.getId());
		}
		
	}

	@Override
	public void deleteFeedback(String id) {
		feedbackRepository.delete(id);
	}

	@Override
	public void likeFeedback(String id) {
		feedbackRepository.saveLike(id, true);
	}

	@Override
	public void dislikeFeedback(String id) {
		feedbackRepository.saveLike(id, false);
	}

	@Override
	public List<Feedback> getFeeedbacksByFeature(Feature feature, int limit) {
		return feedbackRepository.findByFeature(feature, limit);
	}

	@Override
	public Feedback getFeedbackByFeature(Feature feature, User user) {
		return feedbackRepository.findByFeatureForUser(feature, user);
	}

	@Override
	public List<Feedback> getFeedbacksByFeatureLiking(Feature feature,
			boolean like) {
		return feedbackRepository.findByFeatureLiking(feature, like);
	}

	@Override
	public Long getTotalFeedbackCount() {
		return feedbackRepository.findTotalFeedbackCount();
	}

}
