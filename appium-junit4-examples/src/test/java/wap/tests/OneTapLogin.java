package wap.tests;

import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.WaitUtil;
import wap.pagemethods.HomePageMethods;
import wap.pagemethods.LoginPageMethods;
import wap.pagemethods.MenuPageMethods;
import web.pagemethods.WebBaseMethods;

public class OneTapLogin extends BaseTest {
	private String wapUrl;
	private HomePageMethods homePageMethods;
	private LoginPageMethods loginPageMethods;
	private MenuPageMethods menuPageMethods;
	private SoftAssert softAssert;
	Map<String, String> TestData;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl = BaseTest.baseUrl;

	}

	private void doInit() throws IOException {
		launchBrowser();
		homePageMethods = new HomePageMethods(driver);
		loginPageMethods = new LoginPageMethods(driver);
		menuPageMethods = new MenuPageMethods(driver);
		TestData = ExcelUtil.getTestDataRow("TestDataSheet", "verifyLogin", 1);

	}

	@Test(description = "this test is to verify the onetap login on Wap", priority = 0)
	public void verifyOneTapLogin() throws IOException {
		doInit();
		driver.get("https://accounts.google.com");
		homePageMethods.jqueryInjForReactPages();
		WaitUtil.sleep(3000);
		String email = TestData.get("Email");
		String password = TestData.get("Password");
		Assert.assertTrue(loginPageMethods.loginOnGmail(email, password), "user is not able to login through gmail");
		WaitUtil.waitForLoad(driver);
		WaitUtil.sleep(2000);
		WebBaseMethods.navigateTimeOutHandle(wapUrl);
		WebBaseMethods.removeInterstitial();
		WaitUtil.waitForLoad(driver);
		WaitUtil.sleep(2000);

		WebBaseMethods.scrollMultipleTimes(3, "bottom", 150);
		WaitUtil.sleep(10000);
		try {
			WebBaseMethods.switchToFrame(loginPageMethods.getOneTapLoginIframe());
		} catch (Exception ee) {
			Assert.assertTrue(false, "The one tap login pop up is not shown");

		}
		Assert.assertTrue(loginPageMethods.isOneTapDisplayed(), "One tap login pop up is not shown on the homepage");
		Assert.assertTrue(loginPageMethods.clickOneTapLoginButton(), "User is not able to click one tap login button");
		WaitUtil.sleep(3000);
		WebBaseMethods.removeInterstitial();
		WebBaseMethods.navigateTimeOutHandle(wapUrl + config.fetchConfigProperty("MarketsUrl"));
		WaitUtil.sleep(3000);
		Assert.assertTrue(menuPageMethods.clickFooterMenuIconNonReact(),
				"User is not able to click on the footer menu icon");
		Assert.assertTrue(loginPageMethods.checkLoginNonReact(), "The user is not login on the non react templates");
		WebBaseMethods.navigateBackTimeOutHandle();
		Assert.assertTrue(homePageMethods.clickFooterMenuICon(), "Unable to click Sign In button from menu");
		Assert.assertTrue(loginPageMethods.checkLoginReact(), "Login status in side menu is false");

	}

	@Test(description = "this test is to verify the onetap cross button on WAP", priority = 1)
	public void verifyOneTapLoginCrossButton() throws IOException {
		if (driver != null) {
			driver.quit();
		}
		doInit();

		driver.get("https://accounts.google.com");
		homePageMethods.jqueryInjForReactPages();
		WaitUtil.sleep(3000);
		String email = TestData.get("Email");
		String password = TestData.get("Password");
		Assert.assertTrue(loginPageMethods.loginOnGmail(email, password), "user is not able to login through gmail");
		WaitUtil.waitForLoad(driver);
		WaitUtil.sleep(2000);
		WebBaseMethods.navigateTimeOutHandle(wapUrl);
		WebBaseMethods.removeInterstitial();
		WaitUtil.waitForLoad(driver);
		WaitUtil.sleep(2000);

		WebBaseMethods.scrollMultipleTimes(3, "bottom", 150);
		WaitUtil.sleep(10000);
		try {
			WebBaseMethods.switchToFrame(loginPageMethods.getOneTapLoginIframe());
		} catch (Exception ee) {
			Assert.assertTrue(false, "The one tap login pop up is not shown");

		}
		Assert.assertTrue(loginPageMethods.isOneTapDisplayed(), "One tap login pop up is not shown on the homepage");
		Assert.assertTrue(loginPageMethods.clickOneTapCloseButton(), "USer is not able to click on the close button");

		Assert.assertFalse(loginPageMethods.isOneTapDisplayed(),
				"One tap login pop up is shown on the homepage even after closes the pop up");

	}

}
