package com.cognizant.csurvey.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "features")
public class FeatureList {
	private List<Feature> featureList = new ArrayList<Feature>();

	public void setFeatureList(List<Feature> featureList) {
		this.featureList = featureList;
	}

	@XmlElement(name = "feature")
	public List<Feature> getFeatureList() {
		return featureList;
	}

}
