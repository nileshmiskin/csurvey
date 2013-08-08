package com.cognizant.csurvey.test;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cognizant.csurvey.model.Application;
import com.cognizant.csurvey.model.ApplicationStats;
import com.cognizant.csurvey.model.Feature;
import com.cognizant.csurvey.model.Feedback;
import com.cognizant.csurvey.model.User;
import com.cognizant.csurvey.repository.api.ApplicationRepository;
import com.cognizant.csurvey.repository.api.FeedbackRepository;
import com.cognizant.csurvey.repository.api.FileStorageRepository;
import com.cognizant.csurvey.service.api.FeatureService;
import com.cognizant.csurvey.service.api.FeedbackService;
import com.mongodb.DBObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "csurvey-servlet-test.xml",
		"app-dao-test.xml" })
public class TestDataGenerator {

	User user1;
	User user2;
	User user3;
	User user4;
	User user5;

	@Autowired
	ApplicationRepository applicationRepository;

	@Autowired
	private FeatureService featureService;

	@Autowired
	private FeedbackService feedbackService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private FileStorageRepository fileStorageRepository;

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Test
	public void createUser() {

		user1 = new User();
		user1.setName("Nilesh");
		mongoTemplate.save(user1);

		user2 = new User();
		user2.setName("Abhilash");
		mongoTemplate.save(user2);

		user3 = new User();
		user3.setName("Pratik");
		mongoTemplate.save(user3);

		user4 = new User();
		user4.setName("Sujit");
		mongoTemplate.save(user4);

		user5 = new User();
		user5.setName("Vikrant");
		mongoTemplate.save(user5);

	}

	@Test
	public void createApps() {

		user1 = mongoTemplate.findOne(
				new Query(Criteria.where("name").is("Nilesh")), User.class);
		user2 = mongoTemplate.findOne(
				new Query(Criteria.where("name").is("Abhilash")), User.class);
		user3 = mongoTemplate.findOne(
				new Query(Criteria.where("name").is("Pratik")), User.class);
		user4 = mongoTemplate.findOne(
				new Query(Criteria.where("name").is("Sujit")), User.class);
		user5 = mongoTemplate.findOne(
				new Query(Criteria.where("name").is("Vikrant")), User.class);

		Application a1 = new Application();
		a1.setName("App1");
		a1.setDescription("View Your Bill.");
		List<User> users = new ArrayList<User>();
		users.add(user1);
		users.add(user2);
		users.add(user3);
		users.add(user4);
		users.add(user5);
		a1.setUsers(users);
		applicationRepository.save(a1);

		Application a2 = new Application();
		a2.setName("App2");
		a2.setDescription("View Your Bill.");
		users = new ArrayList<User>();
		users.add(user4);
		users.add(user5);
		a2.setUsers(users);
		applicationRepository.save(a2);

		Application a3 = new Application();
		a3.setName("App3");
		a3.setDescription("View Your Bill.");
		users = new ArrayList<User>();
		users.add(user1);
		users.add(user2);
		users.add(user4);
		a3.setUsers(users);
		applicationRepository.save(a3);
		List<ApplicationStats> stats = applicationRepository
				.findApplicationStats();
	}

	@Test
	public void createFeatures() {

		Application a1 = mongoTemplate.findOne(new Query(Criteria.where("name")
				.is("App1")), Application.class);
		Application a2 = mongoTemplate.findOne(new Query(Criteria.where("name")
				.is("App2")), Application.class);
		Application a3 = mongoTemplate.findOne(new Query(Criteria.where("name")
				.is("App3")), Application.class);

		user1 = mongoTemplate.findOne(
				new Query(Criteria.where("name").is("Nilesh")), User.class);
		user2 = mongoTemplate.findOne(
				new Query(Criteria.where("name").is("Abhilash")), User.class);
		user3 = mongoTemplate.findOne(
				new Query(Criteria.where("name").is("Pratik")), User.class);
		user4 = mongoTemplate.findOne(
				new Query(Criteria.where("name").is("Sujit")), User.class);
		user5 = mongoTemplate.findOne(
				new Query(Criteria.where("name").is("Vikrant")), User.class);

		Feature f = new Feature();
		f.setActive(true);
		f.setName("mySpace");
		f.setDescription("Store data in the cloud");
		f.setImageName("cloud.jpg");
		f.setApplication(a1);

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

		fb1.setUser(user1);
		fb2.setUser(user2);
		fb3.setUser(user3);
		fb4.setUser(user4);
		fb5.setUser(user5);

		featureService.saveFeature(f);
		feedbackService.saveFeedback(fb1);
		feedbackService.saveFeedback(fb2);
		feedbackService.saveFeedback(fb3);
		feedbackService.saveFeedback(fb4);
		feedbackService.saveFeedback(fb5);

		// 2nd feature
		Feature f1 = new Feature();
		f1.setActive(true);
		f1.setName("myMap");
		f1.setDescription("Your personal navigator");
		f1.setImageName("nav.jpg");
		f1.setApplication(a1);

		Feedback fb11 = new Feedback();
		fb11.setComment("Nice maps !!!");
		fb11.setLike(true);
		fb11.setFeature(f1);

		Feedback fb21 = new Feedback();
		fb21.setComment("Not up to the mark !!!");
		fb21.setLike(false);
		fb21.setFeature(f1);

		Feedback fb31 = new Feedback();
		fb31.setComment("Not accurate...");
		fb31.setLike(false);
		fb31.setFeature(f1);

		Feedback fb41 = new Feedback();
		fb41.setComment("Okk !!!");
		fb41.setLike(true);
		fb41.setFeature(f1);

		fb11.setUser(user1);
		fb21.setUser(user2);
		fb31.setUser(user3);
		fb41.setUser(user4);

		featureService.saveFeature(f1);
		feedbackService.saveFeedback(fb11);
		feedbackService.saveFeedback(fb21);
		feedbackService.saveFeedback(fb31);
		feedbackService.saveFeedback(fb41);

		// 3rd feature
		Feature f2 = new Feature();
		f2.setActive(true);
		f2.setName("Priority");
		f2.setDescription("Priority - Feel Special!");
		f2.setImageName("priority.jpg");
		f2.setApplication(a1);

		Feedback fb51 = new Feedback();
		fb51.setComment("This is totally cool !!!");
		fb51.setLike(true);
		fb51.setFeature(f2);

		Feedback fb52 = new Feedback();
		fb52.setComment("Amazing...I got world cup tickets before my friends...Whoa!!");
		fb52.setLike(true);
		fb52.setFeature(f2);

		Feedback fb53 = new Feedback();
		fb53.setComment("I feel special...");
		fb53.setLike(true);
		fb53.setFeature(f2);

		Feedback fb54 = new Feedback();
		fb54.setComment("Its just Okk !!!");
		fb54.setLike(false);
		fb54.setFeature(f2);

		fb51.setUser(user1);
		fb52.setUser(user2);
		fb53.setUser(user3);
		fb54.setUser(user4);

		featureService.saveFeature(f2);
		feedbackService.saveFeedback(fb51);
		feedbackService.saveFeedback(fb52);
		feedbackService.saveFeedback(fb53);
		feedbackService.saveFeedback(fb54);

		// 4th feature
		Feature f3 = new Feature();
		f3.setActive(true);
		f3.setName("MobileWallet");
		f3.setDescription("Mobile Wallet - Freedom from cash!");
		f3.setImageName("mobileWallet.jpg");
		f3.setApplication(a1);

		Feedback fb61 = new Feedback();
		fb61.setComment("Crap..Doesn't work !!!");
		fb61.setLike(false);
		fb61.setFeature(f3);

		Feedback fb62 = new Feedback();
		fb62.setComment("No support on my mobile!!!");
		fb62.setLike(false);
		fb62.setFeature(f3);

		Feedback fb63 = new Feedback();
		fb63.setComment("Mobile money is total freedom...I am lovin it...");
		fb63.setLike(true);
		fb63.setFeature(f3);

		Feedback fb64 = new Feedback();
		fb64.setComment("Okk !!!");
		fb64.setLike(true);
		fb64.setFeature(f3);

		fb61.setUser(user1);
		fb62.setUser(user2);
		fb63.setUser(user3);
		fb64.setUser(user4);

		featureService.saveFeature(f3);
		feedbackService.saveFeedback(fb61);
		feedbackService.saveFeedback(fb62);
		feedbackService.saveFeedback(fb63);
		feedbackService.saveFeedback(fb64);

		// 5th feature
		Feature f4 = new Feature();
		f4.setActive(true);
		f4.setName("4G");
		f4.setDescription("4G LTE - Get amazing online experience!");
		f4.setImageName("4g.jpg");
		f4.setApplication(a1);

		Feedback fb71 = new Feedback();
		fb71.setComment("Bang speed!");
		fb71.setLike(true);
		fb71.setFeature(f4);

		Feedback fb72 = new Feedback();
		fb72.setComment("I dint feel any difference compared to 3G");
		fb72.setLike(false);
		fb72.setFeature(f4);

		Feedback fb73 = new Feedback();
		fb73.setComment("Superfast...thats what I was waiting for since long! Thanks..");
		fb73.setLike(true);
		fb73.setFeature(f4);

		Feedback fb74 = new Feedback();
		fb74.setComment("Okk !!!");
		fb74.setLike(true);
		fb74.setFeature(f4);

		fb71.setUser(user1);
		fb72.setUser(user2);
		fb73.setUser(user3);
		fb74.setUser(user4);

		featureService.saveFeature(f4);
		feedbackService.saveFeedback(fb71);
		feedbackService.saveFeedback(fb72);
		feedbackService.saveFeedback(fb73);
		feedbackService.saveFeedback(fb74);

	}

	@Test
	public void testIfItSavesFileToGridFs() throws FileNotFoundException {

		try {
			URL cloudURL= new URL("http://thegadgetsquare.com/wp-content/uploads/2013/07/Cloud-Computing.jpg");
			InputStream cloudstream = cloudURL.openStream();
			String id1 = fileStorageRepository.save(cloudstream, "image/jpeg",
					"cloud.jpg");
			
			URL navURL= new URL("http://www.prominentpeaks.org.uk/images/Google_map_navigation.jpg");
			InputStream navStream = navURL.openStream();
			String id2 = fileStorageRepository.save(navStream, "image/jpeg",
					"nav.jpg");
			
			URL mobileWalletURL= new URL("http://cdn2.hubspot.net/hub/75217/file-25904848-jpg/mobile_wallet_for_landing_page.jpg?t=1364584236000");
			InputStream mobileWalletStream = mobileWalletURL.openStream();
			String id3 = fileStorageRepository.save(mobileWalletStream, "image/jpeg",
					"mobileWallet.jpg");
			
			URL gURL = new URL("http://www.rfdesignuk.com/images/4g.jpg");
			InputStream gStream = gURL.openStream();
			String id4 = fileStorageRepository.save(gStream, "image/jpeg",
					"4g.jpg");
			
			URL priorityURL = new URL("http://img-android.lisisoft.com/img/5/5/1955-1-com.goldengekko.o2pm.jpg");
			InputStream priorityStream = priorityURL.openStream();
			String id5 = fileStorageRepository.save(priorityStream, "image/jpeg",
					"priority.jpg");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

	@Test
	public void testMapReduce() {
		MapReduceResults<DBObject> results = mongoTemplate.mapReduce(new Query(
				Criteria.where("like").is(true)), "feedback",
				"classpath:com/cognizant/csurvey/test/map.js",
				"classpath:com/cognizant/csurvey/test/reduce.js",
				DBObject.class);
		for (DBObject valueObject : results) {
			System.out.println(valueObject);
			System.out.println(valueObject.get("_id"));
			System.out.println(valueObject.get("value"));
		}
		
		GroupByResults<DBObject> gbr = mongoTemplate.group(Criteria.where("like").is(true), "feedback", GroupBy.key("this.feature.$id").initialDocument("{}").reduceFunction("function (featureId, values) {var sum = 0;for (var i = 0; i < values.length; i++) sum += values[i];return sum;}"), DBObject.class);
		System.out.println(gbr);
	}

}
