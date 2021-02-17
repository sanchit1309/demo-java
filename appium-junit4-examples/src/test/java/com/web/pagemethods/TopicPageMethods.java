package com.web.pagemethods;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import web.pageobjects.TopicPageObjects;

public class TopicPageMethods {
	private WebDriver driver;
	private TopicPageObjects topicPageObjects;

	public TopicPageMethods(WebDriver driver) {
		this.driver = driver;
		topicPageObjects = PageFactory.initElements(driver, TopicPageObjects.class);
	}

	public List<String> getActiveSectionHref() {
		List<String> hrefList = new LinkedList<>();
		try {
			hrefList = WebBaseMethods.getListHrefUsingJSE(topicPageObjects.getActiveListUrl());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return hrefList;

	}

	public boolean clickAllTab() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(topicPageObjects.getAllTab());

		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return flag;

	}

	public boolean clickNewsTab() {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(topicPageObjects.getNewsTab());
			
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return flag;

	}

	public boolean clickVideosTab() {
		boolean flag = false;
		try {
			flag =  WebBaseMethods.clickElementUsingJSE(topicPageObjects.getVideosTab());
			
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return flag;

	}

	public boolean clickPhotosTab() {
		boolean flag = false;
		try {
			flag =  WebBaseMethods.clickElementUsingJSE(topicPageObjects.getPhotosTab());
			
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return flag;

	}

	public boolean clickSectionTab(String sectionName) {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(topicPageObjects.getSectionTab(sectionName));
			
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return flag;
	}

	public List<String> checkIfListIsUnique(List<String> hrefList) {
		List<String> dupLinks = new LinkedList<String>();
		try {
			dupLinks = VerificationUtil.isListUnique(hrefList);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return dupLinks;
	}

	public List<String> getAllErrorLinks(List<String> hrefList) {
		List<String> errorLinks = new LinkedList<String>();
		System.out.println(hrefList);
		System.out.println(hrefList.size());
		hrefList.forEach(href -> {
			try {
				int response = HTTPResponse.checkResponseCode(href);
				System.out.println(href + "-------" + response);
				if (!(response == 200)) {
					errorLinks.add(href);
				}
			} catch (Exception ee) {
				ee.printStackTrace();
				errorLinks.add(href);
			}
		});
		return errorLinks;
	}

	public List<String> getTopSectionHref() {
		List<String> hrefList = new LinkedList<>();
		try {
			hrefList = WebBaseMethods.getListHrefUsingJSE(topicPageObjects.getMainStoryUrl());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return hrefList;

	}

	public boolean checkStatusOfLinkIs200orNot(String href) {
		boolean flag = false;
		try {
			int response = HTTPResponse.checkResponseCode(href);
			if (response == 200) {
				flag = true;
			}
		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return flag;

	}

	public String getCompanyWidgetLink() {

		String url = "";
		try {
			url = WebBaseMethods.getHrefUsingJSE(topicPageObjects.getCompanyLink());
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		System.out.println(url);
		return url;

	}

	public String getCompanyWidgetCurrentPrice() {
		String price = "";
		try {

			price = WebBaseMethods.getTextUsingJSE(topicPageObjects.getCurrentCompanyPrice());
			System.out.println("The price is " + price);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return price;
	}

}
