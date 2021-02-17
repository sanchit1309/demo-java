package com.web.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.WaitUtil;
import web.pagemethods.ArticleMethods;
import web.pagemethods.BookmarksPageMethods;
import web.pagemethods.HeaderPageMethods;
import web.pagemethods.HomePageMethods;
import web.pagemethods.LoginPageMethods;
import web.pagemethods.WebBaseMethods;

public class Bookmarks extends BaseTest{
	private LoginPageMethods loginPageMethods;
	private HeaderPageMethods headerPageMethods;
	private ArticleMethods articleMethods;
	private BookmarksPageMethods bookmarksPageMethods;
	private String baseUrl;
	Map<String, String> TestData = new HashMap<String, String>();
	private HomePageMethods homePageMethods;
	private SoftAssert softAssert;
	
	@BeforeClass
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		launchBrowser(baseUrl);
		loginPageMethods = new LoginPageMethods(driver);
		headerPageMethods = new HeaderPageMethods(driver);
		homePageMethods = new HomePageMethods(driver);
		articleMethods = new ArticleMethods(driver);
		bookmarksPageMethods = new BookmarksPageMethods(driver);
		TestData = ExcelUtil.getTestDataRow("TestDataSheet", "VerifyLogin", 1);

	}
	
	
	
	@Test(description="this test verifies the save option on articleshow")
	public void verifySaveOption() {
		softAssert = new SoftAssert();
		WebBaseMethods.clearBrowserSessionCookie(driver);
		WebBaseMethods.refreshTimeOutHandle();
		String email = TestData.get("Email");
		String password = TestData.get("Password");
		Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "Unable to find sign in button");
		Assert.assertTrue(loginPageMethods.registeredUserLogin(email, password),
				"Unable to fill entries in the login window");
		Assert.assertTrue(headerPageMethods.getLoggedInUserImage(), "Logged in user image not shown");
		homePageMethods.clickFirstNews();
		WaitUtil.sleep(4000);
		String articleUrl = driver.getCurrentUrl();
		System.out.println("this is current url "+articleUrl);
		WaitUtil.sleep(10000);
		WebBaseMethods.clickElementUsingJSE(articleMethods.getBookmarkIcon());
		/*WaitUtil.sleep(1000);
		bookmarksPageMethods.getFadeoutSection().click();*/
		WaitUtil.sleep(2000);
		WebBaseMethods.clickElementUsingJSE(articleMethods.getSaveStoriesTab());
		WaitUtil.sleep(5000);
		
		Assert.assertTrue(bookmarksPageMethods.getSavedStoriesList().size()>0, "The saved stories are not shown under the Saved stories list");
		String savedArticleHeading = bookmarksPageMethods.getSavedStoriesHeading().get(0).getAttribute("href");
		System.out.println(savedArticleHeading);
		softAssert.assertTrue(articleUrl.equalsIgnoreCase(savedArticleHeading), "The saved article "+articleUrl+" is different from the article under saved stories "+savedArticleHeading);
		bookmarksPageMethods.deleteSavedStories();
		WaitUtil.sleep(3000);
		Assert.assertTrue(bookmarksPageMethods.getSavedStoriesList().size()==0, "Deleting save stories is not working");
		softAssert.assertAll();
		
		
	}
	

}
