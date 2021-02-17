package com.web.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.ExcelUtil;
import common.utilities.FileUtility;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import web.pagemethods.AdTechMethods;
import web.pagemethods.ArticleMethods;
import web.pagemethods.Articleshow_newPageMethods;
import web.pagemethods.ETSharedMethods;
import web.pagemethods.HeaderPageMethods;
import web.pagemethods.HomePageMethods;
import web.pagemethods.LoginPageMethods;
import web.pagemethods.NewHomePageMethods;
import web.pagemethods.WebBaseMethods;

public class Articleshow_New extends BaseTest {
	private String baseUrl;
	HomePageMethods homePageMethods;
	LoginPageMethods loginPageMethods;
	NewHomePageMethods newHomePageMethods;
	WebBaseMethods webBaseMethods;
	Articleshow_newPageMethods articleMethods_New;
	ArticleMethods articleMethods;
	HeaderPageMethods headerPageMethods;
	SoftAssert softAssert;
	Map<String, String> TestData = new HashMap<>();
	String email;
	String password;
	int counter;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		launchBrowser(baseUrl);
		homePageMethods = new HomePageMethods(driver);
		articleMethods_New = new Articleshow_newPageMethods(driver);
		articleMethods = new ArticleMethods(driver);
		loginPageMethods = new LoginPageMethods(driver);
		headerPageMethods = new HeaderPageMethods(driver);
		TestData = ExcelUtil.getTestDataRow("TestDataSheet", "VerifyLogin", 1);
		email = TestData.get("Email");
		password = TestData.get("Password");
		newHomePageMethods = new NewHomePageMethods(driver);
	}
	
	@AfterClass
	public void afterClass() throws IOException {
		driver.quit();
	}

	@BeforeMethod
	public void openETHomepage() {
		driver.get(baseUrl);
		WaitUtil.waitForLoad(driver);
		if (driver.getCurrentUrl().contains("interstitial")) {
			ETSharedMethods.clickETLinkInterstitialPage();
		}
	}

	@Test(description = "this test verifies all the elements of the articleshow", priority = 1)
	public void verifyElementsOfArticleshow() {
		softAssert = new SoftAssert();
		WaitUtil.waitForLoad(driver);
		boolean flag = newHomePageMethods.clickAndSwitchTOFirstETFreeArticle();
		Assert.assertTrue(flag, "The ET article(free) is not opened");
		WaitUtil.sleep(5000);

		Map<String, String> allElements = articleMethods_New.getArticleAllElements();

		allElements.forEach((key, value) -> {
			String text = allElements.get(key).trim();

			softAssert.assertTrue(text.length() > 10 && !text.contains("is not found"),
					"<br>-The " + key + " text  " + text + " on first article");
			System.out.println("checked " + key);

		});

		List<String> alsoReadlinks = articleMethods_New.getArticleAlsoReadLinksHref();
		alsoReadlinks.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in also read is throwing " + response);
		});
		List<String> readMoreLinks = articleMethods_New.getArticleReadMoreLinksHref();
		readMoreLinks.forEach(href -> {

			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200,
					"<br>- <a href=" + href + "> Story</a> in read more is throwing " + response);
		});
		softAssert.assertAll();
	}

	@Test(description = "this test verifies the font size of the article text", priority = 2)
	public void checkTheFontSize() {
		softAssert = new SoftAssert();
		WaitUtil.waitForLoad(driver);
		boolean flag = newHomePageMethods.clickAndSwitchTOFirstETFreeArticle();
		Assert.assertTrue(flag, "The ET article(free) is not opened");

		WaitUtil.sleep(5000);

		softAssert.assertTrue(articleMethods_New.checkSmallTextSize(),
				"on clicking the small text size the text donot changes to small size");
		softAssert.assertTrue(articleMethods_New.checkMediumTextSize(),
				"on clicking the medium text size the text donot changes to medium size");

		softAssert.assertTrue(articleMethods_New.checkLargeTextSize(),
				"on clicking the large text size the text donot changes to large size");
		softAssert.assertAll();

	}

	@Test(description = "This test verifies twitter sharing of articleshow", priority = 3)
	public void verifyAticleShowSharingWithTwitter() throws InterruptedException {
		boolean flag = newHomePageMethods.clickAndSwitchTOFirstETFreeArticle();
		Assert.assertTrue(flag, "The ET article(free) is not opened");

		WaitUtil.sleep(2000);
		String title = articleMethods_New.getArticleTitle();
		System.out.println(title);
		Assert.assertTrue(articleMethods_New.navigateToTwitterSharing(), "Unable to navigate to twitter sharing");
		WaitUtil.sleep(2000);
		Assert.assertTrue(loginPageMethods.twitterLoginActivity(email, password),
				"user is not able to login in the twitter");
		WaitUtil.sleep(2000);
		String sharedTitle = articleMethods_New.getTwitterSharedLinkTitle().replace("...", "");
		System.out.println(sharedTitle);
		boolean flagTitle = title.contains(sharedTitle);
		Assert.assertTrue(flagTitle, "Headline is not matching on Twitter, <b>expected</b> " + title + " <b>found</b> "
				+ sharedTitle + " .");
	}

	@Test(description = "This test verifies facebook sharing of articleshow", priority = 4)
	public void verifyArticleShowSharingWithFacebook() throws InterruptedException {
		boolean flag = newHomePageMethods.clickAndSwitchTOFirstETFreeArticle();
		Assert.assertTrue(flag, "The ET article(free) is not opened");

		WaitUtil.sleep(2000);

		String title = articleMethods_New.getArticleTitle();
		Assert.assertTrue(articleMethods_New.navigateToFacebookSharing(), "Unable to click fb sharing btn");
		WebBaseMethods.switchToChildWindow(5);
		boolean loginFlag = loginPageMethods.facebookLogin(email, password);
		Assert.assertTrue(loginFlag, "Unable to login through facebook");
		String sharedTitle = articleMethods.getFacebookSharedLinkTitle();
		WebBaseMethods.switchToParentClosingChilds();
		Assert.assertTrue(title.trim().contains(sharedTitle.replace("- The Economic Times", "").trim()),
				"Headline is not matching on Facebook, <b>expected</b> " + title + " <b>found</b> " + sharedTitle
						+ " .");
	}

	@Test(description = "this test verifies the comment functionality on articleshow", priority = 5)
	public void verifyCommentFunctionality() {
		softAssert = new SoftAssert();

		if (headerPageMethods.checkUserLoggedInState()) {
			boolean flag = headerPageMethods.logOutIfUserIsLoggedIn();
			Assert.assertTrue(flag, "Unable to logout the user as some user was already logged in");

		}

		Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "Unable to find sign in button");

		Assert.assertTrue(loginPageMethods.registeredUserLogin(TestData.get("Email"), TestData.get("Password")),
				"Unable to fill entries in the login window");
		WaitUtil.sleep(3000);
		Assert.assertTrue(headerPageMethods.checkUserLoggedInState(), "Logged in user image not shown");
		WaitUtil.waitForLoad(driver);
		boolean flag = newHomePageMethods.clickAndSwitchTOFirstETFreeArticle();
		Assert.assertTrue(flag, "The ET article(free) is not opened");

		WaitUtil.sleep(8000);
		WebBaseMethods.scrollMultipleTimes(2, "Down", 150);

		softAssert.assertTrue(articleMethods_New.clickCommentIcon(), "User is not able to click on the comment icon");
		softAssert.assertTrue(articleMethods_New.verifyCommentIsPosted(),
				"User is not able to successfully post the comment");

		WaitUtil.sleep(4000);

		softAssert.assertAll();
	}

	@AfterMethod
	public void afterMethod() {
		WebBaseMethods.closeAllExceptParentWindow();
	}

}
