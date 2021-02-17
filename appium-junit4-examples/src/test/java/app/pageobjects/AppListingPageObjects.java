package app.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindByAllSet;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;
import io.appium.java_client.pagefactory.iOSXCUITBy;
import io.appium.java_client.pagefactory.iOSXCUITFindAll;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindByAllSet;

public class AppListingPageObjects {

	@AndroidFindBy(id = "com.et.reader.activities:id/imgView")
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeTable/**/XCUIElementTypeStaticText")
	private List<MobileElement> newsImages;

	@HowToUseLocators(iOSXCUITAutomation = LocatorGroupStrategy.ALL_POSSIBLE, androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
	@AndroidFindBy(id = "com.et.reader.activities:id/headingTV")
	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.et.reader.activities:id/headline\")")
	@AndroidFindBy(id="com.et.reader.activities:id/tv_headline")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Acc_Headline' && visible ==1")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'sl_title' && visible ==1")
	@iOSXCUITFindBy(iOSNsPredicate = "name BEGINSWITH 'NewsTitle' && visible ==1")
	@iOSXCUITFindBy(iOSNsPredicate="name=='Acc_slideshow_title' && visible==1")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='News_Headline' && visible ==1")
	private List<MobileElement> newsHeadings;

	@AndroidFindBy(id = "com.et.reader.activities:id/sectionName")
	private List<MobileElement> sectionHeaders;

	@HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
	@AndroidFindBy(id = "com.et.reader.activities:id/hScrollView")
	@AndroidFindBy(id = "com.et.reader.activities:id/headline")
	private List<MobileElement> hScrollNewsHeadline;

	@AndroidFindBy(id = "com.et.reader.activities:id/shadesView")
	private List<MobileElement> sectionTopImage;

	@iOSXCUITFindBy(iOSNsPredicate = "name = 'sl_title' && visible ==1")
	@AndroidFindBy(id = "com.et.reader.activities:id/view_slide_show")
	private MobileElement slideShowButton;

	String videoWatchBy = ".//*[contains(@resource-id,'headline') and (@text=\"%s\")]"
			+ "//following-sibling::*[contains(@resource-id,'watch_now')]";


	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.et.reader.activities:id/headline\")")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='News_Headline' && visible ==1")
	private List<MobileElement> genericHeadlineList;
	/*
	 * @HowToUseLocators(iOSXCUITAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
	 * 
	 * @iOSXCUITFindBy(iOSNsPredicate = "name = 'Acc_Headline' && visible ==1")
	 * 
	 * @iOSXCUITFindBy(iOSNsPredicate = "name = 'sl_title' && visible ==1")
	 * 
	 * @iOSXCUITFindBy(iOSNsPredicate = "name = 'lb_title' && visible ==1")
	 */

	@AndroidFindBy(id = "com.et.reader.activities:id/breaking_news")
	@iOSXCUITFindBy(accessibility = "Acc_breakingnews_headling")
	private List<MobileElement> breakingNews;

	@AndroidFindBy(id = "com.et.reader.activities:id/breakingnews_header")
	private MobileElement breakingNewsHeader;

	@AndroidFindBy(id = "com.et.reader.activities:id/change")
	private List<MobileElement> marketChange;

	@AndroidFindBy(id = "com.et.reader.activities:id/view_slide_show")
	@HowToUseLocators(iOSXCUITAutomation = LocatorGroupStrategy.CHAIN)
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeScrollView[2]/**/XCUIElementTypeTable/XCUIElementTypeCell")
	@iOSXCUITFindBy(xpath = "./XCUIElementTypeStaticText[@text='Slideshow']")
	private List<MobileElement> slideShowList;

	public List<MobileElement> getSlideShowList() {
		return slideShowList;
	}

	public List<MobileElement> getWidgetHeaders() {
		return widgetHeaders;
	}

	@AndroidFindByAllSet(value = { @AndroidFindAll(value = { @AndroidBy(id = "com.et.reader.activities:id/sectionName"),
			@AndroidBy(id = "com.et.reader.activities:id/header"), @AndroidBy(id = "com.et.reader.activities:id/lable"),
			@AndroidBy(id = "com.et.reader.activities:id/title"),
			@AndroidBy(id = "com.et.reader.activities:id/tv_title") }) })
	@iOSXCUITFindByAllSet(value = { @iOSXCUITFindAll(value = {
			@iOSXCUITBy(iOSNsPredicate = "type == 'XCUIElementTypeStaticText' && name == 'Acc_label_Header' && visible ==1"),
			@iOSXCUITBy(iOSNsPredicate = "type == 'XCUIElementTypeStaticText' && name == 'ET NOW - Live Radio' && visible ==1"),
			@iOSXCUITBy(iOSNsPredicate = "type == 'XCUIElementTypeStaticText' && name == 'ET NOW - Live Tv' && visible ==1") }) })
	private List<MobileElement> widgetHeaders;
	

	
	@iOSXCUITFindBy(accessibility = "Read. Lead. Succeed.")
	@AndroidFindByAllSet(value = { @AndroidFindAll(value = { @AndroidBy(id = "com.et.reader.activities:id/prime_widget_story_et_prime_logo"),
			@AndroidBy(id = "com.et.reader.activities:id/prime_widget_recycler_view") }) })
	private MobileElement etPrimeSupportWidget;
	
	@iOSXCUITFindBy(accessibility = "ic_logo_prime_large")
	@AndroidFindBy(id = "com.et.reader.activities:id/prime_widget_image")
	private MobileElement etPrimeWidgetImage;
	
	
	@AndroidFindBy(id = "com.et.reader.activities:id/prime_widget_category")
	private MobileElement etPrimeWidgetCategory;
	
	
	@AndroidFindBy(id = "com.et.reader.activities:id/prime_widget_synopsis")
	private MobileElement etPrimeWidgetSynopsis;
	
	
	@AndroidFindBy(id = "com.et.reader.activities:id/prime_widget_minRead")
	private MobileElement etPrimeWidgetminRead;
	
	
	@AndroidFindBy(id = "com.et.reader.activities:id/bookmark_iv")
	private MobileElement etPrimeWidgetBookmarkicon;
	
	@iOSXCUITFindBy(xpath = "//*[contains(@name,'START FREE TRIAL')]")
	@AndroidFindBy(id = "com.et.reader.activities:id/prime_widget_story_start_trial")
	private MobileElement etPrimeWidgetStartTrial;

	/////// Getters start here/////////

	public MobileElement getBreakingNewsHeader() {
		return breakingNewsHeader;
	}

	public List<MobileElement> getNewsImages() {
		return newsImages;
	}

	public List<MobileElement> getNewsHeadings() {
		return newsHeadings;
	}

	public List<MobileElement> getSectionHeaders() {
		return sectionHeaders;
	}

	public List<MobileElement> gethScrollNewsHeadline() {
		return hScrollNewsHeadline;
	}

	public List<MobileElement> getSectionTopImage() {
		return sectionTopImage;
	}

	public MobileElement getSlideShowButton() {
		return slideShowButton;
	}

	public String getVideoWatchNowButton(String headline) {
		return String.format(videoWatchBy, headline);
	}

	@Deprecated
	public By getSectionByLocator(String name) throws WebDriverException {
		return By.xpath(String.format(
				"//android.widget.TextView[@text='%s' and (contains(@resource-id,'sectionName') or contains(@resource-id,'title') or contains(@resource-id,'header')or contains(@resource-id,'lable'))]",
				name));
	}

	public By getSlideShowByLocator() {
		return By.id("com.et.reader.activities:id/view_slide_show");
	}

	public List<MobileElement> getBreakingNews() {
		return breakingNews;
	}

	public List<MobileElement> getGenericHeadlineList() {
		return genericHeadlineList;
	}

	public List<MobileElement> getMarketChange() {
		return marketChange;
	}
	
	public MobileElement getEtPrimeSupportWidget() {
		return etPrimeSupportWidget;
	}
	
	public MobileElement getEtPrimeWidgetImage() {
		return etPrimeWidgetImage;
	}
	
	public MobileElement getetPrimeWidgetCategory() {
		return etPrimeWidgetCategory;
	}
	
	public MobileElement getEtPrimeWidgetSynopsis() {
		return etPrimeWidgetSynopsis;
	}
	
	public MobileElement getEtPrimeWidgetminRead() {
		return etPrimeWidgetminRead;
	}
	
	public MobileElement getEtPrimeWidgetBookmarkicon() {
		return etPrimeWidgetBookmarkicon;
	}
	
	public MobileElement getEtPrimeWidgetStartTrial() {
		return etPrimeWidgetStartTrial;
	}

}
