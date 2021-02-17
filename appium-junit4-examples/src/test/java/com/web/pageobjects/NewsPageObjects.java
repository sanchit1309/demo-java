package com.web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewsPageObjects {
	private WebDriver driver;

	public NewsPageObjects(WebDriver driver) {
		this.driver = driver;

	}

	@FindBy(xpath = "//section[@id='pageContent']//a[text()='Company']")
	private WebElement companyLink;

	@FindBy(xpath = "//section[@id='pageContent']//h2//a[text()='Economy']")
	private WebElement economyLink;

	@FindBy(xpath = "//section[@id='pageContent']//a[text()='Politics and Nation']")
	private WebElement politicsAndNationLink;

	@FindBy(xpath = "//section[@id='pageContent']//a[text()='Brandwire']")
	private WebElement brandwireLink;

	@FindBy(xpath = "//section[@id='pageContent']//a[text()='Defence']")
	private WebElement defenceLink;

	@FindBy(xpath = "//section[@id='pageContent']//a[text()='International']")
	private WebElement internationalLink;

	@FindBy(xpath = "//section[@id='pageContent']//a[text()='India Unlimited']")
	private WebElement indiaUnlimitedLink;

	@FindBy(xpath = "//section[@id='pageContent']//a[text()='Sports']")
	private WebElement sportsLink;

	@FindBy(xpath = "//section[@id='pageContent']//a[text()='Science']")
	private WebElement scienceLink;

	@FindBy(xpath = "//section[@id='pageContent']//a[text()='Environment']")
	private WebElement enviornmentLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Science')]/../..//ul//li//a[contains(text(),' ')]")
	private List<WebElement> scienceHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Company')]/../..//ul//li//a[contains(text(),' ')]")
	private List<WebElement> companyHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Economy')]/../..//ul//li//a[contains(text(),' ')]")
	private List<WebElement> economyHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Environment')]/../..//ul//li//a[contains(text(),' ')]")
	private List<WebElement> environmentHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Sports')]/../..//ul//li//a[contains(text(),' ')]")
	private List<WebElement> sportsHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'International')]/../..//ul//li//a[contains(text(),' ')]")
	private List<WebElement> internationalHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Defence')]/../..//ul//li//a[contains(text(),' ')]")
	private List<WebElement> defenceHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Brandwire')]/../..//ul//li//a[contains(text(),' ')]")
	private List<WebElement> brandwireHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'India Unlimited')]/../..//ul//li//a[contains(text(),' ')]")
	private List<WebElement> indiaUnlimitedHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Politics and Nation')]/../..//ul//li//a[contains(text(),' ')]")
	private List<WebElement> politicsAndNationHeadlines;

	@FindBy(xpath = "//div[@class='data-cont']//p[contains(@class,'dataText')]//b")
	private List<WebElement> benchmarkValues;

	@FindBy(xpath = "//ul[@class='latestMktNews']/li//a")
	private List<WebElement> marketLatestNews;

	@FindBy(xpath = "//section[@id='featuredNews']//section//a[string-length()>0]")
	private List<WebElement> topStoriesOnNewsPaage;

	////////////////////////////////////////////////

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getCompanyLink() {
		return companyLink;
	}

	public WebElement getEconomyLink() {
		return economyLink;
	}

	public WebElement getPoliticsAndNationLink() {
		return politicsAndNationLink;
	}

	public WebElement getBrandwireLink() {
		return brandwireLink;
	}

	public WebElement getDefenceLink() {
		return defenceLink;
	}

	public WebElement getInternationalLink() {
		return internationalLink;
	}

	public WebElement getIndiaUnlimitedLink() {
		return indiaUnlimitedLink;
	}

	public WebElement getSportsLink() {
		return sportsLink;
	}

	public WebElement getScienceLink() {
		return scienceLink;
	}

	public WebElement getEnviornmentLink() {
		return enviornmentLink;
	}

	public List<WebElement> getScienceHeadlines() {
		return scienceHeadlines;
	}

	public List<WebElement> getCompanyHeadlines() {
		return companyHeadlines;
	}

	public List<WebElement> getEconomyHeadlines() {
		return economyHeadlines;
	}

	public List<WebElement> getEnvironmentHeadlines() {
		return environmentHeadlines;
	}

	public List<WebElement> getSportsHeadlines() {
		return sportsHeadlines;
	}

	public List<WebElement> getInternationalHeadlines() {
		return internationalHeadlines;
	}

	public List<WebElement> getDefenceHeadlines() {
		return defenceHeadlines;
	}

	public List<WebElement> getBrandwireHeadlines() {
		return brandwireHeadlines;
	}

	public List<WebElement> getIndiaUnlimitedHeadlines() {
		return indiaUnlimitedHeadlines;
	}

	public List<WebElement> getPoliticsAndNationHeadlines() {
		return politicsAndNationHeadlines;
	}

	public List<WebElement> getBenchmarkValues() {
		return benchmarkValues;
	}

	public List<WebElement> getMarketLatestNews() {
		return marketLatestNews;
	}

	public List<WebElement> getTopStoriesOnNewsPaage() {
		return topStoriesOnNewsPaage;
	}

}
