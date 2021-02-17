package app.pagemethods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;

import app.pageobjects.BriefPageObjects;
import common.launchsetup.BaseTest;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import static common.launchsetup.BaseTest.iAppCommonMethods;

public class BriefPageMethods {
	private AppiumDriver<?> appiumDriver;
	private BriefPageObjects briefPageObjects;
	private boolean getAdsFlag = false;

	public BriefPageMethods(AppiumDriver<?> driver) {
		appiumDriver = driver;
		briefPageObjects = new BriefPageObjects();
		PageFactory.initElements(new AppiumFieldDecorator(driver), briefPageObjects);

	}

	public String getStoryNumber() {
		try {
			return briefPageObjects.getTotalCount().getText();
		} catch (WebDriverException e) {
			return "0";
		}
	}

	public int getTotalNumberOfStories() {
		try {
			String number = getStoryNumber();
			List<String> countStr = new ArrayList<String>(Arrays.asList(number.split("\\s*[of]+\\s")));
			return Integer.parseInt(countStr.get(1));
		} catch (Exception e) {
			return 0;
		}

	}

	/**
	 * 
	 * @param expected
	 *            value
	 * 
	 * @return map with key as no. of stories found and flag if the count
	 *         matched expected value
	 */
	public List<String> checkAllStories(int total) {
		StoryPageMethods storyPageMethods= new StoryPageMethods(appiumDriver);
		List<String> liMssg = new LinkedList<>();
		String listPageHL = "";
		boolean countFlag = false;
		boolean headlineFlag = false;
		boolean synopsisFlag = false;
		boolean storyPageFlag = false;
		int count = 0;
		for (int i = 0; i < total; i++) {
			if (briefPageObjects.getReadMore().size() > 0) {
				count++;
				System.out.println(count);
				try {
					MobileElement headline = briefPageObjects.getHeadline();
					listPageHL = headline.getText();
					headlineFlag = listPageHL.length() > 0;
					synopsisFlag = briefPageObjects.getSynopsis().getText().length() > 0;
					headline.click();
					WaitUtil.sleep(3000);
					try {
						storyPageFlag = storyPageMethods.checkHeadlineLength();
					} catch (NoSuchElementException | NullPointerException e) {

					}
					storyPageMethods.navigateBackToListPage(appiumDriver);
				} catch (NoSuchElementException | NullPointerException e) {

				}
				if (!headlineFlag)
					liMssg.add("Headline not found on listing page for brief story on position:" + count );
				if (!synopsisFlag)
					liMssg.add("Synopsis not found for story with headline " + listPageHL + ", on position:" + count
							);
				if (!storyPageFlag)
					liMssg.add("Story Page headline not found, on card headline is:" + listPageHL + " and position:"
							+ count );
			} else {
				try {
					WaitUtil.sleep(2000);
					briefPageObjects.getBriefsAdView().isDisplayed();
					getAdsFlag = true;
					count++;
				} catch (NoSuchElementException e) {
					e.printStackTrace();
				}
			}

			iAppCommonMethods.swipeByScreenPercentage(0.70, 0.10);

		}
		if (count == total) {
			countFlag = true;
		}
		if (!countFlag)
			liMssg.add("Count mismatch. Total expected slides: " + total + " and actual slides: " + count);
		return liMssg;
	}

	public boolean getAdsFlag() {
		return getAdsFlag;
	}

	public Map<Integer, Boolean> isStoryPageRendered(int total) {
		StoryPageMethods storyPageMethods=new StoryPageMethods(appiumDriver);
		Map<Integer, Boolean> hm = new HashMap<Integer, Boolean>();
		boolean flag = false;
		int count = 1;
		for (int i = 0; i <= total; i++) {
			if (briefPageObjects.getReadMore().size() > 0) {
				briefPageObjects.getReadMore().get(0).click();
				try {
					if (storyPageMethods.checkHeadlineLength())
						flag = true;
				} catch (WebDriverException e) {
					flag = false;
				} finally {
					storyPageMethods.navigateBackToListPage(appiumDriver);
					if (!flag) {
						hm.put(count, flag);
						break;
					} else
						hm.put(count, flag);
					if (count < total)
						iAppCommonMethods.swipeUp();
				}
			} else
				iAppCommonMethods.swipeUp();
			count++;
		}
		return hm;
	}

	public boolean clickCloseButton() {
		boolean flag = false;
		try {
			briefPageObjects.getBriefsCloseButton().click();
			flag = true;
		} catch (NoSuchElementException e) {

		}
		return flag;

	}

	/*** After iOS integration ***/
	public boolean isBriefPageShown() {
		return iAppCommonMethods.isElementDisplayed(briefPageObjects.getBriefPageIdentifier());
	}

	public boolean openBriefPageByName(String BriefType, MenuPageMethods menuPageMethods) {
		clickCloseButton();
		boolean flag = false;
		if (!iAppCommonMethods.isElementDisplayed(iAppCommonMethods.getElementByText(BriefType)))
			flag = menuPageMethods.clickMenuByLabel("Daily Brief");
		flag = menuPageMethods.clickMenuByLabel(BriefType);
		if (BaseTest.platform.contains("android"))
			new AlertsPromptsMethods((AppiumDriver<?>) appiumDriver).dismissCoachMark();
		return flag;
	}

}
