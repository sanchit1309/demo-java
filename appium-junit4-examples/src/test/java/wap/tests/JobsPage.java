package wap.tests;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import wap.pagemethods.HomePageMethods;
import wap.pagemethods.MenuPageMethods;
import wap.pagemethods.WapListingPageMethods;

public class JobsPage extends BaseTest {
	private String wapUrl;
	private HomePageMethods homePageMethods;
	private MenuPageMethods menuPageMethods;
	private WapListingPageMethods wapListingMethods;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl = BaseTest.baseUrl;
		launchBrowser(wapUrl);
		homePageMethods = new HomePageMethods(driver);
		menuPageMethods = new MenuPageMethods(driver);
		wapListingMethods = new WapListingPageMethods(driver);
		homePageMethods.jqueryInjForReactPages();
		Assert.assertTrue(openJobsSection(), "Unable to click Jobs Tab");
	}

	public boolean openJobsSection() {
		homePageMethods.clickFooterMenuICon();
		WaitUtil.sleep(2000);
		return menuPageMethods.clickMenuOptionReact("Jobs");
	}

	@Test(description = "Verify Top news of jobs Page", groups = { "Jobs Page" }, priority = 0)
	public void verifyJobsTopNews() {
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
}
