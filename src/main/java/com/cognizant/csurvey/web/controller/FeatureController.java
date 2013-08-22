package com.cognizant.csurvey.web.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cognizant.csurvey.model.Feature;
import com.cognizant.csurvey.model.FeatureList;
import com.cognizant.csurvey.model.Feedback;
import com.cognizant.csurvey.model.User;
import com.cognizant.csurvey.service.api.FeatureService;
import com.cognizant.csurvey.service.api.FeedbackService;
import com.cognizant.csurvey.service.api.UserService;
import com.cognizant.csurvey.web.constant.CommonConstants;
import com.cognizant.csurvey.web.vo.FeatureVO;
import com.cognizant.csurvey.web.vo.FeedbackVO;
import com.cognizant.csurvey.web.vo.UserVO;
import com.mongodb.gridfs.GridFSDBFile;

@Controller
public class FeatureController {

	@Autowired
	private FeatureService featureService;

	@Autowired
	private FeedbackService feedbackService;

	@Autowired
	private UserService userService;

	@Autowired
	private Jaxb2Marshaller jaxbMashaller;

	@Value("${application.server}")
	private String serverName;

	@Value("${application.server.port}")
	private String serverPort;

	public void setJaxb2Mashaller(Jaxb2Marshaller jaxb2Mashaller) {
		this.jaxbMashaller = jaxb2Mashaller;
	}

	@RequestMapping("/features")
	public ModelAndView getFeatures() throws FileNotFoundException {

		List<Feature> features = featureService.getAllFeatures();
		FeatureList featureList = new FeatureList();
		featureList.setFeatureList(features);
		return new ModelAndView(CommonConstants.FEATURES_VIEW, "features",
				featureList);
	}

	@RequestMapping("/features/{name}/limit/{limit}")
	public String getFeature(@PathVariable("name") String name,
			@PathVariable("limit") int limit, Model model,
			HttpServletRequest request) {
		Feature feature = featureService.getFeatureByName(name);
		FeatureVO featureVO = new FeatureVO();
		BeanUtils.copyProperties(feature, featureVO);
		String featureImageURL = request.getContextPath() + "/image/"
				+ feature.getImageName() + ".do";
		featureVO.setFeatureImageURL(featureImageURL);
		List<Feedback> feedbacks = feedbackService.getFeeedbacksByFeature(
				feature, -1);
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
			featureVO.setFeatureStats(featureService.getFeatureStats(feature));
			feedbackVOs.add(feedbackVO);
		}
		featureVO.setFeedbacks(feedbackVOs);
		model.addAttribute("feature", featureVO);
		return CommonConstants.FEATURE_FEEDBACK_VIEW;
	}

	@RequestMapping("/features/{name}/{userId}")
	public String getFeatureForUser(@PathVariable String name,
			@PathVariable String userId, Model model, HttpServletRequest request) {
		Feature feature = featureService.getFeatureByName(name);
		FeatureVO featureVO = new FeatureVO();
		BeanUtils.copyProperties(feature, featureVO);

		String featureImageURL = "http://" + serverName + ":" + serverPort
				+ request.getContextPath() + "/image/" + feature.getImageName()
				+ ".do";

		featureVO.setFeatureImageURL(featureImageURL);

		User user = new User();
		user.setId(new ObjectId(userId));

		Feedback feedback = feedbackService.getFeedbackByFeature(feature, user);

		if (null != feedback) {
			user = feedback.getUser();
			UserVO userVO = new UserVO();
			BeanUtils.copyProperties(user, userVO);
			userVO.setUserId(user.getId().toString());
			FeedbackVO feedbackVO = new FeedbackVO();
			BeanUtils.copyProperties(feedback, feedbackVO);
			feedbackVO.setFeedbackId(feedback.getId().toString());
			feedbackVO.setUserVO(userVO);

			List<FeedbackVO> feedbackVOs = new ArrayList<FeedbackVO>();
			feedbackVOs.add(feedbackVO);
			featureVO.setFeedbacks(feedbackVOs);
		}

		model.addAttribute("feature", featureVO);
		return CommonConstants.FEATURE_FEEDBACK_VIEW;
	}

	@RequestMapping("/featureforuser/{featurename}/{username}")
	public String getFeatureForUserByUsername(
			@PathVariable("featurename") String featureName,
			@PathVariable("username") String username, Model model,
			HttpServletRequest request) {
		Feature feature = featureService.getFeatureByName(featureName);
		FeatureVO featureVO = new FeatureVO();
		BeanUtils.copyProperties(feature, featureVO);

		String featureImageURL = "http://" + serverName + ":" + serverPort
				+ request.getContextPath() + "/image/" + feature.getImageName()
				+ ".do";

		featureVO.setFeatureImageURL(featureImageURL);

		User user = userService.getUserByUsername(username);
		Feedback feedback = null;
		if (null != user) {
			feedback = feedbackService.getFeedbackByFeature(feature,
					user);
		}

		if (null != feedback) {
			user = feedback.getUser();
			UserVO userVO = new UserVO();
			BeanUtils.copyProperties(user, userVO);
			userVO.setUserId(user.getId().toString());
			FeedbackVO feedbackVO = new FeedbackVO();
			BeanUtils.copyProperties(feedback, feedbackVO);
			feedbackVO.setFeedbackId(feedback.getId().toString());
			feedbackVO.setUserVO(userVO);

			List<FeedbackVO> feedbackVOs = new ArrayList<FeedbackVO>();
			feedbackVOs.add(feedbackVO);
			featureVO.setFeedbacks(feedbackVOs);
		}

		model.addAttribute("feature", featureVO);
		return CommonConstants.FEATURE_FEEDBACK_VIEW;
	}

	@RequestMapping("/image/{imageName}")
	public void getImage(@PathVariable String imageName,
			HttpServletResponse response) throws IOException {

		GridFSDBFile gridFSDBFile = featureService.getFeatureImage(imageName);
		OutputStream out = response.getOutputStream();
		InputStream in = gridFSDBFile.getInputStream();
		byte[] buffer = new byte[1024];
		while (in.read(buffer) > -1) {
			out.write(buffer);
		}

	}
}
