package com.pagemethods;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;

import app.pageobjects.HomePageObjects;
import busineslogic.BusinessLogicMethods;
import common.launchsetup.BaseTest;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import static common.launchsetup.BaseTest.iAppCommonMethods;

public class HomePageMethods {

	AppiumDriver<?> appiumDriver;
	HomePageObjects homePageObjects;

	private String currentTab;
	private String output;

	private int windowHeight;
	private Map<String, String> sectionLandingPage = new LinkedHashMap<String, String>();
	private Map<String, Boolean> colombiaSectionAd = new LinkedHashMap<String, Boolean>();
	List<String> headlinesOnUI = new ArrayList<String>();

	public HomePageMethods(AppiumDriver<?> driver) {
		appiumDriver = driver;
		homePageObjects = new HomePageObjects();
		PageFactory.initElements(new AppiumFieldDecorator(appiumDriver), homePageObjects);
		windowHeight = iAppCommonMethods.getWindowHeight(appiumDriver);
	}

	public String getCurrentTab() {
		return currentTab;
	}

	public List<MobileElement> getHomepageSlideshows() {
		List<MobileElement> li = homePageObjects.getHomepageSlideshows();

		if (li.size() > 0)
			return homePageObjects.getHomepageSlideshows();
		else
			return null;

	}

	public boolean scrollSectionToTop(String sectionName) {
		if (BaseTest.platform.contains("ios"))
			sectionName = sectionName + ",Acc_label_Header";

		boolean flag = iAppCommonMethods.scrollUpToElement(sectionName);

		if (flag) {
			MobileElement sectionHeader = iAppCommonMethods.getElementByText(sectionName);
			if (sectionHeader.getLocation().getY() > (9 * (windowHeight / 10))) {
				iAppCommonMethods.swipeByScreenPercentage(0.60, 0.60);
			}

			flag = iAppCommonMethods.scrollElementTopToHeader(iAppCommonMethods.getElementByText(sectionName),
					new HeaderPageMethods(appiumDriver).getTopTabsBar(), 1000);
		}
		return flag;
	}

	// public boolean clickNavigateBackWhileScrolling(String sectionName) {
	// String sectionValue = sectionName;
	// if (BaseTest.platform.contains("ios"))
	// sectionName = sectionName + ",Acc_label_Header";
	// boolean flag = iAppCommonMethods.scrollUpToElement(sectionName);
	// if (flag) {
	// MobileElement sectionHeader =
	// iAppCommonMethods.getElementByText(sectionName);
	// if (sectionHeader.getLocation().getY() > (9 * (windowHeight / 10))) {
	// iAppCommonMethods.swipeByScreenPercentage(0.70, 0.60);
	// }
	//
	// sectionHeader.click();
	// String landingPageHeader = new
	// HeaderPageMethods(appiumDriver).getHeaderText();
	// System.out.println(landingPageHeader);
	// sectionLandingPage.put(sectionValue, landingPageHeader);
	// appiumDriver.navigate().back();
	// flag =
	// iAppCommonMethods.scrollElementTopToHeader(iAppCommonMethods.getElementByText(sectionName),
	// 1000);
	// }
	// return flag;
	//
	// }

	public boolean clickNavigateBackWhileScrolling(String sectionName) {
		String sectionValue = sectionName;
		boolean swipeUpFlag = false;
		if (BaseTest.platform.contains("ios")) {
			sectionName = sectionName + ",Acc_label_Header";

		}
		boolean flag = iAppCommonMethods.scrollUpToElement(sectionName);

		if (flag) {
			MobileElement sectionHeader = iAppCommonMethods.getElementByText(sectionName);
			if (sectionHeader.getLocation().getY() > (9 * (windowHeight / 10))) {
				System.out.println("The element is quite down on the page, needs to be scrolled up");
				iAppCommonMethods.swipeByScreenPercentage(0.70, 0.60);
			}

			sectionHeader.click();
			// ads
			if (sectionName.equals("Markets")) {
				iAppCommonMethods.swipeByScreenPercentage(0.80, 0.20);
				swipeUpFlag = true;
			}
			WaitUtil.sleep(2000);
			boolean adFlag = new AdTechMethods(appiumDriver).isAdDisplayed("Colombia");
			WaitUtil.sleep(2000);
			System.out.println("Ad Flag : " + adFlag);
			colombiaSectionAd.put(sectionValue, adFlag);
			if (swipeUpFlag) {
				// iAppCommonMethods.swipeDown();
				iAppCommonMethods.swipeByScreenPercentage(0.20, 0.80);
			}
			//
			String landingPageHeader = new HeaderPageMethods(appiumDriver).getHeaderText();
			System.out.println("Landing Page Header " + landingPageHeader);
			sectionLandingPage.put(sectionValue, landingPageHeader);
			appiumDriver.navigate().back();
			WaitUtil.sleep(2000);
			flag = iAppCommonMethods.scrollElementTopToHeader(iAppCommonMethods.getElementByText(sectionName),
					new HeaderPageMethods(appiumDriver).getTopTabsBar(), 1000);
		}
		return flag;

	}

	public Map<String, String> getSectionLandingPage() {
		return sectionLandingPage;
	}

	public boolean clickSubSection(String subSectionName) {
		boolean flag = false;
		try {
			iAppCommonMethods.clickElement(iAppCommonMethods.getElementByText(subSectionName));
			WaitUtil.sleep(2000);
			flag = true;
		} catch (WebDriverException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public Map<String, String> getMarketsData() {
		Map<String, String> marketsData = new LinkedHashMap<String, String>();
		List<MobileElement> widgetHeaders = homePageObjects.getMarketWidgetLabels();
		List<MobileElement> ltpValues = homePageObjects.getMarketWidgetLTP();
		int size = widgetHeaders.size();
		int swipe = 0;
		if (BaseTest.platform.contains("android")) {
			while (!(size == 4) && (swipe < 4)) {
				iAppCommonMethods.swipeByScreenPercentage(0.60, 0.40);
				widgetHeaders = homePageObjects.getMarketWidgetLabels();
				ltpValues = homePageObjects.getMarketWidgetLTP();
				size = widgetHeaders.size();
				swipe++;
			}
		}
		if (!(size > 0))
			return null;
		for (int i = 0; i < size; i++) {
			String benchmarkName = "Not Found";
			String ltpValue = "Not Found";
			MobileElement elHeaderText = null;
			MobileElement elLTP = null;
			try {
				elHeaderText = widgetHeaders.get(i);
				elLTP = ltpValues.get(i);
			} catch (IndexOutOfBoundsException e) {

			}
			try {
				benchmarkName = elHeaderText.getText();
				ltpValue = elLTP.getText();

			} catch (NullPointerException e) {

			}
			if (!(benchmarkName.equals("Not Found") || ltpValue.contains("Not Found")))
				marketsData.put(benchmarkName, ltpValue);
		}

		return marketsData;

	}

	public double getValueFromApi(String benchmarkName) {
		double apiValues = 0;
		switch (benchmarkName.toUpperCase()) {
		case "SENSEX":
			apiValues = BusinessLogicMethods.getFeedSensexCurrentPrice();
			break;
		case "S&P BSE Sensex":
			apiValues = BusinessLogicMethods.getFeedSensexCurrentPrice();
			break;
		case "NIFTY 50":
			apiValues = BusinessLogicMethods.getFeedNiftyCurrentPrice();
			break;
		case "USD/INR":
			apiValues = BusinessLogicMethods.getFeedUSDINR();
			break;
		case "GOLD":
			apiValues = BusinessLogicMethods.getFeedCommodityPrice("GOLD");
			break;
		case "SILVER":
			apiValues = BusinessLogicMethods.getFeedCommodityPrice("SILVER");
			break;
		case "CRUDE OIL":
			apiValues = BusinessLogicMethods.getFeedCommodityPrice("CRUDEOIL");
			break;
		}
		System.out.println(apiValues + " for " + benchmarkName);
		return apiValues;
	}

	public Map<String, Boolean> getSectionColombiaAdFlags() {
		// TODO Auto-generated method stub
		return colombiaSectionAd;
	}

	public List<MobileElement> getAllHeadlines() {
		return homePageObjects.getHeadlinesList();
	}

	public Boolean verifyETBriefSection() {
		Boolean flag = false;
		int counter = 0;
		while (counter < 5) {
			if (iAppCommonMethods.isElementDisplayed(homePageObjects.getBriefSectionName())) {
				flag = true;
				break;
			}
			iAppCommonMethods.swipeByScreenPercentage(0.90, 0.45);
			WaitUtil.sleep(1000);
			counter++;
		}
		return flag;
	}

	public Boolean clickBriefSectionLink() {
		Boolean flag = false;
		if (!homePageObjects.getBriefSectionViewLink().isDisplayed())
			return null;
		try {
			homePageObjects.getBriefSectionViewLink().click();
			flag = true;

		} catch (NoSuchElementException ex) {
			flag = false;
		}

		return flag;
	}

	public List<MobileElement> getScreenerSectionLinkCount() {
		return homePageObjects.getscreenerSectionLinkCount();
	}

	public boolean verifyStringDisplayed(String Text) {
		try {
			if (iAppCommonMethods.getElementByText(Text) != null
					&& iAppCommonMethods.getElementByText(Text).isDisplayed()) {
				return true;
			}

		} catch (NoSuchElementException e) {
			return false;
		}
		return false;
	}

	public boolean scrolltoSection(String sectionName) {
		int swipes = 40;
		if (sectionName.isEmpty())
			return true;
		Dimension size = appiumDriver.manage().window().getSize();
		while (!verifyViewSectionLink(sectionName, false) && swipes > 0) {
			List<MobileElement> li = homePageObjects.getSectionName(appiumDriver);
			for (MobileElement e : li) {
				if (e.getText().equals(sectionName)) {
					return true;
				}
			}
			try {
				new TouchAction<>((PerformsTouchActions) appiumDriver)
						.press(new PointOption<>().withCoordinates((int) size.width / 2, (int) (size.height * 0.80)))
						.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
						.moveTo(new PointOption<>().withCoordinates((int) size.width / 2, (int) (size.height * 0.40)))
						.release().perform();

			} catch (WebDriverException e) {
			}
			swipes--;
		}
		return false;
	}

	public boolean scrollSectionTabToLeft() {
		boolean flag = false;
		try {
			iAppCommonMethods.scrollLeft(homePageObjects.getScrollView());
			flag = true;
		} catch (NoSuchElementException e) {
			System.out.println("Not able to scroll left");
		}
		return flag;
	}

	public boolean scrollQuickReadsTabsLeft() {
		boolean flag = false;
		try {
			iAppCommonMethods.scrollLeft(homePageObjects.getQuickReadScrollView());
			flag = true;
		} catch (NoSuchElementException e) {
			System.out.println("Not able to scroll left");
		}
		return flag;
	}

	public Boolean verifyViewSectionLink(String sectionname, boolean isClickNeeded) {
		Boolean flag = false;
		try {
			flag = iAppCommonMethods.isElementDisplayed(homePageObjects.getViewSectionLink());
			if (flag) {
				String section = homePageObjects.getViewSectionLink().getText();
				if (section.contains(sectionname) && isClickNeeded == false)
					return true;
				if (section.contains(sectionname) && isClickNeeded == true) {
					homePageObjects.getViewSectionLink().click();
					return true;
				}
			} else {
				iAppCommonMethods.swipeByScreenPercentage(0.8, 0.5);
				if (iAppCommonMethods.isElementDisplayed(homePageObjects.getViewSectionLink())) {
					String section = homePageObjects.getViewSectionLink().getText();
					if (section.contains(sectionname) && isClickNeeded == true) {
						homePageObjects.getViewSectionLink().click();
						return true;
					}
				}

			}
		} catch (NoSuchElementException e) {
			System.out.println("View all " + sectionname + " not displayed ");
			return false;
		}

		return false;
	}

	public boolean scrollMutualFundCategoryTabsLeft() {
		boolean flag = false;
		try {
			iAppCommonMethods.scrollLeft(homePageObjects.getMutualFundWidgetCategoryName());
			flag = true;
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public boolean scrollMutualFundSubCategoryTabsLeft() {
		boolean flag = false;
		try {
			iAppCommonMethods.scrollLeft(homePageObjects.getMFSubCategoryBar());
			flag = true;
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public boolean scrollIndustryMenuTabsLeft() {
		boolean flag = false;
		try {
			iAppCommonMethods.scrollLeft(homePageObjects.getIndustryTabBar());
			flag = true;
		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public boolean scrollDowntoSection(String sectionName) {
		Boolean flag = false;
		try {
			int counter = 4;
			while (counter > 0 && !iAppCommonMethods.isElementDisplayed(homePageObjects.getIndustryTabBar())) {
				iAppCommonMethods.swipeByScreenPercentage(0.3, 0.8);
				counter--;
			}
			if (iAppCommonMethods.isElementDisplayed(homePageObjects.getIndustryTabBar()))
				return true;
		} catch (WebDriverException e) {

		}

		return flag;
	}

	public Boolean verifyIndustrySectionTab(String sectionName, boolean isClickNeeded) {
		Boolean flag = false;
		while (!flag) {
			List<MobileElement> li = homePageObjects.getIndustrySubTab(appiumDriver);
			for (MobileElement e : li) {
				if (e.getText().equals(sectionName) && isClickNeeded == false) {
					return true;
				}
				if (e.getText().contains(sectionName) && isClickNeeded == true) {
					iAppCommonMethods.getElementByText(sectionName).click();
					return true;
				}
			}
		}
		return false;
	}

	public boolean verifyStartFreetrialWidget() {
		boolean flag = false;
		try {
			if (homePageObjects.getStartFreeTrialWidget().size() > 0) {
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean verifyStartMembershipWidget() {
		boolean flag = false;
		try {
			if (homePageObjects.getStartMembershipWidget().size() > 0) {
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public List<String> getMultimediaHeadlines() {
		try {
			List<MobileElement> ele = homePageObjects.getMultimediaHeadlines();
			for (MobileElement s : ele) {

				headlinesOnUI.add(s.getText());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return headlinesOnUI;
	}

	public Boolean verifyPrimeBanner() {
		Boolean flag = false;
		int counter = 0;
		while (counter < 15) {
			if (iAppCommonMethods.isElementDisplayed(homePageObjects.getPrimeBanner())) {
				flag = true;
				break;
			}
			iAppCommonMethods.swipeByScreenPercentage(0.90, 0.45);
			WaitUtil.sleep(1000);
			counter++;
		}
		return flag;
	}

	public boolean scrollToMoreFromPartnerStories() {
		int swipe = 0;
		if (homePageObjects.getMoreFromPartnerHeader().size() > 0) {
			return true;
		}

		else {
			while (swipe < 4) {

				iAppCommonMethods.swipeByScreenPercentage(0.60, 0.40);
				swipe++;
				if (homePageObjects.getMoreFromPartnerHeader().size() > 0) {
					return true;
				}
			}

		}
		return false;

	}

	public boolean scrollToMultimediaCard() {
		int swipe = 0;
		if (homePageObjects.getMultimediaCard().size() > 0) {
			return true;
		}

		else {
			while (swipe < 4) {
				iAppCommonMethods.swipeByScreenPercentage(0.60, 0.40);
				swipe++;
				if (homePageObjects.getMultimediaCard().size() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean scrollToPodcastCardSection() {
		int swipe = 0;
		if (homePageObjects.getPodcastCardHeadline().size() > 0) {
			return true;
		}

		else {
			while (swipe < 4) {
				iAppCommonMethods.swipeByScreenPercentage(0.60, 0.40);
				swipe++;
				if (homePageObjects.getPodcastCardHeadline().size() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean scrollToETMarketsSection() {
		int swipe = 0;
		if (homePageObjects.getETMarketsHeader().size() > 0) {
			return true;
		}

		else {
			while (swipe < 40) {
				iAppCommonMethods.swipeByScreenPercentage(0.65, 0.40);
				swipe++;
				if (homePageObjects.getETMarketsHeader().size() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean scrollToMFWidget() {
		int swipe = 0;
		if (homePageObjects.getMFWidgetParent().size() > 0) {
			return true;
		}

		else {
			while (swipe < 60) {
				iAppCommonMethods.swipeByScreenPercentage(0.75, 0.30);
				swipe++;
				if (homePageObjects.getMFWidgetParent().size() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean verifyMFSubSection(String submenu) {
		if (homePageObjects.getMFWidgetSubMenu().getText().equalsIgnoreCase(submenu))
			return true;
		else
			return false;

	}

	public boolean scrollLeft(String section) {
		boolean flag = false;
		try {
			if (section.equalsIgnoreCase("Podcast")) {
				iAppCommonMethods.scrollLeft(homePageObjects.getPodcastCard());
			} else if (section.equalsIgnoreCase("Multimedia")) {
				iAppCommonMethods.scrollLeft(homePageObjects.getMultimediacard());
			} else
				iAppCommonMethods.scrollLeft(homePageObjects.getScrollView());
			flag = true;
		} catch (NoSuchElementException e) {
			System.out.println("Not able to scroll left");
		}
		return flag;
	}

	public void display() {
		int s = homePageObjects.getHeadlinesList().size();
		List<String> a = new ArrayList<String>();
		for (int i = 0; i < s; i++) {
			a.add(homePageObjects.getHeadlinesList().get(i).getText());
		}
		System.out.println("list on frontent ::: " + a);
	}
}
