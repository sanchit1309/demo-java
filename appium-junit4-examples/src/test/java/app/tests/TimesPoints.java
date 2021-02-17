
package app.tests;

import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import app.pagemethods.HeaderPageMethods;
import app.pagemethods.LoginPageMethods;
import app.pagemethods.MenuPageMethods;
import app.pagemethods.SettingsPageMethods;
import common.launchsetup.BaseTest;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;

public class TimesPoints extends BaseTest {

	private HeaderPageMethods headerPageMethods;
	private MenuPageMethods menuPageMethods;
	private LoginPageMethods loginPageMethods;

	private AppiumDriver<?> appDriver;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		launchBrowser();
		appDriver = iAppCommonMethods.getDriver();
		headerPageMethods = new HeaderPageMethods(appDriver);
		menuPageMethods = new MenuPageMethods(appDriver);
		loginPageMethods = new LoginPageMethods(appDriver);
	}

	@Test(description = "Verifies times point widget")
	public void verifyTimesPointWidget() {
		SoftAssert softAssert = new SoftAssert();
		boolean flag = new LoginPage().doLogin(softAssert, headerPageMethods, menuPageMethods, loginPageMethods);
		if (flag) {
			headerPageMethods.clickMenuIcon();
			softAssert.assertTrue(menuPageMethods.isTimesPointShown(), "<br>-Times Points not shown");
			softAssert.assertTrue(menuPageMethods.isRedeemableShown(),
					"<br>Button to redeem or my points is not shown");
			softAssert.assertTrue(menuPageMethods.clickSettingsIcon(), "<br>-Settings icon not shown");
			WaitUtil.sleep(2000);
			softAssert.assertTrue(new SettingsPageMethods(appDriver).isMyTimesPointOptionPresent(),
					"On settings page, my times point option is not shown");
			softAssert.assertTrue(menuPageMethods.clickLogoutSettingsPage(), "Unable to logout the user");
		}
	
		softAssert.assertAll();
	}

	@AfterClass(alwaysRun = true)
	public void quit() throws Exception {
		appDriver.quit();
	}
}