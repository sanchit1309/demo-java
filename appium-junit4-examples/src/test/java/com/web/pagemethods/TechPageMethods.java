package com.web.pagemethods;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import web.pageobjects.HomePageObjects;
import web.pageobjects.TechPageObjects;

public class TechPageMethods {
	private WebDriver driver;
	private HomePageObjects homePageObjects;
	private TechPageObjects techPageObjects;

	public TechPageMethods(WebDriver driver) {
		this.driver = driver;
		homePageObjects = PageFactory.initElements(driver, HomePageObjects.class);
		techPageObjects = PageFactory.initElements(driver, TechPageObjects.class);
		WaitUtil.waitForAdToDisappear(driver);
	}

	public TechPageObjects getTechPageObjects() {
		return techPageObjects;
	}

	public String getHardwareLink() {
		String href = "";
		try {
			href = techPageObjects.getHardwareLink().getAttribute("href");
		} catch (Exception e) {
			href = "";
		}
		return href;
	}

	public List<WebElement> getHardwareHeadlines() {
		return techPageObjects.getHardwareHeadlines();
	}

	public String getSoftwareLink() {
		String href = "";
		try {
			return techPageObjects.getSoftwareLink().getAttribute("href");
		} catch (Exception e) {
			href = "";
		}
		return href;
	}

	public List<WebElement> getSoftwareHeadlines() {
		return techPageObjects.getSoftwareHeadlines();
	}

	public String getInternetLink() {
		String href = "";
		try {
			href = techPageObjects.getInternetLink().getAttribute("href");
		} catch (Exception e) {
			href = "";
		}
		return href;
	}

	public List<WebElement> getInternetHeadlines() {
		return techPageObjects.getInternetHeadlines();
	}

	public String getItesLink() {
		String href = "";
		try {
			return techPageObjects.getItesLink().getAttribute("href");
		} catch (Exception e) {
			href = "";
		}
		return href;
	}

	public List<WebElement> getItesHeadlines() {
		return techPageObjects.getItesHeadlines();
	}

	public String getTechAndGadgetsLink() {
		String href = "";
		try {
			return techPageObjects.getTechAndGadgetsLink().getAttribute("href");
		} catch (Exception e) {
			href = "";
		}
		return href;
	}

	public List<WebElement> getTechAndGadgetsHeadlines() {
		return techPageObjects.getTechAndGadgetsHeadlines();
	}

	public List<String> getTopStoriesLinks() {
		List<String> linksHrefList = new LinkedList<>();
		try {
			linksHrefList = WebBaseMethods.getListHrefUsingJSE(techPageObjects.getTopNewsLinks());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return linksHrefList;

	}
	
	public List<String> getTopTrendingLinks() {
		List<String> linksHrefList = new LinkedList<>();
		try {
			linksHrefList = WebBaseMethods.getListHrefUsingJSE(techPageObjects.getTrendinTermsWidgetLinks());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return linksHrefList;

	}
}
