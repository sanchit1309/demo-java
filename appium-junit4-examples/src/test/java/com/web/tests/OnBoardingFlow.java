package com.web.tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections.map.HashedMap;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import web.pagemethods.Articleshow_newPageMethods;
import web.pagemethods.ETSharedMethods;
import web.pagemethods.HeaderPageMethods;
import web.pagemethods.LoginPageMethods;
import web.pagemethods.NewHomePageMethods;
import web.pagemethods.OnBoardingFlowMethods;
import web.pagemethods.SaveAndHistoryPageMethods;
import web.pagemethods.WebBaseMethods;

public class OnBoardingFlow extends BaseTest {

	private String baseUrl;
	NewHomePageMethods newHomePageMethods;
	LoginPageMethods loginPageMethods;
	HeaderPageMethods headerPageMethods;
	OnBoardingFlowMethods onBoardingFlowMethods;
	Map<String, String> TestData = new HashMap<String, String>();
	String email;
	String password;
	SoftAssert softAssert;

	@BeforeClass
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		launchBrowser(baseUrl);
		newHomePageMethods = new NewHomePageMethods(driver);
		loginPageMethods = new LoginPageMethods(driver);
		headerPageMethods = new HeaderPageMethods(driver);
		onBoardingFlowMethods = new OnBoardingFlowMethods(driver);
		Random rand01 = new Random();
		int randomNumber = rand01.nextInt(3);
		TestData = ExcelUtil.getTestDataRow("OnBoardingFlowTestData", "VerifyLogin", randomNumber + 1);
		email = TestData.get("Email");
		password = TestData.get("Password");
		verifyUserLogin();
	}

	@Test(description = "This test verifies the onboarding flow for the user on ET")
	public void verifyOnBoardingFlow() {
		softAssert = new SoftAssert();
		try {
			Map<String, String> userPreferencesData = userInputPreferences();
			Assert.assertTrue(headerPageMethods.clickMyPreferencesTab(),
					"User is not able to click on the My preferences tab. User details: user email id: " + email
							+ " password: " + password);
			WaitUtil.sleep(2000);
			WebBaseMethods.switchToChildWindow(5000);
			WaitUtil.sleep(2000);
			Assert.assertTrue(onBoardingFlowMethods.clickEditPreferencesButton(),
					"User is not able to click on the editPreferencesTab. User details: user email id: " + email
							+ " password: " + password);
			WaitUtil.sleep(2000);
			Assert.assertTrue(onBoardingFlowMethods.clickWelcomeScreenContinueButton(),
					"User is not able to click on the welcome screen continue button. User details: user email id: "
							+ email + " password: " + password);
			Assert.assertTrue(onBoardingFlowMethods.fillDataOnPersonaliseYourETExperienceScreen(userPreferencesData),
					"User is not able to fill all the data on the Personalise your ET experience screen. User details: user email id: "
							+ email + " password: " + password);
			WaitUtil.sleep(3000);
			Assert.assertTrue(onBoardingFlowMethods.fillDataOnIndustriesOnYourScreen(userPreferencesData),
					"User is not able to fill all the data on the Industries on your Radar screen. User details: user email id: "
							+ email + " password: " + password);
			WaitUtil.sleep(3000);
			Assert.assertTrue(onBoardingFlowMethods.fillDataOnTopicsOfYourInterestScreen(userPreferencesData),
					"User is not able to fill all the data on the Topics of your interest screen. User details: user email id: "
							+ email + " password: " + password);
			WaitUtil.sleep(3000);
			Assert.assertTrue(onBoardingFlowMethods.fillDataOnWhenYouPreferToRead(userPreferencesData),
					"User is not able to fill all the data on the Personalise your ET experience screen. User details: user email id: "
							+ email + " password: " + password);
			WaitUtil.sleep(3000);
			// Assert.assertTrue(headerPageMethods.clickMyPreferencesTab(),
			// "User is not able to click on the My preferences tab. User
			// details: user email id: " + email
			// + " password: " + password);
			// WebBaseMethods.switchToChildWindow(5000);
			WaitUtil.waitForLoad(driver);
			WaitUtil.sleep(2000);
			Map<String, String> userPreferencesShownOnUI = onBoardingFlowMethods.getUserFinalPreferencesMap();
			userPreferencesData.forEach((field, value) -> {
			String	intialValueGiven = value.replaceAll(" ", "");
			String valueShownOnUi = userPreferencesShownOnUI.get(field).replaceAll(" ", "");
				
				System.out.println("field: " + field + " =====---===== input value: " + value
						+ " =====---===== value shown on UI: " + userPreferencesShownOnUI.get(field));
				System.out.println("field: " + field + " =====---===== input value: " + intialValueGiven
						+ " =====---===== value shown on UI: " + valueShownOnUi);
				softAssert.assertTrue(intialValueGiven.equalsIgnoreCase(valueShownOnUi),
						"The value shown on UI " + valueShownOnUi
								+ " under my preferences for field: " + field
								+ " is not matching with the input value: " + intialValueGiven + ". User details: user email id: "
								+ email + " password: " + password);

			});
			WebBaseMethods.switchToParentClosingChilds();
		} catch (Exception ee) {
			ee.printStackTrace();
			softAssert.assertTrue(false, "Exception occured during the test run. User details: user email id: " + email
					+ " password: " + password);

		}
		softAssert.assertAll();

	}

	public void verifyUserLogin() {
		if (headerPageMethods.checkUserLoggedInState()) {
			boolean flag = headerPageMethods.logOutIfUserIsLoggedIn();
			Assert.assertTrue(flag, "Unable to logout the user as some user was already logged in");

		}
		Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "Unable to find sign in button");

		Assert.assertTrue(loginPageMethods.registeredUserLogin(email, password),
				"Unable to fill entries in the login window");
		WaitUtil.sleep(3000);
		if (driver.getCurrentUrl().contains("interstitial")) {
			ETSharedMethods.clickETLinkInterstitialPage();
		}

		Assert.assertTrue(headerPageMethods.checkUserLoggedInState(), "The user is not able to login");

	}

	public Map<String, String> userInputPreferences() {
		Map<String, String> userPreferences = new HashedMap();
		try {
			String[] fields = { "industry", "jobRole", "experience", "jobLevel", "categories", "topicOfInterest",
					"weekdays", "weekend" };
			List<String> fieldsLi = Arrays.asList(fields);
			fieldsLi.forEach(field -> {
				String value = getRandomValueFromDataSheet("OnBoardingFlowTestData", field);
				userPreferences.put(field, value);
			});
			System.out.println(userPreferences);
			return userPreferences;
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		return userPreferences;

	}

	public String getRandomValueFromDataSheet(String sheetName, String fieldName) {
		Map<String, String> testDataFromExcel = new HashMap<String, String>();
		Random rand01 = new Random();
		int randomNumber = rand01.nextInt(5);
		testDataFromExcel = ExcelUtil.getTestDataRow(sheetName, fieldName, randomNumber + 1);
		String value = testDataFromExcel.get("value");
		return value;
	}
}
