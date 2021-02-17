package com.web.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.WaitUtil;
import web.pagemethods.Articleshow_newPageMethods;
import web.pagemethods.HeaderPageMethods;
import web.pagemethods.LoginPageMethods;
import web.pagemethods.NewHomePageMethods;
import web.pagemethods.SaveAndHistoryPageMethods;
import web.pagemethods.WebBaseMethods;

public class SaveAndHistory extends BaseTest {
	private String baseUrl;
	NewHomePageMethods newHomePageMethods;
	LoginPageMethods loginPageMethods;
	HeaderPageMethods headerPageMethods;
	Articleshow_newPageMethods articleshow_newPageMethods;
	SaveAndHistoryPageMethods saveAndHistoryPageMethods;
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
		saveAndHistoryPageMethods = new SaveAndHistoryPageMethods(driver);
		TestData = ExcelUtil.getTestDataRow("TestDataSheet", "VerifyLogin", 1);
		verifyUserLogin();
	}
	
	@AfterClass
	public void afterClass() throws IOException {
		driver.quit();
	}

	@Test(description = "This test verifies the save and history feature on ET free article")
	public void verifyFreeArticleSaveAndHistory() {
		softAssert = new SoftAssert();
		Assert.assertTrue(newHomePageMethods.clickAndSwitchTOFirstETFreeArticle(),
				"user is not able to click on the First et free article");

		String articleTitle = articleshow_newPageMethods.getArticleTitle();
		System.out.println(articleTitle);

		Assert.assertTrue(articleshow_newPageMethods.clickBookmarkIcon(),
				"user is not able to click the bookmark icon");
		Assert.assertTrue(articleshow_newPageMethods.clickSavedStories(),
				"User is not able to click the saved stories tab");
		softAssert.assertTrue(saveAndHistoryPageMethods.isSavedArticlePresentUnderTheSave(articleTitle),
				"The saved article is not shown on the save page");
		softAssert.assertTrue(saveAndHistoryPageMethods.isVisitedArticlePresentInTheHistory(articleTitle),
				"The article visited is not shown under the history tab");

		WebBaseMethods.navigateBackTimeOutHandle();
		WaitUtil.waitForLoad(driver);
		WebBaseMethods.refreshTimeOutHandle();

		Assert.assertTrue(articleshow_newPageMethods.clickBookmarkIconToUnsave(),
				"User not able to click on the bookmark icon to unsave the article");
		Assert.assertTrue(articleshow_newPageMethods.clickSavedStories(),
				"User is not able to click the saved stories tab");
		softAssert.assertTrue(saveAndHistoryPageMethods.isSavedRemovedFromTheSave(articleTitle),
				"The saved article is still shown on the save page even when user has clicked to unsave the article");

		softAssert.assertAll();

	}

	public void verifyUserLogin() {
		if (headerPageMethods.checkUserLoggedInState()) {
			boolean flag = headerPageMethods.logOutIfUserIsLoggedIn();
			Assert.assertTrue(flag, "Unable to logout the user as some user was already logged in");

		}
		Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "Unable to find sign in button");

		Assert.assertTrue(loginPageMethods.registeredUserLogin(TestData.get("Email"), TestData.get("Password")),
				"Unable to fill entries in the login window");
		WaitUtil.sleep(6000);
		Assert.assertTrue(headerPageMethods.checkUserLoggedInState(), "The user is not able to login");

	}

}
