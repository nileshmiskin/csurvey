package com.cognizant.csurvey.web.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.cognizant.csurvey.model.FeedbackStats;

@XmlRootElement(name = "feature")
public class FeatureVO {
	private String name;
	private String description;
	private boolean active;
	private Long likeCount;
	private String featureImageURL;
	private List<FeedbackVO> feedbacks;
	private FeedbackStats featureStats;
	private ApplicationVO applicationVO;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}
	
	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}
	
	public Long getLikeCount() {
		return likeCount;
	}
	
	public void setFeatureImageURL(String featureImageURL) {
		this.featureImageURL = featureImageURL;
	}
	
	public String getFeatureImageURL() {
		return featureImageURL;
	}
	
	public void setFeedbacks(List<FeedbackVO> feedbacks) {
		this.feedbacks = feedbacks;
	}
	
	@XmlElement(name = "feedback")
	public List<FeedbackVO> getFeedbacks() {
		return feedbacks;
	}
	
	public void setFeatureStats(FeedbackStats featureStats) {
		this.featureStats = featureStats;
	}
	
	@XmlElement(name = "featureStats")
	public FeedbackStats getFeatureStats() {
		return featureStats;
	}
	
	public void setApplicationVO(ApplicationVO application) {
		this.applicationVO = application;
	}
	
	@XmlElement(name = "application")
	public ApplicationVO getApplicationVO() {
		return applicationVO;
	}
	
	@Override
	public String toString() {
		return "Feature{name: " + this.name + ", description:"
				+ this.description + "}";
	}

}
