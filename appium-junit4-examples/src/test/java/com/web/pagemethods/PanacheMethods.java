package com.web.pagemethods;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import web.pageobjects.PanacheObjects;

public class PanacheMethods {

	private PanacheObjects panacheObjects;

	public PanacheMethods(WebDriver driver) {
		panacheObjects = PageFactory.initElements(driver, PanacheObjects.class);
		int i = 0;
		while (i < 2) {
			WebBaseMethods.scrollToBottom();
			WaitUtil.sleep(5000);
			i++;
		}
	}

	public List<String> getTopStoriesText() {
		return WebBaseMethods.getListTextUsingJSE(panacheObjects.getTopStories());
	}

	public List<String> getTopStoriesHref() {
		return WebBaseMethods.getListHrefUsingJSE(panacheObjects.getTopStories());
	}

	public List<String> getSlideShowStoriesText() {
		return WebBaseMethods.getListTextUsingJSE(panacheObjects.getSlideShowStories());
	}

	public List<String> getSlideShowStoriesHref() {
		return WebBaseMethods.getListHrefUsingJSE(panacheObjects.getSlideShowStories());
	}

	public List<String> getAllStoriesText() {
		return WebBaseMethods.getListTextUsingJSE(panacheObjects.getAllStories());
	}

	public List<String> getAllStoriesHref() {
		return WebBaseMethods.getListHrefUsingJSE(panacheObjects.getAllStories());
	}

	public List<String> getBigStoriesText() {
		return WebBaseMethods.getListAttributeUsingJSE(panacheObjects.getBigStories(), "title");
	}

	public List<String> getBigStoriesHref() {
		return WebBaseMethods.getListHrefUsingJSE(panacheObjects.getBigStories());
	}

	public List<String> getBTLArticleHref() {
		return WebBaseMethods.getListHrefUsingJSE(panacheObjects.getBtlArticleTitles());
	}

	public List<String> getVisibleArticleList() {
		return WebBaseMethods
				.getListHrefUsingJSE(WebBaseMethods.getDisplayedItemFromList(panacheObjects.getBtlArticleTitles()));
	}

	public String getBTLSectionHeading() {
		return WebBaseMethods.getHrefUsingJSE(panacheObjects.getBtlSectionHeading());
	}

	public void clickNextPageBTL() {
		WebBaseMethods.clickElementUsingJSE(panacheObjects.getNextBTLSlider());
	}
}
