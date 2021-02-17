package com.web.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ETPrimeHomePageObjects {
	private WebDriver driver;

	public ETPrimeHomePageObjects(WebDriver driver) {
		this.driver = driver;

	}

	@FindBy(xpath = "//div[contains(@class,'top_stry_block')]//a[@class='title' and contains(@href,'primearticleshow')]")
	private List<WebElement> topStoriesBlock;

	////////////// **********Getter*********///////

	public List<WebElement> getTopStoriesBlock() {
		return topStoriesBlock;
	}

}
