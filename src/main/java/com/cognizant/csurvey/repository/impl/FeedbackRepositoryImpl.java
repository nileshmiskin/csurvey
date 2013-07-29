package com.cognizant.csurvey.repository.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.cognizant.csurvey.model.Feature;
import com.cognizant.csurvey.model.Feedback;
import com.cognizant.csurvey.model.User;
import com.cognizant.csurvey.repository.api.FeedbackRepository;

@Repository
public class FeedbackRepositoryImpl implements FeedbackRepository {

	private MongoTemplate mongoTemplate;

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public List<Feedback> findAll() {
		return mongoTemplate.findAll(Feedback.class);
	}

	@Override
	public void save(Feedback feedback) {
		mongoTemplate.insert(feedback);
	}

	@Override
	public Feedback findById(String id) {
		return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)),
				Feedback.class);
	}

	@Override
	public void delete(String id) {
		mongoTemplate.remove(new Query(Criteria.where("id").is(id)),
				Feedback.class);
	}

	@Override
	public List<Feedback> findByFeature(Feature feature) {
		return mongoTemplate.find(
				new Query(Criteria.where("feature.$id").is(feature.getId())),
				Feedback.class);
	}

	@Override
	public void saveLike(String feedbackId, boolean like) {
		mongoTemplate.updateFirst(
				new Query(Criteria.where("id").is(feedbackId)),
				Update.update("like", like),
				Feedback.class);
	}

	@Override
	public Feedback findByFeatureForUser(Feature feature, User user) {
		return mongoTemplate.findOne(
				new Query(Criteria
						.where("feature.$id")
						.is(feature.getId())
						.andOperator(
								Criteria.where("user.$id").is(user.getId()))),
				Feedback.class);
	}

	@Override
	public void update(Feedback fd, ObjectId id) {
				mongoTemplate.updateFirst(
					new Query(Criteria.where("id").is(id)),
					Update.update("like", fd.isLike()),
					Feedback.class);
	}
}
