package com.cognizant.csurvey.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.cognizant.csurvey.model.AggregateFeedbackStats;
import com.cognizant.csurvey.model.Application;
import com.cognizant.csurvey.model.ApplicationStats;
import com.cognizant.csurvey.model.Feature;
import com.cognizant.csurvey.repository.api.ApplicationRepository;
import com.mongodb.DBObject;

public class ApplicationRepositoryImpl implements ApplicationRepository {
	
	private MongoTemplate mongoTemplate;

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public List<Application> findAll() {
		return mongoTemplate.findAll(Application.class);
	}

	@Override
	public void save(Application application) {
		mongoTemplate.save(application);
	}

	@Override
	public Application findById(String id) {
		return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)),
				Application.class);
	}

	@Override
	public void delete(String id) {
		mongoTemplate.remove(new Query(Criteria.where("id").is(id)),
				Application.class);
	}

	@Override
	public List<ApplicationStats> findApplicationStats() {
		MapReduceResults<DBObject> results = mongoTemplate.mapReduce("application",
				"classpath:application-map.js",
				"classpath:application-reduce.js",
				DBObject.class);
		List<ApplicationStats> statsList = new ArrayList<ApplicationStats>();
		for(DBObject dbo: results){
			ApplicationStats stats =  new ApplicationStats();
			stats.setName((String)dbo.get(("_id")));
			stats.setUserCount(((Double)dbo.get("value")).longValue());
			statsList.add(stats);
		}
		/*List<ApplicationStats> statsList = new ArrayList<ApplicationStats>();
		List<Application> applications = this.findAll();
		for(Application application : applications){
			ApplicationStats stats =  new ApplicationStats();
			stats.setName(application.getName());
			stats.setUserCount(new Long(application.getUsers().size()));
			statsList.add(stats);
		}*/
		return statsList;
	}

}
