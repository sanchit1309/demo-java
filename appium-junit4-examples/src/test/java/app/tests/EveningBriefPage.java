package app.tests;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import app.pagemethods.BriefPageMethods;
import app.pagemethods.HeaderPageMethods;
import app.pagemethods.MenuPageMethods;
import common.launchsetup.BaseTest;
import io.appium.java_client.AppiumDriver;

public class EveningBriefPage extends BaseTest {

	private MenuPageMethods menuPageMethods;
	private HeaderPageMethods headerPageMethods;
	private BriefPageMethods briefPageMethods;
	private AppiumDriver<?> appDriver;

	@BeforeClass(alwaysRun = true)
	public void launchApp() throws IOException {
		launchBrowser();
		appDriver = iAppCommonMethods.getDriver();
		headerPageMethods = new HeaderPageMethods(appDriver);
		briefPageMethods = new BriefPageMethods(appDriver);
		menuPageMethods = new MenuPageMethods(appDriver);

	}

	private boolean navigateToBriefsPage(String MoringOrEvenig) {
		headerPageMethods.clickMenuIconSwipeUpFirst();
		return briefPageMethods.openBriefPageByName("Evening Brief", menuPageMethods);
	}

	@Test(description = "Verifies number of stories in evening brief section", priority = 0)
	public void verifyArticlesCount() {
		Assert.assertTrue(navigateToBriefsPage("Morning Brief"), "Unable to navigate to morning briefs");
		int totalNumberOfStories = briefPageMethods.getTotalNumberOfStories();
		System.out.println(totalNumberOfStories);
		Assert.assertTrue(totalNumberOfStories > 0, "No stories are shown on morning briefs section");
		List<String> messageList = briefPageMethods.checkAllStories(totalNumberOfStories);
		Assert.assertTrue(messageList.isEmpty(), String.join("<br>", messageList));

	}

	@Test(description = "Verifies advertisement is present in evening brief section", priority = 1, dependsOnMethods = {
			"verifyArticlesCount" }, alwaysRun = true)
	public void verifyAdIsPresent() {
		Assert.assertTrue(briefPageMethods.getAdsFlag(), "Ad not found while swiping all morning brief stories");
	}

	@AfterClass(alwaysRun = true)
	public void quit() throws IOException {
		driver.quit();
	}
}