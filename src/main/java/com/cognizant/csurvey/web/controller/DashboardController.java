package com.cognizant.csurvey.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cognizant.csurvey.model.AggregateFeedbackStats;
import com.cognizant.csurvey.model.Feature;
import com.cognizant.csurvey.model.Feedback;
import com.cognizant.csurvey.model.SortByLikeCount;
import com.cognizant.csurvey.model.User;
import com.cognizant.csurvey.service.api.FeatureService;
import com.cognizant.csurvey.service.api.FeedbackService;
import com.cognizant.csurvey.web.vo.DashboardVO;
import com.cognizant.csurvey.web.vo.FeatureVO;
import com.cognizant.csurvey.web.vo.FeedbackVO;
import com.cognizant.csurvey.web.vo.UserVO;

@Controller
@RequestMapping("/admin/*")
public class DashboardController {

	@Autowired
	FeatureService featureService;

	@Autowired
	FeedbackService feedbackService;

	@Value("${application.server}")
	private String serverName;

	@Value("${application.server.port}")
	private String serverPort;

	@ModelAttribute
	public DashboardVO getDashboardVO() {
		return new DashboardVO();
	}

	@RequestMapping("dashboard")
	public String showDashboard(Model model) {
		
		/* Features to display in dropdown */
		List<Feature> features = featureService.getAllFeatures();
		
		/* Fetch likes per feature  */
		List<AggregateFeedbackStats> feedbackStats = featureService
				.getAggergateFeedbackStats();
		Collections.sort(feedbackStats, new SortByLikeCount());
		
		/* Fetch total feedback count  */
		Long totalFeedbackCount = feedbackService.getTotalFeedbackCount();
		List<FeatureVO> featureVOs = new ArrayList<FeatureVO>();
		long count = 0;
		for (int i = 0; i < 4 && i < feedbackStats.size(); i++) {
			Feature feature = featureService.getById(feedbackStats.get(i)
					.getFeatureId());
			FeatureVO fvo = new FeatureVO();
			BeanUtils.copyProperties(feature, fvo);
			Long likeCount = feedbackStats.get(i).getLikeCount();
			count += likeCount;
			fvo.setLikeCount(likeCount);
			featureVOs.add(fvo);
		}
		
		/* Treat all other features as Other feature*/
		FeatureVO fvo = new FeatureVO();
		fvo.setName("Others");
		fvo.setLikeCount(totalFeedbackCount - count);
		featureVOs.add(fvo);

		DashboardVO dashboardVO = getDashboardVO();
		dashboardVO.setFeatures(features);
		dashboardVO.setTopFeatures(featureVOs);
		dashboardVO.setTotalFeedbackCount(totalFeedbackCount);
		model.addAttribute("dashboardVO", dashboardVO);
		return "dashboard";
	}

	@RequestMapping("dashboard/{feature}")
	public String showFeedbacks(Model model,
			@PathVariable("feature") String featureName,
			HttpServletRequest request) {

		Feature feature = featureService.getFeatureByName(featureName);
		if (feature != null) {

			List<Feedback> feedbacks = feedbackService
					.getFeeedbacksByFeature(feature);

			String featureImageURL = "http://" + serverName + ":" + serverPort
					+ request.getContextPath() + "/image/"
					+ feature.getImageName() + ".do";

			List<FeedbackVO> feedbackVOs = new ArrayList<FeedbackVO>();
			for (Feedback feedback : feedbacks) {
				User user = feedback.getUser();
				UserVO userVO = new UserVO();
				BeanUtils.copyProperties(user, userVO);
				userVO.setUserId(user.getId().toString());
				FeedbackVO feedbackVO = new FeedbackVO();
				BeanUtils.copyProperties(feedback, feedbackVO);
				feedbackVO.setFeedbackId(feedback.getId().toString());
				feedbackVO.setUserVO(userVO);
				feedbackVOs.add(feedbackVO);
			}

			FeatureVO featureVO = new FeatureVO();
			BeanUtils.copyProperties(feature, featureVO);
			featureVO.setFeatureImageURL(featureImageURL);

			featureVO.setFeedbacks(feedbackVOs);
			featureVO.setFeatureStats(featureService.getFeatureStats(feature));

			model.addAttribute("feature", featureVO);
		}
		return "featureDetails";
	}

	@RequestMapping("dashboard/{feature}/liked")
	public String showLikedFeedbacks(Model model,
			@PathVariable("feature") String featureName,
			HttpServletRequest request) {

		Feature feature = featureService.getFeatureByName(featureName);
		if (feature != null) {

			List<Feedback> feedbacks = feedbackService
					.getFeedbacksByFeatureLiking(feature, true);

			String featureImageURL = "http://" + serverName + ":" + serverPort
					+ request.getContextPath() + "/image/"
					+ feature.getImageName() + ".do";

			List<FeedbackVO> feedbackVOs = new ArrayList<FeedbackVO>();
			for (Feedback feedback : feedbacks) {
				User user = feedback.getUser();
				UserVO userVO = new UserVO();
				BeanUtils.copyProperties(user, userVO);
				userVO.setUserId(user.getId().toString());
				FeedbackVO feedbackVO = new FeedbackVO();
				BeanUtils.copyProperties(feedback, feedbackVO);
				feedbackVO.setFeedbackId(feedback.getId().toString());
				feedbackVO.setUserVO(userVO);
				feedbackVOs.add(feedbackVO);
			}

			FeatureVO featureVO = new FeatureVO();
			BeanUtils.copyProperties(feature, featureVO);
			featureVO.setFeatureImageURL(featureImageURL);

			featureVO.setFeedbacks(feedbackVOs);
			featureVO.setFeatureStats(featureService.getFeatureStats(feature));

			model.addAttribute("feature", featureVO);
		}
		return "feedbacks";
	}

	@RequestMapping("dashboard/{feature}/disliked")
	public String showDislikedFeedbacks(Model model,
			@PathVariable("feature") String featureName,
			HttpServletRequest request) {

		Feature feature = featureService.getFeatureByName(featureName);
		if (feature != null) {

			List<Feedback> feedbacks = feedbackService
					.getFeedbacksByFeatureLiking(feature, false);

			String featureImageURL = "http://" + serverName + ":" + serverPort
					+ request.getContextPath() + "/image/"
					+ feature.getImageName() + ".do";

			List<FeedbackVO> feedbackVOs = new ArrayList<FeedbackVO>();
			for (Feedback feedback : feedbacks) {
				User user = feedback.getUser();
				UserVO userVO = new UserVO();
				BeanUtils.copyProperties(user, userVO);
				userVO.setUserId(user.getId().toString());
				FeedbackVO feedbackVO = new FeedbackVO();
				BeanUtils.copyProperties(feedback, feedbackVO);
				feedbackVO.setFeedbackId(feedback.getId().toString());
				feedbackVO.setUserVO(userVO);
				feedbackVOs.add(feedbackVO);
			}

			FeatureVO featureVO = new FeatureVO();
			BeanUtils.copyProperties(feature, featureVO);
			featureVO.setFeatureImageURL(featureImageURL);

			featureVO.setFeedbacks(feedbackVOs);
			featureVO.setFeatureStats(featureService.getFeatureStats(feature));

			model.addAttribute("feature", featureVO);
		}
		return "feedbacks";
	}
}
