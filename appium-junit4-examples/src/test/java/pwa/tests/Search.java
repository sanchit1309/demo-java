package pwa.tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.HTTPResponse;
import pwa.pagemethods.SearchMethods;
import web.pagemethods.WebBaseMethods;


public class Search extends BaseTest{

	private String wapUrl;
	private SearchMethods searchMethods;
	private SoftAssert softAssert;
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl =BaseTest.baseUrl;
		launchBrowser(wapUrl);
		searchMethods = new SearchMethods(driver);
	}
	
	@Test(description = "Verify search response and search result url")
	public void verifySearch(){
		softAssert = new SoftAssert();
		Map<String, String> testData = ExcelUtil.getTestDataRow("TestDataSheet", "VerifySearch", 1);
		List<String> searchedVals = Arrays.asList(testData.get("Text").split("\\s*,\\s*"));
		searchedVals.forEach(searchedVal->{
			WebBaseMethods.scrollToTop();
			Assert.assertNotNull(searchMethods.openSeachBar(), "Search bar not found");
			Assert.assertTrue(searchMethods.setSearchText(searchedVal),"Unable to input the searched value");
			WebBaseMethods.slowScroll(1);
			softAssert.assertTrue(searchMethods.checkVisibilityOfFirstElement(), "The search response on searching "+searchedVal+" is not coming within 5 seconds");
			List<String> searchUrl = searchMethods.getSearchResultUrl();
			Assert.assertTrue(searchUrl.size()>0,"On searching "+searchedVal+" no results are shown ");
			searchUrl.forEach(action -> {
				int response = HTTPResponse.checkResponseCode(action);
				softAssert.assertEquals(response, 200, "<br>- <a href=" + action + "> URL</a> in Search result on searching "+searchedVal+" is throwing " + response);
			});
			if(searchedVal.equalsIgnoreCase("Tata")){
				softAssert.assertTrue(searchUrl.stream().anyMatch(p->p.contains("companyid-12934.cms?companytype=dvr")),"Tata Motors DVR link is not present on searching "+searchedVal+" ,(expected identifier company type dvr) ");
			}
			Map<String, List<String>> searchResult = searchMethods.getSearchResultText();
			List<String> matchFailed = searchMethods.getFailSearchResult(searchedVal);

			softAssert.assertTrue(matchFailed.size() == 0, "On searching " + searchedVal + " the listed results "+matchFailed+" are not having the searched keyword");
			List<String> newsResult = searchMethods.verifyNewsUrl(searchedVal);

			softAssert.assertTrue(newsResult.size() == 0, "On searching " + searchedVal + " news result "+newsResult+" is not having the searched keyword in its body");
			for (Map.Entry<String, List<String>> entry : searchResult.entrySet()) {
				String key = entry.getKey().toString();
				List<String> lis = entry.getValue();
				if(key.equalsIgnoreCase("Companies") && searchedVal.equalsIgnoreCase("Tata")){
					softAssert.assertTrue(lis.stream().anyMatch(p->p.contains("DVR")),"Tata Motors DVR not shown in results");
				}
				softAssert.assertTrue(lis.size() > 0, "On searching " + searchedVal + " the section " + key + " is found blank");
			}
		});
		softAssert.assertAll();
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
