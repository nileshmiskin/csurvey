package com.cognizant.csurvey.repository.impl;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.cognizant.csurvey.model.Feature;
import com.cognizant.csurvey.repository.api.FeatureRepository;

@Repository
public class FeatureRepositoryImpl implements FeatureRepository {

	private MongoTemplate mongoTemplate;

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public List<Feature> findAll() {
		return mongoTemplate.findAll(Feature.class);
	}

	@Override
	public void save(Feature feature) {
		mongoTemplate.insert(feature);
	}

	@Override
	public Feature findById(String id) {
		return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)),
				Feature.class);
	}

	@Override
	public void delete(String id) {
		mongoTemplate.remove(new Query(Criteria.where("id").is(id)),
				Feature.class);
	}

	@Override
	public Feature findFeatureByName(String name) {
		return mongoTemplate.findOne(
				new Query(Criteria.where("name").is(name)), Feature.class);
	}

	@Override
	public Feature getActiveFeature() {
		return mongoTemplate.findOne(
				new Query(Criteria.where("active").is(true)), Feature.class);
	}

}
