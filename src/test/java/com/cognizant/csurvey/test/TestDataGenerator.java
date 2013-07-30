package com.cognizant.csurvey.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cognizant.csurvey.model.Feature;
import com.cognizant.csurvey.model.Feedback;
import com.cognizant.csurvey.model.User;
import com.cognizant.csurvey.repository.api.FileStorageRepository;
import com.cognizant.csurvey.service.api.FeatureService;
import com.cognizant.csurvey.service.api.FeedbackService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "csurvey-servlet-test.xml",
		"app-dao-test.xml" })
public class TestDataGenerator {

	@Autowired
	private FeatureService featureService;

	@Autowired
	private FeedbackService feedbackService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private FileStorageRepository fileStorageRepository;

	@Test
	public void createFeatures() {
		Feature f = new Feature();
		f.setActive(true);
		f.setName("mySpace");
		f.setDescription("Store data in the cloud");
		f.setImageName("cloud.jpg");
		
		Feedback fb1 = new Feedback();
		fb1.setComment("Nice !!!");
		fb1.setLike(true);
		fb1.setFeature(f);
		
		Feedback fb2 = new Feedback();
		fb2.setComment("good !!!");
		fb2.setLike(true);
		fb2.setFeature(f);
		
		Feedback fb3 = new Feedback();
		fb3.setComment("Not useful...");
		fb3.setLike(false);
		fb3.setFeature(f);
		
		Feedback fb4 = new Feedback();
		fb4.setComment("Greate !!!");
		fb4.setLike(true);
		fb4.setFeature(f);
		
		Feedback fb5 = new Feedback();
		fb5.setComment("Not for me...");
		fb5.setLike(false);
		fb5.setFeature(f);
		
		
		User user1 = new User();
		user1.setName("Nilesh");
		mongoTemplate.save(user1);
		user1 = mongoTemplate.findOne(
				new Query(Criteria.where("name").is("Nilesh")),
				User.class);
		fb1.setUser(user1);
		
		User user2 = new User();
		user2.setName("Abhilash");
		mongoTemplate.save(user2);
		user2 = mongoTemplate.findOne(
				new Query(Criteria.where("name").is("Abhilash")),
				User.class);
		fb2.setUser(user2);
		
		User user3 = new User();
		user3.setName("Pratik");
		mongoTemplate.save(user3);
		user1 = mongoTemplate.findOne(
				new Query(Criteria.where("name").is("Pratik")),
				User.class);
		fb3.setUser(user3);
		
		User user4 = new User();
		user4.setName("Sujit");
		mongoTemplate.save(user4);
		user4 = mongoTemplate.findOne(
				new Query(Criteria.where("name").is("Sujit")),
				User.class);
		fb4.setUser(user4);
		
		User user5 = new User();
		user5.setName("Vikrant");
		mongoTemplate.save(user5);
		user5 = mongoTemplate.findOne(
				new Query(Criteria.where("name").is("Vikrant")),
				User.class);
		fb5.setUser(user5);

//		User user = mongoTemplate.findOne(
//				new Query(Criteria.where("name").is("New user")), User.class);
//		if (null == user) {
//			user = new User();
//			user.setName("New user1");
//			mongoTemplate.save(user);
//			user = mongoTemplate.findOne(
//					new Query(Criteria.where("name").is("New user")),
//					User.class);
//		}

		
		featureService.saveFeature(f);
		feedbackService.saveFeedback(fb1);
		feedbackService.saveFeedback(fb2);
		feedbackService.saveFeedback(fb3);
		feedbackService.saveFeedback(fb4);
		feedbackService.saveFeedback(fb5);
	}

	@Test
	public void testIfItSavesFileToGridFs() throws FileNotFoundException {

		InputStream inputStream = new FileInputStream(
				"D:\\opt\\cloud.jpg");
		String id = fileStorageRepository.save(inputStream, "image/jpeg",	"cloud.jpg");
	}

}
