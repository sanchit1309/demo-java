package prime.wap.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.HTTPResponse;
import common.utilities.WaitUtil;
import common.utilities.reporting.ScreenShots;
import prime.wap.pagemethods.CategoryListingPageMethods;
import prime.wap.pagemethods.PrimeHomePageMethods;
import web.pagemethods.WebBaseMethods;


public class PrimeFilterCategoryListingPage extends BaseTest {	
	PrimeHomePageMethods homePageMethods;
	ScreenShots screenshots;
	SoftAssert softAssert;
	CategoryListingPageMethods categoryListingPageMethods;
	private String primeUrl;
	List<String> allArticles;
	Map<String,WebElement> allFilters;
	String filterName = "";

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		launchBrowser();
		primeUrl = BaseTest.primeUrl;
		homePageMethods = new PrimeHomePageMethods(driver);
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
		//List<String> categories = new ArrayList<String>(Arrays.asList("Economy"));

		allFilters = new HashMap<String,WebElement>();
		categories.forEach(category->{
			softAssert.assertTrue(homePageMethods.clickCategoryOnHeader(category), "<br> Unable to click on Category "+category+"<br>");
			WaitUtil.sleep(5000);
			if(categoryListingPageMethods.getAllSubCategoriesTitleAndHref().size() == 0)
				Assert.assertTrue(false, "<br> No Stories are appearing on Category "+category);
			HashSet<String> filter = new HashSet<String>();
			for(int i=1; i<=categoryListingPageMethods.getAllSubCategoriesTitleAndHref().size();i++)
			{
				//WaitUtil.sleep(5000);
				filterName = categoryListingPageMethods.getFilterNameByNumber(i);
				if(filter.contains(filterName))
					break;
				Assert.assertTrue(categoryListingPageMethods.clickFilterByNumber(i),"<br> Unable to Click on Filter Category for category "+ category);
				filter.add(filterName);
				String expectedURL = "https://m.economictimes.com/prime/tag/";
				filterName = filterName.replace(" ", "-");
				expectedURL = expectedURL + filterName;

				/*Validating URL of the Page */
				softAssert.assertTrue(expectedURL.toLowerCase().trim().contains(driver.getCurrentUrl().toLowerCase().trim()),"<br> Current URL is appearing incorrect as "+driver.getCurrentUrl().toLowerCase()+" instead of "+expectedURL.toLowerCase()+" for Filter Category "+ filterName);

				/* Checking if stories are appearing on filter Category */
				if(categoryListingPageMethods.getTotalStoriesCount() == 0)
					Assert.assertTrue(false, "<br> No Stories are appearing on Category "+category);

				/* Validate Minute Read is appearing on All Articles */
				softAssert.assertEquals(categoryListingPageMethods.getTotalStoriesCount(), categoryListingPageMethods.getTotalMinuteReadCount(),"<br> Count of Minute Read are appearing as  "+categoryListingPageMethods.getTotalMinuteReadCount()+ " and Count of Stories are appearing as "+categoryListingPageMethods.getTotalStoriesCount()+" for filter category "+filterName);


				/* Validating stories are not throwing any Error*/
				categoryListingPageMethods.getAllStoryHrefAndTitle().forEach((href,title) ->{
					int resCode = HTTPResponse.checkResponseCode(href);
					//System.out.println(title);
					softAssert.assertEquals(resCode, 200, "<br>Story Link for <a href='" + href + "'>" + title
							+ "</a> is throwing HTTP " + resCode + " error. for Category <br>"+ category+ " and filter name "+ filterName);
				});
				driver.navigate().back();
				WaitUtil.sleep(4000);
			}
		});

		softAssert.assertAll();
	}

}
