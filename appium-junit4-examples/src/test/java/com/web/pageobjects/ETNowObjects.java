package com.web.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ETNowObjects {
	private WebDriver driver;

	public ETNowObjects(WebDriver driver) {
		this.driver = driver;

	}

	@FindBy(id = "myMovie")
	private WebElement videoIFrame;

	@FindBy(xpath = "//iframe[contains(@src,'videoplayer')]")
	private WebElement slikePlayer;

	@FindBy(tagName = "video")
	private WebElement tvWindow;

	@FindBy(xpath = "//div[@text='Skip Ad']")
	private WebElement skipAd;

	@FindBy(xpath = "//iframe[contains(@src,'sdk.google')]")
	private WebElement googleSDKIframe;

	@FindBy(xpath = ".//*[@id='videoArea']//div[contains(@class,'pagination')]//li")
	private WebElement rightPagination;

	@FindBy(xpath = "(//*[@class='tabsContent'])[1]//li//a[(string-length(text())>0 and contains(@data-track,'Latest')and not(contains(text(),'More from')))]")
	private List<WebElement> latestNews;

	@FindBy(xpath = "(//*[@class='tabsContent'])[1]//li//a[(string-length(text())>0 and contains(@data-track,'Top')and not(contains(text(),'More from')))]")
	private List<WebElement> topNews;

	@FindBy(xpath = "((//*[@class='tabsContent'])[2]//li//a[(string-length(text())>0 and contains(@data-track,'Latest')and not(contains(text(),'More from')))])")
	private List<WebElement> latestVideos;

	@FindBy(xpath = "((//*[@class='tabsContent'])[2]//li//a[(string-length(text())>0 and contains(@data-track,'Most')and not(contains(text(),'More from')))])")
	private List<WebElement> mostVideos;

	@FindBy(xpath = "//div[@class='tabsView']//a[text()='Most Viewed']")
	private WebElement mostViewedTab;

	@FindBy(className = "vidViews")
	private WebElement videoViews;

	private String rightWidgetStories = ".//*[@id='videoRHS']/li[%s]//a[string-length(@href)>0]";

	@FindBy(xpath = "//li//h2//a[@data-track='Most_Viewed' and string-length()>0 and not(@href='#')]")
	private List<WebElement> mostViewedWidgetLinks;

	@FindBy(xpath = "//li//h2//a[@data-track='Latest_Videos' and string-length()>0 and not(@href='#')]")
	private List<WebElement> latestVideosWidgetLinks;

	@FindBy(xpath = "//li//a[@data-track='Latest_News' and string-length()>0 and not(@href='#')]")
	private List<WebElement> latestNewsWidgetLinks;

	@FindBy(xpath = "//li//a[@data-track='Top_News' and string-length()>0 and not(@href='#')]")
	private List<WebElement> topNewsWidgetLinks;

	@FindBy(xpath = "//a[text()='Most Viewed' and @href='#']")
	private WebElement mostViewedWidgetTab;

	@FindBy(xpath = "//a[text()='Latest Videos' and @href='#']")
	private WebElement latestVideosWidgetTab;

	@FindBy(xpath = "//a[text()='Top News' and @href='#']")
	private WebElement topNewsWidgetTab;

	@FindBy(xpath = "//a[text()='Latest News' and @href='#']")
	private WebElement latestNewsWidgetTab;

	/// Getters///
	public WebElement getSkipAdDiv() {
		return skipAd;
	}

	public WebElement getVideoViews() {
		return videoViews;
	}

	public WebElement getSlikePlayer() {
		return slikePlayer;
	}

	public WebElement getGoogleSDKIframe() {
		return googleSDKIframe;
	}

	public WebElement getMostViewedTab() {
		return mostViewedTab;
	}

	public WebElement getVideoIFrame() {
		return videoIFrame;
	}

	public WebElement getTvWindow() {
		return tvWindow;
	}

	public WebElement getRightPagination() {
		return rightPagination;
	}

	public List<WebElement> getLatestNews() {
		return latestNews;
	}

	public List<WebElement> getTopNews() {
		return topNews;
	}

	public List<WebElement> getLatestVideos() {
		return latestVideos;
	}

	public List<WebElement> getMostVideos() {
		return mostVideos;
	}

	public String getRightWidgetStories() {
		return rightWidgetStories;
	}

	public List<WebElement> getSectionVideoLinks(String sectionName) {
		return driver.findElements(By.xpath("//h4//a[@data-track='" + sectionName + "' and string-length()>0]"));
	}

	public WebElement getSectionHeading(String sectionName) {
		return driver.findElement(By.xpath("//h3//a[@data-track='" + sectionName + "' and string-length()>0]"));
	}

	public List<WebElement> getMostViewedWidgetLinks() {
		return mostViewedWidgetLinks;
	}

	public List<WebElement> getLatestVideosWidgetLinks() {
		return latestVideosWidgetLinks;
	}

	public List<WebElement> getLatestNewsWidgetLinks() {
		return latestNewsWidgetLinks;
	}

	public List<WebElement> getTopNewsWidgetLinks() {
		return topNewsWidgetLinks;
	}

	public WebElement getMostViewedWidgetTab() {
		return mostViewedWidgetTab;
	}

	public WebElement getLatestVideosWidgetTab() {
		return latestVideosWidgetTab;
	}

	public WebElement getTopNewsWidgetTab() {
		return topNewsWidgetTab;
	}

	public WebElement getLatestNewsWidgetTab() {
		return latestNewsWidgetTab;
	}

}
