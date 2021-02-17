package prime.web.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.CommonMethods;
import common.utilities.ExcelUtil;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import common.utilities.reporting.ScreenShots;
import prime.web.pagemethods.ArticleShowPageMethods;
import prime.web.pagemethods.CategoryListingPageMethods;
import prime.web.pagemethods.HomePageMethods;
import prime.web.pagemethods.MyLibraryPageMethods;
import web.pagemethods.HeaderPageMethods;
import web.pagemethods.LoginPageMethods;
import web.pagemethods.WebBaseMethods;

public class PrimeArticleShowPage extends BaseTest {	
	HomePageMethods homePageMethods;
	HeaderPageMethods headerPageMethods;
	ScreenShots screenshots;
	SoftAssert softAssert;
	MyLibraryPageMethods myLibraryPageMethods;
	LoginPageMethods loginPageMethods;
	CategoryListingPageMethods categoryListingPageMethods;
	ArticleShowPageMethods articleShowPageMethods;
	private String primeUrl;
	Map<String,String> categoryAndURL = new HashMap<String, String>();
	String headerCategory = "";
	String email = "";
	String password = "";
	String giftStoryTitle = "";
	String toEmail = "";
	String name = "";

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		launchBrowser();
		primeUrl = BaseTest.primeUrl;
		homePageMethods = new HomePageMethods(driver);
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

	@Test(enabled =true, description = "This Test Verify Mandatory Validations on Story page", priority=1)
	public void verifyMandatoryValidationsOnStoryPage() {
		softAssert = new SoftAssert();
		categoryAndURL.put("Tech","technology-and-startups");
		categoryAndURL.put("Consumer","consumer");
		categoryAndURL.put("Markets","money-and-markets");
		categoryAndURL.put("Corp Gov","corporate-governance");
		categoryAndURL.put("Telecom + OTT","media-and-communications");
		categoryAndURL.put("Auto + Aviation","transportation");
		categoryAndURL.put("Pharma","pharma-and-healthcare");
		categoryAndURL.put("Fintech","fintech-and-bfsi");
		categoryAndURL.put("Economy","economy-and-policy");
		categoryAndURL.put("Infra","infrastructure");
		categoryAndURL.put("Environment","environment");
		categoryAndURL.put("Energy","energy");

		Map<String, String> testData = ExcelUtil.getTestDataRow("PrimeTestDataSheet", "verifyUserProfile", 1);
		String email = testData.get("Email");
		String password = testData.get("Password");
		driver.get(primeUrl);

		/* Validating ET Prime Icon on Top */
		softAssert.assertTrue(headerPageMethods.isETPrimeLogoShown(),"<br> ET Prime logo is not appearing on top  <br>");

		/* Logging Through Active User */
		softAssert.assertTrue(headerPageMethods.logOutIfUserIsLoggedIn(),"<br> User is unable to logout first <br>");
		Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "<br> Unable to find sign in button <br> ");
		Assert.assertTrue(loginPageMethods.registeredUserLogin(email, password),
				"Unable to fill entries in the login window");
		WaitUtil.sleep(5000);
		Assert.assertTrue(headerPageMethods.checkUserLoggedInState(), "<br> The user is not able to login <br>");

		/* Validating ET Prime Icon on Top */
		softAssert.assertTrue(headerPageMethods.isETPrimeLogoShown(),"<br> ET Prime logo is not appearing on top after login  <br>");

		/* Validating Prime Icon for Subscribed User */
		softAssert.assertTrue(headerPageMethods.isPrimeIconForSubscribedUserShown(),"<br> Prime icon is not appearing for Subscribed user <br>");

		/* Taking Top Stories Articles, opening each one of them and Validating Mandatory Things  */
		homePageMethods.getTopStoriesHref().forEach((storyTitle,storyHref) ->{
			driver.get(storyHref);
			WaitUtil.waitForLoad(driver);

			/* Validating CategoryName from Header is appearing in URL For Prime Articles*/
			if(driver.getCurrentUrl().contains("primearticleshow"))
			{
				//WaitUtil.sleep(15000);
				categoryAndURL.forEach((K,V)-> {
					if(driver.getCurrentUrl().contains(V))
						headerCategory = K;			
				});
				if(headerCategory.equals(""))
					softAssert.assertTrue(false," <br>Unable to Find L1 Category for story " + storyTitle);
				softAssert.assertTrue(headerPageMethods.validateCategoryIsSelected(headerCategory),"<br> Category "+headerCategory+" is not selected by default for URL" + storyTitle);

				/* Validating Sub Category is appearing on the story page  for Prime Articles*/

				softAssert.assertTrue(articleShowPageMethods.getSubCategoryTitleText().length()>1,"<br> Sub Category is not appearing on Prime Articleshow " + storyTitle);
			}
			System.out.println(storyTitle);
			System.out.println(storyHref);

			/* Validating Prime Icon is appearing on story page */
			softAssert.assertTrue(articleShowPageMethods.isPrimeIconAboveStoryTitleDisplayed(),"<br> Prime Icon is not Displaying above Story Title on Story Page "+ storyTitle);

			/* Validating Same Story is Opened */
			softAssert.assertEquals(storyTitle, articleShowPageMethods.getStoryTitleText(),"<br> Story Title Texts are mismatching, getting "+ articleShowPageMethods.getStoryTitleText()+" instead of "+storyTitle);

			/* Validating Synopsis is present*/
			softAssert.assertTrue(articleShowPageMethods.getSynopsisText().length() > 20,"<br> Synopsis is not appearing for the story "+ storyTitle);

			/* Validating user is able to read the article */
			softAssert.assertFalse(articleShowPageMethods.isPrimePaywalBlockerShown(),
					"The paid user is shown a paywall on the articleshow after successful login for story "+ storyTitle);
			softAssert.assertTrue(articleShowPageMethods.getArticleText().length() > 1000, "<br> Article length is appearing less than 1000 characters for story "+storyTitle);

			/* Validating Minute Read is appearing on article */
			softAssert.assertTrue(articleShowPageMethods.getMinuteReadText().contains("mins read"),"<br> Minute Read is not appering on story "+storyTitle);

			/* Validating Sharing Article Text and number of sharing options are appearing on article */
			softAssert.assertEquals(articleShowPageMethods.getShareArticleText(), "Share This Article", "<br> Share The Article text is appearing incorrect as "+articleShowPageMethods.getShareArticleText());
			softAssert.assertEquals(articleShowPageMethods.getShareArticleListCount(), 4, "<br> Share The Article count  is appearing incorrect as "+articleShowPageMethods.getShareArticleListCount());

			/* Validating Gift icon, Font Size, Save and Comment option are appearing on article */
			List<String> giftFontSaveCommentTexts = new ArrayList<String>(Arrays.asList("GIFT ARTICLE", "FONT SIZE", "SAVE", "COMMENT" ));
			softAssert.assertTrue(VerificationUtil.areListsEqual(giftFontSaveCommentTexts, articleShowPageMethods.getGiftCommentFontSaveTexts()),"<br> Gift, Font, Save and Comment Texts are mismatching <br>");

			/* Validating Add Comment button is displaying */
			softAssert.assertTrue(articleShowPageMethods.isAddCommentButtonDisplayed(),"<br> Add Comment button is not displaying <br>"+storyTitle);

			/* Validating Popular With Readers widget is displaying */
			softAssert.assertTrue(articleShowPageMethods.isPopularWithReadersWidgetDisplayed(),"<br> Popular With Readers widget is not displaying <br>"+storyTitle);

			/*Validating More Stories is displaying and  Title is same */
			if(driver.getCurrentUrl().contains("primearticleshow"))
			{
				softAssert.assertTrue(articleShowPageMethods.isMoreStoriesWidgetDisplayed(),"<br> More Stories Widget is not displaying for story <br>"+storyTitle);
				softAssert.assertTrue(articleShowPageMethods.getMoreStoriesTitleText().contains(headerCategory),"<br> More Stories Title is different, appearing as "+articleShowPageMethods.getMoreStoriesTitleText()+" instead of "+headerCategory);
			}

			headerCategory = "";
		});
		Assert.assertTrue(homePageMethods.signOut(),"<br> Unable to Sign Out <br> ");
		softAssert.assertAll();
	}

	@Test(enabled = true,description="Verify max limit, number and length of message on Gifting Article", priority=2)
	public void verifyMandatoryValidationsOnGiftingArticle() {
		softAssert = new SoftAssert();
		driver.get(primeUrl);

		for(int i=1;i<4;i++)
		{
			Map<String, String> testData = ExcelUtil.getTestDataRow("PrimeTestDataSheet", "etGiftLogin", i);
			email = testData.get("Email");
			password = testData.get("Password");
			driver.get(primeUrl);

			WaitUtil.sleep(2000);
			/* Logging Through Active User */
			softAssert.assertTrue(headerPageMethods.logOutIfUserIsLoggedIn(),"<br> User is unable to logout first <br>");
			Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "<br> Unable to find sign in button <br> ");
			Assert.assertTrue(loginPageMethods.registeredUserLogin(email, password),
					"Unable to fill entries in the login window");
			WaitUtil.sleep(5000);
			Assert.assertTrue(headerPageMethods.checkUserLoggedInState(), "<br> The user is not able to login <br>");

			/* Clicking on the Article */
			Assert.assertTrue(homePageMethods.clickingOnArticleNumberOfTopStories(0),"<br> Unable to Click on the First Article on Top Stories Page <br>");

			/* Clicking on Gift Article Button */
			giftStoryTitle = articleShowPageMethods.getStoryTitleText();
			Assert.assertTrue(articleShowPageMethods.clickGiftArticleIcon(),"<br> Unable to Click on Gift Article Button <br>");

			/* Validating Gift Popup is displayed */
			Assert.assertTrue(articleShowPageMethods.isGiftPopupDisplayed(),"<br> Gift Popup is not displaying <br>");

			/* Validating Limit Reached Message is not displaying, if displaying then breaking the iteration and beginning with other user */
			if(articleShowPageMethods.isGiftLimitReachedDisplayed())
				continue;
			else
				break;
		}
		Assert.assertTrue(!articleShowPageMethods.isGiftLimitReachedDisplayed(),"<br> Limit is reached for all Email ID's");
		/* Validate Maximum Limit of Email in Gifting */
		softAssert.assertTrue(articleShowPageMethods.validateMaxLimitOfEmailInGifting(), "<br> Max Limit on Users in Gifting Article is failing");
		softAssert.assertTrue(articleShowPageMethods.clickClosePopupBtn(), "<br> Unable to close gift popup <br>");

		/*Validating user cannot enter same usernames more than one */
		Assert.assertTrue(articleShowPageMethods.clickGiftArticleIcon(), "<br> Unable to click on Gift Article Button <br>");
		Assert.assertTrue(articleShowPageMethods.isGiftPopupDisplayed(),"<br> Gift Popup is not appearing <br>");
		softAssert.assertTrue(articleShowPageMethods.verifyUserEmailMoreThanOne(), "<br> User Email can be entered more than one");
		softAssert.assertTrue(articleShowPageMethods.clickClosePopupBtn(), "<br> Unable to close gift popup <br>");

		/* Validating user cannot send gift to himself */
		Assert.assertTrue(articleShowPageMethods.clickGiftArticleIcon(), "<br> Unable to click on Gift Article Button <br>");
		Assert.assertTrue(articleShowPageMethods.isGiftPopupDisplayed(),"<br> Gift Popup is not appearing <br>");
		softAssert.assertTrue(articleShowPageMethods.verifyUserGiftingArticleToHimself(email), "<br> User is able to gift article to himself <br>");
		softAssert.assertTrue(articleShowPageMethods.clickClosePopupBtn(), "<br> Unable to close gift popup <br>");


		/* Validating article message length */
		Assert.assertTrue(articleShowPageMethods.clickGiftArticleIcon(), "<br> Unable to click on Gift Article Button <br>");
		Assert.assertTrue(articleShowPageMethods.isGiftPopupDisplayed(),"<br> Gift Popup is not appearing <br>");
		softAssert.assertTrue(articleShowPageMethods.verifyGiftArticleMessage(), "<br> Message length of Gifting Article is accepting more than 300 characters");
		softAssert.assertTrue(articleShowPageMethods.clickClosePopupBtn(), "<br> Unable to close gift popup <br>");

		Assert.assertTrue(homePageMethods.signOut(),"<br> Unable to Sign Out <br> ");
		softAssert.assertAll();

	}

	@Test(enabled = true,description="Verify user gifting an article for Prime Article", priority=3)
	public void verifyUserGiftingPrimeArticle() throws Exception {
		softAssert = new SoftAssert();
		driver.get(primeUrl);
		String message = "Testing Gifting Article";
		String giftedArticleURL = "";
		String emailSubject = " has gifted you an ET Prime article";
		message = "Testing Gifting Article";
		String giftedArticleLink = "";
		for(int i=1;i<11;i++)
		{
			giftStoryTitle = "";
			Map<String, String> testData = ExcelUtil.getTestDataRow("PrimeTestDataSheet", "etGiftedUser", i);
			toEmail = testData.get("Email");
			password = testData.get("Password");
			driver.get(primeUrl);

			WaitUtil.sleep(2000);
			/* Logging In*/
			softAssert.assertTrue(headerPageMethods.logOutIfUserIsLoggedIn(),"<br> User is unable to logout first <br>");
			Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "<br> Unable to find sign in button <br> ");
			Assert.assertTrue(loginPageMethods.registeredUserLogin(toEmail, password),
					"Unable to fill entries in the login window");
			WaitUtil.sleep(5000);
			Assert.assertTrue(headerPageMethods.checkUserLoggedInState(), "<br> The user is not able to login <br>");
			for(String href : homePageMethods.getPrimeArticlesTopStories().values() )
			{
				//homePageMethods.getPrimeArticlesTopStories().forEach((K,V)->{
				driver.get(href);
				if(articleShowPageMethods.isPrimePaywalBlockerShown())
				{
					giftStoryTitle = articleShowPageMethods.getStoryTitleText();
					giftedArticleLink = href;
					break;
				}
			}
			//});
			Assert.assertTrue(homePageMethods.signOut(),"<br> Unable to Sign Out <br> ");
			if(giftStoryTitle.length() > 1)
				break;
		}
		for(int i=1;i<4;i++)
		{
			Map<String, String> testData = ExcelUtil.getTestDataRow("PrimeTestDataSheet", "etGiftLogin", i);
			email = testData.get("Email");
			password = testData.get("Password");
			name = testData.get("Name");
			driver.get(primeUrl);

			WaitUtil.sleep(2000);

			/* Logging Through Active User */
			softAssert.assertTrue(headerPageMethods.logOutIfUserIsLoggedIn(),"<br> User is unable to logout first <br>");
			Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "<br> Unable to find sign in button <br> ");
			Assert.assertTrue(loginPageMethods.registeredUserLogin(email, password),
					"Unable to fill entries in the login window");
			WaitUtil.sleep(5000);
			Assert.assertTrue(headerPageMethods.checkUserLoggedInState(), "<br> The user is not able to login <br>");

			driver.get(giftedArticleLink);

			/* Clicking on Gift Article Button */
			giftStoryTitle = articleShowPageMethods.getStoryTitleText();
			Assert.assertTrue(articleShowPageMethods.clickGiftArticleIcon(),"<br> Unable to Click on Gift Article Button <br>");

			/* Validating Gift Popup is displayed */
			Assert.assertTrue(articleShowPageMethods.isGiftPopupDisplayed(),"<br> Gift Popup is not displaying <br>");

			/* Validating Limit Reached Message is not displaying, if displaying then breaking the iteration and beginning with other user */
			if(articleShowPageMethods.isGiftLimitReachedDisplayed())
				continue;
			else
				break;
		}

		/* Getting Current Epoc Time */
		long currentEpocTime = CommonMethods.getCurrentEpocTime();

		Assert.assertTrue(!articleShowPageMethods.isGiftLimitReachedDisplayed(),"<br> Limit is reached for all Email ID's");
		Assert.assertTrue(articleShowPageMethods.verifyUserGiftingAnArticle(toEmail, message), "<br> Unable to gift article");
		softAssert.assertTrue(articleShowPageMethods.clickClosePopupBtn(), "<br> Unable to close gift popup <br>");
		driver.get(primeUrl);
		WaitUtil.sleep(5000);
		Assert.assertTrue(homePageMethods.signOut(),"<br> Unable to Sign Out from Active User <br> ");

		/* Waiting for 5 Minutes for Email To Arrive */
		WaitUtil.sleep(300000);

		/* Verify user received gifted article email with same Story that was sent*/
		emailSubject = name + emailSubject;
		giftedArticleURL = articleShowPageMethods.getGiftedEmailLink(toEmail, giftedArticleLink, emailSubject, message, currentEpocTime);
		if(giftedArticleURL.length() == 0)
			Assert.assertTrue(false, "<br> Unable to get Gifted Article Email <br>");

		driver.get(giftedArticleURL);

		softAssert.assertTrue(articleShowPageMethods.isGiftPaywallDisplayed(),"<br> Gift Paywall is not Displayed <br>");

		/* Login through Non Subscribed user */
		softAssert.assertTrue(headerPageMethods.logOutIfUserIsLoggedIn(),"<br> User is unable to logout first <br>");
		Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "<br> Unable to find sign in button <br> ");
		Assert.assertTrue(loginPageMethods.registeredUserLogin(toEmail, password),
				"Unable to fill entries in the login window");
		WaitUtil.sleep(5000);
		Assert.assertTrue(headerPageMethods.checkUserLoggedInState(), "<br> The user is not able to login <br>");

		/* Validating user is able to read the article */
		softAssert.assertFalse(articleShowPageMethods.isPrimePaywalBlockerShown(),
				"The Gifted user is shown a paywall on the articleshow for story "+ giftStoryTitle);
		Assert.assertTrue(homePageMethods.signOut(),"<br> Unable to Sign Out <br> ");
		softAssert.assertAll();


	}

	@Test(enabled = true,description="Verify user gifting an article Premium", priority=4)
	public void verifyUserGiftingPremiumArticle() throws Exception {
		softAssert = new SoftAssert();
		driver.get(primeUrl);
		String message = "Testing Gifting Article";
		String giftedArticleURL = "";
		String emailSubject = " has gifted you an ET Prime article";
		message = "Testing Gifting Article";
		String giftedArticleLink = "";
		for(int i=1;i<11;i++)
		{
			giftStoryTitle = "";
			Map<String, String> testData = ExcelUtil.getTestDataRow("PrimeTestDataSheet", "etGiftedUser", i);
			toEmail = testData.get("Email");
			password = testData.get("Password");
			driver.get(primeUrl);

			WaitUtil.sleep(2000);
			/* Logging In*/
			softAssert.assertTrue(headerPageMethods.logOutIfUserIsLoggedIn(),"<br> User is unable to logout first <br>");
			Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "<br> Unable to find sign in button <br> ");
			Assert.assertTrue(loginPageMethods.registeredUserLogin(toEmail, password),
					"Unable to fill entries in the login window");
			WaitUtil.sleep(5000);
			Assert.assertTrue(headerPageMethods.checkUserLoggedInState(), "<br> The user is not able to login <br>");
			for(String href : homePageMethods.getPremiumArticlesTopStories().values() )
			{
				//homePageMethods.getPrimeArticlesTopStories().forEach((K,V)->{
				driver.get(href);
				if(articleShowPageMethods.isPrimePaywalBlockerShown())
				{
					giftStoryTitle = articleShowPageMethods.getStoryTitleText();
					giftedArticleLink = href;
					break;
				}
			}
			//});
			Assert.assertTrue(homePageMethods.signOut(),"<br> Unable to Sign Out <br> ");
			if(giftStoryTitle.length() > 1)
				break;
		}
		for(int i=1;i<4;i++)
		{
			Map<String, String> testData = ExcelUtil.getTestDataRow("PrimeTestDataSheet", "etGiftLogin", i);
			email = testData.get("Email");
			password = testData.get("Password");
			name = testData.get("Name");
			driver.get(primeUrl);

			WaitUtil.sleep(2000);

			/* Logging Through Active User */
			softAssert.assertTrue(headerPageMethods.logOutIfUserIsLoggedIn(),"<br> User is unable to logout first <br>");
			Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "<br> Unable to find sign in button <br> ");
			Assert.assertTrue(loginPageMethods.registeredUserLogin(email, password),
					"Unable to fill entries in the login window");
			WaitUtil.sleep(5000);
			Assert.assertTrue(headerPageMethods.checkUserLoggedInState(), "<br> The user is not able to login <br>");

			driver.get(giftedArticleLink);

			/* Clicking on Gift Article Button */
			giftStoryTitle = articleShowPageMethods.getStoryTitleText();
			Assert.assertTrue(articleShowPageMethods.clickGiftArticleIcon(),"<br> Unable to Click on Gift Article Button <br>");

			/* Validating Gift Popup is displayed */
			Assert.assertTrue(articleShowPageMethods.isGiftPopupDisplayed(),"<br> Gift Popup is not displaying <br>");

			/* Validating Limit Reached Message is not displaying, if displaying then breaking the iteration and beginning with other user */
			if(articleShowPageMethods.isGiftLimitReachedDisplayed())
				continue;
			else
				break;
		}

		/* Getting Current Epoc Time */
		long currentEpocTime = CommonMethods.getCurrentEpocTime();

		Assert.assertTrue(!articleShowPageMethods.isGiftLimitReachedDisplayed(),"<br> Limit is reached for all Email ID's");
		Assert.assertTrue(articleShowPageMethods.verifyUserGiftingAnArticle(toEmail, message), "<br> Unable to gift article");
		softAssert.assertTrue(articleShowPageMethods.clickClosePopupBtn(), "<br> Unable to close gift popup <br>");
		driver.get(primeUrl);
		WaitUtil.sleep(5000);
		Assert.assertTrue(homePageMethods.signOut(),"<br> Unable to Sign Out from Active User <br> ");

		/* Waiting for 5 Minutes for Email To Arrive */
		WaitUtil.sleep(300000);

		/* Verify user received gifted article email with same Story that was sent*/
		emailSubject = name + emailSubject;
		giftedArticleURL = articleShowPageMethods.getGiftedEmailLink(toEmail, giftedArticleLink, emailSubject, message, currentEpocTime);
		if(giftedArticleURL.length() == 0)
			Assert.assertTrue(false, "<br> Unable to get Gifted Article Email <br>");

		driver.get(giftedArticleURL);

		softAssert.assertTrue(articleShowPageMethods.isGiftPaywallDisplayed(),"<br> Gift Paywall is not Displayed <br>");

		/* Login through Non Subscribed user */
		softAssert.assertTrue(headerPageMethods.logOutIfUserIsLoggedIn(),"<br> User is unable to logout first <br>");
		Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "<br> Unable to find sign in button <br> ");
		Assert.assertTrue(loginPageMethods.registeredUserLogin(toEmail, password),
				"Unable to fill entries in the login window");
		WaitUtil.sleep(5000);
		Assert.assertTrue(headerPageMethods.checkUserLoggedInState(), "<br> The user is not able to login <br>");

		/* Validating user is able to read the article */
		softAssert.assertFalse(articleShowPageMethods.isPrimePaywalBlockerShown(),
				"The Gifted user is shown a paywall on the articleshow for story "+ giftStoryTitle);
		Assert.assertTrue(homePageMethods.signOut(),"<br> Unable to Sign Out <br> ");
		softAssert.assertAll();


	}

	@Test(enabled =true, description = "This Test Verify Non Logged In User is unable to read a story", priority=5)
	public void verifyNonLoggedInUserArticleReadabilityOnStoryPage() {
		softAssert = new SoftAssert();
		driver.get(primeUrl);

		softAssert.assertTrue(headerPageMethods.logOutIfUserIsLoggedIn(),"<br> User is unable to logout first <br>");
		/* Taking Top Stories Articles, opening each one of them and Validating Article Readability  */
		homePageMethods.getTopStoriesHref().forEach((storyTitle,storyHref) ->{
			driver.get(storyHref);
			WaitUtil.waitForLoad(driver);

			/* Validating Prime Icon is appearing on story page */
			softAssert.assertTrue(articleShowPageMethods.isPrimeIconAboveStoryTitleDisplayed(),"<br> Prime Icon is not Displaying above Story Title on Story Page "+ storyTitle);

			/* Validating Same Story is Opened */
			softAssert.assertEquals(storyTitle, articleShowPageMethods.getStoryTitleText(),"<br> Story Title Texts are mismatching, getting "+ articleShowPageMethods.getStoryTitleText()+" instead of "+storyTitle);

			/* Validating Synopsis is present*/
			softAssert.assertTrue(articleShowPageMethods.getSynopsisText().length() > 20,"<br> Synopsis is not appearing for the story "+ storyTitle);

			/* Validating user is not able to read the article */
			softAssert.assertTrue(articleShowPageMethods.isPaywallAndTextsDisplayedForNonLoggedInUser(),
					"Non Logged in user is not shown a paywall and respective texts on the articleshow  for story "+ storyTitle);

		});
		softAssert.assertAll();
	}

	@Test(enabled =true, description = "This Test Verify Article Readability for all types of users", priority=6, dataProvider = "login")
	public void verifyArticleReadabilityForAllUsersOnStoryPage(String email, String password, String subscritionStatus) {
		softAssert = new SoftAssert();
		driver.get(primeUrl);

		softAssert.assertTrue(headerPageMethods.logOutIfUserIsLoggedIn(),"<br> User is unable to logout first <br>");
		Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "<br> Unable to find sign in button <br> ");
		Assert.assertTrue(loginPageMethods.registeredUserLogin(email, password),
				"Unable to fill entries in the login window");
		WaitUtil.sleep(5000);
		Assert.assertTrue(headerPageMethods.checkUserLoggedInState(), "<br> The user is not able to login <br>");

		/* Taking Top Stories Articles, opening each one of them and Validating Article Readability  */
		homePageMethods.getTopStoriesHref().forEach((storyTitle,storyHref) ->{
			driver.get(storyHref);
			WaitUtil.waitForLoad(driver);

			/* Validating Prime Icon is appearing on story page */
			softAssert.assertTrue(articleShowPageMethods.isPrimeIconAboveStoryTitleDisplayed(),"<br> Prime Icon is not Displaying above Story Title on Story Page "+ storyTitle);

			/* Validating Same Story is Opened */
			softAssert.assertEquals(storyTitle, articleShowPageMethods.getStoryTitleText(),"<br> Story Title Texts are mismatching, getting "+ articleShowPageMethods.getStoryTitleText()+" instead of "+storyTitle);

			/* Validating Synopsis is present*/
			softAssert.assertTrue(articleShowPageMethods.getSynopsisText().length() > 20,"<br> Synopsis is not appearing for the story "+ storyTitle);

			if(subscritionStatus.equals("Active"))
			{
				softAssert.assertFalse(articleShowPageMethods.isPrimePaywalBlockerShown(),
						"The paid user is shown a paywall on the articleshow after successful login for story "+ storyTitle);
			}
			else if(subscritionStatus.equals("Expired"))
			{
				softAssert.assertTrue(articleShowPageMethods.isPaywallAndTextsDisplayedForExpiredUser(),
						"Expired user is not shown a paywall and respective texts on the articleshow  for story "+ storyTitle);
			}
			else
			{
				softAssert.assertTrue(articleShowPageMethods.isPaywallAndTextsDisplayedForNonSubscribedUser(),
						"Non Subscribed user is not shown a paywall and respective texts on the articleshow  for story "+ storyTitle);
			}

		});
		Assert.assertTrue(homePageMethods.signOut(),"<br> Unable to Sign Out <br> ");
		softAssert.assertAll();
	}

	@Test(enabled =true, description = "This Test Verify Non Subscribed User is unable to read articles even when URL is changed from primearticleshow to articleshow", priority=7)
	public void verifyNonSubscribedUserForPrimeArticleURLChange() {
		softAssert = new SoftAssert();
		driver.get(primeUrl);

		Map<String, String> testData = ExcelUtil.getTestDataRow("PrimeTestDataSheet", "verifyUserProfile", 3);
		String email = testData.get("Email");
		String password = testData.get("Password");
		driver.get(primeUrl);

		/* Login */
		softAssert.assertTrue(headerPageMethods.logOutIfUserIsLoggedIn(),"<br> User is unable to logout first <br>");
		Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "<br> Unable to find sign in button <br> ");
		Assert.assertTrue(loginPageMethods.registeredUserLogin(email, password),
				"Unable to fill entries in the login window");
		WaitUtil.sleep(5000);
		Assert.assertTrue(headerPageMethods.checkUserLoggedInState(), "<br> The user is not able to login <br>");


		/* Taking Top Stories Articles, opening each one of them and Validating Article Readability  */
		for(String href : homePageMethods.getPrimeArticlesTopStories().values() )
		{
			driver.get(href);
			WaitUtil.sleep(15000);
			String articleshowURL = href.replace("primearticleshow", "articleshow");
			driver.get(articleshowURL);
			WaitUtil.sleep(15000);
			softAssert.assertTrue(articleShowPageMethods.isErrorBlockDisplaying(),"<br> Error Block is not displaying for URL " +articleshowURL+" <br>");
			//softAssert.assertEquals(driver.getCurrentUrl(), href,"<br> Prime Article after changing URL to articleshow is not changing to primearticleshow for URL "+href+"<br> ");
			//softAssert.assertTrue(articleShowPageMethods.isPrimePaywalBlockerShown(), "<br> Paywall is not visible to non subscribed user for URL "+href+" <br>");			
		}
		softAssert.assertAll();
	}


	@DataProvider(name = "login")
	public Object[][] getData() {
		Object[][] loginArr = new Object[3][3];
		int i = 1;
		while (i < 4) {
			Map<String, String> testData = ExcelUtil.getTestDataRow("PrimeTestDataSheet", "verifyUserProfile", i);
			loginArr[i - 1][0] = testData.get("Email");
			loginArr[i - 1][1] = testData.get("Password");
			loginArr[i - 1][2] = testData.get("SubscriptionStatus");
			i++;
		}
		System.out.println(loginArr);
		return loginArr;
	}


}
