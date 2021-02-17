package com.web.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.reporting.ScreenShots;
import web.pagemethods.ArticleMethods;
import web.pagemethods.HomePageMethods;
import web.pagemethods.WebBaseMethods;

public class ArticleshowHtmlTagCheck extends BaseTest {
	private String baseUrl;

	HomePageMethods homePageMethods;
	ArticleMethods articleMethods;
	ScreenShots screenShots;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		screenShots = new ScreenShots();
		launchBrowser(baseUrl);
		articleMethods = new ArticleMethods(driver);
		homePageMethods = new HomePageMethods(driver);
		WebBaseMethods.scrollToBottom();
		WebBaseMethods.scrollToTop();

	}

	@Test(description = "This test verifies the content of all the articleshow on the ET homepage")
	public void verifyHtmlTagOnArticleshow() {
		softAssert = new SoftAssert();
		HashMap<String, List<String>> failedUrlMap = articleMethods
				.checkLinksForHtmlTag(homePageMethods.getArticleshowLinksFromHomePage().subList(0, 100));
		System.out.println(failedUrlMap);
		if (failedUrlMap.size() > 0) {
			failedUrlMap.forEach((url, list) -> {
				if (list.contains("The articleshow body content is not found on artilceshow")) {
					softAssert.assertTrue(false, "The articleshow body content is not found on artilceshow " + url);
				} else if (list.size() > 0) {

					softAssert.assertTrue(false, "The Html tags " + htmlAsPlainText(list)
							+ " are found on the body content of articleshow " + url);
				}

			});

		}
		softAssert.assertAll();
	}

	public List<String> htmlAsPlainText(List<String> li) {
		List<String> list = new LinkedList<>();
		List<String> listOfTags = li;
		listOfTags.forEach(tag -> {
			list.add(tag.replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
		});
		return list;
	}
}
