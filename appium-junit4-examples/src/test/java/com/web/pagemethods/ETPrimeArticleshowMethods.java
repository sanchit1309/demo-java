package com.web.pagemethods;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import web.pageobjects.ETPrimeArticleshowObjects;
import web.pageobjects.HeaderPageObjects;

public class ETPrimeArticleshowMethods {

	private ETPrimeArticleshowObjects etPrimeArticleshowObjects;
	private WebDriver driver;

	public ETPrimeArticleshowMethods(WebDriver driver) {
		this.driver = driver;
		etPrimeArticleshowObjects = PageFactory.initElements(driver, ETPrimeArticleshowObjects.class);
	}

	public boolean isPrimePaywalBlockerShown() {
		boolean flag = false;
		try {
			if (etPrimeArticleshowObjects.getEtPrimePaywallBlocker().isDisplayed()) {
				flag = true;
			}

		} catch (Exception ee) {
			ee.printStackTrace();
			flag = false;

		}
		return flag;

	}
	
	
	

}
