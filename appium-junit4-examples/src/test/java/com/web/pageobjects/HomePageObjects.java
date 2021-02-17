package com.web.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class HomePageObjects {
	private WebDriver driver;

	public HomePageObjects(WebDriver driver) {
		this.driver = driver;

	}

	@FindBy(tagName = "h2")
	private List<WebElement> widgetHeadings;

	@FindBy(css = ".allState")
	private WebElement falseLink;
	@FindBy(xpath = "//android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.TextView")
	private WebElement headlineExactPath;

	@FindBy(xpath = "//div[contains(@class,'topPic')]//strong/a")
	private WebElement firstSlideShow;

	@FindBy(xpath = "//*[@id='pageContent']//*[@class='tabsBtn']//a[text()='LATEST NEWS']")
	private WebElement latestNewsTab;

	@FindBy(xpath = "//*[@id='pageContent']//*[@class='tabsBtn']//a[text()='TOP NEWS']")
	private WebElement topNewsTab;

	@FindBy(css = ".newsTab .tabsContent li[style*='list-item'] a[href*='show']:nth-child(1),.newsTab .tabsContent li[style*='list-item'] a[href*='economictimes']:not([href*='latest']):nth-child(1)")
	private List<WebElement> latestNewsList;

	@FindBy(xpath = ".//*[@id='pageContent']//div[@class='tabsView newsTab']//p//a[contains(text(),'Latest News')]")
	private WebElement latestNewsMoreLink;

	@FindBy(xpath = ".//*[@id='pageContent']//div[@class='tabsView newsTab']//p//a[contains(text(),'Top News')]")
	private WebElement topNewsMoreLink;

	// @FindBy(xpath =
	// ".//*[@id='sideBar']//div[@class='tabsContent']//li[contains(@style,'list-item')]//p/a")
	@FindBy(xpath = ".//*[@id='sideBar']//div[@class='tabsContent']//li[2]//p/a")
	private WebElement moreMostShared;

	@FindBy(xpath = ".//*[@id='sideBar']//div[@class='tabsContent']//li[1]//p/a")
	private WebElement moreMostRead;

	@FindBy(xpath = ".//*[@id='sideBar']//div[@class='tabsContent']//li[3]//p/a")
	private WebElement moreMostCommented;

	@FindBy(xpath = "//*[@id='pageContent']/ul/li/a")
	private List<WebElement> mostViewedBusinessNews;

	@FindBy(xpath = "//div[contains(@class,'tabsBtn mostPopular')]//li[2]/a")
	private WebElement mostSharedLinkHomepage;

	@FindBy(xpath = "//div[contains(@class,'tabsBtn mostPopular')]//li[3]/a")
	private WebElement mostCommentedLinkHomepage;

	@FindBy(css = ".newsTab .tabsContent li.active a[href*='show']:not([href*='headlines']):nth-child(1),.newsTab .tabsContent li.active a[href*='economictimes']:not([href*='headlines']):nth-child(1)")
	private List<WebElement> topNewsList;

	@FindBy(xpath = "//*[@id='sideBar']//div[@class='tabsContent']//li[1]//li/a")
	private List<WebElement> topFiveMostRead;

	@FindBy(xpath = "//*[@id='sideBar']//div[@class='tabsContent']//li[2]//li/a")
	private List<WebElement> topMostShared;

	@FindBy(xpath = "//*[@id='sideBar']//div[@class='tabsContent']//li[3]//li/a")
	private List<WebElement> topMostCommented;

	@FindBy(xpath = "//*[contains(@class,'tabsBtn mostPopular')]/ul/li[1]/a")
	private WebElement mostReadTabHomePage;

	@FindBy(xpath = "//*[@id='pageContent']/ul/li[@class='loading']")
	private WebElement pageLoadElement;

	@FindBy(xpath = "//div[@id='epslideshow']//img")
	private List<WebElement> editorPickArticleImg;

	@FindBy(id = "panacheWidget")
	private WebElement panacheWidget;

	@FindBy(xpath = "//*[@id='panacheWidget']//h5/a")
	private List<WebElement> panacheHeadlines;

	@FindBy(xpath = "//section[@id='trendingNow']/a")
	private List<WebElement> panacheTaggedKeywords;

	@FindBy(id = "topGainersWidgetDiv")
	private WebElement topGainersWidget;

	@FindBy(xpath = "//li[@class='gainerli active']//a")
	private WebElement topGainersActive;

	@FindBy(xpath = "//*[@id='topGainersWidgetDiv']//li//a")
	private List<WebElement> topGainersList;

	@FindBy(id = "splightMain")
	private WebElement spotlightSection;

	@FindBy(xpath = "//div[@id='epslideshow']//span//a")
	private List<WebElement> editorPickArticleTitle;

	@FindBy(xpath = ".//*[@id='splightMain']//a[string-length(text())>0 and contains(@href,'.cms')]")
	private List<WebElement> spotlightNews;

	@FindBy(xpath = "//div[@id='splightMain']//h5")
	private List<WebElement> spotlightHeadings;

	@FindBy(xpath = ".//*[@id='splightMain']//img[@width='274']")
	private WebElement spotLightLargeImage;

	@FindBy(xpath = ".//*[@id='splightMain']//img[@width='50']")
	private WebElement spotLightSmallImage;

	@FindBy(xpath = "//div[@class='tabs']//li")
	private List<WebElement> newsByIndusrtyTabs;

	@FindBy(xpath = "//div[@id='splightMain']//div[@class='prev']")
	private WebElement spotlightPrevious;

	@FindBy(xpath = "//div[@id='splightMain']//div[@class='next']")
	private WebElement spotlightNext;

	@FindBy(xpath = "(//*[@id='splightMain']/div[1]//a)[2]")
	private WebElement spotlightDisplayedArticleHeadline;

	@FindBy(xpath = "//div[@id='epslideshow']//span/a")
	private List<WebElement> editorPickArticleHeadings;

	@FindBy(id = "eph")
	private WebElement editorPickSection;

	@FindBy(css = "#eph .pagination>ul>li:not(.active)")
	private List<WebElement> editorSectionNotActivePage;

	@FindBy(xpath = "//ul[@class='latNewsList']//li/a")
	private List<WebElement> latestNewsOnBandLink;

	@FindBy(xpath = "//section[@id='voices']//h3")
	private List<WebElement> voicesWidgetTitle;

	@FindBy(id = "voices")
	private WebElement voicesWidget;

	@FindBy(xpath = ".//*[@id='voices']/ul/li")
	private List<WebElement> voicesWidgetStories;

	@FindBy(xpath = "//a[text()='News by Industry']")
	private WebElement newsByIndusrtyWidget;

	@FindBy(css = "h1 >a")
	private WebElement firstArticleLink;

	@FindAll({ @FindBy(xpath = "//hgroup[@data-ga-action='Top Stories']//h1/a"),
			@FindBy(xpath = "//hgroup[@data-ga-action='Top Stories']//h2/a"),
			@FindBy(xpath = "//hgroup[@data-ga-action='Top Stories']//ul/li/a"),
			@FindBy(xpath = ".//hgroup[@data-ga-action='Top Stories']//h3/a[contains(@data-track,'HLHS')]") })
	private List<WebElement> leftSideHeadLineList;

	@FindBy(xpath = "//div[contains(@class,'sliderLead')]//strong/a")
	private List<WebElement> topSlider;

	@FindBy(xpath = "//*[contains(@data-sctrack,'PN')]//h2/a")
	private WebElement politicsNationLink;

	@FindAll({ @FindBy(xpath = "//*[contains(@data-sctrack,'PN')]//div//h3/a"),
			@FindBy(xpath = "//*[contains(@class,'politic_block')]/ul//a") })
	private List<WebElement> politicsNationHeadlines;

	@FindBy(xpath = "//*[contains(@data-sctrack,'ECO')]//h2/a")
	private WebElement economyLink;

	@FindAll({ @FindBy(xpath = "//*[contains(@data-sctrack,'ECO')]//div[contains(@class,'storyBlock')]//h5/a"),
			@FindBy(xpath = "//*[contains(@data-sctrack,'ECO')]//div[contains(@class,'storyBlock')]//ul//a") })
	private List<WebElement> economyHeadLineList;

	@FindBy(xpath = "//*[contains(@data-sctrack,'ECO')]//*[@class='more']//a")
	private WebElement moreEconomyLink;

	@FindBy(xpath = "//*[contains(@data-sctrack,'SB')]//h2/a")
	private WebElement smallBizLink;

	@FindBy(xpath = "//*[contains(@data-sctrack,'SB')]//div//h5/a[1]")
	private List<WebElement> smallBizHeadlineList;

	@FindBy(xpath = "//*[contains(@data-sctrack,'SB')]//*[@class='more']//a")
	private WebElement smallBizMore;

	@FindAll({ @FindBy(xpath = "//li/*/*[contains(@data-track,'SBSS')]"),
			@FindBy(xpath = "//li//li//*[contains(@data-track,'SBSS')]") })
	private List<WebElement> smallBizSpotlight;

	@FindBy(xpath = "//*[contains(@data-sctrack,'SBPREV')]")
	private WebElement smallBizNext;

	@FindBy(xpath = "//section[@data-sctrack='MA']//h2/a")
	private WebElement marketNewsLink;

	@FindBy(xpath = "//section[@data-sctrack='MA']//*[@id='marketNav']//a")
	private List<WebElement> marketTop10;

	@FindBy(xpath = "//section[@data-sctrack='MA']//div[@id='marketNav']//a")
	private WebElement marketMoreNews;

	@FindAll({ @FindBy(xpath = "//*[contains(@data-sctrack,'MA')]//div[contains(@class,'storyBlock')]//h5/a"),
			@FindBy(xpath = "//*[contains(@data-sctrack,'MA')]//div[contains(@class,'storyBlock')]//ul/li[not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display'))]/a[1]") })
	private List<WebElement> marketNewsHeadlineList;

	@FindBy(xpath = "//h2/*[contains(@data-track,'WHEAD')]")
	private WebElement wealthLink;

	// @FindAll({ @FindBy(xpath =
	// "//*[contains(@data-sctrack,'W')]//div//h5/a"),@FindBy(xpath =
	// "//h5//*[contains(@data-track,'WLS1')]") })

	@FindBy(xpath = "//*[contains(@data-sctrack,'W')]//div//h5/a")
	private List<WebElement> wealthHeadlineList;

	@FindBy(xpath = "//a[contains(@data-track,'WMOR')]")
	private WebElement wealthMoreLink;

	@FindBy(xpath = "//div[@id='topMutualFundBlock']")
	private WebElement mutualFundsWidget;

	@FindBy(xpath = "//div[@id='topMutualFundBlock']//h2/*[contains(@data-track,'MHEAD')]")
	private WebElement mutualfundLink;

	@FindAll({ @FindBy(xpath = "//*[contains(@data-sctrack,'MF')]//h5/a"),
			@FindBy(xpath = "//section[contains(@class,'mutualFunds')]//li[not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display'))]/a[contains(@data-track,'MRHS')]") })
	private List<WebElement> mutualfundHeadlineList;

	@FindBy(xpath = "//*[contains(@data-sctrack,'MF')]//*[@class='more']//a")
	private WebElement mutualfundMoreLink;

	@FindBy(xpath = "//*[@id='topWealthWidgetDiv']//li[@class='active']/a")
	private WebElement topMutualFunds;

	@FindBy(xpath = "//*[contains(@data-sctrack,'INF')]//h2/a")
	private WebElement infotechLink;

	@FindBy(xpath = "//*[contains(@data-sctrack,'INF')]//h5/a[1]")
	private List<WebElement> infotechHeadlineList;

	@FindBy(xpath = "//*[contains(@data-sctrack,'INF')]//*[@class='more']//a")
	private WebElement infoTechMoreLink;

	@FindAll({ @FindBy(xpath = "//li/*/*[contains(@data-track,'TSL')]"),
			@FindBy(xpath = "//li//li//*[contains(@data-track,'TSL')]") })
	private WebElement infoTechSpotLight;

	@FindBy(xpath = "//h4[contains(@data-track,'TSW')]")
	private List<WebElement> slideShowList;

	@FindBy(xpath = "//div[@class='sliderBox']//div[contains(@class,'prev') or contains(@class,'next') ]")
	private WebElement slideShowPrevNext;

	@FindBy(xpath = "//*[contains(@data-track,'TSWHEAD')]")
	private List<WebElement> slideVideoLink;

	@FindBy(xpath = ".//*[@id='mostPopular']/div[contains(@class,'gainerData')]//*[@class='gainerText']//a")
	private WebElement gainersWidget;

	@FindBy(xpath = "//h3/*[contains(text(),'Defence')]")
	private WebElement defenceLink;

	@FindAll({ @FindBy(xpath = "//h3/a[contains(text(),'Defence')]/../..//h5/a"),
			@FindBy(xpath = "//h3/a[contains(text(),'Defence')]/../..//li/a") })
	private List<WebElement> defenceHeadlineList;

	@FindBy(xpath = "//h3/*[contains(text(),'Defence')]/../..//p[@class='more']/a")
	private WebElement defenceMoreLink;

	@FindBy(xpath = "//h3/*[contains(text(),'International')]")
	private WebElement internationalLink;

	@FindAll({ @FindBy(xpath = "//h3/a[contains(text(),'International')]/../..//h5/a"),
			@FindBy(xpath = "//h3/a[contains(text(),'International')]/../..//li/a") })
	private List<WebElement> internationalList;

	@FindBy(xpath = "//h2/a[contains(text(),'Jobs')]")
	private WebElement jobsLink;

	@FindBy(xpath = "//*[contains(@data-sctrack,'JC')]//div[contains(@class,'storyBlock')]//a[@data-track]")
	private List<WebElement> jobsHeadlineList;

	@FindBy(xpath = "//section[@data-sctrack='JC']//p[@class='more']//a")
	private List<WebElement> jobsMore;

	@FindBy(xpath = "//*[@data-track='OPHEAD']")
	private WebElement opinionLink;

	@FindBy(xpath = "//*[@data-track='INHEAD']")
	private WebElement interviewsLink;

	@FindBy(xpath = "//*[@data-track='BHEAD']")
	private WebElement blogsLink;

	@FindAll({ @FindBy(xpath = "//*[@data-track='OPHEAD']/../..//h5/a"),
			@FindBy(xpath = "//*[@data-track='OPHEAD']/../..//li/a") })
	private List<WebElement> opinionIntervBlogList;

	@FindBy(xpath = "//*[@data-track='OMOR']")
	private WebElement opinionMoreLink;

	@FindBy(xpath = "//*[@data-track='INMOR']")
	private WebElement InterviewsMoreLink;

	@FindBy(xpath = "//*[@data-track='BMOR']")
	private WebElement blogsMoreLink;

	@FindBy(xpath = "//h3[contains(text(),'From Around the Web')]")
	private List<WebElement> fromAroundWeb;

	@FindBy(xpath = "//nav[@id='topNav']//a[text() = 'Slideshows']")
	private WebElement slideshowTab;

	// @FindBy(xpath = "//div[@id='subsec17385924']//a")
	@FindBy(xpath = "//section[contains(@class,'otherBlocks')]//h2//a")
	private List<WebElement> slideshowSections;

	@FindBy(xpath = "//div[@id='topWealthWidgetDiv']//li/a")
	private List<WebElement> topMutualFundsList;

	@FindBy(xpath = ".//div[@class='logoDate']")
	private WebElement dateTimetab;

	@FindBy(xpath = ".//*[@id='panacheWidget']/following-sibling::*[1]//a")
	private List<WebElement> panacheSpotlightStories;

	@FindBy(xpath = "//*[@class = 'leadStoryLt']/h2/a")
	private List<WebElement> topNewsLHS;

	@FindBy(xpath = "//a[@data-track='ELCHEAD']")
	private WebElement electionsSectionHead;

	@FindBy(xpath = "//section[@data-sctrack='ELC']//div[@class='storyBlock']//a[contains(@data-track,'ECOS')]")
	private List<WebElement> electionNews;

	@FindBy(xpath = "//a[@data-track='ELCMOR']")
	private WebElement electionsMore;

	@FindBy(xpath = "//ul[@class='mainrelatedstories']//li//a")
	private List<WebElement> articleShowFromHomepage;

	@FindBy(xpath = "//section[@data-ga-action='Widget Jobs']//a[contains(@data-track,'INTLS')]")
	private List<WebElement> jobsStories;

	@FindBy(xpath = "//section[@data-ga-action='Widget International']//a[contains(@data-track,'INTLS')]")
	private List<WebElement> internationalStories;
	
	

	//////////////////////////////////

	@FindBy(xpath = "//a[text()='ET PRIME']")
	private WebElement etPrimeTab;

	@FindBy(xpath = "//p[@class='primeLink']/a")
	private WebElement goToPrimeLink;

	@FindBy(xpath = "//div[contains(@class,'primeSlider')]//ul//li[contains(@class,'newslist')]//a[contains(text(),' ')]")
	private List<WebElement> primeHeadlines;

	@FindBy(xpath = "//div[@class='artcl_hdr']/h1")
	private WebElement primeStoryHeading;

	@FindBy(xpath = "//div[contains(@class,'artcl_smmry')]")
	private WebElement primeStorySummary;

	@FindBy(xpath = "//div[contains(@class,'artcl_meta')]//div[@class='author']/a")
	private WebElement primeAuthorName;

	// FindBy(xpath = "//*[@class='share']/following-sibling::*[contains(@class,
	// 'meta')]/span[1]")
	@FindBy(xpath = "//div[contains(@class,'artcl_meta')]//div[contains(@class,'meta')]/span[1]")
	private WebElement primePublishDate;

	@FindBy(xpath = "//figure[@class='artcl_cvr']/img")
	private WebElement primeCoverImage;

	@FindBy(xpath = "//*[contains(@class,'artcl_txt')]/div")
	private WebElement primeStoryBody;

	@FindBy(xpath = "//div[@class='pollBox horizontal']//div[@class='pTitle']")

	private WebElement homePagePollQuestion;

	@FindBy(xpath = "//a[string-length(@href)>0]")
	private List<WebElement> allHref;

	@FindBy(xpath = "//div[contains(@class,'relNav')]//a")
	private List<WebElement> allRelativeNavLinks;

	@FindBy(xpath = "//a[@data-conttype='100']")
	private List<WebElement> getAllPrimeShowLinks;

	@FindBy(xpath = "//a[contains(@href,'articleshow')]")
	private List<WebElement> getAllArticleshowFromHomePage;

	@FindBy(xpath = "//a[@data-track='INTHEAD' and text()='International'] ")
	private WebElement getInternationalSectionLink;

	@FindBy(xpath = "//a[@data-track='INTMOR' and text()='International News'] ")
	private WebElement getInternationalMoreLink;

	@FindBy(xpath = "//a[@data-track='INTHEAD' and text()='Jobs/Careers']")
	private WebElement getJobsNCareersSectionLink;

	@FindBy(xpath = "//a[@data-track='INTMOR' and text()='Jobs/Careers News']")
	private WebElement getJobNCareersMoreLink;
	
	@FindBy(xpath = "//a[string-length(@href)>0 and contains(@href,'show')]")
	private List<WebElement> allShowHref;
	
	@FindBy(xpath = "//*[@data-ga-action='Widget Prime']//li//a[@data-conttype='100' and string-length()>0]")
	private List<WebElement> allPrimeSectionStories;

	//////////////////////////////////

	public List<WebElement> getGetAllPrimeShowLinks() {
		return getAllPrimeShowLinks;
	}

	public List<WebElement> getAllArticleshowFromHomePage() {
		return getAllArticleshowFromHomePage;
	}

	public WebElement getDateTimetab() {
		return dateTimetab;
	}

	public List<WebElement> getTopMutualFundsList() {
		return topMutualFundsList;
	}

	public WebElement getFalseLink() {
		return falseLink;
	}

	public WebElement getFirstSlideShow() {
		return firstSlideShow;
	}

	public WebElement getLatestNewsTab() {
		return latestNewsTab;
	}

	public List<WebElement> getLatestNewsList() {
		return latestNewsList;
	}

	public List<WebElement> getMostViewedBusinessNews() {
		return mostViewedBusinessNews;
	}

	public List<WebElement> getTopNewsList() {
		return topNewsList;
	}

	public List<WebElement> getTopHeadlinesDivs(String tabName) {
		return driver.findElements(By.xpath("//h2/*[contains(text(),'" + tabName + "')]"));
	}

	public List<WebElement> getTopSectionsText(String tabName) {
		String tempLocator = "";
		if (tabName.equals("Top News Headline"))
			tempLocator = ".//*[@id='pageContent']/ul[@class='headlineData']//a";
		else
			tempLocator = "//h2/*[text()='" + tabName + "']/../following-sibling::*//a[contains(@href,'/"
					+ tabName.toLowerCase().replaceAll("\\s", "-") + "/')]";
		return driver.findElements(By.xpath(tempLocator));
	}

	public WebElement getMostSharedLinkHomepage() {
		return mostSharedLinkHomepage;
	}

	public WebElement getMostCommentedLinkHomepage() {
		return mostCommentedLinkHomepage;
	}

	public List<WebElement> getTopMostShared() {
		return topMostShared;
	}

	public WebElement getMostReadTabHomePage() {
		return mostReadTabHomePage;
	}

	public List<WebElement> getTopMostRead() {
		return topFiveMostRead;
	}

	public List<WebElement> getTopMostCommented() {
		return topMostCommented;
	}

	public WebElement getPageLoadElement() {
		return pageLoadElement;
	}

	public WebElement getFirstArticleLink() {
		return firstArticleLink;
	}

	public List<WebElement> getEditorPickArticleImg() {
		return editorPickArticleImg;
	}

	public List<WebElement> getEditorPickArticleTitle() {
		return editorPickArticleTitle;
	}

	public List<WebElement> getEditorPickArticleHeadings() {
		return editorPickArticleHeadings;
	}

	public WebElement getEditorPickSection() {
		return editorPickSection;
	}

	public List<WebElement> getLatestNewsOnBandLink() {
		return latestNewsOnBandLink;
	}

	public WebElement getVoicesWidget() {
		return voicesWidget;
	}

	public List<WebElement> getVoicesWidgetTitle() {
		return voicesWidgetTitle;
	}

	public List<WebElement> getVoicesWidgetStories() {
		return voicesWidgetStories;
	}

	public List<WebElement> getNewsByIndusrtyTabs() {
		return newsByIndusrtyTabs;
	}

	public WebElement getNewsByIndusrtyWidget() {
		return newsByIndusrtyWidget;
	}

	public List<WebElement> getNewsByIndusrtyTabsLink(int tabId) {
		return driver.findElements(By
				.xpath(".//section[contains(@class,'newsByInd')]//div[contains(@class,'tabsView')]//div[@class='content']//li["
						+ tabId + "]/h4//a"));
	}

	public WebElement getSpotlightNext() {
		return spotlightNext;
	}

	public WebElement getSpotlightPrevious() {
		return spotlightPrevious;
	}

	public List<WebElement> getSpotlightHeadings() {
		return spotlightHeadings;
	}

	public WebElement getSpotlightSection() {
		return spotlightSection;
	}

	public WebElement topGainersActive() {
		return topGainersActive;
	}

	public List<WebElement> getTopGainersList() {
		return topGainersList;
	}

	public WebElement getTopGainersWidget() {
		return topGainersWidget;
	}

	public List<WebElement> getPanacheTaggedKeywords() {
		return panacheTaggedKeywords;
	}

	public WebElement getLatestNewsMoreLink() {
		return latestNewsMoreLink;
	}

	public WebElement getTopNewsMoreLink() {
		return topNewsMoreLink;
	}

	public List<WebElement> getTopFiveMostRead() {
		return topFiveMostRead;
	}

	public WebElement getMoreMostShared() {
		return moreMostShared;
	}

	public WebElement getMoreMostRead() {
		return moreMostRead;
	}

	public WebElement getMoreMostCommented() {
		return moreMostCommented;
	}

	public WebElement getTopNewsTab() {
		return topNewsTab;
	}

	public List<WebElement> getLeftSideHeadLineList() {
		return leftSideHeadLineList;
	}

	public WebElement getSpotLightLargeImage() {
		return spotLightLargeImage;
	}

	public WebElement getSpotLightSmallImage() {
		return spotLightSmallImage;
	}

	public WebElement getSpotlightDisplayedArticleHeadline() {
		return spotlightDisplayedArticleHeadline;
	}

	public WebElement getPanacheWidget() {
		return panacheWidget;
	}

	public List<WebElement> getWidgetHeadings() {
		return widgetHeadings;
	}

	public List<WebElement> getPanacheHeadlines() {
		return panacheHeadlines;
	}

	public List<WebElement> getEditorSectionPagination() {
		return editorSectionNotActivePage;
	}

	public List<WebElement> getEditorSectionNotActivePage() {
		return editorSectionNotActivePage;
	}

	public List<WebElement> getPoliticsNationHeadlines() {
		return politicsNationHeadlines;
	}

	public WebElement getPoliticsNationLink() {
		return politicsNationLink;
	}

	public WebElement getEconomyLink() {
		return economyLink;
	}

	public List<WebElement> getEconomyHeadLineList() {
		return economyHeadLineList;
	}

	public WebElement getMoreEconomyLink() {
		return moreEconomyLink;
	}

	public WebElement getSmallBizLink() {
		return smallBizLink;
	}

	public List<WebElement> getSmallBizHeadlineList() {
		return smallBizHeadlineList;
	}

	public WebElement getSmallBizMore() {
		return smallBizMore;
	}

	public List<WebElement> getSmallBizSpotlight() {
		return smallBizSpotlight;
	}

	public WebElement getSmallBizNext() {
		return smallBizNext;
	}

	public WebElement getMarketNewsLink() {
		return marketNewsLink;
	}

	public List<WebElement> getMarketNewsHeadlineList() {
		return marketNewsHeadlineList;
	}

	public WebElement getWealthLink() {
		return wealthLink;
	}

	public List<WebElement> getWealthHeadlineList() {
		return wealthHeadlineList;
	}

	public WebElement getWealthMoreLink() {
		return wealthMoreLink;
	}

	public WebElement getMutualfundLink() {
		return mutualfundLink;
	}

	public List<WebElement> getMutualfundHeadlineList() {
		return mutualfundHeadlineList;
	}

	public WebElement getMutualfundMoreLink() {
		return mutualfundMoreLink;
	}

	public WebElement getTopMutualFunds() {
		return topMutualFunds;
	}

	public WebElement getInfotechLink() {
		return infotechLink;
	}

	public List<WebElement> getInfotechHeadlineList() {
		return infotechHeadlineList;
	}

	public WebElement getInfoTechMoreLink() {
		return infoTechMoreLink;
	}

	public WebElement getInfoTechSpotLight() {
		return infoTechSpotLight;
	}

	public List<WebElement> getSlideShowList() {
		return slideShowList;
	}

	public WebElement getSlideShowPrevNext() {
		return slideShowPrevNext;
	}

	public List<WebElement> getSlideVideoLink() {
		return slideVideoLink;
	}

	public WebElement getGainersWidget() {
		return gainersWidget;
	}

	public WebElement getDefenceLink() {
		return defenceLink;
	}

	public List<WebElement> getDefenceHeadlineList() {
		return defenceHeadlineList;
	}

	public WebElement getInternationalLink() {
		return internationalLink;
	}

	public List<WebElement> getInternationalList() {
		return internationalList;
	}

	public WebElement getJobsLink() {
		return jobsLink;
	}

	public List<WebElement> getJobsHeadlineList() {
		return jobsHeadlineList;
	}

	public List<WebElement> getJobsMore() {
		return jobsMore;
	}

	public WebElement getOpinionLink() {
		return opinionLink;
	}

	public List<WebElement> getOpinionIntervBlogList() {
		return opinionIntervBlogList;
	}

	public WebElement getOpinionMoreLink() {
		return opinionMoreLink;
	}

	public WebElement getInterviewsMoreLink() {
		return InterviewsMoreLink;
	}

	public WebElement getBlogsMoreLink() {
		return blogsMoreLink;
	}

	public List<WebElement> getFromAroundWeb() {
		return fromAroundWeb;
	}

	public List<WebElement> getMarketTop10() {
		return marketTop10;
	}

	public WebElement getTopGainersActive() {
		return topGainersActive;
	}

	public WebElement getMarketMoreNews() {
		return marketMoreNews;
	}

	public List<WebElement> getTopSlider() {
		return topSlider;
	}

	public WebElement getDefenceMoreLink() {
		return defenceMoreLink;
	}

	public WebElement getInterviewsLink() {
		return interviewsLink;
	}

	public WebElement getBlogsLink() {
		return blogsLink;
	}

	public WebElement getSlideshowTab() {
		return slideshowTab;
	}

	public List<WebElement> getSlideshowSections() {
		return slideshowSections;
	}

	public List<WebElement> getSpotlightNews() {
		return spotlightNews;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getHeadlineExactPath() {
		return headlineExactPath;
	}

	public WebElement getMutualFundsWidget() {
		return mutualFundsWidget;
	}

	public List<WebElement> getPanacheSpotlightStories() {
		return panacheSpotlightStories;
	}

	public List<WebElement> getTopNewsLHS() {
		return topNewsLHS;
	}

	public WebElement getPrimeStoryHeading() {
		return primeStoryHeading;
	}

	public WebElement getPrimeStorySummary() {
		return primeStorySummary;
	}

	public WebElement getPrimeAuthorName() {
		return primeAuthorName;
	}

	public WebElement getPrimePublishDate() {
		return primePublishDate;
	}

	public WebElement getPrimeCoverImage() {
		return primeCoverImage;
	}

	public WebElement getPrimeStoryBody() {
		return primeStoryBody;
	}

	public List<WebElement> getPrimeHeadlines() {
		return primeHeadlines;
	}

	public WebElement getGoToPrimeLink() {
		return goToPrimeLink;
	}

	public WebElement getEtPrimeTab() {
		return etPrimeTab;
	}

	public WebElement getHomePagePollQuestion() {

		return homePagePollQuestion;

	}

	public WebElement getElectionsSectionHead() {
		return electionsSectionHead;
	}

	public List<WebElement> getElectionNews() {
		return electionNews;
	}

	public WebElement getElectionsMore() {
		return electionsMore;
	}

	public List<WebElement> getArticleShowFromHomepage() {
		return articleShowFromHomepage;
	}

	public List<WebElement> getAllHref() {
		return allHref;
	}

	public List<WebElement> getAllRelativeNavLinks() {
		return allRelativeNavLinks;
	}

	public List<WebElement> getAllPrimeShowLinks() {
		return getAllPrimeShowLinks;
	}

	public WebElement getInternationalSectionLink() {
		return getInternationalSectionLink;
	}

	public WebElement getInternationalMoreLink() {
		return getInternationalMoreLink;
	}

	public WebElement getJobsNCareersSectionLink() {
		return getJobsNCareersSectionLink;
	}

	public WebElement getJobNCareersMoreLink() {
		return getJobNCareersMoreLink;
	}

	public List<WebElement> getJobsStories() {
		return jobsStories;
	}

	public List<WebElement> getInternationalStories() {
		return internationalStories;
	}
	
	public List<WebElement> getAllShowHref() {
		return allShowHref;
	}
	
	public List<WebElement> getAllPrimeSectionStories() {
		return allPrimeSectionStories;
	}
	
	public WebElement getPrimeStoryOfIndex(int index) {
		return driver.findElement(By.xpath("(//*[@data-ga-action='Widget Prime']//li//a[@data-conttype='100' and string-length()>0])["+index+"]"));
	}


}
