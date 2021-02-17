package com.web.pagemethods;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import common.utilities.WaitUtil;
import web.pageobjects.BookmarksObjects;

public class BookmarksPageMethods {

	private WebDriver driver;
	private BookmarksObjects bookmarkObjects;

	public BookmarksPageMethods(WebDriver driver) {
		this.driver = driver;
		bookmarkObjects = PageFactory.initElements(driver, BookmarksObjects.class);
	}

	public WebElement getFadeoutSection() {
		return bookmarkObjects.getFadeoutSection();
	}

	public List<WebElement> getSavedStoriesList() {
		return bookmarkObjects.getSavedStoriesList();
	}

	public List<WebElement> getSavedStoriesHeading() {
		return bookmarkObjects.getSavedStoriesHeading();
	}

	public void deleteSavedStories() {
		// WebBaseMethods.clickElementUsingJSE(articleMethods.getSaveStoriesTab());
		List<WebElement> deleteButtons = bookmarkObjects.getDeleteStories();
		getFadeoutSection().click();
		deleteButtons.forEach(button -> {
			WaitUtil.sleep(1000);
			WebBaseMethods.clickElementUsingJSE(button);
			WaitUtil.sleep(2000);
			driver.switchTo().alert().accept();

		});

	}
}
