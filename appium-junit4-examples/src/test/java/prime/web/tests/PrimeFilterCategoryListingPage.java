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
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.HTTPResponse;
import common.utilities.reporting.ScreenShots;
import prime.web.pagemethods.CategoryListingPageMethods;
import prime.web.pagemethods.HomePageMethods;
import prime.web.pagemethods.MyLibraryPageMethods;
import web.pagemethods.HeaderPageMethods;
import web.pagemethods.LoginPageMethods;
import web.pagemethods.WebBaseMethods;


public class PrimeFilterCategoryListingPage extends BaseTest {	
	HomePageMethods homePageMethods;
	HeaderPageMethods headerPageMethods;
	ScreenShots screenshots;
	SoftAssert softAssert;
	MyLibraryPageMethods myLibraryPageMethods;
	LoginPageMethods loginPageMethods;
	CategoryListingPageMethods categoryListingPageMethods;
	private String primeUrl;
	List<String> allArticles;
	Map<String,String> allFilters;

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

	@Test(enabled =true, description = "This Test Verify L2 Category Listing page", priority=1)
	public void verifyCategoryListingPage() {
		softAssert = new SoftAssert();
		driver.get(primeUrl);
		List<String> categories = new ArrayList<String>(Arrays.asList("Tech","Consumer","Markets","Corporate Governance","Telecom + OTT","Auto + Aviation","Pharma","Fintech","Infra","Economy","Energy","Environment"));
		
		allFilters = new HashMap<String,String>();
		categories.forEach(category->{
			softAssert.assertTrue(headerPageMethods.clickCategoryOnHeader(category), "<br> Unable to click on Category "+category+"<br>");
			WebBaseMethods.scrollToBottom();
			categoryListingPageMethods.getAllSubCategoriesTitleAndHref().forEach((K,V)->{
				allFilters.put(K,V);	
			});
		});

		Assert.assertTrue(!(allFilters.isEmpty()),"<br> No Filters are appearing on Category Listing pages<br>");
		System.out.println("Total Filters are "+allFilters.size());
		allFilters.forEach((K,V)->{
			driver.get(V);
			WebBaseMethods.scrollToBottom();
			String expectedURL = "https://economictimes.indiatimes.com/prime/tag/";
			String filterTag = categoryListingPageMethods.getCategoryTitleText().trim();
			filterTag = filterTag.replace(" ", "-");
			expectedURL = expectedURL + filterTag;

			/*Validating URL of the Page */
			softAssert.assertTrue(expectedURL.toLowerCase().trim().contains(driver.getCurrentUrl().toLowerCase().trim()),"<br> Current URL is appearing incorrect as "+driver.getCurrentUrl().toLowerCase()+" instead of "+expectedURL.toLowerCase()+" for Filter Category "+ K);

			/* Validating Title Text of Category */
			softAssert.assertEquals(categoryListingPageMethods.getCategoryTitleText().toLowerCase(),K.toLowerCase(), "<br> Category Title is appearing incorrect, getting text as "+categoryListingPageMethods.getCategoryTitleText()+" instead of "+K+ " for URL " +V);

			/* Validate Minute Read is appearing on All Articles */
			softAssert.assertEquals(categoryListingPageMethods.getTotalStoriesCount(), categoryListingPageMethods.getTotalMinuteReadCount(),"<br> Count of Minute Read are appearing as  "+categoryListingPageMethods.getTotalMinuteReadCount()+ " and Count of Stories are appearing as "+categoryListingPageMethods.getTotalStoriesCount()+" for filter category "+K);

			/* Validate Save Icon is appearing on All Articles */
			softAssert.assertEquals(categoryListingPageMethods.getTotalStoriesCount(), categoryListingPageMethods.getTotalSaveIconCount(),"<br> Count of Save icon are appearing as  "+categoryListingPageMethods.getTotalSaveIconCount()+ " and Count of Stories are appearing as "+categoryListingPageMethods.getTotalStoriesCount()+" for filter category "+K);
		
			/* Validating stories are not throwing any Error*/
			categoryListingPageMethods.getAllStoryHrefAndTitle().forEach((href,title) ->{
				int resCode = HTTPResponse.checkResponseCode(href);
				softAssert.assertEquals(resCode, 200, "<br>Story Link for <a href='" + href + "'>" + title
						+ "</a> is throwing HTTP " + resCode + " error.<br>");
			});
		});
		
		softAssert.assertAll();
	}

}
