package com.web.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ETPrimeArticleshowObjects {

	private WebDriver driver;

	public ETPrimeArticleshowObjects(WebDriver driver) {
		this.driver = driver;

	}

	@FindBy(xpath = "//section[contains(@class,'prime_paywall')]")
	private WebElement etPrimePaywallBlocker;

	/////////////////// getters//////////////////////////

	public WebElement getEtPrimePaywallBlocker() {
		return etPrimePaywallBlocker;
	}

}
