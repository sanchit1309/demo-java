package web.tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
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
import web.pagemethods.PanacheMethods;
import web.pagemethods.WebBaseMethods;

public class Panache extends BaseTest {
	private PanacheMethods panacheMethods;
	HeaderPageMethods headerPageMethods;
	private SoftAssert softAssert;
	private AdTechMethods adTechMethods;
	CommonL1L2PagesMethods commonL1L2PagesMethods;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		String pageUrl = BaseTest.baseUrl + Config.fetchConfigProperty("PanacheUrl");
		launchBrowser(pageUrl);
		panacheMethods = new PanacheMethods(driver);
		headerPageMethods = new HeaderPageMethods(driver);
		commonL1L2PagesMethods = new CommonL1L2PagesMethods(driver);
		adTechMethods = new AdTechMethods(driver);
	}

	@Test(description = " This test verifies the ads on Panache Listing Page")
	public void verifyAds() {
		softAssert = new SoftAssert();
		WaitUtil.sleep(2000);
		Map<String, String> expectedIdsMap = Config.getAllKeys("adIdLocation");
		softAssert.assertTrue(adTechMethods.getDisplayedAdIds().size() > 0,
				"No google ads shown on Panache Listing Page.");
		if (adTechMethods.getDisplayedAdIds().size() > 0) {
			expectedIdsMap.forEach((K, V) -> {
				if (K.contains("ET_ROS") && !(K.contains("_AS_"))) {
					softAssert.assertTrue(adTechMethods.matchIdsWithRegex(K),
							"Following ad(s) is/are not shown " + expectedIdsMap.get(K) + " on Panache Listing Page.");
				}
			});
		}
		List<String> ctnAd = adTechMethods.getMissingColumbiaAds();
		softAssert.assertFalse(ctnAd.size() > 0,
				"Following colombia ad(s) is/are not shown " + ctnAd + " on Panache Listing Page.");
		softAssert.assertAll();
	}

	@Test(description = "This test verifies top block articles")
	public void verifyTopBlockStories() {
		int topNews = 4;
		softAssert = new SoftAssert();
		List<String> topStoriesHeadLines = panacheMethods.getTopStoriesText();
		softAssert.assertTrue(topStoriesHeadLines.size() == topNews,
				"<br>- Top block is not having " + topNews + " articles");
		List<String> topBlockStoriesDup = VerificationUtil.isListUnique(topStoriesHeadLines);
		softAssert.assertTrue(topBlockStoriesDup.isEmpty(),
				"<br>- Top block section is having duplicate stories, repeating story(s)->" + topStoriesHeadLines);
		List<String> topStoriesHref = panacheMethods.getTopStoriesHref();
		topStoriesHref.forEach(href -> {
			int responseCode = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + href + ">Story </a> is throwing " + responseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "This test verifies slideshow section")
	public void verifySlideshowSection() {
		int slideShowCount = 6;
		softAssert = new SoftAssert();
		List<String> slideShowStories = panacheMethods.getSlideShowStoriesText();
		softAssert.assertTrue(slideShowStories.size() > slideShowCount, "<br>- Slide show is having less than "
				+ slideShowCount + " articles, actual count " + slideShowStories.size());
		List<String> slideShowDup = VerificationUtil.isListUnique(slideShowStories);
		softAssert.assertTrue(slideShowDup.isEmpty(),
				"<br>- Slide show section is having duplicate stories, repeating story(s)->" + slideShowDup);
		List<String> slideShowHref = panacheMethods.getSlideShowStoriesHref();
		slideShowHref.forEach(href -> {
			;
			int responseCode = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + href + ">Story </a> is throwing " + responseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "This test verifies big display stories")
	public void verifyBigStorySection() {
		int bigStoriesCount = 4;
		softAssert = new SoftAssert();
		List<String> bigStories = panacheMethods.getBigStoriesText();
		softAssert.assertTrue(bigStories.size() >= bigStoriesCount,
				"<br>- Big  stories are less than " + bigStoriesCount + " articles, actual count " + bigStories.size());
		List<String> bigStoriesDup = VerificationUtil.isListUnique(bigStories);
		softAssert.assertTrue(bigStoriesDup.isEmpty(),
				"<br>- Big Stories are duplicating, repeating story(s)->" + bigStoriesDup);
		List<String> bigStoriesHref = panacheMethods.getBigStoriesHref();
		bigStoriesHref.forEach(href -> {
			int responseCode = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + href + ">Story </a> is throwing " + responseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "This test verifies story list")
	public void verifyAllStories() {
		int allStoriesCount = 20;
		softAssert = new SoftAssert();
		List<String> allStories = panacheMethods.getAllStoriesText();
		softAssert.assertTrue(allStories.size() > allStoriesCount, "<br>- List articles are having less than "
				+ allStoriesCount + " in number, actual count " + allStories.size());
		List<String> allStoriesDup = VerificationUtil.isListUnique(allStories);
		softAssert.assertTrue(allStoriesDup.isEmpty(),
				"<br>- Stories are duplicating, repeating story(s)->" + allStoriesDup);
		List<String> allStoriesHref = panacheMethods.getAllStoriesHref();
		allStoriesHref.forEach(href -> {
			int responseCode = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(responseCode, 200,
					"<br>- <a href=" + href + ">Story </a> is throwing " + responseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "This test verifies between the lines section", enabled = false)
	public void verifyBetweenLinesSection() {
		softAssert = new SoftAssert();
		Assert.assertTrue(panacheMethods.getBTLSectionHeading().length() > 0,
				"<br>Between the lines section is not shown");
		List<String> allStories = panacheMethods.getBTLArticleHref();
		List<String> visibleStories = panacheMethods.getVisibleArticleList();
		int responseCode = HTTPResponse.checkResponseCode(panacheMethods.getBTLSectionHeading());
		softAssert.assertTrue(responseCode == 200, "<br>- <a href=" + panacheMethods.getBTLSectionHeading()
				+ ">Section heading for Between the lines </a> is throwing " + responseCode);
		allStories.forEach(href -> {
			int respCode = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(respCode, 200, "<br>- <a href=" + href + ">Story </a> is throwing " + respCode);
		});
		panacheMethods.clickNextPageBTL();
		List<String> afterClickVisibleStories = panacheMethods.getVisibleArticleList();
		softAssert.assertTrue(!visibleStories.equals(afterClickVisibleStories),
				"Stories are not changing on clicking the next slider");
		softAssert.assertAll();
	}

	@Test(description = "This test verifies date and time shown on header on panache page")
	public void verifyDateTime() {
		softAssert = new SoftAssert();
		String dateTimeN = headerPageMethods.getDateTimeTab();

		DateTime systemDate = new DateTime();

		DateTime date = WebBaseMethods.convertDateMethod(
				dateTimeN.replaceAll("\\|", "").replaceAll(" ", "").replaceAll(",", "").replaceAll("\\.", ":"));

		Assert.assertTrue(dateTimeN != null, "Date time is not shown on header on panache page");
		softAssert.assertTrue(systemDate.getDayOfYear() == date.getDayOfYear(),
				"The date shown on panache page is not the current date instead is showing " + dateTimeN);
		softAssert.assertTrue(
				(systemDate.getMinuteOfDay() - date.getMinuteOfDay() <= 20
						&& systemDate.getMinuteOfDay() - date.getMinuteOfDay() >= 0),
				"on panache  page the time shown " + dateTimeN
						+ " is having difference of more than 20 mins from current time"
						+ systemDate.toString("MMM dd, yyyy hh:mm a"));
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

	@Test(description = "This test verifies the top trending widget on the Panache page")
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
}
