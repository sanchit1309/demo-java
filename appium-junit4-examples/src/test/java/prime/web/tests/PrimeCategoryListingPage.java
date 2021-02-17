package prime.web.tests;

import java.io.IOException;
import java.util.ArrayList;
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
import common.utilities.VerificationUtil;
import common.utilities.reporting.ScreenShots;
import prime.web.pagemethods.CategoryListingPageMethods;
import prime.web.pagemethods.HomePageMethods;
import prime.web.pagemethods.MyLibraryPageMethods;
import web.pagemethods.HeaderPageMethods;
import web.pagemethods.LoginPageMethods;
import web.pagemethods.WebBaseMethods;


public class PrimeCategoryListingPage extends BaseTest {	
	HomePageMethods homePageMethods;
	HeaderPageMethods headerPageMethods;
	ScreenShots screenshots;
	SoftAssert softAssert;
	MyLibraryPageMethods myLibraryPageMethods;
	LoginPageMethods loginPageMethods;
	CategoryListingPageMethods categoryListingPageMethods;
	private String primeUrl;
	List<String> allArticles;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		launchBrowser();
		primeUrl = BaseTest.primeUrl;
		homePageMethods = new HomePageMethods(driver);
		myLibraryPageMethods = new MyLibraryPageMethods(driver);
		loginPageMethods = new LoginPageMethods(driver);
		headerPageMethods = new HeaderPageMethods(driver);
		categoryListingPageMethods = new CategoryListingPageMethods(driver);
		screenshots = new ScreenShots();
	}

	@AfterMethod()
	public void clearBrowserSession()
	{
		WebBaseMethods.clearBrowserSessionCookie(driver);
		WebBaseMethods.clearSessionStorage();
		driver.navigate().refresh();
	}

	@Test(enabled =true, description = "This Test Verify Category Listing page", priority=1, dataProvider = "defaultCategory")
	public void verifyCategoryListingPage(String category) {
		softAssert = new SoftAssert();
		driver.get(primeUrl);
		Map<String,String> categoryAndURL = new HashMap<String, String>();
		categoryAndURL.put("Tech","technology-and-startups");
		categoryAndURL.put("Consumer","consumer");
		categoryAndURL.put("Markets","money-and-markets");
		categoryAndURL.put("Corporate Governance","corporate-governance");
		categoryAndURL.put("Telecom + OTT","media-and-communications");
		categoryAndURL.put("Auto + Aviation","transportation");
		categoryAndURL.put("Pharma","pharma-and-healthcare");
		categoryAndURL.put("Fintech + BFSI","fintech-and-bfsi");
		categoryAndURL.put("Economy","economy-and-policy");
		categoryAndURL.put("Infra","infrastructure");
		categoryAndURL.put("Environment","environment");
		categoryAndURL.put("Energy","energy");

		String expectedURL = "https://economictimes.indiatimes.com/prime/";

		//driver.navigate().refresh();
		WebBaseMethods.scrollToTop();
		System.out.println("Category : "+category+" started");
		/* Clicking Category From Header */
		Assert.assertTrue(headerPageMethods.clickCategoryOnHeader(category), "<br> Unable to click on Category "+category+"<br>");

		/* Validating Title Text of Category */
		softAssert.assertEquals(categoryListingPageMethods.getCategoryTitleText().toLowerCase(),category.toLowerCase(), "<br> Category Title is appearing incorrect, getting text as "+categoryListingPageMethods.getCategoryTitleText());

		expectedURL = expectedURL + categoryAndURL.get(categoryListingPageMethods.getCategoryTitleText());

		/*Validating URL of the Page */
		softAssert.assertEquals(driver.getCurrentUrl(), expectedURL,"<br> Current URL is appearing incorrect as "+driver.getCurrentUrl());

		/* Validating minimum number of articles */
		WebBaseMethods.scrollToBottom();
		Assert.assertTrue(WebBaseMethods.scrollTillBottomOfPageIsReached(), "<br> Unable to Reach bottom of the page");
		softAssert.assertTrue(categoryListingPageMethods.getTotalStoriesCount() > 15,"<br> Less than 15 stories are appearing on Category "+category+"<br>");

		/* Validate Sub Category is appearing on All Articles */
		if(category.equals("Telecom + OTT"))
			softAssert.assertEquals(categoryListingPageMethods.getTotalStoriesCount() - categoryListingPageMethods.getTotalSubCategoriesCount(),2,"<br> Count of Subcategories are appearing as  "+categoryListingPageMethods.getTotalSubCategoriesCount()+ " and Count of Stories are appearing as "+categoryListingPageMethods.getTotalStoriesCount());
		else
			softAssert.assertEquals(categoryListingPageMethods.getTotalStoriesCount(), categoryListingPageMethods.getTotalSubCategoriesCount(),"<br> Count of Subcategories are appearing as  "+categoryListingPageMethods.getTotalSubCategoriesCount()+ " and Count of Stories are appearing as "+categoryListingPageMethods.getTotalStoriesCount());

		/* Validate Minute Read is appearing on All Articles */
		softAssert.assertEquals(categoryListingPageMethods.getTotalStoriesCount(), categoryListingPageMethods.getTotalMinuteReadCount(),"<br> Count of Minute Read are appearing as  "+categoryListingPageMethods.getTotalMinuteReadCount()+ " and Count of Stories are appearing as "+categoryListingPageMethods.getTotalStoriesCount());

		/* Validate Save Icon is appearing on All Articles */
		softAssert.assertEquals(categoryListingPageMethods.getTotalStoriesCount(), categoryListingPageMethods.getTotalSaveIconCount(),"<br> Count of Save icon are appearing as  "+categoryListingPageMethods.getTotalSaveIconCount()+ " and Count of Stories are appearing as "+categoryListingPageMethods.getTotalStoriesCount());

		allArticles = new ArrayList<String>();
		/* Validating All stories are redirecting to same category only */
		categoryListingPageMethods.getAllStoryHrefAndTitle().forEach((href,title) ->{
			if(!(href.contains(categoryAndURL.get(categoryListingPageMethods.getCategoryTitleText()))))
				softAssert.assertTrue(false, "<br> URL "+href+" does not contain Category name "+categoryAndURL.get(categoryListingPageMethods.getCategoryTitleText()));
			
			allArticles.add(title);
		});
		
		/* Validating All Articles are unique */
		List<String> duplicateTitlesInCategory = VerificationUtil.isListUnique(allArticles);
		softAssert.assertTrue(duplicateTitlesInCategory.isEmpty(),"<br> Category "+categoryAndURL.get(categoryListingPageMethods.getCategoryTitleText())+" have duplicate stories "+duplicateTitlesInCategory);
		softAssert.assertAll();
	}


	@DataProvider(name = "defaultCategory")
	public Object[][] getDefaultCatData() {
		return new Object[][] {{"Tech"},{"Consumer"},{"Markets"},{"Corporate Governance"},{"Telecom + OTT"},{"Auto + Aviation"},{"Pharma"},{"Fintech + BFSI"},{"Infra"},{"Economy"},{"Energy"},{"Environment"}};

	}

}
