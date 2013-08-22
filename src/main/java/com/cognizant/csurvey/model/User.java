package com.cognizant.csurvey.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {

	@Id
	private ObjectId id;
	private String name;
	private String username;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public ObjectId getId() {
		return this.id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}

}
