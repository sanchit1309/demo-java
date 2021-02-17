package com.web.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.VerificationUtil;
import common.utilities.reporting.ScreenShots;
import web.pagemethods.ArticleMethods;
import web.pagemethods.ETSharedMethods;
import web.pagemethods.HomePageMethods;
import web.pagemethods.NewHomePageMethods;
import web.pagemethods.WebBaseMethods;

public class NewHomePage extends BaseTest {

	private String baseUrl;
	private int countMostSections = 7;
	int counterNewsIndustry = 1;
	SoftAssert softAssert;
	NewHomePageMethods newHomePageMethods;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		launchBrowser(baseUrl);
		WebBaseMethods.scrollToBottom();
		WebBaseMethods.scrollToTop();
		newHomePageMethods = new NewHomePageMethods(driver);

	}

	@Test(description = "This test verifies the top stories link on ET Homepage")
	public void verifyTopStories() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods.getHrefList(newHomePageMethods.getTopStoriesListETHomepage());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		List<String> keywordsLinks = newHomePageMethods.getHrefList(newHomePageMethods.getTopNewsKeywordsLinks());
		List<String> keywordErrorLinks = newHomePageMethods.getAllErrorLinks(keywordsLinks);
		softAssert.assertTrue(hrefList.size() > 25, "The links shown under the top stories is less than 25");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the top stories on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertTrue(keywordErrorLinks.size() == 0,
				"Few keywords under the top headlines sections is redirecting to error page. List of the links: "
						+ keywordErrorLinks);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the ET prime widget articleshow links on the ET homepage")
	public void verifyETPrimeWidgetArticleshowLinks() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods.getHrefList(newHomePageMethods.getEtPrimeWidgetStoriesList());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() > 4,
				"The links under the ET prime widget should be more than or equal to 4 but found less");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the ET prime widget on ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the stories under the top news widget under prime widget on et homepage ")
	public void verifyTopNewsWidget() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods
				.getHrefList(newHomePageMethods.getTopNewsUnderPrimeWidgetArticleshowLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() > 10,
				"The links under the top news widget should be more than 10 but found less");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the Top news widget on ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the stories under the latest news widget under prime widget on et homepage ")
	public void verifyLatestNewsWidget() {
		softAssert = new SoftAssert();
		Assert.assertTrue(newHomePageMethods.clickLatestNewsTab(),
				"The latest news tab on the ET homepage is not clickable");
		List<String> hrefList = newHomePageMethods
				.getHrefList(newHomePageMethods.getLatestNewsUnderPrimeWidgetArticleshowLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);

		softAssert.assertTrue(hrefList.size() > 10,
				"The links under the latest news widget should be more than 10 but found less");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the Latest news widget on ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the stories under the Rise section on ET homepage")
	public void verifyRiseSection() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods.getHrefList(newHomePageMethods.getRiseSectionArticleshowLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		List<String> keywordsLinks = newHomePageMethods.getHrefList(newHomePageMethods.getRiseKeywordLinks());
		List<String> keywordErrorLinks = newHomePageMethods.getAllErrorLinks(keywordsLinks);
		softAssert.assertTrue(hrefList.size() > 10, "The links shown under the rise section is less than 10");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the rise section on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertTrue(keywordErrorLinks.size() == 0,
				"Few keywords under the rise section is redirecting to error page. List of the links: "
						+ keywordErrorLinks);
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the stories under the Wealth section on ET homepage")
	public void verifyWealthSection() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods.getHrefList(newHomePageMethods.getWealthSectionArticleshowLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		List<String> keywordsLinks = newHomePageMethods.getHrefList(newHomePageMethods.getWealthKeywordLinks());
		List<String> keywordErrorLinks = newHomePageMethods.getAllErrorLinks(keywordsLinks);
		softAssert.assertTrue(hrefList.size() > 10, "The links shown under the Wealth section is less than 10");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the Wealth section on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertTrue(keywordErrorLinks.size() == 0,
				"Few keywords under the Wealth section is redirecting to error page. List of the links: "
						+ keywordErrorLinks);
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the stories under the Mutual funds section on ET homepage")
	public void verifyMutualFundsSection() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods
				.getHrefList(newHomePageMethods.getMutualFundSectionArticleshowLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		List<String> keywordsLinks = newHomePageMethods.getHrefList(newHomePageMethods.getMutualFundKeywordLinks());
		List<String> keywordErrorLinks = newHomePageMethods.getAllErrorLinks(keywordsLinks);
		softAssert.assertTrue(hrefList.size() > 10, "The links shown under the Mutual funds section is less than 10");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the Mutual funds section on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertTrue(keywordErrorLinks.size() == 0,
				"Few keywords under the Mutual funds section is redirecting to error page. List of the links: "
						+ keywordErrorLinks);
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the information technology section on the ET homepage")
	public void verifyInformationTechnologySection() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods
				.getHrefList(newHomePageMethods.getInformationTechnologySectionArticleshowLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() > 10,
				"The links shown under the information technology section is less than 10");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the information technology section on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the Politics and Nation section on the ET homepage")
	public void verifyPoliticsNationSection() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods.getHrefList(newHomePageMethods.getPoliticsSectionArticleshowLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() > 5,
				"The links shown under the Politics and Nation section is less than 5");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the Politics and Nation section on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the Economy section on the ET homepage")
	public void verifyEconomySection() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods.getHrefList(newHomePageMethods.getEconomySectionArticleshowLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() > 5, "The links shown under the Economy section is less than 5");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the Economy section on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the International section on the ET homepage")
	public void verifyInternationalSection() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods
				.getHrefList(newHomePageMethods.getInternationalSectionArticleshowLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() >= 3, "The links shown under the International section is less than 3");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the International section on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the Jobs/Careers section on the ET homepage")
	public void verifyJobsAndCareerSection() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods.getHrefList(newHomePageMethods.getJobsSectionArticleshowLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() >= 3, "The links shown under the Jobs/Careers section is less than 3");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the Jobs/Careers section on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the podcast section on the ET homepage")
	public void verifyPodcastSection() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods.getHrefList(newHomePageMethods.getPodcastWidgetLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() >= 3, "The links shown under the Podcast section is less than 3");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the Podcast section on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();
	}

	@Test(description = "this test verifies the most read section on the ET homepage")
	public void verifyETMostReadWidget() {
		softAssert = new SoftAssert();
		Assert.assertTrue(newHomePageMethods.clickMostReadTab(), "The most read tab is not clickable");
		List<String> hrefList = newHomePageMethods.getHrefList(newHomePageMethods.getMostReadSectionArticleshowLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() >= 7, "The links shown under the most read section is less than 7");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the most read section on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();

	}

	@Test(description = "this test verifies the most shared section on the ET homepage")
	public void verifyETMostSharedWidget() {
		softAssert = new SoftAssert();
		Assert.assertTrue(newHomePageMethods.clickMostSharedTab(), "The most shared tab is not clickable");
		List<String> hrefList = newHomePageMethods
				.getHrefList(newHomePageMethods.getMostSharedSectionArticleshowLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() >= 7, "The links shown under the most shared section is less than 7");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the most shared section on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();

	}

	@Test(description = "this test verifies the most commented section on the ET homepage")
	public void verifyETMostCommentedWidget() {
		softAssert = new SoftAssert();
		Assert.assertTrue(newHomePageMethods.clickMostCommentedTab(), "The most commented tab is not clickable");
		List<String> hrefList = newHomePageMethods
				.getHrefList(newHomePageMethods.getMostCommentedSectionArticleshowLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() >= 7, "The links shown under the most commented section is less than 7");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the most ccommented section on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the not to be missed section on the ET homepage")
	public void verifyNotToBeMissedSection() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods.getHrefList(newHomePageMethods.getNotToBeMissedSectionLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() >= 15,
				"The links shown under the not to be missed section is less than 15");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the not to be missed section on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();
	}

	@Test(description = "this test verifies the panache section on the ET Homepage")
	public void verifyPanacheSection() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods.getHrefList(newHomePageMethods.getPanacheSectionArticleshowLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() >= 5, "The links shown under the panache section is less than 5");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the panache section on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the opinion section on the ET homepage")
	public void verifyOpinionSection() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods.getHrefList(newHomePageMethods.getOpinonSectionArticleshowLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() >= 5, "The links shown under the opinion section is less than 5");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the opinion section on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the Interview section on the ET homepage")
	public void verifyInterviewSection() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods
				.getHrefList(newHomePageMethods.getInterviewSectionArticleshowLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() >= 3, "The links shown under the interview section is less than 3");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the Interview section on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the Blogs section on the ET homepage")
	public void verifyBlogsSection() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods.getHrefList(newHomePageMethods.getBlogsSectionArticleshowLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() >= 3, "The links shown under the Blogs section is less than 3");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the Blogs section on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the Expert views section under the markets on the ET homepage")
	public void verifyExpertViewsSection() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods.getHrefList(newHomePageMethods.getExpertViewsSectionLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() >= 3,
				"The links shown under the Expert views section under the markets is less than 3");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the Expert views section under the markets on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the markets section on the ET homepage")
	public void verifyMarketsSection() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods.getHrefList(newHomePageMethods.getMarketsSectionArticleshowLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() >= 5, "The links shown under the markets section is less than 5");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the markets section on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the Et special section on the ET homepage")
	public void verifyETSpecialSection() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods
				.getHrefList(newHomePageMethods.getEtSpecailSectionArticleshowLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() >= 3, "The links shown under the ET special section is less than 3");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the ET specail section on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the Great Reads section on the ET homepage")
	public void verifyGreadReadsSection() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods
				.getHrefList(newHomePageMethods.getGreatReadsSectionArticleshowLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() >= 3, "The links shown under the Great Reads section is less than 3");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the Great Reads section on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the Top trending terms section on the ET homepage")
	public void verifyTopTrendingSectionInRHS() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods.getHrefList(newHomePageMethods.getTopTrendingTermLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() >= 10,
				"The links shown under the Top trending terms section is less than 10");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the Top trending terms section on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the video widget slider section on the ET homepage")
	public void verifyVideoWidgetSection() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods.getHrefList(newHomePageMethods.getVideoSliderWidgetLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() >= 10,
				"The links shown under the video widget slider section is less than 10");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the video widget slider section on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the Slideshow widget slider section on the ET homepage")
	public void verifySlideshowWidgetSection() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods.getHrefList(newHomePageMethods.getSlideshowSliderWidgetLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() >= 10,
				"The links shown under the slideshow widget slider section is less than 10");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the slideshow widget slider section on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the Editors pick widget section on the ET homepage")
	public void verifyEditorsPickWidgetSection() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods.getHrefList(newHomePageMethods.getEditorsPickWidgetLinks());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() >= 20,
				"The links shown under the Editors pick widget section is less than 20");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the Editors pick widget section on the ET homepage. List of the links: "
						+ dupLinks);

		List<String> beforeClickHeadlines = ETSharedMethods
				.getDisplayedElementsList(newHomePageMethods.getEditorsPickWidgetLinks());
		System.out.println(beforeClickHeadlines.size() + "---" + beforeClickHeadlines);
		newHomePageMethods.clickEditorsPickNonActivePaginations();

		List<String> afterClickHeadlines = ETSharedMethods
				.getDisplayedElementsList(newHomePageMethods.getEditorsPickWidgetLinks());
		System.out.println(afterClickHeadlines.size() + "---" + afterClickHeadlines);

		softAssert.assertFalse(VerificationUtil.areListsEqual(beforeClickHeadlines, afterClickHeadlines),
				"Stories did not change after clicking next on the editors pick section");

		softAssert.assertAll();

	}

	@Test(description = "This test verifies the Panache Videoshow Slideshow slider widget section on the ET homepage")
	public void verifyPanacheVideoshowSlideshowSliderWidgetSection() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods
				.getHrefList(newHomePageMethods.getPanacheSlideshowVideoshowSliderWidget());
		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
		softAssert.assertTrue(hrefList.size() >= 18,
				"The links shown under the Panache Videoshow Slideshow slider widget section is less than 18");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(dupLinks.size() == 0,
				"The duplicate links are present under the Panache Videoshow Slideshow slider widget section on the ET homepage. List of the links: "
						+ dupLinks);
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the different sections of the news by industry widget on the ET homepage")
	public void verifyNewsByIndustryWidgetSections() {
		softAssert = new SoftAssert();
		try {
			List<WebElement> newsByIndustryTabs = newHomePageMethods.getNewsByIndustryWidgetTabs();
			newsByIndustryTabs.forEach(tabs -> {
				WebBaseMethods.clickElementUsingJSE(tabs);
				String tabName = tabs.getText();
				System.out.println(tabName);
				List<String> hrefList = newHomePageMethods
						.getHrefList(newHomePageMethods.getNewsByIndustryWidgetActiveListArticleshowLinks());
				List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
				List<String> dupLinks = newHomePageMethods.checkIfListIsUnique(hrefList);
				softAssert.assertTrue(hrefList.size() >= 6,
						"The links shown under the " + tabName + " tab under news by industry section is less than 6");
				softAssert.assertTrue(errorLinks.size() == 0,
						"Few links are not having status code 200. List of such links are: " + errorLinks);
				softAssert.assertTrue(dupLinks.size() == 0, "The duplicate links are present under the " + tabName
						+ " tab under news by industry section on the ET homepage. List of the links: " + dupLinks);

			});
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "Exception occured during the execution of test case");

		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies all the section headings on the ET homepage")
	public void verifySectionsHeadingLinks() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods.getHrefList(newHomePageMethods.getSectionH2HeadingLinks());
		List<String> subsectionHrefList = newHomePageMethods
				.getHrefList(newHomePageMethods.getSubsectionH4HeadingLinks());

		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		List<String> subSectionErrorLinks = newHomePageMethods.getAllErrorLinks(subsectionHrefList);
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few Sections heading links are not having status code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(subSectionErrorLinks.size() == 0,
				"Few sub section headings links are not having status code 200. List of such links are: " + subSectionErrorLinks);

		softAssert.assertAll();

	}

	@Test(description = "This test verifies all the section More links on the ET homepage")
	public void verifySectionsMoreLinks() {
		softAssert = new SoftAssert();
		List<String> hrefList = newHomePageMethods.getHrefList(newHomePageMethods.getSectionMoreLinks());

		List<String> errorLinks = newHomePageMethods.getAllErrorLinks(hrefList);
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few sections more links are not having status code 200. List of such links are: " + errorLinks);

		softAssert.assertAll();

	}

}
