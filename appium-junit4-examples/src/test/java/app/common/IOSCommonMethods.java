package app.common;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;

import app.pagemethods.AlertsPromptsMethods;
import common.launchsetup.BaseTest;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static common.launchsetup.BaseTest.iAppCommonMethods;

public class IOSCommonMethods implements IAppCommonMethods {
	public IOSDriver<?> iosDriver;
	private int topHeaderY = 0;

	public IOSCommonMethods(IOSDriver<?> driver) {
		iosDriver = driver;
	}

	public static void scrollInDrirection(AppiumDriver<?> iosDriver, String scrollDirection) {
		JavascriptExecutor js = (JavascriptExecutor) iosDriver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", scrollDirection);
		js.executeScript("mobile: scroll", scrollObject);
	}

	public static void scrollUpToElementByText(AppiumDriver<?> iosDriver, String predicateString, String direction) {
		JavascriptExecutor js = (JavascriptExecutor) iosDriver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		// scrollObject.put("element",((RemoteWebElement)
		// iosDriver.findElementByClassName("XCUIElementTypeTable")).getId());
		scrollObject.put("predicateString", predicateString);
		scrollObject.put("direction", direction);
		scrollObject.put("isVisible", "true");
		js.executeScript("mobile: swipe", scrollObject);

	}

	public void tapScreen(AppiumDriver<?> iosDriver, double percX, double percY) {
		JavascriptExecutor js = (JavascriptExecutor) iosDriver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		Dimension size = iosDriver.manage().window().getSize();
		scrollObject.put("x", (int) (size.getWidth() * percX) + "");
		scrollObject.put("y", (int) (size.getWidth() * percY) + "");
		js.executeScript("mobile: tap", scrollObject);
	}

	@Override
	public void handleWelcomeScreenAlerts() {
		AlertsPromptsMethods alertsPromptsMethods = new AlertsPromptsMethods(iosDriver);
		alertsPromptsMethods.acceptAlerts();
		alertsPromptsMethods.clickSkipEducationLayer();
	}

	@Override
	public IOSDriver<?> getDriver() {
		return iosDriver;
	}

	@Override
	public boolean scrollUpToElement(String predicateString) {
		int count = BaseTest.globalFlag ? 10 : 40;
		int visibleSwipes = 2;
		double swipeFromPer = 0.80;
		double swipeToPer = 0.20;
		System.out.println("BaseTest.globalFlag2--> " + BaseTest.globalFlag2);
		if (null == BaseTest.globalFlag2) {
			visibleSwipes = 0;
		} else if (!BaseTest.globalFlag2) {
			visibleSwipes = 1;
			swipeFromPer = 0.80;
			swipeToPer = 0.60;
		}

		System.out.println("Found scroll limit:" + count);
		return scrollUpToElement(predicateString, count, visibleSwipes, swipeFromPer, swipeToPer);
	}

	public boolean scrollUpToElement(String predicateString, int limitSwipes, int visibleSwipes, double swipeFromPer,
			double swipeToPer) {
		String predicateValue = "";
		Boolean isPresent = false;
		boolean isVisible = false;
		if (!(predicateString.toLowerCase().startsWith("name") || predicateString.toLowerCase().startsWith("value")))
			predicateValue = createPredicateString(predicateString);
		else
			predicateValue = predicateString;
		int counter = 0;
		while (!isPresent && counter < limitSwipes) {
			System.out.println("Swiping when counter:" + counter);
			List<?> liEl = iosDriver.findElements(MobileBy.iOSNsPredicateString(predicateValue));

			if (liEl.size() == 0) {
				scrollUpToElementByText(iosDriver, predicateValue, "up");
				WaitUtil.sleep(1000);
			}

			isPresent = ((List<?>) iosDriver.findElements(MobileBy.iOSNsPredicateString(predicateValue))).size() > 0
					? true : false;
			System.out.println("isPresent:" + isPresent);
			WaitUtil.sleep(2000);

			if (isPresent) {
				int visibleCounter = 0;
				isVisible = isElementDisplayed(
						(MobileElement) iosDriver.findElement(MobileBy.iOSNsPredicateString(predicateValue)));
				System.out.println("isVisible:" + isVisible);
				if (!isVisible)
					System.out.println("Counter for visibility swipes:" + visibleSwipes);
				while (((!isVisible) && visibleCounter < visibleSwipes)) {
					System.out.println("IsVisible:" + isVisible + ". Swiping for visibility counter:" + visibleCounter);
					swipeByScreenPercentage(swipeFromPer, swipeFromPer);
					WaitUtil.sleep(5000);
					isVisible = isElementDisplayed(
							(MobileElement) iosDriver.findElement(MobileBy.iOSNsPredicateString(predicateValue)));
					visibleCounter++;
				}
			}
			counter++;
		}
		if (isVisible && (((MobileElement) iosDriver.findElement(MobileBy.iOSNsPredicateString(predicateValue)))
				.getLocation().getY()) > (10 * (iosDriver.manage().window().getSize().height / 11))) {
			swipeByScreenPercentage(0.70, 0.30);

		}
		System.out.println("isPresent:" + isPresent + ",isVisible && isPresent:" + (isVisible && isPresent));
		return isPresent;
	}

	@Override
	public MobileElement getElementByText(String text) {
		String predicateString = createPredicateString(text);
		try {
			return (MobileElement) iosDriver.findElement(MobileBy.iOSNsPredicateString(predicateString));
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public void swipeUp() {
		JavascriptExecutor js = (JavascriptExecutor) iosDriver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "up");
		js.executeScript("mobile: swipe", scrollObject);
	}

	@Override
	public void swipeDown() {
		JavascriptExecutor js = (JavascriptExecutor) iosDriver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "down");
		js.executeScript("mobile: swipe", scrollObject);

	}

	@Override
	public void scrollUp() {
		JavascriptExecutor js = (JavascriptExecutor) iosDriver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "down");
		js.executeScript("mobile: scroll", scrollObject);
	}

	@Override
	public void scrollDown() {
		JavascriptExecutor js = (JavascriptExecutor) iosDriver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "up");
		js.executeScript("mobile: scroll", scrollObject);

	}

	@Override
	public void scrollLeft(MobileElement el) {
		JavascriptExecutor js = (JavascriptExecutor) iosDriver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "right");
		scrollObject.put("element", el.getId());
		js.executeScript("mobile: scroll", scrollObject);

	}

	@Override
	public void scrollRight(MobileElement el) {
		JavascriptExecutor js = (JavascriptExecutor) iosDriver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "left");
		scrollObject.put("element", el.getId());
		js.executeScript("mobile: scroll", scrollObject);

	}

	@Override
	public boolean scrollElementTopToHeader(MobileElement elToBeScrolled, MobileElement elScrolledTill, int time) {
		boolean flag = false;
		try {
			MobileElement mel = elScrolledTill;
			topHeaderY = topHeaderY == 0 ? mel.getLocation().getY() + elToBeScrolled.getSize().getHeight() + 10
					: topHeaderY;
			Point elLocation = elToBeScrolled.getLocation();
			int startX = 0;
			int startY = elLocation.getY();
			int endY = -(startY - topHeaderY);
			new TouchAction<>((PerformsTouchActions) iosDriver)
					.press(new PointOption<>().withCoordinates(startX, startY))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(5000)))
					.moveTo(new PointOption<>().withCoordinates(startX, endY)).release().perform();
			// new TouchAction(iosDriver).press(startX, startY).moveTo(startX,
			// endY).release().perform();
			System.out.println("startY:" + startY + ",endY:" + endY);
			flag = true;
		} catch (Exception ex) {

			ex.printStackTrace();

		}
		return flag;
	}

	private static String createPredicateString(String predicateString) {
		String toReturn = "";
		predicateString = predicateString.replace("'", "\\'");
		List<String> predicateNameLi = new LinkedList<String>();
		List<String> valArray = Arrays.asList(predicateString.split("\\s*,\\s*"));
		if (valArray.size() == 2) {
			predicateNameLi = Arrays.asList("value,name".split("\\s*,\\s*"));
		} else if (valArray.size() == 1) {
			predicateNameLi = Arrays.asList("label");
		}
		for (int i = 0; i < valArray.size(); i++) {
			toReturn += predicateNameLi.get(i) + "== '" + valArray.get(i) + "' && ";
		}
		toReturn = toReturn.trim();
		System.out.println("predicate string:" + toReturn.substring(0, toReturn.length() - 2));
		return toReturn.substring(0, toReturn.length() - 2);
	}

	@Override
	public boolean clickElement(MobileElement e) {
		boolean flag = false;
		try {
			int x = getElementsXCoordinates(e) + 2;
			int y = getElementsYCoordinates(e) + 2;
			new TouchAction<>((PerformsTouchActions) iosDriver).tap(new PointOption<>().withCoordinates(x, y))
					.perform();
			flag = true;
		} catch (NoSuchElementException | NullPointerException ex) {

		}
		WaitUtil.sleep(2000);
		return flag;
	}

	@Override
	public void swipeByScreenPercentage(double startYPerc, double endYPerc) {
		try {
			int y = iAppCommonMethods.getWindowHeight(iosDriver);
			int startx = 80;// 95
			int starty = (int) (y * startYPerc);// 0.80
			int endy = (int) (y * endYPerc);// 0.20
			endy = -(starty - endy);
			System.out.println("scrolling:" + starty + " " + endy);
			new TouchAction<>((PerformsTouchActions) iosDriver)
					.press(new PointOption<>().withCoordinates(startx, starty))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(5000)))
					.moveTo(new PointOption<>().withCoordinates(startx, endy)).release().perform();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public void navigateBack(AppiumDriver<?> appDriver) {
		iAppCommonMethods.swipeDown();
		List<MobileElement> navigateBack = (List<MobileElement>) appDriver
				.findElements(By.xpath("//XCUIElementTypeButton[contains(@label,'Acc_menu_')]"));
		if (navigateBack.size() > 0)
			appDriver.navigate().back();
	}
	

}
