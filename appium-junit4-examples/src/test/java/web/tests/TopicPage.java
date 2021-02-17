package web.tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.WaitUtil;
import web.pagemethods.TopicPageMethods;
import web.pagemethods.WebBaseMethods;

public class TopicPage extends BaseTest {
	private String baseUrl;
	private TopicPageMethods topicPageMethods;
	SoftAssert softAssert;

	@BeforeClass
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		launchBrowser();
		WebBaseMethods.scrollToBottom();
		topicPageMethods = new TopicPageMethods(driver);
	}

	@Test(description = "this test verifies the sections on the topic page", dataProvider = "keywords")
	public void verifyTopicPage(String keyword) {
		softAssert = new SoftAssert();
		driver.get(baseUrl + "/topic" + "/" + keyword);
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
			softAssert.assertTrue(price.length() > 0 && !price.equals("NAN"),
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
		String[] keywords = { "tata", "rahul","amitabh" };

		return keywords;

	}

}
