package com.pagemethods;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;

import app.pageobjects.HeaderPageObjects;
import common.launchsetup.BaseTest;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import static common.launchsetup.BaseTest.iAppCommonMethods;

public class HeaderPageMethods {
	AppiumDriver<?> appDriver;
	HeaderPageObjects headerPageObjects;

	public HeaderPageObjects getHeaderPageObjects() {
		return headerPageObjects;
	}

	public HeaderPageMethods(AppiumDriver<?> driver) {
		appDriver = driver;
		headerPageObjects = new HeaderPageObjects();
		PageFactory.initElements(new AppiumFieldDecorator(appDriver), headerPageObjects);
	}

	public boolean clickMenuIcon() {
		return iAppCommonMethods.clickElement(headerPageObjects.getMenuButton());
	}

	public void clickNotificationIcon() {
		try {
			headerPageObjects.getNotificationIcon().click();
		} catch (NoSuchElementException e) {

		}
	}

	/*
	 * Gets header text for listing page
	 */
	public String getHeaderText() {
		try {
			return headerPageObjects.getTopHeading().getText().replace("Acc_menu_", "");
		} catch (Exception e) {
			return "not found";
		}
	}

	/*
	 * public MobileElement getHeaderEl() { MobileElement el = null; try { el =
	 * headerPageObjects.getHeaderText(); } catch (Exception e) {
	 * 
	 * } return el; }
	 */
	// needed
	public void clickMenuIconSwipeUpFirst() {
		BaseTest.iAppCommonMethods.scrollDown();
		try {
			headerPageObjects.getMenuButton().click();
		} catch (WebDriverException e) {

		}
	}

	public void clickSearchIcon() {
		headerPageObjects.getSearchIcon().click();
	}

	public String getTopHeading() {
		return headerPageObjects.getTopHeading().getText();
	}

	public MobileElement getTopHeadingEl() {
		return headerPageObjects.getTopHeading();
	}

	public MobileElement getTopTabsBar() {
		return headerPageObjects.getTopTabBar();
	}

	public void clickBookMarkIcon() {
		headerPageObjects.getBookmarkButton().click();
	}
	
	public void clickMoreIconButton() {
		headerPageObjects.getMoreIconButton().click();
	}

	public boolean isBookMarkIconPresent() {
		try {
			headerPageObjects.getBookmarkButton().isDisplayed();

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/** New Methods **/

	public boolean scrollTopTabsLeft() {
		boolean flag = false;
		try {
			iAppCommonMethods.scrollLeft(headerPageObjects.getTopTabBar());
			flag = true;
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public boolean scrollTopTabsRight() {
		boolean flag = false;
		int counter = 0;
		try {
			while (counter < 5) {
				iAppCommonMethods.scrollRight(headerPageObjects.getTopTabBar());
				flag = true;
				counter++;
			}
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public boolean isTabDisplayed(String text) {
		/*if (BaseTest.platform.contains("android"))
			text = text.toUpperCase();*/
		if (BaseTest.platform.contains("ios"))
			text = " "+text;
		return iAppCommonMethods.isElementDisplayed(iAppCommonMethods.getElementByText(text));
	}

	public boolean clickTopTab(String text) {
		int counter = 0;
		/*if (BaseTest.platform.contains("android"))
			text = text.toUpperCase();*/
		if (BaseTest.platform.contains("ios"))
			text = " "+text;
		try {
			while (!iAppCommonMethods.isElementDisplayed(iAppCommonMethods.getElementByText(text)) && counter < 10) {
				scrollTopTabsLeft();
				counter++;
			}
			iAppCommonMethods.getElementByText(text).click();
			WaitUtil.sleep(2000);
			return true;
		} catch (WebDriverException | NullPointerException e) {
			return false;

		}
	}
	

	public boolean clickHeaderSignin() {
		boolean flag = false;
		try {
			WaitUtil.sleep(1000);
			headerPageObjects.getHeaderSignin().click();
			flag = true;
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return flag;
	}
}
