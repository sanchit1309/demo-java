package wap.tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
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
import wap.pagemethods.Articleshow_NewPageMethods;
import wap.pagemethods.HomePageMethods;
import wap.pagemethods.LoginPageMethods;
import wap.pagemethods.StoryPageMethods;
import wap.pagemethods.WapListingPageMethods;

public class Articleshow_New extends BaseTest {

	private String wapUrl;
	private StoryPageMethods storyPageMethods;
	private WapListingPageMethods wapListingPageMethods;
	private HomePageMethods homePageMethods;
	private LoginPageMethods loginPageMethods;

	private Articleshow_NewPageMethods articleshow_NewPageMethods;
	private SoftAssert softAssert;
	Map<String, String> TestData;
	private String storyUrl;
	private String primeURL;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl = BaseTest.baseUrl;
		launchBrowser("https://etpwapre.economictimes.com/");
		storyPageMethods = new StoryPageMethods(driver);
		wapListingPageMethods = new WapListingPageMethods(driver);
		loginPageMethods = new LoginPageMethods(driver);

		articleshow_NewPageMethods = new Articleshow_NewPageMethods(driver);
		homePageMethods = new HomePageMethods(driver);
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
				String url = driver.getCurrentUrl().replace("m.economictimes.com", "etpwapre.economictimes.com");
				driver.get(url);
				homePageMethods.jqueryInjForReactPages();
				WaitUtil.waitForLoad(driver);
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
		String articleHeading = articleshow_NewPageMethods.getArticleHeading();
		System.out.println(articleHeading);
		Assert.assertTrue(articleshow_NewPageMethods.clickHeaderSharingButton(),
				"User is not able to click the sharing icon");
		WaitUtil.sleep(3000);
		Assert.assertTrue(articleshow_NewPageMethods.verifyFbLogin(TestData.get("Email"), TestData.get("Password")),
				"Facebook Login failed");
		String fbArticleHeading = articleshow_NewPageMethods.getFbSharedHeading();
		softAssert.assertTrue(articleshow_NewPageMethods.verifySharingMessage(articleHeading, fbArticleHeading),
				"Facebook shared article heading should be " + articleHeading + ", instead found " + fbArticleHeading);
		softAssert.assertAll();
	}

	@Test(description = "Verifies article sharing by Twitter", priority = 2, enabled = true)
	public void verifyArticleShowSharingWithTwitter() {
		// driver.get(storyUrl);
		openArticle();

		String articleHeading = articleshow_NewPageMethods.getArticleHeading();
		articleshow_NewPageMethods.openTwitterShare();
		String twitterArticleHeading = storyPageMethods.getTwitterArticeHeadline(TestData.get("Email"),
				TestData.get("Password"));
		Assert.assertTrue(articleshow_NewPageMethods.verifySharingMessage(articleHeading, twitterArticleHeading),
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
				.getLinkTextList(articleshow_NewPageMethods.getSectionNewsHeadlines(sectionName));
		int count = newsStories.size();
		softAssert.assertTrue(count >= articleCount, "<br>- Headlines under " + subSection
				+ " sections should be more than " + articleCount + " in number, instead found " + count);
		List<String> NewsDup = VerificationUtil.isListUnique(newsStories);
		softAssert.assertTrue(NewsDup.isEmpty(),
				"<br>- " + subSection + " Section has duplicate stories, repeating story(s)->" + NewsDup);
		articleshow_NewPageMethods.getSectionNewsHref(sectionName).forEach(keyword -> {
			int topLinksResponseCode = HTTPResponse.checkResponseCode(keyword);
			softAssert.assertEquals(topLinksResponseCode, 200, "<br>- Top " + subSection + " link <a href=" + keyword
					+ ">link</a> is throwing " + topLinksResponseCode);
		});
		softAssert.assertAll();
	}

	@Test(description = "this test verifies the font size of the articleshow")
	public void checkTheArticleShowFontSize() {
		openArticle();
		softAssert = new SoftAssert();
		softAssert.assertTrue(articleshow_NewPageMethods.checkTheFontSize("small"),
				"On clicking the small font size the size is not changed");
		WaitUtil.sleep(3000);
		softAssert.assertTrue(articleshow_NewPageMethods.checkTheFontSize("normal"),
				"On clicking the normal font size the size is not changed");
		WaitUtil.sleep(3000);
		softAssert.assertTrue(articleshow_NewPageMethods.checkTheFontSize("large"),
				"On clicking the large font size the size is not changed");
		WaitUtil.sleep(3000);
		softAssert.assertAll();

	}

	@Test(description = "this test verifies the comment functionality on articleshow", enabled = false)
	public void verifyCommentFunctionality() {
		softAssert = new SoftAssert();
		homePageMethods.jqueryInjForReactPages();
		Assert.assertTrue(homePageMethods.clickHmaberMenuIcon(), "Unable to click menu icon");
		Assert.assertTrue(loginPageMethods.clickFooterMenuSignIn(), "Unable to click Sign In button from menu");
		Assert.assertTrue(loginPageMethods.clickContinueEmailMobile(),
				"SSO:Unable to click continue Email/Mobile button on login screen");
		Assert.assertTrue(loginPageMethods.findEmailSetValue(TestData.get("Email")), "SSO:Unable to find Email Field");
		Assert.assertTrue(loginPageMethods.clickContinueButton(),
				"Unable to click continue button on the email screen");
		Assert.assertTrue(loginPageMethods.findPasswordSetValue(TestData.get("Password")),
				"SSO:Unable to find Password Field");
		Assert.assertTrue(loginPageMethods.clickContinueButtonFinal(),
				"Unable to click continue button on the password screen");
		WaitUtil.waitForLoad(driver);
		WaitUtil.sleep(1200);
		openArticle();
		WaitUtil.sleep(1200);

		Assert.assertTrue(articleshow_NewPageMethods.clickArticleCommentBtn(),
				"User is not able to click on the article comment button");
		WaitUtil.sleep(1200);
	}

	@Test(description = "This test verifies the articleshow body")
	public void verifyContentsOfArticleshow() {
		openArticle();
		softAssert = new SoftAssert();
		Map<String, String> allElements = articleshow_NewPageMethods.getArticleAllElements();

		allElements.forEach((key, value) -> {
			String text = allElements.get(key).trim();

			softAssert.assertTrue(text.length() > 10 && !text.contains("is not found"),
					"<br>-The " + key + " text  " + text + " on first article");
			System.out.println("checked " + key);

		});

		softAssert.assertAll();

	}

	@DataProvider
	public String[] storyPageSubSection() {

		String[] storyPageSections = { "Related News", "Most Read News" };

		return storyPageSections;
	}

}
