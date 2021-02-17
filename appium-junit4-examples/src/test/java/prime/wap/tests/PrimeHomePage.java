package prime.wap.tests;

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
import common.utilities.CommonMethods;
import common.utilities.ExcelUtil;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import prime.wap.pagemethods.MySubscriptionPageMethod;
import prime.wap.pagemethods.PrimeHeaderPageMethods;
import prime.wap.pagemethods.PrimeHomePageMethods;
import pwa.pagemethods.LoginPageMethods;
import pwa.pagemethods.NewHomePageMethods;
import wap.pagemethods.HomePageMethods;
import wap.pagemethods.MenuPageMethods;
import wap.pagemethods.WapListingPageMethods;
import web.pagemethods.WebBaseMethods;

public class PrimeHomePage extends BaseTest {
	private String wapUrl;
	private HomePageMethods homePageMethods;
	private MenuPageMethods menuPageMethods;
	private WapListingPageMethods wapListingMethods;
	private PrimeHomePageMethods primeHomePageMethods;
	private PrimeHeaderPageMethods primeHeaderPageMethods;
	private CommonMethods commonMethods;
	NewHomePageMethods newHomePageMethods;
	LoginPageMethods loginPageMethods;
	SoftAssert softAssert;
	boolean flag;
	private MySubscriptionPageMethod mySubscriptionPageMethod;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl = BaseTest.baseUrl;
		launchBrowser(wapUrl);
		homePageMethods = new HomePageMethods(driver);
		menuPageMethods = new MenuPageMethods(driver);
		primeHomePageMethods = new PrimeHomePageMethods(driver);
		wapListingMethods = new WapListingPageMethods(driver);
		commonMethods = new CommonMethods(driver);
		newHomePageMethods = new NewHomePageMethods(driver);
		loginPageMethods = new LoginPageMethods(driver);
		primeHeaderPageMethods = new PrimeHeaderPageMethods(driver);
		mySubscriptionPageMethod =  new MySubscriptionPageMethod(driver);
		Assert.assertTrue(openETPrimeSection(), "Unable to click ET prime tab");
	}

	@AfterMethod()
	public void clearBrowserSession()
	{
		WebBaseMethods.clearBrowserSessionCookie(driver);
		WebBaseMethods.clearSessionStorage();
		driver.navigate().refresh();
	}

	public boolean openETPrimeSection() {
		homePageMethods.jqueryInjForReactPages();
		homePageMethods.clickHmaberMenuIcon();
		WaitUtil.sleep(2000);
		flag = menuPageMethods.clickMenuOptionReact("ETPrime");
		WaitUtil.sleep(2000);
		if (driver.getCurrentUrl().contains("/prime"))
			flag = true;
		return flag;
	}

	@Test(description = "Verify ET prime homepage sub section", dataProvider = "subSections")
	public void verifyETPrimeHomePageSubSections(String sectionName) {
		driver.get(primeUrl);
		softAssert = new SoftAssert();
		try {
			WaitUtil.sleep(14000);
			List<String> hrefList = primeHomePageMethods.getSectionStoriesHrefList(sectionName);
			System.out.println("List is "+hrefList);
			List<String> errorLinks = primeHomePageMethods.getAllErrorLinks(hrefList);
			List<String> dupLinks = primeHomePageMethods.checkIfListIsUnique(hrefList);
			softAssert.assertTrue(hrefList.size() >= 4, "The links under the " + sectionName
					+ " on ET prime homepage should be more than or equal to 4 but found less");
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having status code 200. List of such links are: " + errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0, "The duplicate links are present under the " + sectionName
					+ " on ET prime homepage. List of the links: " + dupLinks);
			if (!(sectionName.equalsIgnoreCase("Top News") || sectionName.equalsIgnoreCase("Popular with Readers"))) {
				Map<String, String> viewAllSectionNameAndHrefs = primeHomePageMethods
						.getViewAllFromSectionNameAndURL(sectionName);
				viewAllSectionNameAndHrefs.forEach((name,url) -> {
					System.out.println("View All name is "+ name);
					int response = HTTPResponse
							.checkResponseCode(url);
					softAssert.assertTrue(response == 200,
							"The response code of the view all from section link is not 200 for section: " + sectionName);
					softAssert.assertEquals("View All "+ sectionName, name,"<br> View All name is mismatching, getting "+ name+ " instead"
							+ "of "+ "View All "+ sectionName );
				});
				
			}
			
			/* Validating L1 Section name are present on sections */
			if (!(sectionName.equalsIgnoreCase("Popular with Readers") || sectionName.equalsIgnoreCase("Top News"))) {
				Map<String, String> categoryTitleAndStoryHrefs = primeHomePageMethods
						.getL1CategoryNameAndStoryURLList(sectionName);
				categoryTitleAndStoryHrefs.forEach((url, name) -> {
					System.out.println("URL is " + url);
					System.out.println("L1 name is "+ name);
					if(name.equals(null) || name.length()<1)
					softAssert.assertTrue(false,"<br> Sub Category name is not present on Story URL "+ url+"<br>");
				});
				categoryTitleAndStoryHrefs.clear();
			}
			
			/* Validating Minute Reads are present on sections */
			if (!(sectionName.equalsIgnoreCase("Popular with Readers") || sectionName.equalsIgnoreCase("Top News"))) {
				Map<String, String> minReadAndStoryHrefs = primeHomePageMethods
						.getMinuteReadAndStoryURLList(sectionName);
				minReadAndStoryHrefs.forEach((url, value) -> {
					System.out.println("URL is " + url);
					System.out.println("Min Read is "+ value);
					if(!(value.contains("mins read")))
					softAssert.assertTrue(false,"<br> Minute Read is not present on Story URL "+ url+"<br>");
				});
				minReadAndStoryHrefs.clear();
			}
			
			
		

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "Exception occured during the test run for section " + sectionName);
		}
		softAssert.assertAll();

	}

	@DataProvider
	public String[] subSections() {
		String[] sections = { "Top News", "Recent Stories", "Popular with Readers", "Technology and Startups",
				"Consumer", "Money and Markets", "Under the Lens", "Corporate Governance", "Media and Communications",
				"Transportation", "FMCG", "Pharma and Healthcare", "Fintech and BFSI", "Infrastructure", "Food-Tech",
				"Economy & Policy", "Energy", "Environment" };

		return sections;

	}

	@Test(description = "This test verifies the our team section authors link")
	public void verifyOurTeamSection() {
		softAssert = new SoftAssert();
		String sectionName = "Our Team";
		List<String> hrefList = primeHomePageMethods.getOurTeamAuthorsLink();
		List<String> errorLinks = primeHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = primeHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() >= 4, "The links under the " + sectionName
				+ " on ET prime homepage should be more than or equal to 4 but found less");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0, "The duplicate links are present under the " + sectionName
				+ " on ET prime homepage. List of the links: " + dupLinks);
		List<String> listRedirectingToWeb = commonMethods.getLinksRedirectingToWeb(hrefList);
		softAssert.assertTrue(listRedirectingToWeb.size() == 0, "Few links under the section: " + sectionName
				+ " is opening in Web view. List of such links is: " + listRedirectingToWeb);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the our esteemed readers section on ET prime homepage")
	public void verifyOurEsteemedReadersSection() {
		softAssert = new SoftAssert();
		List<String> esteemedReadersList = primeHomePageMethods.getOurEsteemedReadersList();
		List<String> dupLinks = primeHomePageMethods.checkIfListIsUnique(esteemedReadersList);
		softAssert.assertTrue(esteemedReadersList.size() == 4,
				"Our esteemed reader section is not having count 4 instead found: " + esteemedReadersList.size());
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate readers are present under the our esteemed reader section on ET prime homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the popular categories section on ET Prime Homepage")
	public void verifyPopularCategoriesSectionOnEtPrimeHomepage() {
		softAssert = new SoftAssert();
		String sectionName = "Popular categories";
		List<String> hrefList = primeHomePageMethods.getPopularCategoriesSectionLinks();
		List<String> errorLinks = primeHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = primeHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() == 12, "The links under the " + sectionName
				+ " on ET prime homepage must be equal to 12 but found: " + hrefList.size());
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0, "The duplicate links are present under the " + sectionName
				+ " on ET prime homepage. List of the links: " + dupLinks);
		List<String> expectedCategoryList = new ArrayList<String>(
				Arrays.asList("Tech", "Consumer", "Markets", "Corporate Governance", "Telecom + OTT", "Auto + Aviation", "Pharma",
						"Fintech + BFSI", "Economy", "Infra", "Environment", "Energy"));
		List<String> actualCategoryList = primeHomePageMethods.getPopularCategoriesSectionLinksText();
		List<String> missingCategories = VerificationUtil.differenceTwoLists(actualCategoryList, expectedCategoryList);
		softAssert.assertTrue(
				VerificationUtil.validateContentOfList(expectedCategoryList, actualCategoryList)
				&& missingCategories.size() == 0,
				"Few popular categories are not shown on popular categories section on ET prime homepage. List of missing categories is: "
						+ missingCategories);
		softAssert.assertAll();

	}

	@Test(enabled = true, description = "This Test Verify functionality of Sign-in For different types of users", priority = 1, dataProvider = "login")
	public void verifySignIn(String email, String password, String subscritionStatus, String currentStatus) {
		driver.get(primeUrl);
		softAssert = new SoftAssert();
		softAssert.assertTrue(primeHomePageMethods.isSubscribeButtonDisplaying(),"<br>Subscribe button is not displaying for non logged in user <br>");
		//softAssert.assertTrue(primeHomePageMethods.isSignInButtonDisplaying(),"<br>Sign In button is not displaying for non logged in user <br>");

		/* Login */
		newHomePageMethods.clickMenuIcon();
		softAssert.assertTrue(loginPageMethods.newRegisteredUserLogin(email, password), "Login for registered user");
		newHomePageMethods.clickMenuIcon();
		softAssert.assertTrue(loginPageMethods.newCheckLoginOnly(), "Login status in side menu failed");
		driver.navigate().refresh();


		if(subscritionStatus.equals("Active") && currentStatus.equals("Active"))
		{
			softAssert.assertFalse(primeHomePageMethods.isSubscribeButtonDisplaying(),"<br> Subscribe button is displaying for active user <br>");
			softAssert.assertFalse(primeHomePageMethods.isStartYourMembershipButtonDisplaying(),"<br> Start Your membership button is displaying for active user <br>");

		}
		else if(subscritionStatus.equals("Active") && currentStatus.equals("Cancelled"))
		{
			softAssert.assertFalse(primeHomePageMethods.isSubscribeButtonDisplaying(),"<br> Subscribe button is displaying for active but cancelled user <br>");
			softAssert.assertTrue(primeHomePageMethods.isStartYourMembershipButtonDisplaying(),"<br> Start Your membership button is not displaying for active but cancelled user <br>");

		}
		else if(subscritionStatus.equals("Expired"))
		{
			softAssert.assertFalse(primeHomePageMethods.isSubscribeButtonDisplaying(),"<br> Subscribe button is displaying for active but cancelled user <br>");
			softAssert.assertTrue(primeHomePageMethods.isStartYourMembershipButtonDisplaying(),"<br> Start Your membership button is not displaying for active but cancelled user <br>");

		}
		else
		{
			softAssert.assertTrue(primeHomePageMethods.isSubscribeButtonDisplaying(),"<br> Subscribe button is not displaying for non subscribed user <br>");
			softAssert.assertFalse(primeHomePageMethods.isStartYourMembershipButtonDisplaying(),"<br> Start Your membership button is  displaying for active but cancelled user <br>");
		}

		softAssert.assertTrue(loginPageMethods.doLogout(),"<br> User is unable to logout <br>");
		softAssert.assertAll();
	}

	@Test(enabled= true, description = "Verify ET prime homepage sub section")
	public void verifyETPrimeHomePageHeaderSection() {
		driver.get(primeUrl);
		softAssert = new SoftAssert();
		List<String> headers = new ArrayList<String>(Arrays.asList( "ETPrime", "Tech",
				"Consumer", "Markets","Corporate Governance", "Telecom + OTT",
				"Auto + Aviation", "Pharma","Fintech + BFSI", "Economy", "Infra",
				"Environment", "Energy"));
		
		List<String> hrefList = primeHeaderPageMethods.getAllHeadersHrefList();
		List<String> errorLinks = primeHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = primeHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() == 13, "The links under the header sections "
				+ "on ET prime homepage are appearing less than 13, appearing as: "+ hrefList);
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few  Header links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0, "The duplicate links are present under the header "
				+ "on ET prime homepage. List of the links: " + dupLinks);
		
		softAssert.assertTrue(VerificationUtil.areListsEqualIgnoreCaseSensitivity(headers, primeHeaderPageMethods.getAllHeadersNameList()),"<br> "
				+ "Header Names are appearing incorrect as :" + primeHeaderPageMethods.getAllHeadersNameList());
		softAssert.assertAll();

	}
	
	@Test(enabled= true,description="This test case verifies My Subscription page of Active User", priority =16,  dataProvider = "subscriptionDetails")
	public void verifyMySubscriptionsPage(String email, String password, String subscritionStatus,String CurrentStatus, String Membership, String Amount, String StartDate, String EndDate ) {
		driver.get(primeUrl);
		softAssert = new SoftAssert();
		
		/* Login */
		newHomePageMethods.clickMenuIcon();
		softAssert.assertTrue(loginPageMethods.newRegisteredUserLogin(email, password), "Login for registered user");
		newHomePageMethods.clickMenuIcon();
		softAssert.assertTrue(loginPageMethods.newCheckLoginOnly(), "Login status in side menu failed");
		Assert.assertTrue(menuPageMethods.clickMySubscriptionButton(),"<br> Unable to click on My Subscription <br>");
		WaitUtil.sleep(4000);
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
		driver.get(primeUrl);
		WaitUtil.sleep(5000);
		softAssert.assertTrue(loginPageMethods.doLogout(),"<br> User is unable to logout <br>");
		softAssert.assertAll();
	}
	
	@DataProvider(name = "login")
	public Object[][] getData() {
		Object[][] loginArr = new Object[4][4];
		int i = 1;
		while (i < 5) {
			Map<String, String> testData = ExcelUtil.getTestDataRow("PrimeTestDataSheet", "verifyUserProfile", i);
			loginArr[i - 1][0] = testData.get("Email");
			loginArr[i - 1][1] = testData.get("Password");
			loginArr[i - 1][2] = testData.get("SubscriptionStatus");
			loginArr[i - 1][3] = testData.get("CurrentStatus");
			i++;
		}
		System.out.println(loginArr);
		return loginArr;
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
