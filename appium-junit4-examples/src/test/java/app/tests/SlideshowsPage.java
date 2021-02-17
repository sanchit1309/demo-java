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

import app.pagemethods.HeaderPageMethods;
import app.pagemethods.MenuPageMethods;
import app.pagemethods.SlideshowsPageMethods;
import app.pagemethods.StoryPageMethods;
import common.launchsetup.BaseTest;
import common.urlRepo.AppFeeds;
import common.utilities.ApiHelper;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class SlideshowsPage extends BaseTest {

	private MenuPageMethods menuPageMethods;
	private HeaderPageMethods headerPageMethods;
	private StoryPageMethods storyPageMethods;
	private SlideshowsPageMethods slideshowsPageMethods;
	private AppiumDriver<?> appDriver;
	private SoftAssert softAssert;
	private List<String> apiMenuOptions;
	private int initialCount;
	private int storiesToCheck;
	private List<String> restrictedTabsList = new LinkedList<String>();
	private int tabsToCheck;

	@BeforeClass(alwaysRun = true)
	@Parameters({ "storiesDetailsTocheck", "noOfTabs" })
	public void launchApp(@Optional("1") int storiesDetailsTocheck, @Optional("4") int noOfTabs) throws IOException {
		launchBrowser();
		appDriver = iAppCommonMethods.getDriver();
		menuPageMethods = new MenuPageMethods(appDriver);
		storyPageMethods = new StoryPageMethods(appDriver);
		headerPageMethods = new HeaderPageMethods(appDriver);
		slideshowsPageMethods = new SlideshowsPageMethods(appDriver);
		apiMenuOptions = ApiHelper.getValueFromAPI(AppFeeds.slideShowFeed, "nm", "", "", "Item");
		initialCount = storiesDetailsTocheck;
		tabsToCheck = noOfTabs;
		Assert.assertTrue(goToSlideShow(), "Unable to navigate to slide shows");
	}

	@DataProvider(name = "top tabs")
	public Object[] getTopTabs() {
		restrictedTabsList.addAll(apiMenuOptions.subList(0, tabsToCheck));
		return restrictedTabsList.toArray(new Object[restrictedTabsList.size()]);
	}

	@Test(description = "Verifies slideshows header", priority = 0)
	public void verifySlideshowHeader() {
		softAssert = new SoftAssert();
		String text=headerPageMethods.getHeaderText();
		softAssert.assertEquals(text, "Slideshows","Header text value on slideshow page "+text+" is not same as expected: Slideshows");
		softAssert.assertAll();
	}

	@Test(description = "Verifies slideshows article under tabs", priority = 1, dataProvider = "top tabs")
	public void verifySlideshow(String tab) {
		storiesToCheck = initialCount;
		softAssert = new SoftAssert();
		System.out.println("tab---- " + tab);
		Assert.assertTrue(slideshowsPageMethods.clickTopTab(tab), "Unable to click tab:" + tab);
		WaitUtil.sleep(8000);
		Map<String, MobileElement> mapNews = slideshowsPageMethods.getAllSlideShowsMap();
		mapNews.forEach((headline, story) -> {
			slideShowDetailVerification(story, headline, tab);
		});
		softAssert.assertTrue(mapNews.size() > 0, "<br> No slideshows found on tab:" + tab + ", after 10 secs");
		softAssert.assertAll();
	}

	@Test(description = "Verifies top horizontal tabs", priority = 2, enabled = true)
	public void topTabsSlideShow() {
		softAssert = new SoftAssert();
		softAssert.assertTrue(apiMenuOptions.size() >= 5, "<br>-Menu options are less than 5" + apiMenuOptions);
		headerPageMethods.scrollTopTabsRight();
		apiMenuOptions.forEach(text -> {
			boolean isStoryFound = false;
			if (!slideshowsPageMethods.isTabDisplayed(text))
				headerPageMethods.scrollTopTabsLeft();
			softAssert.assertTrue(slideshowsPageMethods.isTabDisplayed(text), "<br>- Tab " + text + " is not shown.");
			Assert.assertTrue(slideshowsPageMethods.clickTopTab(text), "Unable to click " + text);
			System.out.println("---Tab---" + text);
			WaitUtil.sleep(1000);
			Map<String, MobileElement> mapNews = slideshowsPageMethods.getAllSlideShowsMap();
			isStoryFound = mapNews.size() > 0;
			System.out.println(isStoryFound + "<-isStoryFound," + "<mapNews.size->" + mapNews.size());
			softAssert.assertTrue(isStoryFound, "<br>-Zero stories found in  tab:-" + text);

		});

		softAssert.assertAll();
	}

	private boolean goToSlideShow() {
		boolean flag = false;
		headerPageMethods.clickMenuIconSwipeUpFirst();
		flag = menuPageMethods.clickMenuByLabel("Slideshows");
		WaitUtil.sleep(5000);
		return flag;
	}


	private void slideShowDetailVerification(MobileElement news, String headline, String tabName) {
		softAssert.assertTrue(!headline.equals("null"), "Headline not found under Slideshows,tab:" + tabName);
		if (storiesToCheck > 0) {
			iAppCommonMethods.clickElement(news);
			WaitUtil.sleep(5000);
			Boolean headlineFlag = storyPageMethods.checkHeadlineLength();
			softAssert.assertTrue(headlineFlag != null, "<br>-Headline not found on clicking:" + headline);
			if (headlineFlag != null)
				softAssert.assertTrue(headlineFlag,
						"<br>Headline not shown on story page, list page headline is:" + headline);
			softAssert.assertTrue(slideshowsPageMethods.verifyTotalSlideShowCount(),
					"Slideshow count mismatch for article: " + headline);
			iAppCommonMethods.swipeDown();
			iAppCommonMethods.navigateBack(appDriver);
			storiesToCheck--;
		}

	}
	@AfterClass(alwaysRun = true)
	public void quit() throws Exception {
		appDriver.quit();
	}

}