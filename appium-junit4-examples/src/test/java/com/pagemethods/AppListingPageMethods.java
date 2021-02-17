package com.pagemethods;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;

import app.pageobjects.AppListingPageObjects;
import app.pageobjects.HeaderPageObjects;
import common.launchsetup.BaseTest;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import common.utilities.reporting.ScreenShots;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.restassured.path.json.JsonPath;

import static common.launchsetup.BaseTest.iAppCommonMethods;

public class AppListingPageMethods {
	AppiumDriver<?> appiumDriver;
	AppListingPageObjects appListingPageObjects;
	HeaderPageObjects headerPageObjects;
	int scrollCounts = 0;
	List<String> newsHeadline = new ArrayList<String>();
	List<String> newsHeadlineTopNews = new ArrayList<String>();
	Map<String, Integer> sectionStory = new HashMap<>();
	private int windowHeight = 0;

	public AppListingPageMethods(AppiumDriver<?> appDriver) {
		appiumDriver = appDriver;
		appListingPageObjects = new AppListingPageObjects();
		PageFactory.initElements(new AppiumFieldDecorator(appiumDriver), appListingPageObjects);
		windowHeight = iAppCommonMethods.getWindowHeight(appDriver);
	}

	public AppListingPageObjects getappListingPageObjects() {
		return appListingPageObjects;
	}

	public void clickNewsImageByPosition(int position) {
		appListingPageObjects.getNewsImages().get(position).click();
	}

	public boolean clickSectionHeading(String name) {
		boolean flag = false;
		try {
			iAppCommonMethods.getElementByText(name);
			// appiumDriver.findElement(appListingPageObjects.getSectionByLocator(name)).click();
			flag = true;
		} catch (Exception e) {

		}
		return flag;
	}

	public List<MobileElement> getGenericHeadingList() {
		List<MobileElement> li = new LinkedList<>();
		try {
			li = iAppCommonMethods.getDisplayedElement(appListingPageObjects.getGenericHeadlineList());
		} catch (WebDriverException e) {

		}
		return li;
	}

	public By getSlideshowByLocator() {
		By by = null;
		try {
			by = appListingPageObjects.getSlideShowByLocator();
		} catch (WebDriverException e) {

		}
		return by;
	}

	public MobileElement getSlideShowButton() {
		MobileElement we = null;
		try {
			we = appListingPageObjects.getSlideShowButton();
		} catch (WebDriverException e) {

		}
		return we;
	}

	public List<MobileElement> getGenericHeadlineList() {
		WaitUtil.sleep(2000);
		List<MobileElement> headLinesList = null;
		try {
			headLinesList = iAppCommonMethods.getDisplayedElement(appListingPageObjects.getGenericHeadlineList());
		} catch (Exception e) {

		}
		return headLinesList;

	}

	public By getSlideShowByLocator() {
		return appListingPageObjects.getSlideShowByLocator();
	}

	public void isVideoWatchNowPresent(String headline) {
		WaitUtil.explicitWaitByVisibilityOfElement(appiumDriver, 2, (RemoteWebElement) appiumDriver
				.findElement(MobileBy.xpath(appListingPageObjects.getVideoWatchNowButton(headline.split("\"")[0]))));

	}

	public boolean navigateToHeadline(MobileElement headline) {
		boolean flag = false;
		String temp = headline.getText();
		if (temp.contains("Watch") || temp.contains("watch") || temp.contains("Briefs"))
			return true;
		try {
			isVideoWatchNowPresent(temp);
			return true;
		} catch (NoSuchElementException e) {
			// e.printStackTrace();
		}

		headline.click();
		// WaitUtil.explicitWaitByVisibilityOfElement(appiumDriver, 40, new
		// StoryPageMethods(appiumDriver).getHeadline());
		if (new StoryPageMethods(appiumDriver).isBrief()) {
			iAppCommonMethods.navigateBack(appiumDriver);
			return true;
		}
		flag = new StoryPageMethods(appiumDriver).skipWAP();

		return flag;
	}

	public boolean skipWAP() {
		return new StoryPageMethods(appiumDriver).skipWAP();

	}

	/////////
	public Map<String, MobileElement> getTopNews() {
		Map<String, MobileElement> elementHeadlineMap = new LinkedHashMap<>();
		// List<MobileElement> liNews = new LinkedList<MobileElement>();
		WaitUtil.sleep(5000);
		List<MobileElement> liNewsAll = new LinkedList<MobileElement>();
		liNewsAll.addAll(iAppCommonMethods.getDisplayedElement(appListingPageObjects.getGenericHeadlineList()));
		int size = liNewsAll.size();
		for (int i = 0; i < liNewsAll.size(); i++) {
			MobileElement headline = null;
			try {
				headline = liNewsAll.get(i);
				if (size > 1 && i == size - 1 && headline.getLocation().getY() > (7 * (windowHeight / 10))) {
					System.out.println("Not adding news");
					continue;
				}
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			}
			String headLineText = null;
			try {
				headLineText = headline.getText();
			} catch (NoSuchElementException | NullPointerException e) {
				headLineText = "null";
				e.printStackTrace();
			}

			elementHeadlineMap.put(headLineText, headline);
			System.out.println("Adding:" + headLineText);

		}

		return elementHeadlineMap;

	}

	public Map<String, MobileElement> getNewsAllSections(int storiesToVerify, String sectionName) {
		if (!sectionStory.containsKey(sectionName)) {
			System.out.println("Found value for section:" + sectionName + " value:" + storiesToVerify);
			sectionStory.put(sectionName, storiesToVerify);
		}
		Map<String, MobileElement> elementHeadlineMap = new LinkedHashMap<>();
		List<MobileElement> liNewsAll = new LinkedList<MobileElement>();
		List<MobileElement> temp = iAppCommonMethods.getDisplayedElement(getHeadlineList());
		liNewsAll.addAll(temp);
		System.out.println("Throwing away list size:" + temp.size());
		for (int i = 0; i < liNewsAll.size() && sectionStory.get(sectionName) > 0; i++) {
			int size = liNewsAll.size();
			MobileElement headline = null;
			String headLineText = null;
			try {
				headline = liNewsAll.get(i);
				headLineText = headline.getText();
				// System.out.println(headline.getLocation().getY()+"\n"+(7 *
				// (windowHeight / 10)));
				if (size > 1 && i == size - 1 && headline.getLocation().getY() > (7 * (windowHeight / 10))) {
					System.out.println("Not adding news " + headLineText);
					continue;
				}
				if (!newsHeadline.contains(headLineText) && headLineText != null) {
					newsHeadline.add(headLineText);
					elementHeadlineMap.put(headLineText, headline);
					sectionStory.put(sectionName, --storiesToVerify);
					System.out.println("After adding value for section:" + sectionName + " value storiesToVerify:"
							+ storiesToVerify + ": " + " from map storiesToVerify: " + sectionStory.get(sectionName)
							+ " " + headLineText);
				}
			} catch (NoSuchElementException | NullPointerException | IndexOutOfBoundsException e) {

			}
		}
		return elementHeadlineMap;
	}

	public Map<String, MobileElement> getNewsBeforeSection(boolean completeScrolled) {
		// List<MobileElement> liNews = new LinkedList<MobileElement>();
		Map<String, MobileElement> elementHeadlineMap = new LinkedHashMap<>();
		List<MobileElement> liNewsAll = new LinkedList<MobileElement>();
		liNewsAll.addAll(iAppCommonMethods.getDisplayedElement(appListingPageObjects.getGenericHeadlineList()));
		for (MobileElement headline : liNewsAll) {
			String headLineText = headline.getText();
			if (completeScrolled) {
				List<MobileElement> headers = iAppCommonMethods
						.getDisplayedElement(appListingPageObjects.getWidgetHeaders());
				int headerY = iAppCommonMethods.getElementsYCoordinates(headers.get(0));
				int newsY = iAppCommonMethods.getElementsYCoordinates(headline);
				if (newsY > headerY) {
					System.out.println("Not Adding:" + headLineText);
					new ScreenShots().seleniumNativeScreenshot(appiumDriver, "beforeSection");
					break;
				} else {
					if (!newsHeadline.contains(headLineText) && iAppCommonMethods.isElementDisplayed(headline)) {
						if (BaseTest.platform.contains("android")
								&& (headline.getLocation().getY() + headline.getSize().getHeight()) > 7
										* (appiumDriver.manage().window().getSize().height / 10)
								&& headLineText == null)
							continue;
						else {
							newsHeadline.add(headLineText);
							elementHeadlineMap.put(headLineText, headline);
						}
					}
				}

			} else {
				if (!newsHeadline.contains(headLineText) && iAppCommonMethods.isElementDisplayed(headline)) {
					if (BaseTest.platform.contains("android")
							&& ((headline.getLocation().getY() + headline.getSize().getHeight()) > 7
									* (appiumDriver.manage().window().getSize().height / 10) || headLineText == null)) {
						continue;
					} else {
						newsHeadline.add(headLineText);
						elementHeadlineMap.put(headLineText, headline);
					}
				}
			}

		}
		return elementHeadlineMap;
	}

	public boolean scrollUpIsNextSectionShown(boolean isCheckNeeded) {
		iAppCommonMethods.scrollUp();
		if (isCheckNeeded)
			return appListingPageObjects.getWidgetHeaders().size() > 0 ? true : false;
		else
			return false;

	}

	public int swipeUpListPage(boolean isCompletelyScrolled, int swipe) {
		int swipeCounter = swipe;
		if (!isCompletelyScrolled) {
			System.out.println("swipping up YES isCompletelyScrolled:" + isCompletelyScrolled + " with swipe:" + swipe);
			iAppCommonMethods.swipeByScreenPercentage(0.75, 0.40);
		}
		swipeCounter--;
		System.out.println("returning swipe counter:" + swipeCounter);
		WaitUtil.sleep(1000);
		return swipeCounter;
	}

	public Boolean clickNewsHeadline(MobileElement e) {
		Boolean flag = false;
		if (!e.isDisplayed())
			return null;
		try {
			e.click();
			WaitUtil.sleep(6000);
			if (BaseTest.platform.contains("android")) {
				AlertsPromptsMethods alertsPromptsMethods = new AlertsPromptsMethods(appiumDriver);
				alertsPromptsMethods.dismissCoachMark();
				alertsPromptsMethods.dismissTapToListenPopup();
				alertsPromptsMethods.clickAdCloseIcon();
				alertsPromptsMethods.dismissGoogleSignInpopup();
			}

			flag = true;
		} catch (NoSuchElementException ex) {
			flag = false;
		}
		WaitUtil.sleep(6000);
		return flag;
	}

	public String getNewsHeadline(MobileElement e) {
		return BaseTest.iAppCommonMethods.getElementText(e);
	}

	public List<MobileElement> getHeadlineList() {
		return appListingPageObjects.getNewsHeadings();
	}

	public List<MobileElement> getDisplayedNews() {
		return iAppCommonMethods.getDisplayedElement(appListingPageObjects.getGenericHeadlineList());
	}

	public List<MobileElement> getListOfHeadingsAfterSwipe() {
		List<MobileElement> returnedList = new LinkedList<MobileElement>();
		List<MobileElement> li = appListingPageObjects.getNewsHeadings();
		for (int i = 0; i < li.size(); i++) {
			int size = li.size();
			MobileElement headline = li.get(i);
			if (size > 1 && i == size - 1 && headline.getLocation().getY() > (10 * (windowHeight / 11)))
				continue;
			else
				returnedList.add(headline);
		}
		return returnedList;
	}

	public List<String> getBreakingNews() {
		return VerificationUtil.getLinkTextList(appListingPageObjects.getBreakingNews());

	}

	public Boolean verifyETprimeWidget() {
		Boolean flag = false;
		int counter = 0;
		while (counter < 20) {
			if (iAppCommonMethods.isElementDisplayed(appListingPageObjects.getEtPrimeSupportWidget())) {
				flag = true;
				break;
			}
			iAppCommonMethods.swipeByScreenPercentage(0.90, 0.45);
			WaitUtil.sleep(1000);
			counter++;
		}
		return flag;
	}

	public Boolean verifyStartFreeTrialLink() {
		Boolean flag = false;
		int counter = 0;
		while (counter < 6) {
			if (iAppCommonMethods.isElementDisplayed(appListingPageObjects.getEtPrimeWidgetStartTrial())) {
				flag = true;
				break;
			}
			iAppCommonMethods.swipeByScreenPercentage(0.90, 0.70);
			counter++;
		}
		return flag;
	}

	public String formatText(String text) {
		if (text == null)
			return text;
		text = text.replaceAll("\\\\n", "").replaceAll("[^a-zA-Z0-9]", "");
		try {
			byte[] d = text.getBytes("cp1252");
			text = new String(d, Charset.forName("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return text;
	}

	public Boolean clickHeadline(String e) {
		Boolean flag = false;
		if (!iAppCommonMethods.getElementByText(e).isDisplayed())
			return null;
		try {
			iAppCommonMethods.getElementByText(e).click();
			WaitUtil.sleep(6000);
			if (BaseTest.platform.contains("android")) {
				AlertsPromptsMethods alertsPromptsMethods = new AlertsPromptsMethods(appiumDriver);
				alertsPromptsMethods.dismissCoachMark();
				alertsPromptsMethods.dismissTapToListenPopup();
				alertsPromptsMethods.clickAdCloseIcon();
				alertsPromptsMethods.dismissGoogleSignInpopup();
			}

			flag = true;
		} catch (NoSuchElementException ex) {
			flag = false;
		}
		WaitUtil.sleep(4000);
		return flag;
	}

	public Boolean verifyHeadlineIsDisplayed(String headline) {
		Boolean flag = false;
		try {
			AlertsPromptsMethods alertsPromptsMethods = new AlertsPromptsMethods(appiumDriver);
			int counter = 5;

			while (counter > 0) {
				if (iAppCommonMethods.isElementDisplayed(iAppCommonMethods.getElementByText(headline))) {
					flag = true;
					return flag;
				}
				alertsPromptsMethods.clickRatingCloseIcon();
				iAppCommonMethods.swipeByScreenPercentage(0.8, 0.4);

				counter--;
			}
			if (!flag)
				while (counter < 4) {
					System.out.println("Scrolling down");
					iAppCommonMethods.swipeByScreenPercentage(0.4, 0.8);
					if (iAppCommonMethods.isElementDisplayed(iAppCommonMethods.getElementByText(headline))) {
						flag = true;
						return flag;
					}
					counter++;
				}
		} catch (Exception e) {
			return false;
		}

		return flag;
	}

	public HashMap<String, String> parseStoryFeedAPIData(String response) {
		HashMap<String, String> apiResponseList = new LinkedHashMap<String, String>();
		try {
			String story = JsonPath.given(response).getString("NewsItem.Story");
			String content = Jsoup.parse(story).text();
			apiResponseList.put("hl", formatText(JsonPath.given(response).getString("NewsItem.hl")));
			apiResponseList.put("imcn", formatText(JsonPath.given(response).getString("NewsItem.imcn")));
			apiResponseList.put("author", JsonPath.given(response).getString("NewsItem.dtline").split("\\|")[0].trim());
			apiResponseList.put("date", JsonPath.given(response).getString("NewsItem.dtline").split("\\|")[1].trim());
			apiResponseList.put("summary", formatText(JsonPath.given(response).getString("NewsItem.syn")));
			apiResponseList.put("content", formatText(content));
			getEmbedCount(story, apiResponseList);
		} catch (Exception e) {
			System.out.println("parseStoryFeedAPIData---" + response);
		}
		return apiResponseList;
	}

	public void getEmbedCount(String content, HashMap<String, String> apiResponseList) {
		apiResponseList.put("twitter", count(content, "<twitter") + "");
		apiResponseList.put("slideshow", count(content, "<slideshow") + "");
		// apiResponseList.put("audio", count(content, "<audio") +"");
		// apiResponseList.put("img", count(content, "<img") +"");
	}

	public static int count(String text, String find) {
		int index = 0, count = 0, length = find.length();
		while ((index = text.indexOf(find, index)) != -1) {
			index += length;
			count++;
		}
		return count;
	}

	public List<String> getItemsOutAsList(List<?> li) {
		li.removeAll(Collections.singleton(null));
		List<String> subList = new LinkedList<>();

		li.forEach(el -> {
			if (el.getClass().getSimpleName().equals("ArrayList")) {
				// subList.addAll(Arrays.asList(((String) el).split(",")));
				List<String> temp = (List<String>) el;

				subList.addAll(getItemsOutAsList(temp));

			} else {
				subList.add((String) el);
			}

		});
		return subList;
	}

	public Boolean clickString(String e) {
		Boolean flag = false;
		try {
			iAppCommonMethods.getElementByText(e).click();
			flag = true;
		} catch (NoSuchElementException ex) {
			flag = false;
		}
		return flag;
	}

}
