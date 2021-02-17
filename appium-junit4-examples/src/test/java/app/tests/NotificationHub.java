package app.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import app.pagemethods.HeaderPageMethods;
import app.pagemethods.MenuPageMethods;
import app.pagemethods.NotificationHubMethods;
import common.launchsetup.BaseTest;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;

public class NotificationHub extends BaseTest {
	private SoftAssert softAssert;
	private NotificationHubMethods notificationHubMethods;
	private HeaderPageMethods headerPageMethods;
	private MenuPageMethods menuPageMethods;
	private AppiumDriver<?> appDriver;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		launchBrowser();
		appDriver = iAppCommonMethods.getDriver();
		notificationHubMethods = new NotificationHubMethods(appDriver);
		headerPageMethods = new HeaderPageMethods(appDriver);
		menuPageMethods = new MenuPageMethods(appDriver);
	}

	@Test(description = "Verifies Linked Notifications is present",priority=1)
	public void verifyLinkedNotification() {
		WaitUtil.sleep(2000);
		Assert.assertTrue(navigateToNotificationHub(), "Unable to navigate to notifications hub");
		Assert.assertTrue(notificationHubMethods.isNotificationListShown(), "<br> Notification list is blank");
	}

	@Test(description = "Verifies if linked notfication is latest", dependsOnMethods = { "verifyLinkedNotification" },priority=2)
	public void verifyIsNotificationLatest() {
		Assert.assertTrue(notificationHubMethods.verifyLatestNotification(6),
				"The first notification shown under notfication hub is more than 6 hrs old");

	}

	@Test(description = " Validate sharing icons are present in notification ", enabled = true,priority=3)
	public void sharingIconsInEverynotification() {
		Assert.assertTrue(notificationHubMethods.validateShareIcons(),
				"Share icons are not coming in notification hub");
		BaseTest.iAppCommonMethods.navigateBack(appDriver);
	}

	@Test(description = " Verify ET Prime redirection from Notification Hub", enabled = false)
	public void redirectionCheckFromNotificationhub() {
		Assert.assertTrue(navigateToNotificationHub(), "Unable to navigate to notifications hub");
		Assert.assertTrue(notificationHubMethods.primeIconRedirection(),
				"Prime Homepage redirection from notification hub failed");
	}

	@AfterClass(alwaysRun = true)
	public void quit() throws IOException {
		driver.quit();
	}

	private boolean navigateToNotificationHub() {
		Boolean flag;
		flag = headerPageMethods.clickMenuIcon();
		flag = flag && menuPageMethods.clickMenuByLabel("Notifications Hub");
		return flag;
	}

}
