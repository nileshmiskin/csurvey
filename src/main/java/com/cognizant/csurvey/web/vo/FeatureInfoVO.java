package com.cognizant.csurvey.web.vo;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("featureInfo")
@XmlRootElement(name = "featureInfo")
public class FeatureInfoVO {

	private String name;
	private String url;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
