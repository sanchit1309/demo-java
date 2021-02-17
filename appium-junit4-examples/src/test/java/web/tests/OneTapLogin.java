package web.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.WaitUtil;
import web.pagemethods.HeaderPageMethods;
import web.pagemethods.LoginPageMethods;
import web.pagemethods.WebBaseMethods;

public class OneTapLogin extends BaseTest {

	private LoginPageMethods loginPageMethods;
	private HeaderPageMethods headerPageMethods;
	private String baseUrl;
	Map<String, String> TestData = new HashMap<String, String>();

	@BeforeClass
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;

	}

	private void doInit() throws IOException {
		launchBrowser();
		loginPageMethods = new LoginPageMethods(driver);
		headerPageMethods = new HeaderPageMethods(driver);
		TestData = ExcelUtil.getTestDataRow("TestDataSheet", "VerifyLogin", 1);
	}

	@Test(description = "this test is to verify the onetap login on Web", priority = 0)
	public void verifyOneTapLogin() throws IOException {
		doInit();
		driver.get("https://accounts.google.com");
		WaitUtil.sleep(3000);
		String email = TestData.get("Email");
		String password = TestData.get("Password");
		Assert.assertTrue(loginPageMethods.gmailLogin(email, password), "Unable to login via g plus");
		WaitUtil.waitForLoad(driver);
		WaitUtil.sleep(3000);
		WebBaseMethods.navigateTimeOutHandle(baseUrl);
		WebBaseMethods.removeInterstitial();
		WaitUtil.waitForLoad(driver);
		WaitUtil.sleep(2000);
		try {
			WebBaseMethods.switchToFrame(loginPageMethods.getOneTapLoginIframe());
		} catch (Exception ee) {
			Assert.assertTrue(false, "The one tap login pop up is not shown");

		}
		Assert.assertTrue(loginPageMethods.isOneTapDisplayed(), "One tap login pop up is not shown on the homepage");
		Assert.assertTrue(loginPageMethods.clickOneTapLoginButton(), "User is not able to click one tap login button");
		WaitUtil.sleep(3000);
		WebBaseMethods.removeInterstitial();
		Assert.assertTrue(headerPageMethods.getLoggedInUserImage(), "Logged in user image not shown");

	}

	@Test(description = "this test is to verify the onetap cross button on web", priority = 1)
	public void verifyOneTapLoginCrossButton() throws IOException {
		if (driver != null) {
			driver.quit();
		}
		doInit();

		driver.get("https://accounts.google.com");
		WaitUtil.sleep(3000);
		String email = TestData.get("Email");
		String password = TestData.get("Password");
		Assert.assertTrue(loginPageMethods.gmailLogin(email, password), "Unable to login via g plus");
		WaitUtil.waitForLoad(driver);
		WaitUtil.sleep(3000);
		WebBaseMethods.navigateTimeOutHandle(baseUrl);
		WebBaseMethods.removeInterstitial();
		WaitUtil.waitForLoad(driver);
		WaitUtil.sleep(2000);
		try {
			WebBaseMethods.switchToFrame(loginPageMethods.getOneTapLoginIframe());
		} catch (Exception ee) {
			Assert.assertTrue(false, "The one tap login pop up is not shown");

		}
		Assert.assertTrue(loginPageMethods.clickOneTapCloseButton(), "USer is not able to click on the close button");

		Assert.assertFalse(loginPageMethods.isOneTapDisplayed(),
				"One tap login pop up is shown on the homepage even after closes the pop up");

	}

	@AfterTest
	public void tearDown() {
		driver.quit();

	}

}
