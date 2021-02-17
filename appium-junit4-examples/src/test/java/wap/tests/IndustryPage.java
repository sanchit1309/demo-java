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
import wap.pagemethods.IndustryPageMethods;
import wap.pagemethods.MenuPageMethods;
import wap.pagemethods.WapListingPageMethods;

public class IndustryPage extends BaseTest {

	private String wapUrl;
	private IndustryPageMethods industryPageMethods;
	private HomePageMethods homePageMethods;
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
		industryPageMethods = new IndustryPageMethods(driver);
		adTechMethods = new AdTechMethods(driver);
		wapListingMethods = new WapListingPageMethods(driver);
		testData = ExcelUtil.getTestDataRow(excelPath, "verifyIndustryTopNavigation", 1);
		Assert.assertTrue(openIndustrySection(), "Unable to open industry tab");
	}

	public boolean openIndustrySection() {
		homePageMethods.clickFooterMenuICon();
		WaitUtil.sleep(2000);
		flag = new MenuPageMethods(driver).clickMenuOptionReact("Industry");
		wapListingMethods.removeGoogleVignetteAd();
		WaitUtil.sleep(3000);
		if (driver.getCurrentUrl().contains("industry"))
			flag = true;
		else {
			flag = false;
		}
		return flag;
	}

	@Test(description = "Verify recency of articles", groups = { "Industry Page" }, priority = 0, enabled = false)
	public void verifyTopNewsRecency() {
		softAssert = new SoftAssert();
		Map<String, Boolean> headlineRecency = new WapListingPageMethods(driver).checkRecency(1);
		for (Map.Entry<String, Boolean> entry : headlineRecency.entrySet()) {
			softAssert.assertTrue(entry.getValue(), "Article Recency verification for " + entry.getKey());
		}
		softAssert.assertAll();
	}

	@Test(description = "Verify Industry Page top scroll section", groups = { "Industry Page" }, priority = 1)
	public void verifyIndustryTopSection() {
		softAssert = new SoftAssert();
		List<String> expectedMenuItemList = new ArrayList<>(
				Arrays.asList(testData.get("IndustryPageTopNavigation").split("\\s*,\\s*")));

		List<String> actualMenuItemList = wapListingMethods.getTopSectionHeaders();
		Assert.assertTrue(VerificationUtil.listActualInExpected(actualMenuItemList, expectedMenuItemList),
				"Actual list of menu items is not matching the expected list of menu items. Missing items are->"
						+ VerificationUtil.getMissingMenuOptionList());
	}

	@Test(description = "Verify Top news of Industry Page", groups = { "Industry Page" }, priority = 2)
	public void verifyIndustryTopNews() {
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
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top News link <a href=" + keyword + ">" + keyword
					+ "</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Industry page Auto section", groups = { "Industry Page" }, priority = 3)
	public void verifyIndustryAutoSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionDiv = "Auto";

		/*
		 * String sectionLink =
		 * industryPageMethods.getSectionNewsLink(sectionDiv);
		 * softAssert.assertTrue(sectionLink.contains("auto"),
		 * "<br>- Link under heading of the section is not of auto"); int
		 * responseCode = HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> autoNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionDiv));
		int count = autoNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under auto sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> autoNewsDup = VerificationUtil.isListUnique(autoNewsStories);
		softAssert.assertTrue(autoNewsDup.isEmpty(),
				"<br>- Auto Section has duplicate stories, repeating story(s)->" + autoNewsDup);
		wapListingMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Auto link <a href=" + keyword + ">" + keyword
					+ "</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Industry page Banking/Finance section", groups = {
			"Industry Page" }, priority = 4, enabled = false)
	public void verifyIndustryBankingFinanceSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionDiv = "Banking/Finance";

		/*
		 * String sectionLink =
		 * industryPageMethods.getSectionNewsLink(sectionDiv);
		 * softAssert.assertTrue(sectionLink.contains("banking"),
		 * "<br>- Link under heading of the section is not of banking/finance");
		 * int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> bankingNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionDiv));
		int count = bankingNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under banking sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> bankingNewsDup = VerificationUtil.isListUnique(bankingNewsStories);
		softAssert.assertTrue(bankingNewsDup.isEmpty(),
				"<br>- Banking Section has duplicate stories, repeating story(s)->" + bankingNewsDup);
		wapListingMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Banking/Finance link <a href=" + keyword + ">"
					+ keyword + "</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Industry page Cons. Products section", groups = { "Industry Page" }, priority = 5)
	public void verifyIndustryConsProductsSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionDiv = "Cons. Products";

		/*
		 * String sectionLink =
		 * industryPageMethods.getSectionNewsLink(sectionDiv);
		 * softAssert.assertTrue(sectionLink.contains("cons-products"),
		 * "<br>- Link under heading of the section is not of cons-products");
		 * int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> consProdNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionDiv));
		int count = consProdNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under cons products sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> consProdNewsDup = VerificationUtil.isListUnique(consProdNewsStories);
		softAssert.assertTrue(consProdNewsDup.isEmpty(),
				"<br>- Cons. Product Section has duplicate stories, repeating story(s)->" + consProdNewsDup);
		wapListingMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Cons. Product link <a href=" + keyword + ">"
					+ keyword + "</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Industry page Energy section", groups = { "Industry Page" }, priority = 6)
	public void verifyIndustryEnergySection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionDiv = "Energy";

		/*
		 * String sectionLink =
		 * industryPageMethods.getSectionNewsLink(sectionDiv);
		 * softAssert.assertTrue(sectionLink.contains("energy"),
		 * "<br>- Link under heading of the section is not of Energy"); int
		 * responseCode = HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> energyNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionDiv));
		int count = energyNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under energy sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> energyNewsDup = VerificationUtil.isListUnique(energyNewsStories);
		softAssert.assertTrue(energyNewsDup.isEmpty(),
				"<br>- Energy Section has duplicate stories, repeating story(s)->" + energyNewsDup);
		wapListingMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Energy link <a href=" + keyword + ">"
					+ keyword + "</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Industry page Ind'l Goods/Svs section", groups = {
			"Industry Page" }, priority = 7, enabled = false)
	public void verifyIndustryIndlGoodsSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionDiv = "Ind'l Goods/Svs";

		/*
		 * String sectionLink =
		 * industryPageMethods.getSectionNewsLink(sectionDiv);
		 * softAssert.assertTrue(sectionLink.contains("indl-goods"),
		 * "<br>- Link under heading of the section is not of Ind'l Goods/Svs");
		 * int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> indlGoodsNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionDiv));
		int count = indlGoodsNewsStories.size();
		softAssert.assertTrue(count >= articleCount,
				"<br>- Headlines under Ind'l Goods/Svs sections should be more than " + articleCount
						+ " in number, instead found " + count);
		List<String> indlGoodsNewsDup = VerificationUtil.isListUnique(indlGoodsNewsStories);
		softAssert.assertTrue(indlGoodsNewsDup.isEmpty(),
				"<br>- Ind'l Goods/Svs Section has duplicate stories, repeating story(s)->" + indlGoodsNewsDup);
		wapListingMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Ind'l Goods/Svs link <a href=" + keyword + ">"
					+ keyword + "</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Industry page Healthcare/Biotech section", groups = { "Industry Page" }, priority = 8)
	public void verifyIndustryHealthcareSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionDiv = "Healthcare/Biotech";

		/*
		 * String sectionLink =
		 * industryPageMethods.getSectionNewsLink(sectionDiv);
		 * softAssert.assertTrue(sectionLink.contains("healthcare"),
		 * "<br>- Link under heading of the section is not of Healthcare/Biotech"
		 * ); int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */ List<String> healthcareNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionDiv));
		int count = healthcareNewsStories.size();
		softAssert.assertTrue(count >= articleCount,
				"<br>- Headlines under Healthcare/Biotech sections should be more than " + articleCount
						+ " in number, instead found " + count);
		List<String> healthcareNewsDup = VerificationUtil.isListUnique(healthcareNewsStories);
		softAssert.assertTrue(healthcareNewsDup.isEmpty(),
				"<br>- Healthcare/Biotech Section has duplicate stories, repeating story(s)->" + healthcareNewsDup);
		wapListingMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Healthcare/Biotech link <a href=" + keyword
					+ ">" + keyword + "</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Industry page Services section", groups = { "Industry Page" }, priority = 9)
	public void verifyIndustryServicesSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionDiv = "Services";

		/*
		 * String sectionLink =
		 * industryPageMethods.getSectionNewsLink(sectionDiv);
		 * softAssert.assertTrue(sectionLink.contains("services"),
		 * "<br>- Link under heading of the section is not of Services"); int
		 * responseCode = HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> servicesNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionDiv));
		int count = servicesNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Services sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> servicesNewsDup = VerificationUtil.isListUnique(servicesNewsStories);
		softAssert.assertTrue(servicesNewsDup.isEmpty(),
				"<br>- Services Section has duplicate stories, repeating story(s)->" + servicesNewsDup);
		wapListingMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Services link <a href=" + keyword + ">"
					+ keyword + "</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Industry page Media/Entertainment section", groups = {
			"Industry Page" }, priority = 10, enabled = false)
	public void verifyIndustryMediaSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionDiv = "Media/Entertainment";

		/*
		 * String sectionLink =
		 * industryPageMethods.getSectionNewsLink(sectionDiv);
		 * softAssert.assertTrue(sectionLink.contains("entertainment"),
		 * "<br>- Link under heading of the section is not of Media/Entertainment"
		 * ); int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> mediaNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionDiv));
		int count = mediaNewsStories.size();
		softAssert.assertTrue(count >= articleCount,
				"<br>- Headlines under Media/Entertainment sections should be more than " + articleCount
						+ " in number, instead found " + count);
		List<String> mediaNewsDup = VerificationUtil.isListUnique(mediaNewsStories);
		softAssert.assertTrue(mediaNewsDup.isEmpty(),
				"<br>- Media/Entertainment Section has duplicate stories, repeating story(s)->" + mediaNewsDup);
		wapListingMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Media/Entertainment link <a href=" + keyword
					+ ">" + keyword + "</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Industry page Transportation section", groups = { "Industry Page" }, priority = 11)
	public void verifyIndustryTransportationSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionDiv = "Transportation";

		/*
		 * String sectionLink =
		 * industryPageMethods.getSectionNewsLink(sectionDiv);
		 * softAssert.assertTrue(sectionLink.contains("transportation"),
		 * "<br>- Link under heading of the section is not of Transportation");
		 * int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> transportationNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionDiv));
		int count = transportationNewsStories.size();
		softAssert.assertTrue(count >= articleCount,
				"<br>- Headlines under Transportation sections should be more than " + articleCount
						+ " in number, instead found " + count);
		List<String> transportationNewsDup = VerificationUtil.isListUnique(transportationNewsStories);
		softAssert.assertTrue(transportationNewsDup.isEmpty(),
				"<br>- Transportation Section has duplicate stories, repeating story(s)->" + transportationNewsDup);
		wapListingMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Transportation link <a href=" + keyword + ">"
					+ keyword + "</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Industry page Telecom section", groups = {
			"Industry Page" }, priority = 12, enabled = false)
	public void verifyIndustryTelecomSection() {
		softAssert = new SoftAssert();
		int articleCount = 4;
		String sectionDiv = "Telecom";

		/*
		 * String sectionLink =
		 * industryPageMethods.getSectionNewsLink(sectionDiv);
		 * softAssert.assertTrue(sectionLink.contains("telecom"),
		 * "<br>- Link under heading of the section is not of Telecom"); int
		 * responseCode = HTTPResponse.checkResponseCode(sectionLink);
		 * softAssert.assertEquals(responseCode, 200, "<br>- <a href=" +
		 * sectionLink + ">Link under heading </a> is throwing " +
		 * responseCode);
		 */List<String> telecomNewsStories = VerificationUtil
				.getLinkTextList(wapListingMethods.getSectionNewsHeadlines(sectionDiv));
		int count = telecomNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Telecom sections should be more than "
				+ articleCount + " in number, instead found " + count);
		List<String> telecomNewsDup = VerificationUtil.isListUnique(telecomNewsStories);
		softAssert.assertTrue(telecomNewsDup.isEmpty(),
				"<br>- Telecom Section has duplicate stories, repeating story(s)->" + telecomNewsDup);
		wapListingMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Telecom link <a href=" + keyword + ">"
					+ keyword + "</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Industry Page sub section", groups = {
			"Industry Page" }, dataProvider = "subSections", priority = 12, enabled = false)
	public void verifyIndustrySubSection(String subSection) {
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
		String[] sections = {"Environment", "CSR", "Miscellaneous"};

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
	@Test(description = " This test verifies the Google and colombia ads on Industry Listing page", groups = {
			"Industry Page" }, priority = 13, enabled = false)
	public void verifyAds() {
		SoftAssert softAssert = new SoftAssert();
		WaitUtil.sleep(2000);
		Map<String, String> expectedIdsMap = Config.getAllKeys("wap_adIdLocation");
		softAssert.assertTrue(adTechMethods.getDisplayedAdIds().size() > 0,
				"No google ads shown on Industry Listing Page");
		if (adTechMethods.getDisplayedAdIds().size() > 0) {
			expectedIdsMap.forEach((K, V) -> {
				if (!K.contains("HP"))
					softAssert.assertTrue(adTechMethods.matchIdsWithRegex(K),
							"Following ad(s) is/are not shown " + expectedIdsMap.get(K) + " on Industry Listing Page");
			});
		}
		List<String> ctnAds = adTechMethods.getAllColombiaAds();
		if (ctnAds.size() > 0) {
			List<String> missingAds = adTechMethods.getMissingColumbiaAds();
			softAssert.assertFalse(missingAds.size() > 0, "Following colombia ad(s) is/are not shown " + missingAds);
		} else
			softAssert.assertTrue(false, "No colombia Ads found on Industry Listing Page");

		softAssert.assertAll();
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}