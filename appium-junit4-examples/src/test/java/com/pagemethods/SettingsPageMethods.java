package com.pagemethods;

import org.openqa.selenium.support.PageFactory;

import app.pageobjects.SettingsPageObjects;
import common.launchsetup.BaseTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SettingsPageMethods {
	AppiumDriver<?> appiumDriver;
	SettingsPageObjects settingsPageObjects;

	public SettingsPageMethods(AppiumDriver<?> driver) {
		appiumDriver = driver;
		settingsPageObjects = new SettingsPageObjects();
		PageFactory.initElements(new AppiumFieldDecorator(driver), settingsPageObjects);
	}

	public void clickLogout() {
		settingsPageObjects.getLogoutLink().click();
	}

	public boolean isMyTimesPointOptionPresent() {
		if ( BaseTest.platform.contains("android")) {
			return BaseTest.iAppCommonMethods.isElementDisplayed(settingsPageObjects.getTimesPoints());
		}
		else {
			BaseTest.iAppCommonMethods.swipeUp();
			return BaseTest.iAppCommonMethods.isElementDisplayed(settingsPageObjects.getTimesPoints());
		}
	}

}
