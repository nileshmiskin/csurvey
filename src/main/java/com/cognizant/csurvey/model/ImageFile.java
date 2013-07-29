package com.cognizant.csurvey.model;

import org.springframework.web.multipart.MultipartFile;

public class ImageFile {
	private String name;
	private MultipartFile file;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public MultipartFile getFile() {
		return file;
	}
}
