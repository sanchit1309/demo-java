package com.web.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BudgetPageObjects {
	private WebDriver driver;

	public BudgetPageObjects(WebDriver driver) {
		this.driver = driver;

	}

	@FindBy(xpath = "//a[text()='Economy']/../following-sibling::ul/li//a[contains(text(),' ')]")
	private List<WebElement> economyHeadlines;

	@FindBy(xpath = "//a[text()='बजट']/../following-sibling::ul/li//a[contains(text(),' ')]")
	private List<WebElement> hindiBudgetHeadlines;

	@FindBy(xpath = "//a[text()='Budget Videos']/../following-sibling::ul/li//a[contains(text(),' ')]")
	private List<WebElement> budgetVideos;

	@FindBy(xpath = "//a[text()='TECH']/../following-sibling::ul/li//a[contains(text(),' ')]")
	private List<WebElement> techHeadlines;

	@FindBy(xpath = "//a[text()='Budget for SME']/../following-sibling::ul/li//a[contains(text(),' ')]")
	private List<WebElement> budgetForSMEHeadlines;

	@FindBy(xpath = "//a[text()='Slideshows']/../following-sibling::ul/li//a[contains(text(),' ')]")
	private List<WebElement> slideshowHeadlines;

	@FindBy(xpath = "//a[text()='Budget for MF']/../following-sibling::ul/li//a[contains(text(),' ')]")
	private List<WebElement> budgetForMFHeadlines;

	@FindBy(xpath = "//a[text()='Taxes & You']/../following-sibling::ul/li//a[contains(text(),' ')]")
	private List<WebElement> taxesNyouHeadlines;

	@FindBy(xpath = "//a[text()='Think Tank']/../following-sibling::ul/li//a[contains(text(),' ')]")
	private List<WebElement> thinkTankHeadlines;

	@FindBy(xpath = "//a[text()='Corner Office']/../following-sibling::ul/li//a[contains(text(),' ')]")
	private List<WebElement> cornerOfficeHeadlines;

	@FindBy(xpath = "//a[text()='Expert Views']/../following-sibling::ul/li//a[contains(text(),' ')]")
	private List<WebElement> expertViewsHeadlines;

	@FindBy(xpath = "//a[text()='News']/../following-sibling::ul/li//a[contains(text(),' ')]")
	private List<WebElement> NewsSectionHeadlines;

	@FindBy(xpath = "//a[text()='Auto']/../following-sibling::ul/li//a[contains(text(),' ')]")
	private List<WebElement> autoSectionHeadlines;

	@FindBy(xpath = "//a[text()='Auto']")
	private WebElement autoLink;

	@FindBy(xpath = "//a[contains(text(),'Goods/Svs')]/../following-sibling::ul/li//a[contains(text(),' ')]")
	private List<WebElement> goodsSvsSectionHeadlines;

	@FindBy(xpath = "//h3//a[contains(text(),'Goods/Svs')]")
	private WebElement goodsSvsLink;

	@FindBy(xpath = "//a[text()='If I were FM']/../following-sibling::ul/li//a[contains(text(),' ')]")
	private List<WebElement> ifIwereFMSectionHeadlines;

	@FindBy(xpath = "//a[text()='If I were FM']")
	private WebElement ifIwereFMLink;

	@FindBy(xpath = "//a[text()='Cons. Products']")
	private WebElement consProductsLink;

	@FindBy(xpath = "//a[text()='Cons. Products']/../following-sibling::ul/li//a[contains(text(),' ')]")
	private List<WebElement> consProductsSectionHeadlines;

	@FindBy(xpath = "//a[text()='Economic Survey']/../following-sibling::ul/li//a[contains(text(),' ')]")
	private List<WebElement> economicSurveySectionHeadlines;

	@FindBy(xpath = "//a[text()='Economic Survey']")
	private WebElement economicSurveyLink;

	@FindBy(xpath = "//a[text()='Corner Office']")
	private WebElement cornerOfficeLink;

	@FindBy(xpath = "//a[text()='Economy']")
	private WebElement economyLink;

	@FindBy(xpath = "//h3//a[text()='TECH']")
	private WebElement techLink;

	@FindBy(xpath = "//a[text()='Budget for MF']")
	private WebElement budgetForMFLink;

	@FindBy(xpath = "//a[text()='Think Tank']")
	private WebElement thinkTankLink;

	@FindBy(xpath = "//a[text()='Expert Views']")
	private WebElement expertViewsLink;

	@FindBy(xpath = "//a[text()='Taxes & You']")
	private WebElement taxesNYouLink;

	@FindBy(xpath = "//a[text()='बजट']")
	private WebElement hindiBudgetLink;

	@FindBy(xpath = "//a[text()='Budget Videos']")
	private WebElement budgetVideosLink;

	@FindBy(xpath = "//div[h3[a[text()='Slideshows']]]//h3/a")
	private WebElement slideshowsLink;

	@FindBy(xpath = "//a[text()='Budget for SME']")
	private WebElement budgetForSMELink;

	@FindBy(xpath = "//div[h3[a[text()='News']]]//h3/a")
	private WebElement newsSectionLink;

	@FindBy(xpath = "//section[contains(@class,'mt23')]//div[@class='commonBudgetSlider']//ul//li//a//div[contains(text(),' ')]")
	private List<WebElement> commonMansBudgetHeadlines;

	@FindBy(xpath = "//section[contains(@class,'mt23')]//div[@class='commonBudgetSlider']//ul//li//a")
	private List<WebElement> commonMansBudgetHeadlinesLink;

	@FindBy(xpath = "//section[contains(@class,'mt23')]//div[@class='redPagination']//ul//li[not(contains(@class,'active'))]")
	private List<WebElement> nonActiveCommonMansBudgetCarouselTab;

	@FindBy(xpath = "//section[contains(@class,'power-stocks')]//div[@class='redPagination']//ul//li[not(contains(@class,'active'))]")
	private List<WebElement> nonActivePowerStocksCarouselTab;

	@FindBy(xpath = "//section[contains(@class,'power-stocks')]//div[@class='commonBudgetSlider']//ul//li//h5//a[contains(text(),' ')]")
	private List<WebElement> powerStocksLink;

	@FindBy(xpath = "//div[contains(@class,'custom-scroll')]//li//a")
	private List<WebElement> budgetHeadlines;

	@FindBy(xpath = "//*[@id='sliderTop']//div[contains(@class,'slideBox')]//div/h3/a")
	private List<WebElement> sliderBudgetHeadlines;

	@FindBy(xpath = "//div[contains(@class,'markets-live')]/div/ul//li")
	private List<WebElement> marketsLiveHeadlines;

	@FindBy(xpath = "//div[contains(@class,'industryImpactSlider')]//ul//li/a")
	private List<WebElement> industryImpactTabs;

	@FindBy(xpath = "//ul[@class='strylist']//li[@class='active']//ul//li//h4//a")
	private List<WebElement> industryTabStories;

	@FindBy(xpath = "//li[@class='active']//div[@class='halfcol flt']//a[contains(text(),'More from')]")
	private WebElement moreFromIndustryTabLink;

	@FindBy(xpath = "//div[text()='Benchmark']")
	private WebElement benchMarkTab;

	@FindBy(xpath = "//div[text()='Gainers/Losers']")
	private WebElement gainersLosersTab;

	@FindBy(xpath = "//div[(contains(@class,'initDone'))]/div[@class='slider']//ul//li")
	private List<WebElement> marketsLiveTabs;

	@FindBy(xpath = "//div[@class='indName']")
	private WebElement industryData;

	@FindBy(xpath = "//div[contains(@style,'display: block')]//ul//li//div[@class='indices_name']")
	private List<WebElement> marketsLiveCompanies;

	@FindBy(xpath = "//h3//a[contains(text(),'Budget FAQs')]")
	private WebElement budgetFaqLink;

	@FindBy(xpath = "//div[@class='faq-tab']//label/a")
	private List<WebElement> budgetFaqArticleLinks;

	// links for checking broken URLs
	@FindBy(xpath = "//a[string-length(@href)>0]")
	private List<WebElement> allUrls;

	/////////////// GETTERS////////////////////////

	public List<WebElement> getAllUrls() {
		return allUrls;
	}

	public WebElement getBudgetFaqLink() {
		return budgetFaqLink;
	}

	public List<WebElement> getBudgetFaqArticleLinks() {
		return budgetFaqArticleLinks;
	}

	public List<WebElement> getMarketsLiveCompanies() {
		return marketsLiveCompanies;
	}

	public List<WebElement> getIndustryImpactTabs() {
		return industryImpactTabs;
	}

	public List<WebElement> getIndustryTabStories() {
		return industryTabStories;
	}

	public WebElement getMoreFromIndustryTabLink() {
		return moreFromIndustryTabLink;
	}

	public WebElement getBenchMarkTab() {
		return benchMarkTab;
	}

	public WebElement getGainersLosersTab() {
		return gainersLosersTab;
	}

	public List<WebElement> getMarketsLiveTabs() {
		return marketsLiveTabs;
	}

	public WebElement getIndustryData() {
		return industryData;
	}

	public List<WebElement> getNonActivePowerStocksCarouselTab() {
		return nonActivePowerStocksCarouselTab;
	}

	public List<WebElement> getMarketsLiveHeadlines() {
		return marketsLiveHeadlines;
	}

	public List<WebElement> getSliderBudgetHeadlines() {
		return sliderBudgetHeadlines;
	}

	public List<WebElement> getBudgetHeadlines() {
		return budgetHeadlines;
	}

	public List<WebElement> getCommonMansBudgetHeadlinesLink() {
		return commonMansBudgetHeadlinesLink;
	}

	public List<WebElement> getPowerStocksLink() {
		return powerStocksLink;
	}

	public List<WebElement> getNonActiveCommonMansBudgetCarouselTab() {
		return nonActiveCommonMansBudgetCarouselTab;
	}

	public List<WebElement> getCommonMansBudgetHeadlines() {
		return commonMansBudgetHeadlines;
	}

	public List<WebElement> getNewsSectionHeadlines() {
		return NewsSectionHeadlines;
	}

	public WebElement getCornerOfficeLink() {
		return cornerOfficeLink;
	}

	public WebElement getEconomyLink() {
		return economyLink;
	}

	public WebElement getTechLink() {
		return techLink;
	}

	public WebElement getBudgetForMFLink() {
		return budgetForMFLink;
	}

	public WebElement getThinkTankLink() {
		return thinkTankLink;
	}

	public WebElement getExpertViewsLink() {
		return expertViewsLink;
	}

	public WebElement getTaxesNYouLink() {
		return taxesNYouLink;
	}

	public WebElement getHindiBudgetLink() {
		return hindiBudgetLink;
	}

	public WebElement getBudgetVideosLink() {
		return budgetVideosLink;
	}

	public WebElement getSlideshowsLink() {
		return slideshowsLink;
	}

	public WebElement getBudgetForSMELink() {
		return budgetForSMELink;
	}

	public WebElement getNewsSectionLink() {
		return newsSectionLink;
	}

	public List<WebElement> getEconomyHeadlines() {
		return economyHeadlines;
	}

	public List<WebElement> getHindiBudgetHeadlines() {
		return hindiBudgetHeadlines;
	}

	public List<WebElement> getBudgetVideos() {
		return budgetVideos;
	}

	public List<WebElement> getTechHeadlines() {
		return techHeadlines;
	}

	public List<WebElement> getBudgetForSMEHeadlines() {
		return budgetForSMEHeadlines;
	}

	public List<WebElement> getSlideshowHeadlines() {
		return slideshowHeadlines;
	}

	public List<WebElement> getBudgetForMFHeadlines() {
		return budgetForMFHeadlines;
	}

	public List<WebElement> getTaxesNyouHeadlines() {
		return taxesNyouHeadlines;
	}

	public List<WebElement> getThinkTankHeadlines() {
		return thinkTankHeadlines;
	}

	public List<WebElement> getCornerOfficeHeadlines() {
		return cornerOfficeHeadlines;
	}

	public List<WebElement> getExpertViewsHeadlines() {
		return expertViewsHeadlines;
	}

	public WebElement getMoreFromSectionLink(String sectionName) {
		return driver.findElement(
				By.xpath("//a[contains(text(),'More from') and contains(text(),\"" + sectionName + "\")]"));
	}

	public List<WebElement> getAutoSectionHeadlines() {
		return autoSectionHeadlines;
	}

	public WebElement getAutoLink() {
		return autoLink;
	}

	public WebElement getConsProductsLink() {
		return consProductsLink;
	}

	public List<WebElement> getConsProductsSectionHeadlines() {
		return consProductsSectionHeadlines;
	}

	public List<WebElement> getEconomicSurveySectionHeadlines() {
		return economicSurveySectionHeadlines;
	}

	public WebElement getEconomicSurveyLink() {
		return economicSurveyLink;
	}

	public List<WebElement> getIfIwereFMSectionHeadlines() {
		return ifIwereFMSectionHeadlines;
	}

	public WebElement getIfIwereFMLink() {
		return ifIwereFMLink;
	}

	public List<WebElement> getGoodsSvsSectionHeadlines() {
		return goodsSvsSectionHeadlines;
	}

	public WebElement getGoodsSvsLink() {
		return goodsSvsLink;
	}

}
