package wap.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import wap.pagemethods.HomePageMethods;
import wap.pagemethods.MenuPageMethods;
import wap.pagemethods.NriPageMethods;
import wap.pagemethods.WapListingPageMethods;

public class NriPage extends BaseTest {
	private String wapUrl;
	private HomePageMethods homePageMethods;
	private MenuPageMethods menuPageMethods;
	private WapListingPageMethods wapListingMethods;
	private NriPageMethods nriPageMethods;
	Map<String, String> testData;
	String excelPath = "MenuSubMenuData";

	SoftAssert softAssert;

	boolean flag;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl = BaseTest.baseUrl;
		launchBrowser(wapUrl);
		homePageMethods = new HomePageMethods(driver);
		menuPageMethods = new MenuPageMethods(driver);
		wapListingMethods = new WapListingPageMethods(driver);
		nriPageMethods = new NriPageMethods(driver);
		testData = ExcelUtil.getTestDataRow(excelPath, "verifyNriTopNavigation", 1);

		Assert.assertTrue(openNriSection(), "Unable to click nri tab");
	}

	public boolean openNriSection() {
		homePageMethods.clickFooterMenuICon();
		WaitUtil.sleep(2000);
		flag = menuPageMethods.clickMenuOptionReact("NRI");
		WaitUtil.sleep(2000);
		wapListingMethods.removeGoogleVignetteAd();
		WaitUtil.sleep(3000);
		if (driver.getCurrentUrl().contains("nri"))
			flag = true;
		else {
			flag = false;
		}
		return flag;
	}

	@Test(description = "Verify Nri Page top scroll section", groups = { "Nri Page" }, priority = 1, enabled = false)
	public void verifyNriTopSection() {
		softAssert = new SoftAssert();
		List<String> expectedMenuItemList = new ArrayList<>(
				Arrays.asList(testData.get("NRIPageTopNavigation").split("\\s*,\\s*")));

		List<String> actualMenuItemList = wapListingMethods.getTopSectionHeaders();
		Assert.assertTrue(VerificationUtil.listActualInExpected(actualMenuItemList, expectedMenuItemList),
				"Actual list of menu items is not matching the expected list of menu items. Missing items are->"
						+ VerificationUtil.getMissingMenuOptionList());
	}

	@Test(description = "Verify Top news of Nri Page", groups = { "Nri Page" }, priority = 0)
	public void verifyNriTopNews() {
		softAssert = new SoftAssert();
		int articleCount = 5;

		List<String> topNewsStories = VerificationUtil.getLinkHrefList(wapListingMethods.getTopHeadlines());
		int count = topNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under top section should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> topNewsDup = VerificationUtil.isListUnique(topNewsStories);
		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>- Top News Section has duplicate stories, repeating story(s)->" + topNewsDup);
		wapListingMethods.getTopHeadlinesHref().forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200,
					"<br>- Top News link <a href=" + keyword + ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Nri Page sub section", groups = {
			"NRI Page" }, dataProvider = "subSections", priority = 2)
	public void verifyNriSubSection(String subSection) {
		softAssert = new SoftAssert();
		int articleCount = 4;

		String sectionName = subSection;

		List<String> newsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionName));
		int count = newsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under " + subSection
				+ " sections should be more than " + articleCount + " in number, instead found " + count);
		List<String> NewsDup = VerificationUtil.isListUnique(newsStories);
		softAssert.assertTrue(NewsDup.isEmpty(),
				"<br>- " + subSection + " Section has duplicate stories, repeating story(s)->" + NewsDup);
		wapListingMethods.getSectionNewsHref(sectionName).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top " + subSection + " link <a href=" + keyword
					+ ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@DataProvider
	public String[] subSections() {
		String[] sections = { "Migrate", "Work", "Study", "Visit", "Invest", "Latest Stories", "Top News from ET" };

		return sections;

	}

	@Test(description = "this test verifies the read more from section links on the page")
	public void verifyReadMoreFromSectionLinks() {
		softAssert = new SoftAssert();

		List<String> readMoreLinksList = wapListingMethods.getReadMoreFromSectionlinks();
		List<String> linksDup = VerificationUtil.isListUnique(readMoreLinksList);
		softAssert.assertTrue(linksDup.isEmpty(),
				"<br>- Read More from links have duplicate links, repeating story(s)->" + linksDup);
		readMoreLinksList.forEach(keyword -> {
			int responseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(responseCode, 200,
					"<br>- Read more from section link <a href=" + keyword + ">link</a> is throwing " + responseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify trending in us section on Nri Page", groups = { "Nri Page" })
	public void verifyNriTrendingInUSSectionNews() {
		softAssert = new SoftAssert();
		int articleCount = 5;

		List<String> topNewsStories = nriPageMethods.getTrendingInUslinks();
		int count = topNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Trending in US section should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> topNewsDup = VerificationUtil.isListUnique(topNewsStories);
		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>- Trending in US Section has duplicate stories, repeating story(s)->" + topNewsDup);
		topNewsStories.forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200,
					"<br>- Trending in US Section News link <a href=" + keyword + ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}
	
	
	@Test(description = "Verify Trending terms of NRI Page", groups = { "NRI Page" }, priority = 0)
	public void verifyNRITrendingTerms() {
		softAssert = new SoftAssert();
		int articleCount = 4;

		List<String> trendingLinks = nriPageMethods.getTopTrendinglinks();
		int count = trendingLinks.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Link under top trending terms should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> topNewsDup = VerificationUtil.isListUnique(trendingLinks);
		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>- Top trending terms Section has duplicate stories, repeating story(s)->" + topNewsDup);
		trendingLinks.forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top trending terms link <a href=" + keyword
					+ ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

}
