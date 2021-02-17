package prime.web.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import common.utilities.reporting.ScreenShots;
import prime.web.pagemethods.ArticleShowPageMethods;
import prime.web.pagemethods.CategoryListingPageMethods;
import prime.web.pagemethods.MyLibraryPageMethods;
import web.pagemethods.HeaderPageMethods;
import web.pagemethods.HomePageMethods;
import web.pagemethods.LoginPageMethods;
import web.pagemethods.WebBaseMethods;

public class PrimeArticleShowBlankPageCheck extends BaseTest {	
	HomePageMethods etHomePageMethods;
	HeaderPageMethods headerPageMethods;
	ScreenShots screenshots;
	SoftAssert softAssert;
	MyLibraryPageMethods myLibraryPageMethods;
	LoginPageMethods loginPageMethods;
	CategoryListingPageMethods categoryListingPageMethods;
	ArticleShowPageMethods articleShowPageMethods;
	String headerCategory = "";
	String email = "";
	String password = "";
	String giftStoryTitle = "";
	String toEmail = "";
	String name = "";

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		launchBrowser();
		etHomePageMethods = new HomePageMethods(driver);
		myLibraryPageMethods = new MyLibraryPageMethods(driver);
		loginPageMethods = new LoginPageMethods(driver);
		headerPageMethods = new HeaderPageMethods(driver);
		categoryListingPageMethods = new CategoryListingPageMethods(driver);
		articleShowPageMethods = new ArticleShowPageMethods(driver);
		screenshots = new ScreenShots();
	}

	@AfterMethod()
	public void clearBrowserSession()
	{
		WebBaseMethods.clearBrowserSessionCookie(driver);
		WebBaseMethods.clearSessionStorage();
		driver.navigate().refresh();
	}

	@Test(enabled =true, description = "This Test Verify Prime Article is Opening on Story page from ET Homepage", priority=1)
	public void verifyPrimeArticleOpeningFromETHomePageForActiveUser() {
		softAssert = new SoftAssert();
		Map<String, String> testData = ExcelUtil.getTestDataRow("PrimeTestDataSheet", "verifyUserProfile", 1);
		String email = testData.get("Email");
		String password = testData.get("Password");
		driver.get(baseUrl);

		/* Logging Through Active User */
		softAssert.assertTrue(headerPageMethods.logOutIfUserIsLoggedIn(),"<br> User is unable to logout first <br>");
		Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "<br> Unable to find sign in button <br> ");
		Assert.assertTrue(loginPageMethods.registeredUserLogin(email, password),
				"Unable to fill entries in the login window");
		WaitUtil.sleep(5000);
		Assert.assertTrue(headerPageMethods.checkUserLoggedInState(), "<br> The user is not able to login <br>");


		for(int i = 1; i<= etHomePageMethods.getPrimeSectionStoriesSize(); i++)
		{
			if(!(etHomePageMethods.isPrimeStoryOfIndexDisplayed(i)))
				continue;
			System.out.println(i);
			Assert.assertTrue(etHomePageMethods.clickPrimeStoryOfIndex(i),"<br> Unable to click Prime Story of Index "+i);
			//System.out.println(driver.getCurrentUrl());
			WebBaseMethods.switchChildIfPresent();
			//System.out.println(driver.getCurrentUrl());
			WaitUtil.waitForLoad(driver);

			/* Validating Prime Icon is appearing on story page */
			softAssert.assertTrue(articleShowPageMethods.isPrimeIconAboveStoryTitleDisplayed(),"<br> Prime Icon is not Displaying above Story Title on Story Page "+ driver.getCurrentUrl());

			/* Validating Synopsis is present*/
			softAssert.assertTrue(articleShowPageMethods.getSynopsisText().length() > 20,"<br> Synopsis is not appearing for the story "+ driver.getCurrentUrl());

			/* Validating user is able to read the article */
			softAssert.assertFalse(articleShowPageMethods.isPrimePaywalBlockerShown(),
					"The paid user is shown a paywall on the articleshow after successful login for story "+ driver.getCurrentUrl());
			softAssert.assertTrue(articleShowPageMethods.getArticleText().length() > 1000, "<br> Article length is appearing less than 1000 characters for story "+driver.getCurrentUrl());

			/* Validating Minute Read is appearing on article */
			softAssert.assertTrue(articleShowPageMethods.getMinuteReadText().contains("mins read"),"<br> Minute Read is not appering on story "+driver.getCurrentUrl());

			/* Validating Sharing Article Text and number of sharing options are appearing on article */
			softAssert.assertEquals(articleShowPageMethods.getShareArticleText(), "Share This Article", "<br> Share The Article text is appearing incorrect as "+articleShowPageMethods.getShareArticleText());
			softAssert.assertEquals(articleShowPageMethods.getShareArticleListCount(), 4, "<br> Share The Article count  is appearing incorrect as "+articleShowPageMethods.getShareArticleListCount());

			/* Validating Gift icon, Font Size, Save and Comment option are appearing on article */
			List<String> giftFontSaveCommentTexts = new ArrayList<String>(Arrays.asList("GIFT ARTICLE", "FONT SIZE", "SAVE", "COMMENT" ));
			softAssert.assertTrue(VerificationUtil.areListsEqual(giftFontSaveCommentTexts, articleShowPageMethods.getGiftCommentFontSaveTexts()),"<br> Gift, Font, Save and Comment Texts are mismatching <br>");

			/* Validating Add Comment button is displaying */
			softAssert.assertTrue(articleShowPageMethods.isAddCommentButtonDisplayed(),"<br> Add Comment button is not displaying <br>"+driver.getCurrentUrl());

			/* Validating Popular With Readers widget is displaying */
			softAssert.assertTrue(articleShowPageMethods.isPopularWithReadersWidgetDisplayed(),"<br> Popular With Readers widget is not displaying <br>"+driver.getCurrentUrl());

			/*Validating More Stories is displaying and  Title is same */
			if(driver.getCurrentUrl().contains("primearticleshow"))
			{
				softAssert.assertTrue(articleShowPageMethods.isMoreStoriesWidgetDisplayed(),"<br> More Stories Widget is not displaying for story <br>"+driver.getCurrentUrl());
			}

			WebBaseMethods.switchToParentClosingChilds();
		}
		Assert.assertTrue(headerPageMethods.doLogout(),"<br> Unable to Sign Out <br> ");
		softAssert.assertAll();
	}
	
	@Test(enabled =true, description = "This Test Verify Prime Article is Opening on Story page from ET Homepage for non logged in user", priority=2)
	public void verifyPrimeArticleOpeningFromETHomePageForNonLoggedInUser() {
		softAssert = new SoftAssert();
		driver.get(baseUrl);

		for(int i = 1; i<= etHomePageMethods.getPrimeSectionStoriesSize(); i++)
		{
			WaitUtil.sleep(5500);
			if(!(etHomePageMethods.isPrimeStoryOfIndexDisplayed(i)))
				continue;
			System.out.println(i);
			Assert.assertTrue(etHomePageMethods.clickPrimeStoryOfIndex(i),"<br> Unable to click Prime Story of Index "+i);
			//System.out.println(driver.getCurrentUrl());
			WebBaseMethods.switchChildIfPresent();
			//System.out.println(driver.getCurrentUrl());
			WaitUtil.waitForLoad(driver);

			/* Validating Prime Icon is appearing on story page */
			softAssert.assertTrue(articleShowPageMethods.isPrimeIconAboveStoryTitleDisplayed(),"<br> Prime Icon is not Displaying above Story Title on Story Page "+ driver.getCurrentUrl());

			/* Validating Synopsis is present*/
			softAssert.assertTrue(articleShowPageMethods.getSynopsisText().length() > 20,"<br> Synopsis is not appearing for the story "+ driver.getCurrentUrl());

			/* Validating user is not able to read the article */
			softAssert.assertTrue(articleShowPageMethods.isPrimePaywalBlockerShown(),
					"The paid user is shown a paywall on the articleshow after successful login for story "+ driver.getCurrentUrl());

			WebBaseMethods.switchToParentClosingChilds();
		}
		softAssert.assertAll();
	}
	


}
