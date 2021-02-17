package com.web.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.mysql.jdbc.Driver;

public class PodcastPageObjects {

	private WebDriver driver;

	public PodcastPageObjects(WebDriver driver) {
		this.driver = driver;

	}

	@FindBy(xpath = "//div[contains(@class,'eachStory')]//h2//a")
	private List<WebElement> podcastListingLinks;

	// li is used for listing
	@FindBy(xpath = "//div[contains(@class,'activeStory')]//h2//a")
	private WebElement activePcOnLi;

	@FindBy(xpath = "//div[contains(@class,'activeStory')]//a[@class='fb']")
	private WebElement fbSharingActivePcOnLi;

	@FindBy(xpath = "//a[contains(text(),' ') and (@data-lynx-mode='asynclazy')]")
	private WebElement facebookSharedLink;

	@FindBy(xpath = "//div[contains(@class,'activeStory')]//a[@class='twt']")
	private WebElement twtSharingActivePcOnLi;

	@FindBy(xpath = "//textarea[@id='status']")
	private WebElement twitterStatus;

	@FindBy(xpath = "//div[contains(@class,'activeStory')]//div[@class='spl_timeL']")
	private WebElement runTimePcListing;

	@FindBy(xpath = "//div[contains(@class,'activeStory')]//div[contains(@class,'spl_play')]")
	private WebElement playPauseBtnPcListing;

	@FindBy(xpath = "//div[contains(@class,'activeStory')]//div[contains(@class,'spl_next')]")
	private WebElement nextBtnPcListing;

	///////////////////// getters////////////////////

	public WebElement getRunTimePcListing() {
		return runTimePcListing;
	}

	public WebElement getPlayPauseBtnPcListing() {
		return playPauseBtnPcListing;
	}

	public WebElement getNextBtnPcListing() {
		return nextBtnPcListing;
	}

	public WebElement getTwitterStatus() {
		return twitterStatus;
	}

	public WebElement getTwtSharingActivePcOnLi() {
		return twtSharingActivePcOnLi;
	}

	public WebElement getFacebookSharedLink() {
		return facebookSharedLink;
	}

	public WebElement getFbSharingActivePcOnLi() {
		return fbSharingActivePcOnLi;
	}

	public WebElement getActivePcOnLi() {
		return activePcOnLi;
	}

	public List<WebElement> getPodcastListingLinks() {
		return podcastListingLinks;
	}

	public List<WebElement> getAlsoListenLinks(String msid) {
		return driver
				.findElements(By.xpath("//div[@data-msid='" + msid + "']//div[contains(@class,'more_audio')]//h3//a"));
	}

	public List<WebElement> getRelatedArticlesLinks(String msid) {
		return driver.findElements(
				By.xpath("//div[@data-msid='" + msid + "']//div[contains(@class,'related_article')]//h3//a"));

	}

	public List<WebElement> getMarketsVideosLinks(String msid) {
		return driver.findElements(
				By.xpath("//div[@data-msid='" + msid + "']//div[contains(@class,'markets_videos')]//h3//a"));

	}

	public WebElement getPcHeadingLanding(String msid) {
		return driver.findElement(By.xpath("//div[@data-msid='" + msid + "']//h1[@class='pc_heading']"));

	}

	public WebElement getFbBtnOnPcLanding(String msid) {
		return driver.findElement(By.xpath("//div[@data-msid='" + msid
				+ "']//div[@class='pc_social']//div[@class='socialLinks']//a[@class='fb']"));

	}

	public WebElement getTwtBtnOnPcLanding(String msid) {
		return driver.findElement(By.xpath("//div[@data-msid='" + msid
				+ "']//div[@class='pc_social']//div[@class='socialLinks']//a[@class='twt']"));

	}

	public WebElement getRunTimePcLanding(String msid) {
		return driver.findElement(By.xpath("//div[@data-msid='" + msid + "']//div[@class='spl_timeL']"));

	}

	public WebElement getPlayPauseBtnPcLanding(String msid) {
		return driver.findElement(By.xpath("//div[@data-msid='" + msid + "']//div[contains(@class,'spl_play')]"));

	}

	public WebElement getNextBtnPcLanding(String msid) {
		return driver.findElement(By.xpath("//div[@data-msid='" + msid + "']//div[contains(@class,'spl_next')]"));

	}

	// div[@data-msid='66585040']//div[@class='pc_social']//div[@class='socialLinks']//a[@class='in']
	// div[@data-msid='66585040']//div[@class='page_social']//div[@class='socialLinks']//a[@class='gp']

	// listing
	// div[contains(@class,'activeStory')]//a[@class='in']
	// div[contains(@class,'activeStory')]//div[@class='spl_timeL']//div

}
