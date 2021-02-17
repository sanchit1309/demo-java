
package wap.tests;

import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.WaitUtil;
import wap.pagemethods.HomePageMethods;
import wap.pagemethods.LoginPageMethods;
import web.pagemethods.WebBaseMethods;

/**
 * 
 * Login Page Test Class for WAP
 *
 */
public class LoginPage extends BaseTest {
	private String wapUrl;
	private HomePageMethods homePageMethods;
	private LoginPageMethods loginPageMethods;
	private SoftAssert softAssert;
	Map<String, String> TestData;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl = BaseTest.baseUrl;
		launchBrowser(wapUrl);
		homePageMethods = new HomePageMethods(driver);
		loginPageMethods = new LoginPageMethods(driver);
		TestData = ExcelUtil.getTestDataRow("TestDataSheet", "verifyLogin", 1);
	}

	@Test(description = "Verify SignIn for registered user", groups = { "Login", "Regression",
			"Monitoring" }, enabled = false)
	public void verifyLoginRegisteredUser() {
		// softAssert = new SoftAssert();
		Assert.assertTrue(homePageMethods.clickMenuIcon(), "Unable to click menu icon");
		Assert.assertTrue(loginPageMethods.clickSignInMenu(), "Unable to click Sign In button from menu");
		Assert.assertTrue(loginPageMethods.clickContinueEmailMobile(),
				"SSO:Unable to click continue Email/Mobile button on login screen");
		Assert.assertTrue(loginPageMethods.findEmailSetValue(TestData.get("Email")), "SSO:Unable to find Email Field");
		Assert.assertTrue(loginPageMethods.clickContinueButton(),
				"Unable to click continue button on the email screen");
		Assert.assertTrue(loginPageMethods.findPasswordSetValue(TestData.get("Password")),
				"SSO:Unable to find Password Field");
		Assert.assertTrue(loginPageMethods.clickContinueButtonFinal(),
				"Unable to click continue button on the password screen");

		// softAssert.assertTrue(loginPageMethods.registeredUserLogin(TestData.get("Email"),
		// TestData.get("Password")), "Login for registered user");
		Assert.assertTrue(homePageMethods.clickMenuIcon(), "Unable to click Sign In button from menu");
		Assert.assertTrue(loginPageMethods.checkLogin(), "Login status in side menu is false");
	}

	@Test(description = "Verify SignIn with Facebook", groups = { "Login", "Regression" }, enabled = false)
	public void verifyLoginWithFB() {
		softAssert = new SoftAssert();
		homePageMethods.clickMenuIcon();
		Assert.assertTrue(loginPageMethods.loginWithFB(TestData.get("Email"), TestData.get("Password")),
				"Login with FaceBook");
		homePageMethods.clickMenuIcon();
		softAssert.assertTrue(loginPageMethods.checkLogin(), "Login status in side menu");
		softAssert.assertAll();
	}

	@Test(description = "Verify SignIn with G+", groups = { "Login", "Regression" }, enabled = false)
	public void verifyLoginWithGPlus() {
		softAssert = new SoftAssert();
		homePageMethods.clickMenuIcon();
		Assert.assertTrue(loginPageMethods.loginWithGPlus(TestData.get("Email"), TestData.get("Password")),
				"Login with G+");
		homePageMethods.clickMenuIcon();
		softAssert.assertTrue(loginPageMethods.checkLogin(), "Login status in side menu");
		softAssert.assertAll();
	}

	//////////////////////////////////// ***************************/////////////////////////////////

	@Test(description = "Verify SignIn with Facebook", groups = { "Login", "Regression" }, priority = 3)
	public void verifyLoginWithFBReact() {
		softAssert = new SoftAssert();
		homePageMethods.clickFooterMenuICon();
		Assert.assertTrue(loginPageMethods.loginWithFBReact(TestData.get("Email"), TestData.get("Password")),
				"Login with FaceBook");
		homePageMethods.clickFooterMenuICon();
		softAssert.assertTrue(loginPageMethods.checkLoginReact(), "Login status in side menu");
		softAssert.assertAll();
	}

	@Test(description = "Verify SignIn for registered user on homepage", groups = { "Login", "Regression",
			"Monitoring" }, priority = 1)
	public void verifyLoginRegisteredUserReact() {
		// softAssert = new SoftAssert();
		Assert.assertTrue(homePageMethods.clickFooterMenuICon(), "Unable to click menu icon");
		Assert.assertTrue(loginPageMethods.clickFooterMenuSignIn(), "Unable to click Sign In button from menu");
		Assert.assertTrue(loginPageMethods.clickContinueEmailMobile(),
				"SSO:Unable to click continue Email/Mobile button on login screen");
		Assert.assertTrue(loginPageMethods.findEmailSetValue(TestData.get("Email")), "SSO:Unable to find Email Field");
		Assert.assertTrue(loginPageMethods.clickContinueButton(),
				"Unable to click continue button on the email screen");
		Assert.assertTrue(loginPageMethods.findPasswordSetValue(TestData.get("Password")),
				"SSO:Unable to find Password Field");
		Assert.assertTrue(loginPageMethods.clickContinueButtonFinal(),
				"Unable to click continue button on the password screen");

		// softAssert.assertTrue(loginPageMethods.registeredUserLogin(TestData.get("Email"),
		// TestData.get("Password")), "Login for registered user");
		Assert.assertTrue(homePageMethods.clickFooterMenuICon(), "Unable to click Sign In button from menu");
		Assert.assertTrue(loginPageMethods.checkLoginReact(), "Login status in side menu is false");
	}

	@Test(description = "Verify SignIn with G+", groups = { "Login", "Regression" }, enabled = false, priority = 2)
	public void verifyLoginWithGPlusReact() {
		softAssert = new SoftAssert();
		homePageMethods.clickFooterMenuICon();
		Assert.assertTrue(loginPageMethods.loginWithGPlusReact(TestData.get("Email"), TestData.get("Password")),
				"Login with G+");
		homePageMethods.clickFooterMenuICon();
		softAssert.assertTrue(loginPageMethods.checkLoginReact(), "Login status in side menu");
		softAssert.assertAll();
	}

	@BeforeMethod
	public void takeToHome() {
		System.out.println("before method is executed");
		WebBaseMethods.clearBrowserSessionCookie(driver);
		WebBaseMethods.navigateToUrl(driver, wapUrl);
		WaitUtil.sleep(2000);
	}

	@AfterClass
	public void tearDown() {
		System.out.println("after class is executed");
		driver.quit();
	}

}