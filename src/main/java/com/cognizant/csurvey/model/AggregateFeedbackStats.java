package com.cognizant.csurvey.model;

public class AggregateFeedbackStats {

	private String featureId;
	private Long likeCount;

	public String getFeatureId() {
		return featureId;
	}

	public void setFeatureId(String featureId) {
		this.featureId = featureId;
	}

	public Long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}
	
	@Override
	public String toString() {
		return this.featureId+", "+this.likeCount;
	}

}
