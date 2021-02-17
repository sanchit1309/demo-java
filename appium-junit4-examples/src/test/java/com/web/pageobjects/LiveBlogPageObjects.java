package com.web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LiveBlogPageObjects {
	@FindBy(xpath="(//div[@class='eachStory']/a)[1]")
	private List<WebElement> liveBlogList;
	
	@FindBy(xpath="//p[@class='date-time']/span")
	private WebElement liveBlogTime;

	public List<WebElement> getLiveBlogList() {
		return liveBlogList;
	}

	public WebElement getLiveBlogTime() {
		return liveBlogTime;
	}

}
