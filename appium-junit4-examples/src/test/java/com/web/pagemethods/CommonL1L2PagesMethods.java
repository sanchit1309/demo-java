package com.web.pagemethods;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import web.pageobjects.CommonL1L2PagesObjects;

public class CommonL1L2PagesMethods {
	private WebDriver driver;
	private CommonL1L2PagesObjects commonL1L2PageObjects;

	public CommonL1L2PagesMethods(WebDriver driver) {
		this.driver = driver;
		commonL1L2PageObjects = PageFactory.initElements(driver, CommonL1L2PagesObjects.class);
	}

	public List<String> getVideoWidgetActiveLinksHref() {
		List<String> linksHrefList = new LinkedList<>();
		try {
			linksHrefList = WebBaseMethods
					.getListHrefUsingJSE(commonL1L2PageObjects.getActiveSectionVideoWidgetVideoLinks());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return linksHrefList;

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

	public List<String> checkIfListIsUnique(List<String> hrefList) {
		List<String> dupLinks = new LinkedList<String>();
		try {
			dupLinks = VerificationUtil.isListUnique(hrefList);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return dupLinks;
	}

	public List<WebElement> listOfVideoWidgetTabs() {
		List<WebElement> we = new LinkedList<>();
		try {
			we = commonL1L2PageObjects.getVideoWidgetTabs();
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return we;

	}

	public List<String> getTopTrendingLinksHref() {
		List<String> linksHrefList = new LinkedList<>();
		try {
			linksHrefList = WebBaseMethods.getListHrefUsingJSE(commonL1L2PageObjects.getTopTrendingTermsWidget());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return linksHrefList;

	}

	public List<String> getNotToBeMissedSectionLInks() {
		List<String> linksHrefList = new LinkedList<>();
		try {
			linksHrefList = WebBaseMethods.getListHrefUsingJSE(commonL1L2PageObjects.getNotTOBeMissedSectionLinks());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return linksHrefList;

	}

	public List<String> getTopStoriesLinks() {
		List<String> linksHrefList = new LinkedList<>();
		try {
			linksHrefList = WebBaseMethods.getListHrefUsingJSE(commonL1L2PageObjects.getTopStoriesHrefLinks());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return linksHrefList;

	}

	public List<String> getTopSearchesLinks() {
		List<String> linksHrefList = new LinkedList<>();
		try {
			linksHrefList = WebBaseMethods.getListHrefUsingJSE(commonL1L2PageObjects.getTopSearchesDiv());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return linksHrefList;

	}

	public boolean verifyTheSectionHeadingLink(String href, String sectionNameInHref) {
		boolean flag = false;
		try {
			int response = HTTPResponse.checkResponseCode(href);
			if (response == 200 && href.contains(sectionNameInHref)) {
				flag = true;
			}

		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;
		}
		return flag;

	}
	
	public List<String> getSubSectionStoriesLinks(String section) {
		List<String> linksHrefList = new LinkedList<>();
		try {
			linksHrefList = WebBaseMethods.getListHrefUsingJSE(commonL1L2PageObjects.getNewSubSectionLinks(section));

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return linksHrefList;

	}
}
