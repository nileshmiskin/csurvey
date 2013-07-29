package com.cognizant.csurvey.web.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "feature")
public class FeatureVO {
	private String name;
	private String description;
	private boolean active;
	private String featureImageURL;
	private List<FeedbackVO> feedbacks;

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
	
	@Override
	public String toString() {
		return "Feature{name: " + this.name + ", description:"
				+ this.description + "}";
	}

}
