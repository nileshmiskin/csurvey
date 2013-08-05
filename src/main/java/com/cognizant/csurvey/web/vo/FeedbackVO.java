package com.cognizant.csurvey.web.vo;

import javax.xml.bind.annotation.XmlElement;

public class FeedbackVO {
	private String feedbackId;
	private String comment;
	private long likeCount;
	private long dislikeCount;
	private UserVO userVO;
	private boolean like;


	public void setFeedbackId(String feedbackId) {
		this.feedbackId = feedbackId;
	}
	
	public String getFeedbackId() {
		return feedbackId;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(long likeCount) {
		this.likeCount = likeCount;
	}

	public long getDislikeCount() {
		return dislikeCount;
	}

	public void setDislikeCount(long dislikeCount) {
		this.dislikeCount = dislikeCount;
	}
	
	public void setUserVO(UserVO user) {
		this.userVO = user;
	}
	
	@XmlElement(name = "user")
	public UserVO getUserVO() {
		return userVO;
	}

	public void setLike(boolean like) {
		this.like = like;
	}
	
	public boolean isLike() {
		return like;
	}
	
	
	@Override
	public String toString() {
		return "Feedback{comment:" + this.comment + ",likeCount:"
				+ this.likeCount + ",dislikeCount:" + this.dislikeCount + "}";
	}
}
