package com.web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class ETSharedObjects {

	@FindBy(xpath = ".//*[@id='logoLatNewsCont']//img")
	private WebElement etIcon;

	@FindBy(css = ".menuIcon.newNavSprite")
	private WebElement leftMenuIcon;

	@FindBy(className = "gcmNothx")
	private WebElement notificationNotNow;

	// @FindBy(tagName="a")
	@FindBy(xpath = "//a//div[contains(text(),'Click here to go to')]")

	private WebElement etLinkInterstitialAdvt;

	@FindAll({ @FindBy(xpath = "//a[contains(text(),'>')]"), @FindBy(xpath = "//div[contains(text(),'>')]"),
			@FindBy(xpath = "//span[contains(text(),'>')]"), @FindBy(xpath = "//p[contains(text(),'>')]") })
	private List<WebElement> brokenTags;

	@FindBy(tagName = "h1")
	private List<WebElement> h1Headlines;

	@FindBy(xpath = "//div[@class='du_consent']")
	private List<WebElement> gdprConsent;

	@FindAll({
			@FindBy(xpath = "//div[@id='mostPopular']/div[((contains(@class,'list') and not(contains(@class,'colombiaAd')) and not(contains(@class,'hidden')))or "
					+ "((contains(@class,'hidden') and contains(@style,'display')) and not(contains(@class,'colombiaAd'))))]//a[2]"),
			@FindBy(xpath = "//div[@id='mostPopular']//div[not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display'))]//h4") })
	private List<WebElement> notTobeMissedHeadlinesList;

	@FindAll({
			@FindBy(xpath = "//div[@id='mostPopular']/div[((contains(@class,'list') and not(contains(@class,'colombiaAd')) and not(contains(@class,'hidden')))or "
					+ "((contains(@class,'hidden') and contains(@style,'display')) and not(contains(@class,'colombiaAd'))))]//a[2]"),
			@FindBy(xpath = "//div[@id='mostPopular']//div[not(contains(@class,'hidden')) or (contains(@class,'hidden') and contains(@style,'display'))]//h4/..") })
	private List<WebElement> notTobeMissedHeadlinesurls;

	@FindAll({ @FindBy(xpath = "//div[contains(@class,'pollBox')]//div[@class='pTitle']"),

			@FindBy(xpath = "//div[@class='pollBox']//div[@class='title']/a") })

	private List<WebElement> pollQuestion;

	@FindBy(xpath = "//div[contains(@class,'navElement')]")
	private List<WebElement> topNav;
	
	@FindBy(xpath="//a[contains(@href,'articleshow')]")
	private List<WebElement> articleShows;
	
	@FindBy(xpath="//a[contains(@href,'slideshow')]")
	private List<WebElement> slideShows;
	
	@FindBy(xpath="//a[contains(@href,'videshow')]")
	private List<WebElement> videoShows;
	
	@FindBy(xpath="//a[contains(@href,'liveblog')]")
	private List<WebElement> liveblogs;
	
	////////////////////////////////////////////////////////

	public WebElement getEtIcon() {
		return etIcon;
	}

	public WebElement getLeftMenuIcon() {
		return leftMenuIcon;
	}

	public WebElement getNotificationNotNow() {
		return notificationNotNow;
	}

	public WebElement getEtLinkInterstitialAdvt() {
		return etLinkInterstitialAdvt;
	}

	public List<WebElement> getNotTobeMissedHeadlinesList() {
		return notTobeMissedHeadlinesList;
	}

	public List<WebElement> geth1HeadlineList() {
		return h1Headlines;
	}

	public List<WebElement> getNotTobeMissedHeadlinesurls() {
		return notTobeMissedHeadlinesurls;
	}

	public List<WebElement> getBrokenTags() {
		return brokenTags;
	}

	public List<WebElement> getPollQuestion() {

		return pollQuestion;

	}

	public List<WebElement> getGDPR() {
		return gdprConsent;
	}
	public List<WebElement> getTopNav(){
		return topNav;
	}

	public List<WebElement> getH1Headlines() {
		return h1Headlines;
	}

	public List<WebElement> getGdprConsent() {
		return gdprConsent;
	}

	public List<WebElement> getArticleShows() {
		return articleShows;
	}

	public List<WebElement> getSlideShows() {
		return slideShows;
	}

	public List<WebElement> getVideoShows() {
		return videoShows;
	}

	public List<WebElement> getLiveblogs() {
		return liveblogs;
	}
}
