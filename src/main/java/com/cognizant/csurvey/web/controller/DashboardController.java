package com.cognizant.csurvey.web.controller;

import java.util.ArrayList;
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

import com.cognizant.csurvey.model.Feature;
import com.cognizant.csurvey.model.Feedback;
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
		List<Feature> features = featureService.getAllFeatures();
		DashboardVO dashboardVO = getDashboardVO();
		dashboardVO.setFeatures(features);
		model.addAttribute("dashboardVO", dashboardVO);
		return "dashboard";
	}

	@RequestMapping("dashboard/{feature}")
	public String showFeedbacks(Model model,
			@PathVariable("feature") String featureName,
			HttpServletRequest request) {

		Feature feature = featureService.getFeatureByName(featureName);
		if (feature  != null) {
			
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

			model.addAttribute("feature", featureVO);
		}

		return "dashboard";
	}
}
