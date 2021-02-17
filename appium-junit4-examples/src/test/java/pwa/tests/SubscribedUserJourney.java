package pwa.tests;

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
import prime.wap.pagemethods.PrimeArticleShowPageMethods;
import pwa.pagemethods.Articleshow_newPageMethods;
import pwa.pagemethods.ETPrimeArticleshowMethods;
import pwa.pagemethods.ETPrimeHomePageMethods;
import pwa.pagemethods.LoginPageMethods;
import pwa.pagemethods.NewHomePageMethods;

public class SubscribedUserJourney extends BaseTest {
	private String baseUrl;
	NewHomePageMethods newHomePageMethods;
	LoginPageMethods loginPageMethods;
	Articleshow_newPageMethods articleshow_newPageMethods;
	ETPrimeHomePageMethods etPrimeHomePageMethods;
	ETPrimeArticleshowMethods etPrimeArticleshowMethods;
	Map<String, String> TestData = new HashMap<String, String>();
	PrimeArticleShowPageMethods articleShowPageMethods;
	SoftAssert softAssert;

	@BeforeClass
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		launchBrowser(baseUrl);
		newHomePageMethods = new NewHomePageMethods(driver);
		loginPageMethods = new LoginPageMethods(driver);
		articleshow_newPageMethods = new Articleshow_newPageMethods(driver);
		etPrimeHomePageMethods = new ETPrimeHomePageMethods(driver);
		etPrimeArticleshowMethods = new ETPrimeArticleshowMethods(driver);
		articleShowPageMethods = new PrimeArticleShowPageMethods(driver);
		TestData = ExcelUtil.getTestDataRow("TestDataSheet", "VerifyETPremium", 1);
	}

	@Test(description = "This test verify the login functionality with Active User", priority = 0)
	public void verifyUserLogin() {
		softAssert = new SoftAssert();
		newHomePageMethods.clickMenuIcon();
		softAssert.assertTrue(loginPageMethods.newRegisteredUserLogin(TestData.get("Email"), TestData.get("Password")), "Login for registered user");
		newHomePageMethods.clickMenuIcon();
		softAssert.assertTrue(loginPageMethods.newCheckLoginOnly(), "Login status in side menu failed");
		softAssert.assertAll();

	}

	@Test(description = "This test verify that ET Homepage is showing Top news section after successful login ", dependsOnMethods = {
			"verifyUserLogin" }, priority = 1)
	public void verifyETHomepageAfterLogin() {
		driver.navigate().refresh();
		Assert.assertTrue(newHomePageMethods.getAllTopNewsHref().size() > 5,
				"The top news are not shown on the ET homepage");
		Assert.assertTrue(newHomePageMethods.getEtPrimeWidgetStoriesHref().size() > 2,
				"The ET prime widget stories are not shown on the Et homepage");

	}

//	@Test(description = "This test verifies the ET free articleshow", dependsOnMethods = {
//			"verifyUserLogin" }, priority = 2)
//	public void verifyEtFreeArticleshow() {
//		softAssert = new SoftAssert();
//		Assert.assertTrue(newHomePageMethods.clickFirstETFreeArticle(),
//				"User is not able to click the first free ET article");
//		WaitUtil.sleep(5000);
//		WebBaseMethods.switchToChildWindow(2000);
//		List<String> failedElement = articleshow_newPageMethods.verifyPresenceOfAllElements();
//		Assert.assertTrue(failedElement.size() == 0, "Few element on the articleshow are not shown " + failedElement
//				+ "on articleshow url:" + driver.getCurrentUrl());
//		WebBaseMethods.switchToParentClosingChilds();
//		WaitUtil.sleep(5000);
//	}

	@Test(description = "This test verify that user is able to switch to ET prime Homepage from Header", dependsOnMethods = {
			"verifyUserLogin" }, priority = 3)
	public void verifyETPrimeHomepage() {
		softAssert = new SoftAssert();
		driver.navigate().refresh();
		WaitUtil.sleep(3000);
		Assert.assertTrue(newHomePageMethods.clickETPrimeNavBarLink(),
				"user not able to click on the et prime link on nav bar");

		Assert.assertTrue(etPrimeHomePageMethods.topStoriesListHref().size() > 0,
				"The top block stories are not shown on the ET prime homepage");

	}

	@Test(description = "This test verify that on any Prime Article, Paywall should not appear for Active User", dependsOnMethods = {
			"verifyUserLogin" }, priority = 4)
	public void verifyETPrimeArticleshow() {
		driver.navigate().refresh();
		softAssert = new SoftAssert();
		Assert.assertTrue(etPrimeHomePageMethods.clickFirstPrimeArticleFromPrimeHomepage(),
				"The user is not able to click on the first prime article from prime homepage");

		Assert.assertFalse(etPrimeArticleshowMethods.isPrimePaywalBlockerShown(),
				"The paid user is shown a paywall on the articleshow after successful login");
		
		/* Validating Synopsis is present*/
		softAssert.assertTrue(articleShowPageMethods.getSynopsisText().length() > 20,"<br> Synopsis is not appearing for the story "+ driver.getCurrentUrl());

		softAssert.assertTrue(articleShowPageMethods.getArticleText().length() > 1000, "<br> Article length is appearing less than 1000 characters for story "+driver.getCurrentUrl());

		/* Validating Minute Read is appearing on article */
		softAssert.assertTrue(articleShowPageMethods.getMinuteReadText().contains("Mins Read"),"<br> Minute Read is not appering on story "+driver.getCurrentUrl());

		/* Validating Sharing Article Text and number of sharing options are appearing on article */
		softAssert.assertTrue(articleShowPageMethods.isShareIconDisplaying(),"<br> Share Icon is not appearing on story "+ driver.getCurrentUrl());
		softAssert.assertTrue(articleShowPageMethods.isSaveIconDisplaying(),"<br> Save Icon is not appearing on story "+ driver.getCurrentUrl());
		softAssert.assertTrue(articleShowPageMethods.isFontIconDisplaying(),"<br> Font Icon is not appearing on story "+ driver.getCurrentUrl());
		softAssert.assertTrue(articleShowPageMethods.isCommentIconDisplaying(),"<br> Comment Icon is not appearing on story "+ driver.getCurrentUrl());
		
		/* Validating Add Comment button is displaying */
		softAssert.assertTrue(articleShowPageMethods.isAddCommentButtonDisplayed(),"<br> Add Comment button is not displaying <br>"+driver.getCurrentUrl());

		/* Validating Popular With Readers widget is displaying */
		softAssert.assertTrue(articleShowPageMethods.isPopularWithReadersWidgetDisplayed(),"<br> Popular With Readers widget is not displaying <br>"+driver.getCurrentUrl());

		softAssert.assertAll();
	}

}
