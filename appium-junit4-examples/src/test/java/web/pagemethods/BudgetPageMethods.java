package web.pagemethods;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import web.pageobjects.BudgetPageObjects;

public class BudgetPageMethods {

	private WebDriver driver;
	private BudgetPageObjects budgetPageObjects;

	public BudgetPageMethods(WebDriver driver) {
		this.driver = driver;
		budgetPageObjects = PageFactory.initElements(driver, BudgetPageObjects.class);
		WaitUtil.waitForAdToDisappear(driver);
	}

	public BudgetPageObjects getBudgetPageObjects() {
		return budgetPageObjects;
	}

	public List<WebElement> getEconomyHeadlines() {
		return budgetPageObjects.getEconomyHeadlines();
	}

	public List<WebElement> getBudgetForMFHeadlines() {
		return budgetPageObjects.getBudgetForMFHeadlines();
	}

	public List<WebElement> getBudgetForSMEHeadlines() {
		return budgetPageObjects.getBudgetForSMEHeadlines();
	}

	public List<WebElement> getBudgetVideos() {
		return budgetPageObjects.getBudgetVideos();
	}

	public List<WebElement> getCornerOfficeHeadlines() {
		return budgetPageObjects.getCornerOfficeHeadlines();
	}

	public List<WebElement> getExpertViewsHeadlines() {
		return budgetPageObjects.getExpertViewsHeadlines();
	}

	public List<WebElement> getHindiBudgetHeadlines() {
		return budgetPageObjects.getHindiBudgetHeadlines();
	}

	public List<WebElement> getSlideshowHeadlines() {
		return budgetPageObjects.getSlideshowHeadlines();
	}

	public List<WebElement> getTaxesNYouHeadlines() {
		return budgetPageObjects.getTaxesNyouHeadlines();
	}

	public List<WebElement> getThinkTankHeadlines() {
		return budgetPageObjects.getThinkTankHeadlines();
	}

	public List<WebElement> getTechHeadlines() {
		return budgetPageObjects.getTechHeadlines();
	}

	public List<WebElement> getNewsSectionHeadlines() {
		return budgetPageObjects.getNewsSectionHeadlines();
	}

	public String getCornerOfficeLink() {
		return budgetPageObjects.getCornerOfficeLink().getAttribute("href");
	}

	public String getEconomyLink() {
		return budgetPageObjects.getEconomyLink().getAttribute("href");
	}

	public String getTechLink() {
		return budgetPageObjects.getTechLink().getAttribute("href");
	}

	public String getBudgetForMFLink() {
		return budgetPageObjects.getBudgetForMFLink().getAttribute("href");
	}

	public String getThinkTankLink() {
		return budgetPageObjects.getThinkTankLink().getAttribute("href");
	}

	public String getExpertViewsLink() {
		return budgetPageObjects.getExpertViewsLink().getAttribute("href");
	}

	public String getTaxesNYouLink() {
		return budgetPageObjects.getTaxesNYouLink().getAttribute("href");
	}

	public String getHindiBudgetLink() {
		return budgetPageObjects.getHindiBudgetLink().getAttribute("href");
	}

	public String getBudgetVideosLink() {
		return budgetPageObjects.getBudgetVideosLink().getAttribute("href");
	}

	public String getSlideshowsLink() {
		return budgetPageObjects.getSlideshowsLink().getAttribute("href");
	}

	public String getBudgetForSMELink() {
		return budgetPageObjects.getBudgetForSMELink().getAttribute("href");
	}

	public String getNewsSectionLink() {
		return budgetPageObjects.getNewsSectionLink().getAttribute("href");
	}

	public List<WebElement> getBudgetHeadlines() {
		return budgetPageObjects.getBudgetHeadlines();
	}

	public List<WebElement> getSliderBudgetHeadlines() {
		return budgetPageObjects.getSliderBudgetHeadlines();
	}

	public List<WebElement> getIndustryImpactTabs() {
		return budgetPageObjects.getIndustryImpactTabs();
	}

	public List<WebElement> getIndustryTabStories() {
		return budgetPageObjects.getIndustryTabStories();
	}

	public WebElement getMoreFromIndustryTabLink() {
		return budgetPageObjects.getMoreFromIndustryTabLink();
	}

	public WebElement getBenchMarkTab() {
		return budgetPageObjects.getBenchMarkTab();
	}

	public WebElement getGainersLosersTab() {
		return budgetPageObjects.getGainersLosersTab();
	}

	public List<WebElement> getMarketsLiveTabs() {
		return budgetPageObjects.getMarketsLiveTabs();
	}

	public WebElement getIndustryData() {
		return budgetPageObjects.getIndustryData();
	}

	public List<WebElement> getMarketsLiveCompanies() {
		return budgetPageObjects.getMarketsLiveCompanies();
	}

	public List<String> getSliderHeadlines(List<WebElement> li) {
		List<WebElement> listEl = new LinkedList<>();
		ArrayList<String> titles = new ArrayList<>();
		listEl = li;

		try {
			for (WebElement we : listEl) {
				titles.add(WebBaseMethods.getTextUsingJSE(we));
			}

		} catch (Exception e) {

		}

		for (String tt : titles) {

			System.out.println(tt);
		}

		return titles;
	}

	public void clickNonActiveCarouselTab(List<WebElement> li) {
		WebElement el;
		try {
			el = li.get(0);
			WebBaseMethods.scrollElementIntoViewUsingJSE(el);
			WebBaseMethods.clickElementUsingJSE(el);
		} catch (Exception e) {

		}
		WaitUtil.sleep(2000);
	}

	public String getMoreFromSectionLink(String sectionName) {
		return budgetPageObjects.getMoreFromSectionLink(sectionName).getAttribute("href");

	}
	
	public WebElement getMoreFromSectionLinkElement(String sectionName) {
		return budgetPageObjects.getMoreFromSectionLink(sectionName);

	}

	public List<String> getSectionNewsHref(List<WebElement> li) {
		List<String> news = new ArrayList<>();
		int counter = 0;
		boolean retryFlag = false;
		do {
			try {
				news = WebBaseMethods.getListHrefUsingJSE(li);
			} catch (WebDriverException e) {
				retryFlag = true;
				counter++;
			}
		} while (counter < 5 && retryFlag);
		return news;
	}
	public List<String> getAllUrls() {
		List<String> allUrls = new LinkedList<>();
		try {
			allUrls = WebBaseMethods.getListHrefUsingJSE(budgetPageObjects.getAllUrls());
		} catch (Exception ee) {
			ee.printStackTrace();
			allUrls = new LinkedList<>();
		}
		return allUrls;

	}
	

}
