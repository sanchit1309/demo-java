package com.web.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MutualFundWidgetObjects {
	private WebDriver driver;

	public MutualFundWidgetObjects(WebDriver driver) {
		this.driver = driver;

	}

	@FindBy(xpath = "//div[@id='topWealthWidgetDiv']//li[contains(@class,'active')]")
	private List<WebElement> topMutualFundsList;

	@FindBy(xpath = "//div[@class='flt fund mfwdgettab']//div[@class='tabs_container flt']//li[contains(@class,'tab')]/a")
	private List<WebElement> mutualFundWidgetTab;

	@FindBy(xpath = "//div[contains(@class, 'mfwidget dataWidget')]//ul[@class='tabContainer']")
	private WebElement mutualFundTab;

	@FindBy(xpath = "//div[@class='tbody flt']//div[not(contains(@style,'none'))]//div[contains(@class,'tr flt')]//div[contains(@class,'1mth')]")
	private List<WebElement> mutualFundScheme1Mnthtab;

	@FindBy(xpath = "//div[@class='tbody flt']//div[not(contains(@style,'none'))]//div[contains(@class,'tr flt')]//div[contains(@class,'3mths')]")
	private List<WebElement> mutualFundScheme3Mnthtab;

	@FindBy(xpath = "//div[@class='tbody flt']//div[not(contains(@style,'none'))]//div[contains(@class,'tr flt')]//div[contains(@class,'6mths')]")
	private List<WebElement> mutualFundScheme6Mnthtab;

	@FindBy(xpath = "//div[@class='tbody flt']//div[not(contains(@style,'none'))]//div[contains(@class,'tr flt')]//div[contains(@class,'1yr')]")
	private List<WebElement> mutualFundScheme1Yrtab;

	@FindBy(xpath = "//div[@class='tbody flt']//div[not(contains(@style,'none'))]//div[contains(@class,'tr flt')]//div[contains(@class,'3yrs')]")
	private List<WebElement> mutualFundScheme3Yrstab;

	@FindBy(xpath = "//div[@class='tbody flt']//div[not(contains(@style,'none'))]//div[contains(@class,'tr flt')]//div[contains(@class,'text-left')]//a")
	private List<WebElement> mutualFundSchemeNameLink;

	@FindBy(xpath = "//div[@class='tbody flt']//div[not(contains(@style,'none'))]//div[contains(@class,'flt')]//div[@class='td flt no-result']//span")
	private WebElement mutualFundNoSchemeNameFound;
	
	@FindBy(xpath="//div[contains(@class,'mfwidget')]//a[contains(@class,'buy_now')]")
	private List<WebElement> buyNowButton;
	
	@FindBy(xpath="//div[contains(@class,'mutualFund-container')]")
	private WebElement etMoneyOverlay;
	 
	@FindBy(xpath="//div[contains(@class,'mfwidget')]//span[contains(text(),'Featured')]/following-sibling::a")
	private List<WebElement> promotedScheme;
	
	@FindBy(xpath="//iframe[@id='EconomicTimesETMoneyPartnerFrame']")
	private List<WebElement> etMoneyPartnerFrame;

	////// getters////////
	

	public List<WebElement> getTopMutualFundsList() {
		return topMutualFundsList;
	}

	public List<WebElement> getMutualFundWidgetTab() {
		return mutualFundWidgetTab;
	}

	public List<WebElement> getMutualFundScheme1Mnthtab() {
		return mutualFundScheme1Mnthtab;
	}

	public List<WebElement> getMutualFundScheme3Mnthtab() {
		return mutualFundScheme3Mnthtab;
	}

	public List<WebElement> getMutualFundScheme6Mnthtab() {
		return mutualFundScheme6Mnthtab;
	}

	public List<WebElement> getMutualFundScheme1Yrtab() {
		return mutualFundScheme1Yrtab;
	}

	public List<WebElement> getMutualFundScheme3Yrstab() {
		return mutualFundScheme3Yrstab;
	}

	public List<WebElement> getMutualFundSchemeNameLink() {
		return mutualFundSchemeNameLink;
	}

	public WebElement getMutualFundNoSchemeNameFound() {
		return mutualFundNoSchemeNameFound;
	}

	public WebElement getMutualFundTab() {
		return mutualFundTab;
	}

	public WebElement getMutualFundTabs(String tabName) {

		return driver.findElement(By.xpath("//li[@id='" + tabName + "']/a"));

	}
	
	public WebElement getMfCategoryTab(String catName) {

		return driver.findElement(By.xpath("//a[@id='equity' and text()='" + catName + "']"));

	}

	public List<WebElement> getMFWidgetHeaderDataTabs(String tabName) {

		return driver.findElements(By.xpath("//div[@class='flt " + tabName + "']//div[@class='thead flt']//div[not(contains(@class,'text-left')) and contains(@class,'sort')]"));

	}

	public List<WebElement> getMFWidgetSubTabSchemeName(String tabName) {

		return driver.findElements(By.xpath("//div[@class='flt " + tabName
				+ "']//div[@class='tbody flt']//div[@class='tr flt']//div[contains(@class,'text-left')]"));

	}

	public List<WebElement> getMFWidgetSubTabSchemeData(String tabName) {

		return driver.findElements(By.xpath("//div[@class='flt " + tabName
				+ "']//div[@class='tbody flt']//div[contains(@class,'tr flt')]//div[not(contains(@class,'text-left'))]"));

	}
	
	public List<WebElement> getMutualFundWidgetTabSubmenu(String tabName) {

		return driver
				.findElements(By.xpath("//div[@class='flt fund mfwdgettab']//ul[@data-primary='" + tabName + "']/li"));

	}
	
	public WebElement getPromotedSchemeName(String buyBtnHref) {
		
		return driver.findElement(By.xpath("//div[contains(@class,'mfwidget dataWidgetName')]//a[contains(@href,'"
				+ buyBtnHref + "')]/../../div[1]/a"));
		
	}


	public List<WebElement>  getBuyNowButton() {
		return buyNowButton;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getEtMoneyOverlay() {
		return etMoneyOverlay;
	}

	public List<WebElement> getEtMoneyPartnerFrame() {
		return etMoneyPartnerFrame;
	}
	
	public List<WebElement> getPromotedScheme(){
		return promotedScheme;
	}

}
