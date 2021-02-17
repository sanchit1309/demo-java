package pwa.tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import pwa.pagemethods.HomePageMethods;
import pwa.pagemethods.MenuPageMethods;
import pwa.pagemethods.PoliticsPageMethods;
import pwa.pagemethods.PwaListingPageMethods;


public class PoliticsPage extends BaseTest{
	
	private String wapUrl;
	private PoliticsPageMethods politicsPageMethods;
	private HomePageMethods homePageMethods;
	private MenuPageMethods menuPageMethods;
	SoftAssert softAssert;
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl = BaseTest.baseUrl;
		launchBrowser(wapUrl);
		politicsPageMethods = new PoliticsPageMethods(driver);
		homePageMethods = new HomePageMethods(driver);
		menuPageMethods = new MenuPageMethods(driver);
		Assert.assertTrue(openPoliticsSection(),"Unable to click Politics Tab");
	}
	
	public boolean openPoliticsSection(){
		homePageMethods.clickMenuIcon();
		WaitUtil.sleep(2000);
		return menuPageMethods.clickMenuOption("Politics");
	}
	
	@Test(description="Verify Top news of Politics Page", groups = {"Politics Page"}, priority = 0)
	public void verifyPoliticsTopNews() {
		softAssert = new SoftAssert();
		int articleCount = 10;
		
		List<String> topNewsStories = VerificationUtil.getLinkHrefList(politicsPageMethods.getTopSectionNews());
		int count = topNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under top section should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> topNewsDup = VerificationUtil.isListUnique(topNewsStories);
		softAssert.assertTrue(topNewsDup.isEmpty(), "<br>- Top News Section has duplicate stories, repeating story(s)->" + topNewsDup);
		topNewsStories.forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top News link <a href=" + keyword + ">link</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}
	
	@Test(description ="Verify News page Not to miss section", groups={"Politics Page"}, priority=1)
	public void verifyPoliticsSection() {
		softAssert = new SoftAssert();
		int articleCount = 5;		
		List<String> notToMissStories = VerificationUtil.getLinkHrefList(politicsPageMethods.getSectionNewsHeadlines());
		int count = notToMissStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under not to miss sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> notToMissNewsDup = VerificationUtil.isListUnique(notToMissStories);
		softAssert.assertTrue(notToMissNewsDup.isEmpty(), "<br>- Not to miss Section has duplicate stories, repeating story(s)->" + notToMissNewsDup);
		notToMissStories.forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Not to miss link <a href=" + keyword + ">link</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}
	
	
	@Test(description="Verify recency of articles",groups={"Politics Page"}, priority=2, enabled=true)
	public void verifyTopNewsRecency(){
		softAssert = new SoftAssert();
		
		Map<String, Boolean> headlineRecency = new PwaListingPageMethods(driver).checkRecency(1);
		for (Map.Entry<String, Boolean> entry : headlineRecency.entrySet()) {
			softAssert.assertTrue(entry.getValue(),"Article Recency verification for " + entry.getKey());
		}
		softAssert.assertAll();
	}
	

	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}


}
