package com.cognizant.csurvey.web.vo;

import java.util.List;

import com.cognizant.csurvey.model.Feature;

public class DashboardVO {

	private List<Feature> features;
	private String selectedFeature;
	
	public List<Feature> getFeatures() {
		return features;
	}
	
	public void setFeatures(List<Feature> features) {
		this.features = features;
	}
	
	public String getSelectedFeature() {
		return selectedFeature;
	}
	
	public void setSelectedFeature(String selectedFeature) {
		this.selectedFeature = selectedFeature;
	}
}
