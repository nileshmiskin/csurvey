package com.cognizant.csurvey.repository.impl;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.cognizant.csurvey.model.User;
import com.cognizant.csurvey.repository.api.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {

	private MongoTemplate mongoTemplate;

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public List<User> findAll() {
		return mongoTemplate.findAll(User.class);
	}

	@Override
	public void save(User object) {
		mongoTemplate.save(object);
	}

	@Override
	public User findById(String id) {
		return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)),
				User.class);
	}

	@Override
	public void delete(String id) {
		mongoTemplate
				.remove(new Query(Criteria.where("id").is(id)), User.class);

	}

	@Override
	public User findByUsername(String username) {
		return mongoTemplate.findOne(
				new Query(Criteria.where("username").is(username)), User.class);
	}

}
