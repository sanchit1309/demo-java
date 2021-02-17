package com.web.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MarketBandObjects {

	@FindBy(id = "S1_box")
	private WebElement sensex;

	@FindBy(id = "S1_box")
	private WebElement nifty;

	@FindBy(id = "S4_box")
	private WebElement goldValue;

	@FindBy(id = "S4_box")
	private WebElement usdValue;
	
	@FindBy(xpath = "//div[@id='S1_box']//a[text()='Nifty']")
	private WebElement niftyTitle;
	
	@FindBy(xpath = "//div[@id='S1_box']//a[text()='Sensex']")
	private WebElement sensexTitle;
	
	@FindBy(xpath = "//div[@id='S4_box']//a[contains(text(), 'Gold')]")
	private WebElement goldTitle;
	
	@FindBy(xpath = "//div[@id='S4_box']//a[text()='USD/INR']")
	private WebElement usdinrTitle;

	@FindBy(xpath = "//a[@class='ms']//p")
	private WebElement timeDateElement;

	////////////////////////////////
	public WebElement getTimeDateElement() {
		return timeDateElement;
	}

	public WebElement getSensex() {
		return sensex;
	}

	public WebElement getNifty() {
		return nifty;
	}

	public WebElement getGoldValue() {
		return goldValue;
	}

	public WebElement getUsdValue() {
		return usdValue;
	}

	public WebElement getNiftyTitle() {
		return niftyTitle;
	}

	public WebElement getSensexTitle() {
		return sensexTitle;
	}

	public WebElement getGoldTitle() {
		return goldTitle;
	}

	public WebElement getUsdinrTitle() {
		return usdinrTitle;
	}

}
