package pwa.tests;

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
import pwa.pagemethods.HomePageMethods;
import pwa.pagemethods.LoginPageMethods;
import web.pagemethods.WebBaseMethods;


public class LoginPage extends BaseTest{
	
	private String wapUrl;
	private HomePageMethods homePageMethods;
	private LoginPageMethods loginPageMethods;
	private SoftAssert softAssert;
	Map<String, String> TestData;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl =BaseTest.baseUrl;
		launchBrowser(wapUrl);
		homePageMethods = new HomePageMethods(driver);
		loginPageMethods = new LoginPageMethods(driver);
		TestData = ExcelUtil.getTestDataRow("TestDataSheet", "verifyLogin", 1);
	}
	
	
	
	@Test(description = "Verify SignIn for registered user", groups = { "Login", "Regression","Monitoring" })
	public void verifyLoginRegisteredUser() {
		softAssert = new SoftAssert();
		homePageMethods.clickMenuIcon();
		softAssert.assertTrue(loginPageMethods.registeredUserLogin(TestData.get("Email"), TestData.get("Password")), "Login for registered user");
		homePageMethods.clickMenuIcon();
		softAssert.assertTrue(loginPageMethods.checkLogin(), "Login status in side menu failed");
		softAssert.assertAll();
	}

	@Test(description = "Verify SignIn with Facebook", groups = { "Login", "Regression" }, enabled = true)
	public void verifyLoginWithFB() {
		softAssert = new SoftAssert();
		homePageMethods.clickMenuIcon();
		Assert.assertTrue(loginPageMethods.loginWithFB(TestData.get("Email"), TestData.get("Password")),"Login with FaceBook failed");
		homePageMethods.clickMenuIcon();
		softAssert.assertTrue(loginPageMethods.checkLogin(), "Login status in side menu");
		softAssert.assertAll();
	}
	
	@Test(description = "Verify SignIn with G+", groups ={ "Login", "Regression"}, enabled = false)
	public void verifyLoginWithGPlus(){
		softAssert = new SoftAssert();
		homePageMethods.clickMenuIcon();
		Assert.assertTrue(loginPageMethods.loginWithGPlus(TestData.get("Email"), TestData.get("Password")),"Login with G+");
		homePageMethods.clickMenuIcon();
		softAssert.assertTrue(loginPageMethods.checkLogin(), "Login status in side menu");
		softAssert.assertAll();
	}
	
	@BeforeMethod
	public void takeToHome(){
		WebBaseMethods.clearBrowserSessionCookie(driver);
		driver.get(wapUrl);
		WaitUtil.sleep(2000);
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}
