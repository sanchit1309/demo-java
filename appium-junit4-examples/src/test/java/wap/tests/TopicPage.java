package wap.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.HTTPResponse;
import common.utilities.WaitUtil;
import wap.pagemethods.SearchMethods;
import wap.pagemethods.TopicPageMethods;

public class TopicPage extends BaseTest {

	private String wapUrl;
	private SearchMethods searchMethods;
	private TopicPageMethods topicPageMethods;
	private SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl = BaseTest.baseUrl;
		launchBrowser(wapUrl);
		searchMethods = new SearchMethods(driver);
		topicPageMethods = new TopicPageMethods(driver);
	}
	
	@Test(description = "this test verifies the sections on the topic page", dataProvider = "keywords")
	public void verifyTopicPage(String keyword) {
		softAssert = new SoftAssert();
		driver.get(wapUrl + "/topic" + "/" + keyword);
		WaitUtil.waitForLoad(driver);
		String sections[] = { "All", "News", "Videos", "Photos" };
		List<String> sectionsName = Arrays.asList(sections);
		List<String> topHrefList = topicPageMethods.getTopSectionHref();
		List<String> errorLinksTopSection = topicPageMethods.getAllErrorLinks(topHrefList);
		softAssert.assertTrue(topHrefList.size() > 0,
				"The links under the top section should be more than 1 but found less for keyword: " + keyword);
		softAssert.assertTrue(errorLinksTopSection.size() == 0,
				"Few links under the top section are not having status code 200 for keyword " + keyword
						+ ". List of such links are: " + errorLinksTopSection);
		if (!keyword.equalsIgnoreCase("amitabh")) {
			String companyLink = topicPageMethods.getCompanyWidgetLink();
			softAssert.assertTrue(companyLink.length() > 0,
					"company links are not visible on the topic page for keyword: " + keyword);
			softAssert.assertTrue(topicPageMethods.checkStatusOfLinkIs200orNot(companyLink),
					"the company link on the topic page for keyword " + keyword
							+ " is not having the response code 200");
			String price = topicPageMethods.getCompanyWidgetCurrentPrice();
			softAssert.assertTrue(price.length() > 0 && !price.equalsIgnoreCase("NAN"),
					"Price shown " + price + " for the company on the company widget on the topic page for keyword: "
							+ keyword + " is not correct");
		}
		sectionsName.forEach(section -> {
			boolean flagAllTab = topicPageMethods.clickSectionTab(section);
			WaitUtil.sleep(5000);
			softAssert.assertTrue(flagAllTab,
					"The click " + section + " tab is not clickable on the topic page with keyword: " + keyword);
			if (flagAllTab) {
				List<String> hrefList = topicPageMethods.getActiveSectionHref();
				List<String> errorLinks = topicPageMethods.getAllErrorLinks(hrefList);
				softAssert.assertTrue(hrefList.size() >= 3, "The links under the section " + section
						+ " should be more than 3 but found less for keyword: " + keyword);
				softAssert.assertTrue(errorLinks.size() == 0, "Few links are not having status code 200 under section "
						+ section + " for keyword " + keyword + ". List of such links are: " + errorLinks);

			}

		});
		softAssert.assertAll();
	}

	@DataProvider
	public String[] keywords() {
		String[] keywords = { "tata", "rahul", "amitabh" };

		return keywords;

	}

	@Test(description = "Verifies topic page opens on entry keyword and hitting enter on Search bar", priority = 0, enabled = false)
	public void verifyTitlePage() {
		softAssert = new SoftAssert();
		Map<String, String> testData = ExcelUtil.getTestDataRow("TestDataSheet", "verifyTopic", 1);
		List<String> searchedVals = Arrays.asList(testData.get("Keywords").split("\\s*,\\s*"));
		searchedVals.forEach(searchedVal -> {
			searchMethods.openSeachBar();
			searchMethods.getSearchBar().clear();
			searchMethods.getSearchBar().sendKeys(searchedVal);
			searchMethods.getSearchBar().sendKeys(Keys.RETURN);
			String pageUrl = driver.getCurrentUrl();
			softAssert.assertTrue(pageUrl.contains("topic"),
					"Topic page didn't open instead <a href=" + pageUrl + ">" + pageUrl);
			String pageHeading = topicPageMethods.getTopicPagHeading();
			softAssert.assertTrue(pageHeading.equalsIgnoreCase(searchedVal),
					"Page Heading shoud be " + searchedVal + " instead found " + pageHeading);
			List<String> menuNames = topicPageMethods.getTopicMenuHeader();
			Assert.assertTrue(menuNames.size() > 0, "Topic page header not found on:" + driver.getCurrentUrl());
			List<String> menuListItems = new ArrayList<>();
			// List<String> topNews = new ArrayList<>();;
			for (String name : menuNames) {
				softAssert.assertTrue(topicPageMethods.hasTopStories(),
						"Topic " + searchedVal + " page has no top stories listed");
				topicPageMethods.openTopicSubMenu(name.toLowerCase());
				menuListItems = topicPageMethods.getSubMenuListItems(name.toLowerCase());
				if (menuListItems.size() > 0) {
					for (String href : menuListItems) {
						int linkResponseCode = HTTPResponse.checkResponseCode(href);
						softAssert.assertEquals(linkResponseCode, 200, "<br>- " + name + " link <a href=" + href + ">"
								+ href + "</a> is throwing " + linkResponseCode + " for topic " + searchedVal);
					}
				} else if (menuListItems.size() == 0)
					softAssert.assertTrue(
							topicPageMethods.getNoResultText(name).contains("There are no " + name + " for "),
							"No data displayed for " + name + " tab  on " + searchedVal
									+ " topic page and no appropriate message displayed for the same.");
			}
		});
		softAssert.assertAll();
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}
