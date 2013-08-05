package com.cognizant.csurvey.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.cognizant.csurvey.model.Feature;
import com.cognizant.csurvey.model.AggregateFeedbackStats;
import com.cognizant.csurvey.model.Feedback;
import com.cognizant.csurvey.model.User;
import com.cognizant.csurvey.repository.api.FeedbackRepository;
import com.mongodb.DBObject;

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
	public List<Feedback> findByFeature(Feature feature, int limit) {
		List<Feedback> feedbacks = null;
		if(limit == -1){
			feedbacks = mongoTemplate.find(
					new Query(Criteria.where("feature.$id").is(feature.getId())),
					Feedback.class);
		}
		else{
		feedbacks = mongoTemplate.find(
				new Query(Criteria.where("feature.$id").is(feature.getId())).limit(limit),
				Feedback.class);
		}
		return feedbacks; 
	}

	@Override
	public void saveLike(String feedbackId, boolean like) {
		mongoTemplate.updateFirst(
				new Query(Criteria.where("id").is(feedbackId)),
				Update.update("like", like), Feedback.class);
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
		mongoTemplate.updateFirst(new Query(Criteria.where("id").is(id)),
				Update.update("like", fd.isLike()), Feedback.class);
	}

	@Override
	public Long getLikeCount(Feature feature) {
		return mongoTemplate.count(
				new Query(Criteria.where("feature.$id").is(feature.getId())
						.andOperator(Criteria.where("like").is(true))),
				Feedback.class);
	}

	@Override
	public Long getFeedbackCount(Feature feature) {
		return mongoTemplate.count(
				new Query(Criteria.where("feature.$id").is(feature.getId())),
				Feedback.class);
	}

	@Override
	public List<Feedback> findByFeatureLiking(Feature feature, boolean like) {
		return mongoTemplate.find(
				new Query(Criteria.where("feature.$id").is(feature.getId())
						.andOperator(Criteria.where("like").is(like))),
				Feedback.class);
	}

	@Override
	public List<AggregateFeedbackStats> findAggregateFeedbackStats() {
		MapReduceResults<DBObject> results = mongoTemplate.mapReduce(
				new Query(Criteria.where("like").is(true)), "feedback",
				"classpath:map.js",
				"classpath:reduce.js",
				DBObject.class);
		List<AggregateFeedbackStats> statsList = new ArrayList<AggregateFeedbackStats>();
		for(DBObject dbo: results){
			AggregateFeedbackStats stats =  new AggregateFeedbackStats();
			stats.setFeatureId(((ObjectId)dbo.get("_id")).toString());
			stats.setLikeCount(((Double)dbo.get("value")).longValue());
			statsList.add(stats);
		}
		return statsList;
	}

	@Override
	public Long findTotalFeedbackCount() {
		return mongoTemplate.count(new Query(), Feedback.class);
	}
}
