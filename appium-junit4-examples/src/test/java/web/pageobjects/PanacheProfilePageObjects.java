package web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PanacheProfilePageObjects {

	@FindBy(xpath = "//h1[@class = 'profileTitle']")
	private WebElement profileTitle;

	@FindBy(xpath = "//span[@class = 'profileDesig']")
	private WebElement profileDesignation;

	@FindBy(xpath = "//span[@class = 'heading' and text()='Birth Details']/following-sibling::span[@class='value']")
	private WebElement birthDetails;

	@FindBy(xpath = "//span[@class = 'heading' and text()='Nationality']/following-sibling::span[@class='value']")
	private WebElement nationality;

	@FindBy(xpath = "//span[@class = 'heading' and text()='League']/following-sibling::span[@class='value']")
	private WebElement league;

	@FindBy(xpath = "//span[@class = 'heading' and text()='Education']/following-sibling::span[@class='value']")
	private WebElement education;

	@FindBy(xpath = "//span[@class = 'heading' and text()='Net Worth']/following-sibling::span[@class='value']")
	private WebElement netWorth;

	@FindBy(xpath = "//li[@class='profileNewsStry']//a")
	private List<WebElement> profileNewsStoryList;

	@FindBy(xpath = "//div[@class = 'articleContent flt']//div//ul//li")
	private List<WebElement> profileSummaryDetailsList;

	@FindBy(xpath = "//div[contains(@class,'seoWidget')]//a[string-length()>0]")
	private List<WebElement> trendingInProfilesWidget;

	@FindBy(xpath = "//strong[contains(text(),'Before you go')]/following-sibling::ul[1]//li")
	private List<WebElement> contentListUnderBeforeYouGo;

	@FindBy(xpath = "//strong[contains(text(),'Journey ')]/following-sibling::ul[1]//li")
	private List<WebElement> contentListUnderJourneySoFar;

	public WebElement getProfileTitle() {
		return profileTitle;
	}

	public WebElement getProfileDesignation() {
		return profileDesignation;
	}

	public WebElement getBirthDetails() {
		return birthDetails;
	}

	public WebElement getNationality() {
		return nationality;
	}

	public WebElement getLeague() {
		return league;
	}

	public WebElement getEducation() {
		return education;
	}

	public WebElement getNetWorth() {
		return netWorth;
	}

	public List<WebElement> getProfileNewsStoryList() {
		return profileNewsStoryList;
	}

	public List<WebElement> getProfileSummaryDetailsList() {
		return profileSummaryDetailsList;
	}

	public List<WebElement> getTrendingInProfilesWidget() {
		return trendingInProfilesWidget;
	}

	public List<WebElement> getContentListUnderBeforeYouGo() {
		return contentListUnderBeforeYouGo;
	}

	public List<WebElement> getContentListUnderJourneySoFar() {
		return contentListUnderJourneySoFar;
	}

}
