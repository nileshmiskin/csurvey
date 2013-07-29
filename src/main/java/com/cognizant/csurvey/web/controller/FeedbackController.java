package com.cognizant.csurvey.web.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cognizant.csurvey.model.Feature;
import com.cognizant.csurvey.model.Feedback;
import com.cognizant.csurvey.model.User;
import com.cognizant.csurvey.service.api.FeatureService;
import com.cognizant.csurvey.service.api.FeedbackService;

@Controller
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	@Autowired
	private FeatureService featureService;

	private void saveFeedback(String userId, String featureName,
			String comment, boolean like) {
		Feature feature = featureService.getFeatureByName(featureName);

		User user = new User();
		user.setId(new ObjectId(userId));

		Feedback feedback = new Feedback();
		feedback.setComment(comment);
		feedback.setFeature(feature);
		feedback.setUser(user);
		feedback.setLike(like);

		feedbackService.saveFeedback(feedback);
	}

	@RequestMapping("/feedback/{userId}/{featureName}/{comment}/like")
	public @ResponseBody
	String saveLikeFeedbackWithComment(@PathVariable("userId") String userId,
			@PathVariable("featureName") String featureName,
			@PathVariable("comment") String comment) {
		saveFeedback(userId, featureName, comment, true);
		return "success";
	}

	@RequestMapping("/feedback/{userId}/{featureName}/like")
	public @ResponseBody
	String saveLikeFeedback(@PathVariable("userId") String userId,
			@PathVariable("featureName") String featureName) {
		saveFeedback(userId, featureName, "", true);
		return "success";
	}

	@RequestMapping("/feedback/{userId}/{featureName}/{comment}/dislike")
	public @ResponseBody
	String saveDisLikeFeedbackWithComment(
			@PathVariable("userId") String userId,
			@PathVariable("featureName") String featureName,
			@PathVariable("comment") String comment) {
		saveFeedback(userId, featureName, comment, false);
		return "success";
	}

	@RequestMapping("/feedback/{userId}/{featureName}/dislike")
	public @ResponseBody
	String saveDisLikeFeedback(@PathVariable("userId") String userId,
			@PathVariable("featureName") String featureName) {
		saveFeedback(userId, featureName, "", false);
		return "success";
	}
}
