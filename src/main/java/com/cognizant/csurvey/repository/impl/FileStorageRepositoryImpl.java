package com.cognizant.csurvey.repository.impl;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Repository;

import com.cognizant.csurvey.repository.api.FileStorageRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

@Repository
public class FileStorageRepositoryImpl implements FileStorageRepository {

	@Autowired
	private GridFsOperations gridOperation;
	
	@Override
	public String save(InputStream inputStream, String contentType,
			String filename) {
		System.out.println("Hi Autowiring working!!");
		  DBObject metaData = new BasicDBObject();
		  metaData.put("meta1", filename);
		  metaData.put("meta2", contentType);
		 
		  GridFSFile file = gridOperation.store(inputStream, filename, metaData);
		 
		  return file.getId().toString();
	}

	@Override
	public GridFSDBFile get(String imageName) {
		GridFSDBFile gridFSDBFile = gridOperation.findOne(new Query().addCriteria(Criteria.where("filename").is(imageName)));
		return gridFSDBFile;
	}
	
	

}
