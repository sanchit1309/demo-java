package web.tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
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
import web.pagemethods.HomePageMethods;
import web.pagemethods.RisePageMethods;
import web.pagemethods.WebBaseMethods;

public class RisePage extends BaseTest {
	private String baseUrl;
	HomePageMethods homePageMethods;
	RisePageMethods risePageMethods;
	AdTechMethods adTechMethods;
	CommonL1L2PagesMethods commonL1L2PagesMethods;
	SoftAssert softAssert;

	@BeforeClass
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl + Config.fetchConfigProperty("RiseUrl");
		launchBrowser(baseUrl);
		homePageMethods = new HomePageMethods(driver);
		risePageMethods = new RisePageMethods(driver);
		adTechMethods = new AdTechMethods(driver);
		commonL1L2PagesMethods = new CommonL1L2PagesMethods(driver);
		WebBaseMethods.scrollToBottom();

	}

	@Test(description = " This test verifies the ads on Rise Listing Page")
	public void verifyAds() {
		softAssert = new SoftAssert();
		WaitUtil.sleep(2000);
		Map<String, String> expectedIdsMap = Config.getAllKeys("adIdLocation");
		softAssert.assertTrue(adTechMethods.getDisplayedAdIds().size() > 0,
				"No google ads shown on Rise Listing Page.");
		if (adTechMethods.getDisplayedAdIds().size() > 0) {
			expectedIdsMap.forEach((K, V) -> {
				if (K.contains("ET_ROS") && !(K.contains("_AS_"))) {
					softAssert.assertTrue(adTechMethods.matchIdsWithRegex(K),
							"Following ad(s) is/are not shown " + expectedIdsMap.get(K) + " on Rise Listing Page.");
				}
			});
		}
		List<String> ctnAd = adTechMethods.getMissingColumbiaAds();
		softAssert.assertFalse(ctnAd.size() > 0,
				"Following colombia ad(s) is/are not shown " + ctnAd + " on Rise Listing Page.");
		softAssert.assertAll();
	}

	@Test(description = " This test verifies SME section of the page")
	public void verifySmeSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionlink = risePageMethods.getSmeLink();
		softAssert.assertTrue(sectionlink.contains("small-biz"),
				"<br>- Link under heading of the section is not of SME");
		int responseCode = HTTPResponse.checkResponseCode(sectionlink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionlink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> smeHeadlines = risePageMethods.getSmeHeadlines();
		List<String> smeStories = VerificationUtil.getLinkTextList(risePageMethods.getSmeHeadlines());
		softAssert.assertTrue(smeStories.size() >= articleCount,
				"<br>- Headlines under SME sections should be more than " + articleCount + " in number");
		List<String> smeStoriesHref = WebBaseMethods.getListHrefUsingJSE(smeHeadlines);
		smeStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in SME section is throwing " + response);
		});

		List<String> taxNewsDup = VerificationUtil.isListUnique(smeStories);
		softAssert.assertTrue(taxNewsDup.isEmpty(),
				"<br>- SME is having duplicate stories, repeating story(s)->" + taxNewsDup);
		softAssert.assertAll();
	}

	@Test(description = " This test verifies Startups section of the page", enabled = false)
	public void verifyStartupsSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionlink = risePageMethods.getStartupsLink();
		softAssert.assertTrue(sectionlink.contains("startups"),
				"<br>- Link under heading of the section is not of Startups");
		int responseCode = HTTPResponse.checkResponseCode(sectionlink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionlink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> startupsHeadlines = risePageMethods.getStartupsHeadlines();
		List<String> startupsStories = VerificationUtil.getLinkTextList(risePageMethods.getStartupsHeadlines());
		softAssert.assertTrue(startupsStories.size() >= articleCount,
				"<br>- Headlines under Startups sections should be more than " + articleCount + " in number");
		List<String> startupsStoriesHref = WebBaseMethods.getListHrefUsingJSE(startupsHeadlines);
		startupsStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Startups section is throwing " + response);
		});

		List<String> startupsNewsDup = VerificationUtil.isListUnique(startupsStories);
		softAssert.assertTrue(startupsNewsDup.isEmpty(),
				"<br>- Startups is having duplicate stories, repeating story(s)->" + startupsNewsDup);
		softAssert.assertAll();
	}

	@Test(description = " This test verifies Policy section of the page")
	public void verifyPolicySection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionlink = risePageMethods.getPolicyLink();
		softAssert.assertTrue(sectionlink.contains("policy"),
				"<br>- Link under heading of the section is not of Policy");
		int responseCode = HTTPResponse.checkResponseCode(sectionlink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionlink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> policyHeadlines = risePageMethods.getPolicyHeadlines();
		List<String> policyStories = VerificationUtil.getLinkTextList(risePageMethods.getPolicyHeadlines());
		softAssert.assertTrue(policyStories.size() >= articleCount,
				"<br>- Headlines under Policy sections should be more than " + articleCount + " in number");
		List<String> policyStoriesHref = WebBaseMethods.getListHrefUsingJSE(policyHeadlines);
		policyStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Policy section is throwing " + response);
		});

		List<String> policyNewsDup = VerificationUtil.isListUnique(policyStories);
		softAssert.assertTrue(policyNewsDup.isEmpty(),
				"<br>- Policy is having duplicate stories, repeating story(s)->" + policyNewsDup);
		softAssert.assertAll();
	}

	@Test(description = " This test verifies Entrepreneurship section of the page")
	public void verifyEntrepreneurshipSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionlink = risePageMethods.getEntrepreneurshipLink();
		softAssert.assertTrue(sectionlink.contains("entrepreneurship"),
				"<br>- Link under heading of the section is not of Entrepreneurship");
		int responseCode = HTTPResponse.checkResponseCode(sectionlink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionlink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> entrepreneurshipHeadlines = risePageMethods.getEntrepreneurshipHeadlines();
		List<String> entrepreneurshipStories = VerificationUtil
				.getLinkTextList(risePageMethods.getEntrepreneurshipHeadlines());
		softAssert.assertTrue(entrepreneurshipStories.size() >= articleCount,
				"<br>- Headlines under Entrepreneurship sections should be more than " + articleCount + " in number");
		List<String> entrepreneurshipStoriesHref = WebBaseMethods.getListHrefUsingJSE(entrepreneurshipHeadlines);
		entrepreneurshipStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Entrepreneurship section is throwing " + response);
		});

		List<String> entrepreneurshipNewsDup = VerificationUtil.isListUnique(entrepreneurshipStories);
		softAssert.assertTrue(entrepreneurshipNewsDup.isEmpty(),
				"<br>- Entrepreneurship is having duplicate stories, repeating story(s)->" + entrepreneurshipNewsDup);
		softAssert.assertAll();
	}

	@Test(description = " This test verifies Money section of the page")

	public void verifyMoneySection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionlink = risePageMethods.getMoneyLink();
		softAssert.assertTrue(sectionlink.contains("money"), "<br>- Link under heading of the section is not of Money");
		int responseCode = HTTPResponse.checkResponseCode(sectionlink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionlink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> moneyHeadlines = risePageMethods.getMoneyHeadlines();
		List<String> moneyStories = VerificationUtil.getLinkTextList(risePageMethods.getMoneyHeadlines());
		softAssert.assertTrue(moneyStories.size() >= articleCount,
				"<br>- Headlines under Money sections should be more than " + articleCount + " in number");
		List<String> moneyStoriesHref = WebBaseMethods.getListHrefUsingJSE(moneyHeadlines);
		moneyStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Money section is throwing " + response);
		});

		List<String> moneyNewsDup = VerificationUtil.isListUnique(moneyStories);
		softAssert.assertTrue(moneyNewsDup.isEmpty(),
				"<br>- Money section is having duplicate stories, repeating story(s)->" + moneyNewsDup);
		softAssert.assertAll();
	}

	@Test(description = " This test verifies Security-Tech section of the page")
	public void verifySecuritytechSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionlink = risePageMethods.getSecuritytechLink();
		softAssert.assertTrue(sectionlink.contains("security-tech"),
				"<br>- Link under heading of the section is not of Security-Tech");
		int responseCode = HTTPResponse.checkResponseCode(sectionlink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionlink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> securitytechHeadlines = risePageMethods.getSecuritytechHeadlines();
		List<String> securitytechStories = VerificationUtil.getLinkTextList(risePageMethods.getSecuritytechHeadlines());
		softAssert.assertTrue(securitytechStories.size() >= articleCount,
				"<br>- Headlines under Security-Tech sections should be more than " + articleCount + " in number");
		List<String> securitytechStoriesHref = WebBaseMethods.getListHrefUsingJSE(securitytechHeadlines);
		securitytechStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Security-Tech section is throwing " + response);
		});

		List<String> securitytechNewsDup = VerificationUtil.isListUnique(securitytechStories);
		softAssert.assertTrue(securitytechNewsDup.isEmpty(),
				"<br>- Security-Tech is having duplicate stories, repeating story(s)->" + securitytechNewsDup);
		softAssert.assertAll();
	}

	@Test(description = " This test verifies Marketing section of the page")
	public void verifyMarketingSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionlink = risePageMethods.getMarketingLink();
		softAssert.assertTrue(sectionlink.contains("marketing"),
				"<br>- Link under heading of the section is not of Marketing");
		int responseCode = HTTPResponse.checkResponseCode(sectionlink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionlink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> marketingHeadlines = risePageMethods.getMarketingHeadlines();
		List<String> marketingStories = VerificationUtil.getLinkTextList(risePageMethods.getMarketingHeadlines());
		softAssert.assertTrue(marketingStories.size() >= articleCount,
				"<br>- Headlines under Marketing sections should be more than " + articleCount + " in number");
		List<String> marketingStoriesHref = WebBaseMethods.getListHrefUsingJSE(marketingHeadlines);
		marketingStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Marketing section is throwing " + response);
		});

		List<String> marketingNewsDup = VerificationUtil.isListUnique(marketingStories);
		softAssert.assertTrue(marketingNewsDup.isEmpty(),
				"<br>- Marketing is having duplicate stories, repeating story(s)->" + marketingNewsDup);
		softAssert.assertAll();
	}

	@Test(description = " This test verifies HR section of the page")
	public void verifyHrSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionlink = risePageMethods.getHrLink();
		softAssert.assertTrue(sectionlink.contains("hr-leadership"),
				"<br>- Link under heading of the section is not of HR");
		int responseCode = HTTPResponse.checkResponseCode(sectionlink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionlink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> hrHeadlines = risePageMethods.getHrHeadlines();
		List<String> hrStories = VerificationUtil.getLinkTextList(risePageMethods.getHrHeadlines());
		softAssert.assertTrue(hrStories.size() >= articleCount,
				"<br>- Headlines under HR sections should be more than " + articleCount + " in number");
		List<String> hrStoriesHref = WebBaseMethods.getListHrefUsingJSE(hrHeadlines);
		hrStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in HR section is throwing " + response);
		});

		List<String> hrNewsDup = VerificationUtil.isListUnique(hrStories);
		softAssert.assertTrue(hrNewsDup.isEmpty(),
				"<br>- HR is having duplicate stories, repeating story(s)->" + hrNewsDup);
		softAssert.assertAll();
	}

	@Test(description = " This test verifies Legal section of the page")
	public void verifyLegalSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionlink = risePageMethods.getLegalLink();
		softAssert.assertTrue(sectionlink.contains("legal"), "<br>- Link under heading of the section is not of Legal");
		int responseCode = HTTPResponse.checkResponseCode(sectionlink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionlink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> legalHeadlines = risePageMethods.getLegalHeadlines();
		List<String> legalStories = VerificationUtil.getLinkTextList(risePageMethods.getLegalHeadlines());
		softAssert.assertTrue(legalStories.size() >= articleCount,
				"<br>- Headlines under Legal sections should be more than " + articleCount + " in number");
		List<String> legalStoriesHref = WebBaseMethods.getListHrefUsingJSE(legalHeadlines);
		legalStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Legal section is throwing " + response);
		});

		List<String> legalNewsDup = VerificationUtil.isListUnique(legalStories);
		softAssert.assertTrue(legalNewsDup.isEmpty(),
				"<br>- Legal is having duplicate stories, repeating story(s)->" + legalNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the video widget on the Rise page")
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

	@Test(description = "This test verifies the top trending widget on the Rise page")
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

	@Test(description = "This test verifies the top stories on the Rise page")
	public void verifyTopStoriesOnRisePage() {
		softAssert = new SoftAssert();
		try {
			List<String> topStoriesOnRiseHref = commonL1L2PagesMethods.getTopStoriesLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(topStoriesOnRiseHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(topStoriesOnRiseHref);
			softAssert.assertTrue(topStoriesOnRiseHref.size() >= 7,
					"The number of links under the top stories should be more than equal to 7 but found: "
							+ topStoriesOnRiseHref.size());
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
					"Not to be missed section on the Rise page is shown blank or having links less than 10");

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();
	}

	@Test(description = " This test verifies Trade section of the page")
	public void verifyTradeSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionlink = risePageMethods.getTradeLink();
		softAssert.assertTrue(sectionlink.contains("trade"), "<br>- Link under heading of the section is not of Trade");
		int responseCode = HTTPResponse.checkResponseCode(sectionlink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionlink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> tradeHeadlines = risePageMethods.getTradeHeadlines();
		List<String> tradeStories = VerificationUtil.getLinkTextList(risePageMethods.getTradeHeadlines());
		softAssert.assertTrue(tradeStories.size() >= articleCount,
				"<br>- Headlines under Trade sections should be more than " + articleCount + " in number");
		List<String> tradeStoriesHref = WebBaseMethods.getListHrefUsingJSE(tradeHeadlines);
		tradeStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Trade section is throwing " + response);
		});

		List<String> tradeNewsDup = VerificationUtil.isListUnique(tradeStories);
		softAssert.assertTrue(tradeNewsDup.isEmpty(),
				"<br>- Trade is having duplicate stories, repeating story(s)->" + tradeNewsDup);
		softAssert.assertAll();
	}

	@Test(description = " This test verifies Product Line section of the page", enabled = false)
	public void verifyProductLineSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionlink = risePageMethods.getproductLineLink();
		softAssert.assertTrue(sectionlink.contains("productline"),
				"<br>- Link under heading of the section is not of ProductLine");
		int responseCode = HTTPResponse.checkResponseCode(sectionlink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionlink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> productLineHeadlines = risePageMethods.getProductLineHeadlines();
		List<String> productLineStories = VerificationUtil.getLinkTextList(risePageMethods.getProductLineHeadlines());
		softAssert.assertTrue(productLineStories.size() >= articleCount,
				"<br>- Headlines under Product Line sections should be more than " + articleCount + " in number");
		List<String> productLineStoriesHref = WebBaseMethods.getListHrefUsingJSE(productLineHeadlines);
		productLineStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Product Line section is throwing " + response);
		});

		List<String> productLineNewsDup = VerificationUtil.isListUnique(productLineStories);
		softAssert.assertTrue(productLineNewsDup.isEmpty(),
				"<br>- Product line is having duplicate stories, repeating story(s)->" + productLineNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the top searches section on the Rise page")
	public void verifyTopSearchesSection() {
		softAssert = new SoftAssert();
		try {
			List<String> topSearchesOnRiseHref = commonL1L2PagesMethods.getTopSearchesLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(topSearchesOnRiseHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(topSearchesOnRiseHref);
			softAssert.assertTrue(topSearchesOnRiseHref.size() >= 4,
					"The number of terms under the top searches should be more than equal to 4 but found: "
							+ topSearchesOnRiseHref.size());
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
	
	@Test(description = "This test verifies the rise keywords section on the Rise page")
	public void verifyRiseKeywordsSection() {
		softAssert = new SoftAssert();
		try {
			List<String> keywordsOnRiseHref = risePageMethods.getRiseKeywordsLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(keywordsOnRiseHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(keywordsOnRiseHref);
			softAssert.assertTrue(keywordsOnRiseHref.size() >= 4,
					"The number of terms under the rise keyword should be more than equal to 4 but found: "
							+ keywordsOnRiseHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the rise keywords. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the rise keywords " + dupLinks);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}
	
	@Test(description = "This test verifies the rise biz listing section on the Rise page")
	public void verifyRiseBizListingKeywordSection() {
		softAssert = new SoftAssert();
		try {
			List<String> riseBizListingKeywordsHref = risePageMethods.getRiseBizListingLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(riseBizListingKeywordsHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(riseBizListingKeywordsHref);
			softAssert.assertTrue(riseBizListingKeywordsHref.size() >= 4,
					"The number of terms under the top searches should be more than equal to 4 but found: "
							+ riseBizListingKeywordsHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the rise biz listing keywords. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the rise keywords " + dupLinks);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}
	
	
}
