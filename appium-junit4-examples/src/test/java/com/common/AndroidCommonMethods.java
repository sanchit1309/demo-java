package com.common;

import java.time.Duration;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import app.pagemethods.AlertsPromptsMethods;
import common.launchsetup.BaseTest;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class AndroidCommonMethods implements IAppCommonMethods {
	public AndroidDriver<?> androidDriver;

	public AndroidCommonMethods(AndroidDriver<?> driver) {
		androidDriver = driver;
	}

	public void swipeHorizontallyRightToLeftElement(WebElement el, int time) {
		try {
			Dimension size = androidDriver.manage().window().getSize();
			int startx = (int) (size.width * 0.80);
			int endx = (int) (size.width * 0.20);
			int starty = el.getLocation().getY();
			new TouchAction<>((PerformsTouchActions) androidDriver)
					.press(new PointOption<>().withCoordinates(startx, starty))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
					.moveTo(new PointOption<>().withCoordinates(endx, starty)).release().perform();
			// appDriver.swipe(startx, starty, endx, starty, time);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public boolean scrollUpToElement(String text) {
		int count = BaseTest.globalFlag ? 10 : 40;
		Dimension size = androidDriver.manage().window().getSize();
		boolean flag = false;
		int counter = 0;
		while (!flag && counter < count) {
			try {
				if (androidDriver.findElementsByAndroidUIAutomator("new UiSelector().text(\"" + text + "\")")
						.size() > 0)
					// System.out.println("Scrolled to " + by);
					flag = true;
				else {
					try {
						new TouchAction<>((PerformsTouchActions) androidDriver)
								.press(new PointOption<>().withCoordinates((int) size.width / 2,
										(int) (size.height * 0.80)))
								.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(new PointOption<>()
										.withCoordinates((int) size.width / 2, (int) (size.height * 0.20)))
								.release().perform();

					} catch (WebDriverException e) {

					}
				}
			} catch (NoSuchElementException e) {
				flag = false;
			}
			counter++;
			WaitUtil.sleep(1000);
		}
		return flag;
	}

	@Override
	public void handleWelcomeScreenAlerts() {
		new AlertsPromptsMethods(androidDriver).dismissAllPopups();
	}

	@Override
	public AndroidDriver<?> getDriver() {
		// TODO Auto-generated method stub
		return androidDriver;
	}

	@Override
	public MobileElement getElementByText(String text) {
		try {
			return (MobileElement) androidDriver
					.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"" + text + "\")"));
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	@Override
	public void swipeUp() {
		try {
			swipeByScreenPercentage(0.70, 0.30);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void swipeDown() {
		try {
			swipeByScreenPercentage(0.30, 0.70);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public void scrollUp() {
		try {
			swipeByScreenPercentage(0.90, 0.05);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public void scrollDown() {
		try {
			swipeByScreenPercentage(0.20, 0.80);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void swipeByScreenPercentage(double startYPerc, double endYPerc) {
		try {
			Dimension size = androidDriver.manage().window().getSize();
			int startx = (int) size.width / 10;
			int starty = (int) (size.height * startYPerc);// 0.80
			int endy = (int) (size.height * endYPerc);// 0.20
			new TouchAction<>((PerformsTouchActions) androidDriver)
					.press(new PointOption<>().withCoordinates(startx, starty))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
					.moveTo(new PointOption<>().withCoordinates(startx, endy)).release().perform();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void scrollLeft(MobileElement el) {
		try {
			Dimension size = androidDriver.manage().window().getSize();
			int startx = (int) (size.width * 0.80);
			int endx = (int) (size.width * 0.20);
			int starty = el.getLocation().getY();
			new TouchAction<>((PerformsTouchActions) androidDriver)
					.press(new PointOption<>().withCoordinates(startx, starty))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
					.moveTo(new PointOption<>().withCoordinates(endx, starty)).release().perform();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public void scrollRight(MobileElement el) {
		try {
			Dimension size = androidDriver.manage().window().getSize();
			int startx = (int) (size.width * 0.10);
			int endx = (int) (size.width * 0.90);
			int starty = el.getLocation().getY();
			new TouchAction<>((PerformsTouchActions) androidDriver)
					.press(new PointOption<>().withCoordinates(startx, starty))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
					.moveTo(new PointOption<>().withCoordinates(endx, starty)).release().perform();
			// appDriver.swipe(startx, starty, endx, starty, time);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public boolean scrollElementTopToHeader(MobileElement elToBeScrolled, MobileElement elScrolledTill, int time) {
		boolean flag = false;
		try {
			MobileElement mel = elScrolledTill;
			Point topBar = mel.getLocation();
			Point element = elToBeScrolled.getLocation();
			int startx = element.getX();
			int starty = element.y;
			int endy = topBar.getY();
			new TouchAction<>((PerformsTouchActions) androidDriver)
					.press(new PointOption<>().withCoordinates(startx, starty))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
					.moveTo(new PointOption<>().withCoordinates(startx, endy)).release().perform();
			swipeByScreenPercentage(0.75, 0.70);
			flag = true;
		} catch (Exception ex) {

			ex.printStackTrace();

		}
		return flag;
	}

	@Override
	public boolean clickElement(MobileElement e) {
		boolean flag = false;
		try {
			WaitUtil.sleep(1000);
			e.click();
			WaitUtil.sleep(1000);
			flag = true;
		} catch (NoSuchElementException | NullPointerException ex) {

		}
		WaitUtil.sleep(2000);
		return flag;
	}

	@Override
	public void navigateBack(AppiumDriver<?> appDriver) {
		appDriver.navigate().back();

	}

	@Override
	public void tapScreen(AppiumDriver<?> appDriver, double percX, double percY) {
		// TODO Auto-generated method stub
		
	}

}
