package web.tests;

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
import web.pagemethods.NriPageMethods;
import web.pagemethods.WebBaseMethods;

public class NriPage extends BaseTest {

	private String baseUrl;
	HomePageMethods homePageMethods;
	NriPageMethods nriPageMethods;
	HeaderPageMethods headerPageMethods;
	AdTechMethods adTechMethods;
	CommonL1L2PagesMethods commonL1L2PagesMethods;
	SoftAssert softAssert;

	@BeforeClass
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl + Config.fetchConfigProperty("NriUrl");
		launchBrowser(baseUrl);
		homePageMethods = new HomePageMethods(driver);
		nriPageMethods = new NriPageMethods(driver);
		headerPageMethods = new HeaderPageMethods(driver);
		adTechMethods = new AdTechMethods(driver);
		commonL1L2PagesMethods = new CommonL1L2PagesMethods(driver);
		WebBaseMethods.scrollToBottom();

	}

	@Test(description = " This test verifies the ads on NRI Listing Page", enabled = false)
	public void verifyAds() {
		softAssert = new SoftAssert();
		WaitUtil.sleep(2000);
		Map<String, String> expectedIdsMap = Config.getAllKeys("adIdLocation");
		softAssert.assertTrue(adTechMethods.getDisplayedAdIds().size() > 0, "No google ads shown on NRI Listing Page.");
		if (adTechMethods.getDisplayedAdIds().size() > 0) {
			expectedIdsMap.forEach((K, V) -> {
				if (K.contains("ET_ROS") && !(K.contains("_AS_"))) {
					softAssert.assertTrue(adTechMethods.matchIdsWithRegex(K),
							"Following ad(s) is/are not shown " + expectedIdsMap.get(K) + " on NRI Listing Page.");
				}
			});
		}
		List<String> ctnAd = adTechMethods.getMissingColumbiaAds();
		softAssert.assertFalse(ctnAd.size() > 0,
				"Following colombia ad(s) is/are not shown " + ctnAd + " on NRI Listing Page.");
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Nris In News section on NRI page", enabled = false)
	public void verifyNrisInNewsSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = nriPageMethods.getNrisInNewsLink();
		softAssert.assertTrue(sectionLink.contains("nris-in-news"),
				"<br>- Link under heading of the section is not of Nris In News");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> nrisInNewsHeadlines = nriPageMethods.getNrisInNewsHeadlines();
		List<String> nrisInNewsStories = VerificationUtil.getLinkTextList(nriPageMethods.getNrisInNewsHeadlines());
		softAssert.assertTrue(nrisInNewsStories.size() >= articleCount,
				"<br>- Headlines under NrisInNews sections should be more than " + articleCount + " in number");
		List<String> nrisInNewsStoriesHref = WebBaseMethods.getListHrefUsingJSE(nrisInNewsHeadlines);
		nrisInNewsStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Nris In News section is throwing " + response);
		});
		List<String> nrisInNewsNewsDup = VerificationUtil.isListUnique(nrisInNewsStories);
		softAssert.assertTrue(nrisInNewsNewsDup.isEmpty(),
				"<br>- Nris In News is having duplicate stories, repeating story(s)->" + nrisInNewsNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Nri Real Estate section on NRI page", enabled = false)
	public void verifyNriRealEstateSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = nriPageMethods.getNriRealEstateLink();
		softAssert.assertTrue(sectionLink.contains("nri-real-estate"),
				"<br>- Link under heading of the section is not of Nri Real Estate");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> nriRealEstateHeadlines = nriPageMethods.getNriRealEstateHeadlines();
		List<String> nriRealEstateStories = VerificationUtil
				.getLinkTextList(nriPageMethods.getNriRealEstateHeadlines());
		softAssert.assertTrue(nriRealEstateStories.size() >= articleCount,
				"<br>- Headlines under NriRealEstate sections should be more than " + articleCount + " in number");
		List<String> nriRealEstateStoriesHref = WebBaseMethods.getListHrefUsingJSE(nriRealEstateHeadlines);
		nriRealEstateStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Nri Real Estate section is throwing " + response);
		});
		List<String> nriRealEstateNewsDup = VerificationUtil.isListUnique(nriRealEstateStories);
		softAssert.assertTrue(nriRealEstateNewsDup.isEmpty(),
				"<br>- Nri Real Estate is having duplicate stories, repeating story(s)->" + nriRealEstateNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Nri Investments section on NRI page", enabled = false)
	public void verifyNriInvestmentsSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = nriPageMethods.getNriInvestmentsLink();
		softAssert.assertTrue(sectionLink.contains("nri-investments"),
				"<br>- Link under heading of the section is not of Nri Investments");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> nriInvestmentsHeadlines = nriPageMethods.getNriInvestmentsHeadlines();
		List<String> nriInvestmentsStories = VerificationUtil
				.getLinkTextList(nriPageMethods.getNriInvestmentsHeadlines());
		softAssert.assertTrue(nriInvestmentsStories.size() >= articleCount,
				"<br>- Headlines under Nri Investments sections should be more than " + articleCount + " in number");
		List<String> nriInvestmentsStoriesHref = WebBaseMethods.getListHrefUsingJSE(nriInvestmentsHeadlines);
		nriInvestmentsStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Nri Investments section is throwing " + response);
		});
		List<String> nriInvestmentsNewsDup = VerificationUtil.isListUnique(nriInvestmentsStories);
		softAssert.assertTrue(nriInvestmentsNewsDup.isEmpty(),
				"<br>- Nri Investments is having duplicate stories, repeating story(s)->" + nriInvestmentsNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Forex And Remittance section on Wealth page", enabled = false)
	public void verifyForexAndRemittanceSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = nriPageMethods.getForexAndRemittanceLink();
		softAssert.assertTrue(sectionLink.contains("forex-and-remittance"),
				"<br>- Link under heading of the section is not of Forex And Remittance");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> forexAndRemittanceHeadlines = nriPageMethods.getForexAndRemittanceHeadlines();
		List<String> forexAndRemittanceStories = VerificationUtil
				.getLinkTextList(nriPageMethods.getForexAndRemittanceHeadlines());
		softAssert.assertTrue(forexAndRemittanceStories.size() >= articleCount,
				"<br>- Headlines under Forex And Remittance sections should be more than " + articleCount
						+ " in number");
		List<String> forexAndRemittanceStoriesHref = WebBaseMethods.getListHrefUsingJSE(forexAndRemittanceHeadlines);
		forexAndRemittanceStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Forex And Remittance section is throwing " + response);
		});
		List<String> forexAndRemittanceNewsDup = VerificationUtil.isListUnique(forexAndRemittanceStories);
		softAssert.assertTrue(forexAndRemittanceNewsDup.isEmpty(),
				"<br>- Forex And Remittance is having duplicate stories, repeating story(s)->"
						+ forexAndRemittanceNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Visa And Immigration section on NRI page", enabled = false)
	public void verifyVisaAndImmigrationSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = nriPageMethods.getVisaAndImmigrationLink();
		softAssert.assertTrue(sectionLink.contains("visa-and-immigration"),
				"<br>- Link under heading of the section is not of Visa And Immigration");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> visaAndImmigrationHeadlines = nriPageMethods.getVisaAndImmigrationHeadlines();
		List<String> visaAndImmigrationStories = VerificationUtil
				.getLinkTextList(nriPageMethods.getVisaAndImmigrationHeadlines());
		softAssert.assertTrue(visaAndImmigrationStories.size() >= articleCount,
				"<br>- Headlines under Visa And Immigration sections should be more than " + articleCount
						+ " in number");
		List<String> visaAndImmigrationStoriesHref = WebBaseMethods.getListHrefUsingJSE(visaAndImmigrationHeadlines);
		visaAndImmigrationStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Visa And Immigration section is throwing " + response);
		});
		List<String> visaAndImmigrationNewsDup = VerificationUtil.isListUnique(visaAndImmigrationStories);
		softAssert.assertTrue(visaAndImmigrationNewsDup.isEmpty(),
				"<br>- Visa And Immigration is having duplicate stories, repeating story(s)->"
						+ visaAndImmigrationNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Working Abroad section on NRI page", enabled = false)
	public void verifyWorkingAbroadSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = nriPageMethods.getWorkingAbroadLink();
		softAssert.assertTrue(sectionLink.contains("working-abroad"),
				"<br>- Link under heading of the section is not of Working Abroad");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> workingAbroadHeadlines = nriPageMethods.getWorkingAbroadHeadlines();
		List<String> workingAbroadStories = VerificationUtil
				.getLinkTextList(nriPageMethods.getWorkingAbroadHeadlines());
		softAssert.assertTrue(workingAbroadStories.size() >= articleCount,
				"<br>- Headlines under Working Abroad sections should be more than " + articleCount + " in number");
		List<String> workingAbroadStoriesHref = WebBaseMethods.getListHrefUsingJSE(workingAbroadHeadlines);
		workingAbroadStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Working Abroad section is throwing " + response);
		});
		List<String> workingAbroadNewsDup = VerificationUtil.isListUnique(workingAbroadStories);
		softAssert.assertTrue(workingAbroadNewsDup.isEmpty(),
				"<br>- Working Abroad is having duplicate stories, repeating story(s)->" + workingAbroadNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Returning To India section on NRI page", enabled = false)
	public void verifyReturningToIndiaSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = nriPageMethods.getReturningToIndiaLink();
		softAssert.assertTrue(sectionLink.contains("returning-to-india"),
				"<br>- Link under heading of the section is not of Returning To India");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> returningToIndiaHeadlines = nriPageMethods.getReturningToIndiaHeadlines();
		List<String> returningToIndiaStories = VerificationUtil
				.getLinkTextList(nriPageMethods.getReturningToIndiaHeadlines());
		softAssert.assertTrue(returningToIndiaStories.size() >= articleCount,
				"<br>- Headlines under Returning To India sections should be more than " + articleCount + " in number");
		List<String> returningToIndiaStoriesHref = WebBaseMethods.getListHrefUsingJSE(returningToIndiaHeadlines);
		returningToIndiaStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Returning To India section is throwing " + response);
		});
		List<String> returningToIndiaNewsDup = VerificationUtil.isListUnique(returningToIndiaStories);
		softAssert.assertTrue(returningToIndiaNewsDup.isEmpty(),
				"<br>- Returning To India is having duplicate stories, repeating story(s)->" + returningToIndiaNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Nri Services section on NRI page", enabled = false)
	public void verifyNriServicesSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = nriPageMethods.getNriServicesLink();
		Assert.assertTrue(sectionLink.length() > 0, "Section Services not found");
		softAssert.assertTrue(sectionLink.contains("nriServices"),
				"<br>- Link under heading of the section is not of Nri Services");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> nriServicesHeadlines = nriPageMethods.getNriServicesHeadlines();
		List<String> nriServicesStories = VerificationUtil.getLinkTextList(nriPageMethods.getNriServicesHeadlines());
		softAssert.assertTrue(nriServicesStories.size() >= articleCount,
				"<br>- Headlines under Nri Services sections should be more than " + articleCount + " in number");
		List<String> nriServicesStoriesHref = WebBaseMethods.getListHrefUsingJSE(nriServicesHeadlines);
		nriServicesStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Nri Services section is throwing " + response);
		});
		List<String> nriServicesNewsDup = VerificationUtil.isListUnique(nriServicesStories);
		softAssert.assertTrue(nriServicesNewsDup.isEmpty(),
				"<br>- Nri Services is having duplicate stories, repeating story(s)->" + nriServicesNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Nri Tax section on NRI page", enabled = false)
	public void verifyNriTaxSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = nriPageMethods.getNriTaxLink();
		softAssert.assertTrue(sectionLink.contains("nri-tax"),
				"<br>- Link under heading of the section is not of Nri Tax");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> nriTaxHeadlines = nriPageMethods.getNriTaxHeadlines();
		List<String> nriTaxStories = VerificationUtil.getLinkTextList(nriPageMethods.getNriTaxHeadlines());
		softAssert.assertTrue(nriTaxStories.size() >= articleCount,
				"<br>- Headlines under Nri Tax sections should be more than " + articleCount + " in number");
		List<String> nriTaxStoriesHref = WebBaseMethods.getListHrefUsingJSE(nriTaxHeadlines);
		nriTaxStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Nri Tax section is throwing " + response);
		});
		List<String> nriTaxNewsDup = VerificationUtil.isListUnique(nriTaxStories);
		softAssert.assertTrue(nriTaxNewsDup.isEmpty(),
				"<br>- Nri Tax is having duplicate stories, repeating story(s)->" + nriTaxNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies date and time shown on header on nri page")
	public void verifyDateTime() {
		softAssert = new SoftAssert();
		String dateTimeN = headerPageMethods.getDateTimeTab();
		DateTime systemDate = new DateTime();
		DateTime date = WebBaseMethods.convertDateMethod(
				dateTimeN.replaceAll("\\|", "").replaceAll(" ", "").replaceAll(",", "").replaceAll("\\.", ":"));

		Assert.assertTrue(dateTimeN != null, "Date time is not shown on header on NRI page");
		System.out.println(systemDate.getDayOfYear() + "------" + date.getDayOfYear());
		softAssert.assertTrue(systemDate.getDayOfYear() == date.getDayOfYear(),
				"The date shown on NRI page is not the current date instead is showing " + dateTimeN);
		softAssert.assertTrue(
				(systemDate.getMinuteOfDay() - date.getMinuteOfDay() <= 20
						&& systemDate.getMinuteOfDay() - date.getMinuteOfDay() >= 0),
				"on NRI  page the time shown " + dateTimeN
						+ " is having difference of more than 20 mins from current time"
						+ systemDate.toString("MMM dd, yyyy hh:mm a"));
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the top trending widget on the NRI page", enabled = false)
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

	@Test(description = "This test verifies the top stories on the NRI page")
	public void verifyTopStoriesOnNRIPage() {
		softAssert = new SoftAssert();
		try {
			List<String> topStoriesOnNRIHref = nriPageMethods.getTopStoriesLinksNRIPage();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(topStoriesOnNRIHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(topStoriesOnNRIHref);
			softAssert.assertTrue(topStoriesOnNRIHref.size() >= 7,
					"The number of terms under the top stories should be more than equal to 7 but found: "
							+ topStoriesOnNRIHref.size());
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

	@Test(description = "This test verifies the stories count in the Not to be missed section", enabled = false)
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

	@Test(description = "This test verifies the video widget on the NRI page", enabled = false)
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

	@Test(description = "This test verifies the Stories under subsections on the NRI page", dataProvider = "subSections")
	public void verifySubSectionsOnNRIPage(String section) {
		softAssert = new SoftAssert();
		try {
			List<String> subSecStoriesHref = commonL1L2PagesMethods.getSubSectionStoriesLinks(section);
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(subSecStoriesHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(subSecStoriesHref);
			softAssert.assertTrue(subSecStoriesHref.size() >= 2, "The number of links under the sub section: " + section
					+ " should be more than equal to 2 but found: " + subSecStoriesHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the sub section: " + section
							+ ". List of such links are: " + errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the sub section: " + section + " " + dupLinks);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run for section: " + section);
		}
		softAssert.assertAll();

	}

	@DataProvider
	public String[] subSections() {
		String[] sections = { "Migrate", "Work", "Trending in U.S.", "Study", "Visit", "Invest" };

		return sections;

	}

	@Test(description = "This test verifies the latest Stories section on the NRI page")
	public void verifyLatestStoriesNRIPage() {
		softAssert = new SoftAssert();
		try {
			List<String> subSecStoriesHref = nriPageMethods.getNRILatestNewsSectionLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(subSecStoriesHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(subSecStoriesHref);
			softAssert.assertTrue(subSecStoriesHref.size() >= 4,
					"The number of links under the Latest Stories section should be more than equal to 4 but found: "
							+ subSecStoriesHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200. List of such links are: " + errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the lates stories section " + dupLinks);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the TOP news from ET section on the NRI page")
	public void verifyTOPNewsFromETNRIPage() {
		softAssert = new SoftAssert();
		try {
			List<String> subSecStoriesHref = nriPageMethods.getTopNewsFromETLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(subSecStoriesHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(subSecStoriesHref);
			softAssert.assertTrue(subSecStoriesHref.size() >= 4,
					"The number of links under the Top News from ET should be more than equal to 4 but found: "
							+ subSecStoriesHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200. List of such links are: " + errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the  Top News from ET section " + dupLinks);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the top trending widget on the NRI page")
	public void verifyTopTrendingWidget() {
		softAssert = new SoftAssert();
		try {

			List<String> topTrendingWidgetLinks = nriPageMethods.getTopTrendingLinks();
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

}
