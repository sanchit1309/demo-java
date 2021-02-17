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
import web.pagemethods.TechPageMethods;
import web.pagemethods.WebBaseMethods;

public class TechPage extends BaseTest {

	private String baseUrl;
	HomePageMethods homePageMethods;
	TechPageMethods techPageMethods;
	HeaderPageMethods headerPageMethods;
	AdTechMethods adTechMethods;
	CommonL1L2PagesMethods commonL1L2PagesMethods;
	SoftAssert softAssert;

	@BeforeClass
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl + Config.fetchConfigProperty("TechUrl");
		launchBrowser(baseUrl);
		homePageMethods = new HomePageMethods(driver);
		techPageMethods = new TechPageMethods(driver);
		headerPageMethods = new HeaderPageMethods(driver);
		adTechMethods = new AdTechMethods(driver);
		commonL1L2PagesMethods = new CommonL1L2PagesMethods(driver);
		WebBaseMethods.scrollToBottom();

	}

	@Test(description = " This test verifies the ads on Tech Listing Page", enabled = false)
	public void verifyAds() {
		softAssert = new SoftAssert();
		WaitUtil.sleep(2000);
		Map<String, String> expectedIdsMap = Config.getAllKeys("adIdLocation");
		softAssert.assertTrue(adTechMethods.getDisplayedAdIds().size() > 0,
				"No google ads shown on Tech Listing Page.");
		if (adTechMethods.getDisplayedAdIds().size() > 0) {
			expectedIdsMap.forEach((K, V) -> {
				if (K.contains("ET_ROS") && !(K.contains("_AS_"))) {
					softAssert.assertTrue(adTechMethods.matchIdsWithRegex(K),
							"Following ad(s) is/are not shown " + expectedIdsMap.get(K) + " on Tech Listing Page.");
				}
			});
		}
		List<String> ctnAd = adTechMethods.getMissingColumbiaAds();
		softAssert.assertFalse(ctnAd.size() > 0,
				"Following colombia ad(s) is/are not shown " + ctnAd + " on Tech Listing Page.");
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Hardware section on Tech page", enabled = false)
	public void verifyHardwareSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = techPageMethods.getHardwareLink();
		softAssert.assertTrue(sectionLink.contains("hardware"),
				"<br>- Link under heading of the section is not of Hardware");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> hardwareHeadlines = techPageMethods.getHardwareHeadlines();
		List<String> hardwareStories = VerificationUtil.getLinkTextList(techPageMethods.getHardwareHeadlines());
		softAssert.assertTrue(hardwareStories.size() >= articleCount,
				"<br>- Headlines under Hardware sections should be more than " + articleCount + " in number");
		List<String> hardwareStoriesHref = WebBaseMethods.getListHrefUsingJSE(hardwareHeadlines);
		hardwareStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Hardware section is throwing " + response);
		});
		List<String> hardwareNewsDup = VerificationUtil.isListUnique(hardwareStories);
		softAssert.assertTrue(hardwareNewsDup.isEmpty(),
				"<br>- Hardware is having duplicate stories, repeating story(s)->" + hardwareNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Software section on Tech page", enabled = false)
	public void verifySoftwareSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = techPageMethods.getSoftwareLink();
		softAssert.assertTrue(sectionLink.contains("software"),
				"<br>- Link under heading of the section is not of Software");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> softwareHeadlines = techPageMethods.getSoftwareHeadlines();
		List<String> softwareStories = VerificationUtil.getLinkTextList(techPageMethods.getSoftwareHeadlines());
		softAssert.assertTrue(softwareStories.size() >= articleCount,
				"<br>- Headlines under software sections should be more than " + articleCount + " in number");
		List<String> softwareStoriesHref = WebBaseMethods.getListHrefUsingJSE(softwareHeadlines);
		softwareStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Software section is throwing " + response);
		});
		List<String> softwareNewsDup = VerificationUtil.isListUnique(softwareStories);
		softAssert.assertTrue(softwareNewsDup.isEmpty(),
				"<br>- Software is having duplicate stories, repeating story(s)->" + softwareNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Internet section on Tech page", enabled = false)
	public void verifyInternetSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = techPageMethods.getInternetLink();
		softAssert.assertTrue(sectionLink.contains("internet"),
				"<br>- Link under heading of the section is not of Internet");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> internetHeadlines = techPageMethods.getInternetHeadlines();
		List<String> internetStories = VerificationUtil.getLinkTextList(techPageMethods.getInternetHeadlines());
		softAssert.assertTrue(internetStories.size() >= articleCount,
				"<br>- Headlines under internet sections should be more than " + articleCount + " in number");
		List<String> internetStoriesHref = WebBaseMethods.getListHrefUsingJSE(internetHeadlines);
		internetStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Internet section is throwing " + response);
		});
		List<String> internetNewsDup = VerificationUtil.isListUnique(internetStories);
		softAssert.assertTrue(internetNewsDup.isEmpty(),
				"<br>- Internet is having duplicate stories, repeating story(s)->" + internetNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies ITeS section on Tech page", enabled = false)
	public void verifyItesSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = techPageMethods.getItesLink();
		softAssert.assertTrue(sectionLink.contains("ites"), "<br>- Link under heading of the section is not of ITeS");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> itesHeadlines = techPageMethods.getItesHeadlines();
		List<String> itesStories = VerificationUtil.getLinkTextList(techPageMethods.getItesHeadlines());
		softAssert.assertTrue(itesStories.size() >= articleCount,
				"<br>- Headlines under Ites sections should be more than " + articleCount + " in number");
		List<String> itesStoriesHref = WebBaseMethods.getListHrefUsingJSE(itesHeadlines);
		itesStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Ites section is throwing " + response);
		});
		List<String> itesNewsDup = VerificationUtil.isListUnique(itesStories);
		softAssert.assertTrue(itesNewsDup.isEmpty(),
				"<br>- Ites is having duplicate stories, repeating story(s)->" + itesNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies date and time shown on header on tech page")
	public void verifyDateTime() {
		softAssert = new SoftAssert();
		String dateTimeN = headerPageMethods.getDateTimeTab();

		DateTime systemDate = new DateTime();

		DateTime date = WebBaseMethods.convertDateMethod(
				dateTimeN.replaceAll("\\|", "").replaceAll(" ", "").replaceAll(",", "").replaceAll("\\.", ":"));

		Assert.assertTrue(dateTimeN != null, "Date time is not shown on header on tech page");
		softAssert.assertTrue(systemDate.getDayOfYear() == date.getDayOfYear(),
				"The date shown on tech page is not the current date instead is showing " + dateTimeN);
		softAssert.assertTrue(
				(systemDate.getMinuteOfDay() - date.getMinuteOfDay() <= 20
						&& systemDate.getMinuteOfDay() - date.getMinuteOfDay() >= 0),
				"on tech  page the time shown " + dateTimeN
						+ " is having difference of more than 20 mins from current time"
						+ systemDate.toString("MMM dd, yyyy hh:mm a"));
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the video widget on the Tech page", enabled = false)
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

	@Test(description = "This test verifies the top trending widget on the Tech page", enabled = false)
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

	@Test(description = "This test verifies the top stories on the Tech page")
	public void verifyTopStoriesOnTechPage() {
		softAssert = new SoftAssert();
		try {
			List<String> topStoriesOnNewsHref = techPageMethods.getTopStoriesLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(topStoriesOnNewsHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(topStoriesOnNewsHref);
			softAssert.assertTrue(topStoriesOnNewsHref.size() >= 7,
					"The number of terms under the top stories should be more than equal to 7 but found: "
							+ topStoriesOnNewsHref.size());
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

	@Test(description = "This test verifies Tech and gadgets section on Tech page", enabled = false)
	public void verifyTechAndGadgetsSection() {
		int articleCount = 5;
		softAssert = new SoftAssert();
		String sectionLink = techPageMethods.getTechAndGadgetsLink();
		softAssert.assertTrue(sectionLink.contains("tech-and-gadgets"),
				"<br>- Link under heading of the section is not of Tech and gadget");
		int responseCode = HTTPResponse.checkResponseCode(sectionLink);
		softAssert.assertEquals(responseCode, 200,
				"<br>- <a href=" + sectionLink + ">Link under heading </a> is throwing " + responseCode);
		List<WebElement> techAndGadgetsHeadlines = techPageMethods.getTechAndGadgetsHeadlines();
		List<String> techAndGadgetsStories = VerificationUtil
				.getLinkTextList(techPageMethods.getTechAndGadgetsHeadlines());
		softAssert.assertTrue(techAndGadgetsStories.size() >= articleCount,
				"<br>- Headlines under Tech and Gadgets sections should be more than " + articleCount + " in number");
		List<String> techAndGadgetStoriesHref = WebBaseMethods.getListHrefUsingJSE(techAndGadgetsHeadlines);
		techAndGadgetStoriesHref.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in Tech and Gadgets  section is throwing " + response);
		});
		List<String> techAndGadgetsNewsDup = VerificationUtil.isListUnique(techAndGadgetsStories);
		softAssert.assertTrue(techAndGadgetsNewsDup.isEmpty(),
				"<br>- Tech and Gadgets is having duplicate stories, repeating story(s)->" + techAndGadgetsNewsDup);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the Stories under subsections on the Tech page", dataProvider = "subSections")
	public void verifySubSectionsOnTechPage(String section) {
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
		String[] sections = { "ITES", "Trending Stories", "Startups", "Funding", "Tech & Internet", "The Catalysts",
				"Tech Bytes", "Tech VIDEOS" };

		return sections;

	}
	
	@Test(description = "This test verifies the top trending widget on the Tech page")
	public void verifyTopTrendingWidget() {
		softAssert = new SoftAssert();
		try {

			List<String> topTrendingWidgetLinks = techPageMethods.getTopTrendingLinks();
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
