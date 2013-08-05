package com.cognizant.csurvey.web.vo;

import java.util.List;

import com.cognizant.csurvey.model.Application;
import com.cognizant.csurvey.model.ApplicationStats;
import com.cognizant.csurvey.model.Feature;

public class DashboardVO {

	private List<Feature> features;
	private List<FeatureVO> featureVOs;
	private String selectedFeature;
	private Long totalFeedbackCount;
	private String selectedApplication;
	List<Application> applications;
	List<ApplicationStats> applicationStats;
	
	public List<Feature> getFeatures() {
		return features;
	}
	
	public void setFeatures(List<Feature> features) {
		this.features = features;
	}
	
	public void setFeatureVOs(List<FeatureVO> featureVOs) {
		this.featureVOs = featureVOs;
	}
	
	public List<FeatureVO> getFeatureVOs() {
		return featureVOs;
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
	
	public void setSelectedApplication(String selectedApplication) {
		this.selectedApplication = selectedApplication;
	}
	
	public String getSelectedApplication() {
		return selectedApplication;
	}
	
	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}
	
	public List<Application> getApplications() {
		return applications;
	}
	
	public void setApplicationStats(List<ApplicationStats> applicationStats) {
		this.applicationStats = applicationStats;
	}
	
	public List<ApplicationStats> getApplicationStats() {
		return applicationStats;
	}
}
