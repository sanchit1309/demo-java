package app.tests;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import app.pagemethods.AdTechMethods;
import app.pagemethods.AppListingPageMethods;
import app.pagemethods.HeaderPageMethods;
import app.pagemethods.MenuPageMethods;
import app.pagemethods.StoryPageMethods;
import common.launchsetup.BaseTest;
import common.urlRepo.AppFeeds;
import common.utilities.ApiHelper;
import common.utilities.WaitUtil;
import common.utilities.reporting.ScreenShots;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class TechPage extends BaseTest{
	
	private AppListingPageMethods appListingPageMethods;
	private HeaderPageMethods headerPageMethods;
	private StoryPageMethods storyPageMethods;
	private SoftAssert softAssert;
	private AppiumDriver<?> appDriver;
	private int storiesToCheck;
	private int listPageSwipes;
	private int tabsToCheck;
	private int initialCount;
	private List<String> apiMenuOptions = new LinkedList<>();
	private List<String> restrictedTabsList = new LinkedList<>();
	private AdTechMethods adTechMethods;
	private boolean mrecAdFlag = false;
	
	@BeforeClass(alwaysRun = true)
	@Parameters({ "storiesDetailsTocheck", "listPageStoriesTillSwipe", "noOfTabs" })
	public void launchApp(@Optional("1") int storiesDetailsTocheck, @Optional("3") int noOfSwipes,
			@Optional("2") int noOfTabs) throws IOException {
		launchBrowser();
		appDriver = iAppCommonMethods.getDriver();
		appListingPageMethods = new AppListingPageMethods(appDriver);
		storyPageMethods = new StoryPageMethods(appDriver);
		headerPageMethods = new HeaderPageMethods(appDriver);
		adTechMethods = new AdTechMethods(appDriver); 
		initialCount = storiesDetailsTocheck;
		listPageSwipes = noOfSwipes;
		tabsToCheck = noOfTabs;
		apiMenuOptions = ApiHelper.getValueFromAPI(AppFeeds.lhsFeed, "nm", "nm", "Tech", "Item", "ss");
		Assert.assertTrue(navigateToTechTab());
	}

	
	@Test(description = "Verifies Tech header", priority = 0)
	public void verifyTechHeader() {
		String headerText = headerPageMethods.getHeaderText();
		Assert.assertEquals(headerPageMethods.getHeaderText(), "Tech", "Actual header text:" + headerText);
	}
	
	@Test(description = "Verifies news in top news section", priority = 1, dataProvider = "top tabs")
	public void verifyTopNewsTech(String tabName) {
		Assert.assertTrue(headerPageMethods.clickTopTab(tabName), "Unable to click " + tabName);
		iAppCommonMethods.scrollDown();
		storiesToCheck = initialCount;
		int swipesCounter = listPageSwipes;
		boolean headerAdFlag = false;
		softAssert = new SoftAssert();
		for (int i = 0; i < 7; i++) {
			headerAdFlag = adTechMethods.isAdDisplayed("Header");
			if (headerAdFlag)
				break;
			else {
				iAppCommonMethods.swipeByScreenPercentage(0.20, 0.80);
				WaitUtil.sleep(5000);
			}
		}
		softAssert.assertTrue(headerAdFlag, "Header ad missing from Tech Section, tab : " + tabName);
		WaitUtil.sleep(2000);
		softAssert.assertTrue(adTechMethods.isAdDisplayed("Footer"),
				"Footer ad missing from Tech Section, tab : " + tabName);

		boolean isStoryFound = true;
		boolean flag = false;
		boolean colombiaAdFlag = false;

		while (swipesCounter >= 0) {
			if (!colombiaAdFlag) {
				colombiaAdFlag = adTechMethods.isAdDisplayed("Colombia");
			}
			if (!mrecAdFlag) {
				mrecAdFlag = adTechMethods.isAdDisplayed("Mrec Listing");
			}
			Map<String, MobileElement> mapNews = appListingPageMethods.getTopNews();
			flag = !flag ? mapNews.size() > 0 : true;
			mapNews.forEach((headline, story) -> {
				verifyHeadlineNDate(story, headline, tabName);
			});
			isStoryFound = mapNews.size() > 0;
			System.out.println(isStoryFound + "<-isStoryFound," + flag + "<-flag,mapNews.size->" + mapNews.size());
			softAssert.assertTrue(flag, "<br>-Zero stories found in top news, tab:" + tabName + "in swipe:"
					+ (listPageSwipes - swipesCounter));
			swipesCounter = appListingPageMethods.swipeUpListPage(false, swipesCounter);
		}
		iAppCommonMethods.scrollDown();
		System.out.println(colombiaAdFlag);
		System.out.println(mrecAdFlag);
		softAssert.assertTrue(colombiaAdFlag, "Colombia ad missing from Tech Section, tab : " + tabName);
		softAssert.assertTrue(mrecAdFlag, "Mrec ad missing from Tech Section, tab : " + tabName);
		softAssert.assertAll();

	}


	@Test(description = "Verifies top horizontal tabs and check if stories are loading", priority = 2)
	public void topTabsTech() {
		softAssert = new SoftAssert();
		softAssert.assertTrue(apiMenuOptions.size() >= 5, "<br>-Menu options are less than 5" + apiMenuOptions);
		iAppCommonMethods.scrollDown();
		headerPageMethods.scrollTopTabsRight();
		apiMenuOptions.removeAll(restrictedTabsList);
		apiMenuOptions.forEach(text -> {
			boolean isStoryFound = false;
			if (!headerPageMethods.isTabDisplayed(text))
				headerPageMethods.scrollTopTabsLeft();
			softAssert.assertTrue(headerPageMethods.isTabDisplayed(text), "<br>- Tab " + text + " is not shown.");
			Assert.assertTrue(headerPageMethods.clickTopTab(text), "Unable to click " + text);
			System.out.println("---Tab---" + text);
			WaitUtil.sleep(1000);
			List<MobileElement> mapNews = appListingPageMethods.getHeadlineList();
			isStoryFound = mapNews.size() > 0;
			System.out.println(isStoryFound + "<-isStoryFound," + "<mapNews.size->" + mapNews.size());
			softAssert.assertTrue(isStoryFound, "<br>-Zero stories found in  tab:-" + text);
		});

		softAssert.assertAll();
	}

	@DataProvider(name = "top tabs")
	public Object[] getTopTabs() {
		restrictedTabsList.addAll(apiMenuOptions.subList(0, tabsToCheck));
		return restrictedTabsList.toArray(new Object[restrictedTabsList.size()]);
	}
	
	private boolean navigateToTechTab() {
		headerPageMethods.clickMenuIcon();
		return new MenuPageMethods(appDriver).clickL1L2MenuByLabel("Tech", "Top News");
	}
	
	
	
	private void verifyHeadlineNDate(MobileElement news, String headline, String tabName) {
		softAssert.assertTrue(!headline.equals("null"), "Headline not found under Tech,tab:" + tabName);
		if (storiesToCheck > 0) {
			Boolean clickFlag = appListingPageMethods.clickNewsHeadline(news);
			if (clickFlag != null)
				softAssert.assertTrue(clickFlag, "<br>-Unable to click story:" + headline);
			Boolean headlineFlag = storyPageMethods.checkHeadlineLength();
			softAssert.assertTrue(headlineFlag != null, "<br>-Headline not found on clicking:" + headline);
			if (headlineFlag != null)
				softAssert.assertTrue(headlineFlag,
						"<br>Headline not shown on story page, list page headline is:" + headline);
			if (headlineFlag == null || !headlineFlag)
				new ScreenShots().seleniumNativeScreenshot(appDriver, "headlineMissingTech");
			WaitUtil.sleep(1000);
			iAppCommonMethods.swipeUp();
			Boolean dateFlag = storyPageMethods.checkIfDateinRange(2);
			softAssert.assertTrue(dateFlag != null, "<br>-Date not found for story:" + headline);
			if (dateFlag != null)
				softAssert.assertTrue(dateFlag, "<br>Story->" + headline + " is older than 2 days");
			iAppCommonMethods.swipeDown();
			if (storyPageMethods.validatePrimeLogoOnArticleshow()) {
				softAssert.assertTrue(storyPageMethods.verifyPrimeSubscribeWidget(),
						"App Blocker Subscribe ET prime widget is missing for prime article:" + headline);
				WaitUtil.sleep(1000);
			} else {
				Boolean widgetflag = appListingPageMethods.verifyETprimeWidget();
				softAssert.assertTrue(widgetflag, "ET prime support widget not displayed ");
				Boolean trialButton = appListingPageMethods.verifyStartFreeTrialLink();
				softAssert.assertTrue(trialButton, "ET prime support widget Start Free Trial Link  not displayed ");
			}
			storyPageMethods.navigateBackToListPage(appDriver);
			WaitUtil.sleep(2000);
			iAppCommonMethods.scrollDown();
			WaitUtil.sleep(2000);
			storiesToCheck--;
		}
	}
	
	@AfterClass(alwaysRun = true)
	public void quit() throws Exception {
		appDriver.quit();
	}
}
