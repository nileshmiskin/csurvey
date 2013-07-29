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
		f.setName("gen1");
		f.setDescription("desc1");
		f.setImageName("test.jpg");
		Feedback fb = new Feedback();
		fb.setComment("cmt1");
		fb.setFeature(f);

		User user = mongoTemplate.findOne(
				new Query(Criteria.where("name").is("New user")), User.class);
		if (null == user) {
			user = new User();
			user.setName("New user1");
			mongoTemplate.save(user);
			user = mongoTemplate.findOne(
					new Query(Criteria.where("name").is("New user")),
					User.class);
		}

		fb.setUser(user);
		featureService.saveFeature(f);
		feedbackService.saveFeedback(fb);
	}

	@Test
	public void testIfItSavesFileToGridFs() throws FileNotFoundException {

		InputStream inputStream = new FileInputStream(
				"C:\\Users\\Public\\Pictures\\Sample Pictures\\Tulips.jpg");
		String id = fileStorageRepository.save(inputStream, "image/jpeg",
				"test.jpg");
		System.out.println("New File ID: " + id);

	}

}
