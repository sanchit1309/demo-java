package app.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import app.pagemethods.HeaderPageMethods;
import app.pagemethods.MenuPageMethods;
import app.pagemethods.PodcastPageMethods;
import common.launchsetup.BaseTest;
import io.appium.java_client.AppiumDriver;

public class PodcastPage extends BaseTest {
	private SoftAssert softAssert;
	private AppiumDriver<?> appDriver;
	private PodcastPageMethods podcastPageMethods;
	private MenuPageMethods menuPageMethods;
	private HeaderPageMethods headerPageMethods;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		launchBrowser();
		appDriver = iAppCommonMethods.getDriver();
		menuPageMethods = new MenuPageMethods(appDriver);
		headerPageMethods = new HeaderPageMethods(appDriver);
		podcastPageMethods = new PodcastPageMethods(appDriver);
		softAssert = new SoftAssert();

	}

	@Test(description = "Verify Redirection to podcast listing from LHS menu", priority = 1, enabled = true)
	public void averifyRedirectiontoPodcastListingFromLHSMenu() {
		System.out.println("priorty 1 case");
		Assert.assertTrue(navigateToPodcastTab(), "Podcast redirection from LHS menu failed");
		Assert.assertTrue(podcastPageMethods.validatePodcastRedirection(), " Landing on Podcast listing page failed");
	}

	@Test(description = " Verify podcast header and podcasts present on podcast listing page", dependsOnMethods = {
			"averifyRedirectiontoPodcastListingFromLHSMenu" }, priority = 2, enabled = true)
	public void bVerifyPodcastListingPage() {
		System.out.println("priority2 case");
		softAssert.assertTrue(podcastPageMethods.validatePostcastListSize(),
				" Podcasts are not getting displayed on Podcast listing page");
		softAssert.assertTrue(podcastPageMethods.validatePodcastHeaderOnListing(),
				" Header on podcast listing is not getting displayed as ET Radio");
		softAssert.assertAll();
	}

	@Test(description = "Verify podcast play and pause on podcast detail page", enabled = true, priority = 3)
	public void VerifyPodcastClick() {
		BaseTest.iAppCommonMethods.navigateBack(appDriver);
		Assert.assertTrue(navigateToPodcastTab(), "Podcast redirection from LHS menu failed");
		Assert.assertTrue(podcastPageMethods.getHeaderAndDurationOnListing(),
				"Header and Listing coming as null in listing of podcast");
		softAssert.assertTrue(podcastPageMethods.clickPodcast(),
				" Podcasts are not getting clicked on Podcast listing page");
		softAssert.assertTrue(podcastPageMethods.verifyAutoPlayFunctionalityOfPodcast(),
				"auto play of podcast is not working on podcast page on redirecting from podcast listing");
		softAssert.assertTrue(podcastPageMethods.verifyPodcastPause(),
				"Podcast is not getting paused on podcast detail page");
		softAssert.assertAll();
	}

	@Test(description = " Verify sharing icons present on podcast detail page", priority = 5, dependsOnMethods = {
			"VerifyPodcastClick" },enabled = true)
	public void verifySharingIconsPresent() {
		Assert.assertTrue(podcastPageMethods.verifySharingIconPresent(),
				"Sharing icons are not present on podcast detail page");
	}

	@Test(description = "Verify Podcast Duration and Heading on detail Podcast Page", priority = 4, dependsOnMethods = {
			"VerifyPodcastClick" },enabled = true)
	public void firstPodcastInfo() {
		softAssert.assertTrue(podcastPageMethods.validatePodcastDurationAndHeadingInDetailPage(),
				"Podcast time and duration do not match on podcast detail page");
		softAssert.assertAll();
	}

	@Test(description = " Validate Also Listen Tag Present and redirection to a podcast", priority = 6, dependsOnMethods = {
			"VerifyPodcastClick" },enabled = true)
	public void validateAlsoListenSection() {
		Assert.assertTrue(podcastPageMethods.validateAlsoListenTagPresent(),
				" Also Listen Tag is not present in Podcast Detail Page");
		Assert.assertTrue(podcastPageMethods.validateAlsoListenList(),
				"Also Listen List in Podcast detail page is not present");
		Assert.assertTrue(podcastPageMethods.validateRedirectiontoAlsoListPodcast(),
				"Redirection to podcast detail page from Podcast also listen section failed");

	}

	@AfterClass(alwaysRun = true)
	public void quit() throws IOException {
		driver.quit();
	}

	private boolean navigateToPodcastTab() {
		boolean flag = false;
		headerPageMethods.clickMenuIcon();
		flag = menuPageMethods.clickMenuByLabel("Podcast");

		return flag;
	}

}