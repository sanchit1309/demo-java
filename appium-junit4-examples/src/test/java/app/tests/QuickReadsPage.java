package app.tests;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import app.pagemethods.AlertsPromptsMethods;
import app.pagemethods.BriefPageMethods;
import app.pagemethods.HeaderPageMethods;
import common.launchsetup.BaseTest;
import io.appium.java_client.AppiumDriver;

public class QuickReadsPage extends BaseTest {

	private HeaderPageMethods headerPageMethods;
	private BriefPageMethods briefPageMethods;
	private AppiumDriver<?> appDriver;

	@BeforeClass(alwaysRun = true)
	public void launchApp() throws IOException {
		launchBrowser();
		appDriver = iAppCommonMethods.getDriver();
		headerPageMethods = new HeaderPageMethods(appDriver);
		briefPageMethods = new BriefPageMethods(appDriver);
	}

	@Test(description = "Verifies number of stories in quick reads section", priority = 0)
	public void verifyArticlesCount() {
		Assert.assertTrue(navigateToQuickReads(), "Unable to naigate to quick reads");
		int totalNumberOfStories = briefPageMethods.getTotalNumberOfStories();
		System.out.println(totalNumberOfStories);
		Assert.assertTrue(totalNumberOfStories > 0, "No stories are shown in quick reads section");
		List<String> messageList = briefPageMethods.checkAllStories(totalNumberOfStories);
		Assert.assertTrue(messageList.isEmpty(), String.join("<br>", messageList));

	}

	@Test(description = "Verifies advertisement is present in quick reads section", priority = 1, dependsOnMethods = {
			"verifyArticlesCount" }, alwaysRun = true)
	public void verifyAdIsPresent() {
		Assert.assertTrue(briefPageMethods.getAdsFlag(), "Ad not found while swiping all quick read stories");
	}

	@AfterClass(alwaysRun = true)
	public void quit() throws IOException {
		driver.quit();
	}

	private boolean navigateToQuickReads() {
		boolean flag = false;
		flag = headerPageMethods.clickTopTab("Quick Reads");
		if (BaseTest.platform.contains("android"))
			new AlertsPromptsMethods((AppiumDriver<?>) driver).dismissCoachMark();
		return flag;
	}

}
