

package com.web.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;




import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class HeaderPageObjects {

	private WebDriver driver;

	public HeaderPageObjects(WebDriver driver) {
		this.driver = driver;
	}
	/*
	 * @FindBy(xpath =
	 * ".//div[@class='newTopBar']//*[contains(text(),'Sign') or contains(text(),'SIGN') or contains(text(),'Login') or contains(text(),'Start Now')]"
	 * ) private WebElement signInLink;
	 */

	@FindBy(xpath = "//a[@class='signInLink']")
	private WebElement signInLink;

	@FindBy(css = ".flr.signIn>img")
	private WebElement loggedIn;

	/*
	 * @FindBy(xpath = "//div[@class='logoBar flt']//tdate") private WebElement
	 * dateTimetab;
	 */
	@FindBy(xpath = "//a[text()='Logout']")
	private WebElement logoutLink;

	@FindBy(xpath = "//div[contains(@class,'signIn')]/span[@class='ddArrow']")
	private WebElement dropDown;

	@FindBy(xpath = "//div[@class='timeEdition tac']//span[@class='dib time']")
	private WebElement dateTimetab;

	@FindBy(xpath = "//header//div[contains(@class,'signOut')]")
	private WebElement loggedInUserTab;
	
	@FindBy(xpath = "//div[@id='searchBar']")
	private WebElement searchIcon;
	
	@FindBy(xpath = "//div[@class='srch-overlay-content']/input")
	private WebElement searchField;
	
	@FindBy(xpath = "//ul[@class='searchListAll']/li[@class='head pstories']/following-sibling::li[starts-with(@data-url,'/prime/')]/a")
	private List<WebElement> primeSearchResults;
	
	@FindBy(xpath  = "//div[@class='flr subSign']//span[@class='cSprite icon_pp']")
	private WebElement primeIconForSubscribedUser;
	
	@FindBy(xpath = "//a[@class='sec_logo prime_hd' or @class='sec_logo prime_hd bigLogo']")
	private WebElement etPrimeLogoOnTop;
	
	@FindBy(xpath = "//div[@class='signMenu']//a[text()='Logout']")
	private WebElement logOutLinkUnderLoggedInMenu;

	@FindBy(xpath = "//div[contains(@class,'subSign')]//span[@class='dd']")
	private WebElement dropDownMenuOnSignIn;
	
	@FindBy(xpath = "//div[@id='searchBar']//span[contains(@class,'searchIcon')]")
	private WebElement searchButton;

	@FindBy(xpath = "//a[text()='My Subscriptions']")
	private WebElement mySubscriptions;
	
	@FindBy(xpath = "//a[normalize-space()='My Preferences']")
	private WebElement myPreferencesTab;
	
	

	///////////////////////////////////
	public WebElement getDD() {
		return dropDown;
	}

	public WebElement getLogoutLink() {
		return logoutLink;
	}

	public WebElement getLoggedInUserImage() {
		return loggedIn;
	}

	public WebElement getSignInLink() {
		return signInLink;
	}

	public WebElement getDateTimetab() {
		return dateTimetab;
	}

	public WebElement getLoggedInUserTab() {
		return loggedInUserTab;
	}
	
	public WebElement getSearchIcon() {
		return searchIcon;
	}
	
	public WebElement getSearchField() {
		return searchField;
	}
	
	public List<WebElement> getPrimeSearchResults() {
		return primeSearchResults;
	}
	
	public WebElement getCategoryByNameFromHeader(String name) {
		return driver.findElement(By.xpath("//div[@id='topnavBlk']/following-sibling::div[@class='sbnv_wrapper w1']//a[text()='"+name+"']"));
	}
	
	public WebElement getSelectedCategoryFromHeader(String name) {
		return driver.findElement(By.xpath("//div[@id='topnavBlk']/following-sibling::div[@class='sbnv_wrapper w1']//a[text()='"+name+"' and @class='current']"));
	}
	
	public WebElement getPrimeIconForSubscribedUser() {
		return primeIconForSubscribedUser;
	}
	
	public WebElement getEtPrimeLogoOnTop() {
		return etPrimeLogoOnTop;
	}
	
	public WebElement getLogOutLinkUnderLoggedInMenu() {
		return logOutLinkUnderLoggedInMenu;
	}

	public WebElement getDropDownMenuOnSignIn() {
		return dropDownMenuOnSignIn;
	}
	
	
	public WebElement getSearchButton() {
		return searchButton;
	}
	
	public WebElement getMySubscriptions() {
		return mySubscriptions;
	}
	
	public WebElement getMyPreferencesTab() {
		return myPreferencesTab;
	}
}
