package com.web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BookmarksObjects {

	@FindBy(xpath="//section[@class='bookmark-wrapper hide']")
	private WebElement fadeoutSection;

	@FindBy(xpath="//div[@class='eachStory']")
	private List<WebElement> savedStoriesList;
	
	@FindBy(xpath="//div[@class='stryPart']/h3/a")
	private List<WebElement> savedStoriesHeading;
	
	@FindBy(xpath="//div[@class='sharePart']//div[@class='del shareSprite']")
	private List<WebElement> deleteStories;
	
	
	
	/////////////////////////////
	
	public List<WebElement> getDeleteStories() {
		return deleteStories;
	}

	public List<WebElement> getSavedStoriesHeading() {
		return savedStoriesHeading;
	}

	public List<WebElement> getSavedStoriesList() {
		return savedStoriesList;
	}

	public WebElement getFadeoutSection() {
		return fadeoutSection;
	}
	
	
}
