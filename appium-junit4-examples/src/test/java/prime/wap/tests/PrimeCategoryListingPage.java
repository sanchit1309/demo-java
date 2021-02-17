package prime.wap.tests;

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
import common.utilities.WaitUtil;
import common.utilities.reporting.ScreenShots;
import prime.wap.pagemethods.CategoryListingPageMethods;
import prime.wap.pagemethods.PrimeHomePageMethods;
import web.pagemethods.WebBaseMethods;


public class PrimeCategoryListingPage extends BaseTest {	
	ScreenShots screenshots;
	SoftAssert softAssert;
	CategoryListingPageMethods categoryListingPageMethods;
	PrimeHomePageMethods primeHomePageMethod;
	private String primeUrl;
	List<String> allArticles;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		launchBrowser();
		primeUrl = BaseTest.primeUrl;
		categoryListingPageMethods = new CategoryListingPageMethods(driver);
		primeHomePageMethod=  new PrimeHomePageMethods(driver);
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

		String expectedURL = "https://m.economictimes.com/prime/";

		//driver.navigate().refresh();
		WebBaseMethods.scrollToTop();
		System.out.println("Category : "+category+" started");
		/* Clicking Category From Header */
		Assert.assertTrue(primeHomePageMethod.clickCategoryOnHeader(category), "<br> Unable to click on Category "+category+"<br>");


		expectedURL = expectedURL + categoryAndURL.get(category);

		/*Validating URL of the Page */
		softAssert.assertEquals(driver.getCurrentUrl(), expectedURL,"<br> Current URL is appearing incorrect as "+driver.getCurrentUrl());

		/*Checking if stories are present on Category */
		WaitUtil.sleep(5000);
		if(categoryListingPageMethods.getAllSubCategoriesTitleAndHref().size() == 0)
			Assert.assertTrue(false, "<br> No Stories are appearing on Category "+category);
		
		
		/* Validating minimum number of articles */
		//WebBaseMethods.scrollToBottom();
		//Assert.assertTrue(WebBaseMethods.scrollTillBottomOfPageIsReached(), "<br> Unable to Reach bottom of the page");
		Assert.assertTrue(categoryListingPageMethods.getTotalStoriesCount() > 15,"<br> Less than 15 stories are appearing on Category "+category+"<br>");

		/* Validate Sub Category is appearing on All Articles */

		softAssert.assertEquals(categoryListingPageMethods.getTotalStoriesCount(), categoryListingPageMethods.getTotalSubCategoriesCount(),"<br> Count of Subcategories are appearing as  "+categoryListingPageMethods.getTotalSubCategoriesCount()+ " and Count of Stories are appearing as "+categoryListingPageMethods.getTotalStoriesCount());

		/* Validate Minute Read is appearing on All Articles */
		softAssert.assertEquals(categoryListingPageMethods.getTotalStoriesCount(), categoryListingPageMethods.getTotalMinuteReadCount(),"<br> Count of Minute Read are appearing as  "+categoryListingPageMethods.getTotalMinuteReadCount()+ " and Count of Stories are appearing as "+categoryListingPageMethods.getTotalStoriesCount());

		//		/* Validate Save Icon is appearing on All Articles */
		//		softAssert.assertEquals(categoryListingPageMethods.getTotalStoriesCount(), categoryListingPageMethods.getTotalSaveIconCount(),"<br> Count of Save icon are appearing as  "+categoryListingPageMethods.getTotalSaveIconCount()+ " and Count of Stories are appearing as "+categoryListingPageMethods.getTotalStoriesCount());

		allArticles = new ArrayList<String>();
		/* Validating All stories are redirecting to same category only */
		categoryListingPageMethods.getAllStoryHrefAndTitle().forEach((href,title) ->{
			if(!(href.contains(categoryAndURL.get(category))))
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
