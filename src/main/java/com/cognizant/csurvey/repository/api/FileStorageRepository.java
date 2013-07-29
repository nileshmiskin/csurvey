package com.cognizant.csurvey.repository.api;

import java.io.InputStream;

import com.mongodb.gridfs.GridFSDBFile;

public interface FileStorageRepository {
	String save(InputStream inputStream, String contentType, String filename);
	GridFSDBFile get(String imageName);
}
