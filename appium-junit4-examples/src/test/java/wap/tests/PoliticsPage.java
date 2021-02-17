package wap.tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import wap.pagemethods.AdTechMethods;
import wap.pagemethods.HomePageMethods;
import wap.pagemethods.MenuPageMethods;
import wap.pagemethods.PoliticsPageMethods;
import wap.pagemethods.WapListingPageMethods;

public class PoliticsPage extends BaseTest {

	private String wapUrl;
	private PoliticsPageMethods politicsPageMethods;
	private HomePageMethods homePageMethods;
	private MenuPageMethods menuPageMethods;
	private AdTechMethods adTechMethods;
	private WapListingPageMethods wapListingMethods;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl = BaseTest.baseUrl;
		launchBrowser(wapUrl);
		politicsPageMethods = new PoliticsPageMethods(driver);
		homePageMethods = new HomePageMethods(driver);
		menuPageMethods = new MenuPageMethods(driver);
		adTechMethods = new AdTechMethods(driver);
		wapListingMethods = new WapListingPageMethods(driver);
		homePageMethods.jqueryInjForReactPages();
		Assert.assertTrue(openPoliticsSection(), "Unable to click Politics Tab");
	}

	public boolean openPoliticsSection() {
		homePageMethods.clickFooterMenuICon();
		WaitUtil.sleep(2000);
		return menuPageMethods.clickMenuOptionReact("Politics");
	}

	@Test(description = "Verify Top news of Politics Page", groups = { "Politics Page" }, priority = 0)
	public void verifyPoliticsTopNews() {
		softAssert = new SoftAssert();
		int articleCount = 5;

		List<String> topNewsStories = wapListingMethods.getTopHeadlinesAndSubNewsHref();
		int count = topNewsStories.size();
		System.out.println(count);
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under top section should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> topNewsDup = VerificationUtil.isListUnique(topNewsStories);
		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>- Top News Section has duplicate stories, repeating story(s)->" + topNewsDup);
		topNewsStories.forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200,
					"<br>- Top News link <a href=" + keyword + ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify News page Not to miss section", groups = { "Politics Page" }, priority = 1, enabled = false)
	public void verifyPoliticsSection() {
		softAssert = new SoftAssert();
		int articleCount = 5;
		List<String> notToMissStories = VerificationUtil.getLinkTextList(politicsPageMethods.getSectionNewsHeadlines());
		int count = notToMissStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under not to miss sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> notToMissNewsDup = VerificationUtil.isListUnique(notToMissStories);
		softAssert.assertTrue(notToMissNewsDup.isEmpty(),
				"<br>- Not to miss Section has duplicate stories, repeating story(s)->" + notToMissNewsDup);
		politicsPageMethods.getSectionNewsHref().forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200,
					"<br>- Not to miss link <a href=" + keyword + ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify recency of articles", groups = { "Politics Page" }, priority = 2, enabled = false)
	public void verifyTopNewsRecency() {
		softAssert = new SoftAssert();

		Map<String, Boolean> headlineRecency = new WapListingPageMethods(driver).checkRecency(1);
		for (Map.Entry<String, Boolean> entry : headlineRecency.entrySet()) {
			softAssert.assertTrue(entry.getValue(), "Article Recency verification for " + entry.getKey());
		}
		softAssert.assertAll();
	}

	@Test(description = " This test verifies the Google and colombia ads on Politics Listing page", groups = {
			"Politics Page" }, priority = 3, enabled = false)
	public void verifyAds() {
		SoftAssert softAssert = new SoftAssert();
		WaitUtil.sleep(2000);
		Map<String, String> expectedIdsMap = Config.getAllKeys("wap_adIdLocation");
		softAssert.assertTrue(adTechMethods.getDisplayedAdIds().size() > 0,
				"No google ads shown on Politics Listing Page");
		if (adTechMethods.getDisplayedAdIds().size() > 0) {
			expectedIdsMap.forEach((K, V) -> {
				if (!K.contains("HP"))
					softAssert.assertTrue(adTechMethods.matchIdsWithRegex(K),
							"Following ad(s) is/are not shown " + expectedIdsMap.get(K) + " on Politics Listing Page");
			});
		}
		List<String> ctnAds = adTechMethods.getAllColombiaAds();
		if (ctnAds.size() > 0) {
			List<String> missingAds = adTechMethods.getMissingColumbiaAds();
			softAssert.assertFalse(missingAds.size() > 0, "Following colombia ad(s) is/are not shown " + missingAds);
		} else
			softAssert.assertTrue(false, "No colombia Ads found on Politics Listing Page");

		softAssert.assertAll();
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}
