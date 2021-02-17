package prime.web.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import common.utilities.reporting.ScreenShots;
import prime.web.pagemethods.ArticleShowPageMethods;
import prime.web.pagemethods.AuthorsPageMethods;
import prime.web.pagemethods.HomePageMethods;
import prime.web.pagemethods.MyLibraryPageMethods;
import prime.web.pagemethods.MySubscriptionPageMethod;
import web.pagemethods.HeaderPageMethods;
import web.pagemethods.LoginPageMethods;
import web.pagemethods.WebBaseMethods;


public class PrimeHomePage extends BaseTest {	
	HomePageMethods homePageMethods;
	HeaderPageMethods headerPageMethods;
	ScreenShots screenshots;
	SoftAssert softAssert;
	MyLibraryPageMethods myLibraryPageMethods;
	LoginPageMethods loginPageMethods;
	ArticleShowPageMethods articleShowPageMethods;
	AuthorsPageMethods authorPageMethods;
	private String primeUrl;
	String storyTitle = "";
	String authorName = "";
	MySubscriptionPageMethod mySubscriptionPageMethod;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		launchBrowser();
		primeUrl = BaseTest.primeUrl;
		homePageMethods = new HomePageMethods(driver);
		myLibraryPageMethods = new MyLibraryPageMethods(driver);
		loginPageMethods = new LoginPageMethods(driver);
		headerPageMethods = new HeaderPageMethods(driver);
		articleShowPageMethods = new ArticleShowPageMethods(driver);
		authorPageMethods =  new AuthorsPageMethods(driver);
		mySubscriptionPageMethod =  new MySubscriptionPageMethod(driver);
		screenshots = new ScreenShots();

	}

	@AfterMethod()
	public void clearBrowserSession()
	{
		WebBaseMethods.clearBrowserSessionCookie(driver);
		WebBaseMethods.clearSessionStorage();
		driver.navigate().refresh();
	}

	@Test(enabled = true, description = "This Test Verify functionality of Sign-in For different types of users", priority = 1, dataProvider = "login")
	public void verifySignIn(String email, String password, String subscritionStatus) {
		driver.get(primeUrl);
		softAssert = new SoftAssert();
		
		System.out.println("Number of window "+ driver.getWindowHandles().size());
		
		softAssert.assertTrue(homePageMethods.isSubscribeButtonDisplaying(),"<br>Subscribe button is not displaying for non logged in user <br>");
		softAssert.assertTrue(homePageMethods.isStartYourTrialBoxDisplayed(),"<br>Start your trial button is not displaying for non logged in user <br>");
		softAssert.assertFalse(homePageMethods.isSignUpForFreeReadButtonDisplaying(), "<br> Sign Up for Free Read Button isn't displaying for non logged in user <br>");
		softAssert.assertTrue(homePageMethods.isBecomeMemberButtonDisplaying(),"<br> Become a Member button isn't displaying for non logged in user <br>");
		softAssert.assertTrue(headerPageMethods.logOutIfUserIsLoggedIn(),"<br> User is unable to logout first <br>");
		Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "<br> Unable to find sign in button");

		Assert.assertTrue(loginPageMethods.registeredUserLogin(email, password),
				"Unable to fill entries in the login window");
		WaitUtil.sleep(5000);
		System.out.println("After successfull login, URL is " +driver.getCurrentUrl());
		Assert.assertTrue(headerPageMethods.checkUserLoggedInState(), "<br> The user is not able to login");


		if(subscritionStatus.equals("Active"))
		{
			softAssert.assertFalse(homePageMethods.isStartYourTrialBoxDisplayed(),"<br> Start Your Trial is displaying for active user <br>");
			softAssert.assertFalse(homePageMethods.isSubscribeButtonDisplaying(),"<br>Subscribe button is  displaying for active user <br>");
			softAssert.assertFalse(homePageMethods.isSignUpForFreeReadButtonDisplaying(), "<br> Sign Up for Free Read Button is displaying for active user <br>");
			softAssert.assertFalse(homePageMethods.isBecomeMemberButtonDisplaying(), "<br> Become a Member Read Button is displaying for active user <br>");

		}
		else if(subscritionStatus.equals("Expired"))
		{
			softAssert.assertTrue(homePageMethods.isStartYourMembershipBoxDisplayed(),"<br> Start Your Membership is not displaying for Expired user <br>");
			softAssert.assertTrue(homePageMethods.isSubscribeButtonDisplaying(),"<br>Subscribe button is not displaying for Expired  user <br>");
			softAssert.assertFalse(homePageMethods.isSignUpForFreeReadButtonDisplaying(), "<br> Sign Up for Free Read Button is displaying for expired user <br>");
			softAssert.assertTrue(homePageMethods.isBecomeMemberButtonDisplaying(),"<br>Become a member button is not displaying for Expired user <br>");

		}
		else
		{
			softAssert.assertTrue(homePageMethods.isStartYourTrialBoxDisplayed(),"<br> Start Your Trial is not displaying for Non Subcribed user <br>");
			softAssert.assertTrue(homePageMethods.isSubscribeButtonDisplaying(),"<br>Subscribe button is not displaying for Non Subscribed user <br>");
			softAssert.assertFalse(homePageMethods.isSignUpForFreeReadButtonDisplaying(), "<br> Sign Up for Free Read Button is displaying for non subscribed user <br>");
			softAssert.assertTrue(homePageMethods.isBecomeMemberButtonDisplaying(),"<br>Become a member button is not displaying for Non Subscribed user <br>");

		}

		Assert.assertTrue(homePageMethods.signOut(),"<br> Unable to Sign Out <br> ");
		softAssert.assertAll();
	}

	@Test(enabled = true, description = "This Test Verify the count of Top Stories Sections and Minute Reads", priority=2)
	public void verifyTopStoriesSectionCountAndMinuteReads() {
		softAssert = new SoftAssert();
		driver.get(primeUrl);

		/* Validating Top Stories Count and Minute Reads */
		softAssert.assertTrue(homePageMethods.validateTopStoriesCount(),"<br> Top Stories Count is mismatching <br>");
		Map<String, String> articleNameAndMinuteRead = homePageMethods.getArticleNameAndMinuteReadTopStories();
		if(!articleNameAndMinuteRead.isEmpty()) {
			articleNameAndMinuteRead.forEach((K,V)->{
				if(!(V.contains("mins read")))
					softAssert.assertTrue(false, "<br> Minute Read are not appearing for Article " +K);

			});
		}else
			softAssert.assertTrue(false, "No Articles are found on Top Stories of Layout1");
		articleNameAndMinuteRead.clear();

		softAssert.assertAll();
	}

	//	@Test(enabled = false, description = "This Test Verify the Authors Count of Top Stories Sections ", priority=2)
	//	public void verifyTopStoriesSectionAuthorsCount() {
	//		softAssert = new SoftAssert();
	//		driver.get(primeUrl);
	//
	//		/* Validating Top Stories Authors Count */
	//		if(!(homePageMethods.getAuthorsCountOnTopStoriesSection() == homePageMethods.getTopStoriesCount()))
	//				
	//
	//		softAssert.assertAll();
	//	}

	@Test(enabled = true, description = "This Test Verify that Top Stories Articles are not throwing 404", priority=3)
	public void verifyTopStoriesArticleLinks() {
		softAssert = new SoftAssert();
		driver.get(primeUrl);

		Map<String, String> articleLinks = homePageMethods.getArticleNameAndHrefsOfTopStories();
		if(!articleLinks.isEmpty()) {
			articleLinks.forEach((K,V)->{
				int resCode = HTTPResponse.checkResponseCode(V);
				softAssert.assertEquals(resCode, 200, "<br>Story Link for <a href='" + V + "'>" + K
						+ "</a> is throwing HTTP " + resCode + " error.<br>");
			});
		}else
			softAssert.assertTrue(false, "No Article Story links found on Top Story Page" );
		softAssert.assertAll();
	}

	@Test(enabled= true, description = "This Test Verify Save Functionality of Top Stories", priority = 4)
	public void verifySaveFeatureOfTopStories() {
		softAssert = new SoftAssert();
		Map<String, String> testData = ExcelUtil.getTestDataRow("PrimeTestDataSheet", "verifyUserProfile", 1);
		String email = testData.get("Email");
		String password = testData.get("Password");
		driver.get(primeUrl);
		//Assert.assertTrue(homePageMethods.signInOnly(email, password), "<br> Unable to Sign-In on ET <br>");

		softAssert.assertTrue(headerPageMethods.logOutIfUserIsLoggedIn(),"<br> User is unable to logout first <br>");
		Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "Unable to find sign in button");

		Assert.assertTrue(loginPageMethods.registeredUserLogin(email, password),
				"Unable to fill entries in the login window");
		WaitUtil.sleep(5000);
		Assert.assertTrue(headerPageMethods.checkUserLoggedInState(), "The user is not able to login");


		WaitUtil.sleep(3000);
		Assert.assertTrue(homePageMethods.clickSavebtnOnHeroStoryOfTopStories(),"<br> Unable to Click on Save button of 1st story of Top Stories<br>");
		String articleTitle = homePageMethods.getStoryTitleOfHeroStoryOfTopStory();
		if(articleTitle.length() < 1)
			Assert.assertTrue(false, "<br> Unable to get Article Title of  1st Story of Top Stories <br>");
		Assert.assertTrue(homePageMethods.clickOnSavedStoriesButtonFromMenu(),"<br> Unable to click on Save Stories Button <br>");
		WaitUtil.sleep(3000);
		Assert.assertTrue(myLibraryPageMethods.isSavedStoryPresentOnMyLibraryPage(articleTitle), "<br> Saved Story " + articleTitle+ "is not present on My Library Page <br>");
		Assert.assertTrue(myLibraryPageMethods.unsaveArticleOnMyLibraryPage(articleTitle), "<br> Unable to unsave article on My Library Page <br>");
		driver.get(primeUrl);
		Assert.assertTrue(homePageMethods.isSaveButtonAppearingOnHeroStoryOfTopStories(),"<br> Story is appearing as saved despite being unsaved from My library page <br>");
		Assert.assertTrue(homePageMethods.signOut(),"<br> Unable to Sign Out <br> ");
	}

	@Test(enabled =true, description = "This Test Verify Header Section of Home page", priority=5)
	public void verifyHeaderSection() {
		softAssert = new SoftAssert();
		String txt = "Read. Lead. Succeed. ET Prime - For Members Only\nSharp Insight-rich, Indepth stories across 20+ sectors\nAccess the exclusive Economic Times stories, Editorial and Expert opinion";
		List<String> categoryList = new ArrayList<String>(Arrays.asList("Tech", "Consumer", "Markets", "Corporate Governance", "Telecom + OTT", "Auto + Aviation", "Pharma", "Fintech + BFSI", "Economy", "Infra", "Environment", "Energy"));
		driver.get(primeUrl);
		WaitUtil.sleep(3000);
		/* Validate Header Section is appearing */
		Assert.assertTrue(homePageMethods.isHeaderSectionDisplaying(),"<br> Header Section is not appearing on Home page <br>");

		/* Validate Number of Items in Hamburger Menu */
		softAssert.assertTrue(homePageMethods.validateHamburgerMenuInHeader(),"<br> Hamburger Menu is not appearing <br>");

		/* Validating Prime Home Tab is selected by default */
		softAssert.assertTrue(homePageMethods.isPrimeHomeTabSelected(),"<br> Prime Home Tab is not selected by default<br>");

		/* Validating ET Prime Logo */
		softAssert.assertTrue(homePageMethods.isETPrimeLogoAppearing(),"<br> ET Prime Logo is not appearing on TOP");

		/* Validating Search Icon in Header Section */
		softAssert.assertTrue(homePageMethods.isSearchIconDisplayingInHeaderSection(),"<br> Search icon is not appearing in the Header Section <br>");

		/* Validating SubHeader Text after Header Section */
		softAssert.assertEquals(homePageMethods.getSubHeaderWrapperText(), txt,"<br> SubHeader Text is mismatching, appearing "+homePageMethods.getSubHeaderWrapperText() +" instead of "+txt+ "<br>");

		/* Validate CategoryList in Header Section */
		softAssert.assertEquals(homePageMethods.getCategoriesListFromHeaderSection(), categoryList, "<br> Categories List from Header Section is incorrect, getting " + homePageMethods.getCategoriesListFromHeaderSection()+ " instead of " + categoryList);

		/* Validate CategoryList are not throwing 400 Error */
		List<String> categoryTitles = new ArrayList<String>();
		List<String> categoryHrefs = new ArrayList<String>();
		Map<String, String> storyTitleAndHrefs = homePageMethods
				.getCategoriesTitlesAndHrefsFromHeaderSections();
		storyTitleAndHrefs.forEach((title, href) -> {
			categoryTitles.add(title);
			categoryHrefs.add(href);
			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200, "<br><a href='" + href + "'>" + title
					+ "</a> - is throwing " + response + " error.<br>");
		});


		softAssert.assertAll();


	}

	@Test(enabled =true, description = "This Test Verify Recent Stories Widget and minute reads on articles", priority=6)
	public void verifyRecentStoriesWidgetAndMinuteReads() {
		softAssert = new SoftAssert();
		driver.get(primeUrl);

		/* Validate Prime Story Widget */
		Assert.assertTrue(homePageMethods.isPrimeStoriesWidgetDisplayed(), "<br> Prime Widget is not appearing on home page <br>");
		softAssert.assertTrue(homePageMethods.validateRecentStoriesText(), "<br> Recent Stories Text is not appearing <br>");
		Assert.assertTrue(homePageMethods.isMorePrimeStoriesLinkDisplayed(), "<br> More Prime Stories Link is not appearing <br>");
		softAssert.assertTrue(homePageMethods.checkMorePrimeStoriesLinkRedirection(), "<br> More Prime Stories Link is not redirecting to Prime-Plus page <br>");

		/* Validating Stories aren't redirecting to 400 */
		List<String> storyTitles = new ArrayList<String>();
		List<String> storyHrefs = new ArrayList<String>();
		Map<String, String> storyTitleAndHrefs = homePageMethods
				.getStoryTitlesAndHrefsFromPrimeStorySection();
		storyTitleAndHrefs.forEach((title, href) -> {
			storyTitles.add(title);
			storyHrefs.add(href);
			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200, "<br><a href='" + href + "'>" + title
					+ "</a> - is throwing " + response + " error.<br>");
		});

		/* Validating Minute Reads */
		Map<String, String> articleNameAndMinuteRead = homePageMethods.getArticleNameAndMinuteReadPrimeWidgetStories();
		if(!articleNameAndMinuteRead.isEmpty()) {
			articleNameAndMinuteRead.forEach((K,V)->{
				if(!(V.contains("mins read")))
					softAssert.assertTrue(false, "<br> Minute Read are not appearing for Article " +K);

			});
		}else
			softAssert.assertTrue(false, "No Articles are found on the Stories of Prime Story Widget");
		articleNameAndMinuteRead.clear();
		softAssert.assertAll();

	}

	@Test(enabled= true, description = "This Test Verify Save Functionality of Recent Widget Stories", priority = 7)
	public void verifySaveFeatureOfRecentStories() {
		Map<String, String> testData = ExcelUtil.getTestDataRow("PrimeTestDataSheet", "verifyUserProfile", 1);
		String email = testData.get("Email");
		String password = testData.get("Password");
		driver.get(primeUrl);
		//Assert.assertTrue(homePageMethods.signInOnly(email, password), "<br> Unable to Sign-In on ET <br>");
		softAssert.assertTrue(headerPageMethods.logOutIfUserIsLoggedIn(),"<br> User is unable to logout first <br>");
		Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "Unable to find sign in button");

		Assert.assertTrue(loginPageMethods.registeredUserLogin(email, password),
				"Unable to fill entries in the login window");
		WaitUtil.sleep(5000);
		Assert.assertTrue(headerPageMethods.checkUserLoggedInState(), "The user is not able to login");


		WaitUtil.sleep(3000);
		Assert.assertTrue(homePageMethods.clickSavebtnOnFirstStoryOfRecentStories(),"<br> Unable to Click on Save button of 1st story of Recent Stories<br>");
		String articleTitle = homePageMethods.getStoryTitleOfHeroStoryOfRecentStory();
		if(articleTitle.length() < 1)
			Assert.assertTrue(false, "<br> Unable to get Article Title of  1st Story of Recent Stories <br>");
		Assert.assertTrue(homePageMethods.clickOnSavedStoriesButtonFromMenu(),"<br> Unable to click on Save Stories Button <br>");
		WaitUtil.sleep(3000);
		Assert.assertTrue(myLibraryPageMethods.isSavedStoryPresentOnMyLibraryPage(articleTitle), "<br> Saved Story " + articleTitle+ "is not present on My Library Page <br>");
		Assert.assertTrue(myLibraryPageMethods.unsaveArticleOnMyLibraryPage(articleTitle), "<br> Unable to unsave article on My Library Page <br>");
		driver.get(primeUrl);
		Assert.assertTrue(homePageMethods.isSaveButtonAppearingOnHeroStoryOfRecentStories(),"<br> Story is appearing as saved despite being unsaved from My library page <br>");
		Assert.assertTrue(homePageMethods.signOut(),"<br> Unable to Sign Out <br> ");
	}



	@Test(enabled = true, description = "This Test Verify Popular with Reader Section", priority=5)
	public void verifyPopularWithReaderSection() {
		softAssert = new SoftAssert();
		driver.get(primeUrl);

		/* Validating Popular with Readers Widget */
		Assert.assertTrue(homePageMethods.isPopularWithReaderWidgetDisplaying(),"<br> Popular With Readers Widget is not displaying <br> ");
		softAssert.assertTrue(homePageMethods.validatePopularWithReaderStoriesText(),"<br> Popular with Readers Text is not displaying <br>");

		/* Validating All Links of Popular With Readers for 200 Response*/
		Map<String, String> allLinks = homePageMethods.getAllLinksUnderPopularWithReaderWidget();
		if(!allLinks.isEmpty()) {
			allLinks.forEach((K,V)->{
				int resCode = HTTPResponse.checkResponseCode(V);
				softAssert.assertEquals(resCode, 200, "<br> Link for <a href='" + V + "'>" + K
						+ "</a> is throwing HTTP " + resCode + " error.<br>");
			});
		}else
			softAssert.assertTrue(false, "No links found on Popular With Readers Page" );

		/* Validating Number of Stories under Popular With Readers */
		softAssert.assertTrue(homePageMethods.validateNumberOfStoriesUnderPopularWithReaders(),"<br> Number of Stories under Popular With Readers are not 20 <br>");


		/* Validating Min Reads are appearing on Articles */
		Map<String, String> articleNameAndMinuteRead = homePageMethods.getArticleNameAndMinuteReadPopularWithReaderStories();
		if(!articleNameAndMinuteRead.isEmpty()) {
			articleNameAndMinuteRead.forEach((K,V)->{
				if(!(V.contains("mins read")))
					softAssert.assertTrue(false, "<br> Minute Read are not appearing for Article " +K);

			});
		}else
			softAssert.assertTrue(false, "No Articles are found on the Stories of Popular Wtih Readers Widget");
		articleNameAndMinuteRead.clear();

		/* Validate Save Icon is appearing on Each Story */
		softAssert.assertEquals(homePageMethods.getSaveIconCountOnPopularWithReaderSection(), 20,"<br> Number of Save Icon On Popular with Reader section is appearing "+homePageMethods.getSaveIconCountOnPopularWithReaderSection());
		softAssert.assertAll();
	}

	@Test(enabled = true, description = "This Test Verify Top Categories Widget", priority=6)
	public void verifyTopCategoriesWidget() {
		softAssert = new SoftAssert();
		driver.get(primeUrl);
		List<String> categoryList = new ArrayList<String>(Arrays.asList("Tech", "Consumer", "Markets", "Corporate Governance", "Telecom + OTT", "Auto + Aviation", "Pharma", "Fintech + BFSI", "Economy", "Infra", "Environment", "Energy"));
		List<String> categoryDetails = new ArrayList<String>();
		categoryDetails.add("The people, companies, and themes shaping the brave new world");
		categoryDetails.add("What’s flying off the shelves, what’s not, and why");
		categoryDetails.add("The art, science, and hidden trends of investing");
		categoryDetails.add("Exclusive investigations into critical company actions");
		categoryDetails.add("What’s inside your phone and on your screen");
		categoryDetails.add("What's driving the auto sector");
		categoryDetails.add("Inside the business of healing");
		categoryDetails.add("The technology and people that move the world’s money");
		categoryDetails.add("Making sense of the big picture");
		categoryDetails.add("Tracking the economy's building blocks");
		categoryDetails.add("The business and economics of climate change");
		categoryDetails.add("The policy and business of fossil fuels and clean energy");

		/* Validating Top Categories Widget */
		Assert.assertTrue(homePageMethods.isTopCategoriesWidgetDisplaying(),"<br> Top Catgories Widget is not displaying <br>");
		softAssert.assertTrue(homePageMethods.validateTopCategoriesText(),"<br> Top Categories Text is not displaying <br>");


		/* Validating Count of Top Categories */
		softAssert.assertTrue(homePageMethods.validatetopCategoriesCount(),"<br> Number of Categories in Top Catgories Widget is not 12 <br>");

		/* Validating Title Names Appearing on Top Categories */
		softAssert.assertEquals(homePageMethods.getCategoriesListFromTopCategoriesSection(), categoryList, "<br> Categories Title on Top Categories is appearing incorrect, appearing as " +homePageMethods.getCategoriesListFromTopCategoriesSection());

		/* Validating Title Details Appearing on Top Categories */
		softAssert.assertEquals(homePageMethods.getCategoriesListDetailsFromTopCategoriesSection(), categoryDetails, "<br> Categories Details on Top Categories is appearing incorrect, appearing as " +homePageMethods.getCategoriesListDetailsFromTopCategoriesSection());

		/* Validating Links aren't redirecting to 400 */
		List<String> catTitles = new ArrayList<String>();
		List<String> catHrefs = new ArrayList<String>();
		Map<String, String> categoryTitleAndHrefs = homePageMethods
				.getTitlesAndHrefsFromTopStorySection();
		categoryTitleAndHrefs.forEach((title, href) -> {
			catTitles.add(title);
			catHrefs.add(href);
			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200, "<br><a href='" + href + "'>" + title
					+ "</a> - is throwing " + response + " error.<br>");
		});

		softAssert.assertAll();
	}

	@Test(enabled = true, description = "This Test Verify the Default Categories Widget, Article Links, Save Icon and Min Read ", dataProvider="defaultCategory",priority=7)
	public void verifyDefaultCategoriesSection(String category) {
		softAssert = new SoftAssert();
		driver.get(primeUrl);
		/* Verify Widget is appearing */
		Assert.assertTrue(homePageMethods.isCategoryAppearingOnHomePage(category), "<br> Category "+category+" is not appearing on Home Page");

		/* Verify Title is appearing */
		softAssert.assertEquals(category.toLowerCase(), homePageMethods.getCategoryTitle(category).toLowerCase(), "<br> Category Text is appearing as "+homePageMethods.getCategoryTitle(category));

		/* Verify More From Link is appearing and giving 200 as response */
		softAssert.assertEquals(category.toLowerCase(), homePageMethods.getCategoryMoreFromName(category).toLowerCase(), "<br> More from Text is appearing as "+homePageMethods.getCategoryMoreFromName(category));

		int resp = HTTPResponse.checkResponseCode(homePageMethods.getCategoryMoreLinkFromName(category));
		softAssert.assertEquals(resp, 200, "<br>More From Link of Category is not throwing "+resp+"error");

		/* Verify Article links for 200 Response */
		Map<String, String> categoryTitleAndHrefs = homePageMethods
				.getTitlesAndHrefsFromDefaultCategorySection(category);
		categoryTitleAndHrefs.forEach((title, href) -> {
			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200, "<br>Article <a href='" + href + "'>" + title
					+ "</a> - is throwing " + response + " error.<br>");
		});
		categoryTitleAndHrefs.clear();

		/* Verify Article Counts */
		softAssert.assertEquals(6, homePageMethods.getTotalArticleCountFromDefaultCategorySection(category),"<br> Total Count of Stories on Category is appearing "+homePageMethods.getTotalArticleCountFromDefaultCategorySection(category));

		/* Verify Sub Category links for 200 Response */
		categoryTitleAndHrefs = homePageMethods
				.getTitlesAndHrefsForSubCategoryFromDefaultCategorySection(category);
		categoryTitleAndHrefs.forEach((title, href) -> {
			int response = HTTPResponse.checkResponseCode(href);
			softAssert.assertEquals(response, 200, "<br> Sub Category <a href='" + href + "'>" + title
					+ "</a> - is throwing " + response + " error.<br>");
		});
		categoryTitleAndHrefs.clear();

		/* Verify Sub Category Counts */
		if(category.equals("Under the lens") | category.equals("FMCG") | category.equals("Food-tech") | category.equals("Environment"))
			softAssert.assertEquals(6, homePageMethods.getTotalSubCategoryCountFromDefaultCategorySection(category),"<br> Total Count of Sub Category on Category is appearing "+homePageMethods.getTotalArticleCountFromDefaultCategorySection(category));
		else
			softAssert.assertEquals(4, homePageMethods.getTotalSubCategoryCountFromDefaultCategorySection(category),"<br> Total Count of Sub Category on Category is appearing "+homePageMethods.getTotalArticleCountFromDefaultCategorySection(category));

		/* Verify Minute Read is appearing on every article of category */
		Map<String, String> articleNameAndMinuteRead = homePageMethods.getArticleNameAndMinuteReadFromDefaultCategory(category);
		if(!articleNameAndMinuteRead.isEmpty()) {
			articleNameAndMinuteRead.forEach((K,V)->{
				if(!(V.contains("mins read")))
					softAssert.assertTrue(false, "<br> Minute Read are not appearing for Article " +K);

			});
		}else
			softAssert.assertTrue(false, "No Articles are found on the Stories of Category");
		articleNameAndMinuteRead.clear();

		/* Verify Save Icon Count */
		softAssert.assertEquals(6, homePageMethods.getSaveIconCountOnDefaultCategory(category),"<br> Total Count of Save Icon on Category is appearing "+homePageMethods.getTotalArticleCountFromDefaultCategorySection(category));

		softAssert.assertAll();
	}



	@Test(enabled= false,description="This test case verifies the footer links on Home Page", priority =-8)
	public void verifyFooterLinks() {
		softAssert = new SoftAssert();
		driver.get(primeUrl);

		Map<String, String> footerLinks = homePageMethods.getFooterLinkHrefs();
		if(!footerLinks.isEmpty()) {
			footerLinks.forEach((K,V)->{
				int resCode = HTTPResponse.checkResponseCode(V);
				if(!(resCode ==  0 || resCode == 200))
					softAssert.assertTrue(false, "<br>Footer Link for <a href='" + V + "'>" + K
							+ "</a> is throwing HTTP " + resCode + " error.<br>");
			});
		}else
			softAssert.assertTrue(false, "No footer links found on Home Page");

		softAssert.assertAll();
	}

	@Test(enabled= true,description="This test case verifies the Author's Widget on Home Page", priority =9)
	public void verifyAuthorsWidget() {
		softAssert = new SoftAssert();
		driver.get(primeUrl);
		/* Validate Authors Widget and Text */
		Assert.assertTrue(homePageMethods.isAuthorsWidgetDisplaying(),"<br> Author's Widget is not displaying <br>");
		softAssert.assertEquals(homePageMethods.getAuthorsWidgetText(), "Our team","<br> Author's Widget Text is not appearing as Our Team <br>");

		/* Validate Author's Name and Links are appearing correct */
		Map<String, String> authorsLinks = homePageMethods.getAuthorsNameAndHref();
		if(!authorsLinks.isEmpty()) {
			authorsLinks.forEach((K,V)->{
				int resCode = HTTPResponse.checkResponseCode(V);
				softAssert.assertEquals(resCode, 0 | 200 , "<br>Footer Link for <a href='" + V + "'>" + K
						+ "</a> is throwing HTTP " + resCode + " error.<br>");
			});
		}else
			softAssert.assertTrue(false, "No footer links found on Home Page");
		softAssert.assertEquals(authorsLinks.size(), 36,"<br> Number of author's are appearing "+authorsLinks.size()+" instead of 36");
		softAssert.assertEquals(homePageMethods.getAuthorsDesignationList().size(), 36,"<br> Number of author's designation are appearing "+homePageMethods.getAuthorsDesignationList().size()+" instead of 36");
		softAssert.assertEquals(homePageMethods.getAuthorsDetailsList().size(), 36,"<br> Number of author's details are appearing "+homePageMethods.getAuthorsDetailsList().size()+" instead of 36");
		softAssert.assertEquals(homePageMethods.getAuthorsImageList().size(), 36,"<br> Number of author's image are appearing "+homePageMethods.getAuthorsImageList().size()+" instead of 36");


		softAssert.assertAll();
	}

	@Test(enabled= true,description="This test case verifies the Readers Widget on Home Page", priority =10)
	public void verifyReadersWidget() {
		softAssert = new SoftAssert();
		driver.get(primeUrl);
		/* Validate Readers Widget and Text */
		Assert.assertTrue(homePageMethods.isReadersWidgetDisplaying(),"<br> Reader's Widget is not displaying <br>");
		softAssert.assertEquals(homePageMethods.getReadersWidgetText(), "Our esteemed readers","<br> Readers Widget Text is not appearing as Our esteemed readers <br>");
		softAssert.assertEquals(homePageMethods.getReadersSubHeadingWidgetText(), "The who's who of the industry trust ET Prime for insightful and un-biased stories","<br> Readers Widget Sub Heading Text is not appearing <br>");

		softAssert.assertEquals(homePageMethods.readersCountOnAuthorsWidget(),4,"<br> Number of reader's are appearing "+homePageMethods.readersCountOnAuthorsWidget()+"4");
		softAssert.assertAll();
	}

	@Test(enabled = true, description = "This test verifies search results are shown for a query, no duplicate results are shown and stories are opening properly.", priority = 11)
	public void verifySearch() {
		softAssert = new SoftAssert();
		String searchString = "Flipkart, Modi, Bank";
		List<String> searchKeyword = Arrays.asList(searchString.split("\\s*,\\s*"));
		driver.get(primeUrl);
		WaitUtil.sleep(3000);
		searchKeyword.forEach(keyword -> {
			if (headerPageMethods.clickSearchIconIfVisible()) {
				if (headerPageMethods.isSearchFieldVisible()) {
					headerPageMethods.sendQueryInSearchField(keyword);
					WaitUtil.sleep(5000);
					if (headerPageMethods.areSearchResultsVisible()) {
						List<String> resultTitles = new ArrayList<String>();
						List<String> resultHrefs = new ArrayList<String>();
						Map<String, String> resultTitlesAndHrefs = headerPageMethods.getResultTitlesAndHrefs();
						resultTitlesAndHrefs.forEach((title, href) -> {
							resultTitles.add(title);
							resultHrefs.add(href);
							int response = HTTPResponse.checkResponseCode(href);
							softAssert.assertEquals(response, 200,
									"<br><a href='" + href + "'>" + title
									+ "</a> - story in search results for keyword: " + keyword + " is throwing "
									+ response + " error.<br>");
						});

						List<String> resultTitlesDup = VerificationUtil.isListUnique(resultTitles);
						softAssert.assertTrue(resultTitlesDup.isEmpty(),
								"<br>Below stories are repeating in search results for keyword: " + keyword + " -><br>"
										+ resultTitlesDup + "<br>");

						List<String> storyNotFoundUrls = new ArrayList<String>();
						resultHrefs.forEach(href -> {
							driver.get(href);
							WaitUtil.sleep(4000);
							if (driver.getPageSource().toLowerCase().contains("story not found")) {
								storyNotFoundUrls.add(href);
							}
						});
						softAssert.assertTrue(storyNotFoundUrls.isEmpty(),
								"<br>Below stories in search results for keyword: " + keyword
								+ " are opening a 'Story not found' page -> <br>" + storyNotFoundUrls + "<br>");

					} else {
						softAssert.assertTrue(false,
								"<br>No results are shown in search for keyword: " + keyword + "<br>");
					}
				} else {
					softAssert.assertTrue(false, "<Search field is not visible on Search page.<br>");
				}
			} else {
				softAssert.assertTrue(false, "<br>Unable to click Search icon on Homepage.<br>");
			}
			WaitUtil.sleep(2000);
		});
		softAssert.assertAll();
	}

	@Test(enabled= true,description="This test case verifies the Audio Summary on Home Page", priority =12)
	public void verifyAudioSummaryOnArticlesTopStories() {
		softAssert = new SoftAssert();
		Map<String, String> testData = ExcelUtil.getTestDataRow("PrimeTestDataSheet", "verifyUserProfile", 1);
		String email = testData.get("Email");
		String password = testData.get("Password");
		driver.get(primeUrl);
		softAssert.assertTrue(headerPageMethods.logOutIfUserIsLoggedIn(),"<br> User is unable to logout first <br>");
		Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "Unable to find sign in button");

		Assert.assertTrue(loginPageMethods.registeredUserLogin(email, password),
				"Unable to fill entries in the login window");
		WaitUtil.sleep(5000);
		Assert.assertTrue(headerPageMethods.checkUserLoggedInState(), "The user is not able to login");
		WaitUtil.sleep(3000);


		/* Validate any Audio Summary appearing on Top Stories of Home Page */
		if(homePageMethods.isAudioSummaryAppearingOnHomePage())
		{
			homePageMethods.getAudioSummaryArticlesOnTopStories().forEach((K,V)->{
				driver.get(V);
				softAssert.assertTrue(articleShowPageMethods.isAudioSummaryIconDisplaying(), "<br> Audio Summary isn't appearing on Article "+K);
			});
		}
		Assert.assertTrue(homePageMethods.signOut(),"<br> Unable to Sign Out <br> ");
		softAssert.assertAll();
	}

	@Test(enabled= true,description="This test case verifies the Author's Name on Top Stories Home Page", priority =14)
	public void verifyAuthorNameOnArticlesTopStories() {
		softAssert = new SoftAssert();
		Map<String, String> testData = ExcelUtil.getTestDataRow("PrimeTestDataSheet", "verifyUserProfile", 1);
		String email = testData.get("Email");
		String password = testData.get("Password");
		driver.get(primeUrl);
		softAssert.assertTrue(headerPageMethods.logOutIfUserIsLoggedIn(),"<br> User is unable to logout first <br>");
		Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "Unable to find sign in button");

		Assert.assertTrue(loginPageMethods.registeredUserLogin(email, password),
				"Unable to fill entries in the login window");
		WaitUtil.sleep(5000);
		Assert.assertTrue(headerPageMethods.checkUserLoggedInState(), "The user is not able to login");
		WaitUtil.sleep(3000);

		/* Validate Articles with No Authors on Top Stories should not have authors name in the articleshow page also */
		if(homePageMethods.isArticleWithNoAuthorAppearingOnTopStoriesHomePage())
		{
			homePageMethods.getArticlesWithNoAuthorOnTopStories().forEach((K,V)->{
				driver.get(V);
				softAssert.assertFalse(articleShowPageMethods.isAuthorNameDisplaying(), "<br> Author name is appearing on articleshow page but not on Prime homepage for article "+K);
			});
		}

		driver.get(primeUrl);

		/* Validate Articles with Authors name */
		if(homePageMethods.isArticleWithAuthorAppearingOnTopStoriesHomePage())
		{
			homePageMethods.getAuthorNameAndStoryOnTopStories().forEach((K,V)->{
				driver.get(V);
				softAssert.assertTrue(articleShowPageMethods.isAuthorNameDisplaying(), "<br> Author name is not appearing on articleshow page for article "+V);
				K = K.replace(" & ", "");
				softAssert.assertTrue(K.trim().equalsIgnoreCase(articleShowPageMethods.getAuthorName().trim()),"<br> Getting Author name on homepage as "+K+" and getting author name on articleshow as "+articleShowPageMethods.getAuthorName()+" for article "+V);
			});
		}

		Assert.assertTrue(homePageMethods.signOut(),"<br> Unable to Sign Out <br> ");
		softAssert.assertAll();
	}


	@Test(enabled= true,description="This test case verifies Author of Top Stories contain same stories on Author's page", priority =15)
	public void verifyTopStoriesArticlesOnAuthorPage() {
		softAssert = new SoftAssert();

		Map<String, String> testData = ExcelUtil.getTestDataRow("PrimeTestDataSheet", "verifyUserProfile", 1);
		String email = testData.get("Email");
		String password = testData.get("Password");
		driver.get(primeUrl);
		softAssert.assertTrue(headerPageMethods.logOutIfUserIsLoggedIn(),"<br> User is unable to logout first <br>");
		Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "Unable to find sign in button");

		Assert.assertTrue(loginPageMethods.registeredUserLogin(email, password),
				"Unable to fill entries in the login window");
		WaitUtil.sleep(5000);
		Assert.assertTrue(headerPageMethods.checkUserLoggedInState(), "The user is not able to login");
		WaitUtil.sleep(3000);

		/* Validate Articles with Authors name */
		if(homePageMethods.isArticleWithAuthorAppearingOnTopStoriesHomePage())
		{
			homePageMethods.getArticlesWithAuthorOnTopStories().forEach((K,V)->{
				driver.get(V);
				Assert.assertTrue(articleShowPageMethods.isAuthorNameDisplaying(), "<br> Author name is not appearing on articleshow page for article "+K);
				storyTitle = articleShowPageMethods.getStoryTitleText();
				authorName = articleShowPageMethods.getAuthorsName();
				softAssert.assertTrue(authorName.length()>0,"<br> Unable to Get Author name on article "+storyTitle);
				Assert.assertTrue(articleShowPageMethods.clickAuthorName(),"<br> Unable to click on Author's name <br>");
				WebBaseMethods.switchChildIfPresent();
				Assert.assertTrue(driver.getCurrentUrl().contains("https://economictimes.indiatimes.com/etreporter/author"),"<br Unable to switch to Author's Page, getting current URL as "+driver.getCurrentUrl());
				softAssert.assertTrue(authorPageMethods.isStoryPresentOnAuthorPage(storyTitle),"<br> Story "+storyTitle+" is not present on Author's page "+driver.getCurrentUrl());
				softAssert.assertTrue(authorName.contains(authorPageMethods.getAuthorName()),"<br> Author's name is appearing incorrect as "+authorPageMethods.getAuthorName()+" instead of "+authorName);
				WebBaseMethods.switchToParentClosingChilds();
			});
		}

		softAssert.assertAll();
	}

	@Test(enabled= true,description="This test case verifies My Subscription page of Active User", priority =16,  dataProvider = "subscriptionDetails")
	public void verifyMySubscriptionsPage(String email, String password, String subscritionStatus,String CurrentStatus, String Membership, String Amount, String StartDate, String EndDate ) {
		driver.get(primeUrl);
		softAssert = new SoftAssert();
		softAssert.assertTrue(headerPageMethods.logOutIfUserIsLoggedIn(),"<br> User is unable to logout first <br>");
		Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "<br> Unable to find sign in button");

		Assert.assertTrue(loginPageMethods.registeredUserLogin(email, password),
				"Unable to fill entries in the login window");
		WaitUtil.sleep(5000);
		Assert.assertTrue(headerPageMethods.checkUserLoggedInState(), "<br> The user is not able to login");
		Assert.assertTrue(headerPageMethods.clickMySubscription(),"<br> Unable to click on My Subscription <br>");
		WaitUtil.sleep(4000);
		WebBaseMethods.switchChildIfPresent();
		if((subscritionStatus.equals("Active") && CurrentStatus.equals("Cancelled"))|| subscritionStatus.equals("Expired"))
		{
			String Amnt[] = Amount.split("[.]");
			Amount = Amnt[0];
			softAssert.assertTrue(mySubscriptionPageMethod.isSubscriptionBoxDisplayed(),"<br> My Subscription Box is not displaying <br>");
			softAssert.assertTrue(mySubscriptionPageMethod.isSubscriptionBoxContainsText(CurrentStatus),"<br> Subscription status "+ CurrentStatus+ " is not appearing in Subscription Box <br>");
			softAssert.assertTrue(mySubscriptionPageMethod.isSubscriptionBoxContainsText(Membership),"<br> Membership status "+ Membership+ " is not appearing in Subscription Box <br>");
			softAssert.assertTrue(mySubscriptionPageMethod.isSubscriptionBoxContainsText(Amount),"<br> Amount "+ Amount+ " is not appearing in Subscription Box <br>");
			softAssert.assertTrue(mySubscriptionPageMethod.isSubscriptionBoxContainsText(StartDate),"<br> StartDate  "+ StartDate+ " is not appearing in Subscription Box <br>");
			softAssert.assertTrue(mySubscriptionPageMethod.isSubscriptionBoxContainsText(EndDate),"<br> EndDate  "+ EndDate+ " is not appearing in Subscription Box <br>");
		}
		else if(subscritionStatus.equals("Active") && CurrentStatus.equals("Active"))
		{
			String Amnt[] = Amount.split("[.]");
			Amount = Amnt[0];
			softAssert.assertTrue(mySubscriptionPageMethod.isSubscriptionBoxDisplayed(),"<br> My Subscription Box is not displaying <br>");
			softAssert.assertTrue(mySubscriptionPageMethod.isSubscriptionBoxContainsText(CurrentStatus),"<br> Subscription status "+ CurrentStatus+ " is not appearing in Subscription Box <br>");
			softAssert.assertTrue(mySubscriptionPageMethod.isSubscriptionBoxContainsText(Membership),"<br> Membership status "+ Membership+ " is not appearing in Subscription Box <br>");
			softAssert.assertTrue(mySubscriptionPageMethod.isSubscriptionBoxContainsText(Amount),"<br> Amount "+ Amount+ " is not appearing in Subscription Box <br>");
			softAssert.assertTrue(mySubscriptionPageMethod.isSubscriptionBoxContainsText(StartDate),"<br> StartDate  "+ StartDate+ " is not appearing in Subscription Box <br>");
			softAssert.assertTrue(mySubscriptionPageMethod.isSubscriptionBoxContainsText(EndDate),"<br> EndDate  "+ EndDate+ " is not appearing in Subscription Box <br>");
			softAssert.assertTrue(mySubscriptionPageMethod.isRenewButtonDisplayed(),"<br> Renew button is not displayed for Active user "+ email);
			softAssert.assertTrue(mySubscriptionPageMethod.isCancelButtonDisplayed(),"<br> Cancel button is not displayed for Active user "+ email);
			if(mySubscriptionPageMethod.isCancelButtonDisplayed())
			{
				softAssert.assertTrue(mySubscriptionPageMethod.clickCancelButton(),"<br>  Unable to click on Cancel button <br>");
				softAssert.assertTrue(mySubscriptionPageMethod.clickOtherOptionInCancelSuvery(),"<br>  Unable to click on Other Option in Cancellation Survey <br>");
				softAssert.assertTrue(mySubscriptionPageMethod.clickNextButtonInSurvey(),"<br>  Unable to click on Next button In Cancellation Survey <br>");
				softAssert.assertTrue(mySubscriptionPageMethod.clickOtherOptionInCancelSuvery(),"<br>  Unable to click on Other Option in Cancellation Survey <br>");
				softAssert.assertTrue(mySubscriptionPageMethod.clickNextButtonInSurvey(),"<br>  Unable to click on Next button In Cancellation Survey <br>");
				softAssert.assertTrue(mySubscriptionPageMethod.isCancelMembershipButtonDisplayed(),"<br> Cancel Membership button is not appearing on Final Cancellation Page");
				softAssert.assertTrue(mySubscriptionPageMethod.isCancelMembershipWrapperDisplayed(),"<br> Cancel Membership Wrapper is not appearing on Final Cancellation Page");	
			}
		}
		else
		{
			softAssert.assertTrue(mySubscriptionPageMethod.isSubscribeNowButtonDisplayed(),"<br> Subscribe Now button is not displaying <br>");
			softAssert.assertFalse(mySubscriptionPageMethod.isSubscriptionBoxDisplayed(),"<br> My Subscription Box is displaying <br>");

		}
		WebBaseMethods.switchToParentClosingChilds();
		Assert.assertTrue(homePageMethods.signOut(),"<br> Unable to Sign Out <br> ");
		softAssert.assertAll();
	}
	
	@Test(enabled= true,description="This test case verifies ETPrime.com Redirection", priority =16)
	public void verifyETPrimeRedirection() {
		softAssert = new SoftAssert();
		driver.get("https://etprime.com/");
		softAssert.assertTrue(homePageMethods.isSubscribeButtonDisplaying(),"<br>Subscribe button is not displaying for non logged in user <br>");
		softAssert.assertTrue(homePageMethods.isStartYourTrialBoxDisplayed(),"<br>Start your trial button is not displaying for non logged in user <br>");
		softAssert.assertFalse(homePageMethods.isSignUpForFreeReadButtonDisplaying(), "<br> Sign Up for Free Read Button isn't displaying for non logged in user <br>");
		softAssert.assertTrue(homePageMethods.isBecomeMemberButtonDisplaying(),"<br> Become a Member button isn't displaying for non logged in user <br>");
		softAssert.assertTrue(driver.getCurrentUrl().equals("https://economictimes.indiatimes.com/prime"),"<br> ETPrime.com is redirecting to "+driver.getCurrentUrl());
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

	

	@DataProvider(name = "defaultCategory")
	public Object[][] getDefaultCatData() {
		return new Object[][] {{"Tech"},{"Consumer"},{"Markets"},{"Under the lens"},{"Corporate Governance"},{"Telecom + OTT"},{"Auto + Aviation"},{"FMCG"},{"Pharma"},{"Fintech + BFSI"},{"Infra"},{"Food-tech"},{"Economy"},{"Energy"},{"Environment"}};
	}

	@DataProvider(name = "subscriptionDetails")
	public Object[][] getSubscriptionData() {
		Object[][] loginArr = new Object[4][8];
		int i = 1;
		while (i < 5) {
			Map<String, String> testData = ExcelUtil.getTestDataRow("PrimeTestDataSheet", "verifyUserProfile", i);
			loginArr[i - 1][0] = testData.get("Email");
			loginArr[i - 1][1] = testData.get("Password");
			loginArr[i - 1][2] = testData.get("SubscriptionStatus");
			loginArr[i - 1][3] = testData.get("CurrentStatus");
			loginArr[i - 1][4] = testData.get("Membership");
			loginArr[i - 1][5] = testData.get("Amount");
			loginArr[i - 1][6] = testData.get("StartDate");
			loginArr[i - 1][7] = testData.get("EndDate");
			i++;
		}
		System.out.println(loginArr);
		return loginArr;
	}

}
