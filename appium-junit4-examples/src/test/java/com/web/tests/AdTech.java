package com.web.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.ExcelUtil;
import common.utilities.reporting.ScreenShots;
import common.utilities.reporting.SendEmail;
import web.pagemethods.AdTechMethods;
import web.pagemethods.HeaderPageMethods;
import web.pagemethods.LoginPageMethods;
import web.pagemethods.WebBaseMethods;

public class AdTech extends BaseTest {

	LoginPageMethods loginMethodMethods;
	AdTechMethods adTechMethods;
	String baseUrl;
	Map<String, String> TestData = new HashMap<String, String>();
	String[] sendTo = new String[] { "navdeep.gill@timesinternet.in" };
	String html = "<h3 align='center'><span style='color: #000000; font-family: Tahoma; font-size: medium;'>Ad Automation Report</span></h3>"
			+ "<table border='2' width='100%'><tbody><tr><td align='center' bgcolor='#e7f064' width='15%'><strong>"
			+ "<span style='color: #000000; font-family: Tahoma; font-size: 15px;'>Test Case</span></strong></td>"
			+ "<td align='center' bgcolor='#e7f064' width='15%'><strong><span style='color: #000000; font-family: Tahoma; font-size: 15px;'>"
			+ "No. of Ads</span></strong></td><td align='center' bgcolor='#e7f064' width='15%'><strong><span style='color: #000000; font-family: Tahoma; font-size: 15px;'>"
			+ "Screenshot of Page</span></strong></td></tr>";

	String dataSheet = ".//src//main//resources//testdata//web//ListUrls.xlsx";
	File fileName1;
	File fileName2;
	File fileName3;
	private int columbiaAds;
	private int otherAds;
	ScreenShots screenshots;

	@BeforeClass
	public void launchClass() throws IOException {
		baseUrl = Config.fetchConfigProperty("WebUrl");
		launchBrowser(baseUrl);
		adTechMethods = new AdTechMethods(driver);
		loginMethodMethods = new LoginPageMethods(driver);
		screenshots = new ScreenShots();
		/*TestData = ExcelUtil.getTestDataRow("TestDataSheet", "VerifyAdlite", 1);
		String email = TestData.get("Email");
		String password = TestData.get("Password");
		new HeaderPageMethods(driver).clickOnSignInLink();
		loginMethodMethods.registeredUserLogin(email, password);
		*/
	}

	@AfterMethod
	public void clearCookies() {
		WebBaseMethods.clearBrowserSessionCookie(driver);
	}

	@Test(dataProvider = "url")
	public void checkAdLiteSubscription(String url,String name) {
		System.out.println("url-> "+url);
		WebBaseMethods.navigateTimeOutHandle(url);
		WebBaseMethods.refreshTimeOutHandle();
		TestData = ExcelUtil.getTestDataRow("TestDataSheet", "VerifyAdlite", 1);
		String email = TestData.get("Email");
		String password = TestData.get("Password");
		new HeaderPageMethods(driver).clickOnSignInLink();
		loginMethodMethods.registeredUserLogin(email, password);
		
		columbiaAds = adTechMethods.findColumbiaOtherSponsored();
		otherAds = adTechMethods.getAdsIFrameCount();
		screenshots.takeScreenshotAnotherDriver(driver,"adLite"+name);
		fileName1 = screenshots.getFile();
		html += "<tr><td style='font-size: 15px;' align='center'  width='15%'>Ad Lite Subscription Verification<br>On URL: "+url
				+ "</td><td style='font-size: 15px;' align='left'  width='15%'>Columbia Ads: " + columbiaAds + "<br>External Ads: " + otherAds
				+ "</td><td style='font-size: 15px;' align='center' width='15%'>" + fileName1.getName() + "</td></tr>";
	}

	@Test(dataProvider = "url",enabled=false)
	public void checkAdFreeSubscription(String url,String name) {
		WebBaseMethods.navigateTimeOutHandle(url);
		WebBaseMethods.refreshTimeOutHandle();
		TestData = ExcelUtil.getTestDataRow("TestDataSheet", "VerifyAdsFree", 1);
		String email = TestData.get("Email");
		String password = TestData.get("Password");
		new HeaderPageMethods(driver).clickOnSignInLink();
		loginMethodMethods.registeredUserLogin(email, password);
		columbiaAds = adTechMethods.findColumbiaOtherSponsored();
		otherAds = adTechMethods.getAdsIFrameCount();
		screenshots.takeScreenshotAnotherDriver(driver,"adFree"+name);
		fileName2 = screenshots.getFile();
		html += "<tr><td style='font-size: 15px;' align='center'  width='15%'>Ad Free Subscription Verification<br>" + email + "/" + password+"<br>On URL: "+url
				+ "</td><td style='font-size: 15px;' align='left'  width='15%'>Columbia Ads: " + columbiaAds + "<br>External Ads: " + otherAds
				+ "</td><td style='font-size: 15px;' align='center' width='15%'>" + fileName2.getName() + "</td></tr>";
	}

	@Test(dataProvider = "url", enabled = false)
	public void checkNoSubscription(String url,String name) {
		TestData = ExcelUtil.getTestDataRow("TestDataSheet", "VerifyLogin", 1);
		String email = TestData.get("Email");
		String password = TestData.get("Password");
		new HeaderPageMethods(driver).clickOnSignInLink();
		loginMethodMethods.registeredUserLogin(email, password);
		columbiaAds = adTechMethods.findColumbiaOtherSponsored();
		otherAds = adTechMethods.getAdsIFrameCount();
		screenshots.takeScreenshotAnotherDriver(driver,"noSubs"+name);
		fileName3 = screenshots.getFile();
		html += "<tr><td style='font-size: 15px;' align='center'  width='15%'>No Subscription Ad Verification<br>" + email + "/" + password
				+ "</td><td style='font-size: 15px;' align='left'  width='15%'>Columbia Ads: " + columbiaAds + "<br>External Ads: " + otherAds
				+ "</td><td style='font-size: 15px;' align='center' width='15%'>" + fileName3.getName() + "</td></tr>";
	}

	@DataProvider(name = "url")
	public String[][] getExcelData() {
		File file = new File(dataSheet);
		ExcelUtil excelUtil = new ExcelUtil(file);
		String sheetName = "Sheet1";
		System.out.println(excelUtil.getCellData(sheetName, 1, 2));
		String[][] dataArray = new String[excelUtil.getRowCount(sheetName) - 1][2];
		for (int i = 2; i <= excelUtil.getRowCount(sheetName); i++) {
			for (int j = 0; j < 2; j++)
				dataArray[i - 2][j] = excelUtil.getCellData(sheetName, j, i);
		}
		return dataArray;
	}

	@AfterClass
	public void sendMail() {
		SendEmail.sendCustomEmail(sendTo, "Ad Tech Automation Report", html + "</tbody></table>");
	}

}
