package com.web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TechPageObjects {
	private WebDriver driver;

	public TechPageObjects(WebDriver driver) {
		this.driver = driver;

	}

	@FindBy(xpath = "//h2//a[contains(text(),'Hardware')]")
	private WebElement hardwareLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Software')]")
	private WebElement softwareLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Internet')]")
	private WebElement internetLink;

	@FindBy(xpath = "//h2//a[contains(text(),'ITeS')]")
	private WebElement itesLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Hardware')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> hardwareHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Software')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> softwareHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Internet')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> internetHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'ITeS')]//..//..//li//a[contains(text(),' ')]")
	private List<WebElement> itesHeadlines;

	@FindBy(xpath = "//h2//a[contains(text(),'Tech & Gadgets')]")
	private WebElement techAndGadgetsLink;

	@FindBy(xpath = "//h2//a[contains(text(),'Tech & Gadgets')]//..//..//div//a[contains(text(),' ')]")
	private List<WebElement> techAndGadgetsHeadlines;

	@FindBy(xpath = "//div[contains(@data-ga-category,'Top News') or contains(@data-ga-category,'Top_News') ]//a[string-length()>0 and contains(@href,'show')]")
	private List<WebElement> topNewsLinks;

	@FindBy(xpath = "//div[@class='seoWidget']//a[string-length()>0]")
	private List<WebElement> trendinTermsWidgetLinks;

	/////////////////////////////////////////////////////////////

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getHardwareLink() {
		return hardwareLink;
	}

	public WebElement getSoftwareLink() {
		return softwareLink;
	}

	public WebElement getInternetLink() {
		return internetLink;
	}

	public WebElement getItesLink() {
		return itesLink;
	}

	public List<WebElement> getHardwareHeadlines() {
		return hardwareHeadlines;
	}

	public List<WebElement> getSoftwareHeadlines() {
		return softwareHeadlines;
	}

	public List<WebElement> getInternetHeadlines() {
		return internetHeadlines;
	}

	public List<WebElement> getItesHeadlines() {
		return itesHeadlines;
	}

	public WebElement getTechAndGadgetsLink() {
		return techAndGadgetsLink;
	}

	public List<WebElement> getTechAndGadgetsHeadlines() {
		return techAndGadgetsHeadlines;
	}

	public List<WebElement> getTopNewsLinks() {
		return topNewsLinks;
	}

	public List<WebElement> getTrendinTermsWidgetLinks() {
		return trendinTermsWidgetLinks;
	}
}
