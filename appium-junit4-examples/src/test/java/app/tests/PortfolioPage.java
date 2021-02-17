package app.tests;

import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import app.pagemethods.AlertsPromptsMethods;
import app.pagemethods.HeaderPageMethods;
import app.pagemethods.LoginPageMethods;
import app.pagemethods.MenuPageMethods;
import app.pagemethods.PortfolioPageMethods;
import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class PortfolioPage extends BaseTest {

	AppiumDriver<?> appDriver;
	SoftAssert softAssert;
	private HeaderPageMethods headerPageMethods;
	private PortfolioPageMethods portfolioPageMethods;
	private LoginPageMethods loginPageMethods;
	private MenuPageMethods menuPageMethods;
	Map<String, String> TestData;
	private AlertsPromptsMethods alertsPromptsMethods;

	@BeforeClass(alwaysRun = true)
	public void launchApp() throws IOException {
		launchBrowser();
		appDriver = iAppCommonMethods.getDriver();
		headerPageMethods = new HeaderPageMethods(appDriver);
		portfolioPageMethods = new PortfolioPageMethods(appDriver);
		loginPageMethods = new LoginPageMethods(appDriver);
		menuPageMethods = new MenuPageMethods(appDriver);
		alertsPromptsMethods = new AlertsPromptsMethods(appDriver);
		TestData = ExcelUtil.getTestDataRow("TestDataSheet", "verifyLogin", 1);
	}

	@Test(description = "Verify Portfolio landing Page for Signed In registered user", priority=3,enabled=true)
	public void verifyPortfolioForLoggedInUser() {
		softAssert = new SoftAssert();
		headerPageMethods.clickMenuIcon();
		menuPageMethods.clickMenuByLabel("Portfolio");
		Assert.assertTrue(loginPageMethods.enterCredentials(TestData.get("Email"), TestData.get("Password")),
				"Login for registered user successful from Porfolio");
		WaitUtil.sleep(6000);
		// alertsPromptsMethods.clickRatingCloseIcon();
		softAssert.assertTrue(portfolioPageMethods.isPortfolioSectionDisplayed(),
				"Create Portfolio link not present on Portfolio page");
		softAssert.assertAll();
	}

	@Test(description = "Verify successful creation and deleteion of portfolio", dependsOnMethods = {
			"verifyPortfolioForLoggedInUser" },priority=4,enabled=true)
	public void verifyPortfolioCreationAndDeletion() {
		softAssert = new SoftAssert();
		String portfolioName = portfolioPageMethods.createPortfolio();
		MobileElement editButton = portfolioPageMethods.getEditButtonElement(portfolioName);
		Assert.assertTrue(editButton != null,
				"Portfolio creation failed for portfolio name " + portfolioName + " no edit button found");
		editButton.click();
		WaitUtil.sleep(3000);
		softAssert.assertTrue(portfolioPageMethods.deletePortfolio(),
				"Portfolio deletion failed for portfolio name " + portfolioName);
		softAssert.assertAll();
	}

	@Test(description = " Verify portfolio redirection for already loggedin user", enabled = true, priority=1)
	public void verifyPortfolioforAlreadySignedInUser() {
		softAssert = new SoftAssert();
		boolean status = new LoginPage().login(softAssert, headerPageMethods, menuPageMethods, loginPageMethods,
				TestData.get("Email"), TestData.get("Password"));
		alertsPromptsMethods.clickRatingCloseIcon();
		if (status == true) {
			headerPageMethods.clickMenuIcon();
			if (BaseTest.platform.contains("ios")) {
				softAssert.assertTrue(portfolioPageMethods.clickOnCheckNowIcon(), "Check Now icon not clicked");
				WaitUtil.sleep(3000);
				softAssert.assertTrue(portfolioPageMethods.isPortfolioSectionDisplayed(),
						"Create Portfolio link not present on Portfolio page");
				softAssert.assertTrue(portfolioPageMethods.clickPortfolioMenuIcon(), "Menu icon not clicked");
			} else {
				menuPageMethods.clickMenuByLabel("Check Now");
				softAssert.assertTrue(portfolioPageMethods.isPortfolioSectionDisplayed(),
						"Create Portfolio link not present on Portfolio page");
				BaseTest.iAppCommonMethods.navigateBack(appDriver);
				headerPageMethods.clickMenuIcon();
			}
			WaitUtil.sleep(3000);
			softAssert.assertTrue(menuPageMethods.clickSettingsIcon(), "<br>-Settings icon not shown");
			softAssert.assertTrue(menuPageMethods.clickLogoutSettingsPage(), "Unable to logout the user");
			softAssert.assertAll();

		}
	}

	@Test(description = " Verify portfolio watchlist creation and deletion  for already loggedin user", enabled = true,priority=2)
	public void verifyPortfoliowatchlistforAlreadySignedInUser() {
		softAssert = new SoftAssert();
		boolean status = new LoginPage().login(softAssert, headerPageMethods, menuPageMethods, loginPageMethods,
				TestData.get("Email"), TestData.get("Password"));
		alertsPromptsMethods.clickRatingCloseIcon();
		if (status == true) {
			headerPageMethods.clickMenuIcon();
			if (BaseTest.platform.contains("ios")) {
				softAssert.assertTrue(portfolioPageMethods.clickOnCheckNowIcon(), "Check Now icon not clicked");
			} else {
				menuPageMethods.clickMenuByLabel("Check Now");
			}
			softAssert.assertTrue(portfolioPageMethods.clickonWatchListIcon(),
					"Click on Watchlist  on portfolio failed");
			WaitUtil.sleep(6000);
			softAssert.assertTrue(portfolioPageMethods.clickonWatchListaddIcon(),
					"Click on Add watchlist icon on portfolio page failed");
			softAssert.assertTrue(portfolioPageMethods.nameWatchlistandSave(),
					"Click on name  watchlist and save  icon on portfolio page failed");
			softAssert.assertTrue(portfolioPageMethods.verifyWatchlistAdded(),
					" watchlist addition on portfolio page failed");
			softAssert.assertTrue(portfolioPageMethods.verifyDeleteWatchlist(),
					"Watchlist deletion on portfolio failed");
			if (BaseTest.platform.contains("ios")) {
				softAssert.assertTrue(portfolioPageMethods.clickPortfolioMenuIcon(), "Menu icon not clicked");
			} else {
				loginPageMethods.backAndmenuClick();
			}
			WaitUtil.sleep(3000);
			softAssert.assertTrue(menuPageMethods.clickSettingsIcon(), "<br>-Settings icon not shown");
			softAssert.assertTrue(menuPageMethods.clickLogoutSettingsPage(), "Unable to logout the user");
			softAssert.assertAll();

		}
	}

	@AfterClass(alwaysRun = true)
	public void quit() throws Exception {
		appDriver.quit();
	}

}
