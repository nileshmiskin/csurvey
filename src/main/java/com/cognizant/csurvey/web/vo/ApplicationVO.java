package com.cognizant.csurvey.web.vo;


public class ApplicationVO {
	private String applicationId;
	private String name;
	private String description;

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	
	public String getApplicationId() {
		return applicationId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
