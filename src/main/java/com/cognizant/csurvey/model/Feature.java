package com.cognizant.csurvey.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "feature")
public class Feature {
	@Id
	private ObjectId id;
	private String name;
	private String description;
	private boolean active;
	private String imageName;
	@DBRef
	private Application application;
	
	public String getImageName() {
		return imageName;
	}
	
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}

	public ObjectId getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}
	
	public void setApplication(Application application) {
		this.application = application;
	}
	
	public Application getApplication() {
		return application;
	}

	@Override
	public String toString() {
		return "Feature{name: " + this.name + ", description:"
				+ this.description + "}";
	}
}
