package common.tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import common.launchsetup.BaseTest;
import common.utilities.DBUtil;
import common.utilities.FileUtility;
import common.utilities.WaitUtil;
import common.utilities.browser.localbrowser.LocalBrowserUtilWap;
import common.utilities.browser.localbrowser.LocalBrowserUtilWeb;
import common.utilities.reporting.ScreenShots;
import common.utilities.reporting.SendEmail;
import wap.pagemethods.HomePageMethods;
import web.pagemethods.ETSharedMethods;
import web.pagemethods.HeaderPageMethods;
import web.pagemethods.LoginPageMethods;
import web.pagemethods.WebBaseMethods;

public class LoginTests {

	private LocalBrowserUtilWeb localBrowserUtilWeb = new LocalBrowserUtilWeb();
	private WebDriver dweb;
	private LocalBrowserUtilWap localBrowserUtilWap = new LocalBrowserUtilWap();
	private WebDriver dwap;
	private HeaderPageMethods headerPageMethodsWeb;
	private LoginPageMethods loginPageMethodsWeb;
	private HomePageMethods homePageMethodsWap;
	private wap.pagemethods.LoginPageMethods loginPageMethodsWap;
	private String platform;
	private Date startsql;
	private String testCaseName = "Login";
	private String testCaseDetails = "This test verifies registered user login";
	private String email = "etauto2409@gmail.com";
	private String password = "Times@1234";
	private String url = "https://economictimes.indiatimes.com/";
	private boolean alarm = false;
	private String temp = "";
	private String mailRecipients = "nityananda.ghosh@timesinternet.in,lavish.mehta@timesinternet.in,abhinav.singal@timesinternet.in,rajat.chaudhary@timesinternet.in";
	private String alertMailRecipients = "lavish.mehta@timesinternet.in,sakshi.sharma@timesinternet.in,"
			+ "abhinav.singal@timesinternet.in,rajat.chaudhary@timesinternet.in,nityananda.ghosh@timesinternet.in,"
			+ "virkin.malla@timesinternet.in,c-mukarram.khan@timesinternet.in,c-sunil.kumar2@timesinternet.in,"
			+ "c-ankita.gupta@timesinternet.in,nishant.singh1@timesinternet.in";
	private boolean isFailPresent = false;
	private String emailSubject = "Report for Login";
	private ArrayList<String[]> records;
	private List<File> screenShotPaths = new ArrayList<>();
	private File currentTCScreenShot;

	@BeforeClass
	public void launchBrowser() throws IOException {
		BaseTest.browserName = "chrome";
		dweb = localBrowserUtilWeb.launchBrowser();
		FileUtility.writePropertiesFile("suiterun.properties", "browserName", "wap");
		dwap = localBrowserUtilWap.launchBrowser();
		headerPageMethodsWeb = new HeaderPageMethods(dweb);
		loginPageMethodsWeb = new LoginPageMethods(dweb);
		homePageMethodsWap = new HomePageMethods(dwap);
		loginPageMethodsWap = new wap.pagemethods.LoginPageMethods(dwap);
	}

	@Test(description = "This test verifies login by email on Web.", priority = 0)
	public void LoginWeb() {
		alarm = false;
		platform = "Web";
		startsql = new Date();
		BaseTest.driver = (RemoteWebDriver) dweb;
		ETSharedMethods.init(dweb);
		Assert.assertTrue(WebBaseMethods.navigateTimeOutHandle(dweb, url, 60), "Page did not stop loading in 60 secs");
		if (dweb.getCurrentUrl().contains("interstitial")) {
			ETSharedMethods.clickETLinkInterstitialPage();
		}
		Assert.assertTrue(headerPageMethodsWeb.clickOnSignInLink(), "Unable to find sign in button");
		Assert.assertTrue(loginPageMethodsWeb.clickContinueEmailMobile(),
				"SSO:Unable to click continue Email/Mobile button on login screen");
		Assert.assertTrue(loginPageMethodsWeb.findEmailSetValue(email), "SSO:Unable to find Email Field");
		Assert.assertTrue(loginPageMethodsWeb.clickContinueButton(),
				"Unable to click continue button on the email screen");
		Assert.assertTrue(loginPageMethodsWeb.findPasswordSetValue(password), "SSO:Unable to find Password Field");
		Assert.assertTrue(loginPageMethodsWeb.clickContinueButtonFinal(),
				"Unable to click continue button on the password screen");
		WaitUtil.sleep(2000);
		if (dweb.getCurrentUrl().contains("interstitial")) {
			ETSharedMethods.clickETLinkInterstitialPage();
		}
		Assert.assertTrue(headerPageMethodsWeb.getLoggedInUserImage(), "Logged in user image not shown");
		Assert.assertTrue(headerPageMethodsWeb.doLogout(), "Unable to logout user");
		String currUrl = dweb.getCurrentUrl();
		Assert.assertTrue(currUrl.equals(url),
				"Did not land onto:" + url + " after signing out, instead was on:" + currUrl);

	}

	@Test(description = "This test verifies login by email on WAP.", priority = 1)
	public void LoginWAP() {
		alarm = false;
		platform = "WAP";
		BaseTest.driver = (RemoteWebDriver) dwap;
		ETSharedMethods.init(dwap);
		startsql = new Date();
		Assert.assertTrue(WebBaseMethods.navigateTimeOutHandle(dwap, url, 60), "Page did not stop loading in 60 secs");
		if (dwap.getCurrentUrl().contains("interstitial")) {
			WaitUtil.sleep(6000);
		}
		if (ETSharedMethods.isGDPRShown()) {
			Assert.assertTrue(WebBaseMethods.navigateTimeOutHandle(dwap, url, 60),
					"Page did not stop loading in 60 secs");
		}
		Assert.assertTrue(homePageMethodsWap.clickFooterMenuICon(), "Unable to click menu icon");
		Assert.assertTrue(loginPageMethodsWap.clickFooterMenuSignIn(), "Unable to click Sign In button from menu");
		Assert.assertTrue(loginPageMethodsWap.clickContinueEmailMobile(),
				"SSO:Unable to click continue Email/Mobile button on login screen");
		Assert.assertTrue(loginPageMethodsWap.findEmailSetValue(email), "SSO:Unable to find Email Field");
		Assert.assertTrue(loginPageMethodsWap.clickContinueButton(),
				"Unable to click continue button on the email screen");
		Assert.assertTrue(loginPageMethodsWap.findPasswordSetValue(password), "SSO:Unable to find Password Field");
		Assert.assertTrue(loginPageMethodsWap.clickContinueButtonFinal(),
				"Unable to click continue button on the password screen");
		// softAssert.assertTrue(loginPageMethods.registeredUserLogin(TestData.get("Email"),
		// TestData.get("Password")), "Login for registered user");
		Assert.assertTrue(homePageMethodsWap.clickFooterMenuICon(), "Unable to click menu icon");
		Assert.assertTrue(loginPageMethodsWap.checkLoginReact(), "Login status in side menu is false");

	}

	public boolean getLastRecordsFlag(ArrayList<String[]> results) {
		boolean result = results.stream().allMatch(row -> (row[5].equals("FAIL") && row[8].equals("0")));
		return result;
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) throws Exception {
		String dbResult = "PASS";
		switch (result.getStatus()) {
		case ITestResult.SUCCESS:
			dbResult = "PASS";
			break;
		case ITestResult.FAILURE:
			dbResult = "FAIL";
		case ITestResult.SKIP:
			dbResult = "FAIL";
			break;
		default:
			break;
		}
		String message = "";
		String screenShotName = "";
		String screenShotPath = "";
		if (result.getStatus() == ITestResult.FAILURE || result.getStatus() == ITestResult.SKIP) {
			message = result.getThrowable().toString().replaceAll("System info:.*", "").replaceAll("Build info:.*", "")
					.replaceAll("Driver info:.*", "").replaceAll("Capabilities.*", "").replaceAll("Session.*", "")
					.replaceAll("expected \\[[^,\r\n]+\\]", "").replace("java.lang.AssertionError:", "")
					.replaceAll("<[^>]+>", "").replaceAll("\\*\\*\\* Element info\\:.*", "")
					.replaceAll(":", "");
			if (message.length() > 100)
				message = message.substring(0, message.length() - 1);
			isFailPresent = true;
			currentTCScreenShot = new ScreenShots().seleniumNativeScreenshot(BaseTest.driver,
					result.getMethod().getMethodName());
			String[] arr = currentTCScreenShot.getAbsolutePath().split("\\\\");
			screenShotPaths.add(currentTCScreenShot);
			screenShotName = arr[arr.length - 1];
			screenShotPath = currentTCScreenShot.getAbsolutePath();
			System.out.println("ScreenshotPath: " + screenShotPath);
			DBUtil.dbMonitoringInsert("monitoring", platform, startsql, testCaseName, testCaseDetails, dbResult,
					message.trim(), screenShotPath);
			records = DBUtil.selectLast3Records("Login", platform);
			alarm = alarm ? alarm : getLastRecordsFlag(records);
			System.out.println("alarm:" + alarm);
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			DBUtil.dbMonitoringInsert("monitoring", platform, startsql, testCaseName, testCaseDetails, dbResult,
					message.trim(), screenShotPath);
		}
		temp = temp + "<tr> <td style=\"text-align: center;\">Login</td> <td style=\"text-align: center;\">" + platform
				+ "</td> <td style=\"text-align: center;\">" + dbResult + "</td> <td style=\"text-align: center;\">"
				+ (message.trim().length() > 0 ? message : "N.A.") + "</td><td style=\"text-align: center;\">"
				+ (screenShotName.trim().length() > 0 ? screenShotName : "N.A.") + "</td> </tr> ";

		if (alarm) {
			String alarmHTML = "<p>Hi All,<br/><br/>Please find the last three consecutive failues in login test<br/><br/></p> <table border=\"2\" width=\"100%\"> <tbody> <tr> <td style=\"text-align: center;\" bgcolor=\"#e7f064\" width=\"15%\"><strong>Test Case Name</strong></td> <td style=\"text-align: center;\" bgcolor=\"#e7f064\" width=\"15%\"><strong>Platform</strong></td> <td style=\"text-align: center;\" bgcolor=\"#e7f064\" width=\"15%\"><strong>Time Of Run</strong></td> <td style=\"text-align: center;\" bgcolor=\"#e7f064\" width=\"15%\"><strong>Reason Of Failure(If Any)</strong></td><td style=\"text-align: center;\" bgcolor=\"#e7f064\" width=\"15%\"><strong>Screenshot Name</strong></td> </tr> ";
			screenShotPaths = new ArrayList<>();
			String failureRows = "";
			for (int i = 0; i < 3; i++) {
				screenShotPaths.add(new File(records.get(i)[7]));
				String[] arr = records.get(i)[7].split("\\\\");
				failureRows += "<tr> <td style=\"text-align: center;\">ET-Main Login</td> <td style=\"text-align: center;\">"
						+ records.get(i)[1] + "</td> <td style=\"text-align: center;\">" + records.get(i)[2]
						+ "</td> <td style=\"text-align: center;\">" + records.get(i)[6]
						+ "</td><td style=\"text-align: center;\">" + arr[arr.length - 1] + "</td> </tr> ";

			}
			System.out.println(screenShotPaths);
			File[] args = screenShotPaths.stream().toArray(File[]::new);
			alarmHTML = alarmHTML + failureRows + "</tbody></table>";
			System.out.println(alarmHTML);
			SendEmail.sendCustomEmail(alertMailRecipients.split("\\s*,\\s*"),
					"Alert: Failure " + emailSubject + "-" + platform, alarmHTML, args);
			DBUtil.updateEmailSent("Login", platform);
		}
	}

	@AfterClass(alwaysRun = true)
	public void finishUp() throws Exception {
		dweb.quit();
		dwap.quit();
		String html = "<p>Hi All,<br/><br/>Please find Login Report for ET-Main on both Web and WAP:<br/><br/></p><p style=\"text-align: center;\"><strong><span style=\"text-decoration: underline;\">Started At:"
				+ new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startsql)
				+ "</span></strong></p> <table border=\"2\" width=\"100%\"> <tbody> <tr> <td style=\"text-align: center;\" bgcolor=\"#e7f064\" width=\"15%\"><strong>Test Case Name</strong></td> <td style=\"text-align: center;\" bgcolor=\"#e7f064\" width=\"15%\"><strong>Platform</strong></td> <td style=\"text-align: center;\" bgcolor=\"#e7f064\" width=\"15%\"><strong>Pass/Fail</strong></td> "
				+ "<td style=\"text-align: center;\" bgcolor=\"#e7f064\" width=\"15%\"><strong>Reason Of Failure(If Any)</strong></td><td style=\"text-align: center;\" bgcolor=\"#e7f064\" width=\"15%\"><strong>Screenshot Name</strong></td> </tr> ";
		html = html + temp + "</tbody> </table>";
		File[] args = screenShotPaths.stream().toArray(File[]::new);
		System.out.println(html);
		emailSubject = isFailPresent ? "Failure " + emailSubject + "|" + startsql
				: "Automation " + emailSubject + "|" + startsql;
		SendEmail.sendCustomEmail(mailRecipients.split("\\s*,\\s*"), emailSubject, html, args);

	}
	

}
