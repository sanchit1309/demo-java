package web.tests;

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
import web.pagemethods.NewsPageMethods;
import web.pagemethods.WebBaseMethods;

public class NewsPage extends BaseTest {
	private String baseUrl;
	AdTechMethods adTechMethods;
	NewsPageMethods newsPageMethods;
	HeaderPageMethods headerPageMethods;
	CommonL1L2PagesMethods commonL1L2PagesMethods;
	SoftAssert softAssert;

	@BeforeClass
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl + Config.fetchConfigProperty("NewsUrl");
		launchBrowser(baseUrl);
		headerPageMethods = new HeaderPageMethods(driver);
		newsPageMethods = new NewsPageMethods(driver);
		commonL1L2PagesMethods = new CommonL1L2PagesMethods(driver);
		WebBaseMethods.scrollToBottom();
		adTechMethods = new AdTechMethods(driver);
	}

	@Test(description = " This test verifies the ads on News Listing Page")
	public void verifyAds() {
		softAssert = new SoftAssert();
		WaitUtil.sleep(2000);
		Map<String, String> expectedIdsMap = Config.getAllKeys("adIdLocation");
		softAssert.assertTrue(adTechMethods.getDisplayedAdIds().size() > 0,
				"No google ads shown on News Listing Page.");
		if (adTechMethods.getDisplayedAdIds().size() > 0) {
			expectedIdsMap.forEach((K, V) -> {
				if (K.contains("ET_ROS") && !(K.contains("_AS_"))) {
					softAssert.assertTrue(adTechMethods.matchIdsWithRegex(K),
							"Following ad(s) is/are not shown " + expectedIdsMap.get(K) + " on News Listing Page.");
				}
			});
		}
		List<String> ctnAd = adTechMethods.getMissingColumbiaAds();
		softAssert.assertFalse(ctnAd.size() > 0,
				"Following colombia ad(s) is/are not shown " + ctnAd + " on News Listing Page.");
		softAssert.assertAll();
	}

	@Test(description = "This test verifies company section on News page")
	public void verifyCompanySection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = newsPageMethods.getCompanyLink();
		softAssert.assertTrue(sectionLink.contains("company"),
				"<br>- Link under heading of the section is not of Company");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> companyHeadlines = newsPageMethods.getCompanyHeadlines();
		List<String> companyStories = VerificationUtil.getLinkTextList(newsPageMethods.getCompanyHeadlines());
		softAssert.assertTrue(companyStories.size() >= articleCount,
				"<br>- Headlines under Company sections should be more than " + articleCount + " in number");
		List<String> companiesStoriesHref = WebBaseMethods.getListHrefUsingJSE(companyHeadlines);
		companiesStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Company section is throwing " + response);
		});
		List<String> companyNewsDup = VerificationUtil.isListUnique(companyStories);
		softAssert.assertTrue(companyNewsDup.isEmpty(),
				"<br>- Company is having duplicate stories, repeating story(s)->" + companyNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Economy section on News page")
	public void verifyEconomySection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = newsPageMethods.getEconomyLink();
		softAssert.assertTrue(sectionLink.contains("economy"),
				"<br>- Link under heading of the section is not of Economy");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> economyHeadlines = newsPageMethods.getEconomyHeadlines();
		List<String> economyStories = VerificationUtil.getLinkTextList(newsPageMethods.getEconomyHeadlines());
		softAssert.assertTrue(economyStories.size() >= articleCount,
				"<br>- Headlines under economy sections should be more than " + articleCount + " in number");
		List<String> economyStoriesHref = WebBaseMethods.getListHrefUsingJSE(economyHeadlines);
		economyStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Company section is throwing " + response);
		});
		List<String> economyNewsDup = VerificationUtil.isListUnique(economyStories);
		softAssert.assertTrue(economyNewsDup.isEmpty(),
				"<br>- Economy is having duplicate stories, repeating story(s)->" + economyNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Politics and Nation section on News page")
	public void verifyPoliticsAndNationSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = newsPageMethods.getPoliticsAndNationLink();
		softAssert.assertTrue(sectionLink.contains("politics"),
				"<br>- Link under heading of the section is not of Politics And Nation");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> politicsAndNationHeadlines = newsPageMethods.getPoliticsAndNationHeadlines();
		List<String> politicsAndNationStories = VerificationUtil.getLinkTextList(newsPageMethods.getEconomyHeadlines());
		softAssert.assertTrue(politicsAndNationStories.size() >= articleCount,
				"<br>- Headlines under Politics And Nation sections should be more than " + articleCount
						+ " in number");
		List<String> politicsAndNationHref = WebBaseMethods.getListHrefUsingJSE(politicsAndNationHeadlines);
		politicsAndNationHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Politics And Nation section is throwing " + response);
		});
		List<String> politicsAndNationNewsDup = VerificationUtil.isListUnique(politicsAndNationStories);
		softAssert.assertTrue(politicsAndNationNewsDup.isEmpty(),
				"<br>- Politics And Nation is having duplicate stories, repeating story(s)->"
						+ politicsAndNationNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Brandwire section on News page", enabled = false)
	public void verifyBrandwireSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = newsPageMethods.getBrandwireLink();
		System.out.println(newsPageMethods.getBrandwireLink());
		softAssert.assertTrue(sectionLink.contains("brandwire"),
				"<br>- Link under heading of the section is not of Brandwire");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> brandwireHeadlines = newsPageMethods.getBrandwireHeadlines();
		List<String> brandwireStories = VerificationUtil.getLinkTextList(newsPageMethods.getBrandwireHeadlines());
		softAssert.assertTrue(brandwireStories.size() >= articleCount,
				"<br>- Headlines under brandwire sections should be more than " + articleCount + " in number");
		List<String> brandwireStoriesHref = WebBaseMethods.getListHrefUsingJSE(brandwireHeadlines);
		brandwireStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Brandwire section is throwing " + response);
		});
		List<String> brandwireNewsDup = VerificationUtil.isListUnique(brandwireStories);
		softAssert.assertTrue(brandwireNewsDup.isEmpty(),
				"<br>- Brandwire is having duplicate stories, repeating story(s)->" + brandwireNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Defence section on News page")
	public void verifyDefenceSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = newsPageMethods.getDefenseLink();
		System.out.println(newsPageMethods.getDefenseLink());
		softAssert.assertTrue(sectionLink.contains("defence"),
				"<br>- Link under heading of the section is not of Defence");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> defenceHeadlines = newsPageMethods.getDefenseHeadlines();
		List<String> defenceStories = VerificationUtil.getLinkTextList(newsPageMethods.getDefenseHeadlines());
		softAssert.assertTrue(defenceStories.size() >= articleCount,
				"<br>- Headlines under Defence sections should be more than " + articleCount + " in number");
		List<String> defenceStoriesHref = WebBaseMethods.getListHrefUsingJSE(defenceHeadlines);
		defenceStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in defence section is throwing " + response);
		});
		List<String> defenceNewsDup = VerificationUtil.isListUnique(defenceStories);
		softAssert.assertTrue(defenceNewsDup.isEmpty(),
				"<br>- Defence is having duplicate stories, repeating story(s)->" + defenceNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies International section on News page")
	public void verifyInternationalSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = newsPageMethods.getInternationalLink();
		softAssert.assertTrue(sectionLink.contains("international"),
				"<br>- Link under heading of the section is not of International");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> internationalHeadlines = newsPageMethods.getInternationalHeadlines();
		List<String> internationalStories = VerificationUtil
				.getLinkTextList(newsPageMethods.getInternationalHeadlines());
		softAssert.assertTrue(internationalStories.size() >= articleCount,
				"<br>- Headlines under International sections should be more than " + articleCount + " in number");
		List<String> internationalStoriesHref = WebBaseMethods.getListHrefUsingJSE(internationalHeadlines);
		internationalStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in International section is throwing " + response);
		});
		List<String> internationalNewsDup = VerificationUtil.isListUnique(internationalStories);
		softAssert.assertTrue(internationalNewsDup.isEmpty(),
				"<br>- International is having duplicate stories, repeating story(s)->" + internationalNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies India Unlimited section on News page")
	public void verifyIndiaUnlimitedSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = newsPageMethods.getIndiaUnlimitedLink();
		softAssert.assertTrue(sectionLink.contains("india-unlimited"),
				"<br>- Link under heading of the section is not of India Unlimited");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> indiaUnlimitedHeadlines = newsPageMethods.getIndiaUnlimitedHeadlines();
		List<String> indiaUnlimitedStories = VerificationUtil
				.getLinkTextList(newsPageMethods.getIndiaUnlimitedHeadlines());
		softAssert.assertTrue(indiaUnlimitedStories.size() >= articleCount,
				"<br>- Headlines under India Unlimited  sections should be more than " + articleCount + " in number");
		List<String> indiaUnlimitedStoriesHref = WebBaseMethods.getListHrefUsingJSE(indiaUnlimitedHeadlines);
		indiaUnlimitedStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in India Unlimited section is throwing " + response);
		});
		List<String> indiaUnlimitedNewsDup = VerificationUtil.isListUnique(indiaUnlimitedStories);
		softAssert.assertTrue(indiaUnlimitedNewsDup.isEmpty(),
				"<br>- India Unlimited is having duplicate stories, repeating story(s)->" + indiaUnlimitedNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies company section on News page")
	public void verifySportsSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = newsPageMethods.getSportsLink();
		softAssert.assertTrue(sectionLink.contains("sports"),
				"<br>- Link under heading of the section is not of Sports");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> sportsHeadlines = newsPageMethods.getSportsHeadlines();
		List<String> sportsStories = VerificationUtil.getLinkTextList(newsPageMethods.getSportsHeadlines());
		softAssert.assertTrue(sportsStories.size() >= articleCount,
				"<br>- Headlines under Sports sections should be more than " + articleCount + " in number");
		List<String> sportsStoriesHref = WebBaseMethods.getListHrefUsingJSE(sportsHeadlines);
		sportsStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Sports section is throwing " + response);
		});
		List<String> sportsNewsDup = VerificationUtil.isListUnique(sportsStories);
		softAssert.assertTrue(sportsNewsDup.isEmpty(),
				"<br>- Sports is having duplicate stories, repeating story(s)->" + sportsNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Science section on News page")
	public void verifyScienceSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = newsPageMethods.getScienceLink();
		softAssert.assertTrue(sectionLink.contains("science"),
				"<br>- Link under heading of the section is not of Science");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> scienceHeadlines = newsPageMethods.getScienceHeadlines();
		List<String> scienceStories = VerificationUtil.getLinkTextList(newsPageMethods.getScienceHeadlines());
		softAssert.assertTrue(scienceStories.size() >= articleCount,
				"<br>- Headlines under Science sections should be more than " + articleCount + " in number");
		List<String> scienceStoriesHref = WebBaseMethods.getListHrefUsingJSE(scienceHeadlines);
		scienceStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Science section is throwing " + response);
		});
		List<String> scienceNewsDup = VerificationUtil.isListUnique(scienceStories);
		softAssert.assertTrue(scienceNewsDup.isEmpty(),
				"<br>- Science is having duplicate stories, repeating story(s)->" + scienceNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Enviornment section on News page")
	public void verifyEnvironmentSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = newsPageMethods.getEnviornmentLink();
		softAssert.assertTrue(sectionLink.contains("environment"),
				"<br>- Link under heading of the section is not of Environment");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> enviornmentHeadlines = newsPageMethods.getEnviornmentHeadlines();
		List<String> enviornmentStories = VerificationUtil.getLinkTextList(newsPageMethods.getEnviornmentHeadlines());
		softAssert.assertTrue(enviornmentStories.size() >= articleCount,
				"<br>- Headlines under Enviornment sections should be more than " + articleCount + " in number");
		List<String> enviornmentStoriesHref = WebBaseMethods.getListHrefUsingJSE(enviornmentHeadlines);
		enviornmentStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Enviornment section is throwing " + response);
		});
		List<String> enviornmentNewsDup = VerificationUtil.isListUnique(enviornmentStories);
		softAssert.assertTrue(enviornmentNewsDup.isEmpty(),
				"<br>- Enviornment is having duplicate stories, repeating story(s)->" + enviornmentNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies date and time shown on header on news page")
	public void verifyDateTime() {
		softAssert = new SoftAssert();
		String dateTimeN = headerPageMethods.getDateTimeTab();
		DateTime systemDate = new DateTime();

		DateTime date = WebBaseMethods.convertDateMethod(
				dateTimeN.replaceAll("\\|", "").replaceAll(" ", "").replaceAll(",", "").replaceAll("\\.", ":"));

		Assert.assertTrue(dateTimeN != null, "Date time is not shown on header on news page");
		softAssert.assertTrue(systemDate.getDayOfYear() == date.getDayOfYear(),
				"The date shown on news page is not the current date instead is showing " + dateTimeN);
		softAssert.assertTrue(
				(systemDate.getMinuteOfDay() - date.getMinuteOfDay() <= 20
						&& systemDate.getMinuteOfDay() - date.getMinuteOfDay() >= 0),
				"on news  page the time shown " + dateTimeN
						+ " is having difference of more than 20 mins from current time "
						+ systemDate.toString("MMM dd, yyyy hh:mm a"));
		softAssert.assertAll();

	}

	@Test(description = "This test verifies latest from markets section", enabled = false)
	public void verifyLatestFromMarketsNews() {
		softAssert = new SoftAssert();
		int articleCount = 5;
		List<Double> benchamrkUI = newsPageMethods.getBenchmarkTextValuesUI();
		List<Double> benchmarkExpected = newsPageMethods.getBenchmarkValuesFromAPI();
		Assert.assertTrue(benchamrkUI.size() == 3, "Value of benchmark is not Sensex,Nift,Gold");
		softAssert.assertTrue(VerificationUtil.valueIsInRange(benchamrkUI.get(0), benchmarkExpected.get(0), 20),
				"Value of sensex on UI: " + benchamrkUI.get(0) + " differing from expected value:"
						+ benchmarkExpected.get(0));
		softAssert.assertTrue(VerificationUtil.valueIsInRange(benchamrkUI.get(1), benchmarkExpected.get(1), 20),
				"Value of nifty on UI: " + benchamrkUI.get(1) + " differing from expected value:"
						+ benchmarkExpected.get(1));
		softAssert.assertTrue(VerificationUtil.valueIsInRange(benchamrkUI.get(2), benchmarkExpected.get(2), 20),
				"Value of gold on UI: " + benchamrkUI.get(2) + " differing from expected value:"
						+ benchmarkExpected.get(2));
		List<WebElement> marketNews = newsPageMethods.getMarketLatestNews();
		List<String> marketStories = VerificationUtil.getLinkTextList(marketNews);
		softAssert.assertTrue(marketStories.size() >= articleCount,
				"<br>- Headlines under Markets section should be more than " + articleCount + " in number");
		List<String> enviornmentStoriesHref = WebBaseMethods.getListHrefUsingJSE(marketNews);
		enviornmentStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Markets section is throwing " + response);
		});
		List<String> marketNewsDup = VerificationUtil.isListUnique(marketStories);
		softAssert.assertTrue(marketNewsDup.isEmpty(),
				"<br>- Market News is having duplicate stories, repeating story(s)->" + marketNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the video widget on the News page")
	public void verifyVideoWidget() {
		softAssert = new SoftAssert();
		List<WebElement> videoWidgetTabs = commonL1L2PagesMethods.listOfVideoWidgetTabs();

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
			softAssert.assertTrue(dupLinks.size() == 0, "Duplicate links are present under the video widget for tab: "
					+ tab.getText() + ".List of links: " + dupLinks);
		});

		softAssert.assertAll();

	}

	@Test(description = "This test verifies the top trending widget on the News page")
	public void verifyTopTrendingWidgetInRhs() {
		softAssert = new SoftAssert();

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

		softAssert.assertAll();

	}

	@Test(description = "This test verifies the top stories on the News page")
	public void verifyTopStoriesOnNewsPage() {
		softAssert = new SoftAssert();
		List<String> topStoriesOnNewsHref = newsPageMethods.getTopStoriesNewsPageLinksHref();
		List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(topStoriesOnNewsHref);
		List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(topStoriesOnNewsHref);
		softAssert.assertTrue(topStoriesOnNewsHref.size() >= 7,
				"The number of links under the top stories should be more than equal to 7 but found: "
						+ topStoriesOnNewsHref.size());
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having response code 200 under the top stories. List of such links are: "
						+ errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0, "Duplicate links are present under the top stories: " + dupLinks);

		softAssert.assertAll();

	}

	@Test(description = "This test verifies the stories count in the Not to be missed section")
	public void verifyNotToBeMissedSection() {
		try {
			softAssert = new SoftAssert();

			List<String> notToBeMissedSectionLinks = commonL1L2PagesMethods.getNotToBeMissedSectionLInks();
			System.out.println(notToBeMissedSectionLinks.size());
			softAssert.assertTrue(notToBeMissedSectionLinks.size() > 10,
					"Not to be missed section on the news page is shown blank or having links less than 10");

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();
	}

}
