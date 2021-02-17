package com.web.pagemethods;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import web.pageobjects.MarketPageObjects;

public class MarketsPageMethods {

	private WebDriver driver;
	private MarketPageObjects marketsPageObjects;

	public MarketsPageMethods(WebDriver driver) {
		this.driver = driver;
		marketsPageObjects = PageFactory.initElements(driver, MarketPageObjects.class);
		WaitUtil.waitForAdToDisappear(driver);
	}

	public boolean clickTabName(String tabName) {
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(marketsPageObjects.getMarketsTabName(tabName));
			flag = true;
		} catch (NoSuchElementException e) {

		}
		return flag;
	}
	public boolean isTabShow(String tabName) {
		boolean flag = false;
		try {
			flag=marketsPageObjects.getMarketsTabName(tabName).isDisplayed();
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public WebElement getSubTabLink(String name) {
		WebElement el = null;
		try {
			el = marketsPageObjects.getSubTabLink(name);
		} catch (NoSuchElementException e) {

		}
		return el;
	}

	public List<WebElement> getStoriesLink(String idName) {
		return marketsPageObjects.getStoriesLink(idName);
	}

	public WebElement getSectionLinkHeading(String name) {
		WebElement el = null;
		try {
			el = marketsPageObjects.getSectionLinkHeading(name);
		} catch (NoSuchElementException e) {

		}
		return el;

	}

	public List<WebElement> getSectionData(String name) {

		return marketsPageObjects.getSectionData(name);

	}

}
