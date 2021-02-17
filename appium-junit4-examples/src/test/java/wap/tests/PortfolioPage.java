package wap.tests;

import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.WaitUtil;
import wap.pagemethods.HomePageMethods;
import wap.pagemethods.LoginPageMethods;
import wap.pagemethods.PortfolioPageMethods;
import web.pagemethods.WebBaseMethods;

public class PortfolioPage extends BaseTest{
	private String wapUrl;
	private HomePageMethods homePageMethods;
	private LoginPageMethods loginPageMethods;
	private PortfolioPageMethods portfolioPageMethods;
	private SoftAssert softAssert;
	Map<String, String> TestData;
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl =BaseTest.baseUrl;
		launchBrowser(wapUrl);
		homePageMethods = new HomePageMethods(driver);
		loginPageMethods = new LoginPageMethods(driver);
		portfolioPageMethods = new PortfolioPageMethods(driver);
		TestData = ExcelUtil.getTestDataRow("TestDataSheet", "verifyLogin", 1);
	}
	
	@Test(description = "Verify Portfolio landing Page for Signed In registered user", groups = "Porfolio Page")
	public void verifyPortfolioForLoggedInUser() {
		softAssert = new SoftAssert();
		homePageMethods.clickMenuIcon();
		softAssert.assertTrue(loginPageMethods.registeredUserLogin(TestData.get("Email"), TestData.get("Password")), "Login for registered user");
		WaitUtil.sleep(500);
		Assert.assertTrue(homePageMethods.openTopSection("Portfolio"),"Unable to open Portfolio section");
		softAssert.assertTrue(portfolioPageMethods.isPortfolioSectionDisplayed(),"My Portfolio link not present on page");
		WebBaseMethods.clearBrowserSessionCookie(driver);
		softAssert.assertAll();
	}
	
	
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
