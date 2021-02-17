package web.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.WaitUtil;
import common.utilities.reporting.ScreenShots;
import web.pagemethods.CommonL1L2PagesMethods;
import web.pagemethods.ETNowMethods;
import web.pagemethods.WebBaseMethods;

public class ETNow extends BaseTest {
	private String etNowUrl;
	private ETNowMethods etNowMethods;
	CommonL1L2PagesMethods commonL1L2PagesMethods;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		etNowUrl = BaseTest.baseUrl + Config.fetchConfigProperty("ETNowUrl");
		launchBrowser(etNowUrl);
		commonL1L2PagesMethods = new CommonL1L2PagesMethods(driver);
		etNowMethods = new ETNowMethods(driver);
	}

	@Test(description = "This test verifies live tv streaming", enabled = false)
	public void verifyLiveTV() {
		WaitUtil.sleep(3000);
		etNowMethods.switchToVideoFrame();
		if (BaseTest.browserName.equalsIgnoreCase("chrome")) {

		}
		etNowMethods.playOnly();
		Assert.assertTrue(etNowMethods.hasAdvtCompleted(),
				"Live TV is not playing, waited for 60 secs for advt. to disappear.");
		Assert.assertTrue(etNowMethods.pauseVideo(), "LiveTV did not pause");
		Assert.assertTrue(etNowMethods.playVideo(), "Live TV did not play after pausing the streaming");
		WebBaseMethods.switchToDefaultContext();
	}

	@Test(description = "This test verifies view count on most viewd videos")
	public void verifyMostViwedVideosViewCount() {
		WebBaseMethods.navigateTimeOutHandle(etNowUrl);
		softAssert = new SoftAssert();
		WaitUtil.sleep(3000);
		Assert.assertTrue(etNowMethods.clickMostViewedTab(), "MostViewed tabs not found on " + etNowUrl);
		List<WebElement> mostViewdVideos = etNowMethods.getMostViewedVideos();
		Assert.assertTrue(mostViewdVideos.size() > 0, "No most watched video in the section");
		for (int i = 0; i < mostViewdVideos.size(); i++) {
			WebElement el = etNowMethods.getMostViewedVideos().get(i);
			String href = el.getAttribute("href");
			WebBaseMethods.clickElementUsingJSE(el);
			WaitUtil.waitForLoad(driver);
			int viewCount = etNowMethods.getViewCount();
			if (viewCount >= 0) {
				if (viewCount == 0) {
					new ScreenShots().takeScreenshotAnotherDriver(driver, "verifyMostViwedVideosViewCount");
				}
				softAssert.assertTrue(viewCount > 0, "Most watched video " + href + " is having 0 views");
			}
			WebBaseMethods.navigateBackTimeOutHandle();
		}
		softAssert.assertAll();
	}

	@Test(description = "Verify ET now page sub section", dataProvider = "subSections")
	public void verifyETNowSubSection(String subSection) {
		softAssert = new SoftAssert();
		String sectionName = subSection;
		String sectionNameInHeadingLink = sectionName.toLowerCase();
		if (sectionName.equals("Brand_Equity")) {
			sectionNameInHeadingLink = "brand-equity";
		}
		try {

			List<String> listHref = etNowMethods.getSectionVideoLinks(sectionName);
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(listHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(listHref);
			softAssert.assertTrue(listHref.size() >= 4, "The number of links under the " + sectionName
					+ " section should be more than equal to 4 but found: " + listHref.size());
			softAssert.assertTrue(errorLinks.size() == 0, "Few links are not having response code 200 under the "
					+ sectionName + " section. List of such links are: " + errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the " + sectionName + " section " + dupLinks);

			boolean sectionHeadingFlag = commonL1L2PagesMethods.verifyTheSectionHeadingLink(
					etNowMethods.getSectionHeadingLink(sectionName), sectionNameInHeadingLink);
			softAssert.assertTrue(sectionHeadingFlag,
					"The section Heading link is not of the same section or link is redirecting to error page");
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the Latest news section")
	public void verifyLatestNewsSection() {
		softAssert = new SoftAssert();

		try {
			Assert.assertTrue(etNowMethods.clickLatestNewsTab(), "Latest News Tab is not clickable on the page");

			List<String> listHref = etNowMethods.getLatestNewsLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(listHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(listHref);
			softAssert.assertTrue(listHref.size() >= 4,
					"The number of links under the Latest news section should be more than equal to 4 but found: "
							+ listHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the Latest news section. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the Latest news section " + dupLinks);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the Top news section")
	public void verifyTopNewsSection() {
		softAssert = new SoftAssert();

		try {
			Assert.assertTrue(etNowMethods.clickTopNewsTab(), "Top News Tab is not clickable on the page");

			List<String> listHref = etNowMethods.getTopNewsLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(listHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(listHref);
			softAssert.assertTrue(listHref.size() >= 5,
					"The number of links under the Top news section should be more than equal to 5 but found: "
							+ listHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the Top news section. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the Top news section " + dupLinks);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the Latest Videos section")
	public void verifyLatestVideoSection() {
		softAssert = new SoftAssert();

		try {
			Assert.assertTrue(etNowMethods.clickLatestVideosTab(), "Latest Videos Tab is not clickable on the page");

			List<String> listHref = etNowMethods.getLatestVideosLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(listHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(listHref);
			softAssert.assertTrue(listHref.size() >= 5,
					"The number of links under the Latest Videos section should be more than equal to 5 but found: "
							+ listHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the Latest Videos section. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the Latest Videos section " + dupLinks);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the Most Viewed section")
	public void verifyMostViewedSection() {
		softAssert = new SoftAssert();

		try {
			Assert.assertTrue(etNowMethods.clickMostViewedWidgetTab(), "Most Viewed Tab is not clickable on the page");

			List<String> listHref = etNowMethods.getMostViewedWidgetLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(listHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(listHref);
			softAssert.assertTrue(listHref.size() >= 5,
					"The number of links under the Most Viewed section should be more than equal to 5 but found: "
							+ listHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the Most Viewed section. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the Most Viewed section " + dupLinks);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

	@DataProvider
	public String[] subSections() {
		String[] sections = { "Stocks", "Experts", "Corporate", "Markets", "Auto", "Tech", "Policy", "Finance",
				"Commodities", "Daily", "Brand_Equity", "Entertainment", "Results", "Budget" };

		return sections;

	}

}
