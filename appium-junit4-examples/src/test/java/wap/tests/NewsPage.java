package wap.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.ExcelUtil;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import wap.pagemethods.AdTechMethods;
import wap.pagemethods.HomePageMethods;
import wap.pagemethods.MenuPageMethods;
import wap.pagemethods.NewsPageMethods;
import wap.pagemethods.WapListingPageMethods;

public class NewsPage extends BaseTest {
	private String wapUrl;
	private NewsPageMethods newsPageMethods;
	private HomePageMethods homePageMethods;
	private MenuPageMethods menuPageMethods;
	private WapListingPageMethods wapListingMethods;
	private AdTechMethods adTechMethods;
	String excelPath = "MenuSubMenuData";
	Map<String, String> testData;
	SoftAssert softAssert;
	boolean flag;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl = BaseTest.baseUrl;
		launchBrowser(wapUrl);
		newsPageMethods = new NewsPageMethods(driver);
		homePageMethods = new HomePageMethods(driver);
		menuPageMethods = new MenuPageMethods(driver);
		adTechMethods = new AdTechMethods(driver);
		wapListingMethods = new WapListingPageMethods(driver);
		testData = ExcelUtil.getTestDataRow(excelPath, "verifyNewsTopNavigation", 1);
		Assert.assertTrue(openNewsSection(), "Unable to click news tab");
	}

	public boolean openNewsSection() {
		homePageMethods.clickFooterMenuICon();
		WaitUtil.sleep(2000);
		flag = menuPageMethods.clickMenuOptionReact("News");
		WaitUtil.sleep(2000);
		wapListingMethods.removeGoogleVignetteAd();
		WaitUtil.sleep(3000);
		if (driver.getCurrentUrl().contains("news"))
			flag = true;
		else {
			flag = false;
		}
		return flag;
	}

	@Test(description = "Verify News Page top scroll section", groups = { "News Page" }, priority = 1)
	public void verifyNewsTopSection() {
		softAssert = new SoftAssert();
		List<String> expectedMenuItemList = new ArrayList<>(
				Arrays.asList(testData.get("NewsPageTopNavigation").split("\\s*,\\s*")));

		List<String> actualMenuItemList = wapListingMethods.getTopSectionHeaders();
		if (!actualMenuItemList.isEmpty())
			Assert.assertTrue(VerificationUtil.listActualInExpected(expectedMenuItemList, actualMenuItemList),
					"Actual list of menu items is not matching the expected list of menu items.Missing items:"
							+ VerificationUtil.getMissingMenuOptionList() + ", Actual List " + actualMenuItemList
							+ " Expected List " + expectedMenuItemList);
		else
			softAssert.assertTrue(false, "Top scroll Menu items missing from New Page");
		
		softAssert.assertAll();
	}

	@Test(description = "Verify Top news of News Page", groups = { "News Page" }, priority = 0)
	public void verifyNewsTopNews() {
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

	@Test(description = "Verify News page Company section", groups = { "News Page" }, priority = 2, enabled = false)
	public void verifyNewsCompanySection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		// String sectionName= "2";
		String sectionName = "Company";
		/*
		 * String sectionLink = newsPageMethods.getSectionNewsLink(sectionName);
		 * Assert.assertTrue(sectionLink.contains("company"),
		 * "<br>- Link under heading of the section is not of company, instead is:"
		 * +sectionLink); int responseCode =
		 * HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> companyNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionName));
		int count = companyNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under company sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> companyNewsDup = VerificationUtil.isListUnique(companyNewsStories);
		softAssert.assertTrue(companyNewsDup.isEmpty(),
				"<br>- Company Section has duplicate stories, repeating story(s)->" + companyNewsDup);
		wapListingMethods.getSectionNewsHref(sectionName).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200,
					"<br>- Top Company link <a href=" + keyword + ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify News page Economy section", groups = { "News Page" }, priority = 3)
	public void verifyNewsEconomySection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionName = "Economy";

		/*
		 * String sectionLink = newsPageMethods.getSectionNewsLink(sectionName);
		 * Assert.assertTrue(sectionLink.contains("economy"),
		 * "<br>- Link under heading of the section is not of economy, instead is:"
		 * +sectionLink); int responseCode =
		 * HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> economyNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionName));
		int count = economyNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Economy sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> economyNewsDup = VerificationUtil.isListUnique(economyNewsStories);
		softAssert.assertTrue(economyNewsDup.isEmpty(),
				"<br>- Economy Section has duplicate stories, repeating story(s)->" + economyNewsDup);
		wapListingMethods.getSectionNewsHref(sectionName).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200,
					"<br>- Top Economy link <a href=" + keyword + ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify News page Politics and Nation section", groups = { "News Page" }, priority = 4)
	public void verifyNewsPoliticsNationSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionName = "Politics and Nation";

		/*
		 * String sectionLink = newsPageMethods.getSectionNewsLink(sectionName);
		 * Assert.assertTrue(sectionLink.contains("politicsnation"),
		 * "<br>- Link under heading of the section is not of politics and nation, instead is:"
		 * +sectionLink); int responseCode =
		 * HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> politicsNationNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionName));
		int count = politicsNationNewsStories.size();
		softAssert.assertTrue(count >= articleCount,
				"<br>- Headlines under politics and Nation sections should be more than " + articleCount
						+ " in number, instead found " + count);
		List<String> politicsNationNewsDup = VerificationUtil.isListUnique(politicsNationNewsStories);
		softAssert.assertTrue(politicsNationNewsDup.isEmpty(),
				"<br>- Politics and Nation Section has duplicate stories, repeating story(s)->"
						+ politicsNationNewsDup);
		wapListingMethods.getSectionNewsHref(sectionName).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Poltics and Nation link <a href=" + keyword
					+ ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify News page Brandwire section", groups = { "News Page" }, priority = 5, enabled = false)
	public void verifyNewsBrandwireSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionName = "Brandwire";

		/*
		 * String sectionLink = newsPageMethods.getSectionNewsLink(sectionName);
		 * Assert.assertTrue(sectionLink.contains("brandwire"),
		 * "<br>- Link under heading of the section is not of Brandwire, instead is:"
		 * +sectionLink); int responseCode =
		 * HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> brandwireNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionName));
		int count = brandwireNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under brandwire sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> brandwireNewsDup = VerificationUtil.isListUnique(brandwireNewsStories);
		softAssert.assertTrue(brandwireNewsDup.isEmpty(),
				"<br>- Brandwire Section has duplicate stories, repeating story(s)->" + brandwireNewsDup);
		wapListingMethods.getSectionNewsHref(sectionName).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200,
					"<br>- Top Brandwire link <a href=" + keyword + ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify News page Defence section", groups = { "News Page" }, priority = 6)
	public void verifyNewsDefenceSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionName = "Defence";

		/*
		 * String sectionLink = newsPageMethods.getSectionNewsLink(sectionName);
		 * Assert.assertTrue(sectionLink.contains("defence"),
		 * "<br>- Link under heading of the section is not of Defence, instead is:"
		 * +sectionLink); int responseCode =
		 * HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> defenceNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionName));
		int count = defenceNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Defence sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> defenceNewsDup = VerificationUtil.isListUnique(defenceNewsStories);
		softAssert.assertTrue(defenceNewsDup.isEmpty(),
				"<br>- Defence Section has duplicate stories, repeating story(s)->" + defenceNewsDup);
		wapListingMethods.getSectionNewsHref(sectionName).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200,
					"<br>- Top Defence link <a href=" + keyword + ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify News page International section", groups = {
			"News Page" }, priority = 7, enabled = false)
	public void verifyNewsInternationalSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionName = "International";

		/*
		 * String sectionLink = newsPageMethods.getSectionNewsLink(sectionName);
		 * Assert.assertTrue(sectionLink.contains("international"),
		 * "<br>- Link under heading of the section is not of International, instead is:"
		 * +sectionLink); int responseCode =
		 * HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> internationalNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionName));
		int count = internationalNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under international section should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> internationalNewsDup = VerificationUtil.isListUnique(internationalNewsStories);
		softAssert.assertTrue(internationalNewsDup.isEmpty(),
				"<br>- International Section has duplicate stories, repeating story(s)->" + internationalNewsDup);
		wapListingMethods.getSectionNewsHref(sectionName).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top International link <a href=" + keyword
					+ ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify News page India Unlimited section", groups = { "News Page" }, priority = 8)
	public void verifyNewsIndiaUnlimitedSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionName = "India Unlimited";

		/*
		 * String sectionLink = newsPageMethods.getSectionNewsLink(sectionName);
		 * Assert.assertTrue(sectionLink.contains("india-unlimited"),
		 * "<br>- Link under heading of the section is not of India Unlimited, instead is:"
		 * +sectionLink); int responseCode =
		 * HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */ List<String> indiaUnlimitedNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionName));
		int count = indiaUnlimitedNewsStories.size();
		softAssert.assertTrue(count >= articleCount,
				"<br>- Headlines under India Unlimited sections should be more than " + articleCount
						+ " in number, instead found " + count);
		List<String> indiaUnlimitedNewsDup = VerificationUtil.isListUnique(indiaUnlimitedNewsStories);
		softAssert.assertTrue(indiaUnlimitedNewsDup.isEmpty(),
				"<br>- India Unlimited Section has duplicate stories, repeating story(s)->" + indiaUnlimitedNewsDup);
		wapListingMethods.getSectionNewsHref(sectionName).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top India Unlimited link <a href=" + keyword
					+ ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify News page Sports section", groups = { "News Page" }, priority = 9, enabled = false)
	public void verifyNewsSportsSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionName = "Sports";

		/*
		 * String sectionLink = newsPageMethods.getSectionNewsLink(sectionName);
		 * Assert.assertTrue(sectionLink.contains("sports"),
		 * "<br>- Link under heading of the section is not of Sports, instead is:"
		 * +sectionLink); int responseCode =
		 * HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> sportsNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionName));
		int count = sportsNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Sports sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> sportsNewsDup = VerificationUtil.isListUnique(sportsNewsStories);
		softAssert.assertTrue(sportsNewsDup.isEmpty(),
				"<br>- Sports Section has duplicate stories, repeating story(s)->" + sportsNewsDup);
		wapListingMethods.getSectionNewsHref(sectionName).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200,
					"<br>- Top Sports link <a href=" + keyword + ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify News page Science section", groups = { "News Page" }, priority = 10)
	public void verifyNewsSciencenSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionName = "Science";

		/*
		 * String sectionLink = newsPageMethods.getSectionNewsLink(sectionName);
		 * Assert.assertTrue(sectionLink.contains("science"),
		 * "<br>- Link under heading of the section is not of Science, instead is:"
		 * +sectionLink); int responseCode =
		 * HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> scienceNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionName));
		int count = scienceNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Science sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> scienceNewsDup = VerificationUtil.isListUnique(scienceNewsStories);
		softAssert.assertTrue(scienceNewsDup.isEmpty(),
				"<br>- Science Section has duplicate stories, repeating story(s)->" + scienceNewsDup);
		wapListingMethods.getSectionNewsHref(sectionName).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200,
					"<br>- Top Science link <a href=" + keyword + ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify News page Environment section", groups = {
			"News Page" }, priority = 11, enabled = false)
	public void verifyNewsEnvironmentSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionName = "Environment";

		/*
		 * String sectionLink = newsPageMethods.getSectionNewsLink(sectionName);
		 * Assert.assertTrue(sectionLink.contains("environment"),
		 * "<br>- Link under heading of the section is not of Environment, instead is:"
		 * +sectionLink); int responseCode =
		 * HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> envNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionName));
		int count = envNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Environment sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> envNewsDup = VerificationUtil.isListUnique(envNewsStories);
		softAssert.assertTrue(envNewsDup.isEmpty(),
				"<br>- Environment Section has duplicate stories, repeating story(s)->" + envNewsDup);
		wapListingMethods.getSectionNewsHref(sectionName).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200,
					"<br>- Top Environment link <a href=" + keyword + ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify recency of articles", groups = { "News Page" }, priority = 12, enabled = false)
	public void verifyTopNewsRecency() {
		softAssert = new SoftAssert();

		Map<String, Boolean> headlineRecency = new WapListingPageMethods(driver).checkRecency(1);
		for (Map.Entry<String, Boolean> entry : headlineRecency.entrySet()) {
			softAssert.assertTrue(entry.getValue(), "Article Recency verification for " + entry.getKey());
		}
		softAssert.assertAll();
	}

	@Test(description = "Verify News Page sub section", groups = {
			"News Page" }, dataProvider = "subSections", priority = 12, enabled = false)
	public void verifyNewsSubSection(String subSection) {
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
			System.out.println(keyword + ":" + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@DataProvider
	public String[] subSections() {
		String[] sections = { "Elections", "ET Explains" };

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

	@Test(description = " This test verifies the Google and colombia ads on News Listing page", groups = {
			"News Page" }, priority = 13, enabled = false)
	public void verifyAds() {
		SoftAssert softAssert = new SoftAssert();
		WaitUtil.sleep(2000);
		Map<String, String> expectedIdsMap = Config.getAllKeys("wap_adIdLocation");
		softAssert.assertTrue(adTechMethods.getDisplayedAdIds().size() > 0, "No google ads shown on News Listing Page");
		if (adTechMethods.getDisplayedAdIds().size() > 0) {
			expectedIdsMap.forEach((K, V) -> {
				if (!K.contains("HP"))
					softAssert.assertTrue(adTechMethods.matchIdsWithRegex(K),
							"Following ad(s) is/are not shown " + expectedIdsMap.get(K) + " on News Listing Page");
			});
		}
		List<String> ctnAds = adTechMethods.getAllColombiaAds();
		if (ctnAds.size() > 0) {
			List<String> missingAds = adTechMethods.getMissingColumbiaAds();
			softAssert.assertFalse(missingAds.size() > 0, "Following colombia ad(s) is/are not shown " + missingAds);
		} else
			softAssert.assertTrue(false, "No colombia Ads found on News Listing Page");

		softAssert.assertAll();
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}