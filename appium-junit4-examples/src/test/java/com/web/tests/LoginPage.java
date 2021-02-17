package com.web.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.ExcelUtil;
import common.utilities.WaitUtil;
import web.pagemethods.HeaderPageMethods;
import web.pagemethods.LoginPageMethods;
import web.pagemethods.WebBaseMethods;

public class LoginPage extends BaseTest {

	private LoginPageMethods loginPageMethods;
	private HeaderPageMethods headerPageMethods;
	private String baseUrl;
	Map<String, String> TestData = new HashMap<String, String>();

	@BeforeClass
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		doInit();

	}

	private void doInit() throws IOException {
		launchBrowser(baseUrl);
		loginPageMethods = new LoginPageMethods(driver);
		headerPageMethods = new HeaderPageMethods(driver);
		TestData = ExcelUtil.getTestDataRow("TestDataSheet", "VerifyLogin", 1);
	}

	@Test(description = "This test verifies login by email", groups = { "monitoring" }, priority = 0)
	public void verifyRegisteredUserSignIn() {
		Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "Unable to find sign in button");

		Assert.assertTrue(loginPageMethods.registeredUserLogin(TestData.get("Email"), TestData.get("Password")),
				"Unable to fill entries in the login window");
		Assert.assertTrue(headerPageMethods.checkUserLoggedInState(), "Logged in user image not shown");
	}

	@Test(description = "This test verifies login by facebook", priority = 1)
	public void verifyFBLogin() throws InterruptedException, IOException {
		driver.quit();
		doInit();
		String email = TestData.get("Email");
		String password = TestData.get("Password");
		Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "Unable to find sign in button");
		Assert.assertTrue(loginPageMethods.clickFbButton(), "Unable to find fb button");
		WaitUtil.sleep(2000);
		Assert.assertTrue(loginPageMethods.facebookLoginMain(email, password), "Unable to sign in user on FB");
		WebBaseMethods.switchToParentWindow();
		Assert.assertTrue(headerPageMethods.checkUserLoggedInState(), "Logged in user image not shown");
	}

	@Test(description = "This test verifies login by google plus", groups = { "login" }, enabled = false)
	public void verifyGPlusLogin() throws InterruptedException, IOException {
		driver.quit();
		doInit();
		String email = TestData.get("Email");
		String password = TestData.get("Password");
		Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "Unable to find sign in button");
		WebBaseMethods.switchToChildWindow(5);
		Assert.assertTrue(loginPageMethods.clickGPlusButton(), "Unable to click g plus button");
		Assert.assertTrue(loginPageMethods.googlePlusLogin(email, password), "Unable to login via g plus");
		WebBaseMethods.switchToParentWindow();
		Assert.assertTrue(headerPageMethods.getLoggedInUserImage(), "Logged in user image not shown");
	}

	// @AfterMethod
	public void loginAfterMethod() throws IOException {
		driver.quit();
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
