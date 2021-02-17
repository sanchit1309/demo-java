package com.web.pagemethods;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import web.pageobjects.HomePageObjects;
import web.pageobjects.PrimeShowStoryPageObjects;
import web.pageobjects.TechPageObjects;

public class PrimeShowStoryPageMethods {

	private WebDriver driver;
	private HomePageObjects homePageObjects;
	private PrimeShowStoryPageObjects primeShowStoryPageObjects;

	public PrimeShowStoryPageMethods(WebDriver driver) {
		this.driver = driver;
		homePageObjects = PageFactory.initElements(driver, HomePageObjects.class);
		primeShowStoryPageObjects = PageFactory.initElements(driver, PrimeShowStoryPageObjects.class);

	}

	public WebElement getPrimeShowBodyContent() {

		try {
			return primeShowStoryPageObjects.getPrimeShowContentBody();

		} catch (Exception ee) {
			ee.printStackTrace();
			return null;
		}
	}

	public String getPrimeShowBodyContentText() {

		try {
			return WebBaseMethods.getListTextUsingJSE(primeShowStoryPageObjects.getPrimeShowContentBody());

		} catch (Exception ee) {
			ee.printStackTrace();
			return "The primeshow body content is not found";
		}
	}
	
	public List<String> getAllETPrimeShowLinks() {

		List<String> primeUrls = new LinkedList<>();
		ArrayList<String> newList = new ArrayList<String>();
		try {
			primeUrls = WebBaseMethods.getListHrefUsingJSE(primeShowStoryPageObjects.getAllETpremiumLinks());
			for (String element : primeUrls) {
				
				if (!newList.contains(element)) {

					newList.add(element);
				}
			}

		} catch (Exception ee) {
			ee.printStackTrace();

		}
		System.out.println(newList);
		return newList;
	}
}
