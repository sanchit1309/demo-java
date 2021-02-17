package com.web.tests;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.WaitUtil;
import web.pagemethods.AuthorPageMethods;
import web.pagemethods.PanacheProfilePageMethods;
import web.pagemethods.WebBaseMethods;

public class PanacheProfilePage extends BaseTest {

	private String baseUrl;
	private PanacheProfilePageMethods panacheProfilePageMethods;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		launchBrowser(baseUrl);
		panacheProfilePageMethods = new PanacheProfilePageMethods(driver);

	}

	@Test(description = "This test verifies the panache profilepage", dataProvider = "profiles")
	public void verifyPanacheProfilePage(String profile) {
		softAssert = new SoftAssert();
		String profileUrl = baseUrl + "/panache/panache-people-101" + profile;
		driver.get(profileUrl);
		WaitUtil.waitForLoad(driver);
		softAssert.assertTrue(panacheProfilePageMethods.getProfileTitle().length() > 0,
				"The profile title is shown blank for the profile link: " + profileUrl);
		softAssert.assertTrue(panacheProfilePageMethods.getProfileDesignation().length() > 0,
				"The profile Designation is shown blank for the profile link: " + profileUrl);
		softAssert.assertTrue(panacheProfilePageMethods.getProfileBirthDetails().length() > 0,
				"The profile birth details is shown blank for the profile link: " + profileUrl);
		softAssert.assertTrue(panacheProfilePageMethods.getProfileEducation().length() > 0,
				"The profile education is shown blank for the profile link: " + profileUrl);
		softAssert.assertTrue(panacheProfilePageMethods.getProfileLeague().length() > 0,
				"The profile league is shown blank for the profile link: " + profileUrl);
		softAssert.assertTrue(panacheProfilePageMethods.getProfileNationality().length() > 0,
				"The profile Nationality is shown blank for the profile link: " + profileUrl);
		softAssert.assertTrue(panacheProfilePageMethods.getProfileNetWorth().length() > 0,
				"The profile Net worth is shown blank for the profile link: " + profileUrl);
		List<String> profileStoryList = panacheProfilePageMethods.getProfileNewsStoryList();
		softAssert.assertTrue(profileStoryList.size() > 0,
				"The articles are not shown under the News section for profile link: " + profileUrl);
		List<String> errorLinks = WebBaseMethods.getAllErrorLinks(profileStoryList);
		softAssert.assertTrue(errorLinks.size() == 0, "Few links under the profile news section for profile link: "
				+ profileUrl + " is throwing 404. List of urls: " + errorLinks);
		List<String> trendingInProfileList = panacheProfilePageMethods.getTrendingInProfile();
		softAssert.assertTrue(trendingInProfileList.size() > 0,
				"Trending in profile widget is shown blank on profile link: " + profileUrl);
		List<String> profileErrorLinks = WebBaseMethods.getAllErrorLinks(trendingInProfileList);
		softAssert.assertTrue(profileErrorLinks.size() == 0,
				"Few links under the trending in profile widget is throwing 404. List of such links are: "
						+ profileErrorLinks);
		softAssert.assertTrue(panacheProfilePageMethods.getBeforeYouGoContentList().size() > 0,
				"Before you go section is shown blank, for the profile: " + profileUrl);
		softAssert.assertTrue(panacheProfilePageMethods.getJourneySoFarContentList().size() > 0,
				"Journey so far section is shown blank, for the profile: " + profileUrl);
		softAssert.assertAll();
	}

	@DataProvider
	public String[] profiles() {
		String[] keywords = { "/mukesh-ambani/profileshow/79551066.cms", "/jeff-bezos/profileshow/79278871.cms",
				"/bill-gates/profileshow/79020550.cms"};

		return keywords;

	}
}
