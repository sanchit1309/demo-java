package wap.tests;

import java.io.IOException;
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
import wap.pagemethods.StoryPageMethods;
import wap.pagemethods.WapListingPageMethods;
import web.pagemethods.WebBaseMethods;

public class StoryPage extends BaseTest {

	private String wapUrl;
	private StoryPageMethods storyPageMethods;
	private WapListingPageMethods wapListingPageMethods;
	private HomePageMethods homePageMethods;
	private AdTechMethods adTechMethods;
	private SoftAssert softAssert;
	Map<String, String> TestData;
	private String storyUrl;
	private String primeURL;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl = BaseTest.baseUrl;
		launchBrowser(wapUrl);
		storyPageMethods = new StoryPageMethods(driver);
		wapListingPageMethods = new WapListingPageMethods(driver);
		homePageMethods = new HomePageMethods(driver);
		adTechMethods = new AdTechMethods(driver);
		TestData = ExcelUtil.getTestDataRow("TestDataSheet", "verifyLogin", 1);
		primeURL = Config.fetchConfigProperty("primeUrl");

	}

	public void openArticle() {
		String pageUrl = BaseTest.baseUrl + Config.fetchConfigProperty("PoliticsNationUrl");
		driver.get(pageUrl);
		boolean retry = false;
		int counter = 0;
		do {
			try {
				wapListingPageMethods.clickFirstHeadline();
				retry = false;
			} catch (Exception e) {
				retry = true;
				counter++;
			}
		} while (retry && counter < 3);
		storyUrl = driver.getCurrentUrl();
		WaitUtil.sleep(2000);

	}

	@Test(description = "Verifies article sharing by Facebook", priority = 0, enabled = true)
	public void verifyArticleShowSharingWithFacebook() {
		openArticle();
		softAssert = new SoftAssert();
		String articleHeading = storyPageMethods.getArticleHeading();
		System.out.println(articleHeading);
		Assert.assertTrue(storyPageMethods.clickHeaderSharingButton(), "User is not able to click the sharing icon");
		WaitUtil.sleep(3000);
		Assert.assertTrue(storyPageMethods.verifyVisibilityOfFbShareIcon(), "Fb share icon not available");
		Assert.assertTrue(storyPageMethods.verifyFbLogin(TestData.get("Email"), TestData.get("Password")),
				"Facebook Login failed");
		String fbArticleHeading = storyPageMethods.getSharedHeading();
		softAssert.assertTrue(storyPageMethods.verifySharingMessage(articleHeading, fbArticleHeading),
				"Facebook shared article heading should be " + articleHeading + ", instead found " + fbArticleHeading);
		softAssert.assertAll();
	}

	@Test(description = "Verifies article sharing by Twitter", priority = 2, enabled = true)
	public void verifyArticleShowSharingWithTwitter() {
		driver.get(storyUrl);
		String articleHeading = storyPageMethods.getArticleHeading();
		Assert.assertTrue(storyPageMethods.clickHeaderSharingButton(), "User is not able to click the sharing icon");
		WaitUtil.sleep(3000);
		Assert.assertTrue(storyPageMethods.verifyVisibilityOfTwitterShareIcon(), "Twitter share icon not available");
		storyPageMethods.openTwitterShare();
		String twitterArticleHeading = storyPageMethods.getTwitterArticeHeadline(TestData.get("Email"),
				TestData.get("Password"));
		Assert.assertTrue(storyPageMethods.verifySharingMessage(articleHeading, twitterArticleHeading),
				"Twitter shared article heading should be " + articleHeading + ", instead found "
						+ twitterArticleHeading);
	}

	@Test(description = "Verify Story Page sub section", dataProvider = "storyPageSubSection", priority = 3)
	public void verifyStoryPageSubSection(String subSection) {
		openArticle();
		softAssert = new SoftAssert();
		int articleCount = 2;

		String sectionName = subSection;

		List<String> newsStories = VerificationUtil
				.getLinkTextList(storyPageMethods.getSectionNewsHeadlines(sectionName));
		int count = newsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under " + subSection
				+ " sections should be more than " + articleCount + " in number, instead found " + count);
		List<String> NewsDup = VerificationUtil.isListUnique(newsStories);
		softAssert.assertTrue(NewsDup.isEmpty(),
				"<br>- " + subSection + " Section has duplicate stories, repeating story(s)->" + NewsDup);
		storyPageMethods.getSectionNewsHref(sectionName).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top " + subSection + " link <a href=" + keyword
					+ ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = " This test verifies the Google and colombia ads on article show page", enabled = false)
	public void verifyAds() {
		SoftAssert softAssert = new SoftAssert();
		List<String> articleUrls = homePageMethods.getFirstFewHeadlines();
		if (!articleUrls.isEmpty()) {
			for (String url : articleUrls) {
				if (url.contains("articleshow")) {
					WebBaseMethods.navigateTimeOutHandle(driver, url, 2);
					WaitUtil.sleep(2000);
					Map<String, String> expectedIdsMap = Config.getAllKeys("wap_adIdLocation");
					Assert.assertTrue(storyPageMethods.clickReadMoreButton(),
							"Unable to click Read More button on article detail page for article <a href=" + url
									+ "> url" + url + "</a>");
					softAssert.assertTrue(adTechMethods.getDisplayedAdIds().size() > 0,
							"No google ads shown for article url <a href='" + url + "'>" + url + "</a>");
					if (adTechMethods.getDisplayedAdIds().size() > 0) {
						expectedIdsMap.forEach((K, V) -> {
							if (!K.contains("HP"))
								softAssert.assertTrue(adTechMethods.matchIdsWithRegex(K),
										"Following ad(s) is/are not shown " + expectedIdsMap.get(K)
												+ " for article url <a href='" + url + "'>" + url + "</a>");
						});
					}
				}
			}
		} else {
			Assert.assertTrue(false, "No headlines fetched from Home Page.");
		}
		softAssert.assertAll();
	}

	@Test(description = "Verifies article sharing by LinkedIn", priority = 1, enabled = false)
	public void verifyArticleShowSharingWithLinkedIn() {
		driver.get(storyUrl);
		// openArticle();
		softAssert = new SoftAssert();
		String articleHeading = storyPageMethods.getArticleHeading();
		Assert.assertTrue(storyPageMethods.clickHeaderSharingButton(), "User is not able to click the sharing icon");
		WaitUtil.sleep(3000);
		Assert.assertTrue(storyPageMethods.verifyVisibilityOfLinkedInShareIcon(), "LinkedIn share icon not available");

		Assert.assertTrue(storyPageMethods.verifyLinkedInLogin(TestData.get("Email"), TestData.get("LinkedInPassword")),
				"LinkedIn Login failed");
		String linkedInArticleHeading = storyPageMethods.getLinkedInArticleHeadline();
		softAssert.assertTrue(storyPageMethods.verifySharingMessage(articleHeading, linkedInArticleHeading),
				"Linkdin shared article heading should be " + articleHeading + ", instead found "
						+ linkedInArticleHeading);
		softAssert.assertAll();
	}

	@Test(description = "Verifies article sharing by Google+ ", priority = 3, enabled = false)
	public void verifyArticleShowSharingWithGoogle() {
		driver.get(storyUrl);
		softAssert = new SoftAssert();
		String articleHeading = storyPageMethods.getArticleHeading();
		Assert.assertTrue(storyPageMethods.verifyVisibilityOfGPlusShareIcon(), "Google+ share icon not available");
		Assert.assertTrue(storyPageMethods.verifyGPlusLogin(TestData.get("Email"), TestData.get("Password")),
				"Google+  Login failed");
		String gPlusArticleHeading = storyPageMethods.getGoogleArticeHeadline();
		softAssert.assertTrue(storyPageMethods.verifySharingMessage(articleHeading, gPlusArticleHeading),
				"Google+ shared article heading should be " + articleHeading + ", instead found "
						+ gPlusArticleHeading);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies prime article listed on article show page.", enabled = false)
	public void verifyPrimeArticle() {
		softAssert = new SoftAssert();
		openArticle();
		WebBaseMethods.navigateTimeOutHandle(driver, storyUrl, 5);
		Assert.assertTrue(storyPageMethods.isPrimeArticleDisplayed(),
				"Prime article not present on article detail page");
		Map<String, String> url = storyPageMethods.getPrimeArticleLink();
		if (!url.isEmpty()) {
			url.forEach((K, V) -> {
				int res = HTTPResponse.checkResponseCode(K);
				softAssert.assertEquals(HTTPResponse.checkResponseCode(K), 200,
						"Prime story link for <a href = " + K + "> " + V + "is throwing " + res);
				softAssert.assertTrue(K.contains(primeURL),
						"Link under prime article on Story page is not of prime instead " + K);
			});
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies more from the ET section on article show Page.", enabled = false)
	public void verifyMoreFromETSection() {
		softAssert = new SoftAssert();
		openArticle();
		WebBaseMethods.navigateTimeOutHandle(driver, storyUrl, 2);
		Assert.assertTrue(storyPageMethods.isMoreFromETSectionDisplayed(),
				"More from ET section not present on article detail page");
		int count = storyPageMethods.getMoreFromArticleLink().size();
		softAssert.assertEquals(count, 2,
				"Less than 2 articles found under More from ET Section expected 2 found " + count);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies Promoted Stories section on article show Page.", enabled = false)
	public void verifyPromotedStories() {
		softAssert = new SoftAssert();
		openArticle();
		WebBaseMethods.navigateTimeOutHandle(driver, storyUrl, 5);
		Assert.assertTrue(storyPageMethods.isPromotedSectionDisplayed(),
				"Promoted Stories section not present on article detail page");
		int count = storyPageMethods.getPromotedArticleLink().size();
		softAssert.assertEquals(count, 2,
				"Less than 2 articles found under Promoted Stories Section expected 2 found " + count);
		softAssert.assertAll();
	}

	@DataProvider
	public String[] storyPageSubSection() {

		String[] storyPageSections = { "Related News", "Most Read News" };

		return storyPageSections;
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
