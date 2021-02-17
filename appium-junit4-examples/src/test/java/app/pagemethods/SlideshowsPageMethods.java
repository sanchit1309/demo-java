package app.pagemethods;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;

import app.pageobjects.SlideshowsPageObjects;
import common.launchsetup.BaseTest;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import static common.launchsetup.BaseTest.iAppCommonMethods;

/***
 * This class list methods for SlideShow page.
 *
 */
public class SlideshowsPageMethods {

	AppiumDriver<?> appDriver;
	private SlideshowsPageObjects slideshowsPageObjects = new SlideshowsPageObjects();
	private String slideCountInfo = "1/1";
	private String firstSlideInfo = "0/0";
	private List<String> slideIndexNumbers = new LinkedList<>();
	private Set<String> slideIndex = new HashSet<>();
    private HeaderPageMethods headerPageMethods;


	public SlideshowsPageMethods(AppiumDriver<?> driver) {
		appDriver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(appDriver), slideshowsPageObjects);
        headerPageMethods = new HeaderPageMethods(appDriver);

	}

	public Map<String, MobileElement> getAllSlideShowsMap() {
		Map<String, MobileElement> elementHeadlineMap = new LinkedHashMap<>();
		List<MobileElement> liNewsAll = new LinkedList<MobileElement>();
		liNewsAll.addAll(slideshowsPageObjects.getListPageSlideShows());
		for (int i = 0; i < liNewsAll.size(); i++) {
			MobileElement headline = null;
			try {
				headline = liNewsAll.get(i);
			} catch (IndexOutOfBoundsException e) {

			}
			String headLineText = null;
			try {
				headLineText = headline.getText();
			} catch (NoSuchElementException | NullPointerException e) {
				headLineText = "null";
			}

			elementHeadlineMap.put(headLineText, headline);
			System.out.println("Adding:" + headLineText);

		}

		return elementHeadlineMap;

	}

	public boolean verifyTotalSlideShowCount() {
		slideIndex = new HashSet<>();
		slideCountInfo = "0/0";
		firstSlideInfo = "0/0";
		int totalSlide = 0;
		int swipeCounter = 0;
		boolean flag = false;
		Set<String> slideNumberInfo = new HashSet<>();
		try {
			firstSlideInfo = numberOfSlide().iterator().next();
			System.out.println(firstSlideInfo);
			totalSlide = Integer.parseInt(firstSlideInfo.split("\\/")[1].trim());
			if (firstSlideInfo.equals("0/0"))
				return flag;
			Boolean isNextShown = false;
			flag = (slideNumberInfo.contains(totalSlide + "/" + totalSlide));
			while (swipeCounter < 40) {
				AlertsPromptsMethods alertsPromptsMethods = new AlertsPromptsMethods(appDriver);
				alertsPromptsMethods.dismissGoogleSignInpopup();
				if (!isNextShown && !flag) {
					isNextShown = swipeIsNextPresent();
					slideNumberInfo = numberOfSlide();
					flag = (slideNumberInfo.contains(totalSlide + "/" + totalSlide));
					swipeCounter++;
				}
				if (isNextShown && !flag) {
					BaseTest.iAppCommonMethods.swipeByScreenPercentage(0.80, 0.50);
					slideNumberInfo = numberOfSlide();
					flag = (slideNumberInfo.contains(totalSlide + "/" + totalSlide));
				} else if (flag)
					break;

			}
		} catch (Exception e) {

		}
		return flag;
	}

	/**
	 * Method returns SlideShow Header
	 * 
	 * @return
	 */
	public String getSlideshowsHeader() {
		return slideshowsPageObjects.getSlideshowHeader().getText();
	}

	public List<String> getSlideNumberText(List<MobileElement> me) {
		List<String> slideNumbers = new LinkedList<>();
		me.forEach(slideCount -> {
			slideNumbers.add(slideCount.getText().replaceAll("\\_number", "").replaceAll("\\_", "\\/"));
			System.out.println(slideNumbers);
		});

		return slideNumbers;
	}

	public Set<String> numberOfSlide() {
		List<MobileElement> slides = slideshowsPageObjects.getSlideNumber();
		slides.forEach(slide -> {
			slideIndexNumbers = getSlideNumberText(slides);
			slideIndex.addAll(slideIndexNumbers);
		});
		return slideIndex;
	}

	public String getLastSlideInfo() {
		return slideCountInfo;
	}

	public String getTotalSlideInfo() {
		return firstSlideInfo;
	}

	public boolean swipeIsNextPresent() {
		boolean flag = false;
		BaseTest.iAppCommonMethods.swipeUp();
		try {
			slideshowsPageObjects.getNextSlideShowStrip().isDisplayed();
			flag = true;

		} catch (NoSuchElementException e) {

		}
		return flag;
	}

	public void navigateBack() {
		BaseTest.iAppCommonMethods.swipeDown();
		BaseTest.iAppCommonMethods.navigateBack(appDriver);

	}

	public boolean scrollToNextSlideShow() {
		boolean flag = false;
		int counter = 0;
		do {
			try {
				slideshowsPageObjects.getNextSlideShowStrip().isDisplayed();
				flag = true;
			} catch (NoSuchElementException e) {

			}
			if (flag)
				break;

			BaseTest.iAppCommonMethods.swipeUp();
			counter++;

		} while (!flag && counter < 20);
		return flag;
	}

	public String getHeadlineText() {
		try {
			WaitUtil.sleep(2000);
			return slideshowsPageObjects.getSlideHeadline().getText();
		} catch (Exception e) {
			return "";
		}
	}

	public List<MobileElement> getAllSlideShows() {
		return slideshowsPageObjects.getSlideshowArticles();
	}
	
	public boolean clickTopTab(String text) {
		int counter = 0;
		if (BaseTest.platform.contains("android"))
			text = text.toUpperCase();
		try {
			while (!iAppCommonMethods.isElementDisplayed(iAppCommonMethods.getElementByText(text)) && counter < 10) {
				headerPageMethods.scrollTopTabsLeft();
				counter++;
			}
			iAppCommonMethods.getElementByText(text).click();
			WaitUtil.sleep(2000);
			return true;
		} catch (WebDriverException | NullPointerException e) {
			return false;

		}

	}
	
	public boolean isTabDisplayed(String text) {
		if (BaseTest.platform.contains("android"))
			text = text.toUpperCase();
		return iAppCommonMethods.isElementDisplayed(iAppCommonMethods.getElementByText(text));
	}
	

}
