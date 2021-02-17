package com.web.tests;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.WaitUtil;
import web.pagemethods.Budget2021PageMethods;
import web.pagemethods.WebBaseMethods;

public class Budget2021Page extends BaseTest {
	private String webUrl;
	SoftAssert softAssert;
	Budget2021PageMethods budget2021PageMethods;
	

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		webUrl = BaseTest.baseUrl + "/budget";
		launchBrowser(webUrl);
		budget2021PageMethods = new Budget2021PageMethods(driver);
	}

	@Test(description = "This test verifies the subsections on the budget page", dataProvider = "subSections")
	public void verifyBudgetPageSubsections(String subSection) {
		softAssert = new SoftAssert();
		String sectionName = subSection;
		WaitUtil.waitForLoad(driver);
		int articleCount = 5;
		List<String> urlList = budget2021PageMethods.getSectionNewsHref(sectionName);
		int size = urlList.size();
		List<String> errorLinks = WebBaseMethods.getAllErrorLinks(urlList);
		List<String> duplicateLinks = WebBaseMethods.checkIfListIsUnique(urlList);
		softAssert.assertTrue(size >= articleCount, "The number of links shown under the budget subsection "
				+ sectionName + " should be greater than equal to " + articleCount + " but found: " + size + ".");
		softAssert.assertTrue(errorLinks.size() == 0, "Few links under the subsection: " + sectionName
				+ " are not having the response code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(duplicateLinks.size() == 0, "Duplicate links are shown under the section: " + sectionName
				+ ". List of such links are: " + duplicateLinks);
		softAssert.assertAll();
	}

	@DataProvider
	public String[] subSections() {

		String[] subSections = { "topStories", "thirtyShots", "budgetIdea", "newsWidget", "viewsWidget", "budget_bytes",
				"taxes_youWidget", "commonmanWidget", "hindiWidget" };
		return subSections;

	}

	@Test(description = "This test verifies if there is any broken link on the budget Page")
	public void verifyBrokenLinks() {
		softAssert = new SoftAssert();
		List<String> allUrls = budget2021PageMethods.getAllUrls();
		System.out.println(allUrls);
		System.out.println(allUrls.size());
		List<String> errorUrls = WebBaseMethods.getAllErrorLinks(allUrls);
		softAssert.assertTrue(errorUrls.size() == 0,
				"Few links are not having the response code 200. List of such links: " + errorUrls);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the subsections under the Sector in Focus on the budget page", dataProvider = "sectorInFocusSubSections")
	public void verifyBudgetPageSectorInFocusSubsections(String subSection) {
		softAssert = new SoftAssert();
		String sectionName = subSection;
		WaitUtil.waitForLoad(driver);
		int articleCount = 5;
		List<String> urlList = budget2021PageMethods.getSectorInFocusSectionLinks(sectionName);
		int size = urlList.size();
		List<String> errorLinks = WebBaseMethods.getAllErrorLinks(urlList);
		List<String> duplicateLinks = WebBaseMethods.checkIfListIsUnique(urlList);
		softAssert.assertTrue(size >= articleCount,
				"The number of links shown under the budget sector in focus subsection " + sectionName
						+ " should be greater than equal to " + articleCount + " but found: " + size + ".");
		softAssert.assertTrue(errorLinks.size() == 0, "Few links under the sector in focus subsection: " + sectionName
				+ " are not having the response code 200. List of such links are: " + errorLinks);
		softAssert.assertTrue(duplicateLinks.size() == 0,
				"Duplicate links are shown under the sector in focus section: " + sectionName
						+ ". List of such links are: " + duplicateLinks);
		softAssert.assertAll();
	}

	@DataProvider
	public String[] sectorInFocusSubSections() {

		String[] subSections = { "Banking & Finance", "Energy", "Tech", "Auto", "FMCG", "MSME" };
		return subSections;

	}

	@Test(description = "This test verifies the budget trending terms on the budget page")
	public void verifyBudgetTrendingLinks() {
		softAssert = new SoftAssert();
		WaitUtil.waitForLoad(driver);
		int articleCount = 5;
		List<String> urlList = budget2021PageMethods.getBudgetTrendingLinks();
		int size = urlList.size();
		List<String> errorLinks = WebBaseMethods.getAllErrorLinks(urlList);
		List<String> duplicateLinks = WebBaseMethods.checkIfListIsUnique(urlList);
		softAssert.assertTrue(size >= articleCount,
				"The number of links shown under the budget trending section should be greater than equal to "
						+ articleCount + " but found: " + size + ".");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links under the budget trending terms are not having the response code 200. List of such links are: "
						+ errorLinks);
		softAssert.assertTrue(duplicateLinks.size() == 0,
				"Duplicate links are shown under the budget trending terms. List of such links are: " + duplicateLinks);
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the budget archive widget on the budget page")
	public void verifyBudgetArchiveLinks() {
		softAssert = new SoftAssert();
		WaitUtil.waitForLoad(driver);
		int articleCount = 5;
		List<String> urlList = budget2021PageMethods.getBudgetArchiveLinks();
		int size = urlList.size();
		List<String> errorLinks = WebBaseMethods.getAllErrorLinks(urlList);
		List<String> duplicateLinks = WebBaseMethods.checkIfListIsUnique(urlList);
		softAssert.assertTrue(size >= articleCount,
				"The number of links shown under the budget archive section should be greater than equal to "
						+ articleCount + " but found: " + size + ".");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few links under the budget archives are not having the response code 200. List of such links are: "
						+ errorLinks);
		softAssert.assertTrue(duplicateLinks.size() == 0,
				"Duplicate links are shown under the budget archive section. List of such links are: "
						+ duplicateLinks);
		softAssert.assertAll();
	}
	
	@Test(description = "This test verifies the more from the section link on budget page")
	public void verifyMoreFromLinks() {
		softAssert = new SoftAssert();
		WaitUtil.waitForLoad(driver);
		int articleCount = 5;
		List<String> urlList = budget2021PageMethods.getMoreFromSectionLinks();
		int size = urlList.size();
		List<String> errorLinks = WebBaseMethods.getAllErrorLinks(urlList);
		List<String> duplicateLinks = WebBaseMethods.checkIfListIsUnique(urlList);
		softAssert.assertTrue(size >= articleCount,
				"More from section links are not shown on few sections on the budget page "
						+ articleCount + " but found: " + size + ".");
		softAssert.assertTrue(errorLinks.size() == 0,
				"Few More from section links are not having the response code 200. List of such links are: "
						+ errorLinks);
		softAssert.assertTrue(duplicateLinks.size() == 0,
				"Duplicate links are there for more from section links. List of such links are: "
						+ duplicateLinks);
		softAssert.assertAll();
	}
}
