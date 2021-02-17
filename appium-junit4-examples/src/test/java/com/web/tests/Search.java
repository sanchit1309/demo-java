package com.web.tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.ExcelUtil;
import common.utilities.HTTPResponse;
import common.utilities.WaitUtil;
import web.pagemethods.HeaderPageMethods;
import web.pagemethods.SearchMethods;
import web.pagemethods.WebBaseMethods;

public class Search extends BaseTest {
	private String baseUrl;
	SearchMethods searchmethods;
	HeaderPageMethods headerPageMethods;
	SoftAssert softAssert;

	@BeforeClass
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		launchBrowser(baseUrl);
		searchmethods = new SearchMethods(driver);
		headerPageMethods = new HeaderPageMethods(driver);
	}

	@Test(description = "This test verifies search response and search result url")
	public void verifySearch() {
		softAssert = new SoftAssert();
		Map<String, String> testData = ExcelUtil.getTestDataRow("TestDataSheet", "VerifySearch", 1);
		List<String> searchedVal = Arrays.asList(testData.get("Text").split("\\s*,\\s*"));
		searchedVal.forEach(keyword -> {
			if (!searchmethods.isSearchBarDisplayed()) {
				Assert.assertTrue(headerPageMethods.clickOnSearchButton(),
						"User is not able to click on the search button");
			}
			softAssert.assertTrue(searchmethods.getSearchBarType(keyword), "Hello");
			WaitUtil.sleep(3000);
			// WebBaseMethods.scrollToBottom();

			softAssert.assertTrue(searchmethods.checkPresenceOfFirstElement(),
					"<br>The search response on searching " + keyword + " is not coming within 5 seconds");

			List<String> searchUrl = searchmethods.getSearchResultUrl();
			softAssert.assertTrue(searchUrl.size() > 0, "On searching " + keyword + " no results are shown ");
			if (searchUrl.size() > 0) {
				searchUrl.forEach(action -> {

					int response = HTTPResponse.checkResponseCode(action);
					softAssert.assertEquals(response, 200, "<br>- <a href=" + action
							+ "> URL</a> in Search result on searching " + keyword + " is throwing " + response);

				});
				if (keyword.equalsIgnoreCase("Tata")) {
					softAssert.assertTrue(
							searchUrl.stream().anyMatch(p -> p.contains("companyid-12934.cms?companytype=dvr")),
							"Tata Motors DVR link is not present on searching " + keyword
									+ " ,(expected identifier company type dvr) ");
					softAssert.assertTrue(
							searchUrl.stream().anyMatch(p -> p.contains("companyid-12902.cms?companytype=pp")),
							"Tata Steel ltd(Partly Paid) link is not present on searching " + keyword
									+ " ,(expected identifier company type partly paid) ");
				}
				Map<String, List<String>> searchResult = searchmethods.getSearchResultText();
				List<String> matchFailed = searchmethods.getFailSearchResult(keyword);

				softAssert.assertTrue(matchFailed.size() == 0, "On searching " + keyword + " the listed results "
						+ matchFailed + " are not having the searched keyword");
				List<String> newsResult = searchmethods.verifyNewsUrl(keyword);

				softAssert.assertTrue(newsResult.size() == 0, "On searching " + keyword + " news result " + newsResult
						+ " is not having the searched keyword in its body");
				for (Map.Entry<String, List<String>> entry : searchResult.entrySet()) {
					String key = entry.getKey().toString();
					List<String> lis = entry.getValue();
					if (key.equalsIgnoreCase("Companies") && keyword.equalsIgnoreCase("Tata")) {
						softAssert.assertTrue(lis.stream().anyMatch(p -> p.contains("DVR")),
								"Tata Motors DVR not shown in results");
					}
					softAssert.assertTrue(lis.size() > 0,
							"On searching " + keyword + " the section " + key + " is found blank");
				}
			}
		});
		softAssert.assertAll();
	}

}
