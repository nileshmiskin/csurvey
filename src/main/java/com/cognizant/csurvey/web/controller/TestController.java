package com.cognizant.csurvey.web.controller;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

@Controller
public class TestController {

	User user1;
	User user2;
	User user3;
	User user4;
	User user5;
	User user6;
	User user7;
	User user8;

	Application a1;
	Application a2;
	Application a3;

	private User createUser(String name) {
		User user = new User();
		user.setName(name);
		mongoTemplate.save(user);
		user = mongoTemplate.findOne(
				new Query(Criteria.where("name").is(name)), User.class);
		return user;
	}

	/*
	 * private void createUsers() {
	 * 
	 * user1 = new User(); user1.setName("Ella Miller");
	 * mongoTemplate.save(user1);
	 * 
	 * user2 = new User(); user2.setName("James Chapman");
	 * mongoTemplate.save(user2);
	 * 
	 * user3 = new User(); user3.setName("Daniel Hudson");
	 * mongoTemplate.save(user3);
	 * 
	 * user4 = new User(); user4.setName("William"); mongoTemplate.save(user4);
	 * 
	 * user5 = new User(); user5.setName("Alice Turner");
	 * mongoTemplate.save(user5);
	 * 
	 * user6 = new User(); user6.setName("Ethan Holmes");
	 * mongoTemplate.save(user6);
	 * 
	 * user7 = new User(); user7.setName("Andy Murray");
	 * mongoTemplate.save(user7);
	 * 
	 * user8 = new User(); user8.setName("Laura Stone");
	 * mongoTemplate.save(user8); }
	 */

	private Feature createFeature(String fname, String desc, String iname,
			Application app) {
		Feature feature = new Feature();
		feature.setActive(true);
		feature.setName(fname);
		feature.setDescription(desc);
		feature.setImageName(iname);
		feature.setApplication(app);
		mongoTemplate.save(feature);
		feature = mongoTemplate.findOne(
				new Query(Criteria
						.where("name")
						.is(feature.getName())
						.andOperator(
								Criteria.where("application.$id").is(
										app.getId()))), Feature.class);
		return feature;
	}

	private Application createApp(String name, String desc, List<User> users) {
		Application app = new Application();
		app.setName(name);
		app.setDescription(desc);
		app.setUsers(users);
		mongoTemplate.save(app);
		app = mongoTemplate.findOne(new Query(Criteria.where("name").is(name)),
				Application.class);
		return app;
	}

	private Feedback createFeedback(String comment, boolean like,
			Feature feature, User user) {
		Feedback fb = new Feedback();
		fb.setComment(comment);
		fb.setLike(like);
		fb.setFeature(feature);
		fb.setUser(user);
		mongoTemplate.save(fb);
		fb = mongoTemplate.findOne(
				new Query(Criteria
						.where("feature.$id")
						.is(feature.getId())
						.andOperator(
								Criteria.where("user.$id").is(user.getId()))),
				Feedback.class);
		return fb;
	}

	@RequestMapping("/test/loadtestdata")
	public @ResponseBody
	String loadTestData() throws FileNotFoundException {
		// Create Users
		User user1 = this.createUser("Ella Miller");
		User user2 = this.createUser("James Chapman");
		User user3 = this.createUser("Daniel Hudson");
		User user4 = this.createUser("William");
		User user5 = this.createUser("Alice Turner");
		User user6 = this.createUser("Ethan Holmes");
		User user7 = this.createUser("Andy Murray");
		User user8 = this.createUser("Laura Stone");

		List<User> listForA1 = new ArrayList<User>();
		listForA1.add(user1);
		listForA1.add(user2);
		listForA1.add(user3);
		listForA1.add(user4);
		listForA1.add(user5);
		listForA1.add(user7);
		listForA1.add(user8);

		List<User> listForA2 = new ArrayList<User>();
		listForA2.add(user1);
		listForA2.add(user2);
		listForA2.add(user3);
		listForA2.add(user4);

		List<User> listForA3 = new ArrayList<User>();
		listForA3.add(user1);
		listForA3.add(user2);
		listForA3.add(user3);
		listForA3.add(user4);
		listForA3.add(user5);
		listForA3.add(user6);
		listForA3.add(user7);
		listForA3.add(user8);

		Application a1 = this
				.createApp(
						"O2 Wallet",
						"O2 Wallet is an app that lets you send and receive money, compare prices and shop. All on your mobile.",
						listForA1);
		Application a2 = this
				.createApp(
						"O2 Priority Moments",
						"Priority Moments is a clever new mobile service bringing you great nearby offers and experiences exclusive to O2 for O2 customers.",
						listForA2);
		Application a3 = this.createApp("My O2",
				"Access your mobile accounts quickly and easily.", listForA3);

		// Create Feature
		Feature a1f1 = this.createFeature("TrainTravel",
				"Buy, plan and save train tickets", "TrainTravel.jpg", a1);
		Feature a1f2 = this.createFeature("TopupOnTheGo",
				"Use Pay & Go Top-Up to add credit to any O2 Pay & Go mobile",
				"TopupOnTheGo.jpg", a1);
		Feature a1f3 = this.createFeature("MoneyMessage",
				"Send money as easily as sending a text", "MoneyMessage.jpg",
				a1);

		// Create Feedback for a1f1
		this.createFeedback(
				"…that doesn't even support the iPhone 5 display after so long!",
				false, a1f1, user1);
		this.createFeedback("Very useful :)", true, a1f1, user2);
		this.createFeedback("I m loving it...", true, a1f1, user3);

		// Create Feedback for a1f2
		this.createFeedback("Thanx O2.. Topups are now easy!", true, a1f2,
				user1);
		this.createFeedback("Very useful :)", true, a1f2, user2);
		this.createFeedback(
				"I always use it... Wish they provide top up loans too!", true,
				a1f2, user3);
		this.createFeedback("I like this feature. Its cool!", true, a1f2, user4);

		// Create Feedback for a1f3
		this.createFeedback(
				"Not everyone has a visa/MasterCard to load money so to add a maestro debit card to load money would be good! ",
				true, a1f3, user4);
		this.createFeedback(
				"Some stores are charging to add money on O2 money card too!!!",
				false, a1f3, user5);
		this.createFeedback("When I click in expire date it closes the app",
				false, a1f3, user3);
		this.createFeedback("I like this feature. Its cool!", true, a1f3, user1);

		// Create Feature
		Feature a2f1 = this
				.createFeature(
						"Offers",
						"Get great offers, discounts and top deals from some of your favourite brands and stores.",
						"Offers.jpg", a2);
		Feature a2f2 = this.createFeature("PriorityPrizes",
				"Use Pay & Go Top-Up to add credit to any O2 Pay & Go mobile",
				"PriorityPrizes.jpg", a2);

		// Create Feedback for a2f1
		this.createFeedback(
				"…that doesn't even support the iPhone 5 display after so long!",
				false, a2f1, user1);
		this.createFeedback("Very useful :)", true, a2f1, user2);
		this.createFeedback("I m loving it...", true, a2f1, user3);

		// Create Feedback for a2f2
		this.createFeedback("Thanx O2..I got tickets!", true, a2f2, user1);
		this.createFeedback("Very useful :)", true, a2f2, user2);
		this.createFeedback("I always use it..", true, a2f2, user3);
		this.createFeedback("I like this feature. Its cool!", true, a2f2, user4);

		// Create Feature
		Feature a3f1 = this.createFeature("PayAndGo",
				"Check your usage and remaining balance.", "PayAndGo.jpg", a3);
		Feature a3f2 = this
				.createFeature(
						"HelpAtHand",
						"Configure alerts & zones. Update your helper details. Make a payment.",
						"HelpAtHand.jpg", a3);
		Feature a3f3 = this.createFeature("HomeBroadband",
				"View your bill. Change your package. Softwares & downloads",
				"HomeBroadband.jpg", a3);

		// Create Feedback for a3f1
		this.createFeedback(
				"…that doesn't even support the iPhone 5 display after so long!",
				false, a3f1, user1);
		this.createFeedback("Very useful :)", true, a3f1, user2);
		this.createFeedback("I m loving it...", true, a3f1, user3);

		// Create Feedback for a3f2
		this.createFeedback("Thanx O2..I got tickets!", true, a3f2, user1);
		this.createFeedback("Very useful :)", true, a3f2, user2);
		this.createFeedback("I always use it..", true, a3f2, user3);
		this.createFeedback("I like this feature. Its cool!", true, a3f3, user4);

		// Create Feedback for a3f3
		this.createFeedback("Thanx O2..I got tickets!", true, a3f3, user1);
		this.createFeedback("Very useful :)", true, a3f3, user2);
		this.createFeedback("I always use it..", true, a3f3, user3);
		this.createFeedback("I like this feature. Its cool!", true, a3f3, user4);

		testIfItSavesFileToGridFs();

		return "success";
	}

	@RequestMapping("/test/removetestdata")
	public @ResponseBody
	String removeAllData() {
		mongoTemplate.dropCollection(Feedback.class);
		mongoTemplate.dropCollection(Feature.class);
		mongoTemplate.dropCollection(Application.class);
		mongoTemplate.dropCollection(User.class);
		gridOperation.delete(new Query(Criteria.where("filename").ne(
				"Sample.jpg")));
		return "success";
	}

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

	@Autowired
	private GridFsOperations gridOperation;

	public void testIfItSavesFileToGridFs() throws FileNotFoundException {

		try {
			URL cloudURL = new URL(
					"http://a2.mzstatic.com/us/r1000/081/Purple/v4/e4/9a/26/e49a26e8-1776-6d5c-c43b-e852ad52028c/mzl.bzxdrunn.320x480-75.jpg");
			InputStream cloudstream = cloudURL.openStream();
			String id1 = fileStorageRepository.save(cloudstream, "image/jpeg",
					"MoneyMessage.jpg");

			URL navURL = new URL(
					"http://static.o2.co.uk/www/img/consumer/money/traintravel/samsungphone.png");
			InputStream navStream = navURL.openStream();
			String id2 = fileStorageRepository.save(navStream, "image/jpeg",
					"TrainTravel.jpg");

			URL mobileWalletURL = new URL(
					"http://static.o2.co.uk/www/img/money/wallet/airtime-topup.jpg");
			InputStream mobileWalletStream = mobileWalletURL.openStream();
			String id3 = fileStorageRepository.save(mobileWalletStream,
					"image/jpeg", "TopupOnTheGo.jpg");

			URL proirityOfferURL = new URL(
					"http://www.o2online.ie/o2/images/priority/Priority-Offers_364x236.jpg");
			InputStream proirityOfferStream = proirityOfferURL.openStream();
			String id4 = fileStorageRepository.save(proirityOfferStream,
					"image/jpeg", "Offers.jpg");

			URL proirityPrizeURL = new URL(
					"http://lh6.ggpht.com/0z8PoPyce546Zhu8ZdHsc8y6o8WXeMwreLPvip_dfKIkpHJaWjxJkH5O-RypAG3_sIFm=h900");
			InputStream proirityPrizeStream = proirityPrizeURL.openStream();
			String id5 = fileStorageRepository.save(proirityPrizeStream,
					"image/jpeg", "PriorityPrizes.jpg");
			
			URL helpAtHandURL = new URL(
					"http://static.o2.co.uk/www/img/kana/help_at_hand_support_tab_156_x_100.jpg");
			InputStream helpAtHandStream = helpAtHandURL.openStream();
			String id6 = fileStorageRepository.save(helpAtHandStream,
					"image/jpeg", "HelpAtHand.jpg");
			
			URL homeBroadbandURL = new URL(
					"http://www.steveandian.com/wp-content/themes/photonexus/timthumb.php?src=http://www.steveandian.com/wp-content/uploads/2013/03/O2HBB_retailHF_integ-selects.jpg&w=730&q=72");
			InputStream homeBroadbandStream = homeBroadbandURL.openStream();
			String id7 = fileStorageRepository.save(homeBroadbandStream,
					"image/jpeg", "HomeBroadband.jpg");
			
			
			URL payAndGoURL = new URL(
					"http://images.mobilefun.co.uk/graphics/240pixelp/o2-pay-as-you-go-sim-card-pack-p153-240.jpg");
			InputStream payAndGoStream = payAndGoURL.openStream();
			String id8 = fileStorageRepository.save(payAndGoStream,
					"image/jpeg", "PayAndGo.jpg");
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
