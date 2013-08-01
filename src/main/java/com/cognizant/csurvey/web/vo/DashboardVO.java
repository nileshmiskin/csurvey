package com.cognizant.csurvey.web.vo;

import java.util.List;

import com.cognizant.csurvey.model.Feature;

public class DashboardVO {

	private List<Feature> features;
	private List<FeatureVO> topFeatures;
	private String selectedFeature;
	private Long totalFeedbackCount;
	
	public List<Feature> getFeatures() {
		return features;
	}
	
	public void setFeatures(List<Feature> features) {
		this.features = features;
	}
	
	public void setTopFeatures(List<FeatureVO> topFeatures) {
		this.topFeatures = topFeatures;
	}
	
	public List<FeatureVO> getTopFeatures() {
		return topFeatures;
	}
	
	public String getSelectedFeature() {
		return selectedFeature;
	}
	
	public void setSelectedFeature(String selectedFeature) {
		this.selectedFeature = selectedFeature;
	}
	
	public void setTotalFeedbackCount(Long totalFeedbackCount) {
		this.totalFeedbackCount = totalFeedbackCount;
	}
	
	public Long getTotalFeedbackCount() {
		return totalFeedbackCount;
	}
}
