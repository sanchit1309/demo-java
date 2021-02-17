package web.tests;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.WaitUtil;
import web.pagemethods.HomePageMethods;

public class GetAllErrorLinksParallelRun extends BaseTest {
	String urlStringWeb;
	String urlStringWap;
	RemoteWebDriver driverWeb = null;
	RemoteWebDriver driverWap = null;
	static URL urlWeb = null;
	static URL urlWAP = null;
	

	@Test(description = "This test checks for all the broken links on the page Web", dataProvider = "sheetName")
	public void getAllErrorLinksOnThePage(String sheetName) throws IOException {
		HomePageMethods homePageMethods;
		SoftAssert softAssert;

		if (driverWeb == null || driverWeb.getSessionId() == null) {
			driverWeb = launchWebBrowser();
			driverWeb.manage().window().maximize();
		}

		homePageMethods = new HomePageMethods(driverWeb);
		String baseUrl = "https://economictimes.indiatimes.com";
		softAssert = new SoftAssert();
		System.out.println(sheetName);
		List<String> urlList = getTheListOfAllUrls(sheetName);
		softAssert.assertTrue(urlList.size()>0, "The sheet "+sheetName+" is not having links");
		System.out.println(urlList);

		urlList.forEach(url -> {
			try {
				urlStringWeb = baseUrl + url;
				driverWeb.get(urlStringWeb);
				WaitUtil.sleep(2000);
				scrollToBottom(driverWeb);
				scrollToTop(driverWeb);
				WaitUtil.sleep(3000);

				List<String> allUrlList = new LinkedList<>();

				allUrlList = homePageMethods.getAllShowHref();

				Set<String> allHrefSet = new HashSet<String>(allUrlList);
				System.out.println(allHrefSet.size());
				List<String> failedUrls = new LinkedList<>();

				allHrefSet.forEach(href -> {
					try {

						if (!href.contains("javascript") && href.contains("economictimes")) {
							int response = checkResponseCodeWeb(href);
						//	System.out.println("the href is " + href + "-----" + response);
							if (response != 200) {
								failedUrls.add("<br>- <a href=" + href + "> " + href + " </a>" + " is having response= "
										+ response);
							}

						}
					} catch (Exception ee) {
						ee.printStackTrace();
						failedUrls.add("<br>- <a href=" + href + "> " + href + " </a>");

					}
				});
				softAssert.assertTrue(failedUrls.size() == 0, "Few links on page " + urlStringWeb
						+ " are not having the response code 200, list of such links:" + failedUrls);
			} catch (Exception ee) {
				ee.printStackTrace();
				softAssert.assertTrue(false, "exception occured while checking for url: " + urlStringWeb);

			}
		});

		softAssert.assertAll();
	}

	@Test(description = "This test checks for all the broken links on the page", dataProvider = "sheetName")
	public void getAllErrorLinksOnThePageWAP(String sheetName) throws IOException {
		HomePageMethods homePageMethods;
		SoftAssert softAssert;

		if (driverWap == null || driverWap.getSessionId() == null) {
			driverWap = launchWapBrowser();
		}

		homePageMethods = new HomePageMethods(driverWap);
		String baseUrl = "https://m.economictimes.com";
		softAssert = new SoftAssert();
		System.out.println(sheetName);
		List<String> urlList = getTheListOfAllUrls(sheetName);
		softAssert.assertTrue(urlList.size()>0, "The sheet "+sheetName+" is not having links");
		System.out.println(urlList);

		urlList.forEach(url -> {
			try {
				urlStringWap = baseUrl + url;
				driverWap.get(urlStringWap);
				WaitUtil.sleep(2000);
				scrollToBottom(driverWap);
				scrollToTop(driverWap);
				WaitUtil.sleep(3000);

				List<String> allUrlList = new LinkedList<>();

				allUrlList = homePageMethods.getAllShowHref();

				Set<String> allHrefSet = new HashSet<String>(allUrlList);
				System.out.println(allHrefSet.size());
				List<String> failedUrls = new LinkedList<>();

				allHrefSet.forEach(href -> {
					try {

						if (!href.contains("javascript") && href.contains("economictimes")) {
							int response = checkResponseCodeWAP(href);
							//System.out.println(href + "-----" + response);
							if (response != 200) {
								failedUrls.add("<br>- <a href=" + href + "> " + href + " </a>" + " is having response= "
										+ response);
							}

						}
					} catch (Exception ee) {
						ee.printStackTrace();
						failedUrls.add("<br>- <a href=" + href + "> " + href + " </a>");

					}
				});
				softAssert.assertTrue(failedUrls.size() == 0, "Few links on page " + urlStringWap
						+ " are not having the response code 200, list of such links:" + failedUrls);
			} catch (Exception ee) {
				ee.printStackTrace();
				softAssert.assertTrue(false, "exception occured while checking for url: " + urlStringWap);

			}
		});

		softAssert.assertAll();
	}

	@AfterSuite(alwaysRun = true)
	public void tearDown() {
		driverWeb.quit();
		driverWap.quit();
	}

	@DataProvider(name = "sheetName")
	public Object[] sheetName() {
		String[] sectionName = { "HomePage", "Prime", "ETL1Pages", "ETPrimeL1Pages" };
		return sectionName;
	}

	public List<String> getTheListOfAllUrls(String sheetName) {
		List<String> urlList = new LinkedList<>();
		try {
			String sectionsName = "./src/main/resources/testdata/web/UrlSectionWise.xlsx";
			File file = new File(sectionsName);
			ExcelUtil excelUtil = new ExcelUtil(file);
			int index = excelUtil.getWorkbook().getSheetIndex(sheetName);
			excelUtil.setSheet(excelUtil.getWorkbook().getSheetAt(index));

			excelUtil.extractAsList(0).forEach(li -> {
				String url = li.get(0).toString();
				urlList.add(url);
			});
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return urlList;
	}

	public RemoteWebDriver launchWebBrowser() {
		RemoteWebDriver driverWeb;
		final String pathAppender = "/src/main/resources/drivers/";

		String browser_driver_path = System.getProperty("user.dir") + pathAppender;
		ChromeOptions options = new ChromeOptions();
		options.setPageLoadStrategy(PageLoadStrategy.NONE);
		options.addArguments("--test-type");
		options.addArguments("--disable-popup-blocking");
		options.addArguments("disable-infobars");
		options.addArguments("--disable-gpu");
		options.addArguments("--disable-features=VizDisplayCompositor");
		options.addArguments("--dns-prefetch-disable");

		String browserDriver = !(Platform.getCurrent().toString().equalsIgnoreCase("MAC")) ? "chromedriver.exe"
				: "chromedriver_mac";
		System.setProperty("webdriver.chrome.driver", browser_driver_path + browserDriver);
		driverWeb = new ChromeDriver(options);
		driverWeb.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		return driverWeb;

	}

	public RemoteWebDriver launchWapBrowser() {
		RemoteWebDriver driverWap;
		final String pathAppender = "/src/main/resources/drivers/";
		String browser_driver_path = System.getProperty("user.dir") + pathAppender;

		String browserDriver = !(Platform.getCurrent().toString().equalsIgnoreCase("MAC")) ? "chromedriver.exe"
				: "chromedriver_mac";
		System.setProperty("webdriver.chrome.driver", browser_driver_path + browserDriver);
		ChromeOptions mobileOptions = new ChromeOptions();
		Map<String, String> mobileEmulation = new HashMap<String, String>();
		mobileEmulation.put("deviceName", "iPhone 6");
		Map<String, Object> chromeOptions = new HashMap<String, Object>();
		chromeOptions.put("mobileEmulation", mobileEmulation);
		mobileOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
		driverWap = new ChromeDriver(mobileOptions);
		driverWap.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		return driverWap;
	}

	public static void scrollToBottom(RemoteWebDriver driver) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
			WaitUtil.sleep(4000);
		} catch (Exception e) {
			// do nothing
		}
	}

	public static void scrollToTop(RemoteWebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0);");
	}
	
	public static int checkResponseCodeWeb(String href) {
		
		if (!href.startsWith("http")) {
			href = "https:" + href;
		}
		System.setProperty("jsse.enableSNIExtension", "false");
		String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36";

		try {
			urlWeb = new URL(href);
		} catch (MalformedURLException | IllegalArgumentException e) {
			System.out.println("Failed while checking for ==> " + href);
			e.printStackTrace();
		}
		HttpURLConnection http = null;
		try {
			http = (HttpURLConnection) urlWeb.openConnection();
			http.setConnectTimeout(15000);
			http.setInstanceFollowRedirects(false);
		} catch (IOException | IllegalArgumentException e1) {
			System.out.println("Failed while checking for ==> " + href);
			e1.printStackTrace();
		}
		try {
			http.setRequestMethod("GET");
		} catch (ProtocolException | IllegalArgumentException e) {
			System.out.println("Failed while checking for ==> " + href);
			e.printStackTrace();
		}
		http.addRequestProperty("User-Agent", userAgent);

		try {
			http.connect();
		} catch (IOException | IllegalArgumentException e) {
			System.out.println("Failed while checking for ==> " + href);
			e.printStackTrace();
			return 0;
		}
		int responseCode = 0;
		try {
			responseCode = http.getResponseCode();
		} catch (IOException | IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			int counter = 0;
			while (responseCode != 200 && counter < 5) {

				if (responseCode == HttpURLConnection.HTTP_MOVED_PERM
						|| responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
					URL base = new URL(href);
					String location = http.getHeaderField("Location");
					URL next = new URL(base, location);
					href = next.toExternalForm();
					urlWeb = new URL(href);
					http = (HttpURLConnection) urlWeb.openConnection();
					http.connect();
				}
				try {
					responseCode = http.getResponseCode();
				} catch (Exception e) {

				}
				counter++;
			}
		} catch (Exception e) {
			System.out.println("Failed while checking for ==> " + href);
			e.printStackTrace();
		}
		System.out.println("Web url: "+href+"----"+responseCode);
		return responseCode;
	}
	
	public static int checkResponseCodeWAP(String href) {
		if (!href.startsWith("http")) {
			href = "https:" + href;
		}
		System.setProperty("jsse.enableSNIExtension", "false");
		String userAgent = "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Mobile Safari/537.36";

		try {
			urlWAP = new URL(href);
		} catch (MalformedURLException | IllegalArgumentException e) {
			System.out.println("Failed while checking for ==> " + href);
			e.printStackTrace();
		}
		HttpURLConnection http = null;
		try {
			http = (HttpURLConnection) urlWAP.openConnection();
			http.setConnectTimeout(15000);
			http.setInstanceFollowRedirects(false);
		} catch (IOException | IllegalArgumentException e1) {
			System.out.println("Failed while checking for ==> " + href);
			e1.printStackTrace();
		}
		try {
			http.setRequestMethod("GET");
		} catch (ProtocolException | IllegalArgumentException e) {
			System.out.println("Failed while checking for ==> " + href);
			e.printStackTrace();
		}
		http.addRequestProperty("User-Agent", userAgent);

		try {
			http.connect();
		} catch (IOException | IllegalArgumentException e) {
			System.out.println("Failed while checking for ==> " + href);
			e.printStackTrace();
			return 0;
		}
		int responseCode = 0;
		try {
			responseCode = http.getResponseCode();
		} catch (IOException | IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			int counter = 0;
			while (responseCode != 200 && counter < 5) {

				if (responseCode == HttpURLConnection.HTTP_MOVED_PERM
						|| responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
					URL base = new URL(href);
					String location = http.getHeaderField("Location");
					URL next = new URL(base, location);
					href = next.toExternalForm();
					urlWAP = new URL(href);
					http = (HttpURLConnection) urlWAP.openConnection();
					http.connect();
				}
				try {
					responseCode = http.getResponseCode();
				} catch (Exception e) {

				}
				counter++;
			}
		} catch (Exception e) {
			System.out.println("Failed while checking for ==> " + href);
			e.printStackTrace();
		}
		System.out.println("WAP url: "+href+"----"+responseCode);
		return responseCode;
	}

}
