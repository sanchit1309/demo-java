package app.pageobjects;

import java.util.List;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
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

public class HomePageObjects {
	@HowToUseLocators(iOSXCUITAutomation = LocatorGroupStrategy.ALL_POSSIBLE, androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.et.reader.activities:id/headline\")")
	@AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.et.reader.activities:id/headingTV\")")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'Acc_Headline' && visible ==1")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'NewsTitle_Acc_Headline' && visible ==1")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'sl_title' && visible ==1")
	@iOSXCUITFindBy(iOSNsPredicate = "name = 'lb_title' && visible ==1")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='News_Headline' && visible ==1")
	private List<MobileElement> headlinesList;

	@AndroidFindBy(id = "com.et.reader.activities:id/view_slide_show")
	@HowToUseLocators(iOSXCUITAutomation = LocatorGroupStrategy.CHAIN)
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeScrollView[2]/**/XCUIElementTypeTable/XCUIElementTypeCell")
	@iOSXCUITFindBy(xpath = "./XCUIElementTypeStaticText[@text='Slideshow']")
	private List<MobileElement> slideShowList;

	@AndroidFindByAllSet(value = { @AndroidFindAll(value = { @AndroidBy(id = "com.et.reader.activities:id/sectionName"),
			@AndroidBy(id = "com.et.reader.activities:id/header"), @AndroidBy(id = "com.et.reader.activities:id/lable"),
			@AndroidBy(id = "com.et.reader.activities:id/title"),
			@AndroidBy(id = "com.et.reader.activities:id/tv_title") }) })
	@iOSXCUITFindByAllSet(value = { @iOSXCUITFindAll(value = {
			@iOSXCUITBy(iOSNsPredicate = "type == 'XCUIElementTypeStaticText' && name == 'Acc_label_Header' && visible ==1"),
			@iOSXCUITBy(iOSNsPredicate = "type == 'XCUIElementTypeStaticText' && name == 'ET NOW - Live Radio' && visible ==1"),
			@iOSXCUITBy(iOSNsPredicate = "type == 'XCUIElementTypeStaticText' && name == 'ET NOW - Live Tv' && visible ==1") }) })
	private List<MobileElement> widgetHeaders;

	@AndroidFindBy(id = "com.et.reader.activities:id/imgType")
	@iOSXCUITFindBy(iOSNsPredicate = "name=='News_Headline' && visible ==1")
	private List<MobileElement> homepageSlideshows;

	@iOSXCUITFindBy(iOSNsPredicate = "name=='Acc_et_market_title_label' && visible == 1")
	@AndroidFindBy(id = "com.et.reader.activities:id/name")
	private List<MobileElement> marketWidgetLabels;

	@iOSXCUITFindBy(iOSNsPredicate = "name=='Acc_et_market_change_label' && visible ==1")
	@AndroidFindBy(id = "com.et.reader.activities:id/currentValue")
	private List<MobileElement> marketWidgetLTP;

	@iOSXCUITFindBy(iOSNsPredicate = "name=='Acc_et_market_percent_label' && visible ==1")
	@AndroidFindBy(id = "com.et.reader.activities:id/change")
	private List<MobileElement> marketChangeAbs;

	@AndroidFindBy(id = "com.et.reader.activities:id/lbLabelBrief")
	private MobileElement briefSectionName;

	@AndroidFindBy(id = "com.et.reader.activities:id/more_stories_tv")
	private MobileElement briefSectionViewLink;

	@AndroidFindBy(id = "com.et.reader.activities:id/headline_tv")
	private List<MobileElement> screenerSectionLinkCount;

	@AndroidFindBy(id = "com.et.reader.activities:id/hScrollView")
	private MobileElement scrollView;

	@AndroidFindBy(id = "com.et.reader.activities:id/categoriesList")
	private MobileElement quickReadScrollView;

	@AndroidFindBy(id = "com.et.reader.activities:id/view_all_tv")
	@iOSXCUITFindBy(xpath = "//*[contains(@name,'View All')]")
	private MobileElement viewSectionLink;

	@AndroidFindBy(id = "com.et.reader.activities:id/tabs_header")
	@iOSXCUITFindBy(xpath = "//*[contains(@name,'Acc_label_Header')]")
	private MobileElement industryTabBar;

	@AndroidFindBy(id = "com.et.reader.activities:id/ll_mf_pager_item_parent")
	@iOSXCUITFindBy(xpath = "./XCUIElementTypeButton[@name='pageUnSelected']")
	private MobileElement mfSubTabBar;

	@AndroidFindBy(id = "com.et.reader.activities:id/horizScroll")
	@iOSXCUITFindBy(className = "XCUIElementTypeScrollView")
	private MobileElement mutualFundWidgetTabName;

	@AndroidFindBy(id = "com.et.reader.activities:id/hero_text_headline")
	private MobileElement heroStory;
	// com.google.android.gms:id/account_picker_container

	@AndroidFindBy(id = "com.et.reader.activities:id/story_section")
	private List<MobileElement> storySection;

	@AndroidFindBy(id = "com.et.reader.activities:id/story_headline")
	private List<MobileElement> primeStoryHeader;

	@AndroidFindBy(id = "com.google.android.gms:id/account_picker_container")
	private List<MobileElement> gmailOnetapSigninPopup;

	@AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.et.reader.activities:id/story_section']//following::android.widget.TextView")
	private List<MobileElement> storyHeader;

	@AndroidFindBy(id = "com.et.reader.activities:id/ad_image_view")
	private List<MobileElement> advertisment;
	// com.et.reader.activities:id/story_start_trial_container ,
	// com.et.reader.activities:id/prime_widget_story_start_trial

	@AndroidFindBy(id = "com.et.reader.activities:id/story_start_trial_container")
	@iOSXCUITFindBy(xpath = "//*[contains(@name,'START FREE TRIAL')]")
	private List<MobileElement> startFreetrialWidget;

	@AndroidFindBy(id = "com.et.reader.activities:id/prime_widget_story_start_trial")
	@iOSXCUITFindBy(xpath = "//*[contains(@name,'Start your membership')]")
	private List<MobileElement> startMembershipwidget;

	@AndroidFindBy(id = "com.et.reader.activities:id/headline")
	private List<MobileElement> multimediaHeadline;

	@iOSXCUITFindBy(iOSNsPredicate = "name=='Read. Lead. Succeed.'")
	private MobileElement primebanner;

	@AndroidFindBy(id = "com.et.reader.activities:id/card_view")
	private List<MobileElement> moreFromPartnerHeader;

	@AndroidFindBy(id = "com.et.reader.activities:id/imgView")
	private List<MobileElement> multimediaWidgetCard;

	@AndroidFindBy(id = "com.et.reader.activities:id/home_pod_item_headline")
	private List<MobileElement> podcastHeadlineCard;

	@AndroidFindBy(id = "com.et.reader.activities:id/tab_title")
	private List<MobileElement> ETMarketsHeaders;

	@AndroidFindBy(id = "com.et.reader.activities:id/ll_mf_pager_item_parent")
	private List<MobileElement> MFWidgetParent;

	@AndroidFindBy(id = "com.et.reader.activities:id/top_mf_pager_title")
	private MobileElement MFWidgetSubMenu;

	@AndroidFindBy(id = "com.et.reader.activities:id/home_pod_item_headline")
	private MobileElement podcastCard;

	@AndroidFindBy(id = "com.et.reader.activities:id/imgView")
	private MobileElement multimediaCard;

	/// Getters///

	public List<MobileElement> getHeadlinesList() {
		return headlinesList;
	}

	public List<MobileElement> getWidgetHeaders() {
		return widgetHeaders;
	}

	public List<MobileElement> getSlideShowList() {
		return slideShowList;
	}

	public List<MobileElement> getHomepageSlideshows() {
		return homepageSlideshows;
	}

	public List<MobileElement> getMarketWidgetLabels() {
		return marketWidgetLabels;
	}

	public List<MobileElement> getMarketWidgetLTP() {
		return marketWidgetLTP;
	}

	public List<MobileElement> getMarketChangeAbs() {
		return marketChangeAbs;
	}

	public MobileElement getBriefSectionName() {
		return briefSectionName;
	}

	public MobileElement getBriefSectionViewLink() {
		return briefSectionViewLink;
	}

	public List<MobileElement> getscreenerSectionLinkCount() {
		return screenerSectionLinkCount;
	}

	public List<MobileElement> getSectionName(AppiumDriver<?> driver) {
		return (List<MobileElement>) driver.findElements(MobileBy.id("com.et.reader.activities:id/sectionName"));
	}

	public MobileElement getScrollView() {
		return scrollView;
	}

	public MobileElement getQuickReadScrollView() {
		return quickReadScrollView;
	}

	public MobileElement getViewSectionLink() {
		return viewSectionLink;
	}

	public MobileElement getMutualFundWidgetCategoryName() {
		return mutualFundWidgetTabName;
	}

	public MobileElement getMFSubCategoryBar() {
		return mfSubTabBar;
	}

	public MobileElement getIndustryTabBar() {
		return industryTabBar;
	}

	public List<MobileElement> getIndustrySubTab(AppiumDriver<?> driver) {
		return (List<MobileElement>) driver.findElements(MobileBy.id("com.et.reader.activities:id/tab_title"));
	}

	public MobileElement getHeroStory() {
		return heroStory;
	}

	public List<MobileElement> getStorySection() {
		return storySection;
	}

	public List<MobileElement> getStoryHeading() {
		return storyHeader;
	}

	public List<MobileElement> getOnetapsignPopUp() {
		return gmailOnetapSigninPopup;
	}

	public List<MobileElement> getPrimeStoryHeading() {
		return primeStoryHeader;
	}

	public List<MobileElement> getAdvertisment() {
		return advertisment;
	}

	public List<MobileElement> getStartFreeTrialWidget() {
		return startFreetrialWidget;
	}

	public List<MobileElement> getStartMembershipWidget() {
		return startMembershipwidget;
	}

	public List<MobileElement> getMultimediaHeadlines() {
		return multimediaHeadline;
	}

	public MobileElement getPrimeBanner() {
		return primebanner;
	}

	public List<MobileElement> getMoreFromPartnerHeader() {
		return moreFromPartnerHeader;
	}

	public List<MobileElement> getMultimediaCard() {
		return multimediaWidgetCard;
	}

	public List<MobileElement> getPodcastCardHeadline() {
		return podcastHeadlineCard;
	}

	public List<MobileElement> getETMarketsHeader() {
		return ETMarketsHeaders;
	}

	public List<MobileElement> getMFWidgetParent() {
		return MFWidgetParent;
	}

	public MobileElement getMFWidgetSubMenu() {
		return MFWidgetSubMenu;
	}

	public MobileElement getPodcastCard() {
		return podcastCard;
	}

	public MobileElement getMultimediacard() {
		return multimediaCard;
	}
}