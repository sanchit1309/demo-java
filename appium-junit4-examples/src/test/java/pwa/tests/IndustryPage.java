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
import pwa.pagemethods.IndustryPageMethods;
import pwa.pagemethods.MenuPageMethods;
import pwa.pagemethods.PwaListingPageMethods;

public class IndustryPage extends BaseTest{
	
	private String wapUrl;
	private IndustryPageMethods industryPageMethods;
	private HomePageMethods homePageMethods;
	private MenuPageMethods menuPageMethods;
	String excelPath = "MenuSubMenuData";
	Map<String, String> testData;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl =BaseTest.baseUrl;
		launchBrowser(wapUrl);
		homePageMethods = new HomePageMethods(driver);
		industryPageMethods = new IndustryPageMethods(driver);
		menuPageMethods = new MenuPageMethods(driver);
		testData = ExcelUtil.getTestDataRow(excelPath, "verifyIndustryTopNavigation", 1);
		Assert.assertTrue(openIndustrySection(),"Unable to open industry tab");
	}
	
	public boolean openIndustrySection() {
		homePageMethods.clickMenuIcon();
		WaitUtil.sleep(2000);
		boolean flag=menuPageMethods.clickMenuOption("Industry");
		WaitUtil.sleep(2000);
		return flag;
	}


	@Test(description = "Verify recency of articles", groups = { "Industry Page" }, priority = 0, enabled = false)
	public void verifyTopNewsRecency() {
		softAssert = new SoftAssert();
		Map<String, Boolean> headlineRecency = new PwaListingPageMethods(driver).checkRecency(1);
		for (Map.Entry<String, Boolean> entry : headlineRecency.entrySet()) {
			softAssert.assertTrue(entry.getValue(), "Article Recency verification for " + entry.getKey());
		}
		softAssert.assertAll();
	}

//	@Test(description = "Verify Industry Page top scroll section", groups = { "Industry Page" }, priority = 1)
//	public void verifyIndustryTopSection() {
//		softAssert = new SoftAssert();
//		List<String> expectedMenuItemList = new ArrayList<>(Arrays.asList(testData.get("IndustryPageTopNavigation").split("\\s*,\\s*")));
//
//		List<String> actualMenuItemList = industryPageMethods.getTopSectionHeaders();
//		Assert.assertTrue(VerificationUtil.listActualInExpected(actualMenuItemList, expectedMenuItemList),
//				"Actual list of menu items is not matching the expected list of menu items. Missing items are->" + VerificationUtil
//						.getMissingMenuOptionList());
//	}

	@Test(description = "Verify Top news of Industry Page", groups = { "Industry Page" }, priority = 2)
	public void verifyIndustryTopNews() {
		softAssert = new SoftAssert();
		int articleCount = 10;

		List<String> topNewsStories = VerificationUtil.getLinkHrefList(industryPageMethods.getTopSectionNews());
		int count = topNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under top section should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> topNewsDup = VerificationUtil.isListUnique(topNewsStories);
		softAssert.assertTrue(topNewsDup.isEmpty(), "<br>- Top News Section has duplicate stories, repeating story(s)->" + topNewsDup);
		industryPageMethods.getTopSectionNewsHref().forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top News link <a href=" + keyword + ">"+keyword+"</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Industry page Auto section", groups = { "Industry Page" }, priority = 3)
	public void verifyIndustryAutoSection() {
		softAssert = new SoftAssert();
		int articleCount = 5;
		String sectionDiv = "Auto";

//		String sectionLink = industryPageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("auto"), "<br>- Link under heading of the section is not of auto");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> autoNewsStories = VerificationUtil.getLinkTextList(industryPageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = autoNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under auto sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> autoNewsDup = VerificationUtil.isListUnique(autoNewsStories);
		softAssert.assertTrue(autoNewsDup.isEmpty(), "<br>- Auto Section has duplicate stories, repeating story(s)->" + autoNewsDup);
		industryPageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Auto link <a href=" + keyword + ">"+keyword+"</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Industry page Banking/Finance section", groups = { "Industry Page" }, priority = 4)
	public void verifyIndustryBankingFinanceSection() {
		softAssert = new SoftAssert();
		int articleCount = 5;
		String sectionDiv = "Banking";
//
//		String sectionLink = industryPageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("banking"), "<br>- Link under heading of the section is not of banking/finance");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> bankingNewsStories = VerificationUtil.getLinkTextList(industryPageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = bankingNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under banking sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> bankingNewsDup = VerificationUtil.isListUnique(bankingNewsStories);
		softAssert.assertTrue(bankingNewsDup.isEmpty(), "<br>- Banking Section has duplicate stories, repeating story(s)->" + bankingNewsDup);
		industryPageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Banking/Finance link <a href=" + keyword + ">"+keyword+"</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Industry page Cons. Products section", groups = { "Industry Page" }, priority = 5)
	public void verifyIndustryConsProductsSection() {
		softAssert = new SoftAssert();
		int articleCount = 5;
		String sectionDiv = "Cons";

//		String sectionLink = industryPageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("cons-products"), "<br>- Link under heading of the section is not of cons-products");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> consProdNewsStories = VerificationUtil.getLinkTextList(industryPageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = consProdNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under cons products sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> consProdNewsDup = VerificationUtil.isListUnique(consProdNewsStories);
		softAssert.assertTrue(consProdNewsDup.isEmpty(), "<br>- Cons. Product Section has duplicate stories, repeating story(s)->" + consProdNewsDup);
		industryPageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Cons. Product link <a href=" + keyword + ">"+keyword+"</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Industry page Energy section", groups = { "Industry Page" }, priority = 6)
	public void verifyIndustryEnergySection() {
		softAssert = new SoftAssert();
		int articleCount = 5;
		String sectionDiv = "Energy";

//		String sectionLink = industryPageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("energy"), "<br>- Link under heading of the section is not of Energy");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> energyNewsStories = VerificationUtil.getLinkTextList(industryPageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = energyNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under energy sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> energyNewsDup = VerificationUtil.isListUnique(energyNewsStories);
		softAssert.assertTrue(energyNewsDup.isEmpty(), "<br>- Energy Section has duplicate stories, repeating story(s)->" + energyNewsDup);
		industryPageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Energy link <a href=" + keyword + ">"+keyword+"</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Industry page Ind'l Goods/Svs section", groups = { "Industry Page" }, priority = 7)
	public void verifyIndustryIndlGoodsSection() {
		softAssert = new SoftAssert();
		int articleCount = 5;
		String sectionDiv = "Goods";

//		String sectionLink = industryPageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("indl-goods"), "<br>- Link under heading of the section is not of Ind'l Goods/Svs");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> indlGoodsNewsStories = VerificationUtil.getLinkTextList(industryPageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = indlGoodsNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Ind'l Goods/Svs sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> indlGoodsNewsDup = VerificationUtil.isListUnique(indlGoodsNewsStories);
		softAssert.assertTrue(indlGoodsNewsDup.isEmpty(), "<br>- Ind'l Goods/Svs Section has duplicate stories, repeating story(s)->"
				+ indlGoodsNewsDup);
		industryPageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Ind'l Goods/Svs link <a href=" + keyword + ">"+keyword+"</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Industry page Healthcare/Biotech section", groups = { "Industry Page" }, priority = 8)
	public void verifyIndustryHealthcareSection() {
		softAssert = new SoftAssert();
		int articleCount = 5;
		String sectionDiv = "Healthcare";

//		String sectionLink = industryPageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("healthcare"), "<br>- Link under heading of the section is not of Healthcare/Biotech");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> healthcareNewsStories = VerificationUtil.getLinkTextList(industryPageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = healthcareNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Healthcare/Biotech sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> healthcareNewsDup = VerificationUtil.isListUnique(healthcareNewsStories);
		softAssert.assertTrue(healthcareNewsDup.isEmpty(), "<br>- Healthcare/Biotech Section has duplicate stories, repeating story(s)->"
				+ healthcareNewsDup);
		industryPageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Healthcare/Biotech link <a href=" + keyword + ">"+keyword+"</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Industry page Services section", groups = { "Industry Page" }, priority = 9)
	public void verifyIndustryServicesSection() {
		softAssert = new SoftAssert();
		int articleCount = 5;
		String sectionDiv = "Services";
//
//		String sectionLink = industryPageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("services"), "<br>- Link under heading of the section is not of Services");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> servicesNewsStories = VerificationUtil.getLinkTextList(industryPageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = servicesNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Services sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> servicesNewsDup = VerificationUtil.isListUnique(servicesNewsStories);
		softAssert.assertTrue(servicesNewsDup.isEmpty(), "<br>- Services Section has duplicate stories, repeating story(s)->" + servicesNewsDup);
		industryPageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Services link <a href=" + keyword + ">"+keyword+"</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Industry page Media/Entertainment section", groups = { "Industry Page" }, priority = 10)
	public void verifyIndustryMediaSection() {
		softAssert = new SoftAssert();
		int articleCount = 5;
		String sectionDiv = "Media";

//		String sectionLink = industryPageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("entertainment"), "<br>- Link under heading of the section is not of Media/Entertainment");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> mediaNewsStories = VerificationUtil.getLinkTextList(industryPageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = mediaNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Media/Entertainment sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> mediaNewsDup = VerificationUtil.isListUnique(mediaNewsStories);
		softAssert.assertTrue(mediaNewsDup.isEmpty(), "<br>- Media/Entertainment Section has duplicate stories, repeating story(s)->" + mediaNewsDup);
		industryPageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Media/Entertainment link <a href=" + keyword + ">"+keyword+"</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Industry page Transportation section", groups = { "Industry Page" }, priority = 11)
	public void verifyIndustryTransportationSection() {
		softAssert = new SoftAssert();
		int articleCount = 5;
		String sectionDiv = "Transportation";

//		String sectionLink = industryPageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("transportation"), "<br>- Link under heading of the section is not of Transportation");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> transportationNewsStories = VerificationUtil.getLinkTextList(industryPageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = transportationNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Transportation sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> transportationNewsDup = VerificationUtil.isListUnique(transportationNewsStories);
		softAssert.assertTrue(transportationNewsDup.isEmpty(), "<br>- Transportation Section has duplicate stories, repeating story(s)->"
				+ transportationNewsDup);
		industryPageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Transportation link <a href=" + keyword + ">"+keyword+"</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "Verify Industry page Telecom section", groups = { "Industry Page" }, priority = 12, enabled = true)
	public void verifyIndustryTelecomSection() {
		softAssert = new SoftAssert();
		int articleCount = 5;
		String sectionDiv = "Telecom";
//
//		String sectionLink = industryPageMethods.getSectionNewsLink(sectionDiv);
//		softAssert.assertTrue(sectionLink.contains("telecom"), "<br>- Link under heading of the section is not of Telecom");
//		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
//		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<String> telecomNewsStories = VerificationUtil.getLinkTextList(industryPageMethods.getSectionNewsHeadlines(sectionDiv));
		int count = telecomNewsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under Telecom sections should be more than " + articleCount
				+ " in number, instead found " + count);
		List<String> telecomNewsDup = VerificationUtil.isListUnique(telecomNewsStories);
		softAssert.assertTrue(telecomNewsDup.isEmpty(), "<br>- Telecom Section has duplicate stories, repeating story(s)->" + telecomNewsDup);
		industryPageMethods.getSectionNewsHref(sectionDiv).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top Telecom link <a href=" + keyword + ">"+keyword+"</a> is throwing "
					+ topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}
