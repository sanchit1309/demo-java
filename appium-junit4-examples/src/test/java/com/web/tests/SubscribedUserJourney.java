package com.web.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import prime.web.pagemethods.ArticleShowPageMethods;
import web.pagemethods.Articleshow_newPageMethods;
import web.pagemethods.ETPrimeArticleshowMethods;
import web.pagemethods.ETPrimeHomePageMethods;
import web.pagemethods.HeaderPageMethods;
import web.pagemethods.HomePageMethods;
import web.pagemethods.LoginPageMethods;
import web.pagemethods.NewHomePageMethods;
import web.pagemethods.WebBaseMethods;

public class SubscribedUserJourney extends BaseTest {
	private String baseUrl;
	NewHomePageMethods newHomePageMethods;
	LoginPageMethods loginPageMethods;
	HeaderPageMethods headerPageMethods;
	Articleshow_newPageMethods articleshow_newPageMethods;
	ETPrimeHomePageMethods etPrimeHomePageMethods;
	ArticleShowPageMethods articleShowPageMethods;
	ETPrimeArticleshowMethods etPrimeArticleshowMethods;
	Map<String, String> TestData = new HashMap<String, String>();

	SoftAssert softAssert;

	@BeforeClass
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		launchBrowser(baseUrl);
		newHomePageMethods = new NewHomePageMethods(driver);
		loginPageMethods = new LoginPageMethods(driver);
		headerPageMethods = new HeaderPageMethods(driver);
		articleshow_newPageMethods = new Articleshow_newPageMethods(driver);
		etPrimeHomePageMethods = new ETPrimeHomePageMethods(driver);
		etPrimeArticleshowMethods = new ETPrimeArticleshowMethods(driver);
		articleShowPageMethods = new ArticleShowPageMethods(driver);
		TestData = ExcelUtil.getTestDataRow("TestDataSheet", "VerifyETPremium", 1);
	}

	@Test(description = "This test verify the login functionality with Active User", priority = 0)
	public void verifyUserLogin() {
		Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "Unable to find sign in button");

		Assert.assertTrue(loginPageMethods.registeredUserLogin(TestData.get("Email"), TestData.get("Password")),
				"Unable to fill entries in the login window");
		WaitUtil.sleep(5000);
		Assert.assertTrue(headerPageMethods.checkUserLoggedInState(), "The user is not able to login");

	}

	@Test(description = "This test verify that ET Homepage is showing Top news section after successful login ", dependsOnMethods = {
			"verifyUserLogin" }, priority = 1)
	public void verifyETHomepageAfterLogin() {
		Assert.assertTrue(newHomePageMethods.getAllTopNewsHref().size() > 10,
				"The top news are not shown on the ET homepage");
		Assert.assertTrue(newHomePageMethods.getEtPrimeWidgetStoriesHref().size() > 4,
				"The ET prime widget stories are not shown on the Et homepage");

	}

	@Test(description = "This test verifies the ET Free articleshow are appearing on HomePage", dependsOnMethods = {
			"verifyUserLogin" }, priority = 2)
	public void verifyEtFreeArticleshow() {
		softAssert = new SoftAssert();
		Assert.assertTrue(newHomePageMethods.clickFirstETFreeArticle(),
				"User is not able to click the first free ET article");
		WaitUtil.sleep(5000);
		WebBaseMethods.switchToChildWindow(2000);
		List<String> failedElement = articleshow_newPageMethods.verifyPresenceOfAllElements();
		Assert.assertTrue(failedElement.size() == 0, "Few element on the articleshow are not shown " + failedElement
				+ "on articleshow url:" + driver.getCurrentUrl());
		WebBaseMethods.switchToParentClosingChilds();
		WaitUtil.sleep(5000);
	}

	@Test(description = "This test verifies the ET premium articleshow on the et homepage ", dependsOnMethods = {
			"verifyUserLogin" }, priority = 3)
	public void verifyETPremiumArticleshow() {
		softAssert = new SoftAssert();

		Assert.assertTrue(newHomePageMethods.clickFirstETPremiumArticle(),
				"The user is not able to click on the first premium article from ET homepage");

		Assert.assertFalse(etPrimeArticleshowMethods.isPrimePaywalBlockerShown(),
				"The paid user is shown a paywall on the premium articleshow after successful login");

		/* Validating Synopsis is present */
		softAssert.assertTrue(articleShowPageMethods.getSynopsisText().length() > 20,
				"<br> Synopsis is not appearing for the story " + driver.getCurrentUrl());

		softAssert.assertTrue(articleShowPageMethods.getArticleText().length() > 1000,
				"<br> Article length is appearing less than 1000 characters for story " + driver.getCurrentUrl());

		/* Validating Minute Read is appearing on article */
		softAssert.assertTrue(articleShowPageMethods.getMinuteReadText().contains("mins read"),
				"<br> Minute Read is not appering on story " + driver.getCurrentUrl());

		/*
		 * Validating Sharing Article Text and number of sharing options are
		 * appearing on article
		 */
		softAssert.assertEquals(articleShowPageMethods.getShareArticleText(), "Share This Article",
				"<br> Share The Article text is appearing incorrect as "
						+ articleShowPageMethods.getShareArticleText());
		softAssert.assertEquals(articleShowPageMethods.getShareArticleListCount(), 4,
				"<br> Share The Article count  is appearing incorrect as "
						+ articleShowPageMethods.getShareArticleListCount());

		/*
		 * Validating Gift icon, Font Size, Save and Comment option are
		 * appearing on article
		 */
		List<String> giftFontSaveCommentTexts = new ArrayList<String>(
				Arrays.asList("GIFT ARTICLE", "FONT SIZE", "SAVE", "COMMENT"));
		softAssert.assertTrue(
				VerificationUtil.areListsEqual(giftFontSaveCommentTexts,
						articleShowPageMethods.getGiftCommentFontSaveTexts()),
				"<br> Gift, Font, Save and Comment Texts are mismatching <br>");

		/* Validating Add Comment button is displaying */
		softAssert.assertTrue(articleShowPageMethods.isAddCommentButtonDisplayed(),
				"<br> Add Comment button is not displaying <br>" + driver.getCurrentUrl());

		/* Validating Popular With Readers widget is displaying */
		softAssert.assertTrue(articleShowPageMethods.isPopularWithReadersWidgetDisplayed(),
				"<br> Popular With Readers widget is not displaying <br>" + driver.getCurrentUrl());
		WebBaseMethods.switchToParentClosingChilds();
		WaitUtil.sleep(5000);
		softAssert.assertAll();

	}

	@Test(description = "This test verify that user is able to switch to ET prime Homepage from Header", dependsOnMethods = {
			"verifyUserLogin" }, priority = 4)
	public void verifyETPrimeHomepage() {
		softAssert = new SoftAssert();
		WaitUtil.sleep(3000);
		Assert.assertTrue(newHomePageMethods.clickETPrimeNavBarLink(),
				"user not able to click on the et prime link on nav bar");

		Assert.assertTrue(etPrimeHomePageMethods.topStoriesListHref().size() > 0,
				"The top block stories are not shown on the ET prime homepage");

	}

	@Test(description = "This test verify that on any Prime Article, Paywall should not appear for Active User", dependsOnMethods = {
			"verifyUserLogin" }, priority = 5)
	public void verifyETPrimeArticleshow() {
		WaitUtil.sleep(4000);
		softAssert = new SoftAssert();

		Assert.assertTrue(newHomePageMethods.clickETPrimeNavBarLink(),
				"user not able to click on the et prime link on nav bar");
		Assert.assertTrue(etPrimeHomePageMethods.clickFirstPrimeArticleFromPrimeHomepage(),
				"The user is not able to click on the first prime article from prime homepage");

		Assert.assertFalse(etPrimeArticleshowMethods.isPrimePaywalBlockerShown(),
				"The paid user is shown a paywall on the articleshow after successful login");

		/* Validating Synopsis is present */
		softAssert.assertTrue(articleShowPageMethods.getSynopsisText().length() > 20,
				"<br> Synopsis is not appearing for the story " + driver.getCurrentUrl());

		softAssert.assertTrue(articleShowPageMethods.getArticleText().length() > 1000,
				"<br> Article length is appearing less than 1000 characters for story " + driver.getCurrentUrl());

		/* Validating Minute Read is appearing on article */
		softAssert.assertTrue(articleShowPageMethods.getMinuteReadText().contains("mins read"),
				"<br> Minute Read is not appering on story " + driver.getCurrentUrl());

		/*
		 * Validating Sharing Article Text and number of sharing options are
		 * appearing on article
		 */
		softAssert.assertEquals(articleShowPageMethods.getShareArticleText(), "Share This Article",
				"<br> Share The Article text is appearing incorrect as "
						+ articleShowPageMethods.getShareArticleText());
		softAssert.assertEquals(articleShowPageMethods.getShareArticleListCount(), 4,
				"<br> Share The Article count  is appearing incorrect as "
						+ articleShowPageMethods.getShareArticleListCount());

		/*
		 * Validating Gift icon, Font Size, Save and Comment option are
		 * appearing on article
		 */
		List<String> giftFontSaveCommentTexts = new ArrayList<String>(
				Arrays.asList("GIFT ARTICLE", "FONT SIZE", "SAVE", "COMMENT"));
		softAssert.assertTrue(
				VerificationUtil.areListsEqual(giftFontSaveCommentTexts,
						articleShowPageMethods.getGiftCommentFontSaveTexts()),
				"<br> Gift, Font, Save and Comment Texts are mismatching <br>");

		/* Validating Add Comment button is displaying */
		softAssert.assertTrue(articleShowPageMethods.isAddCommentButtonDisplayed(),
				"<br> Add Comment button is not displaying <br>" + driver.getCurrentUrl());

		/* Validating Popular With Readers widget is displaying */
		softAssert.assertTrue(articleShowPageMethods.isPopularWithReadersWidgetDisplayed(),
				"<br> Popular With Readers widget is not displaying <br>" + driver.getCurrentUrl());

		softAssert.assertAll();
	}

	@AfterMethod
	public void switchToParentWindow() {

		WebBaseMethods.switchToParentClosingChilds();
	}
}
