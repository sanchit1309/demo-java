package app.tests;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import app.pagemethods.HeaderPageMethods;
import app.pagemethods.MenuPageMethods;
import common.launchsetup.BaseTest;
import common.urlRepo.AppFeeds;
import common.utilities.ApiHelper;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;

public class MenuPage extends BaseTest {
	private MenuPageMethods menuPageMethods;
	private HeaderPageMethods headerPageMethods;
	private SoftAssert softAssert;
	private AppiumDriver<?> appDriver;
	private List<String> menuOptions = new LinkedList<String>();

	@BeforeClass(alwaysRun = true)
	public void launchApp() throws IOException {
		launchBrowser();
		appDriver = iAppCommonMethods.getDriver();
		menuPageMethods = new MenuPageMethods(appDriver);
		headerPageMethods = new HeaderPageMethods(appDriver);
		Assert.assertTrue(headerPageMethods.clickMenuIcon(), "Unable to clicking menu icon");
		menuOptions = ApiHelper.getValueFromAPI(AppFeeds.lhsFeed, "nm", "", "", "Item");
		globalFlag = true;
	}

	@Test(description = "Verifies all menu options and their sub menus from  LHS feed")
	public void verifyLHSMenu() {
		softAssert = new SoftAssert();
		Assert.assertTrue(menuOptions.size() > 5, "Less than 5 L1 menus fetched from API " + AppFeeds.lhsFeed);
		navigateToSearchMenu();
		menuOptions.forEach(l1Menu -> {
			softAssert = new SoftAssert();
			System.out.println("l1Menu: " + l1Menu);
			List<String> subMenu = ApiHelper.getValuesFromJSONResponse(ApiHelper.getOutput(), "nm", "nm", l1Menu,
					"Item", "ss");
			boolean isClickNeeded = subMenu.isEmpty() ? false : true;
			Assert.assertTrue(menuPageMethods.scrollToMenuOptionClick(l1Menu, isClickNeeded),
					"<br>Unable to find menu option: " + l1Menu);
			subMenu.forEach(l2Menu -> {
				softAssert.assertTrue(menuPageMethods.scrollSubMenuItemToView(l2Menu),
						"<br>Unable to find submenu option: " + l2Menu + " under menu option: " + l1Menu);
			});
		});
		softAssert.assertAll();
	}

	@AfterClass(alwaysRun = true)
	public void quit() throws Exception {
		appDriver.quit();
	}
	
	private boolean navigateToMoreApps() {
		new HeaderPageMethods(appDriver).clickMenuIconSwipeUpFirst();
		return new MenuPageMethods(appDriver).clickMenuByLabel("More Apps");
	}
	
	private void navigateToSearchMenu() {
		softAssert.assertTrue(navigateToMoreApps(), "Page not redirected to More Apps");
		headerPageMethods.clickMenuIconSwipeUpFirst();
		WaitUtil.sleep(1000);
		menuPageMethods.scrollDownToSettingIcon();
	}
}
