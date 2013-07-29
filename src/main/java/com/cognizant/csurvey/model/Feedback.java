package com.cognizant.csurvey.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Feedback {
	@Id
	private ObjectId id;
	private String comment;
	private boolean like;
	@DBRef
	private Feature feature;

	@DBRef
	private User user;

	public void setId(ObjectId id) {
		this.id = id;
	}

	public ObjectId getId() {
		return id;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}

	public Feature getFeature() {
		return feature;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setLike(boolean like) {
		this.like = like;
	}

	public boolean isLike() {
		return like;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "Feedback{comment:" + this.comment + ",like:" + this.like + "}";
	}
}
