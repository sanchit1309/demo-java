package com.web.tests;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.DBUtil;
import common.utilities.ExcelUtil;
import common.utilities.HTTPResponse;
import common.utilities.WaitUtil;
import common.utilities.browser.ProxyBrowserUtil;
import common.utilities.reporting.ScreenShots;
import common.utilities.reporting.SendEmail;
import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.HarEntry;
import de.sstoehr.harreader.model.HarPage;

public class Performance extends BaseTest {

	ProxyBrowserUtil proxyBrowserUtil;
	String message = "<h3 align='center'><span style='color: #000000; font-family: Tahoma; font-size: medium;'>Performance Report</span></h3><table border='2' width='100%'><tbody>"
			+ "<tr><td align='center' bgcolor='#e7f064' width='15%'><strong><span style='color: #000000; font-family: Tahoma; font-size: 15px;'>Page Under Test</span></strong></td>"
			+ "<td align='center' bgcolor='#e7f064' width='15%'><strong><span style='color: #000000; font-family: Tahoma; font-size: 15px;'>Reason for failure</span></strong></td>"
			+ "</tr>";
	String propertiesFile = "./src/main/resources/properties/benchmark.properties";
	String pageName = "";
	String dataSheet = "";
	String excelOutput = "";
	String start = "" + new Date(System.currentTimeMillis());
	private boolean firstViewTimeOut;
	private boolean rerunFlag;
	private boolean iseEntry;
	private boolean benchmarkFlag;
	private boolean timedOut;
	private String rerunMessage = "";
	private Date startsql = new Date();
	private double pageLoad = 0;
	private double domContentLoad = 0;
	private SoftAssert softAssert;

	@BeforeClass
	public void beforeClass() throws IOException {
		// launchBrowser();
		/* To be used for proxy settings only */
		// proxyBrowserUtil = ProxyBrowserUtil.getInstance();
		if (platform.equals("Web")) {
			dataSheet = ".//src//main//resources//testdata//web//PerformanceData.xlsx";
			excelOutput = ".//src//main//resources//testdata//web//PerformanceBenchmark.xlsx";
		}
		if (platform.equals("WAP")) {
			dataSheet = ".//src//main//resources//testdata//wap//PerformanceData.xlsx";
			excelOutput = ".//src//main//resources//testdata//wap//PerformanceBenchmark.xlsx";
		}
	}

	// @AfterMethod
	public void tearDown() {
		driver.quit();
	}

	// @Test(dataProvider = "Page Data", enabled = false)
	public void performance(String pageName, String url) throws HarReaderException {
		File outfile = new File(excelOutput);
		ExcelUtil excelUtil = new ExcelUtil(outfile);
		SoftAssert softAssert = new SoftAssert();
		int counter = 0;
		do {
			rerunMessage = "";
			WebDriver webDriver = new FirefoxDriver();
			webDriver.manage().timeouts().implicitlyWait(2, TimeUnit.MICROSECONDS);
			Thread t = new Thread(new Runnable() {

				public void run() {

					webDriver.get(Thread.currentThread().getName());

				}
			}, url);
			t.start();
			try {
				t.join(5000);
			} catch (InterruptedException e) { // ignore
			}
			try {
				if (t.isAlive()) { // Thread still alive, we need to abort
					System.out.println("Timeout on loading page " + url);
					t.interrupt();
					throw new TimeoutException();
				}
			} catch (TimeoutException e) {
				System.out.println("Verifying first fold load after 5 secs Timeout");
				try {
					WaitUtil.explicitWaitByVisibilityOfElement(webDriver, 1, webDriver.findElement(By.tagName("body")));
					// throw new WebDriverException();
				} catch (TimeoutException toe) {
					firstViewTimeOut = true;
					System.out.println("Timed out, trying " + (counter + 1) + " times");
					continue;
				} catch (StaleElementReferenceException se) {
					System.out.println("Stale Element Exception Thrown");
				} catch (WebDriverException we) {
					System.out.println("Unknown exception " + e.getMessage());
					rerunMessage += e.getMessage();
					rerunFlag = true;
					continue;
				}
			} catch (WebDriverException e) {
				rerunFlag = true;
				System.out.println("Error page " + webDriver.getCurrentUrl() + "rerunFlag is TRUE");
				continue;
			} finally {
				webDriver.close();
				counter++;
			}
		} while (counter < 2 && (rerunFlag || firstViewTimeOut));
		// checking HAR
		int i = 0;
		Double webLoadTime = 0.0;
		Double benchmark = 0.0;
		String iseEntryList;
		do {
			rerunMessage = "";

			i++;
			System.out.println("Running " + (i) + " time");
			webLoadTime = 0.0;
			benchmark = 0.0;
			System.out.println("Started for url " + url);

			this.pageName = pageName;
			excelUtil.setSheet(excelUtil.getWorkbook().getSheetAt(excelUtil.getWorkbook().getSheetIndex(pageName)));

			iseEntry = false;
			benchmarkFlag = false;
			timedOut = false;
			rerunFlag = false;
			File file = null;
			iseEntryList = "";
			try {
				timedOut = false;
				file = proxyBrowserUtil.returnHARFile(url);
			} catch (TimeoutException e) {
				timedOut = true;
				System.out.println("Page load time out, flag timedOut is set TRUE while grepping HAR requests");
				continue;
			} catch (WebDriverException e) {
				rerunFlag = true;
				rerunMessage += e.getMessage();
				System.out.println("Error page " + driver.getCurrentUrl() + " re run flag 'rerunFlag' is set to true");
				continue;
			}
			HarReader harReader = new HarReader();
			de.sstoehr.harreader.model.Har harRead = harReader.readFromFile(file);
			List<HarPage> pages = harRead.getLog().getPages();
			long startTime = pages.get(0).getStartedDateTime().getTime();
			List<HarEntry> hentry = harRead.getLog().getEntries();
			long entryLoadTime = 0;
			long entryLoadTimeMs = 0;
			for (HarEntry entry : hentry) {
				entryLoadTimeMs = entry.getTime();
				entryLoadTime = entry.getStartedDateTime().getTime() + entryLoadTimeMs;
				if (entry.getResponse().getStatus() >= 500) {
					if (HTTPResponse.checkResponseCode(entry.getRequest().getUrl()) >= 500) {
						iseEntryList += entry.getRequest().getUrl() + "<br>";
						iseEntry = true;
					}
				}
			}
			long loadTimeSpan = entryLoadTime - startTime;
			System.out.println("loadTimeSpan: " + loadTimeSpan);

			webLoadTime = ((double) loadTimeSpan) / 1000;
			double webLoadTimeInSeconds = Math.round(webLoadTime * 100.0) / 100.0;
			System.out.println("Web Load Time: " + webLoadTimeInSeconds);
			String percentile = new Config().fetchConfig(new File(propertiesFile), pageName + "_percentile");
			String standardDeviation = new Config().fetchConfig(new File(propertiesFile),
					pageName + "_standardDeviation");
			benchmark = Double.parseDouble(percentile) + Double.parseDouble(standardDeviation);
			System.out.println("Page Load Time =>" + webLoadTime + " Value for comparison=>" + benchmark);

			benchmarkFlag = webLoadTime > benchmark;

		} while (i < 2 && (rerunFlag || benchmarkFlag || timedOut));
		softAssert.assertFalse(iseEntry, "ISE entry found for " + iseEntryList);
		softAssert.assertFalse(firstViewTimeOut, "First view not loading after 5 seconds.");
		softAssert.assertFalse(timedOut, "Page load time out, complete page did not load in 2 mins");
		softAssert.assertFalse(benchmarkFlag, "Total page load time is exceeding the benchmark value");
		softAssert.assertFalse(rerunFlag, "Unexpected error in test " + rerunMessage);
		// Custom Email
		if (benchmarkFlag) {
			message += "<tr><td style='font-size: 15px;' align='center' width='15%'>" + url + "</td>"
					+ "<td style='font-size: 15px;' align='left' width='15%'>Page load time " + webLoadTime
					+ "(sec) is higher than the threshhold value: " + benchmark + "(sec)</td>" + "</tr>";
			excelUtil.setCellData(pageName, 0, excelUtil.getSheet().getPhysicalNumberOfRows() + 1, start, "FAIL",
					"Page load time exceeded the benchmarked value." + webLoadTime);
			// DBUtil.dbInsertPerformance(startsql, pageName, webLoadTime,
			// "FAIL", "Page load time exceeded the benchmarked value.");
		} else if (iseEntry) {
			message += "<tr><td style='font-size: 15px;' align='center' width='15%'>" + url + "</td>"
					+ "<td style='font-size: 15px;' align='left' width='15%'> Following request(s) on page is/are giving  500: <br>"
					+ iseEntryList + "</td></tr>";
			excelUtil.setCellData(pageName, 0, excelUtil.getSheet().getPhysicalNumberOfRows() + 1, start, " ",
					"FAIL " + iseEntryList.replaceAll("<[^>]+>", ""));
			// DBUtil.dbInsertPerformance(startsql, pageName, webLoadTime,
			// "FAIL", "ISE on following requests " +
			// iseEntryList.replaceAll("<[^>]+>", ""));

		} else if (timedOut) {
			message += "<tr><td style='font-size: 15px;' align='center' width='15%'>" + url + "</td>"
					+ "<td style='font-size: 15px;' align='left' width='15%'>Page load time is exceeding 2 mins</tr>";
			excelUtil.setCellData(pageName, 0, excelUtil.getSheet().getPhysicalNumberOfRows() + 1, start, 120000 + "",
					"FAIL");
			// DBUtil.dbInsertPerformance(startsql, pageName, webLoadTime,
			// "FAIL", "Page Load Timed Out:2 mins");
		} else if (firstViewTimeOut) {
			message += "<tr><td style='font-size: 15px;' align='center' width='15%'>" + url + "</td>"
					+ "<td style='font-size: 15px;' align='left' width='15%'>First view is not loading in 5 seconds</tr>";
			excelUtil.setCellData(pageName, 0, excelUtil.getSheet().getPhysicalNumberOfRows() + 1, start, 5000 + "",
					"FAIL");
			// DBUtil.dbInsertPerformance(startsql, pageName, webLoadTime,
			// "FAIL", "First View is not loading in 5 seconds");
		} else {
			excelUtil.setCellData(pageName, 0, excelUtil.getSheet().getPhysicalNumberOfRows() + 1, start,
					webLoadTime + "", "PASS");
			// DBUtil.dbInsertPerformance(startsql, pageName, webLoadTime,
			// "PASS", "");
		}
		System.out.println(message);
		softAssert.assertAll();
	}

	@Test(dataProvider = "Page Data", description = "This test check DomContentLoad and PageLoad event on page")
	public void pageLoadBenchmark(String pageName, String url) {
		softAssert = new SoftAssert();
		File outfile = new File(excelOutput);
		ExcelUtil excelUtil = new ExcelUtil(outfile);
		String avg = new Config().fetchConfig(new File(propertiesFile), pageName + "_loadTime");
		// String standardDeviation = new Config().fetchConfig(new
		// File(propertiesFile), pageName + "_standardDeviation");
		Double benchmark = Double.parseDouble(avg);
		String assertionMessage = "";
		double lowestValue = 0.0;
		int counter = 0;
		boolean rerun = false;
		do {
			rerun = false;
			assertionMessage = "";
			System.out.println("Attempt:" + (counter + 1) + " for " + url);
			try {
				launchBrowser();
				driver.get(url);
				Thread.sleep(1000);
				int count = 0;
				while (driver.getCurrentUrl().contains("interstitial") && count < 5) {
					Thread.sleep(2500);
					count++;
				}
				/*
				 * if(pageName.equalsIgnoreCase("HomePage")){
				 * ETSharedMethods.clickETLinkInterstitialPage(); }
				 */
				
			} catch (Exception e) {
				assertionMessage += "Unable to navigate to page";
				new ScreenShots().seleniumNativeScreenshot(driver,"navigatefail");
				System.out.println(assertionMessage);
				rerun = true;
			}
			if (!rerun) {
				try {
					JavascriptExecutor js = ((JavascriptExecutor) driver);
					Object navigationStart = js.executeScript("" + "return window.performance.timing.navigationStart");
					Object domLoad = js.executeScript("" + "return window.performance.timing.domContentLoadedEventEnd");
					Object completeLoad = js.executeScript("" + "return window.performance.timing.loadEventEnd");
					pageLoad = (Double.parseDouble(completeLoad.toString())
							- Double.parseDouble(navigationStart.toString())) / 1000;
					lowestValue = counter == 0 ? pageLoad : (lowestValue <= pageLoad ? lowestValue : pageLoad);
					domContentLoad = (Double.parseDouble(domLoad.toString())
							- Double.parseDouble(navigationStart.toString())) / 1000;
					if (counter < 4) {
						assertionMessage = "";
						driver.quit();
					}
					if (domContentLoad > 5) {
						assertionMessage += "First view did not load in 5 seconds, instead it took " + domContentLoad
								+ " seconds.";
						System.out.println("domContentLoad > 5 " + domContentLoad);
						rerun = true;
					}

					if (lowestValue > benchmark) {
						assertionMessage += "Complete page did not load in the set benchmark value " + benchmark
								+ " seconds, instead it took " + lowestValue + " seconds.";
						System.out.println("pageLoad > benchmark " + pageLoad + " v/s " + benchmark + " for " + url);
						rerun = true;
					}

				} catch (WebDriverException e) {
					assertionMessage += "Timed out exception, waited max time "
							+ e.getMessage().substring((e.getMessage().length()) / 2) + "...";
					System.out.println("WebDriverException occured " + e.getMessage());
					rerun = true;
				}
			}
			counter++;
		} while (counter < 5 && rerun);

		if (rerun) {
			excelUtil.setCellData(pageName, 0, excelUtil.getSheet().getPhysicalNumberOfRows() + 1, start, pageLoad + "",
					"FAIL");
			DBUtil.dbInsertPerformance(startsql, platform, pageName, domContentLoad, pageLoad, "FAIL",
					assertionMessage);
		} else {
			excelUtil.setCellData(pageName, 0, excelUtil.getSheet().getPhysicalNumberOfRows() + 1, start, pageLoad + "",
					"PASS");
			DBUtil.dbInsertPerformance(startsql, platform, pageName, domContentLoad, pageLoad, "PASS", "");
		}
		System.out.println(assertionMessage);
		softAssert.assertFalse(rerun, "On <a href=" + url + ">" + url + "</a> " + assertionMessage);
		softAssert.assertAll();
		if (driver != null)
			driver.quit();
	}

	@DataProvider(name = "Page Data")
	public String[][] getExcelData() {
		File file = new File(dataSheet);
		ExcelUtil excelUtil = new ExcelUtil(file);
		String sheetName = "Sheet1";
		String[][] dataArray = new String[excelUtil.getRowCount(sheetName) - 1][2];
		for (int i = 2; i <= excelUtil.getRowCount(sheetName); i++) {
			for (int j = 0; j < 2; j++)
				dataArray[i - 2][j] = excelUtil.getCellData(sheetName, j, i).trim();
		}
		return dataArray;
	}

	// @AfterClass
	public void sendMail() {
		boolean flag = message.contains("Exception") || message.contains("load") ? true : false;
		if (flag)
			SendEmail.sendCustomEmail(new String[] { "navdeep.gill@timesinternet.in" }, "Performance Automation Report",
					message + "</tbody></table>");
	}
}
