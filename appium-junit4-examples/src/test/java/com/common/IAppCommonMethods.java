package com.common;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;

public interface IAppCommonMethods {

	public void handleWelcomeScreenAlerts();

	public boolean scrollUpToElement(String text);

	/**
	 * Slightest swipe up the screen
	 */
	public void swipeByScreenPercentage(double startYPerc, double endYPerc);

	/**
	 * Slightly swipes up the screen
	 */
	public void swipeUp();

	/**
	 * Slightly swipes down the screen
	 */
	public void swipeDown();

	/**
	 * Scrolls one complete view upwards
	 */
	public void scrollUp();

	/**
	 * Scrolls one complete view downwards
	 */
	public void scrollDown();

	public MobileElement getElementByText(String text);

	public AppiumDriver<?> getDriver();

	public void scrollLeft(MobileElement el);

	public void scrollRight(MobileElement el);
	
	public  void tapScreen(AppiumDriver<?> appDriver, double percX, double percY);

	// Default Methods///
	public void navigateBack(AppiumDriver<?> appDriver) ;

	default void switchToWebView(AppiumDriver<?> appDriver, String viewIdentifier) {
		Set<String> availableContext = appDriver.getContextHandles();
		for (String context : availableContext) {
			if (context.contains("WEBVIEW") && context.contains(viewIdentifier)) {
				appDriver.context(context);
			}
		}
	}

	default void switchToNativeView(AppiumDriver<?> appDriver) {
		Set<String> availableContext = appDriver.getContextHandles();
		for (String context : availableContext) {
			if (context.contains("NATIVE")) {
				appDriver.context(context);
				break;
			}
		}
	}

	default List<MobileElement> getDisplayedElement(List<MobileElement> li) {
		List<MobileElement> liEl = new LinkedList<MobileElement>();
		for (MobileElement el : li) {
			if (el.isDisplayed()) {
				liEl.add(el);
			}
		}
		return liEl;
	}

	default boolean tapElementRightSide(AppiumDriver<?> appDriver, MobileElement element) {
		try {
			int x = (int) (getElementsXCoordinates(element) + element.getSize().getWidth()*.8);
			int y = (int)((getElementsYCoordinates(element) + element.getSize().getHeight()/2));
			new TouchAction<>((PerformsTouchActions) appDriver).tap(new PointOption<>().withCoordinates(x, y)).perform();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	default boolean tapScreenRightSide(AppiumDriver<?> appDriver) {
		Dimension size = appDriver.manage().window().getSize();
		new TouchAction<>((PerformsTouchActions) appDriver)
				.tap(new PointOption<>().withCoordinates((int) (size.width * 0.90), (int) (size.height / 2))).perform();
		return false;
	}

	default boolean tapByCoordinates(AppiumDriver<?> appDriver, int x, int y) {
		try {
			new TouchAction<>((PerformsTouchActions) appDriver).tap(new PointOption<>().withCoordinates(x, y))
					.perform();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean scrollElementTopToHeader(MobileElement elToBeScrolled, MobileElement elScrolledTill, int time);

	public boolean clickElement(MobileElement e);

	default int getElementsYCoordinates(MobileElement el) {
		return el.getLocation().getY();
	}

	default int getElementsXCoordinates(MobileElement el) {
		return el.getLocation().getX();
	}

	default String getElementText(MobileElement e) {
		String text = null;
		try {
			text = e.getText();

		} catch (NoSuchElementException ex) {

		}
		return text;
	}

	default boolean isElementDisplayed(MobileElement el) {
		try {
			return el.isDisplayed();
		} catch (WebDriverException | NullPointerException e) {
			return false;
		}

	}

	default int getWindowHeight(AppiumDriver<?> appiumDriver) {
		return appiumDriver.manage().window().getSize().getHeight();
	}

	default boolean sendKeys(MobileElement el, String inputVal) {
		boolean flag = false;
		try {
			el.sendKeys(inputVal);
			flag = true;
		} catch (WebDriverException wde) {

		}
		return flag;
	}
	
	
}
