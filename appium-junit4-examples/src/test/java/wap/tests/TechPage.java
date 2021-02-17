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
import wap.pagemethods.WapListingPageMethods;

public class TechPage extends BaseTest {
	private String wapUrl;
	private HomePageMethods homePageMethods;
	private MenuPageMethods menuPageMethods;
	private WapListingPageMethods wapListingMethods;
	String excelPath = "MenuSubMenuData";
	Map<String, String> testData;
	SoftAssert softAssert;
	boolean flag;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl = BaseTest.baseUrl;
		launchBrowser(wapUrl);
		homePageMethods = new HomePageMethods(driver);
		menuPageMethods = new MenuPageMethods(driver);
		wapListingMethods = new WapListingPageMethods(driver);
		testData = ExcelUtil.getTestDataRow(excelPath, "verifyTechTopNavigation", 1);

		Assert.assertTrue(openTechSection(), "Unable to click tech tab");
	}

	public boolean openTechSection() {
		homePageMethods.clickFooterMenuICon();
		WaitUtil.sleep(2000);
		flag = menuPageMethods.clickMenuOptionReact("Tech");
		WaitUtil.sleep(2000);
		if (driver.getCurrentUrl().contains("tech"))
			flag = true;
		return flag;
	}

	@Test(description = "Verify Tech Page top scroll section", groups = { "Tech Page" }, priority = 1, enabled = false)
	public void verifyTechTopSection() {
		softAssert = new SoftAssert();
		List<String> expectedMenuItemList = new ArrayList<>(
				Arrays.asList(testData.get("TechPageTopNavigation").split("\\s*,\\s*")));

		List<String> actualMenuItemList = wapListingMethods.getTopSectionHeaders();
		Assert.assertTrue(VerificationUtil.listActualInExpected(actualMenuItemList, expectedMenuItemList),
				"Actual list of menu items is not matching the expected list of menu items. Missing items are->"
						+ VerificationUtil.getMissingMenuOptionList());
	}

	@Test(description = "Verify Top news of Tech Page", groups = { "Tech Page" }, priority = 0)
	public void verifyTechTopNews() {
		softAssert = new SoftAssert();
		int articleCount = 5;

		List<String> topNewsStories = wapListingMethods.getTechTopNewsText();
		int count = topNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under top section should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> topNewsDup = VerificationUtil.isListUnique(topNewsStories);
		softAssert.assertTrue(topNewsDup.isEmpty(),
				"<br>- Top News Section has duplicate stories, repeating story(s)->" + topNewsDup);
		wapListingMethods.getTechTopNewslinks().forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200,
					"<br>- Top News link <a href=" + keyword + ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Tech Page hardware section", groups = { "Tech Page" }, priority = 2, enabled = false)
	public void verifyTechHardwareSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;

		String sectionName = "Hardware";

		List<String> techHardwareStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionName));
		int count = techHardwareStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Hardware sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> hardwareNewsDup = VerificationUtil.isListUnique(techHardwareStories);
		softAssert.assertTrue(hardwareNewsDup.isEmpty(),
				"<br>- Harwdare Section has duplicate stories, repeating story(s)->" + hardwareNewsDup);
		wapListingMethods.getSectionNewsHref(sectionName).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200,
					"<br>- Top hardware link <a href=" + keyword + ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify tech page software section", groups = { "Tech Page" }, priority = 3, enabled = false)
	public void verifyTechSoftwareSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionName = "Software";

		List<String> softwareNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionName));
		int count = softwareNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Software sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> softwareNewsDup = VerificationUtil.isListUnique(softwareNewsStories);
		softAssert.assertTrue(softwareNewsDup.isEmpty(),
				"<br>- Software Section has duplicate stories, repeating story(s)->" + softwareNewsDup);
		wapListingMethods.getSectionNewsHref(sectionName).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200,
					"<br>- Top software link <a href=" + keyword + ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify tech page internet section", groups = { "Tech Page" }, priority = 4, enabled = false)
	public void verifyTechInternetSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionName = "Internet";

		List<String> internetNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionName));
		int count = internetNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under internet sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> internetNewsDup = VerificationUtil.isListUnique(internetNewsStories);
		softAssert.assertTrue(internetNewsDup.isEmpty(),
				"<br>- Internet Section has duplicate stories, repeating story(s)->" + internetNewsDup);
		wapListingMethods.getSectionNewsHref(sectionName).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200,
					"<br>- Top internet link <a href=" + keyword + ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Tech page ITeS section", groups = { "Tech Page" }, priority = 5, enabled = false)
	public void verifyTechItesSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionName = "ITeS";

		List<String> itesNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionName));
		int count = itesNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under ITes sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> itesNewsDup = VerificationUtil.isListUnique(itesNewsStories);
		softAssert.assertTrue(itesNewsDup.isEmpty(),
				"<br>- ITes Section has duplicate stories, repeating story(s)->" + itesNewsDup);
		wapListingMethods.getSectionNewsHref(sectionName).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200,
					"<br>- Top ITes link <a href=" + keyword + ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Tech page Tech and Gadgets section", groups = {
			"Tech Page" }, priority = 6, enabled = false)
	public void verifyTechAndGadgetsSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionName = "Tech and Gadgets";

		List<String> itesNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionName));
		int count = itesNewsStories.size();
		softAssert.assertTrue(count >= articleCount,
				"<br>- Headlines under Tech and Gadgets sections should be more than " + articleCount
						+ " in number, instead found " + count);
		List<String> itesNewsDup = VerificationUtil.isListUnique(itesNewsStories);
		softAssert.assertTrue(itesNewsDup.isEmpty(),
				"<br>- Tech and Gadgets Section has duplicate stories, repeating story(s)->" + itesNewsDup);
		wapListingMethods.getSectionNewsHref(sectionName).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Tech and Gadgets link <a href=" + keyword
					+ ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "this test verifies the read more from section links on the page")
	public void verifyReadMoreFromSectionLinks() {
		softAssert = new SoftAssert();
		List<String> readMoreLinksList = wapListingMethods.getTechMoreFromSectionlinks();
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

	@Test(description = "Verify Tech Page sub section", groups = {
			"Tech Page" }, dataProvider = "subSections", priority = 2)
	public void verifyTechSubSection(String subSection) {
		softAssert = new SoftAssert();
		int articleCount = 3;

		String sectionName = subSection;

		List<String> newsStories = wapListingMethods.getTechSubSectionNewsText(sectionName);
		int count = newsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under " + subSection
				+ " sections should be more than " + articleCount + " in number, instead found " + count);
		List<String> NewsDup = VerificationUtil.isListUnique(newsStories);
		softAssert.assertTrue(NewsDup.isEmpty(),
				"<br>- " + subSection + " Section has duplicate stories, repeating story(s)->" + NewsDup);
		wapListingMethods.getTechSubSectionNewsHref(sectionName).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top " + subSection + " link <a href=" + keyword
					+ ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@DataProvider
	public String[] subSections() {
		String[] sections = { "ITES", " Trending Stories", "Startups", "Funding", "Tech & Internet", "The Catalysts",
				"Tech Bytes", "Tech Videos" };

		return sections;

	}

	@Test(description = "Verify Trending terms of Tech Page", groups = { "Tech Page" }, priority = 0)
	public void verifyTechTrendingTerms() {
		softAssert = new SoftAssert();
		int articleCount = 5;

		List<String> trendingLinks = wapListingMethods.getTechTopTrendinglinks();
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
