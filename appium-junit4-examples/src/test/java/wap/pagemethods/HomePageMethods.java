package wap.pagemethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import common.launchsetup.BaseTest;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import wap.pageobjects.HomePageObjects;
import web.pagemethods.WebBaseMethods;

/**
 * Home Page Method class for WAP
 *
 */
public class HomePageMethods {
	private HomePageObjects homePageObjects;
	private WapListingPageMethods wapListingPageMethods;
	private StoryPageMethods storyPageMethods;
	private WebDriver driver;

	public HomePageMethods(WebDriver driver) {
		homePageObjects = PageFactory.initElements(driver, HomePageObjects.class);
		this.driver = driver;
		wapListingPageMethods = new WapListingPageMethods(driver);
		storyPageMethods = new StoryPageMethods(driver);
	}

	/**
	 * Method to click Menu Icon
	 * 
	 * @return
	 */
	public boolean clickMenuIcon() {
		boolean flag = false;
		try {
			// WaitUtil.explicitWaitByElementToBeClickable(driver, 20,
			// homePageObjects.getMenuIcon());
			flag = WebBaseMethods.clickElementUsingJSE(homePageObjects.getMenuIcon());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	public boolean clickFooterMenuICon() {

		boolean flag = false;
		try {
			// WaitUtil.explicitWaitByElementToBeClickable(driver, 20,
			// homePageObjects.getMenuIcon());
			flag = WebBaseMethods.clickElementUsingJSE(homePageObjects.getFooterMenuIcon());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;

	}

	/**
	 * Method return top navigational section list
	 * 
	 * @return
	 */
	public List<String> getTopSectionHeaders() {
		List<String> topSectionHeaders = new ArrayList<String>();
		for (WebElement ele : homePageObjects.getHomeSubMenu()) {
			WebBaseMethods.scrollElementIntoViewUsingJSE(ele);
			topSectionHeaders.add(ele.getText().replaceAll(".*\\n", ""));
		}
		return topSectionHeaders;
	}

	public boolean openTopSection(String menuName) {
		boolean flag = false;
		try {
			WebBaseMethods.moveToElementAndClick(
					driver.findElement(By.xpath("//nav[@id='topNav']/a[contains(text(),'" + menuName + "')]")));
			flag = true;
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public boolean openPWATopSection(String menuName) {
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(
					driver.findElement(By.xpath("//div[@id='masthead-nav']/a[contains(text(),'" + menuName + "')]")));
			flag = true;
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public List<WebElement> getIndustryTopSectionHeaders() {
		moveToSection(homePageObjects.getHomeIndustrySection());
		return homePageObjects.getIndustrySubMenu();
	}

	public List<WebElement> getPanacheTopSectionHeaders() {
		moveToSection(homePageObjects.getHomePanacheSection());
		return homePageObjects.getPanacheSubMenu();
	}

	public WebElement moveToTab(String tabName) throws NoSuchElementException {
		for (WebElement ele : homePageObjects.getIndustrySubMenu()) {
			if (ele.getText().equalsIgnoreCase(tabName))
				return ele;
		}
		return null;
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
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * Method to move to Industry section on Home Page
	 * 
	 * @return
	 */
	public void moveToIndustrySection() {
		try {
			WebBaseMethods.moveToElement(homePageObjects.getHomeIndustrySection());
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

	/**
	 * Method to move to Panache section on Home Page
	 * 
	 * @return
	 */
	public void moveToPanacheSection() {
		try {
			WebBaseMethods.moveToElement(homePageObjects.getHomePanacheSection());
			if (!homePageObjects.getHomePanacheSection().isDisplayed()) {
				Reporter.log("Panache Section Not Displayed !");
			} else {
				if (homePageObjects.getHomeIndustryNews().size() <= 0) {
					Reporter.log("No News in Panche Section !");
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
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
		boolean status = false;
		try {
			// Map<String, String> headlineLinks =
			// wapListingPageMethods.getHeadlinesLink(wapListingPageMethods.getHomeHeadlines());
			List<String> headlineLinks = WebBaseMethods.getListHrefUsingJSE(wapListingPageMethods.getHomeHeadlines());
			for (String entry : headlineLinks) {
				if (!VerificationUtil.isNews(entry))
					continue;
				status = checkRecencyArticle(entry, days);
				if (status == false) {
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
	public boolean checkRecencyArticle(String URL, int days) {
		try {
			WebBaseMethods.navigateTimeOutHandle(driver, URL, 2);
			if (URL.contains("blogs.")) {
				Reporter.log("Live Blog : " + URL);
				return true;
			} else if (URL.contains("newslist")) {
				Reporter.log("News List:" + URL);
				return true;
			}
			String date = storyPageMethods.getArticleDate();
			return VerificationUtil.isLatest(date, days);
		} catch (Exception e) {
			return false;
		}
	}

	public String getSectionNewsLink(String section) {
		moveToSection(homePageObjects.getHomeSectionGeneric(section));
		return homePageObjects.getSectionHeading(section).getAttribute("href");
	}

	public String getIndustryNewsLink() {
		return WebBaseMethods.getHrefUsingJSE(homePageObjects.getHomeIndustryHeading());

	}

	public String getPanacheNewsLink() {
		return WebBaseMethods.getHrefUsingJSE(homePageObjects.getHomePanacheHeading());

	}

	public List<String> getIndustryNewsHeadlines() {
		List<String> hrefList = new ArrayList<>();
		int counter = 0;
		boolean retryFlag = false;
		do {
			try {
				WaitUtil.sleep(2000);
				hrefList = WebBaseMethods.getListTextUsingJSE(homePageObjects.getIndustryNews());
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

	public List<WebElement> getSectionNewsHeadlines(String section) {
		return homePageObjects.getSectionNews(section);
	}

	public void openReadMoreTab(WebElement ele) {
		WebBaseMethods.moveToElement(ele);
		WebBaseMethods.clickElementUsingJSE(ele);
	}

	public List<String> getIndustryTop10Href(WebElement ele) {
		List<String> news = new LinkedList<>();
		openSubSection(ele);
		try {
			WaitUtil.sleep(100);

			WaitUtil.sleep(100);
			openReadMoreTab(homePageObjects.getReadMoreTabIndustry());
			WaitUtil.explicitWaitByVisibilityOfAllElements(driver, 5, homePageObjects.getIndustryMoreNews());
			news.addAll(WebBaseMethods.getListHrefUsingJSE(homePageObjects.getIndustryNews()));
			news.addAll(WebBaseMethods.getListHrefUsingJSE(homePageObjects.getIndustryMoreNews()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return news;
	}

	public List<String> getPanacheTop10Href(WebElement ele) {
		List<String> news = new LinkedList<>();
		openSubSection(ele);
		try {
			WaitUtil.sleep(100);
			WaitUtil.sleep(100);
			openReadMoreTab(homePageObjects.getReadMoreTabPanache());
			WaitUtil.explicitWaitByVisibilityOfAllElements(driver, 5, homePageObjects.getPanacheMoreNews());
			news.addAll(WebBaseMethods.getListHrefUsingJSE(homePageObjects.getPanacheNews()));
			news.addAll(WebBaseMethods.getListHrefUsingJSE(homePageObjects.getPanacheMoreNews()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return news;
	}

	public List<String> getSectionTop10Href(String section) {
		List<WebElement> news = new LinkedList<>();
		openReadMoreTab(homePageObjects.getSectionReadMoreTab(section));
		news.addAll(homePageObjects.getSectionNews(section));
		news.addAll(homePageObjects.getSectionMoreNews(section));
		return WebBaseMethods.getListHrefUsingJSE(news);
	}

	public List<String> getSectionHref(String section) {
		List<WebElement> news = new LinkedList<>();
		List<String> hrefList = new LinkedList<>();
		news.addAll(homePageObjects.getSectionNewsLinks(section));
		news.forEach(action -> {
			hrefList.add(action.getAttribute("href"));
		});
		System.out.println(hrefList);
		return hrefList;
	}

	public String getMoreSectionNewsHref(String section) {
		try {
			WebBaseMethods.scrollElementIntoViewUsingJSE(homePageObjects.getGenericMoreNewsButton(section));
			// WebBaseMethods.moveToElement(homePageObjects.getGenericMoreNewsButton(section));
			return homePageObjects.getGenericMoreNewsButton(section).getAttribute("href");
		} catch (Exception ee) {
			ee.printStackTrace();
			return "";
		}
	}

	public String getMoreMarketsHref() {
		WebBaseMethods.scrollElementIntoViewUsingJSE(homePageObjects.getMarketsMoreButton());
		// WebBaseMethods.moveToElement(homePageObjects.getGenericMoreNewsButton(section));
		return homePageObjects.getMarketsMoreButton().getAttribute("href");

	}

	public List<String> getIndustrySectionHeaders() {
		moveToSection(homePageObjects.getHomeIndustrySection());
		List<String> industrySectionHeaders = new ArrayList<String>();
		for (WebElement ele : homePageObjects.getIndustrySubMenu()) {
			WebBaseMethods.scrollElementIntoViewUsingJSE(ele);
			industrySectionHeaders.add(ele.getText());
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
		WebElement ele = homePageObjects.getIndustryMoreNewsButton();
		WebBaseMethods.scrollElementIntoViewUsingJSE(ele);
		return WebBaseMethods.getTextUsingJSE(ele);
		// }
	}

	public String getMorePanacheNewsHref(WebElement e) {
		WebBaseMethods.moveToElement(homePageObjects.getPanacheMoreNewsButton());
		return homePageObjects.getPanacheMoreNewsButton().getAttribute("href");
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

	public List<String> getSectionHeaders() {
		return WebBaseMethods.getListTextUsingJSE(homePageObjects.getSpotlightSections());
	}

	public List<WebElement> getSpotlightSectionHeadlines(int count) {
		return homePageObjects.getSectionHeadlines(count);
	}

	public List<String> getSpotlightSectionHref(int count) {
		return WebBaseMethods.getListHrefUsingJSE(getSpotlightSectionHeadlines(count));
	}

	public void moveToSpotlightSection() {
		try {
			WebBaseMethods.slowScroll(15);
			WebBaseMethods.moveToElement(homePageObjects.getSpotlightSection());
			if (!homePageObjects.getSpotlightSection().isDisplayed()) {
				Reporter.log("Spotlight Section Not Displayed !");
			}
		} catch (Exception e) {
			e.getMessage();
		}

	}

	/**
	 * check if Markets widget visible
	 * 
	 * @return
	 */

	public boolean isMarketsWidgetVisible() {
		return (homePageObjects.getEtMarkets().isDisplayed());
	}

	public List<String> getAllBenchmarkLinksHref() {
		List<WebElement> stocks = new ArrayList<>();
		for (WebElement ele : homePageObjects.getStocksList()) {
			stocks.add(ele);
		}
		return WebBaseMethods.getListHrefUsingJSE(stocks);
	}

	public List<WebElement> getMarketDataBenchMark() {
		return homePageObjects.getMarketData();
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

	public List<String> getSubSectionHeaders() {
		List<String> subMenuTabs = new ArrayList<>();
		subMenuTabs = WebBaseMethods.getListTextUsingJSE(homePageObjects.getMarketsSubMenu());
		return subMenuTabs;
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

	public void switchToTab(String tabName) {

	}

	public boolean isMFWidgetVisible() {
		boolean flag = false;
		WebBaseMethods.slowScroll(10);
		for (int i = 0; i < 20; i++) {
			try {

				// WebBaseMethods.scrollElementIntoViewUsingJSE(homePageObjects.getMFWidget());
				// WebBaseMethods.moveToElement(homePageObjects.getMFWidget());
				if (homePageObjects.getMFWidget().isDisplayed()) {
					flag = true;
					break;
				}
			} catch (NoSuchElementException e) {
				WebBaseMethods.slowScroll(2);
			}
		}
		return flag;
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
			// WebBaseMethods.moveToElement(tab);
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

	public WebElement getTabSubMenuDropDown(String tabName) {
		return homePageObjects.getMFWidgetTabDropDown(tabName);
	}

	public List<String> getTabSubMenuList(String tabName) {
		List<String> subMenuList = new ArrayList<>();
		List<WebElement> li = homePageObjects.getTabSubMenuList(tabName);
		if (li.size() > 0)
			subMenuList = WebBaseMethods.getListTextUsingJSE(li);
		else
			subMenuList.add(null);
		return subMenuList;
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

	public List<String> getFirstFewHeadlines() {
		List<String> newsUrls = new ArrayList<>();
		List<WebElement> newsLink = new ArrayList<>();
		try {
			newsLink.add(homePageObjects.getBigImageNews());
			newsLink.addAll(homePageObjects.getTopNews());
			for (WebElement link : newsLink) {
				newsUrls.add(link.getAttribute("href"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(newsUrls);
		return newsUrls;
	}

	public boolean clickMenuIconReact() {
		boolean flag = false;
		try {
			// WaitUtil.explicitWaitByElementToBeClickable(driver, 20,
			// homePageObjects.getMenuIcon());
			flag = WebBaseMethods.clickElementUsingJSE(homePageObjects.getMenuIcon());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	public boolean jqueryInjForReactPages() {
		boolean flag = false;
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String jqScript = "return (typeof jQuery)";
			String type = js.executeScript(jqScript, "").toString();

			if (type.equalsIgnoreCase("undefined")) {
				String script = "var headID = document.getElementsByTagName('head')[0];"
						+ "var newScript = document.createElement(\"script\");"
						+ "newScript.type = \"text/javascript\";"
						+ "newScript.src = \"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\";"
						+ "headID.appendChild(newScript);";
				js.executeScript(script, "");
				System.out.println(" jquery loaded successfully");
				flag = true;
			}
		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return flag;
	}

	public List<String> getAllHref() {
		List<String> allHref = new ArrayList<>();
		allHref = WebBaseMethods.getListHrefUsingJSE(homePageObjects.getAllHref());

		return allHref;
	}

	public List<String> getAllPrimeShowLinks() {

		List<String> primeUrls = new LinkedList<>();
		ArrayList<String> newList = new ArrayList<String>();
		try {
			primeUrls = WebBaseMethods.getListHrefUsingJSE(homePageObjects.getAllPrimeshowLinks());
			for (String element : primeUrls) {

				if (!newList.contains(element)) {

					newList.add(element);
				}
			}

		} catch (Exception ee) {
			ee.printStackTrace();

		}
		System.out.println(newList);
		return newList;
	}

	public List<String> getMultimediaLinks() {
		List<String> li = new LinkedList<>();
		try {
			li = WebBaseMethods.getListHrefUsingJSE(homePageObjects.getMultimediaStoryLinks());
		} catch (Exception ee) {
			ee.printStackTrace();
		}

		return li;

	}

	public List<String> getPrimeLinks() {
		List<String> li = new LinkedList<>();
		try {
			li = WebBaseMethods.getListHrefUsingJSE(homePageObjects.getPrimeWidgetStoryLinks());
		} catch (Exception ee) {
			ee.printStackTrace();
		}

		return li;

	}

	public List<String> getSliderWidgetSubSecStoryLinks(String mainSection) {
		List<String> li = new LinkedList<>();
		try {
			li = WebBaseMethods.getListHrefUsingJSE(homePageObjects.getSliderWidgetSubSecStoryLinks(mainSection));
		} catch (Exception ee) {
			ee.printStackTrace();
		}

		return li;

	}

	public boolean clickSliderWidgetSubSec(String mainSection, String section) {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(homePageObjects.getSliderWidgetSubSecLink(mainSection, section));
			WaitUtil.sleep(2000);
			
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return flag;
	}

	public String getSliderWidgetSubSectionMoreLink(String mainSection) {
		try {
			return WebBaseMethods.getHrefUsingJSE(homePageObjects.getSliderWidgetSubSectionMoreLink(mainSection));
		} catch (Exception ee) {
			ee.printStackTrace();
			return "";
		}
	}

	public List<String> getPodcastStoryLinks() {
		List<String> li = new LinkedList<>();
		try {
			li = WebBaseMethods.getListHrefUsingJSE(homePageObjects.getPodcastStoryLinks());
		} catch (Exception ee) {
			ee.printStackTrace();
		}

		return li;

	}

	public List<String> getAllTopSectionStories() {
		List<String> li = new LinkedList<>();
		try {
			li = WebBaseMethods.getListHrefUsingJSE(homePageObjects.getAllTopSectionStories());
		} catch (Exception ee) {
			ee.printStackTrace();
		}

		return li;

	}

	public List<String> getSpotlightSectionsText() {
		List<String> li = new LinkedList<>();
		try {
			li = WebBaseMethods.getListTextUsingJSE(homePageObjects.getSpotlightSectionsText());
		} catch (Exception ee) {
			ee.printStackTrace();
		}

		return li;

	}

	public List<String> getSpotlightSubSectionsStoryLinks(String section) {
		List<String> li = new LinkedList<>();
		try {
			li = WebBaseMethods.getListHrefUsingJSE(homePageObjects.getSpotlightSubSectionLinks(section));
		} catch (Exception ee) {
			ee.printStackTrace();
		}

		return li;

	}

	public boolean clickMarketSubSectionTab(String section) {
		boolean flag = false;
		try {
			flag = WebBaseMethods.clickElementUsingJSE(homePageObjects.getTheMarketSubSectionTab(section));
			WaitUtil.sleep(2000);
			
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return flag;
	}

	public List<String> getMarketWidgetLinks() {
		List<String> li = new LinkedList<>();
		try {
			li = WebBaseMethods.getListHrefUsingJSE(homePageObjects.getGetMarketsWidgetLinks());
		} catch (Exception ee) {
			ee.printStackTrace();
		}

		return li;

	}

	public boolean clickHmaberMenuIcon() {
		boolean flag = false;
		try {
			// WaitUtil.explicitWaitByElementToBeClickable(driver, 20,
			// homePageObjects.getMenuIcon());
			flag = WebBaseMethods.clickElementUsingJSE(homePageObjects.getPwaHamberMenuIcon());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}
	
	public List<String> getMarketWidgetTopStoriesLinks() {
		List<String> li = new LinkedList<>();
		try {
			li = WebBaseMethods.getListHrefUsingJSE(homePageObjects.getMarketsWidgetTopStoriesLinks());
		} catch (Exception ee) {
			ee.printStackTrace();
		}

		return li;

	}

	public Map<String, String> getTopStoriesMarketStripWidgetData() {
		Map<String, String> dataMap = new HashMap<String, String>();
		try {
			List<String> hrefList = WebBaseMethods
					.getListHrefUsingJSE(homePageObjects.getMarketStripDataLinksOnMarketWidgetTopStories());
			List<String> dataList = WebBaseMethods
					.getListTextUsingJSE(homePageObjects.getMarketStripIndexDataOnMarketWidgetTopStories());
			
			int size = hrefList.size();
			for (int i = 0; i < size; i++) {
				System.out.println(hrefList.get(i) + "------" + dataList.get(i));
				dataMap.put(hrefList.get(i), dataList.get(i));
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		System.out.println(dataMap);
		return dataMap;

	}
	
	public String getStartYourFreeTrialPrimeWidgetLink(){
		String url = "";
		try{
			url = WebBaseMethods.getHrefUsingJSE(homePageObjects.getStartYourFreeTrialPrimeWidgetLink());
		}catch(Exception ee){
			ee.printStackTrace();
		}
		return url;
	}
	
	public List<String> convertAllLinksIntoAbsoluteLinks(List<String> hrefList) {
		List<String> actualList = hrefList;
		List<String> convertedList = new LinkedList<>();
		System.out.println("Actual List is: " + actualList);
		try {
			actualList.forEach(href -> {
				href = (href.contains("economictimes.") || href.contains("://")) ? href
						: (href.startsWith("/") ? BaseTest.baseUrl + href : BaseTest.baseUrl + "/" + href);
				convertedList.add(href);

			});
			System.out.println("converted List is: " + convertedList);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return convertedList;

	}
}