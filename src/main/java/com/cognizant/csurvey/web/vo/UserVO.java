package com.cognizant.csurvey.web.vo;


public class UserVO {

	private String userId;
	private String name;

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String id) {
		this.userId = id;		
	}
}
