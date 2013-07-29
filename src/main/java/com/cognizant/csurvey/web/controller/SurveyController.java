package com.cognizant.csurvey.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cognizant.csurvey.model.Feature;
import com.cognizant.csurvey.service.api.FeatureService;
import com.cognizant.csurvey.web.vo.FeatureInfoVO;

@Controller
public class SurveyController {

	@Autowired
	private FeatureService featureService;

	@Value("${application.server}")
	private String serverName;

	@Value("${application.server.port}")
	private String serverPort;

	@RequestMapping("/featuredata/{userId}")
	public String getFeatureDataResult(Model model, HttpServletRequest request,
			@PathVariable String userId) {
		Feature feature = featureService.getActiveFeature();
		FeatureInfoVO featureInfoVO = new FeatureInfoVO();
		featureInfoVO.setName(feature.getName());
		String baseUrl = "http://" + serverName + ":" + serverPort + "/"
				+ request.getContextPath();

		featureInfoVO.setUrl(baseUrl + "/features/" + feature.getName() + "/"
				+ userId + ".do");
		model.addAttribute("featureInfo", featureInfoVO);
		return "result";
	}

}
