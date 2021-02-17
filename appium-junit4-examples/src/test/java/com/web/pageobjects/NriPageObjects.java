package com.web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NriPageObjects {
	private WebDriver driver;

	public NriPageObjects(WebDriver driver) {
		this.driver = driver;

	}

	@FindBy(xpath = "//h2//a[contains(text(),'NRIs in News')]")
	private WebElement nrisInNewsLink;

	@FindBy(xpath = "//h2//a[contains(text(),'NRI Real Estate')]")
	private WebElement nriRealEstateLink;

	@FindBy(xpath = "//h2//a[contains(text(),'NRI Tax')]")
	private WebElement nriTaxLink;

	@FindBy(xpath = "//h2//a[contains(text(),'NRI Investments')]")
	private WebElement nriInvestmentsLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Forex and Remittance')]")
	private WebElement forexAndRemittanceLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Visa and Immigration')]")
	private WebElement visaAndImmigrationLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Working Abroad')]")
	private WebElement workingAbroadLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Returning to India')]")
	private WebElement returningToIndiaLink;

	@FindBy(xpath = "//h2//a[contains(text(),'NRI Services')]")
	private List<WebElement> nriServicesLink;

	@FindBy(xpath = "//h2//a[contains(text(),'NRIs in News')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> nrisInNewsHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'NRI Real Estate')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> nriRealEstateHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'NRI Tax')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> nriTaxHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'NRI Investments')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> nriInvestmentsHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Forex and Remittance')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> forexAndRemittanceHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Visa and Immigration')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> visaAndImmigrationHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Working Abroad')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> workingAbroadHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Returning to India')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> returningToIndiaHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'NRI Services')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> nriServicesHeadlines;

	@FindBy(xpath = "//div[@data-ga-action='Latest News Widget']//a[string-length()>0]")
	private List<WebElement> nriLatestNewsWidget;

	@FindBy(xpath = "//div[@data-ga-action='Latest TOP News Widget']//a[string-length()>0]")
	private List<WebElement> topNewsFromETWidget;

	@FindBy(xpath = "//div[@class='seoWidget']//a[string-length()>0]")
	private List<WebElement> trendingTerms;
	
	@FindBy(xpath = "//div[@data-ga-action='Top Stories']//a[string-length()>0 and contains(@href,'show')]")
private List<WebElement> topStoriesOnNriPage;
	
	

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getNrisInNewsLink() {
		return nrisInNewsLink;
	}

	public WebElement getNriRealEstateLink() {
		return nriRealEstateLink;
	}

	public WebElement getNriTaxLink() {
		return nriTaxLink;
	}

	public WebElement getNriInvestmentsLink() {
		return nriInvestmentsLink;
	}

	public WebElement getForexAndRemittanceLink() {
		return forexAndRemittanceLink;
	}

	public WebElement getVisaAndImmigrationLink() {
		return visaAndImmigrationLink;
	}

	public WebElement getWorkingAbroadLink() {
		return workingAbroadLink;
	}

	public WebElement getReturningToIndiaLink() {
		return returningToIndiaLink;
	}

	public List<WebElement> getNrisInNewsHeadlines() {
		return nrisInNewsHeadlines;
	}

	public List<WebElement> getNriRealEstateHeadlines() {
		return nriRealEstateHeadlines;
	}

	public List<WebElement> getNriTaxHeadlines() {
		return nriTaxHeadlines;
	}

	public List<WebElement> getNriInvestmentsHeadlines() {
		return nriInvestmentsHeadlines;
	}

	public List<WebElement> getForexAndRemittanceHeadlines() {
		return forexAndRemittanceHeadlines;
	}

	public List<WebElement> getVisaAndImmigrationHeadlines() {
		return visaAndImmigrationHeadlines;
	}

	public List<WebElement> getWorkingAbroadHeadlines() {
		return workingAbroadHeadlines;
	}

	public List<WebElement> getReturningToIndiaHeadlines() {
		return returningToIndiaHeadlines;
	}

	public List<WebElement> getNriServicesLink() {
		return nriServicesLink;
	}

	public List<WebElement> getNriServicesHeadlines() {
		return nriServicesHeadlines;
	}

	public List<WebElement> getNriLatestNewsWidget() {
		return nriLatestNewsWidget;
	}

	public List<WebElement> getTopNewsFromETWidget() {
		return topNewsFromETWidget;
	}

	public List<WebElement> getTrendingTerms() {
		return trendingTerms;
	}
	
	public List<WebElement> getTopStoriesOnNriPage() {
		return topStoriesOnNriPage;
	}
}
