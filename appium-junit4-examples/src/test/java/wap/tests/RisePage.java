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
import wap.pagemethods.RisePageMethods;
import wap.pagemethods.WapListingPageMethods;

public class RisePage extends BaseTest {

	private String wapUrl;
	private HomePageMethods homePageMethods;
	private MenuPageMethods menuPageMethods;
	private RisePageMethods risePageMethods;
	private AdTechMethods adTechMethods;
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
		risePageMethods = new RisePageMethods(driver);
		menuPageMethods = new MenuPageMethods(driver);
		adTechMethods = new AdTechMethods(driver);
		wapListingMethods = new WapListingPageMethods(driver);
		testData = ExcelUtil.getTestDataRow(excelPath, "verifyRiseTopNavigation", 1);
		// Assert.assertTrue(openRiseSection(),"Unable to open rise section");
	}

	public boolean openRiseSection() {
		homePageMethods.clickFooterMenuICon();
		WaitUtil.sleep(2000);
		flag = menuPageMethods.clickMenuOptionReact("RISE");
		WaitUtil.sleep(2000);
		wapListingMethods.removeGoogleVignetteAd();
		WaitUtil.sleep(3000);
		if (driver.getCurrentUrl().contains("small-biz"))
			flag = true;
		else {
			flag = false;
		}
		return flag;
	}

	@Test(description = "Verify Rise Page top scroll section", groups = { "Rise Page" }, priority = 0)
	public void verifyRiseTopSection() {
		if (!flag)
		softAssert = new SoftAssert();
		openRiseSection();
		List<String> expectedMenuItemList = new ArrayList<>(
				Arrays.asList(testData.get("RisePageTopNavigation").split("\\s*,\\s*")));
		List<String> actualMenuItemList = wapListingMethods.getTopSectionHeaders();
		Assert.assertTrue(VerificationUtil.listActualInExpected(expectedMenuItemList, actualMenuItemList),
				"Actual list of menu items is not matching the expected list of menu items. Missing items are->"
						+ VerificationUtil.getMissingMenuOptionList());
	}

	@Test(description = "Verify recency of articles", groups = { "Rise Page" }, priority = 1, enabled = false)
	public void verifyTopNewsRecency() {
		softAssert = new SoftAssert();
		if (!flag)
			openRiseSection();
		Map<String, Boolean> headlineRecency = new WapListingPageMethods(driver).checkRecency(1);
		for (Map.Entry<String, Boolean> entry : headlineRecency.entrySet()) {
			softAssert.assertTrue(entry.getValue(), "Article Recency verification for " + entry.getKey());
		}
		softAssert.assertAll();
		openRiseSection();
	}

	@Test(description = "Verify Top news of Rise Page", groups = { "Rise Page" }, priority = 2)
	public void verifyRiseTopNews() {
		softAssert = new SoftAssert();
		int articleCount = 5;
		if (!flag)
			openRiseSection();
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

	@Test(description = "Verify Rise page SME Sector section", groups = { "Rise Page" }, priority = 3, enabled = false)
	public void verifySMESectorSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionDiv = "SME Sector";
		if (!flag)
			openRiseSection();

		// String sectionLink = risePageMethods.getSectionNewsLink(sectionDiv);
		// softAssert.assertTrue(sectionLink.contains("sme-sector"), "<br>- Link
		// under heading of the section is not of SME Sector");
		// int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		// softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		// sectionLink + ">Link under heading </a> is throwing " +
		// responseCode);

		List<String> smeSectorNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionDiv));
		int count = smeSectorNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under SME Sector sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> smeSectorNewsDup = VerificationUtil.isListUnique(smeSectorNewsStories);
		softAssert.assertTrue(smeSectorNewsDup.isEmpty(),
				"<br>- SME Sector Section has duplicate stories, repeating story(s)->" + smeSectorNewsDup);
		wapListingMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200,
					"<br>- Top SME Sector link <a href=" + keyword + ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Rise page Startups section", groups = { "Rise Page" }, priority = 4, enabled = false)
	public void verifyStartupsSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionDiv = "Startups";
		if (!flag)
			openRiseSection();
		/*
		 * String sectionLink = risePageMethods.getSectionNewsLink(sectionDiv);
		 * softAssert.assertTrue(sectionLink.contains("startups"),
		 * "<br>- Link under heading of the section is not of Startups"); int
		 * responseCode = HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> startupsNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionDiv));
		int count = startupsNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Startups sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> startupsSectorNewsDup = VerificationUtil.isListUnique(startupsNewsStories);
		softAssert.assertTrue(startupsSectorNewsDup.isEmpty(),
				"<br>- Startups Section has duplicate stories, repeating story(s)->" + startupsSectorNewsDup);
		wapListingMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200,
					"<br>- Top Startups link <a href=" + keyword + ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Rise page Policy & Trends section", groups = {
			"Rise Page" }, priority = 5, enabled = false)
	public void verifyPolicySection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionDiv = "Policy & Trends";
		if (!flag)
			openRiseSection();
		/*
		 * String sectionLink = risePageMethods.getSectionNewsLink(sectionDiv);
		 * softAssert.assertTrue(sectionLink.contains("policy-trends"),
		 * "<br>- Link under heading of the section is not of Policy & Trends");
		 * int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> policyNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionDiv));
		int count = policyNewsStories.size();
		softAssert.assertTrue(count >= articleCount,
				"<br>- Headlines under Policy & Trends sections should be more than " + articleCount
						+ " in number, instead found " + count);
		List<String> policyNewsDup = VerificationUtil.isListUnique(policyNewsStories);
		softAssert.assertTrue(policyNewsDup.isEmpty(),
				"<br>- Policy & Trends Section has duplicate stories, repeating story(s)->" + policyNewsDup);
		wapListingMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Policy & Trends link <a href=" + keyword
					+ ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Rise page Entrepreneurship section", groups = {
			"Rise Page" }, priority = 6, enabled = false)
	public void verifyEntrepreneurshipSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionDiv = "Entrepreneurship";
		if (!flag)
			openRiseSection();
		/*
		 * String sectionLink = risePageMethods.getSectionNewsLink(sectionDiv);
		 * softAssert.assertTrue(sectionLink.contains("entrepreneurship"),
		 * "<br>- Link under heading of the section is not of Entrepreneurship"
		 * ); int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> entrepreneurshipNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionDiv));
		int count = entrepreneurshipNewsStories.size();
		softAssert.assertTrue(count >= articleCount,
				"<br>- Headlines under Entrepreneurship sections should be more than " + articleCount
						+ " in number, instead found " + count);
		List<String> entrepreneurshipNewsDup = VerificationUtil.isListUnique(entrepreneurshipNewsStories);
		softAssert.assertTrue(entrepreneurshipNewsDup.isEmpty(),
				"<br>- Entrepreneurship Section has duplicate stories, repeating story(s)->" + entrepreneurshipNewsDup);
		wapListingMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Entrepreneurship link <a href=" + keyword
					+ ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Rise page Money section", groups = { "Rise Page" }, priority = 7)
	public void verifyMoneySection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionDiv = "Money";
		if (!flag)
			openRiseSection();
		/*
		 * String sectionLink = risePageMethods.getSectionNewsLink(sectionDiv);
		 * softAssert.assertTrue(sectionLink.contains("money"),
		 * "<br>- Link under heading of the section is not of Money"); int
		 * responseCode = HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> moneyNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionDiv));
		int count = moneyNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Money sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> moneyNewsDup = VerificationUtil.isListUnique(moneyNewsStories);
		softAssert.assertTrue(moneyNewsDup.isEmpty(),
				"<br>- Money Section has duplicate stories, repeating story(s)->" + moneyNewsDup);
		wapListingMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200,
					"<br>- Top Money link <a href=" + keyword + ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Rise page Security-Tech section", groups = { "Rise Page" }, priority = 8)
	public void verifySecuritySection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionDiv = "IT";
		if (!flag)
			openRiseSection();
		/*
		 * String sectionLink = risePageMethods.getSectionNewsLink(sectionDiv);
		 * softAssert.assertTrue(sectionLink.contains("security-tech"),
		 * "<br>- Link under heading of the section is not of Security-Tech");
		 * int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */
		List<String> securityNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionDiv));
		int count = securityNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Security-Tech sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> securityNewsDup = VerificationUtil.isListUnique(securityNewsStories);
		softAssert.assertTrue(securityNewsDup.isEmpty(),
				"<br>- Security-Tech Section has duplicate stories, repeating story(s)->" + securityNewsDup);
		wapListingMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Security-Tech link <a href=" + keyword
					+ ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Rise page Marketing-Branding section", groups = { "Rise Page" }, priority = 9)
	public void verifyMarketingSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionDiv = "Marketing-Branding";
		if (!flag)
			openRiseSection();
		/*
		 * String sectionLink = risePageMethods.getSectionNewsLink(sectionDiv);
		 * softAssert.assertTrue(sectionLink.contains("marketing-branding"),
		 * "<br>- Link under heading of the section is not of Marketing-Branding"
		 * ); int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> marketingNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionDiv));
		int count = marketingNewsStories.size();
		softAssert.assertTrue(count >= articleCount,
				"<br>- Headlines under Marketing-Branding sections should be more than " + articleCount
						+ " in number, instead found " + count);
		List<String> marketingNewsDup = VerificationUtil.isListUnique(marketingNewsStories);
		softAssert.assertTrue(marketingNewsDup.isEmpty(),
				"<br>- Marketing-Branding Section has duplicate stories, repeating story(s)->" + marketingNewsDup);
		wapListingMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Marketing-Branding link <a href=" + keyword
					+ ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Rise page HR-Leadership section", groups = { "Rise Page" }, priority = 10)
	public void verifyHrLeadershipSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionDiv = "HR-Leadership";
		if (!flag)
			openRiseSection();
		/*
		 * String sectionLink = risePageMethods.getSectionNewsLink(sectionDiv);
		 * softAssert.assertTrue(sectionLink.contains("hr-leadership"),
		 * "<br>- Link under heading of the section is not of HR-Leadership");
		 * int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> hrNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionDiv));
		int count = hrNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under HR-Leadership sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> hrNewsDup = VerificationUtil.isListUnique(hrNewsStories);
		softAssert.assertTrue(hrNewsDup.isEmpty(),
				"<br>- HR-Leadership Section has duplicate stories, repeating story(s)->" + hrNewsDup);
		wapListingMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top HR-Leadership link <a href=" + keyword
					+ ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Rise page Legal section", groups = { "Rise Page" }, priority = 11)
	public void verifyLegalSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionDiv = "Legal";
		if (!flag)
			openRiseSection();
		/*
		 * String sectionLink = risePageMethods.getSectionNewsLink(sectionDiv);
		 * softAssert.assertTrue(sectionLink.contains("legal"),
		 * "<br>- Link under heading of the section is not of Legal"); int
		 * responseCode = HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> legalNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionDiv));
		int count = legalNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Legal sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> legalNewsDup = VerificationUtil.isListUnique(legalNewsStories);
		softAssert.assertTrue(legalNewsDup.isEmpty(),
				"<br>- Legal Section has duplicate stories, repeating story(s)->" + legalNewsDup);
		wapListingMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200,
					"<br>- Top Legal link <a href=" + keyword + ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Rise Page sub section", groups = {
			"Rise Page" }, dataProvider = "subSections", priority = 12)
	public void verifyRiseSubSection(String subSection) {
		softAssert = new SoftAssert();
		if (!flag)
			openRiseSection();
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
		String[] sections = {"Trade", "IT" };

		return sections;

	}
	
	@Test(description = "this test verifies the read more from section links on the page")
	public void verifyReadMoreFromSectionLinks() {
		softAssert = new SoftAssert();
		if (!flag)
			openRiseSection();
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


	@Test(description = " This test verifies the Google and colombia ads on Rise Listing page", groups = {
			"Rise Page" }, priority = 12, enabled = false)
	public void verifyAds() {
		SoftAssert softAssert = new SoftAssert();
		WaitUtil.sleep(2000);
		if (!flag)
			openRiseSection();
		Map<String, String> expectedIdsMap = Config.getAllKeys("wap_adIdLocation");
		softAssert.assertTrue(adTechMethods.getDisplayedAdIds().size() > 0, "No google ads shown on Rise Listing Page");
		if (adTechMethods.getDisplayedAdIds().size() > 0) {
			expectedIdsMap.forEach((K, V) -> {
				if (!K.contains("HP"))
					softAssert.assertTrue(adTechMethods.matchIdsWithRegex(K),
							"Following ad(s) is/are not shown " + expectedIdsMap.get(K) + " on Rise Listing Page");
			});
		}
		List<String> ctnAds = adTechMethods.getAllColombiaAds();
		if (ctnAds.size() > 0) {
			List<String> missingAds = adTechMethods.getMissingColumbiaAds();
			softAssert.assertFalse(missingAds.size() > 0, "Following colombia ad(s) is/are not shown " + missingAds);
		} else
			softAssert.assertTrue(false, "No colombia Ads found on Rise Listing Page");

		softAssert.assertAll();
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
