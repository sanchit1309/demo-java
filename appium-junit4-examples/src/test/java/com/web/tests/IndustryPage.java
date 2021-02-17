package com.web.tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import web.pagemethods.AdTechMethods;
import web.pagemethods.CommonL1L2PagesMethods;
import web.pagemethods.HeaderPageMethods;
import web.pagemethods.HomePageMethods;
import web.pagemethods.IndustryPageMethods;
import web.pagemethods.WebBaseMethods;

public class IndustryPage extends BaseTest {

	private String baseUrl;
	HomePageMethods homePageMethods;
	IndustryPageMethods industryPageMethods;
	HeaderPageMethods headerPageMethods;
	AdTechMethods adTechMethods;
	CommonL1L2PagesMethods commonL1L2PagesMethods;
	SoftAssert softAssert;

	@BeforeClass
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl + Config.fetchConfigProperty("IndustryUrl");
		launchBrowser(baseUrl);
		homePageMethods = new HomePageMethods(driver);
		industryPageMethods = new IndustryPageMethods(driver);
		headerPageMethods = new HeaderPageMethods(driver);
		adTechMethods = new AdTechMethods(driver);
		commonL1L2PagesMethods = new CommonL1L2PagesMethods(driver);
		WebBaseMethods.scrollToBottom();

	}

	@Test(description = " This test verifies the ads on Industry Listing page")
	public void verifyAds() {
		softAssert = new SoftAssert();
		WaitUtil.sleep(2000);
		Map<String, String> expectedIdsMap = Config.getAllKeys("adIdLocation");
		softAssert.assertTrue(adTechMethods.getDisplayedAdIds().size() > 0,
				"No google ads shown on Industry Listing Page.");
		if (adTechMethods.getDisplayedAdIds().size() > 0) {
			expectedIdsMap.forEach((K, V) -> {
				if (K.contains("ET_ROS") && !(K.contains("_AS_"))) {
					softAssert.assertTrue(adTechMethods.matchIdsWithRegex(K),
							"Following ad(s) is/are not shown " + expectedIdsMap.get(K) + " on Industry Listing Page.");
				}
			});
		}
		List<String> ctnAd = adTechMethods.getMissingColumbiaAds();
		softAssert.assertFalse(ctnAd.size() > 0,
				"Following colombia ad(s) is/are not shown " + ctnAd + " on Industry Listing Page.");
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Auto section on Industry page")
	public void verifyAutoSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = industryPageMethods.getAutoLink();
		softAssert.assertTrue(sectionLink.contains("auto"), "<br>- Link under heading of the section is not of Auto");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> autoHeadlines = industryPageMethods.getAutoHeadlines();
		List<String> autoStories = VerificationUtil.getLinkTextList(industryPageMethods.getAutoHeadlines());
		softAssert.assertTrue(autoStories.size() >= articleCount,
				"<br>- Headlines under Auto sections should be more than " + articleCount + " in number, instead found "
						+ autoStories.size());
		List<String> autoStoriesHref = WebBaseMethods.getListHrefUsingJSE(autoHeadlines);
		autoStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Auto section is throwing " + response);
		});
		/*
		 * List<String> autoNewsDup =
		 * VerificationUtil.isListUnique(autoStories);
		 * softAssert.assertTrue(autoNewsDup.isEmpty(),
		 * "<br>- Auto is having duplicate stories, repeating story(s)->" +
		 * autoNewsDup);
		 */
		softAssert.assertAll();

	}

	@Test(description = "This test verifies Banking/Finance section on Industry page")
	public void verifyBankingFinanceSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = industryPageMethods.getBankingFinanceLink();
		softAssert.assertTrue(sectionLink.contains("banking/finance"),
				"<br>- Link under heading of the section is not of Banking/Finance");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> bankingFinanceHeadlines = industryPageMethods.getBankingFinanceHeadlines();
		List<String> bankingFinanceStories = VerificationUtil
				.getLinkTextList(industryPageMethods.getBankingFinanceHeadlines());
		softAssert.assertTrue(bankingFinanceStories.size() >= articleCount,
				"<br>- Headlines under Banking/Finance sections should be more than " + articleCount + " in number");
		List<String> bankingFinanceStoriesHref = WebBaseMethods.getListHrefUsingJSE(bankingFinanceHeadlines);
		bankingFinanceStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Banking/Finance section is throwing " + response);
		});
		/*
		 * List<String> bankingFinanceNewsDup =
		 * VerificationUtil.isListUnique(bankingFinanceStories);
		 * softAssert.assertTrue(bankingFinanceNewsDup.isEmpty(),
		 * "<br>- Banking/Finance is having duplicate stories, repeating story(s)->"
		 * + bankingFinanceNewsDup);
		 */
		softAssert.assertAll();
	}

	@Test(description = "This test verifies ConsProducts section on Industry page")
	public void verifyConsProductsSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = industryPageMethods.getConsProductsLink();
		softAssert.assertTrue(sectionLink.contains("cons-products"),
				"<br>- Link under heading of the section is not of ConsProducts");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> consProductsHeadlines = industryPageMethods.getConsProductsHeadlines();
		List<String> consProductsStories = VerificationUtil
				.getLinkTextList(industryPageMethods.getConsProductsHeadlines());
		softAssert.assertTrue(consProductsStories.size() >= articleCount,
				"<br>- Headlines under ConsProducts sections should be more than " + articleCount + " in number");
		List<String> consProductsStoriesHref = WebBaseMethods.getListHrefUsingJSE(consProductsHeadlines);
		consProductsStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in ConsProducts section is throwing " + response);
		});
		/*
		 * List<String> consProductsNewsDup =
		 * VerificationUtil.isListUnique(consProductsStories);
		 * softAssert.assertTrue(consProductsNewsDup.isEmpty(),
		 * "<br>- ConsProducts is having duplicate stories, repeating story(s)->"
		 * + consProductsNewsDup);
		 */
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Energy section on Industry page")
	public void verifyEnergySection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = industryPageMethods.getEnergyLink();
		softAssert.assertTrue(sectionLink.contains("energy"),
				"<br>- Link under heading of the section is not of Energy");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> energyHeadlines = industryPageMethods.getEnergyHeadlines();
		List<String> energyStories = VerificationUtil.getLinkTextList(industryPageMethods.getEnergyHeadlines());
		softAssert.assertTrue(energyStories.size() >= articleCount,
				"<br>- Headlines under Energy sections should be more than " + articleCount + " in number");
		List<String> energyStoriesHref = WebBaseMethods.getListHrefUsingJSE(energyHeadlines);
		energyStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Energy section is throwing " + response);
		});
		/*
		 * List<String> energyNewsDup =
		 * VerificationUtil.isListUnique(energyStories);
		 * softAssert.assertTrue(energyNewsDup.isEmpty(),
		 * "<br>- Energy is having duplicate stories, repeating story(s)->" +
		 * energyNewsDup);
		 */
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Ind'l Goods/Svs section on Industry page")
	public void verifyIndlGoodsSvsSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = industryPageMethods.getIndlGoodsSvsLink();
		softAssert.assertTrue(sectionLink.contains("indl-goods/svs"),
				"<br>- Link under heading of the section is not of Ind'l Goods/Svs");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> indlGoodsSvsHeadlines = industryPageMethods.getIndlGoodsSvsHeadlines();
		List<String> indlGoodsSvsStories = VerificationUtil
				.getLinkTextList(industryPageMethods.getIndlGoodsSvsHeadlines());
		softAssert.assertTrue(indlGoodsSvsStories.size() >= articleCount,
				"<br>- Headlines under Ind'l Goods/Svs sections should be more than " + articleCount + " in number");
		List<String> indlGoodsSvsStoriesHref = WebBaseMethods.getListHrefUsingJSE(indlGoodsSvsHeadlines);
		indlGoodsSvsStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Ind'l Goods/Svs section is throwing " + response);
		});
		/*
		 * List<String> indlGoodsSvsNewsDup =
		 * VerificationUtil.isListUnique(indlGoodsSvsStories);
		 * softAssert.assertTrue(indlGoodsSvsNewsDup.isEmpty(),
		 * "<br>- Ind'l Goods/Svs is having duplicate stories, repeating story(s)->"
		 * + indlGoodsSvsNewsDup);
		 */
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Healthcare Biotech section on Industry page")
	public void verifyHealthcareBiotechSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = industryPageMethods.getHealthcareBiotechLink();
		softAssert.assertTrue(sectionLink.contains("healthcare/biotech"),
				"<br>- Link under heading of the section is not of Healthcare Biotech");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> healthcareBiotechHeadlines = industryPageMethods.getHealthcareBiotechHeadlines();
		List<String> healthcareBiotechStories = VerificationUtil
				.getLinkTextList(industryPageMethods.getHealthcareBiotechHeadlines());
		softAssert.assertTrue(healthcareBiotechStories.size() >= articleCount,
				"<br>- Headlines under Healthcare Biotech sections should be more than " + articleCount + " in number");
		List<String> healthcareBiotechStoriesHref = WebBaseMethods.getListHrefUsingJSE(healthcareBiotechHeadlines);
		healthcareBiotechStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Healthcare Biotech section is throwing " + response);
		});
		/*
		 * List<String> healthcareBiotechNewsDup =
		 * VerificationUtil.isListUnique(healthcareBiotechStories);
		 * softAssert.assertTrue(healthcareBiotechNewsDup.isEmpty(),
		 * "<br>- Healthcare Biotech is having duplicate stories, repeating story(s)->"
		 * + healthcareBiotechNewsDup);
		 */
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Services section on Industry page")
	public void verifyServicesSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = industryPageMethods.getServicesLink();
		softAssert.assertTrue(sectionLink.contains("services"),
				"<br>- Link under heading of the section is not of Services");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> servicesHeadlines = industryPageMethods.getServicesHeadlines();
		List<String> servicesStories = VerificationUtil.getLinkTextList(industryPageMethods.getServicesHeadlines());
		softAssert.assertTrue(servicesStories.size() >= articleCount,
				"<br>- Headlines under Services sections should be more than " + articleCount + " in number");
		List<String> servicesStoriesHref = WebBaseMethods.getListHrefUsingJSE(servicesHeadlines);
		servicesStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Services section is throwing " + response);
		});
		/*
		 * List<String> servicesNewsDup =
		 * VerificationUtil.isListUnique(servicesStories);
		 * softAssert.assertTrue(servicesNewsDup.isEmpty(),
		 * "<br>- Services is having duplicate stories, repeating story(s)->" +
		 * servicesNewsDup);
		 */
		softAssert.assertAll();
	}

	@Test(description = "This test verifies MediaEntertainment section on Industry page")
	public void verifyMediaEntertainmentSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = industryPageMethods.getMediaEntertainmentLink();
		softAssert.assertTrue(sectionLink.contains("media/entertainment"),
				"<br>- Link under heading of the section is not of MediaEntertainment");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> mediaEntertainmentHeadlines = industryPageMethods.getMediaEntertainmentHeadlines();
		List<String> mediaEntertainmentStories = VerificationUtil
				.getLinkTextList(industryPageMethods.getMediaEntertainmentHeadlines());
		softAssert.assertTrue(mediaEntertainmentStories.size() >= articleCount,
				"<br>- Headlines under MediaEntertainment sections should be more than " + articleCount + " in number");
		List<String> mediaEntertainmentStoriesHref = WebBaseMethods.getListHrefUsingJSE(mediaEntertainmentHeadlines);
		mediaEntertainmentStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in MediaEntertainment section is throwing " + response);
		});
		/*
		 * List<String> mediaEntertainmentNewsDup =
		 * VerificationUtil.isListUnique(mediaEntertainmentStories);
		 * softAssert.assertTrue(mediaEntertainmentNewsDup.isEmpty(),
		 * "<br>- MediaEntertainment is having duplicate stories, repeating story(s)->"
		 * + mediaEntertainmentNewsDup);
		 */
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Transportation section on Industry page")
	public void verifyTransportationSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = industryPageMethods.getTransportationLink();
		softAssert.assertTrue(sectionLink.contains("transportation"),
				"<br>- Link under heading of the section is not of Transportation");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> transportationHeadlines = industryPageMethods.getTransportationHeadlines();
		List<String> transportationStories = VerificationUtil
				.getLinkTextList(industryPageMethods.getTransportationHeadlines());
		softAssert.assertTrue(transportationStories.size() >= articleCount,
				"<br>- Headlines under Transportation sections should be more than " + articleCount + " in number");
		List<String> transportationStoriesHref = WebBaseMethods.getListHrefUsingJSE(transportationHeadlines);
		transportationStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Transportation section is throwing " + response);
		});
		/*
		 * List<String> transportationNewsDup =
		 * VerificationUtil.isListUnique(transportationStories);
		 * softAssert.assertTrue(transportationNewsDup.isEmpty(),
		 * "<br>- Transportation is having duplicate stories, repeating story(s)->"
		 * + transportationNewsDup);
		 */
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Telecom section on Industry page")
	public void verifyTelecomSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = industryPageMethods.getTelecomLink();
		softAssert.assertTrue(sectionLink.contains("telecom"),
				"<br>- Link under heading of the section is not of Telecom");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> telecomHeadlines = industryPageMethods.getTelecomHeadlines();
		List<String> telecomStories = VerificationUtil.getLinkTextList(industryPageMethods.getTelecomHeadlines());
		softAssert.assertTrue(telecomStories.size() >= articleCount,
				"<br>- Headlines under Telecom sections should be more than " + articleCount + " in number");
		List<String> telecomStoriesHref = WebBaseMethods.getListHrefUsingJSE(telecomHeadlines);
		telecomStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Telecom section is throwing " + response);
		});
		/*
		 * List<String> telecomNewsDup =
		 * VerificationUtil.isListUnique(telecomStories);
		 * softAssert.assertTrue(telecomNewsDup.isEmpty(),
		 * "<br>- Telecom is having duplicate stories, repeating story(s)->" +
		 * telecomNewsDup);
		 */
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Tech section on Industry page", enabled = false)
	public void verifyTechSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = industryPageMethods.getTechLink();
		Assert.assertTrue(sectionLink.length() > 0, "Section Tech not found");
		softAssert.assertTrue(sectionLink.contains("tech"), "<br>- Link under heading of the section is not of Tech");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> techHeadlines = industryPageMethods.getTechHeadlines();
		List<String> techStories = VerificationUtil.getLinkTextList(industryPageMethods.getTechHeadlines());
		softAssert.assertTrue(techStories.size() >= articleCount,
				"<br>- Headlines under Tech sections should be more than " + articleCount + " in number");
		List<String> techStoriesHref = WebBaseMethods.getListHrefUsingJSE(techHeadlines);
		techStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Tech section is throwing " + response);
		});
		/*
		 * List<String> techNewsDup =
		 * VerificationUtil.isListUnique(techStories);
		 * softAssert.assertTrue(techNewsDup.isEmpty(),
		 * "<br>- Tech is having duplicate stories, repeating story(s)->" +
		 * techNewsDup);
		 */
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Miscellaneous section on Industry page", enabled = false)
	public void verifyMiscellaneousSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = industryPageMethods.getMiscellaneousLink();
		Assert.assertTrue(sectionLink.length() > 0, "Section Miscellaneous not found");
		softAssert.assertTrue(sectionLink.contains("miscellaneous"),
				"<br>- Link under heading of the section is not of Miscellaneous");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> miscellaneousHeadlines = industryPageMethods.getMiscellaneousHeadlines();
		List<String> miscellaneousStories = VerificationUtil
				.getLinkTextList(industryPageMethods.getMiscellaneousHeadlines());
		softAssert.assertTrue(miscellaneousStories.size() >= articleCount,
				"<br>- Headlines under Miscellaneous sections should be more than " + articleCount + " in number");
		List<String> miscellaneousStoriesHref = WebBaseMethods.getListHrefUsingJSE(miscellaneousHeadlines);
		miscellaneousStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Miscellaneous section is throwing " + response);
		});
		/*
		 * List<String> miscellaneousNewsDup =
		 * VerificationUtil.isListUnique(miscellaneousStories);
		 * softAssert.assertTrue(miscellaneousNewsDup.isEmpty(),
		 * "<br>- Miscellaneous is having duplicate stories, repeating story(s)->"
		 * + miscellaneousNewsDup);
		 */
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Defence section on Industry page", enabled = false)
	public void verifyDefenceSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = industryPageMethods.getDefenceLink();
		Assert.assertTrue(sectionLink.length() > 0, "Section Defence not found");
		softAssert.assertTrue(sectionLink.contains("defence"),
				"<br>- Link under heading of the section is not of Defence");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> defenceHeadlines = industryPageMethods.getDefenceHeadlines();
		List<String> defenceStories = VerificationUtil.getLinkTextList(industryPageMethods.getDefenceHeadlines());
		softAssert.assertTrue(defenceStories.size() >= articleCount,
				"<br>- Headlines under Defence sections should be more than " + articleCount + " in number");
		List<String> defenceStoriesHref = WebBaseMethods.getListHrefUsingJSE(defenceHeadlines);
		defenceStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Defence section is throwing " + response);
		});
		/*
		 * List<String> defenceNewsDup =
		 * VerificationUtil.isListUnique(defenceStories);
		 * softAssert.assertTrue(defenceNewsDup.isEmpty(),
		 * "<br>- Defence is having duplicate stories, repeating story(s)->" +
		 * defenceNewsDup);
		 */
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Sports section on Industry page", enabled = false)
	public void verifySportsSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = industryPageMethods.getSportsLink();
		Assert.assertTrue(sectionLink.length() > 0, "Section Sports not found");
		softAssert.assertTrue(sectionLink.contains("sports"),
				"<br>- Link under heading of the section is not of Sports");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> sportsHeadlines = industryPageMethods.getSportsHeadlines();
		List<String> sportsStories = VerificationUtil.getLinkTextList(industryPageMethods.getSportsHeadlines());
		softAssert.assertTrue(sportsStories.size() >= articleCount,
				"<br>- Headlines under Sports sections should be more than " + articleCount + " in number");
		List<String> sportsStoriesHref = WebBaseMethods.getListHrefUsingJSE(sportsHeadlines);
		sportsStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Sports section is throwing " + response);
		});
		/*
		 * List<String> sportsNewsDup =
		 * VerificationUtil.isListUnique(sportsStories);
		 * softAssert.assertTrue(sportsNewsDup.isEmpty(),
		 * "<br>- Sports is having duplicate stories, repeating story(s)->" +
		 * sportsNewsDup);
		 */
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Csr section on Industry page", enabled = false)
	public void verifyCsrSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = industryPageMethods.getCsrLink();
		Assert.assertTrue(sectionLink.length() > 0, "Section CSR not found");
		softAssert.assertTrue(sectionLink.contains("csr"), "<br>- Link under heading of the section is not of Csr");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> csrHeadlines = industryPageMethods.getCsrHeadlines();
		List<String> csrStories = VerificationUtil.getLinkTextList(industryPageMethods.getCsrHeadlines());
		softAssert.assertTrue(csrStories.size() >= articleCount,
				"<br>- Headlines under Csr sections should be more than " + articleCount + " in number");
		List<String> csrStoriesHref = WebBaseMethods.getListHrefUsingJSE(csrHeadlines);
		csrStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Csr section is throwing " + response);
		});
		/*
		 * List<String> csrNewsDup = VerificationUtil.isListUnique(csrStories);
		 * softAssert.assertTrue(csrNewsDup.isEmpty(),
		 * "<br>- Csr is having duplicate stories, repeating story(s)->" +
		 * csrNewsDup);
		 */
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Environment section on Industry page", enabled = false)
	public void verifyEnvironmentSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = industryPageMethods.getEnvironmentLink();
		Assert.assertTrue(sectionLink.length() > 0, "Section Environment not found");
		softAssert.assertTrue(sectionLink.contains("environment"),
				"<br>- Link under heading of the section is not of Environment");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> environmentHeadlines = industryPageMethods.getEnvironmentHeadlines();
		List<String> environmentStories = VerificationUtil
				.getLinkTextList(industryPageMethods.getEnvironmentHeadlines());
		softAssert.assertTrue(environmentStories.size() >= articleCount,
				"<br>- Headlines under Environment sections should be more than " + articleCount + " in number");
		List<String> environmentStoriesHref = WebBaseMethods.getListHrefUsingJSE(environmentHeadlines);
		environmentStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Environment section is throwing " + response);
		});
		/*
		 * List<String> environmentNewsDup =
		 * VerificationUtil.isListUnique(environmentStories);
		 * softAssert.assertTrue(environmentNewsDup.isEmpty(),
		 * "<br>- Environment is having duplicate stories, repeating story(s)->"
		 * + environmentNewsDup);
		 */
		softAssert.assertAll();
	}

	@Test(description = "This test verifies date and time shown on header on industry page")
	public void verifyDateTime() {
		softAssert = new SoftAssert();
		String dateTimeN = headerPageMethods.getDateTimeTab();

		DateTime systemDate = new DateTime();

		DateTime date = WebBaseMethods.convertDateMethod(
				dateTimeN.replaceAll("\\|", "").replaceAll(" ", "").replaceAll(",", "").replaceAll("\\.", ":"));

		Assert.assertTrue(dateTimeN != null, "Date time is not shown on header on industry page");
		softAssert.assertTrue(systemDate.getDayOfYear() == date.getDayOfYear(),
				"The date shown on industry page is not the current date instead is showing " + dateTimeN);
		softAssert.assertTrue(
				(systemDate.getMinuteOfDay() - date.getMinuteOfDay() <= 20
						&& systemDate.getMinuteOfDay() - date.getMinuteOfDay() >= 0),
				"on industry  page the time shown " + dateTimeN
						+ " is having difference of more than 20 mins from current time"
						+ systemDate.toString("MMM dd, yyyy hh:mm a"));
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the video widget on the Industry page")
	public void verifyVideoWidget() {
		softAssert = new SoftAssert();
		List<WebElement> videoWidgetTabs = commonL1L2PagesMethods.listOfVideoWidgetTabs();
		try {
			videoWidgetTabs.forEach(tab -> {
				System.out.println(tab.getText());
				WebBaseMethods.clickElementUsingJSE(tab);
				List<String> activeSectionVideoLinks = commonL1L2PagesMethods.getVideoWidgetActiveLinksHref();
				List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(activeSectionVideoLinks);
				List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(activeSectionVideoLinks);
				softAssert.assertTrue(activeSectionVideoLinks.size() >= 3,
						"The number of videos shown under the video widget under the tab " + tab.getText()
								+ " is not more than or equal to 3");
				softAssert.assertTrue(errorLinks.size() == 0, "Few links are not having response code 200 under tab "
						+ tab.getText() + ". List of such links are: " + errorLinks);
				softAssert.assertTrue(dupLinks.size() == 0,
						"Duplicate links are present under the video widget for tab: " + tab.getText()
								+ ".List of links: " + dupLinks);
			});
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}

		softAssert.assertAll();

	}

	@Test(description = "This test verifies the top trending widget on the Industry page")
	public void verifyTopTrendingWidgetInRhs() {
		softAssert = new SoftAssert();
		try {

			List<String> topTrendingWidgetLinks = commonL1L2PagesMethods.getTopTrendingLinksHref();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(topTrendingWidgetLinks);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(topTrendingWidgetLinks);
			softAssert.assertTrue(topTrendingWidgetLinks.size() >= 5,
					"The number of terms under the top trending widget should be more than equal to 5 but found: "
							+ topTrendingWidgetLinks.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the top trending terms widget. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the top trending widget in RHS: " + dupLinks);
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the top stories on the Industry page")
	public void verifyTopStoriesOnIndustryPage() {
		softAssert = new SoftAssert();
		try {
			List<String> topStoriesOnIndustryHref = commonL1L2PagesMethods.getTopStoriesLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(topStoriesOnIndustryHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(topStoriesOnIndustryHref);
			softAssert.assertTrue(topStoriesOnIndustryHref.size() >= 7,
					"The number of terms under the top stories should be more than equal to 7 but found: "
							+ topStoriesOnIndustryHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the top stories. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the top stories " + dupLinks);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the stories count in the Not to be missed section")
	public void verifyNotToBeMissedSection() {
		try {
			softAssert = new SoftAssert();

			List<String> notToBeMissedSectionLinks = commonL1L2PagesMethods.getNotToBeMissedSectionLInks();
			System.out.println(notToBeMissedSectionLinks.size());
			softAssert.assertTrue(notToBeMissedSectionLinks.size() > 10,
					"Not to be missed section on the Industry page is shown blank or having links less than 10");

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();
	}

}
