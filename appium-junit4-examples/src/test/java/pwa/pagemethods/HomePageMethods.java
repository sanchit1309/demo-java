package pwa.pagemethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import pwa.pageobjects.HomePageObjects;
import web.pagemethods.WebBaseMethods;

public class HomePageMethods {
	private HomePageObjects homePageObjects;
	private PwaListingPageMethods pwaListingPageMethods;
	private StoryPageMethods storyPageMethods;

	private WebDriver driver;

	public HomePageMethods(WebDriver driver) {
		homePageObjects = PageFactory.initElements(driver, HomePageObjects.class);
		this.driver = driver;
		pwaListingPageMethods = new PwaListingPageMethods(driver);
		storyPageMethods = new StoryPageMethods(driver);
	}

	/**
	 * Method return top navigational section list
	 * 
	 * @return
	 */
	public List<String> getTopSectionHeaders() {
		List<String> topSectionHeaders = new ArrayList<String>();
		for (WebElement ele : homePageObjects.getHomeSubMenu()) {
			topSectionHeaders.add(ele.getText().replaceAll(".*\\n", ""));
		}
		return topSectionHeaders;
	}

	/**
	 * Method checks recency of specific tab
	 * 
	 * @param tabs
	 * @param days
	 * @return
	 */
	public List<String> getOldStories(String tabName, int days) {
		List<String> oldStoriesLi = new LinkedList<String>();
		// List<WebElement> tabList = homePageObjects.getHomeSubMenu();
		// wapListingPageMethods.navigateToTabAndClick(tabList, tabName);
		List<String> returnedLi = checkHomeTabRecency(days);
		oldStoriesLi = returnedLi != null ? returnedLi : oldStoriesLi;
		return oldStoriesLi;
	}

	/**
	 * Method to check home page articles recency
	 * 
	 * @param days
	 * @return
	 */
	public List<String> checkHomeTabRecency(int days) {
		List<String> tabRecencyList = new LinkedList<String>();
		Boolean status = false;
		try {
			// Map<String, String> headlineLinks =
			// wapListingPageMethods.getHeadlinesLink(wapListingPageMethods.getHomeHeadlines());
			List<String> headlineLinks = VerificationUtil.getLinkHrefList(pwaListingPageMethods.getHomeHeadlines());
			for (String entry : headlineLinks) {
				if (!VerificationUtil.isNews(entry))
					continue;
				status = checkRecencyArticle(entry, days);
				if (status!=null && status == false) {
					tabRecencyList.add(entry);
					Reporter.log("Old Article :: " + entry);
				}
			}
			return tabRecencyList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getArticleDate(String url) {
		String date = "";
		try {
			driver.get(url);
			date = storyPageMethods.getArticleDate();
			return date;
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * Check date of the article
	 * 
	 * @param URL
	 * @param days
	 * @return
	 */
	public Boolean checkRecencyArticle(String URL, int days) {
		Boolean flag = false;
		try {
			WebBaseMethods.navigateTimeOutHandle(URL);
			if (URL.contains("blogs.")) {
				Reporter.log("Live Blog : " + URL);
				return true;
			} else if (URL.contains("newslist")) {
				Reporter.log("News List:" + URL);
				return true;
			}
			String date = storyPageMethods.getArticleDate();
			flag = VerificationUtil.isLatest(date, days);
		} catch (Exception e) {

		}
		return flag;
	}

	/**
	 * check is Slideshows visible
	 * 
	 * @return
	 */

	public List<WebElement> getSlideShows() {
		// System.out.println(homePageObjects.getSlideshows().size());
		return homePageObjects.getSlideshows();
	}

	public boolean isMFWidgetVisible() {
		try {
			// WebBaseMethods.slowScroll(10);
			// WebBaseMethods.scrollElementIntoViewUsingJSE(homePageObjects.getMFWidget());
			WebBaseMethods.moveToElement(homePageObjects.getMFWidget());
			WebBaseMethods.slowScroll(1);
			if (homePageObjects.getMFWidget().isDisplayed()) {
				return true;
			} else
				return false;

		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public String getMfHeadingLinkHref() {
		String hrefLink = homePageObjects.getMfHeading().getAttribute("href");
		return hrefLink;
	}

	public List<String> getMfSubSectionHeaders() {
		List<String> subMenuTabs = new ArrayList<>();
		WebBaseMethods.moveToElement(homePageObjects.getMfSubSections().get(0));
		subMenuTabs = VerificationUtil.getLinkTextList(homePageObjects.getMfSubSections());
		return subMenuTabs;
	}

	public void openMfSubMenu(String tabName) {
		for (WebElement tab : homePageObjects.getMfSubSections()) {
			WebBaseMethods.moveToElement(tab);
			if (tab.getText().equalsIgnoreCase(tabName)) {
				WebBaseMethods.clickElementUsingJSE(tab);
				break;
			}
		}
	}

	public List<String> getSubMenuMfEntriesHref(String tab) {
		List<String> subMenuEntriesMfHref = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			try {
				WaitUtil.sleep(2000);
				subMenuEntriesMfHref = WebBaseMethods.getListHrefUsingJSE(homePageObjects.getCompaniesLinks(tab));
				break;
			} catch (WebDriverException e) {
				e.toString();
				Reporter.log("Trying to recover from a stale element :" + e.getMessage());
			}
		}
		System.out.println(subMenuEntriesMfHref.size() + " entries found in MF Widget " + tab + " Tab");
		return subMenuEntriesMfHref;
	}

	public String getNoResultText(String tabName) {
		WaitUtil.sleep(1000);
		String errorMsg = homePageObjects.getNoResult(tabName).getText();
		// System.out.println(errorMsg);
		return errorMsg;
	}

	public String getSectionNewsLink(String section) {
		return driver.getCurrentUrl() + "default_pwa.cms?list=" + getSectionDataId(section);
	}

	public String getSectionDataId(String section) {
		WebBaseMethods.slowScroll(20);
		moveToSection(homePageObjects.getHomeSectionGeneric(section));
		String sectionListId = homePageObjects.getSectionListDataId(section).getAttribute("data-id");
		System.out.println(sectionListId);
		return sectionListId;
	}

	/**
	 * Method to move to section on Home Page
	 * 
	 * @return
	 */

	public void moveToSection(WebElement ele) {
		try {
			// builder.moveToElement(ele).build().perform();
			WebBaseMethods.moveToElement(ele);
			WebBaseMethods.scrollElementIntoViewUsingJSE(ele);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public List<WebElement> getSectionNewsHeadlines(String section) {
		return homePageObjects.getSectionNews(section);
	}

	public List<String> getSectionTop10Href(String section) {
		openReadMoreTab(homePageObjects.getSectionReadMoreTab(section));
		List<WebElement> news = homePageObjects.getSectionAllNews(section);
		// news.addAll(homePageObjects.getSectionNews(section));
		// news.addAll(homePageObjects.getSectionMoreNews(section));
		return WebBaseMethods.getListHrefUsingJSE(news);
	}

	public void openReadMoreTab(WebElement ele) {
		WebBaseMethods.moveToElement(ele);
		WebBaseMethods.clickElementUsingJSE(ele);
	}

	public String getMoreSectionNewsHref(String section) {
		WebBaseMethods.moveToElement(homePageObjects.getGenericMoreNewsButton(section));
		return homePageObjects.getGenericMoreNewsButton(section).getAttribute("data-id");

	}

	/**
	 * check if Markets widget visible
	 * 
	 * @return
	 */

	public boolean isMarketsWidgetVisible() {
		WebBaseMethods.slowScroll(20);
		return (homePageObjects.getEtMarkets().isDisplayed());
	}

	public List<WebElement> getMarketDataBenchMark() {
		return homePageObjects.getMarketData();
	}

	public List<String> getSubSectionHeaders() {
		List<String> subMenuTabs = new ArrayList<>();
		subMenuTabs = WebBaseMethods.getListTextUsingJSE(homePageObjects.getMarketsSubMenu());
		return subMenuTabs;
	}

	public String getSensexLinkHref() {
		String href = "";
		try {
			href = homePageObjects.getSensexLink().getAttribute("href");
		} catch (NoSuchElementException e) {

		}
		return href;
	}

	public String getNiftyLinkHref() {
		String href = "";
		try {
			href = homePageObjects.getNiftyLink().getAttribute("href");
		} catch (NoSuchElementException e) {

		}
		return href;
	}

	public String getGoldLinkHref() {
		String href = "";
		try {
			href = homePageObjects.getGoldLink().getAttribute("href");
		} catch (NoSuchElementException e) {

		}
		return href;
	}

	public String getForexLinkHref() {
		String href = "";
		try {
			href = homePageObjects.getForexLink().getAttribute("href");
		} catch (NoSuchElementException e) {

		}
		return href;
	}

	public String getAllBenchMarkLinkHref() {
		String href = "";
		try {
			return homePageObjects.getAllBenchMarkLink().getAttribute("href");
		} catch (NoSuchElementException e) {

		}
		return href;
	}

	public double getSensexValue() {
		double sensex = 0.0;
		for (int i = 0; i < 4; i++) {
			try {
				String sensexStr = homePageObjects.getSensexStockChangeValue().getText();
				if (sensexStr != null && !sensexStr.isEmpty()) {
					sensex = Double.parseDouble(sensexStr);
					break;
				}

			} catch (WebDriverException e) {
				e.toString();
				Reporter.log("Trying to recover from a stale element :" + e.getMessage());
			}
		}
		return sensex;
	}

	public double getNIftyValue() {
		double nifty = 0.0;
		for (int i = 0; i < 4; i++) {
			try {
				String niftyStr = homePageObjects.getNiftyStockChangeValue().getText();
				if (niftyStr != null && !niftyStr.isEmpty()) {
					nifty = Double.parseDouble(niftyStr);
					break;
				}
			} catch (WebDriverException e) {
				e.toString();
				Reporter.log("Trying to recover from a stale element :" + e.getMessage());
			}
		}
		return nifty;
	}

	public double getGoldValue() {
		double gold = 0.0;
		for (int i = 0; i < 4; i++) {
			try {
				String goldStr = homePageObjects.getGoldStockChangeValue().getText();
				if (goldStr != null && !goldStr.isEmpty()) {
					gold = Double.parseDouble(goldStr);
					break;
				}

			} catch (WebDriverException e) {
				e.toString();
				Reporter.log("Trying to recover from a stale element :" + e.getMessage());
			}
		}
		return gold;
	}

	public double getForexValue() {
		double forex = 0.0;
		for (int i = 0; i < 4; i++) {
			try {
				String forexStr = homePageObjects.getForexValue().getText();
				if (forexStr != null && !forexStr.isEmpty()) {
					forex = Double.parseDouble(forexStr);
					break;
				}
			} catch (WebDriverException e) {
				e.toString();
				Reporter.log("Trying to recover from a stale element :" + e.getMessage());
			}
		}
		return forex;
	}

	

	public List<String> getAllBenchmarkLinksHref() {
		List<WebElement> stocks = new ArrayList<>();
		for (WebElement ele : homePageObjects.getStocksList()) {
			stocks.add(ele);
		}
		return WebBaseMethods.getListHrefUsingJSE(stocks);
	}

	public void openMarketSubMenu(String menu) {
		for (WebElement subMenu : homePageObjects.getMarketsSubMenu()) {
			// WebBaseMethods.moveToElement(subMenu);
			if (subMenu.getText().equalsIgnoreCase(menu)) {
				WebBaseMethods.clickElementUsingJSE(subMenu);
				break;
			}
		}
		WaitUtil.sleep(5000);
	}

	public List<String> getSubMenuEntriesHref() {
		List<String> subMenuEntriesHref = new ArrayList<>();
		WaitUtil.explicitWaitByVisibilityOfAllElements(driver, 20, homePageObjects.getSubMenuData());
		for (int i = 0; i < 10; i++) {
			try {
				subMenuEntriesHref = WebBaseMethods.getListHrefUsingJSE(homePageObjects.getSubMenuData());
				break;
			} catch (WebDriverException e) {
				e.toString();
				Reporter.log("Trying to recover from a stale element :" + e.getMessage());
			}
		}
		return subMenuEntriesHref;
	}

	public String getMoreDataLinkHref() {
		String moreDataHref = WebBaseMethods.getHrefUsingJSE(homePageObjects.getMoreDataLink());
		return moreDataHref;
	}

	public List<String> getIndustrySectionHeaders() {
		// moveToSection(homePageObjects.getHomeIndustrySection());
		List<String> industrySectionHeaders = new ArrayList<String>();
		for (WebElement ele : homePageObjects.getIndustrySubMenu()) {
			industrySectionHeaders.add(ele.getText());
			System.out.println(ele.getText());
		}
		return industrySectionHeaders;
	}

	public String getMoreIndustryNewsHref(WebElement e) {
		/*
		 * if (e.getText().equalsIgnoreCase("Auto")) { WaitUtil.sleep(1000);
		 * WebBaseMethods.moveToElement(homePageObjects.
		 * getIndustryMoreNewsButtonAuto()); return
		 * homePageObjects.getIndustryMoreNewsButtonAuto().getAttribute("href");
		 * } else {
		 */
		WaitUtil.sleep(1000);
		// WebBaseMethods.moveToElement(homePageObjects.getIndustryMoreNewsButton());
		// return
		// WebBaseMethods.getTextUsingJSE(homePageObjects.getIndustryMoreNewsButton());
		// // }
		String data_url = homePageObjects.getIndustryMoreNewsButton().getAttribute("data-url");
		String moreNewsLink = driver.getCurrentUrl() + data_url;
		return moreNewsLink;

	}

	/**
	 * Method to move to Industry section on Home Page
	 * 
	 * @return
	 */
	public void moveToIndustrySection(String section) {
		try {
			WebBaseMethods.slowScroll(20);
			WebBaseMethods.moveToElement(homePageObjects.getHomeSectionGeneric(section));
			// builder.moveToElement(homePageObjects.getHomeIndustrySection()).build().perform();
			if (!homePageObjects.getHomeIndustrySection().isDisplayed()) {
				System.out.println("Industry Section Not Displayed !");
			} else {
				if (homePageObjects.getHomeIndustryNews().size() <= 0) {
					System.out.println("No News in Industry Section !");
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public String getIndustryNewsLink() {
		return WebBaseMethods.getHrefUsingJSE(homePageObjects.getHomeIndustryHeading());

	}

	public List<WebElement> getIndustryTopSectionHeaders() {
		WebBaseMethods.slowScroll(20);
		moveToSection(homePageObjects.getHomeIndustrySection());
		return homePageObjects.getIndustrySubMenu();
	}

	public void openSubSection(WebElement ele) {
		try {
			WaitUtil.explicitWaitByElementToBeClickable(driver, 10, ele);
			WebBaseMethods.moveToElement(ele);

			WebBaseMethods.clickElementUsingJSE(ele);
			WaitUtil.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> getIndustryNewsHeadlines() {
		List<String> hrefList = new ArrayList<>();
		int counter = 0;
		boolean retryFlag = false;
		do {
			try {
				WaitUtil.sleep(2000);
				hrefList = WebBaseMethods.getListTextUsingJSE(homePageObjects.getIndustryNews());
				System.out.println(hrefList);
				retryFlag = false;
			} catch (WebDriverException e) {
				retryFlag = true;
				counter++;
				System.out.println("Trying to recover from a stale element :" + e.getMessage());
			}
		} while (retryFlag && counter < 5);
		return hrefList;
	}

	public List<String> getPanacheNewsHeadlines() {
		List<String> hrefList = new ArrayList<>();
		int counter = 0;
		boolean retryFlag = false;
		do {
			try {
				WaitUtil.sleep(2000);
				hrefList = WebBaseMethods.getListTextUsingJSE(homePageObjects.getPanacheNews());
				retryFlag = false;
			} catch (WebDriverException e) {
				retryFlag = true;
				counter++;
				System.out.println("Trying to recover from a stale element :" + e.getMessage());
			}
		} while (retryFlag && counter < 5);
		return hrefList;
	}

	public List<String> getIndustryTop10Href(WebElement ele) {
		List<String> news = new LinkedList<>();
		openSubSection(ele);
		try {
			WaitUtil.sleep(1000);

			WaitUtil.sleep(100);
			openReadMoreTab(homePageObjects.getReadMoreTabIndustry());
			WaitUtil.explicitWaitByVisibilityOfAllElements(driver, 5, homePageObjects.getIndustryMoreNews());
			// news.addAll(WebBaseMethods.getListHrefUsingJSE(homePageObjects.getIndustryNews()));
			news.addAll(WebBaseMethods.getListHrefUsingJSE(homePageObjects.getIndustryMoreNews()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return news;
	}

	public boolean moveToSpotlightSection() {
		boolean flag = false;
		try {
			WebBaseMethods.slowScroll(25);
			WebBaseMethods.moveToElement(homePageObjects.getSpotlightSection());
			if (!homePageObjects.getSpotlightSection().isDisplayed()) {
				System.out.println("Spotlight Section Not Displayed !");
			} else
				flag = true;
		} catch (Exception e) {
			e.getMessage();
			flag = false;
		}
		return flag;
	}

	public List<String> getSectionHeaders() {
		System.out.println(homePageObjects.getSpotlightSections().size());
		return WebBaseMethods.getListTextUsingJSE(homePageObjects.getSpotlightSections());
	}

	public List<String> getSpotlightSectionHref(int count) {
		return WebBaseMethods.getListHrefUsingJSE(getSpotlightSectionHeadlines(count));
	}

	public List<WebElement> getSpotlightSectionHeadlines(int count) {
		return homePageObjects.getSectionHeadlines(count);
	}

	public boolean isAddToScreenIconVisible() {
		boolean flag = false;
		try {
			if (homePageObjects.getAddToScreenIcon().isDisplayed()) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;

	}

	public List<WebElement> getPanacheTopSectionHeaders() {
		moveToSection(homePageObjects.getHomePanacheSection());
		return homePageObjects.getPanacheSubMenu();
	}

	public List<String> getPanacheTop10Href(WebElement ele) {
		List<String> news = new LinkedList<>();
		openSubSection(ele);
		try {
			WaitUtil.sleep(100);
			WaitUtil.sleep(100);
			openReadMoreTab(homePageObjects.getReadMoreTabPanache());
			WaitUtil.explicitWaitByVisibilityOfAllElements(driver, 5, homePageObjects.getPanacheMoreNews());
			// news.addAll(WebBaseMethods.getListHrefUsingJSE(homePageObjects.getPanacheNews()));
			news.addAll(VerificationUtil.getLinkHrefList(homePageObjects.getPanacheMoreNews()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return news;
	}

	/**
	 * Method to click Menu Icon
	 */
	public void clickMenuIcon() {
		WaitUtil.sleep(2000);
		WaitUtil.explicitWaitByElementToBeClickable(driver, 20, homePageObjects.getMenuIcon());
		WebBaseMethods.clickElementUsingJSE(homePageObjects.getMenuIcon());
	}

	public boolean isPrimeWidgetPresent() {
		boolean flag = false;
		try {
			WebBaseMethods.moveToElement(homePageObjects.getPrimeWidget());
			if (homePageObjects.getPrimeWidget().isDisplayed())
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public String getSectionHeaderHref() {
		String href = null;
		try {
			href = WebBaseMethods.getHrefUsingJSE(homePageObjects.getPrimeSectionHeader());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return href;
	}

	public List<String> getPrimeNewsListHref() {

		List<String> newsHrefList = new ArrayList<>();
		try {
			newsHrefList = VerificationUtil.getLinkHrefList(homePageObjects.getPrimeNewsList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newsHrefList;
	}

	public Map<String, String> getPrimeNews() {
		Map<String, String> newsMap = new HashMap<String, String>();
		try {
			List<WebElement> newsList = homePageObjects.getPrimeNewsList();

			for (WebElement ele : newsList) {
				newsMap.put(ele.getText(), ele.getAttribute("href"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newsMap;
	}

	public String getPrimeMoreLinkHref() {
		String href = null;

		try {
			href = homePageObjects.getPrimeMoreLink().getAttribute("href");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return href;
	}

	public boolean openTopSection(String menuName) {
		boolean flag = false;
		try {
			WebElement ele = driver
					.findElement(By.xpath("//div[@id='masthead-nav']/a[contains(text(),'" + menuName + "')]"));
			WebBaseMethods.moveToElement(ele);
			WebBaseMethods.clickElementUsingJSE(ele);
			flag = true;
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

}
