package com.web.pagemethods;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.launchsetup.Config;
import common.utilities.ExcelUtil;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import web.pageobjects.HomePageObjects;

public class HomePageMethods {
	private WebDriver driver;
	private HomePageObjects homePageObjects;

	public HomePageMethods(WebDriver driver) {
		this.driver = driver;
		homePageObjects = PageFactory.initElements(driver, HomePageObjects.class);
		WaitUtil.waitForAdToDisappear(driver);
	}

	public HomePageObjects getHomePageObjects() {
		return homePageObjects;
	}

	public boolean clickFirstNews() {
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(homePageObjects.getFirstArticleLink());
			WaitUtil.sleep(5000);
			WebBaseMethods.switchChildIfPresent();
			System.out.println(driver.getCurrentUrl());
			if (driver.getCurrentUrl().contains("articleshow")) {

				flag = true;
			} else if (driver.getCurrentUrl().contains("primeshow")) {
				WebBaseMethods.switchToParentClosingChilds();
				WebBaseMethods.clickElementUsingJSE(homePageObjects.getArticleShowFromHomepage().get(0));
				WaitUtil.sleep(5000);
				WebBaseMethods.switchChildIfPresent();
				if (driver.getCurrentUrl().contains("articleshow")) {

					flag = true;
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return flag;
	}

	public List<String> getFirstFewHeadlines() {
		List<String> newsUrls = new ArrayList<>();
		List<WebElement> newsLink = new ArrayList<>();
		try {
			newsLink.add(homePageObjects.getFirstArticleLink());
			newsLink.addAll(homePageObjects.getTopNewsLHS());
			for (WebElement link : newsLink) {
				newsUrls.add(link.getAttribute("href"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newsUrls;
	}

	public boolean clickTopNewsMoreLink() {
		boolean flag = false;
		try {
			WebBaseMethods.clickElementUsingJSE(homePageObjects.getTopNewsMoreLink());
			WaitUtil.waitForLoad(driver);
			WaitUtil.sleep(2000);
			WebBaseMethods.scrollToBottom();
			flag = true;
		} catch (WebDriverException e) {

		}
		return flag;
	}

	public int getTopHeadlineDivs(String action) {
		return homePageObjects.getTopHeadlinesDivs(action).size();
	}

	public int getTopSectionsArticleList(String action) {
		return homePageObjects.getTopSectionsText(action).size();
	}

	public int checkLatestNewsListSize() {
		int latestNewsListSize = 0;
		try {
			WaitUtil.explicitWaitByVisibilityOfAllElements(driver, 20, homePageObjects.getLatestNewsList());
			latestNewsListSize = homePageObjects.getLatestNewsList().size();
		} catch (WebDriverException e) {
			e.printStackTrace();
		}
		return latestNewsListSize;
	}

	public List<String> getLatestNewsStories() {
		List<String> titles = new ArrayList<>();
		List<WebElement> li = homePageObjects.getLatestNewsList();
		titles = VerificationUtil.getLinkHrefList(li);
		return titles;
	}

	public void clickLatestNewsTab() {
		WebBaseMethods.clickElementUsingJSE(homePageObjects.getLatestNewsTab());
	}

	public void clickMoreLatestNews() {
		WebBaseMethods.clickElementUsingJSE(homePageObjects.getLatestNewsMoreLink());
		WaitUtil.waitForLoad(driver);
	}

	public int checkMoreFromLatestNewsPage() {
		WebBaseMethods.switchToChildWindow(1500);
		WaitUtil.waitForLoad(driver);
		WaitUtil.sleep(2000);
		WebBaseMethods.scrollToBottom();
		int size = homePageObjects.getMostViewedBusinessNews().size();
		WebBaseMethods.switchToParentClosingChilds();
		return size;
	}

	public void clickTopNewsSection() {
		WebBaseMethods.clickElementUsingJSE(homePageObjects.getTopNewsTab());
	}

	public int checkTopNewsSectionSize() {
		int size = 0;
		try {
			// WaitUtil.explicitWaitByVisibilityOfAllElements(driver, 20,
			// homePageObjects.getTopNewsList());
			size = homePageObjects.getTopNewsList().size();
		} catch (Exception e) {

		}
		return size;
	}

	public List<String> getTopNewsStories() {
		return VerificationUtil.getLinkHrefList(homePageObjects.getTopNewsList());
	}

	public List<Boolean> checkMoreFromTopNewsPage() {
		Map<String, String> testData = ExcelUtil.getTestDataRow("TestDataSheet", "VerifySectionTopNews", 1);
		String[] tabName = new String[] { testData.get("Text") };
		List<Boolean> flags = new ArrayList<>();
		try {
			homePageObjects.getTopNewsMoreLink().click();
			WaitUtil.waitForLoad(driver);
			for (int i = 0; i < tabName.length; i++) {
				try {
					flags.add(true);
				} catch (NoSuchElementException e) {
					flags.add(false);
				}
			}
		} finally {
			flags.add(false);
		}
		return flags;
	}

	public int getArticleCountTopShared() {
		int size = 0;
		try {
			WaitUtil.sleep(3000);
			size = homePageObjects.getTopMostShared().size();
		} catch (Exception e) {

		}
		return size;
	}

	public int getArticleCountTopCommented() {
		int size = 0;
		try {
			WaitUtil.sleep(3000);
			size = homePageObjects.getTopMostCommented().size();
		} catch (Exception e) {

		}
		return size;
	}

	public List<String> getTopSharedStories() {
		List<String> topSharedText = new LinkedList<>();
		try {
			List<WebElement> topShared = homePageObjects.getTopMostShared();
			topSharedText = VerificationUtil.getLinkHrefList(topShared);
		} catch (Exception e) {

		}
		return topSharedText;
	}

	public List<String> getTopCommentedStories() {
		List<String> topMostCommentedText = new LinkedList<>();
		try {
			List<WebElement> topCommented = homePageObjects.getTopMostCommented();
			topMostCommentedText = VerificationUtil.getLinkHrefList(topCommented);
		} catch (Exception e) {

		}
		return topMostCommentedText;
	}

	public void clickTopSharedTab() {
		WebBaseMethods.clickElementUsingJSE(homePageObjects.getMostSharedLinkHomepage());
	}

	public void clickTopCommentedTab() {
		WebBaseMethods.clickElementUsingJSE(homePageObjects.getMostCommentedLinkHomepage());
	}

	public String clickMoreTopShared() {
		String currentUrl = driver.getCurrentUrl();
		WebBaseMethods.clickElementUsingJSE(homePageObjects.getMoreMostShared());
		WaitUtil.waitforUrlToChange(driver, currentUrl, 20);
		WaitUtil.waitForLoad(driver);
		return driver.getCurrentUrl();
	}

	public String clickMoreTopCommented() {
		try {
			String currentUrl = driver.getCurrentUrl();
			WebBaseMethods.clickElementUsingJSE(homePageObjects.getMoreMostCommented());
			WaitUtil.waitforUrlToChange(driver, currentUrl, 20);
			WebBaseMethods.switchChildIfPresent();
			WaitUtil.waitForLoad(driver);
			return driver.getCurrentUrl();
		} catch (Exception e) {
			return "exception while clicking more top commented";
		}
	}

	public List<String> getArticleListMoreTopShared() {
		WaitUtil.waitForLoad(driver);
		WaitUtil.sleep(2000);
		for (int i = 0; i < 5; i++) {
			WebBaseMethods.scrollToBottom();
			WaitUtil.sleep(5000);
		}
		List<String> we = new LinkedList<>();
		try {
			we = WebBaseMethods.getListHrefUsingJSE(homePageObjects.getMostViewedBusinessNews());
		} finally {
			driver.navigate().back();
		}
		return we;
	}

	public int getMostReadArticleList() {
		List<WebElement> we = new LinkedList<>();
		try {
			WebBaseMethods.clickElementUsingJSE(homePageObjects.getMostReadTabHomePage());
			we = homePageObjects.getTopMostRead();
		} catch (Exception e) {

		}
		return we.size();
	}

	public List<String> getTopMostReadStories() {
		List<String> topMostStories = new LinkedList<>();
		try {
			List<WebElement> topRead = homePageObjects.getTopMostRead();
			topMostStories = VerificationUtil.getLinkHrefList(topRead);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return topMostStories;
	}

	public String clickMoreTopRead() {
		String currentUrl = driver.getCurrentUrl();
		WebBaseMethods.scrollElementIntoViewUsingJSE(homePageObjects.getMoreMostRead());
		WebBaseMethods.clickElementUsingJSE(homePageObjects.getMoreMostRead());
		WaitUtil.waitforUrlToChange(driver, currentUrl, 20);
		WebBaseMethods.switchChildIfPresent();
		WaitUtil.waitForLoad(driver);
		return driver.getCurrentUrl();
	}

	public List<String> getArticleCountMoreTopRead() {
		WaitUtil.waitForLoad(driver);
		WaitUtil.sleep(2000);
		for (int i = 0; i < 5; i++) {
			WebBaseMethods.scrollToBottom();
			WaitUtil.sleep(2000);
		}
		List<String> stories = new LinkedList<>();
		try {
			stories = WebBaseMethods.getListHrefUsingJSE(homePageObjects.getMostViewedBusinessNews());
		} catch (Exception e) {

		}
		WebBaseMethods.navigateBackTimeOutHandle();
		return stories;

	}

	public boolean verifyEditorPickArticles() {
		boolean flag = false;
		ArrayList<String> title = new ArrayList<>();

		for (WebElement we : homePageObjects.getEditorPickArticleTitle()) {
			String[] href = we.getAttribute("href").split("/");
			int size = href.length;
			System.out.println(href[size - 1]);
			title.add(href[size - 1]);

		}
		for (int i = 0; i < 4; i++) {

			homePageObjects.getEditorPickArticleTitle().get(i).click();
			String[] title1 = driver.getCurrentUrl().split("/");
			int size1 = title1.length;
			driver.get(Config.fetchConfigProperty("WebUrl"));
			WaitUtil.waitForLoad(driver);
			if (title1[size1 - 1].equalsIgnoreCase(title.get(i))) {
				flag = true;
			} else {
				return false;
			}

		}

		return flag;
	}

	public List<String> getStoriesEditorsPick() {
		WebElement el;
		List<WebElement> listEl = new LinkedList<>();
		ArrayList<String> titles = new ArrayList<>();
		try {
			el = homePageObjects.getEditorPickArticleHeadings().get(0);
			WebBaseMethods.scrollElementIntoViewUsingJSE(el);
			listEl = homePageObjects.getEditorPickArticleHeadings();
		} catch (Exception e) {

		}
		for (WebElement we : listEl) {
			listEl = homePageObjects.getEditorPickArticleHeadings();
			titles.add(WebBaseMethods.getTextUsingJSE(we));
		}

		return titles;
	}

	public void clickPaginationEditorsSection() {
		WebElement el;
		try {
			el = homePageObjects.getEditorSectionPagination().get(0);
			WebBaseMethods.scrollElementIntoViewUsingJSE(el);
			WebBaseMethods.clickElementUsingJSE(el);
		} catch (Exception e) {

		}
		WaitUtil.sleep(2000);
	}

	public List<String> getLeftSideStories() {
		List<String> titles = new ArrayList<>();
		List<WebElement> el = new LinkedList<WebElement>();
		try {
			el = homePageObjects.getLeftSideHeadLineList();
			System.out.println(el.size());
			if (el.size() > 0)
				WebBaseMethods.scrollElementIntoViewUsingJSE(el.get(0));
			titles = VerificationUtil.getLinkTextList(el);
		} catch (Exception e) {

		}
		return titles;
	}

	public List<String> getLeftSideStoriesHref() {
		List<String> titles = new ArrayList<>();
		List<WebElement> el = new LinkedList<WebElement>();
		try {
			el = homePageObjects.getLeftSideHeadLineList();

			titles = VerificationUtil.getLinkHrefList(el);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return titles;
	}

	public List<String> getTopFlashNews() {
		List<String> liHeadlines = new ArrayList<>();
		List<WebElement> li = new LinkedList<WebElement>();
		try {
			li = homePageObjects.getLatestNewsOnBandLink();
			li.forEach(action -> {
				liHeadlines.add(WebBaseMethods.getTextUsingJSE(action));
			});
		} catch (Exception e) {

		}

		return liHeadlines;

	}

	public List<String> getVoiceNews() {
		List<String> liHeadlines = new ArrayList<>();
		WebBaseMethods.scrollToMiddle();
		WebBaseMethods.scrollElementIntoViewUsingJSE(homePageObjects.getVoicesWidget());
		try {
			List<WebElement> li = homePageObjects.getVoicesWidgetStories();
			liHeadlines = WebBaseMethods.getListTextUsingJSE(li);
		} catch (Exception e) {

		}
		return liHeadlines;
	}

	public List<WebElement> getIndustryNewsTabList() {
		return homePageObjects.getNewsByIndusrtyTabs();
	}

	public List<String> getNewsByIndusrtyTabsLink(int tabId) {
		List<String> headlines = new LinkedList<>();
		if (homePageObjects.getNewsByIndusrtyTabsLink(tabId).size() > 0) {
			WaitUtil.explicitWaitByPresenceOfElement(driver, 20,
					homePageObjects.getNewsByIndusrtyTabsLink(tabId).get(0));
			homePageObjects.getNewsByIndusrtyTabsLink(tabId).forEach(action -> {
				headlines.add(WebBaseMethods.getTextUsingJSE(action));
			});
		} else
			headlines.add(",Tab is going blank");
		return headlines;
	}

	public List<String> getSpotLightImageList() {
		List<String> srcImages = new LinkedList<>();
		try {
			srcImages.add(homePageObjects.getSpotLightLargeImage().getAttribute("src"));
			srcImages.add(homePageObjects.getSpotLightSmallImage().getAttribute("src"));
		} catch (Exception e) {

		}
		return srcImages;
	}

	public List<String> getSpotLightAllNews() {
		List<String> hrefText = new LinkedList<>();
		try {
			hrefText = WebBaseMethods.getListHrefUsingJSE(homePageObjects.getSpotlightNews());
		} catch (Exception e) {

		}
		return hrefText;
	}

	public String getSpotLightHeadLine() {
		return WebBaseMethods.getTextUsingJSE(homePageObjects.getSpotlightDisplayedArticleHeadline());

	}

	public void clickNextSpotLight() {
		WebBaseMethods.scrollElementIntoViewUsingJSE(homePageObjects.getSpotlightSection());
		WebBaseMethods.clickElementUsingJSE(homePageObjects.getSpotlightNext());
		WaitUtil.sleep(2000);
	}

	public String getPancheHeadingLink() {
		WebElement widget = homePageObjects.getPanacheWidget();
		String headingText = "";
		try {
			By heading = WebBaseMethods.getLocatorByWebElement(homePageObjects.getWidgetHeadings().get(0));
			headingText = widget.findElement(heading).findElement(By.tagName("a")).getAttribute("href");
		} catch (Exception e) {

		}
		return headingText;
	}

	public List<String> getPanacheHeadlinesText() {
		List<String> headlineText = new LinkedList<>();
		try {
			homePageObjects.getPanacheHeadlines().forEach(action -> {
				headlineText.add(WebBaseMethods.getTextUsingJSE(action));
			});
		} catch (Exception e) {

		}
		return headlineText;
	}

	public List<String> getTopGainerList() {
		List<String> topGainers = new LinkedList<>();
		try {
			WebBaseMethods.scrollElementIntoViewUsingJSE(homePageObjects.getMarketNewsLink());
			topGainers = WebBaseMethods.getListHrefUsingJSE(homePageObjects.getTopGainersList());
		} catch (WebDriverException e) {

		}
		return topGainers;
	}

	public String getMoreMarketNewsHref() {
		String href = "";
		try {
			href = homePageObjects.getMarketMoreNews().getAttribute("href");
		} catch (NoSuchElementException e) {

		}
		return href;
	}

	public List<String> getPancheKeywordsHref() {
		List<String> href = new LinkedList<>();
		try {
			href = VerificationUtil.getLinkHrefList(homePageObjects.getPanacheTaggedKeywords());
		} catch (NoSuchElementException e) {

		}
		return href;

	}

	public List<String> getPancheSpotLightHref() {
		List<String> href = new LinkedList<>();
		try {
			href = VerificationUtil.getLinkHrefList(homePageObjects.getPanacheSpotlightStories());
		} catch (NoSuchElementException e) {

		}
		return href;

	}

	public String getMarketsNewsLink() {
		String href = "";
		try {
			href = homePageObjects.getMarketNewsLink().getAttribute("href");
		} catch (NoSuchElementException e) {

		}
		return href;
	}

	public List<WebElement> getMarketsNewsHeadlines() {
		return homePageObjects.getMarketNewsHeadlineList();
	}

	public WebElement getDateTimeTab() {

		return homePageObjects.getDateTimetab();
	}

	public List<String> getMarketsTop10Href() {
		List<String> href = new LinkedList<>();
		try {
			href = VerificationUtil.getLinkHrefList(homePageObjects.getMarketTop10());
		} catch (NoSuchElementException e) {

		}
		return href;
	}

	public String getPoliticsNationLink() {
		String href = "";
		try {
			href = homePageObjects.getPoliticsNationLink().getAttribute("href");
		} catch (NoSuchElementException e) {

		}
		return href;
	}

	public List<WebElement> getPoliticsNationHeadlines() {
		return homePageObjects.getPoliticsNationHeadlines();
	}

	public String getEconomyLink() {
		String href = "";
		try {
			href = homePageObjects.getEconomyLink().getAttribute("href");
		} catch (NoSuchElementException e) {

		}
		return href;
	}

	public List<WebElement> getEconomyHeadlines() {
		return homePageObjects.getEconomyHeadLineList();
	}

	public String getEconomyMoreHref() {
		String href = "";
		try {
			href = homePageObjects.getMoreEconomyLink().getAttribute("href");
		} catch (NoSuchElementException e) {

		}
		return href;
	}

	public String getSmallBizLink() {
		String href = "";
		try {
			href = homePageObjects.getSmallBizLink().getAttribute("href");
		} catch (NoSuchElementException e) {

		}
		return href;
	}

	public List<WebElement> getSmallBizHeadlines() {
		return homePageObjects.getSmallBizHeadlineList();
	}

	public List<WebElement> getSmallBizSpotLightHeadlines() {
		return homePageObjects.getSmallBizSpotlight();
	}

	public String getSmallBizMoreHref() {
		String href = "";
		try {
			href = homePageObjects.getSmallBizMore().getAttribute("href");
		} catch (NoSuchElementException e) {

		}
		return href;
	}

	public List<WebElement> getTopSliderLinks() {
		return homePageObjects.getTopSlider();
	}

	public String getWealthLink() {
		WebBaseMethods.scrollToMiddle();
		String href = "";
		try {
			href = homePageObjects.getWealthLink().getAttribute("href");
		} catch (Exception e) {

		}
		return href;
	}

	public String getWealthMoreHref() {
		String href = "";
		try {
			href = homePageObjects.getWealthMoreLink().getAttribute("href");
		} catch (Exception e) {

		}
		return href;
	}

	public List<WebElement> getWealthHeadlines() {
		return homePageObjects.getWealthHeadlineList();
	}

	public WebElement getMutualFundWidget() {
		return homePageObjects.getMutualFundsWidget();
	}

	public String getMutualFundsLink() {
		String href = "";
		try {
			href = homePageObjects.getMutualfundLink().getAttribute("href");
		} catch (NoSuchElementException e) {

		}
		return href;
	}

	public String getMutualFundsMoreHref() {
		String href = "";
		try {
			href = homePageObjects.getMutualfundMoreLink().getAttribute("href");
		} catch (Exception e) {

		}
		return href;
	}

	public List<WebElement> getMutualFundsHeadlines() {
		return homePageObjects.getMutualfundHeadlineList();
	}

	public boolean aroundWebIsDisplayed() {
		return homePageObjects.getFromAroundWeb().size() > 0;
	}

	public List<WebElement> aroundWebAds() {
		return homePageObjects.getFromAroundWeb().get(0).findElements(By.xpath("./..//a"));
	}

	public String getInfoTechLink() {
		String href = "";
		try {
			href = homePageObjects.getInfotechLink().getAttribute("href");
		} catch (Exception e) {

		}
		return href;
	}

	public String getInfoTechMoreHref() {
		String href = "";
		try {
			href = homePageObjects.getInfoTechMoreLink().getAttribute("href");
		} catch (Exception e) {

		}
		return href;
	}

	public List<WebElement> getInfoTechHeadlines() {
		return homePageObjects.getInfotechHeadlineList();
	}

	public String getDefenceLink() {
		WebBaseMethods.scrollToMiddle();
		WaitUtil.waitForLoad(driver);
		String href = "";
		try {
			WebBaseMethods.scrollElementIntoViewUsingJSE(homePageObjects.getDefenceLink());
			href = homePageObjects.getDefenceLink().getAttribute("href");
		} catch (Exception e) {

		}
		return href;
	}

	public String getDefenceMoreHref() {
		String href = "";
		try {
			href = WebBaseMethods.getHrefUsingJSE(homePageObjects.getDefenceMoreLink());
		} catch (Exception e) {

		}
		return href;
	}

	public List<WebElement> getDefenceHeadlines() {
		return homePageObjects.getDefenceHeadlineList();
	}

	public List<WebElement> getTopLeadStories() {
		return homePageObjects.getTopSlider();
	}

	public List<WebElement> getSlidesVideoTopLinks() {
		return homePageObjects.getSlideVideoLink();
	}

	public List<WebElement> getSlideVideoStories() {
		return homePageObjects.getSlideShowList();
	}

	public WebElement getPrevNextSlides() {
		return homePageObjects.getSlideShowPrevNext();
	}

	public WebElement getOpinionLink() {
		WebElement el = null;
		try {
			WebBaseMethods.scrollToBottom();
			WebBaseMethods.scrollElementIntoViewUsingJSE(homePageObjects.getOpinionLink());
			el = homePageObjects.getOpinionLink();
		} catch (Exception e) {

		}
		return el;
	}

	public WebElement getOpinionMoreLink() {
		return homePageObjects.getOpinionMoreLink();
	}

	public WebElement getInterviewsLink() {
		return homePageObjects.getInterviewsLink();
	}

	public WebElement getInterviewsMoreLink() {
		return homePageObjects.getInterviewsMoreLink();
	}

	public WebElement getBlogsLink() {
		return homePageObjects.getBlogsLink();
	}

	public WebElement getBlogsMoreLink() {
		return homePageObjects.getBlogsMoreLink();
	}

	public List<WebElement> getOpinionIntervBlogLinks() {
		return homePageObjects.getOpinionIntervBlogList();
	}

	public int getTopMutualFundsList() {
		WebElement el;
		try {
			el = homePageObjects.getTopMutualFundsList().get(0);
			WebBaseMethods.scrollElementIntoViewUsingJSE(el);
		} catch (IndexOutOfBoundsException | WebDriverException e) {
			return 0;
		}
		return homePageObjects.getTopMutualFundsList().size();
	}

	public List<String> getTopCommentedStoryLink() {
		return WebBaseMethods.getListHrefUsingJSE(homePageObjects.getTopMostCommented());

	}

	public String getStoryHeadline() {
		try {
			return homePageObjects.getPrimeStoryHeading().getText();
		} catch (WebDriverException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getStorySummary() {
		try {
			return homePageObjects.getPrimeStorySummary().getText();
		} catch (WebDriverException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getStoryBody() {
		try {
			return homePageObjects.getPrimeStoryBody().getText();
		} catch (WebDriverException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getAuthorName() {
		try {
			return homePageObjects.getPrimeAuthorName().getText();
		} catch (WebDriverException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getPublishDate() {
		try {
			return homePageObjects.getPrimePublishDate().getText();
		} catch (WebDriverException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getCvrImageDimensions() {
		int height = 0;
		int width = 0;
		try {
			height = homePageObjects.getPrimeCoverImage().getSize().getHeight();
			width = homePageObjects.getPrimeCoverImage().getSize().getWidth();
			System.out.println("Cover image Dimensions: " + height + "x" + width);
		} catch (Exception e) {

		}
		return height + "x" + width;

	}

	public WebElement getEtPrimeTab() {

		return homePageObjects.getEtPrimeTab();

	}

	public String getGoToPrimeLink() {
		String link = null;
		try {
			link = homePageObjects.getGoToPrimeLink().getAttribute("href");
		} catch (WebDriverException e) {

		}
		return link;
	}

	public List<WebElement> getPrimeHeadlines() {

		return homePageObjects.getPrimeHeadlines();

	}

	public String getHomepagePollQuestion() {

		return homePageObjects.getHomePagePollQuestion().getText();

	}

	public List<WebElement> getAllAssemblyElectionsNews() {

		return homePageObjects.getElectionNews();
	}

	public WebElement getMoreAssemblyElectionLink() {
		// TODO Auto-generated method stub
		return homePageObjects.getElectionsMore();
	}

	public WebElement getAssemblyElectionSectionHeading() {
		// TODO Auto-generated method stub
		return homePageObjects.getElectionsSectionHead();
	}

	public List<String> getAllHrefWithJse() {
		List<String> allHref = new ArrayList<>();
		allHref = WebBaseMethods.getListHrefUsingJSE(homePageObjects.getAllHref());

		return allHref;
	}

	public List<String> getAllHref() {
		List<String> allHref = new ArrayList<>();
		allHref = VerificationUtil.getLinkHrefList(homePageObjects.getAllHref());

		return allHref;
	}

	public List<String> getAllRelNavLinks() {
		List<String> allHref = new ArrayList<>();
		allHref = VerificationUtil.getLinkHrefList(homePageObjects.getAllRelativeNavLinks());
		// allHref =
		// WebBaseMethods.getListHrefUsingJSE(homePageObjects.getAllRelativeNavLinks());

		return allHref;
	}

	public List<String> getAllPrimeShowLinks() {

		List<String> primeUrls = new LinkedList<>();
		ArrayList<String> newList = new ArrayList<String>();
		try {
			primeUrls = WebBaseMethods.getListHrefUsingJSE(homePageObjects.getAllPrimeShowLinks());
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

	public List<String> getArticleshowLinksFromHomePage() {

		List<String> articleUrls = new LinkedList<>();
		ArrayList<String> newList = new ArrayList<String>();
		try {
			articleUrls = WebBaseMethods.getListHrefUsingJSE(homePageObjects.getAllArticleshowFromHomePage());
			for (String element : articleUrls) {

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

	public String getInternationalLink() {
		WebBaseMethods.scrollToMiddle();
		WaitUtil.waitForLoad(driver);
		String href = "";
		try {
			WebBaseMethods.scrollElementIntoViewUsingJSE(homePageObjects.getInternationalSectionLink());
			href = homePageObjects.getInternationalSectionLink().getAttribute("href");
		} catch (Exception e) {

		}
		return href;
	}

	public String getInternationalMoreHref() {
		String href = "";
		try {
			href = WebBaseMethods.getHrefUsingJSE(homePageObjects.getInternationalMoreLink());
		} catch (Exception e) {

		}
		return href;
	}

	public List<WebElement> getInternationalHeadlines() {
		return homePageObjects.getInternationalStories();
	}

	public String getJobsNCareersLink() {
		WebBaseMethods.scrollToMiddle();
		WaitUtil.waitForLoad(driver);
		String href = "";
		try {
			WebBaseMethods.scrollElementIntoViewUsingJSE(homePageObjects.getJobsNCareersSectionLink());
			href = homePageObjects.getJobsNCareersSectionLink().getAttribute("href");
		} catch (Exception e) {

		}
		return href;
	}

	public String getJobsNCareersMoreHref() {
		String href = "";
		try {
			href = WebBaseMethods.getHrefUsingJSE(homePageObjects.getJobNCareersMoreLink());
		} catch (Exception e) {

		}
		return href;
	}

	public List<WebElement> getJobsNCareersHeadlines() {
		return homePageObjects.getJobsStories();
	}

	public List<String> getAllShowHref() {
		List<String> allHref = new ArrayList<>();
		try {
			allHref = VerificationUtil.getLinkHrefList(homePageObjects.getAllShowHref());
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return allHref;
	}
	
	public int getPrimeSectionStoriesSize() {
		int size = 0;
		try {
			size = homePageObjects.getAllPrimeSectionStories().size();
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return size;
	}
	
	public boolean clickPrimeStoryOfIndex(int index) {
		boolean flag = false;
		try {
			//WebBaseMethods.clickElementUsingJSE(homePageObjects.clickPrimeStoryOfIndex(index));
			WebBaseMethods.moveToElementAndClick(homePageObjects.getPrimeStoryOfIndex(index));
			flag = true;
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
	
	public boolean isPrimeStoryOfIndexDisplayed(int index) {
		boolean flag = false;
		try {
			flag = WebBaseMethods.isDisplayed(homePageObjects.getPrimeStoryOfIndex(index), 4);
			
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return flag;
	}
}
