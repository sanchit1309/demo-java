package web.tests;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import web.pagemethods.CommonL1L2PagesMethods;
import web.pagemethods.OpinionPageMethods;
import web.pagemethods.WebBaseMethods;

public class OpinionPage extends BaseTest {
	private String baseUrl;
	CommonL1L2PagesMethods commonL1L2PagesMethods;
	OpinionPageMethods opinionPageMethods;
	SoftAssert softAssert;

	@BeforeClass
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl + "/opinion";
		launchBrowser(baseUrl);
		opinionPageMethods = new OpinionPageMethods(driver);
		commonL1L2PagesMethods = new CommonL1L2PagesMethods(driver);
		WebBaseMethods.scrollToBottom();

	}

	@Test(description = "This test verifies the ET editorial section")
	public void verifyETEditorialSection() {
		softAssert = new SoftAssert();
		String sectionName = "ET EDITORIAL";
		try {

			List<String> listHref = opinionPageMethods.getEditorialSectionLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(listHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(listHref);
			softAssert.assertTrue(listHref.size() >= 5,
					"The number of links under the ET Editorial section should be more than equal to 5 but found: "
							+ listHref.size());
			softAssert.assertTrue(errorLinks.size() == 0,
					"Few links are not having response code 200 under the ET Editorial section. List of such links are: "
							+ errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the ET Editorial section " + dupLinks);

			boolean sectionHeadingFlag = commonL1L2PagesMethods.verifyTheSectionHeadingLink(
					opinionPageMethods.getSectionHeadingLink(sectionName), "et-editorials");
			softAssert.assertTrue(sectionHeadingFlag, "The section Heading link is not of the same section ");
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the ET commentary section")
	public void verifyETCommentarySection() {
		softAssert = new SoftAssert();
		String sectionName = "ET COMMENTARY";
		try {

			List<String> listHref = opinionPageMethods.getEtCommentarySectionLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(listHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(listHref);
			softAssert.assertTrue(listHref.size() >= 5, "The number of links under the " + sectionName
					+ " section should be more than equal to 5 but found: " + listHref.size());
			softAssert.assertTrue(errorLinks.size() == 0, "Few links are not having response code 200 under the "
					+ sectionName + " section. List of such links are: " + errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the " + sectionName + " section " + dupLinks);

			boolean sectionHeadingFlag = commonL1L2PagesMethods.verifyTheSectionHeadingLink(
					opinionPageMethods.getSectionHeadingLink(sectionName), "et-commentary");
			softAssert.assertTrue(sectionHeadingFlag, "The section Heading link is not of the same section ");
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the Interviews section")
	public void verifyInterviewsSection() {
		softAssert = new SoftAssert();
		String sectionName = "INTERVIEWS";
		try {

			List<String> listHref = opinionPageMethods.getEtInterviewSectionLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(listHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(listHref);
			softAssert.assertTrue(listHref.size() >= 5, "The number of links under the " + sectionName
					+ " section should be more than equal to 5 but found: " + listHref.size());
			softAssert.assertTrue(errorLinks.size() == 0, "Few links are not having response code 200 under the "
					+ sectionName + " section. List of such links are: " + errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the " + sectionName + " section " + dupLinks);

			boolean sectionHeadingFlag = commonL1L2PagesMethods
					.verifyTheSectionHeadingLink(opinionPageMethods.getSectionHeadingLink(sectionName), "interviews");
			softAssert.assertTrue(sectionHeadingFlag, "The section Heading link is not of the same section ");
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the Datawise section")
	public void verifyDatawiseSection() {
		softAssert = new SoftAssert();
		String sectionName = "DATA WISE";
		try {

			List<String> listHref = opinionPageMethods.getDatawiseSectionLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(listHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(listHref);
			softAssert.assertTrue(listHref.size() >= 5, "The number of links under the " + sectionName
					+ " section should be more than equal to 5 but found: " + listHref.size());
			softAssert.assertTrue(errorLinks.size() == 0, "Few links are not having response code 200 under the "
					+ sectionName + " section. List of such links are: " + errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the " + sectionName + " section " + dupLinks);

			boolean sectionHeadingFlag = commonL1L2PagesMethods
					.verifyTheSectionHeadingLink(opinionPageMethods.getSectionHeadingLink(sectionName), "data-wise");
			softAssert.assertTrue(sectionHeadingFlag, "The section Heading link is not of the same section ");
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the Citing section")
	public void verifyCitingsSection() {
		softAssert = new SoftAssert();
		String sectionName = "CITINGS";
		try {

			List<String> listHref = opinionPageMethods.getEtCitingSectionLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(listHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(listHref);
			softAssert.assertTrue(listHref.size() >= 4, "The number of links under the " + sectionName
					+ " section should be more than equal to 4 but found: " + listHref.size());
			softAssert.assertTrue(errorLinks.size() == 0, "Few links are not having response code 200 under the "
					+ sectionName + " section. List of such links are: " + errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the " + sectionName + " section " + dupLinks);

			boolean sectionHeadingFlag = commonL1L2PagesMethods
					.verifyTheSectionHeadingLink(opinionPageMethods.getSectionHeadingLink(sectionName), "citings");
			softAssert.assertTrue(sectionHeadingFlag, "The section Heading link is not of the same section ");
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the Speaking Tree section")
	public void verifySpeakingTreeSection() {
		softAssert = new SoftAssert();
		String sectionName = "SPEAKING TREE";
		try {

			List<String> listHref = opinionPageMethods.getSpeakingTreeSectionLinks();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(listHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(listHref);
			softAssert.assertTrue(listHref.size() >= 5, "The number of links under the " + sectionName
					+ " section should be more than equal to 5 but found: " + listHref.size());
			softAssert.assertTrue(errorLinks.size() == 0, "Few links are not having response code 200 under the "
					+ sectionName + " section. List of such links are: " + errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the " + sectionName + " section " + dupLinks);

			boolean sectionHeadingFlag = commonL1L2PagesMethods.verifyTheSectionHeadingLink(
					opinionPageMethods.getSectionHeadingLink(sectionName), "speaking-tree");
			softAssert.assertTrue(sectionHeadingFlag, "The section Heading link is not of the same section ");
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

	@Test(description = "This test verifies the top trending widget on the opinion page")
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

	@Test(description = "This test verifies the Blog section")
	public void verifyBlogSection() {
		softAssert = new SoftAssert();
		String sectionName = "BLOGS";
		try {

			List<String> listHref = opinionPageMethods.getBlogSection();
			List<String> errorLinks = commonL1L2PagesMethods.getAllErrorLinks(listHref);
			List<String> dupLinks = commonL1L2PagesMethods.checkIfListIsUnique(listHref);
			softAssert.assertTrue(listHref.size() >= 5, "The number of links under the " + sectionName
					+ " section should be more than equal to 5 but found: " + listHref.size());
			softAssert.assertTrue(errorLinks.size() == 0, "Few links are not having response code 200 under the "
					+ sectionName + " section. List of such links are: " + errorLinks);
			softAssert.assertTrue(dupLinks.size() == 0,
					"Duplicate links are present under the " + sectionName + " section " + dupLinks);
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "exception occured during the test run");
		}
		softAssert.assertAll();

	}

}
