package com.cognizant.csurvey.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.csurvey.model.AggregateFeedbackStats;
import com.cognizant.csurvey.model.Feature;
import com.cognizant.csurvey.model.FeedbackStats;
import com.cognizant.csurvey.repository.api.FeatureRepository;
import com.cognizant.csurvey.repository.api.FeedbackRepository;
import com.cognizant.csurvey.repository.api.FileStorageRepository;
import com.cognizant.csurvey.service.api.FeatureService;
import com.mongodb.gridfs.GridFSDBFile;

@Service
public class FeatureServiceImpl implements FeatureService {

	@Autowired
	private FeatureRepository featureRepository;

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Autowired
	private FileStorageRepository fileStorageRepository;

	@Override
	public List<Feature> getAllFeatures() {
		List<Feature> features = featureRepository.findAll();
		return features;
	}

	@Override
	public Feature getFeatureByName(String name) {
		return featureRepository.findFeatureByName(name);
	}

	@Override
	public void saveFeature(Feature feature) {
		featureRepository.save(feature);
	}

	@Override
	public void deleteFeature(String id) {
		featureRepository.delete(id);

	}

	@Override
	public GridFSDBFile getFeatureImage(String imageName) {
		return fileStorageRepository.get(imageName);
	}

	@Override
	public Feature getActiveFeature() {
		return featureRepository.getActiveFeature();
	}

	@Override
	public FeedbackStats getFeatureStats(Feature feature) {
		Long likeCount = feedbackRepository.getLikeCount(feature);
		Long totalUsers = feedbackRepository.getFeedbackCount(feature);
		FeedbackStats featureStats = null;
		if (null != likeCount && null != totalUsers) {
			featureStats = new FeedbackStats();
			featureStats.setFeatureId(feature.getId().toString());
			featureStats.setLikeCount(likeCount);
			featureStats.setTotalUsers(totalUsers);
			featureStats.setDislikeCount(totalUsers - likeCount);
		}
		return featureStats;
	}

	@Override
	public List<AggregateFeedbackStats> getAggergateFeedbackStats() {
		List<AggregateFeedbackStats> feedbackStats = feedbackRepository
				.findAggregateFeedbackStats();
		return feedbackStats;
	}

	@Override
	public Feature getById(String id) {
		return featureRepository.findById(id);
	}

}
