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
import common.utilities.ExcelUtil;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import pwa.pagemethods.HomePageMethods;
import pwa.pagemethods.MenuPageMethods;
import pwa.pagemethods.PwaListingPageMethods;
import pwa.pagemethods.RisePageMethods;

public class RisePage extends BaseTest{
	private String wapUrl;
	private HomePageMethods homePageMethods;
	private MenuPageMethods menuPageMethods;
	private RisePageMethods risePageMethods;
	String excelPath = "MenuSubMenuData";
	Map<String, String> testData;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl = BaseTest.baseUrl;
		launchBrowser(wapUrl);
		homePageMethods = new HomePageMethods(driver);
		risePageMethods = new RisePageMethods(driver);
		menuPageMethods = new MenuPageMethods(driver);
		testData = ExcelUtil.getTestDataRow(excelPath, "verifyRiseTopNavigation", 1);
		Assert.assertTrue(openRiseSection(),"Unable to open rise section");
	}

	public boolean openRiseSection() {
		homePageMethods.clickMenuIcon();
		WaitUtil.sleep(2000);
		boolean flag=menuPageMethods.clickMenuOption("RISE");
		WaitUtil.sleep(2000);
		return flag;
	}

/*	@Test(description = "Verify Rise Page top scroll section", groups = { "Rise Page" }, priority = 0)
	public void verifyRiseTopSection() {
		softAssert = new SoftAssert();
		List<String> expectedMenuItemList = new ArrayList<>(Arrays.asList(testData.get("RisePageTopNavigation").split("\\s*,\\s*")));
		List<String> actualMenuItemList = risePageMethods.getTopSectionHeaders();
		Assert.assertTrue(VerificationUtil.listActualInExpected(expectedMenuItemList, actualMenuItemList),
				"Actual list of menu items is not matching the expected list of menu items. Missing items are->" + VerificationUtil
						.getMissingMenuOptionList());
	}*/

	@Test(description = "Verify recency of articles", groups = { "Rise Page" }, priority = 1, enabled = false)
	public void verifyTopNewsRecency() {
		softAssert = new SoftAssert();

		Map<String, Boolean> headlineRecency = new PwaListingPageMethods(driver).checkRecency(1);
		for (Map.Entry<String, Boolean> entry : headlineRecency.entrySet()) {
			softAssert.assertTrue(entry.getValue(), "Article Recency verification for " + entry.getKey());
		}
		softAssert.assertAll();
		openRiseSection();
	}

	@Test(description = "Verify Top news of Rise Page", groups = { "Rise Page" }, priority = 2)
	public void verifyRiseTopNews() {
		softAssert = new SoftAssert();
		int articleCount = 10;

		List<String> topNewsStories = VerificationUtil.getLinkHrefList(risePageMethods.getTopSectionNews());
		int count = topNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under top section should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> topNewsDup = VerificationUtil.isListUnique(topNewsStories);
		softAssert.assertTrue(topNewsDup.isEmpty(), "<br>- Top News Section has duplicate stories, repeating story(s)->" + topNewsDup);
		risePageMethods.getTopSectionNewsHref().forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top News link <a href=" + keyword + ">link</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}
	//*[@id='subSections']//a[contains(text(),'SME Sector')]/ancestor::div/div[contains(@class,'card')]/a
	@Test(description = "Verify Rise page SME Sector section", groups = { "Rise Page" }, priority = 3)
	public void verifySMESectorSection() {
		softAssert = new SoftAssert();
		int articleCount = 5;
		String sectionDiv = "SME Sector";

		//String sectionLink = risePageMethods.getSectionNewsLink(sectionDiv);
		//softAssert.assertTrue(sectionLink.contains("sme-sector"), "<br>- Link under heading of the section is not of SME Sector");
		//int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		//softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> smeSectorNewsStories = VerificationUtil.getLinkTextList(risePageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = smeSectorNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under SME Sector sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> smeSectorNewsDup = VerificationUtil.isListUnique(smeSectorNewsStories);
		softAssert.assertTrue(smeSectorNewsDup.isEmpty(), "<br>- SME Sector Section has duplicate stories, repeating story(s)->" + smeSectorNewsDup);
		risePageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top SME Sector link <a href=" + keyword + ">link</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Rise page Startups section", groups = { "Rise Page" }, priority = 4)
	public void verifyStartupsSection() {
		softAssert = new SoftAssert();
		int articleCount = 5;
		String sectionDiv = "Startups";

		//String sectionLink = risePageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("startups"), "<br>- Link under heading of the section is not of Startups");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> startupsNewsStories = VerificationUtil.getLinkTextList(risePageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = startupsNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Startups sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> startupsSectorNewsDup = VerificationUtil.isListUnique(startupsNewsStories);
		softAssert.assertTrue(startupsSectorNewsDup.isEmpty(), "<br>- Startups Section has duplicate stories, repeating story(s)->"
				+ startupsSectorNewsDup);
		risePageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Startups link <a href=" + keyword + ">link</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Rise page Policy & Trends section", groups = { "Rise Page" }, priority = 5)
	public void verifyPolicySection() {
		softAssert = new SoftAssert();
		int articleCount = 5;
		String sectionDiv = "Policy & Trends";
//
//		String sectionLink = risePageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("policy-trends"), "<br>- Link under heading of the section is not of Policy & Trends");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> policyNewsStories = VerificationUtil.getLinkTextList(risePageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = policyNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Policy & Trends sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> policyNewsDup = VerificationUtil.isListUnique(policyNewsStories);
		softAssert.assertTrue(policyNewsDup.isEmpty(), "<br>- Policy & Trends Section has duplicate stories, repeating story(s)->" + policyNewsDup);
		risePageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Policy & Trends link <a href=" + keyword + ">link</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Rise page Entrepreneurship section", groups = { "Rise Page" }, priority = 6)
	public void verifyEntrepreneurshipSection() {
		softAssert = new SoftAssert();
		int articleCount = 5;
		String sectionDiv = "Entrepreneurship";

//		String sectionLink = risePageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("entrepreneurship"), "<br>- Link under heading of the section is not of Entrepreneurship");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> entrepreneurshipNewsStories = VerificationUtil.getLinkTextList(risePageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = entrepreneurshipNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Entrepreneurship sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> entrepreneurshipNewsDup = VerificationUtil.isListUnique(entrepreneurshipNewsStories);
		softAssert.assertTrue(entrepreneurshipNewsDup.isEmpty(), "<br>- Entrepreneurship Section has duplicate stories, repeating story(s)->"
				+ entrepreneurshipNewsDup);
		risePageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Entrepreneurship link <a href=" + keyword + ">link</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Rise page Money section", groups = { "Rise Page" }, priority = 7)
	public void verifyMoneySection() {
		softAssert = new SoftAssert();
		int articleCount = 5;
		String sectionDiv = "Money";
//
//		String sectionLink = risePageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("money"), "<br>- Link under heading of the section is not of Money");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> moneyNewsStories = VerificationUtil.getLinkTextList(risePageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = moneyNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Money sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> moneyNewsDup = VerificationUtil.isListUnique(moneyNewsStories);
		softAssert.assertTrue(moneyNewsDup.isEmpty(), "<br>- Money Section has duplicate stories, repeating story(s)->" + moneyNewsDup);
		risePageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Money link <a href=" + keyword + ">link</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Rise page Security-Tech section", groups = { "Rise Page" }, priority = 8)
	public void verifySecuritySection() {
		softAssert = new SoftAssert();
		int articleCount = 5;
		String sectionDiv = "Security-Tech";

//		String sectionLink = risePageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("security-tech"), "<br>- Link under heading of the section is not of Security-Tech");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> securityNewsStories = VerificationUtil.getLinkTextList(risePageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = securityNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Security-Tech sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> securityNewsDup = VerificationUtil.isListUnique(securityNewsStories);
		softAssert.assertTrue(securityNewsDup.isEmpty(), "<br>- Security-Tech Section has duplicate stories, repeating story(s)->" + securityNewsDup);
		risePageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Security-Tech link <a href=" + keyword + ">link</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Rise page Marketing-Branding section", groups = { "Rise Page" }, priority = 9)
	public void verifyMarketingSection() {
		softAssert = new SoftAssert();
		int articleCount = 5;
		String sectionDiv = "Marketing-Branding";
//
//		String sectionLink = risePageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("marketing-branding"), "<br>- Link under heading of the section is not of Marketing-Branding");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> marketingNewsStories = VerificationUtil.getLinkTextList(risePageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = marketingNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Marketing-Branding sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> marketingNewsDup = VerificationUtil.isListUnique(marketingNewsStories);
		softAssert.assertTrue(marketingNewsDup.isEmpty(), "<br>- Marketing-Branding Section has duplicate stories, repeating story(s)->"
				+ marketingNewsDup);
		risePageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Marketing-Branding link <a href=" + keyword + ">link</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Rise page HR-Leadership section", groups = { "Rise Page" }, priority = 10)
	public void verifyHrLeadershipSection() {
		softAssert = new SoftAssert();
		int articleCount = 5;
		String sectionDiv = "HR-Leadership";
//		String sectionLink = risePageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("hr-leadership"), "<br>- Link under heading of the section is not of HR-Leadership");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> hrNewsStories = VerificationUtil.getLinkTextList(risePageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = hrNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under HR-Leadership sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> hrNewsDup = VerificationUtil.isListUnique(hrNewsStories);
		softAssert.assertTrue(hrNewsDup.isEmpty(), "<br>- HR-Leadership Section has duplicate stories, repeating story(s)->" + hrNewsDup);
		risePageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top HR-Leadership link <a href=" + keyword + ">link</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Rise page Legal section", groups = { "Rise Page" }, priority = 11)
	public void verifyLegalSection() {
		softAssert = new SoftAssert();
		int articleCount = 5;
		String sectionDiv = "Legal";
//
//		String sectionLink = risePageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("legal"), "<br>- Link under heading of the section is not of Legal");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
	List<String> legalNewsStories = VerificationUtil.getLinkTextList(risePageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = legalNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Legal sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> legalNewsDup = VerificationUtil.isListUnique(legalNewsStories);
		softAssert.assertTrue(legalNewsDup.isEmpty(), "<br>- Legal Section has duplicate stories, repeating story(s)->" + legalNewsDup);
		risePageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Legal link <a href=" + keyword + ">link</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}
