package wap.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.ExcelUtil;
import common.utilities.FileUtility;
import common.utilities.WaitUtil;
import wap.pagemethods.AdTechMethods;
import wap.pagemethods.HomePageMethods;
import wap.pagemethods.LoginPageMethods;
import web.pagemethods.WebBaseMethods;

public class AdLite extends BaseTest {
	LoginPageMethods loginMethodMethods;
	AdTechMethods adTechMethods;
	HomePageMethods homePageMethods;
	String baseUrl;
	String devUrl = "https://spmdev8243.indiatimes.com";
	String pwaUrl = "https://spmdev8243.indiatimes.com/default_pwa.cms";
	int count = 0;
	Map<String, String> TestData = new HashMap<String, String>();
	String filePath = ".//src//main//resources//properties//AllPagesAdLocation.properties";
	String dataSheet = ".//src//main//resources//testdata//wap//AdliteUrls.xlsx";

	
	File file = new File(filePath);
	private SoftAssert softAssert;

	@BeforeClass
	public void beforeClass() throws IOException {
		baseUrl = Config.fetchConfigProperty("HomeUrl");
		launchBrowser(baseUrl);
		adTechMethods = new AdTechMethods(driver);
		loginMethodMethods = new LoginPageMethods(driver);
		homePageMethods = new HomePageMethods(driver);
		FileUtility.deleteFile(filePath);
		//FileUtility.createFile(filePath);
		TestData = ExcelUtil.getTestDataRow("TestDataSheet", "VerifyAdLiteVersion", 1);
		String email = TestData.get("Email");
		String password = TestData.get("Password");
		driver.get(devUrl);
		//new HeaderPageMethods(driver).clickOnSignInLink();
		homePageMethods.clickMenuIcon();
		Assert.assertTrue(loginMethodMethods.registeredUserLogin(email, password),"The login with the registered user is not working");
	}

	@Test(dataProvider = "url", description = "This test verifies the Adlite version", enabled = false)
	public void checkAdlite(String url, String pageName) {

		WebBaseMethods.navigateTimeOutHandle(baseUrl + url);
		WaitUtil.sleep(2000);
		WebBaseMethods.scrollToBottom();
		List<String> adIframes = adTechMethods.getAdIframeIds();
		adIframes.forEach(iframe -> {
			FileUtility.writePropertiesFile(filePath, pageName + "-Iframe-" + count++, iframe);

		});

		count = 0;
		WaitUtil.sleep(3000);
		// Map<String, String> adIframe =
		// adTechMethods.getPageSpecificAdIframes(pageName, file);
		// adIframe.forEach((key, value) -> System.out.println(key + ":" +
		// value));

		WebBaseMethods.navigateTimeOutHandle(devUrl + url);
		WaitUtil.sleep(6000);
		WebBaseMethods.scrollToBottom();
		adIframes = adTechMethods.getAdIframeIds();
		if (adIframes.size() > 1)
			System.out.println("The " + pageName + " is showing more than one Ads");
		softAssert.assertTrue(adIframes.size() < 2, pageName + " shows more than one ad");
		softAssert.assertAll();
	}

	@Test(dataProvider = "url", description = "This test verifies the Adlite version on dev only", priority =0)
	public void checkAdliteDev(String url, String pageName) {
		
		softAssert = new SoftAssert();

		WebBaseMethods.navigateTimeOutHandle(devUrl + url);
		WebBaseMethods.slowScroll(10);
		WebBaseMethods.scrollToBottom();
		WaitUtil.sleep(2000);
		List<String> adIframes = adTechMethods.getAdIframeIds();
		if (adIframes.size() > 1)
			System.out.println(pageName + " section is showing more than one Ads. Url : "+ url);
		softAssert.assertTrue(adIframes.size() < 2, pageName + " shows more than one ad");
		adIframes.forEach(id -> {

			softAssert.assertTrue(id.contains("Mrec"),
					"The ads shown on the page " + pageName + " is other than the MREC add is " + id);

		});
		int columbiaAds = adTechMethods.findColumbiaOtherSponsored();
		softAssert.assertTrue(columbiaAds == 0, "on page " + pageName + " colombia ads are shown on loading the page");
		softAssert.assertAll();

	}
	
	@Test(description = "This test verifies the Adlite version on PWA Home dev", priority = 1, enabled = false)
	public void checkAdlitePWAHomedev() {
		softAssert = new SoftAssert();
		driver.get(pwaUrl);
		homePageMethods.clickMenuIcon();
		//Assert.assertTrue(loginMethodMethods.registeredUserLogin(email, password),"Login with registered user is not working");
		WebBaseMethods.slowScroll(20);
		WebBaseMethods.scrollToBottom();
		WaitUtil.sleep(2000);
		List<String> adIframes = adTechMethods.getAdIframeIds();
		if (adIframes.size() > 1)
			System.out.println(" Home page is displaying more than one Ads.");
		softAssert.assertTrue(adIframes.size() < 2, " Home page has more than one ad");
		adIframes.forEach(id -> {

			softAssert.assertTrue(id.contains("Mrec"),
					"The ads shown on home page is other than the MREC add is " + id);

		});
		int columbiaAds = adTechMethods.findColumbiaOtherSponsored();
		softAssert.assertTrue(columbiaAds == 0, "on home page colombia ads are shown on loading the page");
		softAssert.assertAll();
	}
	
	@Test(description = "This test verifies the Adlite version on PWA Home dev", priority =2, enabled = false)
	public void checkAdlitePWASectiondev() {
		softAssert = new SoftAssert();
		driver.get(pwaUrl);
		homePageMethods.clickMenuIcon();
		//Assert.assertTrue(loginMethodMethods.registeredUserLogin(email, password),"Login with registered user is not working");
		WebBaseMethods.slowScroll(20);
		WebBaseMethods.scrollToBottom();
		WaitUtil.sleep(2000);
		List<String> adIframes = adTechMethods.getAdIframeIds();
		if (adIframes.size() > 1)
			System.out.println(" Home page is displaying more than one Ads.");
		softAssert.assertTrue(adIframes.size() < 2, " Home page has more than one ad");
		adIframes.forEach(id -> {

			softAssert.assertTrue(id.contains("Mrec"),
					"The ads shown on home page is other than the MREC add is " + id);

		});
		int columbiaAds = adTechMethods.findColumbiaOtherSponsored();
		softAssert.assertTrue(columbiaAds == 0, "on home page colombia ads are shown on loading the page");
		softAssert.assertAll();
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

}
