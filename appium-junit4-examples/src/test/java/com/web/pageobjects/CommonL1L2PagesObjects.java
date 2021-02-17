package com.web.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CommonL1L2PagesObjects {

	private WebDriver driver;

	public CommonL1L2PagesObjects(WebDriver driver) {
		this.driver = driver;

	}
	@FindBy(xpath = "//div[@class='tabs']//ul[@class='tUl']//li//a")
	private List<WebElement> videoWidgetTabs;

	@FindBy(xpath = "//li[not(contains(@class,'hide'))]//div[@class='slidmainCont']//div[@class='slideDiv active']//div//a")
	private List<WebElement> activeSectionVideoWidgetVideoLinks;

	@FindBy(xpath = "//div[@class='contentDiv']//li//a")
	private List<WebElement> topSearchesDiv;

	@FindBy(xpath = "//div[@id='mostPopular']//div//a[string-length()>0]")
	private List<WebElement> notTOBeMissedSectionLinks;

	@FindBy(xpath = "//div[@class='seoWidget_con']//a")
	private List<WebElement> topTrendingTermsWidget;

	// hidden links are also included as 404 from them will also checked
	// div[@class='top-news' or @class='featured' or
	// @class='bThumb']//a[string-length()>0]
	// xpath to remove the hidden links
	// div[@class='top-news' or @class='featured' or
	// @class='bThumb']//a[string-length()>0 and not(parent:: li[@class='hidden'
	// and not(@style)])]
	@FindBy(xpath = "//div[@class='top-news' or @class='featured' or @class='bThumb']//a[string-length()>0 and not(parent:: li[@class='hidden' and not(@style)])]")
	private List<WebElement> topStoriesHrefLinks;

	//////////////////////// getters/////////////////////////////////

	public List<WebElement> getNotTOBeMissedSectionLinks() {
		return notTOBeMissedSectionLinks;
	}

	public List<WebElement> getVideoWidgetTabs() {
		return videoWidgetTabs;
	}

	public List<WebElement> getActiveSectionVideoWidgetVideoLinks() {
		return activeSectionVideoWidgetVideoLinks;
	}

	public List<WebElement> getTopSearchesDiv() {
		return topSearchesDiv;
	}

	public List<WebElement> getTopTrendingTermsWidget() {
		return topTrendingTermsWidget;
	}

	public List<WebElement> getTopStoriesHrefLinks() {
		return topStoriesHrefLinks;
	}
	
	public List<WebElement> getNewSubSectionLinks(String section){
		return driver.findElements(By.xpath("//h2[@class='secHead' and text()='"+section+"']//ancestor::div[@class='contentwrapper']//a[string-length()>0 and contains(@href,'show')]"));
	}
}
