package com.web.tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
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
import web.pagemethods.WealthPageMethods;
import web.pagemethods.WebBaseMethods;

public class WealthPage extends BaseTest {
	private String baseUrl;
	HomePageMethods homePageMethods;
	WealthPageMethods wealthPageMethods;
	HeaderPageMethods headerPageMethods;
	CommonL1L2PagesMethods commonL1L2PagesMethods;
	AdTechMethods adTechMethods;
	SoftAssert softAssert;

	@BeforeClass
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl+Config.fetchConfigProperty("WealthUrl");
		launchBrowser(baseUrl);
		homePageMethods = new HomePageMethods(driver);
		wealthPageMethods = new WealthPageMethods(driver);
		headerPageMethods = new HeaderPageMethods(driver);
		commonL1L2PagesMethods = new CommonL1L2PagesMethods(driver);
		adTechMethods = new AdTechMethods(driver);
		WebBaseMethods.scrollToBottom();
	}

	@Test(description = " This test verifies the ads on Wealth Listing Page")
	public void verifyAds() {
		softAssert = new SoftAssert();
		WaitUtil.sleep(2000);
		Map<String, String> expectedIdsMap = Config.getAllKeys("adIdLocation");
		softAssert.assertTrue(adTechMethods.getDisplayedAdIds().size()>0, "No google ads shown on Wealth Listing Page.");
		if(adTechMethods.getDisplayedAdIds().size()>0) {
			expectedIdsMap.forEach((K,V) -> {
				if(K.contains("ET_ROS") && !( K.contains("_AS_"))){
				softAssert.assertTrue(adTechMethods.matchIdsWithRegex(K), "Following ad(s) is/are not shown " + expectedIdsMap.get(K)+ " on Wealth Listing Page.");
				}
			});
		}
		List<String> ctnAd = adTechMethods.getMissingColumbiaAds();
		softAssert.assertFalse(ctnAd.size()>0, "Following colombia ad(s) is/are not shown "+ ctnAd+ " on Wealth Listing Page.");	
		if(adTechMethods.acrossWebIsDisplayed()) {
			softAssert.assertEquals(adTechMethods.acrossWebAds().size(), 3,
					"<br>- From across web does not have 3 articles instead has " + adTechMethods.acrossWebAds().size());
		}else {
			softAssert.assertTrue(adTechMethods.acrossWebIsDisplayed(), "From across the web section is not displayed on wealth listing page");
		}	
		softAssert.assertAll();
	}
	@Test(description = "This test verifies Tax section on Wealth page")
	public void verifyTaxSection() {
		int articleCount = 7;
		softAssert = new SoftAssert();
		String sectionLink = wealthPageMethods.getTaxLink();
		softAssert.assertTrue(sectionLink.contains("tax"), "<br>- Link under heading of the section is not of Tax");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> taxHeadlines = wealthPageMethods.getTaxHeadlines();
		List<String> taxStories = VerificationUtil.getLinkTextList(wealthPageMethods.getTaxHeadlines());
		softAssert.assertTrue(taxStories.size() >= articleCount, "<br>- Headlines under Tax sections should be more than " + articleCount
				+ " in number instead found "+taxStories.size());
		List<String> taxStoriesHref = WebBaseMethods.getListHrefUsingJSE(taxHeadlines);
		taxStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200, "<br>- <a href=" + href + "> Story</a> in Tax section is throwing " + response);
		});
		List<String> taxNewsDup = VerificationUtil.isListUnique(taxStories);
		softAssert.assertTrue(taxNewsDup.isEmpty(), "<br>- Tax is having duplicate stories, repeating story(s)->" + taxNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Save section on Wealth page")
	public void verifySaveSection() {
		int articleCount = 7;
		softAssert = new SoftAssert();
		String sectionLink = wealthPageMethods.getSaveLink();
		softAssert.assertTrue(sectionLink.contains("save"), "<br>- Link under heading of the section is not of Save");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> saveHeadlines = wealthPageMethods.getSaveHeadlines();
		List<String> saveStories = VerificationUtil.getLinkTextList(wealthPageMethods.getSaveHeadlines());
		softAssert.assertTrue(saveStories.size() >= articleCount, "<br>- Headlines under Save sections should be more than " + articleCount
				+ " in number");
		List<String> saveStoriesHref = WebBaseMethods.getListHrefUsingJSE(saveHeadlines);
		saveStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200, "<br>- <a href=" + href + "> Story</a> in Save section is throwing " + response);
		});
		List<String> saveNewsDup = VerificationUtil.isListUnique(saveStories);
		softAssert.assertTrue(saveNewsDup.isEmpty(), "<br>- Save is having duplicate stories, repeating story(s)->" + saveNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Invest section on Wealth page")
	public void verifyInvestSection() {
		int articleCount = 7;
		softAssert = new SoftAssert();
		String sectionLink = wealthPageMethods.getInvestLink();
		softAssert.assertTrue(sectionLink.contains("invest"), "<br>- Link under heading of the section is not of Invest");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> investHeadlines = wealthPageMethods.getInvestHeadlines();
		List<String> investStories = VerificationUtil.getLinkTextList(wealthPageMethods.getInvestHeadlines());
		softAssert.assertTrue(investStories.size() >= articleCount, "<br>- Headlines under Invest sections should be more than " + articleCount
				+ " in number");
		List<String> investStoriesHref = WebBaseMethods.getListHrefUsingJSE(investHeadlines);
		investStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200, "<br>- <a href=" + href + "> Story</a> in Invest section is throwing " + response);
		});
		List<String> investNewsDup = VerificationUtil.isListUnique(investStories);
		softAssert.assertTrue(investNewsDup.isEmpty(), "<br>- Invest is having duplicate stories, repeating story(s)->" + investNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Insure section on Wealth page")
	public void verifyInsureSection() {
		int articleCount = 7;
		softAssert = new SoftAssert();
		String sectionLink = wealthPageMethods.getInsureLink();
		softAssert.assertTrue(sectionLink.contains("insure"), "<br>- Link under heading of the section is not of Insure");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> insureHeadlines = wealthPageMethods.getInsureHeadlines();
		List<String> insureStories = VerificationUtil.getLinkTextList(wealthPageMethods.getInsureHeadlines());
		softAssert.assertTrue(insureStories.size() >= articleCount, "<br>- Headlines under Insure sections should be more than " + articleCount
				+ " in number");
		List<String> insureStoriesHref = WebBaseMethods.getListHrefUsingJSE(insureHeadlines);
		insureStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200, "<br>- <a href=" + href + "> Story</a> in Insure section is throwing " + response);
		});
		List<String> insureNewsDup = VerificationUtil.isListUnique(insureStories);
		softAssert.assertTrue(insureNewsDup.isEmpty(), "<br>- Insure is having duplicate stories, repeating story(s)->" + insureNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Spend section on Wealth page")
	public void verifySpendSection() {
		int articleCount = 7;
		softAssert = new SoftAssert();
		String sectionLink = wealthPageMethods.getSpendLink();
		softAssert.assertTrue(sectionLink.contains("spend"), "<br>- Link under heading of the section is not of Spend");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> spendHeadlines = wealthPageMethods.getSpendHeadlines();
		List<String> spendStories = VerificationUtil.getLinkTextList(wealthPageMethods.getSpendHeadlines());
		softAssert.assertTrue(spendStories.size() >= articleCount, "<br>- Headlines under Spend sections should be more than " + articleCount
				+ " in number");
		List<String> spendStoriesHref = WebBaseMethods.getListHrefUsingJSE(spendHeadlines);
		spendStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200, "<br>- <a href=" + href + "> Story</a> in Spend section is throwing " + response);
		});
		List<String> spendNewsDup = VerificationUtil.isListUnique(spendStories);
		softAssert.assertTrue(spendNewsDup.isEmpty(), "<br>- Spend is having duplicate stories, repeating story(s)->" + spendNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Borrow section on Wealth page")
	public void verifyBorrowSection() {
		int articleCount = 7;
		softAssert = new SoftAssert();
		String sectionLink = wealthPageMethods.getBorrowLink();
		softAssert.assertTrue(sectionLink.contains("borrow"), "<br>- Link under heading of the section is not of Borrow");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> borrowHeadlines = wealthPageMethods.getBorrowHeadlines();
		List<String> borrowStories = VerificationUtil.getLinkTextList(wealthPageMethods.getBorrowHeadlines());
		softAssert.assertTrue(borrowStories.size() >= articleCount, "<br>- Headlines under Borrow sections should be more than " + articleCount
				+ " in number");
		List<String> borrowStoriesHref = WebBaseMethods.getListHrefUsingJSE(borrowHeadlines);
		borrowStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200, "<br>- <a href=" + href + "> Story</a> in Borrow section is throwing " + response);
		});
		List<String> borrowNewsDup = VerificationUtil.isListUnique(borrowStories);
		softAssert.assertTrue(borrowNewsDup.isEmpty(), "<br>- Borrow is having duplicate stories, repeating story(s)->" + borrowNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Earn section on Wealth page")
	public void verifyEarnSection() {
		int articleCount = 7;
		softAssert = new SoftAssert();
		String sectionLink = wealthPageMethods.getEarnLink();
		softAssert.assertTrue(sectionLink.contains("earn"), "<br>- Link under heading of the section is not of Earn");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> earnHeadlines = wealthPageMethods.getEarnHeadlines();
		List<String> earnStories = VerificationUtil.getLinkTextList(wealthPageMethods.getEarnHeadlines());
		softAssert.assertTrue(earnStories.size() >= articleCount, "<br>- Headlines under Earn sections should be more than " + articleCount
				+ " in number");
		List<String> earnStoriesHref = WebBaseMethods.getListHrefUsingJSE(earnHeadlines);
		earnStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200, "<br>- <a href=" + href + "> Story</a> in Earn section is throwing " + response);
		});
		List<String> earnNewsDup = VerificationUtil.isListUnique(earnStories);
		softAssert.assertTrue(earnNewsDup.isEmpty(), "<br>- Earn is having duplicate stories, repeating story(s)->" + earnNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Plan section on Wealth page")
	public void verifyPlanSection() {
		int articleCount = 7;
		softAssert = new SoftAssert();
		String sectionLink = wealthPageMethods.getPlanLink();
		softAssert.assertTrue(sectionLink.contains("plan"), "<br>- Link under heading of the section is not of Plan");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> planHeadlines = wealthPageMethods.getPlanHeadlines();
		List<String> planStories = VerificationUtil.getLinkTextList(wealthPageMethods.getPlanHeadlines());
		softAssert.assertTrue(planStories.size() >= articleCount, "<br>- Headlines under Plan sections should be more than " + articleCount
				+ " in number");
		List<String> planStoriesHref = WebBaseMethods.getListHrefUsingJSE(planHeadlines);
		planStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200, "<br>- <a href=" + href + "> Story</a> in Plan section is throwing " + response);
		});
		List<String> planNewsDup = VerificationUtil.isListUnique(planStories);
		System.out.println(planNewsDup.size());
		for(String s : planNewsDup){
			
			System.out.println(s);
		}
		softAssert.assertTrue(planNewsDup.isEmpty(), "<br>- Plan is having duplicate stories, repeating story(s)->" + planNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Real Estate section on Wealth page")
	public void verifyRealEstateSection() {
		int articleCount = 7;
		softAssert = new SoftAssert();
		String sectionLink = wealthPageMethods.getRealEstateLink();
		softAssert.assertTrue(sectionLink.contains("real-estate"), "<br>- Link under heading of the section is not of Real Estate");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> realEstateHeadlines = wealthPageMethods.getRealEstateHeadlines();
		List<String> realEstateStories = VerificationUtil.getLinkTextList(wealthPageMethods.getRealEstateHeadlines());
		softAssert.assertTrue(realEstateStories.size() >= articleCount, "<br>- Headlines under Real Estate sections should be more than "
				+ articleCount + " in number");
		List<String> realEstateStoriesHref = WebBaseMethods.getListHrefUsingJSE(realEstateHeadlines);
		realEstateStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200, "<br>- <a href=" + href + "> Story</a> in Real Estate section is throwing " + response);
		});
		List<String> realEstateNewsDup = VerificationUtil.isListUnique(realEstateStories);
		softAssert.assertTrue(realEstateNewsDup.isEmpty(), "<br>- Real Estate is having duplicate stories, repeating story(s)->" + realEstateNewsDup);
		softAssert.assertAll();
	}
	@Test(description = "This test verifies date and time shown on header on wealth page")
	public void verifyDateTime(){
		softAssert = new SoftAssert();
		String dateTimeN = headerPageMethods.getDateTimeTab();

		DateTime systemDate = new DateTime();

		DateTime date = WebBaseMethods.convertDateMethod(dateTimeN.replaceAll("\\|", "").replaceAll(" ", "").replaceAll(",", "").replaceAll("\\.",
				":"));

		Assert.assertTrue(dateTimeN != null, "Date time is not shown on header on wealth page");
		softAssert.assertTrue(systemDate.getDayOfYear() == date.getDayOfYear(),
				"The date shown on wealth page is not the current date instead is showing " + dateTimeN);
		softAssert.assertTrue((systemDate.getMinuteOfDay() - date.getMinuteOfDay() <= 20 && systemDate.getMinuteOfDay() - date.getMinuteOfDay() >= 0),
				"on wealth  page the time shown " + dateTimeN + " is having difference of more than 20 mins from current time"+ systemDate.toString("MMM dd, yyyy hh:mm a"));
		softAssert.assertAll();
		
	}
	
	@Test(description = "This test verifies P2P section on Wealth page")
	public void verifyP2PSection() {
		int articleCount = 7;
		softAssert = new SoftAssert();
		String sectionLink = wealthPageMethods.getP2PLink();
		softAssert.assertTrue(sectionLink.contains("peer-to-peer"), "<br>- Link under heading of the section is not of P2P");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200, "<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> p2pHeadlines = wealthPageMethods.getP2PHeadlines();
		List<String> p2pStories = VerificationUtil.getLinkTextList(wealthPageMethods.getP2PHeadlines());
		softAssert.assertTrue(p2pStories.size() >= articleCount, "<br>- Headlines under P2P sections should be more than "
				+ articleCount + " in number");
		List<String> p2pStoriesHref = WebBaseMethods.getListHrefUsingJSE(p2pHeadlines);
		p2pStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200, "<br>- <a href=" + href + "> Story</a> in P2P section is throwing " + response);
		});
		List<String> p2pNewsDup = VerificationUtil.isListUnique(p2pStories);
		softAssert.assertTrue(p2pNewsDup.isEmpty(), "<br>- P2P is having duplicate stories, repeating story(s)->" + p2pNewsDup);
		softAssert.assertAll();
	}
	
	@Test(description = "This test verifies the rise keywords section on the Wealth page")
	public void verifyWealthKeywordsSection() {
		softAssert = new SoftAssert();
		try {
			List<String> keywordsOnWealthHref = wealthPageMethods.getWealthKeywordsLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(keywordsOnWealthHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(keywordsOnWealthHref);
			softAssert.assertTrue(keywordsOnWealthHref.size() >= 4,
					"The number of terms under the Wealth keyword should be more than equal to 4 but found: "
							+ keywordsOnWealthHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the Wealth keywords. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the Wealth Keywords " + dupLinks);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}
	
	
	@Test(description = "This test verifies the top searches section on the Wealth page")
	public void verifyTopSearchesSection() {
		softAssert = new SoftAssert();
		try {
			List<String> topSearchesOnWealthHref = commonL1L2PagesMethods.getTopSearchesLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(topSearchesOnWealthHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(topSearchesOnWealthHref);
			softAssert.assertTrue(topSearchesOnWealthHref.size() >= 4,
					"The number of terms under the top searches should be more than equal to 4 but found: "
							+ topSearchesOnWealthHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the top searches. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the top Searches " + dupLinks);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}
	
	@Test(description = "This test verifies the top stories on the Wealth page")
	public void verifyTopStoriesOnWealthPage() {
		softAssert = new SoftAssert();
		try {
			List<String> topStoriesOnWealthHref = wealthPageMethods.getTopStoriesOnWealthPage();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(topStoriesOnWealthHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(topStoriesOnWealthHref);
			softAssert.assertTrue(topStoriesOnWealthHref.size() >= 7,
					"The number of Links under the top stories should be more than equal to 7 but found: "
							+ topStoriesOnWealthHref.size());
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
	
	
	@Test(description = "Verify Wealth page sub section keywords", dataProvider = "subSections")
	public void verifyKeywordsOfSubSection(String subSection) {
		softAssert = new SoftAssert();
		String sectionName = subSection;
		
		try {

			List<String> listHref = wealthPageMethods.getKnowAllAboutLinksOfSection(sectionName);
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(listHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(listHref);
			softAssert.assertTrue(listHref.size() > 0, "The number of links under the " + sectionName
					+ " section keywords should be more than 0 but found: " + listHref.size());
			softAssert.assertTrue(errorLinks.size() == 0, "Few links are not having response code 200 under the "
					+ sectionName + " section keywords. List of such links are: " + errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the " + sectionName + " section keywords " + dupLinks);

			} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();
	}
	
	@Test(description = "This test verifies the top trending widget on the Wealth page")
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
	
	@Test(description = "This test verifies the This week edition list on the Wealth page")
	public void verifyThisWeekEditionListWidget() {
		softAssert = new SoftAssert();
		try {
			List<String> thisWeekEditionListWealthHref = wealthPageMethods.getThisWeeksEditionListLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(thisWeekEditionListWealthHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(thisWeekEditionListWealthHref);
			softAssert.assertTrue(thisWeekEditionListWealthHref.size() >= 4,
					"The number of Links under the This week edition list widget should be more than equal to 4 but found: "
							+ thisWeekEditionListWealthHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the This week edition list widget. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the This week edition list widget " + dupLinks);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}
	
	@Test(description = "This test verifies the Wealth Magazine archives on the Wealth page")
	public void verifyWealthMagazineArchives() {
		softAssert = new SoftAssert();
		try {
			List<String> magazineArchivesWealthHref = wealthPageMethods.getEtWealthMagazineArchiveWidgetLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(magazineArchivesWealthHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(magazineArchivesWealthHref);
			softAssert.assertTrue(magazineArchivesWealthHref.size() >= 4,
					"The number of Links under the Wealth Magazine archives be more than equal to 4 but found: "
							+ magazineArchivesWealthHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the Wealth Magazine archives. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the Wealth Magazine archives " + dupLinks);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}
	
	@Test(description = "This test verifies the ET Wealth Classroom on the Wealth page")
	public void verifyETWealthClassroomWidget() {
		softAssert = new SoftAssert();
		try {
			List<String> etWealthClassroomWidgetHref = wealthPageMethods.getEtWealthClassroomWidgetLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(etWealthClassroomWidgetHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(etWealthClassroomWidgetHref);
			softAssert.assertTrue(etWealthClassroomWidgetHref.size() >= 20,
					"The number of Links under the ET Wealth Classroom be more than equal to 20 but found: "
							+ etWealthClassroomWidgetHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the ET Wealth Classroom. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the ET Wealth Classroom " + dupLinks);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}
	
	@Test(description = "This test verifies the News and Updates widget on the Wealth page")
	public void verifyNewsAndUpdatesWidget() {
		softAssert = new SoftAssert();
		try {
			List<String> newsAndUpdateHref = wealthPageMethods.getNewsAndUpdatesWidgetLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(newsAndUpdateHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(newsAndUpdateHref);
			softAssert.assertTrue(newsAndUpdateHref.size() >= 20,
					"The number of Links under the News and Updates widget be more than equal to 20 but found: "
							+ newsAndUpdateHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the News and Updates widget. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the News and Updates widget" + dupLinks);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}
	
	
	@DataProvider
	public String[] subSections() {
		String[] sections = {"Tax", "Save", "Invest", "Insure", "Borrow", "Spend", "Earn", "Plan", "Real Estate", "P2P"};

		return sections;

	}

}


