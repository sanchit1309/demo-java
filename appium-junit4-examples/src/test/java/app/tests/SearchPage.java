package app.tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import app.common.AndroidCommonMethods;
import app.pagemethods.AppListingPageMethods;
import app.pagemethods.HeaderPageMethods;
import app.pagemethods.MenuPageMethods;
import app.pagemethods.SearchPageMethods;
import app.pagemethods.StoryPageMethods;
import common.launchsetup.BaseTest;
import common.urlRepo.AppFeeds;
import common.utilities.ApiHelper;
import common.utilities.ExcelUtil;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;

public class SearchPage extends BaseTest {

	private AppiumDriver<?> appDriver;
	SearchPageMethods searchPageMethods;
	HeaderPageMethods headerPageMethods;
	MenuPageMethods menuPageMethods;
	AppListingPageMethods appListingPageMethods;
	StoryPageMethods storyPageMethods;
	AndroidCommonMethods androidCommonMethods;
	SoftAssert softAssert;
	private Map<String, String> testData = new LinkedHashMap<>();

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		launchBrowser();
		appDriver = iAppCommonMethods.getDriver();
		storyPageMethods = new StoryPageMethods(appDriver);
		searchPageMethods = new SearchPageMethods(appDriver);
		headerPageMethods = new HeaderPageMethods(appDriver);
		menuPageMethods = new MenuPageMethods(appDriver);
		appListingPageMethods = new AppListingPageMethods(appDriver);
		//androidCommonMethods = new AndroidCommonMethods((AndroidDriver<?>) appDriver);
		//headerPageMethods.clickMenuIcon();
		//menuPageMethods.scrollToMenuOptionClick("Search", true);
		headerPageMethods.clickSearchIcon();
	}

	@Test(description = "Verifies search result", dataProvider = "Search Data")
	public void verifySearchResult(String keyword, String tabs) {
		softAssert = new SoftAssert();
		List<String> feedSearchResult = new LinkedList<>();
		List<String> appSearchResult = new LinkedList<>();
		String[] headerTabs = tabs.split(",");

		if (searchPageMethods.enterValueInSearchBox(keyword) == true) {
			for (String header : headerTabs) {
				// feedSearchResult.addAll(getFeedData(keyword,header));
				if (BaseTest.platform.equals("iosApp") && header.equals("Mutual Fund")) {
					header = "Mutual Funds";
				}
				softAssert.assertTrue(searchPageMethods.isHeaderPresent(header, 2),
						"<br>Header missing on the app :: " + header);
			}
			appSearchResult = searchPageMethods.getSearchResults();
			// softAssert.assertTrue(appSearchResult.equals(feedSearchResult),
			// "<br>Data different from feed :: " +
			// CollectionUtils.disjunction(appSearchResult,feedSearchResult));
			softAssert.assertTrue(appSearchResult.size() > 0, "No result shown  in app");
			// Verify detail Pages
		} else {
			softAssert.assertTrue(false, "<br>Unable to enter value " + keyword);

		}
		softAssert.assertAll();
	}

	@Test(description = "Verified search result for news section", dataProvider = "searchData",enabled=false)
	public void verifySearchNewsResult(String keyword, String company, String commodity, String mutualFunds,
			String forex, String NewsKeyword, String TopicLink) {
		softAssert = new SoftAssert();
		List<String> actualRelatedTopics = new LinkedList<>();
		List<String> expectedRelatedTopics = new LinkedList<>();

		expectedRelatedTopics = Arrays.asList(TopicLink.split("\\s*,\\s*"));

		if (searchPageMethods.enterValueInSearchBox(NewsKeyword) == true) {

			softAssert.assertTrue(searchPageMethods.clickNewsTab(), "Unable to click on News Tab");
			softAssert.assertTrue(searchPageMethods.clickOnFirstNews(), "Unable to click on first News Item");
			WaitUtil.sleep(2000);
			;
			softAssert.assertTrue(storyPageMethods.getHeadline(), "Headline not present on Story Page");
			searchPageMethods.scrollToRelatedCompanies();
			actualRelatedTopics = storyPageMethods.gettopicsName();

			softAssert.assertTrue(VerificationUtil.validateContentOfList(expectedRelatedTopics, actualRelatedTopics),
					"On Searching " + NewsKeyword + " company tab doesnot have expected company Name");

			// actucalNews = searchPageMethods
		}
	}

	@Test(description = "Verifies universal Search Result", dataProvider = "tabbedSearch")
	public void verifySearchResults(String keyword, String company, String mutualFunds, String commodity,
			String forex) {
		softAssert = new SoftAssert();
		List<String> expectedCompanyNames = new LinkedList<>();
		List<String> expectedmutualNames = new LinkedList<>();
		List<String> expectedCommodityNames = new LinkedList<>();
		List<String> expectedforexNames = new LinkedList<>();
		if (!company.equals(null)) {
			expectedCompanyNames = Arrays.asList(company.split("\\s*,\\s*"));
			System.out.println("Expected COmpany Names:" + expectedCompanyNames);
		}
		if (!mutualFunds.equals(null)) {
			System.out.println("mutualFunds:" + mutualFunds);
			expectedmutualNames = Arrays.asList(mutualFunds.split("\\s*,\\s*"));
			System.out.println("Expected MF Names:" + expectedmutualNames);
		}
		if (!commodity.equals(null) && !commodity.equals("-")) {
			expectedCommodityNames = Arrays.asList(commodity.split("\\s*,\\s*"));
		}
		if (!forex.equals(null) && !forex.equals("-")) {
			expectedforexNames = Arrays.asList(forex.split("\\s*,\\s*"));
		}

		if (searchPageMethods.enterValueInSearchBox(keyword)) {
			softAssert.assertTrue(searchPageMethods.clickCompanyTab(), "Unable to click on company Tab");
			List<String> actualCompanyNames = searchPageMethods.getcompanyName();
			if (expectedCompanyNames.size() != 0) {
				softAssert.assertTrue(VerificationUtil.listActualInExpected(actualCompanyNames, expectedCompanyNames),
						"On searching for " + keyword + " actual company names " + actualCompanyNames
								+ " is not matching with the expected company names " + expectedCompanyNames);

			}
			/*
			 * List<String> companyLtp = searchPageMethods.getCompanyLtp();
			 * softAssert.assertEquals(actualCompanyNames.size(),
			 * companyLtp.size(), "Ltp prices missing for" + keyword +
			 * "Search");
			 */
			softAssert.assertTrue(searchPageMethods.clickMutualFundsTab(), "Unable to click on mutual funds  Tab");
			List<String> actualmutualNames = searchPageMethods.getmutulFundsName();
			// List<String> mutalFundLtp = searchPageMethods.getMutualNaV();
			if (expectedmutualNames.size() != 0) {
				softAssert.assertTrue(VerificationUtil.listActualInExpected(actualmutualNames, expectedmutualNames),
						"On searching for " + keyword + " actual mutual fund names " + actualmutualNames
								+ " is not matching with the expected mutual fund names " + expectedmutualNames);

			}
			/*
			 * softAssert.assertEquals(actualmutualNames.size(),
			 * mutalFundLtp.size(), "Ltp prices missing for" + keyword +
			 * "Search");
			 */
			if (expectedCommodityNames.size() > 0) {
				softAssert.assertTrue(searchPageMethods.clickCommodityTab(), "Unable to click on commodity Tab");
				List<String> actualCommodityNames = searchPageMethods.getCommodityName();
				// List<String> commodityLtp =
				// searchPageMethods.getcommodityLtp();
				if (expectedCommodityNames.size() != 0) {
					softAssert.assertTrue(
							VerificationUtil.listActualInExpected(actualCommodityNames, expectedCommodityNames),
							"On searching for " + keyword + " actual commodity names " + actualCommodityNames
									+ " is not matching with the expected commodity names " + expectedCommodityNames);
				}
			}

			/*
			 * softAssert.assertEquals(actualCommodityNames.size(),
			 * commodityLtp.size(), "Ltp prices missing for" + keyword +
			 * "Search");
			 */
			if (expectedforexNames.size() > 0) {
				softAssert.assertTrue(searchPageMethods.clickForexTab(), "Unable to click on forex  Tab");
				List<String> actualforexNames = searchPageMethods.getForexName();
				if (expectedforexNames.size() != 0) {
					softAssert.assertTrue(VerificationUtil.listActualInExpected(actualforexNames, expectedforexNames),
							"On searching for " + keyword + " actual forex names " + actualforexNames
									+ " is not matching with the expected forex names");

				}
			}
		} else
			softAssert.assertTrue(false, "Unable to enter value " + keyword + " in search box");
		softAssert.assertAll();

	}

	@DataProvider(name = "Search Data")
	private Object[][] getData() {
		// int testDataCount = (int)
		// Double.parseDouble(ExcelUtil.getTestDataRow("TestDataSheet",
		// "VerifySearch", 1).get("testDataCount"));
		int testDataCount = 3;
		Object[][] obj = new Object[testDataCount][2];
		for (int i = 1; i <= testDataCount; i++) {
			testData = ExcelUtil.getTestDataRow("TestDataSheet", "VerifySearch", i);
			obj[i - 1][0] = testData.get("Keyword");
			obj[i - 1][1] = testData.get("Tabs");
		}
		return obj;
	}

	private List<String> getFeedData(String keyword, String header) {
		String api = AppFeeds.searchFeed + "&query=" + keyword;
		List<String> resultList = ApiHelper.getValueFromAPI(api, "nm", "", "", "Item",
				header.toLowerCase().replaceAll(" ", ""));
		return resultList;
	}

	@DataProvider(name = "tabbedSearch")
	public Object[][] getSearchData() {
		Object[][] searchData = new Object[1][5];
		int i = 1;
		while (i < 2) {
			Map<String, String> testData = ExcelUtil.getTestDataRow("TestDataSheet", "verifyTabbedSearch", i);
			searchData[i - 1][0] = testData.get("Keyword");
			searchData[i - 1][1] = testData.get("Company");
			searchData[i - 1][2] = testData.get("MutualFund");
			searchData[i - 1][3] = testData.get("Commodity");
			searchData[i - 1][4] = testData.get("Forex");
			// searchData[i - 1][5] = testData.get("NewsKeyword");
			// searchData[i - 1][6] = testData.get("TopicLink");
			i++;

		}
		System.out.println(searchData);
		return searchData;
	}

	@AfterClass(alwaysRun = true)
	public void quit() throws Exception {
		appDriver.quit();
	}

}