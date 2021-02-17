package com.web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewHomePageObjects {
	private WebDriver driver;

	public NewHomePageObjects(WebDriver driver) {
		this.driver = driver;

	}

	@FindBy(xpath = "//div[@id='topStories']//ul//li//a[@target='_blank' and string-length()>0]")
	private List<WebElement> topStoriesListETHomepage;

	@FindBy(xpath = "//div[@data-ga-action='Widget Prime']//a[@class='tle_wrp' and @data-conttype='100']")
	private List<WebElement> etPrimeWidgetStoriesList;

	@FindBy(xpath = "//div[@id='topStories']//ul//li//a[@target='_blank' and contains(@href,'articleshow') and not(@data-conttype)]")
	private List<WebElement> topStoriesFreeArticleListETHomepage;

	@FindBy(xpath = "//nav//div[@data-ga-action='ETPrime']//a")
	private WebElement etPrimeNavBarLink;

	@FindBy(xpath = "//section[@id='riseWidget']//div[contains(@class,'stories')]//a[string-length(text())>0]")
	private List<WebElement> riseSectionArticleshowLinks;

	@FindBy(xpath = "//section[@id='wealthWidget']//div[contains(@class,'stories')]//a[string-length(text())>0]")
	private List<WebElement> wealthSectionArticleshowLinks;

	@FindBy(xpath = "//section[@id='mfWidget']//div[contains(@class,'stories')]//a[string-length(text())>0]")
	private List<WebElement> mutualFundSectionArticleshowLinks;

	@FindBy(xpath = "//section[@id='techWidget']//div[contains(@class,'stories')]//a[string-length(text())>0]")
	private List<WebElement> informationTechnologySectionArticleshowLinks;

	@FindBy(xpath = "//section[@id='internationalWidget']//div[contains(@class,'stories')]//a[string-length(text())>0]")
	private List<WebElement> internationalSectionArticleshowLinks;

	@FindBy(xpath = "//section[@id='economyWidget']//div[contains(@class,'stories')]//a[string-length(text())>0]")
	private List<WebElement> economySectionArticleshowLinks;

	@FindBy(xpath = "//section[@id='jobsWidget']//div[contains(@class,'stories')]//a[string-length(text())>0]")
	private List<WebElement> jobsSectionArticleshowLinks;

	@FindBy(xpath = "//section[@id='panacheWidget']//div[contains(@class,'stories')]//a[string-length(text())>0]")
	private List<WebElement> panacheSectionArticleshowLinks;

	@FindBy(xpath = "//section[@id='opinionWidget']//div[contains(@class,'leftList stories')]//a[string-length(text())>0]")
	private List<WebElement> opinonSectionArticleshowLinks;

	@FindBy(xpath = "//li[@data-ga-action='Widget Top News']//li//a")
	private List<WebElement> topNewsUnderPrimeWidgetArticleshowLinks;

	@FindBy(xpath = "//li[@data-ga-action='Widget Latest News']//li//a")
	private List<WebElement> latestNewsUnderPrimeWidgetArticleshowLinks;

	@FindBy(xpath = "//section[@id='podcastWidget']//li//a")
	private List<WebElement> podcastWidgetLinks;

	@FindBy(xpath = "//ul//li//a[contains(@data-ga-onclick,'Most Read')]")
	private List<WebElement> mostReadSectionArticleshowLinks;

	@FindBy(xpath = "//ul//li//a[contains(@data-ga-onclick,'Most Shared')]")
	private List<WebElement> mostSharedSectionArticleshowLinks;

	@FindBy(xpath = "//ul//li//a[contains(@data-ga-onclick,'Most Commented')]")
	private List<WebElement> mostCommentedSectionArticleshowLinks;

	@FindBy(xpath = "//section[@id='politicsWidget']//div[contains(@class,'stories')]//a[string-length(text())>0]")
	private List<WebElement> politicsSectionArticleshowLinks;

	@FindBy(xpath = "//div[@class='interViews']//div//a[string-length(text())>0]")
	private List<WebElement> interviewSectionArticleshowLinks;

	@FindBy(xpath = "//section[@id='opinionWidget']//div[@class='expertColumn']//div//a[string-length(text())>0]")
	private List<WebElement> blogsSectionArticleshowLinks;

	@FindBy(xpath = "//ul//li[text()='Most Read']")
	private WebElement mostReadSharedCommWidget_MostReadtab;

	@FindBy(xpath = "//ul//li[text()='Most Shared']")
	private WebElement mostReadSharedCommWidget_MostSharedtab;

	@FindBy(xpath = "//ul//li[text()='Most Commented']")
	private WebElement mostReadSharedCommWidget_MostCommentedtab;

	@FindBy(xpath = "//div[(@class = 'seoWidget') and (@data-ga-action='Top Trending Terms Seo Widget')]//a")
	private List<WebElement> topTrendingTermLinks;

	@FindBy(xpath = "//li[@data-ga-onclick='Tab Latest']")
	private WebElement latestNewsTab;

	@FindBy(xpath = "//div[@data-ga-action='Markets Seo Widget']//a")
	private List<WebElement> marketsKeywordLinks;

	@FindBy(xpath = "//div[@data-ga-action='RISE Seo Widget']//a")
	private List<WebElement> riseKeywordLinks;

	@FindBy(xpath = "//div[@data-ga-action='Wealth Seo Widget']//a")
	private List<WebElement> wealthKeywordLinks;

	@FindBy(xpath = "//div[@data-ga-action='Mutual Funds Seo Widget']//a")
	private List<WebElement> mutualFundKeywordLinks;

	@FindBy(xpath = "//div[@data-ga-action='Top News Seo Widget']//a")
	private List<WebElement> topNewsKeywordsLinks;

	@FindBy(xpath = "//div[@id='mostPopular']//div[contains(@class,'list')]//a[string-length(text())>0]")
	private List<WebElement> notToBeMissedSectionLinks;

	@FindBy(xpath = "//section[@id='marketsWidget']//div[@class='expertColumn']//div//a[string-length(text())>0]")
	private List<WebElement> expertViewsSectionLinks;

	@FindBy(xpath = "//section[@id='marketsWidget']//div[contains(@class,'List stories')]/div[contains(@class,'stryList')]//a[string-length(text())>0]")
	private List<WebElement> marketsSectionArticleshowLinks;

	@FindBy(xpath = "//section[@id='et-specialWidget']//div[contains(@class,'stories')]//a[string-length(text())>0]")
	private List<WebElement> etSpecailSectionArticleshowLinks;

	@FindBy(xpath = "//section[@id='greatReads']//li//a")
	private List<WebElement> greatReadsSectionArticleshowLinks;

	@FindBy(xpath = "//section[@id='videoWidget']//li//a")
	private List<WebElement> videoSliderWidgetLinks;

	@FindBy(xpath = "//section[@id='slideWidget']//li//a")
	private List<WebElement> slideshowSliderWidgetLinks;

	@FindBy(xpath = "//section[@id='eph']//li//a[string-length(text())>0]")
	private List<WebElement> editorsPickWidgetLinks;

	@FindBy(xpath = "//section[@id='eph']//div[@class='pagination']//li[not(@class='active')]//span")
	private List<WebElement> editorsPickNonActivePagination;

	@FindBy(xpath = "//section[@id='panacheWidget']//div[@id='panacheSlider']//li//a")
	private List<WebElement> panacheSlideshowVideoshowSliderWidget;

	@FindBy(xpath = "//section[contains(@class,'newsByInd')]//ul[@class='tabs']//li")
	private List<WebElement> newsByIndustryWidgetTabs;

	@FindBy(xpath = "//section[contains(@class,'newsByInd')]//ul[@class='font_faus']//li[((@class='active') or (@style='display: list-item;')) and not((@style='display: none;'))]//h4//a")
	private List<WebElement> newsByIndustryWidgetActiveListArticleshowLinks;

	@FindBy(xpath = "//h2//a[@data-ga-onclick='Title - href']")
	private List<WebElement> sectionH2HeadingLinks;

	@FindBy(xpath = "//h4[@class='subHeading']/a")
	private List<WebElement> subsectionH4HeadingLinks;

	@FindBy(xpath = "//div[@class='moreNews flr']/a")
	private List<WebElement> sectionMoreLinks;
	
	@FindBy(xpath = "//a[contains(@href,'articleshow') and @data-conttype='100']")
	private List<WebElement> etPremiumArticleshowLinks;

	/////// **********GETTERS******//////////////////////

	
	public List<WebElement> getEditorsPickNonActivePagination() {
		return editorsPickNonActivePagination;
	}

	public List<WebElement> getNotToBeMissedSectionLinks() {
		return notToBeMissedSectionLinks;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public List<WebElement> getWealthSectionArticleshowLinks() {
		return wealthSectionArticleshowLinks;
	}

	public List<WebElement> getMutualFundSectionArticleshowLinks() {
		return mutualFundSectionArticleshowLinks;
	}

	public List<WebElement> getEconomySectionArticleshowLinks() {
		return economySectionArticleshowLinks;
	}

	public List<WebElement> getInternationalSectionArticleshowLinks() {
		return internationalSectionArticleshowLinks;
	}

	public List<WebElement> getJobsSectionArticleshowLinks() {
		return jobsSectionArticleshowLinks;
	}

	public List<WebElement> getPanacheSectionArticleshowLinks() {
		return panacheSectionArticleshowLinks;
	}

	public List<WebElement> getOpinonSectionArticleshowLinks() {
		return opinonSectionArticleshowLinks;
	}

	public List<WebElement> getTopNewsUnderPrimeWidgetArticleshowLinks() {
		return topNewsUnderPrimeWidgetArticleshowLinks;
	}

	public List<WebElement> getLatestNewsUnderPrimeWidgetArticleshowLinks() {
		return latestNewsUnderPrimeWidgetArticleshowLinks;
	}

	public List<WebElement> getPodcastWidgetLinks() {
		return podcastWidgetLinks;
	}

	public List<WebElement> getMostReadSectionArticleshowLinks() {
		return mostReadSectionArticleshowLinks;
	}

	public List<WebElement> getMostSharedSectionArticleshowLinks() {
		return mostSharedSectionArticleshowLinks;
	}

	public List<WebElement> getMostCommentedSectionArticleshowLinks() {
		return mostCommentedSectionArticleshowLinks;
	}

	public List<WebElement> getPoliticsSectionArticleshowLinks() {
		return politicsSectionArticleshowLinks;
	}

	public List<WebElement> getInterviewSectionArticleshowLinks() {
		return interviewSectionArticleshowLinks;
	}

	public List<WebElement> getBlogsSectionArticleshowLinks() {
		return blogsSectionArticleshowLinks;
	}

	public WebElement getMostReadSharedCommWidget_MostReadtab() {
		return mostReadSharedCommWidget_MostReadtab;
	}

	public WebElement getMostReadSharedCommWidget_MostSharedtab() {
		return mostReadSharedCommWidget_MostSharedtab;
	}

	public WebElement getMostReadSharedCommWidget_MostCommentedtab() {
		return mostReadSharedCommWidget_MostCommentedtab;
	}

	public List<WebElement> getTopTrendingTermLinks() {
		return topTrendingTermLinks;
	}

	public List<WebElement> getTopStoriesListETHomepage() {
		return topStoriesListETHomepage;
	}

	public List<WebElement> getEtPrimeWidgetStoriesList() {
		return etPrimeWidgetStoriesList;
	}

	public List<WebElement> getTopStoriesFreeArticleListETHomepage() {
		return topStoriesFreeArticleListETHomepage;
	}

	public WebElement getEtPrimeNavBarLink() {
		return etPrimeNavBarLink;
	}

	public List<WebElement> getRiseSectionArticleshowLinks() {
		return riseSectionArticleshowLinks;
	}

	public WebElement getLatestNewsTab() {
		return latestNewsTab;
	}

	public List<WebElement> getMarketsKeywordLinks() {
		return marketsKeywordLinks;
	}

	public List<WebElement> getRiseKeywordLinks() {
		return riseKeywordLinks;
	}

	public List<WebElement> getWealthKeywordLinks() {
		return wealthKeywordLinks;
	}

	public List<WebElement> getMutualFundKeywordLinks() {
		return mutualFundKeywordLinks;
	}

	public List<WebElement> getTopNewsKeywordsLinks() {
		return topNewsKeywordsLinks;
	}

	public List<WebElement> getInformationTechnologySectionArticleshowLinks() {
		return informationTechnologySectionArticleshowLinks;
	}

	public List<WebElement> getExpertViewsSectionLinks() {
		return expertViewsSectionLinks;
	}

	public List<WebElement> getMarketsSectionArticleshowLinks() {
		return marketsSectionArticleshowLinks;
	}

	public List<WebElement> getEtSpecailSectionArticleshowLinks() {
		return etSpecailSectionArticleshowLinks;
	}

	public List<WebElement> getGreatReadsSectionArticleshowLinks() {
		return greatReadsSectionArticleshowLinks;
	}

	public List<WebElement> getVideoSliderWidgetLinks() {
		return videoSliderWidgetLinks;
	}

	public List<WebElement> getSlideshowSliderWidgetLinks() {
		return slideshowSliderWidgetLinks;
	}

	public List<WebElement> getEditorsPickWidgetLinks() {
		return editorsPickWidgetLinks;
	}

	public List<WebElement> getPanacheSlideshowVideoshowSliderWidget() {
		return panacheSlideshowVideoshowSliderWidget;
	}

	public List<WebElement> getNewsByIndustryWidgetTabs() {
		return newsByIndustryWidgetTabs;
	}

	public List<WebElement> getNewsByIndustryWidgetActiveListArticleshowLinks() {
		return newsByIndustryWidgetActiveListArticleshowLinks;
	}

	public List<WebElement> getSectionH2HeadingLinks() {
		return sectionH2HeadingLinks;
	}

	public List<WebElement> getSubsectionH4HeadingLinks() {
		return subsectionH4HeadingLinks;
	}

	public List<WebElement> getSectionMoreLinks() {
		return sectionMoreLinks;
	}

	public List<WebElement> getEtPremiumArticleshowLinks() {
		return etPremiumArticleshowLinks;
	}

}
