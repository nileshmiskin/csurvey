package com.cognizant.csurvey.model;

public class ApplicationStats implements Comparable<ApplicationStats>{
	private String name;
	private Long userCount;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getUserCount() {
		return userCount;
	}
	public void setUserCount(Long userCount) {
		this.userCount = userCount;
	}
	@Override
	public int compareTo(ApplicationStats o) {
		return o.getUserCount().compareTo(this.getUserCount());
	}
	
}
