package com.web.tests;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import web.pagemethods.CommonL1L2PagesMethods;
import web.pagemethods.MutualFundPageMethods;
import web.pagemethods.WebBaseMethods;

public class MutualFundsPage extends BaseTest {
	private String baseUrl;
	CommonL1L2PagesMethods commonL1L2PagesMethods;
	MutualFundPageMethods mutualFundPageMethods;
	SoftAssert softAssert;

	@BeforeClass
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl + "/mutual-funds";
		launchBrowser(baseUrl);
		mutualFundPageMethods = new MutualFundPageMethods(driver);
		commonL1L2PagesMethods = new CommonL1L2PagesMethods(driver);
		WebBaseMethods.scrollToBottom();

	}

	@Test(description = "This test verifies the Top stories on the mutual funds page")
	public void verifyTopStoriesOnMutualFundsPage() {
		softAssert = new SoftAssert();
		try {
			List<String> topStoriesOnMfHref = mutualFundPageMethods.getTopStoriesOnMutualFundsPage();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(topStoriesOnMfHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(topStoriesOnMfHref);
			softAssert.assertTrue(topStoriesOnMfHref.size() >= 7,
					"The number of terms under the top stories should be more than equal to 7 but found: "
							+ topStoriesOnMfHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the top stories. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the top stories " + dupLinks);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the keywords on the mutual funds page under the top stories")
	public void verifyKeywordsOnMutualFundsPage() {
		softAssert = new SoftAssert();
		try {
			List<String> listHref = mutualFundPageMethods.getMutualFundTopKeywords();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(listHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(listHref);
			softAssert.assertTrue(listHref.size() >= 5,
					"The number of terms under the keywords should be more than equal to 5 but found: "
							+ listHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the keywords. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0, "Duplicate links are present under the keywords " + dupLinks);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the mutual funds learn section")
	public void verifyMutualFundsLearnSection() {
		softAssert = new SoftAssert();
		String sectionName = "Mutual Funds Learn  ";
		try {

			List<String> listHref = mutualFundPageMethods.getMutualFundsLearnSectionLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(listHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(listHref);
			softAssert.assertTrue(listHref.size() >= 7,
					"The number of links under the mutual fund learn section should be more than equal to 7 but found: "
							+ listHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the mutual fund learn section. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the mutual fund learn section " + dupLinks);

			boolean sectionHeadingFlag = commonL1L2PagesMethods
					.verifyTheSectionHeadingLink(mutualFundPageMethods.getSectionHeadingLink(sectionName), "learn");
			softAssert.assertTrue(sectionHeadingFlag, "The section Heading link is not of the same section ");
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the mutual funds news analysis section")
	public void verifyMutualFundsNewsAnalysisSection() {
		softAssert = new SoftAssert();
		String sectionName = "Mutual Funds News Analysis";
		try {
			List<String> listHref = mutualFundPageMethods.getMutualFundsAnalysisSectionLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(listHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(listHref);
			softAssert.assertTrue(listHref.size() >= 7,
					"The number of links under the mutual fund news analysis section should be more than equal to 7 but found: "
							+ listHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the mutual fund news analysis. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the mutual fund news analysis " + dupLinks);
			
			boolean sectionHeadingFlag = commonL1L2PagesMethods
					.verifyTheSectionHeadingLink(mutualFundPageMethods.getSectionHeadingLink(sectionName), "analysis");
			softAssert.assertTrue(sectionHeadingFlag, "The section Heading link is not of the same section ");
	

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the mutual funds news  section")
	public void verifyMutualFundsNewsSection() {
		softAssert = new SoftAssert();
		String sectionName = "Mutual Funds News";
		try {
			List<String> listHref = mutualFundPageMethods.getMutualFundsNewsSectionLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(listHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(listHref);
			softAssert.assertTrue(listHref.size() >= 7,
					"The number of links under the mutual fund news section should be more than equal to 7 but found: "
							+ listHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the mutual fund news . List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the mutual fund news " + dupLinks);
			boolean sectionHeadingFlag = commonL1L2PagesMethods
					.verifyTheSectionHeadingLink(mutualFundPageMethods.getSectionHeadingLink(sectionName), "mf-news");
			softAssert.assertTrue(sectionHeadingFlag, "The section Heading link is not of the same section ");
	
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the top searches section on the mutual funds page")
	public void verifyTopSearchesSection() {
		softAssert = new SoftAssert();
		try {
			List<String> listHref = commonL1L2PagesMethods.getTopSearchesLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(listHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(listHref);
			softAssert.assertTrue(listHref.size() >= 5,
					"The number of terms under the top searches should be more than equal to 5 but found: "
							+ listHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the top searches. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the top searches " + dupLinks);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the top trending widget on the Mutual funds page")
	public void verifyTopTrendingWidgetInRhs() {
		softAssert = new SoftAssert();
		try {

			List<String> topTrendingWidgetLinks = commonL1L2PagesMethods.getTopTrendingLinksHref();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(topTrendingWidgetLinks);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(topTrendingWidgetLinks);
			softAssert.assertTrue(topTrendingWidgetLinks.size() >= 5,
					"The number of terms under the top trending widget should be more than equal to 5 but found: "
							+ topTrendingWidgetLinks.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the top trending terms widget. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the top trending widget in RHS: " + dupLinks);
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the mutual fund news and updates in rhs ")
	public void verifyMutualFundNewsAndUpdatesSection() {
		softAssert = new SoftAssert();
		try {

			List<String> mutualFundNewsAndUpdatesLinks = mutualFundPageMethods.getMutualFundNewsAndUpdates();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(mutualFundNewsAndUpdatesLinks);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(mutualFundNewsAndUpdatesLinks);
			softAssert.assertTrue(mutualFundNewsAndUpdatesLinks.size() >= 10,
					"The number of links under the mutual fund news and updates section should be more than equal to 10 but found: "
							+ mutualFundNewsAndUpdatesLinks.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the mutual fund news and updates section. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the mutual fund news and updates section in RHS: " + dupLinks);
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}
}
