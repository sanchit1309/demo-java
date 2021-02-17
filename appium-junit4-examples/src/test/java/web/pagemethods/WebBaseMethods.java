package web.pagemethods;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;

import static common.launchsetup.BaseTest.driver;

public class WebBaseMethods {

	private static String parentWindow;

	public static By getLocatorByWebElement(WebElement element) {

		By by = null;
		boolean isDone = false;
		String s = element.toString();
		String type = "";
		String locatorValue = "";
		if (s.equals(null)) {
			throw new NullPointerException();
		}
		try {
			String temp[] = s.split("->")[1].split(":");
			type = temp[0].trim();
			// System.out.println("type :" + type);
			locatorValue = temp[1].substring(0, temp[1].length() - 1);
			// System.out.println("locatorValue:" + locatorValue);
			isDone = true;
		} catch (Exception e) {
			isDone = false;
		}
		if (isDone) {
			if (type.equalsIgnoreCase("css selector")) {
				by = By.cssSelector(locatorValue);
			} else if (type.equalsIgnoreCase("id")) {
				by = By.id(locatorValue);
			} else if (type.equalsIgnoreCase("xpath")) {
				by = By.xpath(locatorValue);
			} else if (type.equalsIgnoreCase("name")) {
				by = By.name(locatorValue);
			} else if (type.equalsIgnoreCase("partial link text")) {
				by = By.partialLinkText(locatorValue);
			} else if (type.equalsIgnoreCase("link text")) {
				by = By.linkText(locatorValue);
			} else if (type.equalsIgnoreCase("tag name")) {
				by = By.tagName(locatorValue.trim());
			}
		}
		return by;
	}

	/*
	 * Use this method as an overriden method isDisplayed
	 */
	public static boolean isDisplayed(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException | StaleElementReferenceException exception) {
			System.out.println("Required WebElement could not be seen or page got refershed");
			Reporter.log("Required WebElement could not be seen.");
			return false;
		}
	}

	/** Use this method to check if your locator is present now */
	public static boolean isPresent(WebElement element, int maxWaitSec) {
		try {

			waitFor(ExpectedConditions.elementToBeClickable(element), maxWaitSec);
		} catch (StaleElementReferenceException exception) {
			driver.navigate().refresh();
			waitFor(ExpectedConditions.elementToBeClickable(element), maxWaitSec);
		} catch (Exception exception) {

			System.out.println("Required WebElement " + element.toString() + " could not be found after waiting "
					+ maxWaitSec + " seconds.");
			Reporter.log("Required WebElement could not be found after waiting " + maxWaitSec + " seconds.");

			// exception.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * steps to check that element is not visible
	 *
	 * @param element
	 * @return boolean
	 */
	public static boolean ifElementNotVisible(WebElement element) {

		try {
			element.isDisplayed();
			element.isEnabled();
			return false;
		} catch (NoSuchElementException e) {
			// Returns true because the element is not present in DOM. The
			// try block checks if the element is present but is invisible.
			return true;
		} catch (StaleElementReferenceException e) {
			// Returns true because stale element reference implies that element
			// is no longer visible.
			return true;
		}
	}

	public static boolean isNotDisplayed(WebElement element, int maxWaitSec) {
		try {
			int count = maxWaitSec * 10;
			while (count > 0) {

				if (ifElementNotVisible(element)) {
					break;
				}
				Thread.sleep(100);
				count--;
			}
		} catch (Exception exception) {
			System.out.println("Required WebElement " + element.toString() + " could not be found after waiting "
					+ maxWaitSec + " seconds.");
			Reporter.log("Required WebElement could not be found after waiting " + maxWaitSec + " seconds.");
			return false;
		}

		return true;
	}

	/*
	 * Use this method to override the isDisplayed method with waiting time
	 */
	public static boolean isDisplayed(WebElement element, int maxWaitSec) {
		try {
			waitFor(ExpectedConditions.visibilityOf(element), maxWaitSec);
		} catch (Exception exception) {
			System.out.println("Required WebElement " + element.toString() + " could not be found after waiting "
					+ maxWaitSec + " seconds.");
			Reporter.log("Required WebElement could not be found after waiting " + maxWaitSec + " seconds.");
			System.out.println(exception.getMessage());
			return false;
		}
		return true;
	}

	public static boolean isDisplayedList(List<WebElement> element, int maxWaitSec) {
		try {
			waitForList(ExpectedConditions.visibilityOfAllElements(element), maxWaitSec);
		} catch (Exception exception) {
			System.out.println("Required WebElement " + element.toString() + " could not be found after waiting "
					+ maxWaitSec + " seconds.");
			Reporter.log("Required WebElement could not be found after waiting " + maxWaitSec + " seconds.");
			exception.printStackTrace();
			return false;
		}
		return true;
	}

	public static void navigateToUrl(WebDriver driver, String url) throws JavascriptException {
		if (!url.equals(driver.getCurrentUrl())) {
			navigateTimeOutHandle(driver, url, 20);
		}

		// return driver.findElement(By.cssSelector("html >
		// body")).getText().trim();
	}

	public static void moveToElement(WebElement element) {
		if (element == null)
			return;
		try {
			Actions act = new Actions(driver);
			act.moveToElement(element).build().perform();
			WaitUtil.sleep(5000);
		} catch (NoSuchElementException e) {

		}
	}

	public static void moveToElementAndClick(WebElement element) {
		Actions act = new Actions(driver);
		act.moveToElement(element).click().perform();
	}

	/**
	 * switch to child window when window counts gets increased and it will poll
	 * for every 100 mili second
	 *
	 * @throws InterruptedException
	 */
	public static void switchToChildWindow(int maxWaitSec) {
		int countMs = maxWaitSec * 1000;
		parentWindow = driver.getWindowHandle();
		while (countMs > 0) {
			if (driver.getWindowHandles().size() > 1)
				break;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			countMs -= 100;
		}
		Set<String> openWindows = driver.getWindowHandles();

		for (String window : openWindows) {

			if (!(window.equalsIgnoreCase(parentWindow)))
				driver.switchTo().window(window);
			if (Platform.getCurrent().toString().contains("WIN"))
				driver.manage().window().maximize();
			WaitUtil.waitForLoad(driver);
		}
	}

	public static void switchChildIfPresent() {
		parentWindow = driver.getWindowHandle();
		Set<String> openWindows = driver.getWindowHandles();
		if (openWindows.size() > 1) {
			for (String window : openWindows) {

				if (!(window.equalsIgnoreCase(parentWindow)))
					driver.switchTo().window(window);
				WaitUtil.sleep(1000);
				if (Platform.getCurrent().toString().contains("WIN"))
					driver.manage().window().maximize();

			}
		}
	}

	public static void switchToLastWindow() {
		Set<String> openWindows = driver.getWindowHandles();
		WaitUtil.sleep(2000);
		for (String window : openWindows) {
			driver.switchTo().window(window);
			WaitUtil.sleep(2000);
		}

	}

	/**
	 * To be called after switchToChildWindow as parent window is set in the
	 * function
	 */
	public static void switchToParentWindow() {
		try {
			driver.switchTo().window(parentWindow);
		} catch (WebDriverException | NullPointerException nsw) {

		}
	}

	public static void closeAllExceptParentWindow() {
		Set<String> openWindows = driver.getWindowHandles();
		Iterator<String> windowIterator = openWindows.iterator();
		String parentWindow = windowIterator.next();
		for (String handle : openWindows) {
			if (!(handle.equalsIgnoreCase(parentWindow))) {
				driver.switchTo().window(handle);
				driver.close();
			}
		}
		driver.switchTo().window(parentWindow);
	}

	public static void scrollMultipleTimes(int counter, String direction, int pixels) {

		while (counter > 0) {
			if (direction.equalsIgnoreCase("Top")) {
				try {
					((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-" + pixels + ")");
					System.out.println("executed script");
				} catch (JavascriptException e) {
					e.printStackTrace();
				}
			}

			else {
				try {
					((JavascriptExecutor) driver).executeScript("window.scrollBy(0," + pixels + ")");
				} catch (JavascriptException e) {
					e.printStackTrace();

				}
			}

			WaitUtil.sleep(2000);
			counter--;

		}
	}

	public static void scrollElementIntoViewUsingJSE(WebElement ele) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", ele);
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,150);", "");
	}

	public static boolean JSHoverOver(WebElement targetElement) {
		boolean flag = false;
		try {
			String javaScript = "var evObj = document.createEvent('MouseEvents');"
					+ "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
					+ "arguments[0].dispatchEvent(evObj);";
			((JavascriptExecutor) driver).executeScript(javaScript, targetElement);
			flag = true;
		} catch (WebDriverException e) {

		}
		return flag;
	}

	public static String getText(WebDriver driver, WebElement element) {
		return (String) ((JavascriptExecutor) driver).executeScript("return jQuery(arguments[0]).text();", element);
	}

	/**
	 * steps to switch to parent only when every child gets closed
	 *
	 * @throws InterruptedException
	 */
	public static void switchToParentWindowWithZeroChild(int maxWaitSec) throws InterruptedException {
		int count = maxWaitSec * 10;
		while (count > 0) {

			if (driver.getWindowHandles().size() < 2) {
				break;
			}
			Thread.sleep(100);
			count--;
		}

		driver.switchTo().window(parentWindow);
	}

	public static void switchToParentClosingChilds() {
		Set<String> windowHandles = driver.getWindowHandles();
		Iterator<String> handlesIterator = windowHandles.iterator();
		String parentWindow = handlesIterator.next();
		while (handlesIterator.hasNext()) {
			driver.switchTo().window(handlesIterator.next());
			driver.close();
		}
		driver.switchTo().window(parentWindow);
	}

	public static void switchToParentClosingChildsAcceptAlert() {
		Set<String> windowHandles = driver.getWindowHandles();
		Iterator<String> handlesIterator = windowHandles.iterator();
		String parentWindow = handlesIterator.next();
		while (handlesIterator.hasNext()) {
			driver.switchTo().window(handlesIterator.next());
			try {
				Alert alert = driver.switchTo().alert();
				alert.accept();
			} catch (WebDriverException e) {
				// do nothing
			}
			driver.close();

		}
		driver.switchTo().window(parentWindow);
	}

	public static void scrollUpDownUsingJSE(int x, int y, WebDriver driver) {
		String a = "" + x;
		String b = "" + y;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(" + a + "," + b + ")", "");
	}

	public static void scrollUpDownUsingJSE(int x, int y) {
		String a = "" + x;
		String b = "" + y;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(" + a + "," + b + ")", "");
	}

	public static boolean clickElementUsingJSE(WebElement element) {
		boolean flag = false;
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			flag = true;
		} catch (NoSuchElementException | TimeoutException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static String getTextUsingJSE(WebElement element) {
		String text = "";
		try {
			text = (String) ((JavascriptExecutor) driver).executeScript("return jQuery(arguments[0]).text();", element);
		} catch (WebDriverException e) {

		}
		return text;
	}

	public static List<String> getListTextUsingJSE(List<WebElement> li) {
		List<String> textLi = new ArrayList<>();
		try {
			li.forEach(action -> {
				textLi.add(((String) ((JavascriptExecutor) driver).executeScript("return jQuery(arguments[0]).text();",
						action)).trim());
			});
		} catch (WebDriverException e) {

		}
		return textLi;
	}

	public static String getListTextUsingJSE(WebElement li) {
		String textLi = null;
		try {

			textLi = ((String) ((JavascriptExecutor) driver).executeScript("return jQuery(arguments[0]).text();", li));

		} catch (WebDriverException e) {

		}
		return textLi;
	}

	public static String getHrefUsingJSE(WebElement element) {
		String href = "";
		try {
			href = (String) ((JavascriptExecutor) driver).executeScript("return jQuery(arguments[0]).attr('href');",
					element);
			href = href.replaceAll("[\\s]+", "%20");

			href = (href.contains("economictimes.") || href.contains("://")) ? href
					: (href.startsWith("/") ? BaseTest.baseUrl + href : BaseTest.baseUrl + "/" + href);

		} catch (WebDriverException e) {
			// TODO: handle exception
		}
		return href;
	}

	public static List<String> getListHrefUsingJSE(List<WebElement> li) {
		if (li.size() == 0)
			return new LinkedList<>();
		scrollToTop();
		List<String> textLi = new ArrayList<>();
		li.forEach(action -> {
			try {
				String href = (String) ((JavascriptExecutor) driver)
						.executeScript("return jQuery(arguments[0]).attr('href');", action);
				href = href.trim().replaceAll("[\\s]+", "%20");

				href = (href.contains("economictimes.") || href.contains("://")) ? href
						: (href.startsWith("/") ? BaseTest.baseUrl + href : BaseTest.baseUrl + "/" + href);
				textLi.add(href);
			} catch (Exception e) {
				e.printStackTrace();
				// do nothing
			}
		});
		return textLi;
	}

	public static List<String> getListAnyAttributeUsingJSE(List<WebElement> li, String attributeName) {
		List<String> textLi = new ArrayList<>();
		li.forEach(action -> {
			textLi.add((String) ((JavascriptExecutor) driver)
					.executeScript("return jQuery(arguments[0]).attr('" + attributeName + "');", action));
		});
		return textLi;
	}

	public static List<String> getListAttributeUsingJSE(List<WebElement> li, String attribute) {
		List<String> textLi = new ArrayList<>();
		li.forEach(action -> {
			textLi.add((String) ((JavascriptExecutor) driver)
					.executeScript("return jQuery(arguments[0]).attr('" + attribute + "');", action));
		});
		return textLi;
	}

	/**
	 * Scrolls the Current WebPage to the bottom
	 */
	public static void scrollToBottom() {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
			WaitUtil.sleep(4000);
		} catch (Exception e) {
			// do nothing
		}
	}

	/**
	 * Scrolls To the Top of the page
	 */
	public static void scrollToTop() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0);");
	}

	public static void scrollWindow(Integer index) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0," + index + ")", "");
	}

	public static void scrollToMiddle() {
		((JavascriptExecutor) driver).executeScript(
				"window.scrollTo(0,Math.max(document.documentElement.scrollHeight/2,document.body.scrollHeight,document.documentElement.clientHeight/2));");
	}

	public static void scrollFixedHeightMultipleTimes(int counter, String direction) {

		while (counter > 0) {
			if (direction.equalsIgnoreCase("Top")) {
				try {
					((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-($(window).height()/2))");
				} catch (JavascriptException e) {

				}
			}

			else {
				try {
					((JavascriptExecutor) driver).executeScript("window.scrollBy(0,($(window).height()/2))");
				} catch (JavascriptException e) {

				}
			}

			WaitUtil.sleep(2000);
			counter--;

		}
	}

	public static void scrollingToElementofAPage(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
	}

	// clear user auth data and browser cookie

	public static void clearBrowserSessionCookie(WebDriver driver) {
		if (driver != null) {
			// driver.manage().deleteAllCookies();
			// ((JavascriptExecutor)
			// driver).executeScript("javascript:localStorage.clear();");
			driver.manage().deleteAllCookies();
			driver.navigate().refresh();
		}
	}

	public static String getNumericStringFromAlphaNumeric(String alphaNumericString) {
		String numericString = null;
		if (!"".equals(alphaNumericString) && alphaNumericString != null) {
			numericString = alphaNumericString.replaceAll("[^\\d]", "");
		}
		return numericString;

	}

	/**
	 * To fetch the value of switches present on the web Page
	 *
	 * @param WebElementId
	 * @return
	 */
	public static String getAttrbuteValuefromPageSource(String WebElementId) {
		try {
			String text = driver.findElement(By.id(WebElementId)).getAttribute("value");
			return text;
		} catch (NoSuchElementException e) {
			return null;
		}

	}

	/*
	 * Private method, not to be used outside
	 */
	private static void waitFor(ExpectedCondition<WebElement> condition, Integer timeOutInSeconds) {

		timeOutInSeconds = timeOutInSeconds != null ? timeOutInSeconds : 5;
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(condition);
	}

	private static void waitForList(ExpectedCondition<List<WebElement>> condition, Integer timeOutInSeconds) {
		timeOutInSeconds = timeOutInSeconds != null ? timeOutInSeconds : 5;
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(condition);
	}

	public static void refreshTimeOutHandle() {
		try {
			driver.navigate().refresh();
		} catch (WebDriverException e) {
			driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
		}
	}

	public static void navigateBackTimeOutHandle() {
		try {
			driver.navigate().back();
		} catch (WebDriverException e) {
			driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
		}
	}

	public static void navigateTimeOutHandle(String url) throws JavascriptException {
		try {
			driver.get(url);
		} catch (TimeoutException e) {
			try {
				driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
			} catch (WebDriverException we) {

			}
		} catch (NoSuchSessionException e) {

		}
	}

	public static boolean navigateTimeOutHandle(WebDriver driver, String url, long pageloadTimeSec) {
		boolean flag = false;
		try {
			driver.manage().timeouts().pageLoadTimeout(pageloadTimeSec, TimeUnit.SECONDS);
			if (url.startsWith("http")) {
				driver.get(url);
				flag = true;
			}
		} catch (TimeoutException e) {
			flag = false;
			try {
				driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
			} catch (Exception to) {
				flag = false;
			}

		}
		return flag;
	}

	public static String getUrlPage(WebDriver driver) {
		String url = "";
		try {
			url = driver.getCurrentUrl();
		} catch (TimeoutException e) {

		}
		return url;
	}

	public static void setClipboardContents(String text) {
		StringSelection stringSelection = new StringSelection(text);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}

	public static List<WebElement> getDisplayedItemFromList(List<WebElement> li) {
		List<WebElement> displayedElement = new LinkedList<>();
		int exceptionCount = 0;
		for (int i = 0; i < li.size();) {
			try {
				WebElement el = li.get(i);

				if (el.isDisplayed())
					displayedElement.add(el);
				i++;
			} catch (StaleElementReferenceException e) {
				System.out.println("Displayed Element Stale Element Exception");
				exceptionCount++;
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Index not found");
				exceptionCount++;
			}
			if (exceptionCount > 2)
				break;
		}
		return displayedElement;
	}

	public static boolean acceptIfAlertPresent() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			return true;
		} // try
		catch (NoAlertPresentException Ex) {
			return false;
		} // catch
	}

	public static boolean declineIfAlertPresent() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
			return true;
		} // try
		catch (NoAlertPresentException Ex) {
			return false;
		} // catch
	}

	public static void stopPageLoad() {
		driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
	}

	public static String getCookieValue(String cookieName) {
		return driver.manage().getCookieNamed(cookieName).getValue();
	}

	public static void slowScroll(int n) {
		for (int i = 0; i < n; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("javascript:window.scrollBy(0,200)");
			WaitUtil.sleep(2000);
		}

	}

	public static void slowScrollToElement(WebElement ele, int maxScroll) {
		for (int i = 0; i < maxScroll; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("javascript:window.scrollBy(0,200)");
			WaitUtil.sleep(2000);
			try {
				if (isPresent(ele, 2)) {
					break;
				}
			} catch (NoSuchElementException e) {
				e.printStackTrace();
			}
		}
	}

	public static DateTime convertDateMethod(String date) {
		String checkDate = date.split("2020")[0];
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(checkDate);
		matcher.find();
		if (matcher.group().length() == 1) {
			date = date.replaceFirst(matcher.group(), "0" + matcher.group());
		}

		date = date.replace("(IST)", "").replace("IST", "");
		DateTimeParser[] parsers = { DateTimeFormat.forPattern("MMMddyyyyhh:mma").getParser(),
				DateTimeFormat.forPattern("ddmmyyyyhh:mma").getParser(),
				DateTimeFormat.forPattern("ddmmyyyyHH:mm").getParser(),
				DateTimeFormat.forPattern("MMMddyyyyHH:mm").getParser(),
				DateTimeFormat.forPattern("MMMddyyyy").getParser(),
				DateTimeFormat.forPattern("ddMMMyyyyHH:mm").getParser(),
				DateTimeFormat.forPattern("ddMMMyyyyhh:mma").getParser(),
				DateTimeFormat.forPattern("ddMMyyyy").getParser(),
				DateTimeFormat.forPattern("EEEMMMddyyyy").getParser(),
				DateTimeFormat.forPattern("EEEMMMddyyyyhh:mma").getParser(),
				DateTimeFormat.forPattern("yyyy-mm-dd'T'hh:mm:ss+00:00").getParser() };
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(null, parsers).toFormatter();
		DateTime myDate = formatter.parseDateTime(date);
		return myDate;

	}

	public static void switchToFrame(WebElement frameElement) {
		driver.switchTo().frame(frameElement);
	}

	public static void switchToDefaultContext() {
		driver.switchTo().defaultContent();
	}

	public static int getFrameSizeHeight() {
		return driver.manage().window().getSize().getHeight();
	}

	public static int getFrameSizeWidth() {
		return driver.manage().window().getSize().getWidth();
	}

	public static String getTitle() {
		String title = "";
		try {
			title = driver.findElement(By.tagName("title")).getText();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return title;
	}

	public static int getWindowsSize() {
		return driver.getWindowHandles().size();
	}

	public static void removeInterstitial() {
		WaitUtil.sleep(2000);
		if (driver.getCurrentUrl().contains("interstitial"))
			if (BaseTest.platform.equalsIgnoreCase("Web"))
				ETSharedMethods.clickETLinkInterstitialPage();
		if (BaseTest.platform.equalsIgnoreCase("WAP"))
			WaitUtil.sleep(6000);

	}
	
	public static boolean isElementVisible(WebElement element) {
		boolean result;
		WaitUtil.turnOffImplicitWaits(driver);
		try {
			if (element.isDisplayed()) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			result = false;
		}
		WaitUtil.turnOnImplicitWaits(driver);
		return result;
	}

	public static boolean selectDropDownByValue(String value, WebElement element)
	{
		boolean result = false;
		try {
			Select select = new Select(element);
			select.selectByValue(value);
			result = true;
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}

		return result;
	}

	public static boolean scrollTillBottomOfPageIsReached(){
		JavascriptExecutor javascript = (JavascriptExecutor) driver;

		long last_height = (long) javascript.executeScript("return document.body.scrollHeight");
		WebBaseMethods.scrollToBottom();
		long new_height =  (long) javascript.executeScript("return document.body.scrollHeight");
		int count = 0;
		while(last_height != new_height && count <60)
		{
			last_height = (long) javascript.executeScript("return document.body.scrollHeight");
			WebBaseMethods.scrollToBottom();
			WaitUtil.sleep(6000);
			new_height =  (long) javascript.executeScript("return document.body.scrollHeight");
			count ++;
		}
		if(last_height == new_height)
			return true;
		else
			return false;
	} 
	
	public static boolean isElementVisible(WebElement element, int maxWaitSec) {
		try {
			driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
			waitFor(ExpectedConditions.elementToBeClickable(element), maxWaitSec);
		} catch (StaleElementReferenceException exception) {
			driver.navigate().refresh();
			waitFor(ExpectedConditions.elementToBeClickable(element), maxWaitSec);
		} catch (Exception exception) {

			System.out.println("Required WebElement " + element.toString() + " could not be found after waiting "
					+ maxWaitSec + " seconds.");
			Reporter.log("Required WebElement could not be found after waiting " + maxWaitSec + " seconds.");

			// exception.printStackTrace();

			return false;
		}
		driver.manage().timeouts().implicitlyWait(Long.parseLong(Config.fetchConfigProperty("ElementWaitTime")),
				TimeUnit.SECONDS);
		return true;
	}
	
	public static void clearSessionStorage() {
		JavascriptExecutor javascript = (JavascriptExecutor) driver;
		javascript.executeScript(String.format("window.sessionStorage.clear();"));
	}
	
	public static String getIntegerValueFromAlphanumericString(String str)
	{
		String integerValue="";
		try {
			for(int i=0;i<str.length()-1;i++)
			{
				if(Character.isDigit(str.charAt(i)))
						integerValue=integerValue+ str.charAt(i);
			}
			
		} catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return integerValue;
	}
	
	public static String getRandomCharactersUptoLength(int length) {
		// chose a Character random from this String 
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "0123456789"
				+ "abcdefghijklmnopqrstuvxyz"; 
		String str ="";
		for (int i = 0; i < length; i++) { 
			// generate a random number between 
			// 0 to AlphaNumericString variable length 
			int index 
			= (int)(AlphaNumericString.length() 
					* Math.random()); 

			// add Character one by one in end of sb 
			str= str + AlphaNumericString 
					.charAt(index); 
		} 
		return str;

	}
	
	public static List<String> checkIfListIsUnique(List<String> hrefList) {
		List<String> dupLinks = new LinkedList<String>();
		try {
			dupLinks = VerificationUtil.isListUnique(hrefList);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return dupLinks;
	}

	public static List<String> getAllErrorLinks(List<String> hrefList) {
		List<String> errorLinks = new LinkedList<String>();
		System.out.println(hrefList);
		System.out.println(hrefList.size());
		hrefList.forEach(href -> {
			try {
				int response = HTTPResponse.checkResponseCode(href);
				System.out.println(href + "-------" + response);
				if (!(response == 200)) {
					errorLinks.add(href);
				}
			} catch (Exception ee) {
				ee.printStackTrace();
				errorLinks.add(href);
			}
		});
		return errorLinks;
	}
}