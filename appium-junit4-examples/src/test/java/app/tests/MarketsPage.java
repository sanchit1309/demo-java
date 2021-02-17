package app.tests;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import app.pagemethods.AdTechMethods;
import app.pagemethods.AppListingPageMethods;
import app.pagemethods.HeaderPageMethods;
import app.pagemethods.HomePageMethods;
import app.pagemethods.MenuPageMethods;
import app.pagemethods.StoryPageMethods;
import busineslogic.BusinessLogicMethods;
import common.launchsetup.BaseTest;
import common.urlRepo.AppFeeds;
import common.utilities.ApiHelper;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import common.utilities.reporting.ScreenShots;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class MarketsPage extends BaseTest {

	private AppListingPageMethods appListingPageMethods;
	private HeaderPageMethods headerPageMethods;
	private StoryPageMethods storyPageMethods;
	private MenuPageMethods menuPageMethods;
	private SoftAssert softAssert;
	private AppiumDriver<?> appDriver;
	private int storiesToCheck = 2;
	private int listPageSwipes;
	private List<String> apiMenuOptions = new LinkedList<>();
	private AdTechMethods adTechMethods;
	private boolean mrecAdFlag = false;
	private String sectionToExlude = "Market Data,Top News";

	@BeforeClass(alwaysRun = true)
	public void launchApp() throws IOException {
		launchBrowser();
		appDriver = iAppCommonMethods.getDriver();
		appListingPageMethods = new AppListingPageMethods(appDriver);
		storyPageMethods = new StoryPageMethods(appDriver);
		headerPageMethods = new HeaderPageMethods(appDriver);
		adTechMethods = new AdTechMethods(appDriver);
		menuPageMethods=new MenuPageMethods(appDriver);
		apiMenuOptions = ApiHelper.getValueFromAPI(AppFeeds.lhsFeed, "nm", "nm", "Markets", "Item", "ss");
		
	}

	@Test(description = "Verifies markets page header", priority = 0)
	public void verifyMarketsHeader() {
		softAssert = new SoftAssert();
		navigateToMarkets("Markets", "Top News",true);
		String headerText = headerPageMethods.getHeaderText();
		softAssert.assertEquals(headerPageMethods.getHeaderText(), "Markets", "Actual header text:" + headerText);
		softAssert.assertAll();

	}

	@Test(description = "Verifies markets data widget on markets page", priority = 1)
	public void verifiesETMarketsWidget() {
		softAssert = new SoftAssert();

		HomePageMethods homePageMethods = new HomePageMethods(appDriver);
		Map<String, String> data = homePageMethods.getMarketsData();
		Assert.assertTrue(data != null, "No data found in the markets benchmark section");
		int allbenchmarks = data.size();
		int sizeToValidate = BaseTest.platform.contains("android") ? 4 : 6;
		if (allbenchmarks < sizeToValidate) {
			iAppCommonMethods.swipeByScreenPercentage(0.80, 0.75);
			data.putAll(homePageMethods.getMarketsData());
			allbenchmarks = data.size();
		}
		softAssert.assertEquals(allbenchmarks, sizeToValidate,
				"A benchmark value is not found, actual:" + allbenchmarks + ",expected:" + sizeToValidate);
		data.forEach((benchmarkName, ltpUi) -> {
			if (benchmarkName.equalsIgnoreCase("S&P BSE Sensex")) {
				benchmarkName = "SENSEX";
			}
			double ltp = homePageMethods.getValueFromApi(benchmarkName);
			int allowedDiff = BusinessLogicMethods.getBenchmarksAllowedValueDiff(benchmarkName);
			softAssert.assertTrue(
					VerificationUtil.valueIsInRange(ltp, VerificationUtil.parseDouble(ltpUi), allowedDiff),
					"<br>The value for " + benchmarkName + " is outside the allowed diff of " + allowedDiff + ",actual:"
							+ ltpUi + ",expected:" + ltp);
		});
		iAppCommonMethods.scrollDown();
		softAssert.assertAll();
	}

	@Test(description = "Verifies news in top news section", priority = 2)
	public void verifyTopNewMarkets() {
		boolean headerAdFlag = false;
		softAssert = new SoftAssert();
		WaitUtil.sleep(2000);
		headerAdFlag = adTechMethods.isAdDisplayed("Header");	

		softAssert.assertTrue(headerAdFlag, "Header ad missing from Markets Page");
		softAssert.assertTrue(adTechMethods.isAdDisplayed("Footer"), "Footer ad missing from Markets Page");
		String tabName = "Top News";
		iAppCommonMethods.swipeUp();
		int swipesCounter = 4;
		boolean flag = false;
		boolean colombiaAdFlag = false;
		while (swipesCounter >= 0) {
			if(!colombiaAdFlag){
				colombiaAdFlag = adTechMethods.isAdDisplayed("Colombia");
			}
			System.out.println(colombiaAdFlag);
			if(!mrecAdFlag) {
				mrecAdFlag = adTechMethods.isAdDisplayed("Mrec Listing");
			}
			Map<String, MobileElement> mapNews = appListingPageMethods.getTopNews();
			flag = !flag ? mapNews.size() > 0 : true;
			mapNews.forEach((headline, story) -> {
				verifyHeadlineNDate(story, headline, tabName);
			});
			System.out.println(flag + "<-flag, mapNews.size->" + mapNews.size());
			softAssert.assertTrue(flag, "<br>-Zero stories found in top news, tab:" + tabName + "in swipe:"
					+ (listPageSwipes - swipesCounter));
			swipesCounter = appListingPageMethods.swipeUpListPage(false, swipesCounter);
		}
		softAssert.assertTrue(colombiaAdFlag, "Colombia ad missing from markets Section, tab : "+tabName);
		softAssert.assertTrue(mrecAdFlag, "Mrec ad missing from Markets Section, tab : "+tabName);
		iAppCommonMethods.scrollDown();
       softAssert.assertAll();

	}

	@Test(description = "Verifies top horizontal navigation", priority = 3)
	public void topTabsMarkets() {
		softAssert = new SoftAssert();
		softAssert.assertTrue(apiMenuOptions.size() >= 5, "<br>-Menu options are less than 5" + apiMenuOptions);
		apiMenuOptions.forEach(text -> {
			boolean isStoryFound = false;
			if (!headerPageMethods.isTabDisplayed(text))
				headerPageMethods.scrollTopTabsLeft();
			softAssert.assertTrue(headerPageMethods.isTabDisplayed(text), "<br>- Tab " + text + " is not shown.");
			Assert.assertTrue(headerPageMethods.clickTopTab(text), "Unable to click " + text);
			System.out.println("---Tab---"+text);
			WaitUtil.sleep(1000);
			List<MobileElement> mapNews = appListingPageMethods.getHeadlineList();
			isStoryFound = mapNews.size() > 0;
			System.out.println(isStoryFound + "<-isStoryFound," + "<mapNews.size->" + mapNews.size());
			if (!sectionToExlude.contains(text))
				softAssert.assertTrue(isStoryFound, "<br>-Zero stories found in  tab:-" + text);
		});

		softAssert.assertAll();
	}

	private void verifyHeadlineNDate(MobileElement news, String headline, String tabName) {
		softAssert.assertTrue(!headline.equals("null"), "Headline not found under MF tab:" + tabName);
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
				new ScreenShots().seleniumNativeScreenshot(appDriver,"headlineMissingMarkets");
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
			storiesToCheck--;
		}
	}

	
	private void navigateToMarkets(String l1Menu, String l2Menu, boolean isClickNeeded) {
		softAssert.assertTrue(navigateToMoreApps(), "Page not redirected to More Apps");
		headerPageMethods.clickMenuIconSwipeUpFirst();
		menuPageMethods.scrollDownToSettingIcon();
		softAssert.assertTrue(menuPageMethods.scrollToMenuOptionClick(l1Menu, isClickNeeded),
				"<br>Unable to find menu option: " + l1Menu);
		WaitUtil.sleep(1000);
		boolean flag = menuPageMethods.clickL2MenuByName(l2Menu);
		softAssert.assertTrue(flag, "Sub Menu" + l2Menu + "is not clicked");

	}

	@AfterClass(alwaysRun = true)
	public void quit() throws Exception {
		appDriver.quit();
	}
	
	private boolean navigateToMoreApps() {
		new HeaderPageMethods(appDriver).clickMenuIconSwipeUpFirst();
		return new MenuPageMethods(appDriver).clickMenuByLabel("More Apps");
	}

}
