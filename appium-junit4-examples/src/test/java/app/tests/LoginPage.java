package app.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import app.pagemethods.AlertsPromptsMethods;
import app.pagemethods.HeaderPageMethods;
import app.pagemethods.HomePageMethods;
import app.pagemethods.LoginPageMethods;
import app.pagemethods.MenuPageMethods;
import app.pagemethods.StoryPageMethods;
import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.WaitUtil;
import io.appium.java_client.AppiumDriver;

public class LoginPage extends BaseTest {
	private SoftAssert softAssert;
	private HeaderPageMethods headerPageMethods;
	private MenuPageMethods menuPageMethods;
	private LoginPageMethods loginPageMethods;
	private HomePageMethods homePageMethods;
	private StoryPageMethods storyPageMethods;
	private AppiumDriver<?> appDriver;
	private String menuName = "Mutual Funds";
	private AlertsPromptsMethods alertsPromptsMethods;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		launchBrowser();
		appDriver = iAppCommonMethods.getDriver();
		headerPageMethods = new HeaderPageMethods(appDriver);
		menuPageMethods = new MenuPageMethods(appDriver);
		loginPageMethods = new LoginPageMethods(appDriver);
		homePageMethods = new HomePageMethods(appDriver);
		storyPageMethods = new StoryPageMethods(appDriver);
		alertsPromptsMethods = new AlertsPromptsMethods(appDriver);
	}

	@Test(description = "Verifies sign in for a registered user", priority = 0, enabled = true)
	public void verifyLogin() {
		boolean status = false;
		softAssert = new SoftAssert();
		status = doLogin(softAssert, headerPageMethods, menuPageMethods, loginPageMethods);
		alertsPromptsMethods.clickRatingCloseIcon();
		if (status == true) {
			headerPageMethods.clickMenuIcon();
			softAssert.assertTrue(menuPageMethods.clickSettingsIcon(), "<br>-Settings icon not shown");
			softAssert.assertTrue(menuPageMethods.clickLogoutSettingsPage(), "Unable to logout the user");
		}

		softAssert.assertAll();
	}

	@Test(description = "Verifies Login with FB account - WebView", priority = 1, enabled = false)
	public void LoginFB() {
		softAssert = new SoftAssert();
		Map<String, String> TestData = ExcelUtil.getTestDataRow("TestDataSheet", "verifyLogin", 1);
		boolean status = false;
		headerPageMethods.clickMenuIcon();
		if (menuPageMethods.isLoginDisplayed()) {
			menuPageMethods.clickSignInIcon();
			status = loginPageMethods.fbLoginMenu(TestData.get("Email"), TestData.get("Password"));
			softAssert.assertTrue(status, "Login with Facebook - WebView");
			if (status == true) {
				headerPageMethods.clickMenuIcon();
				softAssert.assertTrue(menuPageMethods.clickLogoutSettingsPage(), "Logout Status");
			}
		} else {
			softAssert.fail("Could not Login");
		}
		softAssert.assertAll();
	}

	@Test(description = "Verifies user is able to add comment on the  article", priority = 11, enabled = false)
	public void verifyAddArticleComment() {
		softAssert = new SoftAssert();
		boolean status = new LoginPage().doLogin(softAssert, headerPageMethods, menuPageMethods, loginPageMethods);
		headerPageMethods.clickMenuIcon();
		menuPageMethods.clickL1L2MenuByLabel(menuName, "Learn");
		WaitUtil.sleep(3000);
		if (status == true) {
			homePageMethods.getAllHeadlines().get(0).click();
			if (storyPageMethods.enterValueInCommentBox() == true) {
				softAssert.assertTrue(storyPageMethods.clickPostButton(), "Unable to click Post button");
				softAssert.assertEquals(storyPageMethods.getMessagePostComment(),
						"Thank you for commenting. You will be intimated by email when your post appears in the comment's list.",
						"Unable to post comment");
			} else {
				softAssert.fail("Could not enter comment");
			}
			loginPageMethods.backAndmenuClick();
			softAssert.assertTrue(menuPageMethods.scrollDownToSettingIcon(), "Hello");
			softAssert.assertTrue(menuPageMethods.clickLogoutSettingsPage(), "Unable to logout the user");


		}

		softAssert.assertAll();
	}

	@Test(description = "Verify sign in from experied user and check its subscription ", priority = 3, enabled = true)
	public void verifyExpiredUserSignIn() {
		softAssert = new SoftAssert();
		Map<String, String> TestData = ExcelUtil.getTestDataRow("TestDataSheet", "verifyExpiredUserSignIn", 1);
		boolean status = new LoginPage().login(softAssert, headerPageMethods, menuPageMethods, loginPageMethods,
				TestData.get("Email"), TestData.get("Password"));
		alertsPromptsMethods.clickRatingCloseIcon();
		if (status == true) {
			headerPageMethods.clickMenuIcon();
			softAssert.assertTrue(loginPageMethods.clickOnSettingButton(), " Unable to click on Settings button");
			softAssert.assertTrue(loginPageMethods.clickOnMysubscriptionButton(),
					" Unable to click on Mysubscription button");
			softAssert.assertTrue(loginPageMethods.checkSubscription("Expired"),
					"Experied status of the user does not match on subscription page");
			iAppCommonMethods.navigateBack(appDriver);
			softAssert.assertTrue(menuPageMethods.clickLogoutSettingsPage(), "Unable to logout the user");

		}

		softAssert.assertAll();
	}

	@Test(description = "Verify sign in from Cancelled user and check its subscription ", priority = 4, enabled = true)
	public void verifyCancelledUserSignIn() {
		softAssert = new SoftAssert();
		Map<String, String> TestData = ExcelUtil.getTestDataRow("TestDataSheet", "verifyCancelledUserSignIn", 1);
		boolean status = new LoginPage().login(softAssert, headerPageMethods, menuPageMethods, loginPageMethods,
				TestData.get("Email"), TestData.get("Password"));
		alertsPromptsMethods.clickRatingCloseIcon();
		if (status == true) {
			headerPageMethods.clickMenuIcon();

			softAssert.assertTrue(loginPageMethods.clickOnSettingButton(), " Unable to click on Settings button");
			softAssert.assertTrue(loginPageMethods.clickOnMysubscriptionButton(),
					" Unable to click on Mysubscription button");
			softAssert.assertTrue(loginPageMethods.checkSubscription("Cancelled"),
					"Cancelled status of the user does not match on subscription page");
			iAppCommonMethods.navigateBack(appDriver);
			softAssert.assertTrue(menuPageMethods.clickLogoutSettingsPage(), "Unable to logout the user");
		}

		softAssert.assertAll();
	}

	@Test(description = "Verify sign in from Active user and check its subscription ", priority = 5)
	public void verifyActiveUserSignIn() {
		softAssert = new SoftAssert();
		Map<String, String> TestData = ExcelUtil.getTestDataRow("TestDataSheet", "verifyActiveUserSignIn", 1);
		boolean status = new LoginPage().login(softAssert, headerPageMethods, menuPageMethods, loginPageMethods,
				TestData.get("Email"), TestData.get("Password"));
		alertsPromptsMethods.clickRatingCloseIcon();
		if (status == true) {
			headerPageMethods.clickMenuIcon();
			softAssert.assertTrue(loginPageMethods.clickOnSettingButton(), " Unable to click on Settings button");
			softAssert.assertTrue(loginPageMethods.clickOnMysubscriptionButton(),
					" Unable to click on Mysubscription button");
			softAssert.assertTrue(loginPageMethods.checkSubscription("Active"),
					"Active status of the user does not match on subscription page");
			iAppCommonMethods.navigateBack(appDriver);
			softAssert.assertTrue(menuPageMethods.clickLogoutSettingsPage(), "Unable to logout the user");
		}

		softAssert.assertAll();
	}

	@Test(description = "Verify sign in from user with no subscription and check its subscription status ", priority = 2)
	public void verifynosubscriptionUserSignIn() {
		softAssert = new SoftAssert();
		Map<String, String> TestData = ExcelUtil.getTestDataRow("TestDataSheet", "verifynosubscriptionUserSignIn", 1);
		boolean status = new LoginPage().login(softAssert, headerPageMethods, menuPageMethods, loginPageMethods,
				TestData.get("Email"), TestData.get("Password"));
		if (status == true) {
			alertsPromptsMethods.clickRatingCloseIcon();
			headerPageMethods.clickMenuIcon();
			softAssert.assertTrue(loginPageMethods.clickOnSettingButton(), " Unable to click on Settings button");
			softAssert.assertTrue(loginPageMethods.clickOnMysubscriptionButton(),
					" Unable to click on Mysubscription button");
			softAssert.assertTrue(loginPageMethods.checkNoSubscriptionError(),
					"No subscription status of the user  without any subscription does not match on subscription page");
			iAppCommonMethods.navigateBack(appDriver);
			softAssert.assertTrue(menuPageMethods.clickLogoutSettingsPage(), "Unable to logout the user");
		}

		softAssert.assertAll();
	}

	@Test(description = "Verify Login from settings Page", priority = 6)
	public void verifyLoginfromSettingsPage() {
		softAssert = new SoftAssert();
		Assert.assertTrue(headerPageMethods.clickMenuIcon(), "Clicking On menu Icon failed");
		Assert.assertTrue(loginPageMethods.clickOnSettingButton(), "clicking on settings Button failed");
		Assert.assertTrue(loginPageMethods.clickOnSignInFromSettingsPage(),
				"Clicking On Sign In On Setting page failed");
		Map<String, String> TestData = ExcelUtil.getTestDataRow("TestDataSheet", "verifyLogin", 1);
		Boolean status = loginPageMethods.enterCredentials(TestData.get("Email"), TestData.get("Password"));
		softAssert.assertTrue(status, "Unable to enter login credentials");
		if (status == true) {
			headerPageMethods.clickMenuIcon();
			softAssert.assertTrue(menuPageMethods.clickSettingsIcon(), "<br>-Settings icon not shown");
			softAssert.assertTrue(menuPageMethods.clickLogoutSettingsPage(), "Unable to logout the user");
		}
		softAssert.assertAll();

	}

	@Test(description = " Verify non prime user login from Top header", priority = 7)
	public void verifyNonSubscribedLoginFromHeader() {
		softAssert = new SoftAssert();
		softAssert.assertTrue(loginPageMethods.clickOnTopHeadersignInButton(),
				"SingIn Button On top header is not clickable");
		Map<String, String> TestData = ExcelUtil.getTestDataRow("TestDataSheet", "verifynosubscriptionUserSignIn", 1);
		Boolean status = loginPageMethods.enterCredentials(TestData.get("Email"), TestData.get("Password"));
		softAssert.assertTrue(status, "Unable to enter login credentials");
		softAssert.assertTrue(loginPageMethods.validateSubscribeToETButtonOnHeader(),
				"Subscribe to Et Button on header post login from non prime user is not present");
		headerPageMethods.clickMenuIcon();
		softAssert.assertTrue(menuPageMethods.clickSettingsIcon(), "<br>-Settings icon not shown");
		softAssert.assertTrue(menuPageMethods.clickLogoutSettingsPage(), "Unable to logout the user");
		softAssert.assertAll();
	}

	@Test(description = " Verify  prime user login from Top header", priority = 8)
	public void verifySubscribedLoginFromHeader() {
		softAssert = new SoftAssert();
		navigateToHome();
		System.out.println("validate..");
		softAssert.assertTrue(loginPageMethods.clickOnTopHeadersignInButton(),
				"SingIn Button On top header is not clickable");
		Map<String, String> TestData = ExcelUtil.getTestDataRow("TestDataSheet", "verifyActiveUserSignIn", 1);
		Boolean status = loginPageMethods.enterCredentials(TestData.get("Email"), TestData.get("Password"));
		softAssert.assertTrue(status, "Unable to enter login credentials");
		softAssert.assertEquals(java.util.Optional.ofNullable(loginPageMethods.validateSubscribeToETButtonOnHeader()), false,
				"Subscribe to Et Button on header post login from prime user is  present");
		headerPageMethods.clickMenuIcon();
		softAssert.assertTrue(menuPageMethods.clickSettingsIcon(), "<br>-Settings icon not shown");
		softAssert.assertTrue(menuPageMethods.clickLogoutSettingsPage(), "Unable to logout the user");
		softAssert.assertAll();
	}

	@AfterClass(alwaysRun = true)
	public void quit() throws IOException {
		driver.quit();
	}

	public boolean doLogin(SoftAssert softAssert, HeaderPageMethods headerPageMethods, MenuPageMethods menuPageMethods,
			LoginPageMethods loginPageMethods) {
		boolean status = false;
		Map<String, String> TestData = new HashMap<>();
		TestData.put("Email", "etauto2409@gmail.com");
		TestData.put("Password", "Times@1234");
		headerPageMethods.clickMenuIcon();
		String portfolioIdentifier = "My Portfolio";
		portfolioIdentifier = portfolioIdentifier.toUpperCase();
		if (iAppCommonMethods.isElementDisplayed(iAppCommonMethods.getElementByText(portfolioIdentifier))) {
			menuPageMethods.clickLogout();
			headerPageMethods.clickMenuIcon();
		}
		Assert.assertTrue(menuPageMethods.isLoginDisplayed(), "Sign in icon not shown in left menu");
		menuPageMethods.clickSignInIcon();
		status = loginPageMethods.enterCredentials(TestData.get("Email"), TestData.get("Password"));
		softAssert.assertTrue(status, "Unable to enter login credentials");
		return status;
	}

	public boolean login(SoftAssert softAssert, HeaderPageMethods headerPageMethods, MenuPageMethods menuPageMethods,
			LoginPageMethods loginPageMethods, String email, String password) {
		boolean status = false;
		headerPageMethods.clickMenuIcon();
		Assert.assertTrue(menuPageMethods.isLoginDisplayed(), "Sign in icon not shown in left menu");
		menuPageMethods.clickSignInIcon();
		status = loginPageMethods.enterCredentials(email, password);
		softAssert.assertTrue(status, "Unable to enter login credentials");
		return status;
	}
	
	private boolean navigateToHome() {
		new HeaderPageMethods(appDriver).clickMenuIconSwipeUpFirst();
		return new MenuPageMethods(appDriver).clickMenuByLabel("Home");
	}

}
