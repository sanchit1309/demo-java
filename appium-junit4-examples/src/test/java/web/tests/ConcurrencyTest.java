package web.tests;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.utilities.ExcelUtil;
import common.utilities.WaitUtil;

public class ConcurrencyTest {

	private SoftAssert softAssert;
	private static String testDataSheet = "./src/main/resources/testdata/web/TestDataAPI.xlsx";
	private Map<String, String> apiMap = new LinkedHashMap<String, String>();
	private WebDriver driver;

	@BeforeMethod
	public void beforeSuite() {
		Apis();
		// Keys=Dashboard, CreatePortfolio, EditPortfolio,
		// DeletePortfolio,ViewMFTransactionDR
		// AddMFTransaction, EditMFTransaction, DeleteMF, CreateWatchlist
	}

	@Test
	@Parameters({ "email", "password", "portfolio", "watchlist" })
	public void parallelTest(@Optional("navdeep.gill@timesinternet.in") String email,
			@Optional("P@ssw0rd") String password, @Optional("TPortfolio22") String portfolio,
			@Optional("WList21") String watchlist) throws IOException {
		softAssert = new SoftAssert();
		String browserDriver = !(Platform.getCurrent().toString().equalsIgnoreCase("MAC")) ? "geckodriver.exe"
				: "geckodriver_mac";
		FirefoxOptions firefoxoptions = new FirefoxOptions();
		System.setProperty("webdriver.gecko.driver", "./src/main/resources/drivers/" + browserDriver);
		firefoxoptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		firefoxoptions.setCapability("webdriver.log.driver", "OFF");
		driver = new FirefoxDriver();
		WaitUtil.sleep(5000);
		driver.get("http://etportfolio.indiatimes.com/");
		WaitUtil.sleep(2000);
		String handle = driver.getWindowHandle();
		driver.findElement(By.xpath("//button[text()='SIGN IN']")).click();
		WaitUtil.sleep(2000);
		int size = driver.getWindowHandles().size();
		String childHandle = driver.getWindowHandles().toArray(new String[size])[size - 1];
		driver.switchTo().window(childHandle);
		WaitUtil.sleep(2000);
		driver.findElement(By.name("emailId")).sendKeys(email);
		driver.findElement(By.xpath("//input[@value='Sign In']")).click();
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.xpath("//input[@value='Sign In']")).click();
		driver.switchTo().window(handle);
		driver.get(String.format(apiMap.get("CreatePortfolio"), portfolio));
		WaitUtil.sleep(1000);
		String portfolioId = driver.findElement(By.xpath("//table//tr[contains(@id,'portfolioId')]/td[2]")).getText();
		System.out.println(portfolioId);
		driver.get(apiMap.get("DashboardDR"));
		softAssert.assertTrue(driver
				.findElements(By.xpath(String
						.format("//td[contains(@class,'treeValueCell')]//span[contains(text(),'%s')]", portfolioId)))
				.size() == 1, "Portfolio not created name:" + portfolio);
		String newPortName = "changedName";
		driver.get(String.format(apiMap.get("EditPortfolio"), portfolioId, newPortName));
		softAssert.assertTrue(
				driver.findElements(By.xpath("//span[contains(text(),'" + newPortName + "')]")).size() > 0,
				"Changed portfolio name not found:" + newPortName);
		driver.get(String.format(apiMap.get("DeletePortfolio"), portfolioId));
		driver.get(apiMap.get("DashboardDR"));
		softAssert.assertTrue(driver
				.findElements(By.xpath(String
						.format("//td[contains(@class,'treeValueCell')]//span[contains(text(),'%s')]", portfolioId)))
				.size() == 0, "Portfolio not deleted id:" + portfolioId);
		String schemeCode = "29162";
		driver.get(String.format(apiMap.get("AddMFTransaction"), portfolioId, schemeCode));
		String transactionId = driver.findElement(By.xpath("//table//tr[contains(@id,'transactionId')]/td[2]"))
				.getText();
		driver.get(apiMap.get("ViewMFTransactionDR"));
		softAssert.assertTrue(driver
				.findElements(By.xpath(String
						.format("//td[contains(@class,'treeValueCell')]//span[contains(text(),'%s')]", portfolioId)))
				.size() == 0, "Scheme id added not shown:" + portfolioId);
		driver.get(String.format(apiMap.get("EditMFTransaction"), transactionId));
		String watchList = "testWL";
		driver.get(String.format(apiMap.get("CreateWatchlist"), watchList));
		String watchlistId = driver.findElement(By.xpath("//table//tr[contains(@id,'watchListId')]/td[2]")).getText();
		driver.get(String.format(apiMap.get("ViewWatchlistDR"), watchlistId));
		softAssert.assertTrue(driver
				.findElements(By.xpath(String
						.format("//td[contains(@class,'treeValueCell')]//span[contains(text(),'%s')]", watchlistId)))
				.size() > 0, "Watchlist Id not found");
		driver.get(String.format(apiMap.get("DeleteWatchlist"), watchList));
		softAssert.assertTrue(driver
				.findElements(By.xpath(String
						.format("//td[contains(@class,'treeValueCell')]//span[contains(text(),'%s')]", watchlistId)))
				.size() == 0, "Watchlist Id not deleted");
		softAssert.assertAll();

	}

	// @DataProvider(name = "user info", parallel = true)
	public static Object[][] login() {
		File file = new File(testDataSheet);
		ExcelUtil excelUtil = new ExcelUtil(file);
		String sheetName = "UserName";
		String[][] dataArray = new String[excelUtil.getRowCount(sheetName) - 1][4];
		for (int i = 2; i <= excelUtil.getRowCount(sheetName); i++) {
			for (int j = 0; j < 4; j++)
				dataArray[i - 2][j] = excelUtil.getCellData(sheetName, j, i).trim();
		}
		return dataArray;
	}

	private Map<String, String> Apis() {
		File file = new File(testDataSheet);
		ExcelUtil excelUtil = new ExcelUtil(file);
		String sheetName = "APIs";
		for (int i = 2; i <= excelUtil.getRowCount(sheetName); i++) {
			int j = 0;
			String key = excelUtil.getCellData(sheetName, j, i).trim();
			j++;
			String value = excelUtil.getCellData(sheetName, j, i).trim();
			apiMap.put(key, value);
		}

		return apiMap;
	}

}
