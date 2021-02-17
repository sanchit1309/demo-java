package com.web.pagemethods;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import web.pageobjects.HomePageObjects;
import web.pageobjects.RisePageObjects;

public class RisePageMethods {

	private WebDriver driver;
	private RisePageObjects risePageObjects;
	private HomePageObjects homePageObjects;

	public RisePageMethods(WebDriver driver) {
		this.driver = driver;
		homePageObjects = PageFactory.initElements(driver, HomePageObjects.class);
		risePageObjects = PageFactory.initElements(driver, RisePageObjects.class);
		WaitUtil.waitForAdToDisappear(driver);
	}

	public RisePageObjects getRisePageObjects() {
		return risePageObjects;
	}

	public String getSmeLink() {
		return risePageObjects.getSmeLink().getAttribute("href");
	}

	public List<WebElement> getSmeHeadlines() {
		return risePageObjects.getSmeHeadlines();
	}

	public String getStartupsLink() {
		return risePageObjects.getStartupsLink().getAttribute("href");
	}

	public List<WebElement> getStartupsHeadlines() {
		return risePageObjects.getStartupsHeadlines();
	}

	public String getPolicyLink() {
		return risePageObjects.getPolicyLink().getAttribute("href");
	}

	public List<WebElement> getPolicyHeadlines() {
		return risePageObjects.getPolicyHeadlines();
	}

	public String getEntrepreneurshipLink() {
		return risePageObjects.getEntrepreneurshipLink().getAttribute("href");
	}

	public List<WebElement> getEntrepreneurshipHeadlines() {
		return risePageObjects.getEntrepreneurshipHeadlines();
	}

	public String getMoneyLink() {
		return risePageObjects.getMoneyLink().getAttribute("href");
	}

	public List<WebElement> getMoneyHeadlines() {
		return risePageObjects.getMoneyHeadlines();
	}

	public String getSecuritytechLink() {
		return risePageObjects.getSecuritytechLink().getAttribute("href");
	}

	public List<WebElement> getSecuritytechHeadlines() {
		return risePageObjects.getSecuritytechHeadlines();
	}

	public String getMarketingLink() {
		return risePageObjects.getMarketingLink().getAttribute("href");
	}

	public List<WebElement> getMarketingHeadlines() {
		return risePageObjects.getMarketingHeadlines();
	}

	public String getHrLink() {
		return risePageObjects.getHrLink().getAttribute("href");
	}

	public List<WebElement> getHrHeadlines() {
		return risePageObjects.getHrHeadlines();
	}

	public String getLegalLink() {
		return risePageObjects.getLegalLink().getAttribute("href");
	}

	public List<WebElement> getLegalHeadlines() {
		return risePageObjects.getLegalHeadlines();
	}

	public String getTradeLink() {
		try {
			return risePageObjects.getTradeLink().getAttribute("href");
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return "";
	}

	public List<WebElement> getTradeHeadlines() {
		return risePageObjects.getTradeHeadlines();
	}

	public String getproductLineLink() {
		try {
			return risePageObjects.getProductLineLink().getAttribute("href");
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return "";
	}

	public List<WebElement> getProductLineHeadlines() {
		return risePageObjects.getProductLineHeadlines();
	}

	public List<String> getRiseKeywordsLinks() {
		List<String> linksHrefList = new LinkedList<>();
		try {
			linksHrefList = WebBaseMethods.getListHrefUsingJSE(risePageObjects.getRiseKeywordsUnderTopStories());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return linksHrefList;

	}

	public List<String> getRiseBizListingLinks() {
		List<String> linksHrefList = new LinkedList<>();
		try {
			linksHrefList = WebBaseMethods.getListHrefUsingJSE(risePageObjects.getRiseBizListingKeywords());

		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return linksHrefList;

	}
}
