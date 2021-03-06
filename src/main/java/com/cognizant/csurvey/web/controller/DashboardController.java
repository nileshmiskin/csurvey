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

import com.cognizant.csurvey.model.Application;
import com.cognizant.csurvey.model.ApplicationStats;
import com.cognizant.csurvey.model.Feature;
import com.cognizant.csurvey.model.Feedback;
import com.cognizant.csurvey.model.User;
import com.cognizant.csurvey.service.api.ApplicationService;
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

	@Autowired
	ApplicationService applicationService;

	@Value("${application.server}")
	private String serverName;

	@Value("${application.server.port}")
	private String serverPort;

	@ModelAttribute
	public DashboardVO getDashboardVO() {
		return new DashboardVO();
	}

	@RequestMapping("dashboard")
	public String showApplication(Model model,
			@ModelAttribute DashboardVO dashboardVO) {

		/* Applications to display in dropdown */
		List<Application> applications = applicationService
				.getAllApplications();

		/* Fetch user stats for all applications */
		List<ApplicationStats> applicationStats = applicationService
				.getApplicationStats();

		if (null != dashboardVO.getSelectedApplication()
				&& !dashboardVO.getSelectedApplication().equals("-1")) {
			// Fetch application data
			Application application = applicationService
					.getApplicationById(dashboardVO.getSelectedApplication());

			// Features to display in dropdown
			List<Feature> features = featureService
					.getFeaturesByApplication(application);

			List<FeatureVO> featureVOs = new ArrayList<FeatureVO>();
			for (Feature feature : features) {
				FeatureVO featureVO = new FeatureVO();
				BeanUtils.copyProperties(feature, featureVO);
				featureVO.setFeatureStats(featureService
						.getFeatureStats(feature));
				featureVOs.add(featureVO);
			}
			dashboardVO.setFeatures(features);
			dashboardVO.setFeatureVOs(featureVOs);
		}

		// Sort application stats
		Collections.sort(applicationStats);
		
		dashboardVO.setApplications(applications);
		dashboardVO.setApplicationStats(applicationStats);
		model.addAttribute("dashboardVO", dashboardVO);
		return "dashboard";
	}

	// @RequestMapping("dashboard")
	public String showDashboard(Model model) {

		/* Applications to display in dropdown */
		List<Application> applications = applicationService
				.getAllApplications();

		/* Fetch user stats for all applications */
		List<ApplicationStats> applicationStats = applicationService
				.getApplicationStats();

		DashboardVO dashboardVO = getDashboardVO();
		dashboardVO.setApplications(applications);
		dashboardVO.setApplicationStats(applicationStats);
		model.addAttribute("dashboardVO", dashboardVO);
		return "dashboard";
	}

	@RequestMapping("dashboard/{feature}")
	public String showFeedbacks(Model model,
			@PathVariable("feature") String featureName,
			HttpServletRequest request) {

		Feature feature = featureService.getFeatureByName(featureName);
		if (feature != null) {

			List<Feedback> feedbacks = feedbackService.getFeeedbacksByFeature(
					feature, 3);

			String featureImageURL = request.getContextPath() + "/image/"
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

			String featureImageURL = request.getContextPath() + "/image/"
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

			String featureImageURL = request.getContextPath() + "/image/"
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
