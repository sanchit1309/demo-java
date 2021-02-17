package com.web.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import web.pagemethods.CommonL1L2PagesMethods;
import web.pagemethods.JobPageMethods;
import web.pagemethods.WebBaseMethods;

public class JobPage extends BaseTest {
	private String baseUrl;
	CommonL1L2PagesMethods commonL1L2PagesMethods;
	JobPageMethods jobPageMethods;
	SoftAssert softAssert;

	@BeforeClass
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl + "/jobs";
		launchBrowser(baseUrl);
		jobPageMethods = new JobPageMethods(driver);
		commonL1L2PagesMethods = new CommonL1L2PagesMethods(driver);
		WebBaseMethods.scrollToBottom();

	}

	@Test(description = "This test verifies the Jobs ET article List")
	public void verifyJobsETArticleList() {
		softAssert = new SoftAssert();

		try {

			List<String> listHref = jobPageMethods.getJobETArticleListLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(listHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(listHref);
			softAssert.assertTrue(listHref.size() >= 20,
					"The number of links under the jobs section should be more than equal to 20 but found: "
							+ listHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the jobs section. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the jobs section " + dupLinks);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the WorkLIfe WIdget List")
	public void verifyWorkLifeWidgetLinks() {
		softAssert = new SoftAssert();

		try {

			List<String> listHref = jobPageMethods.getWorklifeWidgetLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(listHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(listHref);
			softAssert.assertTrue(listHref.size() >= 6,
					"The number of links under the worklife widget should be more than equal to 6 but found: "
							+ listHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the worklife widget section. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the worklife widget section " + dupLinks);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the Prime Articles under the prime tab on Jobs page", priority = 10)
	public void verifyETPrimeJobArticles() {
		softAssert = new SoftAssert();

		try {
			Assert.assertTrue(jobPageMethods.clickThePrimeSectionTab(),
					"The prime tab on the job page is not clickable");
			List<String> listHref = jobPageMethods.getJobETPrimeArticleListLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(listHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(listHref);
			softAssert.assertTrue(listHref.size() >= 20,
					"The number of links under the ET prime section on jobs page should be more than equal to 20 but found: "
							+ listHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the the ET prime section on jobs page. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the the ET prime section on jobs page " + dupLinks);

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the top trending widget on the jobs page")
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

	@Test(description = "This test verifies the stories count in the Not to be missed section")
	public void verifyNotToBeMissedSection() {
		try {
			softAssert = new SoftAssert();

			List<String> notToBeMissedSectionLinks = commonL1L2PagesMethods.getNotToBeMissedSectionLInks();
			System.out.println(notToBeMissedSectionLinks.size());
			softAssert.assertTrue(notToBeMissedSectionLinks.size() > 10,
					"Not to be missed section on the Job page is shown blank or having links less than 10");

		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the video widget on the job page")
	public void verifyVideoWidget() {
		softAssert = new SoftAssert();
		List<WebElement> videoWidgetTabs = commonL1L2PagesMethods.listOfVideoWidgetTabs();
		try {
			videoWidgetTabs.forEach(tab -> {
				System.out.println(tab.getText());
				WebBaseMethods.clickElementUsingJSE(tab);
				List<String> activeSectionVideoLinks = commonL1L2PagesMethods.getVideoWidgetActiveLinksHref();
				List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(activeSectionVideoLinks);
				List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(activeSectionVideoLinks);
				softAssert.assertTrue(activeSectionVideoLinks.size() >= 3,
						"The number of videos shown under the video widget under the tab " + tab.getText()
								+ " is not more than or equal to 3");
				softAssert.assertTrue(errorLinks.size() == 0, "Few links are not having response code 200 under tab "
						+ tab.getText() + ". List of such links are: " + errorLinks);
				softAssert.assertTrue(dupLinks.size() == 0,
						"Duplicate links are present under the video widget for tab: " + tab.getText()
								+ ".List of links: " + dupLinks);
			});
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}

		softAssert.assertAll();

	}
}
