package com.web.tests;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.WaitUtil;
import web.pagemethods.AuthorPageMethods;

public class AuthorPage extends BaseTest {
	private String baseUrl;
	private AuthorPageMethods authorPageMethods;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		launchBrowser(baseUrl);
		authorPageMethods = new AuthorPageMethods(driver);

	}

	@Test(description = "this test verifies the author page", dataProvider = "authors")
	public void verifyAuthorPage(String author, String authorType) {
		softAssert = new SoftAssert();
		driver.get(baseUrl + "/etreporter" + "/" + author);
		WaitUtil.waitForLoad(driver);
		List<String> topArticleList = authorPageMethods.getAuthorArticlesHref();
		List<String> errorLinksArticleSection = authorPageMethods.getAllErrorLinks(topArticleList);
		List<String> dupArticles = authorPageMethods.checkIfListIsUnique(topArticleList);
		softAssert.assertTrue(topArticleList.size() > 0,
				"The article under the Stories by author section should be more than 3 but found less for author: "
						+ author);
		softAssert.assertTrue(errorLinksArticleSection.size() == 0,
				"Few links under the Stories by author section are not having status code 200 for author " + author
						+ ". List of such links are: " + errorLinksArticleSection);
		softAssert.assertTrue(dupArticles.size() == 0,
				"Few links under the Stories by author section are repeating for author " + author
						+ ". List of such links are: " + dupArticles);
		String authorName = authorPageMethods.getAuthorName();
		softAssert.assertTrue(authorName.length() > 0, "Author name is not shown for the the author page: " + author);
		String authorDesc = authorPageMethods.getAuthorDescription();
		softAssert.assertTrue(authorDesc.length() > 0, "Author bio is not shown for the author: " + author);
		int days = 0;
		if (authorType.equalsIgnoreCase("prime")) {
			days = 10;
		} else {
			days = 5;
		}
		LinkedHashMap<String, String> oldArticles = authorPageMethods.getOldArticlesShownOnThePage(days);
		softAssert.assertTrue(oldArticles.size() == 0, "Few recent articles are not the latest Articles for author: "
				+ author + " Article details" + oldArticles);
		softAssert.assertAll();
	}

	@DataProvider
	public String[][] authors() {
		String[][] keywords = { { "author-writankar-mukherjee-8447.cms", "prime" },
				{ "author-sidhartha-shukla-479252951.cms", "prime" },
				{ "author-maulik-vyas-479252993.cms", "non-prime" } };

		return keywords;

	}
}
