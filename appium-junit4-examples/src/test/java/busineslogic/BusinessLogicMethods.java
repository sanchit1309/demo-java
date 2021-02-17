package busineslogic;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import common.launchsetup.BaseTest;
import common.urlRepo.FeedRepo;
import common.utilities.ApiHelper;
import common.utilities.WaitUtil;
import web.pagemethods.WebBaseMethods;

public class BusinessLogicMethods {

	private static WebDriver driver = BaseTest.driver;
	private final static String pathAppender = "/src/main/resources/drivers/";
	private static String browser_driver_path = System.getProperty("user.dir") + pathAppender;
	static String browserDriver;
	static {
		Platform platform = Platform.getCurrent();
		browserDriver = !(platform.toString().equalsIgnoreCase("MAC")) ? "geckodriver.exe" : "geckodriver_mac";
	}

	public static String getSensexCurrentPrice() {
		boolean flag = false;
		int counter = 0;
		String sensexPrice = "Error";
		By sensexValue = By.id("idcrval");
		try {
			System.setProperty("webdriver.gecko.driver", browser_driver_path + browserDriver);
			driver = new FirefoxDriver();
			try {
				driver.navigate().to("https://www.bseindia.com/sensex/code/16/");
			} catch (WebDriverException e) {
				return sensexPrice;
			}
			// driver.navigate().refresh();
			WaitUtil.explicitWaitByPresenceOfElement(driver, 60, sensexValue);;
			do {
				try {
					sensexPrice = driver.findElement(sensexValue).getText().trim();
					System.out.println("sensexPrice =>" + sensexPrice);
					flag = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				counter++;
			} while (!flag && counter < 3);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			driver.quit();
		}
		return sensexPrice.replace(",", "");
	}

	public static String getNiftyCurrentPrice() {
		boolean flag = false;
		int counter = 0;
		String niftyPrice = "Error";
		// By niftyValue = By.xpath("//span[@id='lastPriceNIFTY 50']/span");
		By niftyValue = By.xpath("//p[@class='right']//nobr");

		try {
			//System.setProperty("webdriver.chrome.driver", browser_driver_path + browserDriver);
			
			System.setProperty("webdriver.gecko.driver", browser_driver_path + browserDriver);
			driver = new FirefoxDriver();
			try {
				driver.navigate().to("https://www1.nseindia.com/live_market/dynaContent/live_watch/equities_stock_watch.htm?cat=N");
				driver.navigate().refresh();
				WaitUtil.sleep(5000);
			} catch (WebDriverException e) {
				return niftyPrice;
			}
			do {
				WaitUtil.explicitWaitByVisibilityOfElement(driver, 60,
						(RemoteWebElement) driver.findElement(niftyValue));

				try {
					niftyPrice = driver.findElement(niftyValue).getText().trim();
					flag = true;
				} catch (Exception e) {
					// do nothing
				}
				counter++;
			} while (!flag && counter < 3);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			driver.quit();
		}
		return niftyPrice.replace(",", "");
	}

	public static String getGoldPrice() {
		String mcxSiteUrl = "https://www.mcxindia.com/en/market-data/get-quote/GOLD";
		By goldValue = By.id("litPrice");
		String goldPrice = "Error";
		try {
			System.setProperty("webdriver.chrome.driver", browser_driver_path + browserDriver);
			driver = new ChromeDriver();
			try {
				driver.get(mcxSiteUrl);
			} catch (WebDriverException e) {
				return goldPrice;
			}
			WaitUtil.sleep(5000);
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 60, (RemoteWebElement) driver.findElement(goldValue));
			goldPrice = driver.findElement(goldValue).getText().trim();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.quit();
		}
		return goldPrice.replace(",", "");
	}

	public static String getUSDINR() {
		String USDINRSite = "http://www.mecklai.com";
		By currenciesTab = By.className("cuuriences");
		By USDINR = By.xpath("//*[text()='USD/INR']/../../../td[2]");
		String exchangevalue = "Error";
		try {
			System.setProperty("webdriver.chrome.driver", browser_driver_path + browserDriver);
			driver = new ChromeDriver();
			try {
				driver.navigate().to(USDINRSite);
			} catch (WebDriverException e) {
				return exchangevalue;
			}
			WaitUtil.sleep(5000);
			WaitUtil.waitForLoad(driver);
			WebBaseMethods.scrollUpDownUsingJSE(0, 500, driver);
			WaitUtil.sleep(2000);
			WaitUtil.explicitWaitByElementToBeClickable(driver, 40,
					(RemoteWebElement) driver.findElement(currenciesTab));
			driver.findElement(currenciesTab).click();
			WaitUtil.sleep(2000);
			WaitUtil.explicitWaitByElementToBeClickable(driver, 20, (RemoteWebElement) driver.findElement(USDINR));
			exchangevalue = driver.findElement(USDINR).getText().trim();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.quit();
		}
		return exchangevalue;
	}

	public static int getBenchmarksAllowedValueDiff(String benchmarkName) {
		int allowedDiff = 0;
		benchmarkName = benchmarkName.toUpperCase();
		switch (benchmarkName) {
		case "SENSEX":
			allowedDiff = 40;
			break;
		case "NIFTY 50":
			allowedDiff = 20;
			break;
		case "GOLD":
			allowedDiff = 50;
			break;
		case "SILVER":
			allowedDiff = 50;
			break;
		case "CRUDE OIL":
			allowedDiff = 50;
			break;
		default:
			allowedDiff = 0;
			break;

		}
		return allowedDiff;
	}

	public static double getFeedSensexCurrentPrice() {
		String sensexPrice = "0";
		try {
			String response = ApiHelper.getAPIResponse(FeedRepo.MarketsDashboardData_BSE);
			String str = response.substring(0, response.length() - 1).replace("marketHomeData(", "");
			String bseFeedOutput=str.substring(0, str.length()-2);
			System.out.println("bse FeedOutput : " +bseFeedOutput);
			sensexPrice = ApiHelper.getListOfAllValues(bseFeedOutput, "CurrentIndexValue", "IndexName", "SENSEX",
					"STOCK_SENSX", "INDEX_DETAIL").get(0);
			System.out.println("sensex price :: "+sensexPrice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Double.parseDouble(sensexPrice);
	}
	
	

	public static double getFeedNiftyCurrentPrice() {
		String niftyPrice = "0";
		try {
			String response = ApiHelper.getAPIResponse(FeedRepo.MarketsDashboardData_NSE);
			String str = response.substring(0, response.length() - 1).replace("marketHomeData(", "");
			String bseFeedOutput=str.substring(0, str.length()-2);
			niftyPrice = ApiHelper.getListOfAllValues(bseFeedOutput, "CurrentIndexValue", "IndexName", "NIFTY 50",
					"STOCK_NIFTY", "INDEX_DETAIL").get(0);
			System.out.println("niftyprice : "+niftyPrice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Double.parseDouble(niftyPrice);
	}

	public static double getFeedUSDINR() {
		String forexRate = "0";
		try {
			String response = ApiHelper.getAPIResponse(FeedRepo.FOREX_CURRENCYPAIRRATE);
			// String bseFeedOutput = response.substring(0, response.length() -
			// 1).replace("marketHomeData(", "");
			forexRate = ApiHelper
					.getListOfAllValues(response, "spotRate", "currencyPairName", "USD/INR", "searchresult").get(0);
			System.out.println("forex rate " +forexRate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Double.parseDouble(forexRate);
	}

	public static double getFeedCommodityPrice(String symbol) {
		String lastTradedPrice = "0";
		try {
			String mcxFeedOutput = ApiHelper.getAPIResponse(FeedRepo.COMMODITYDATA + symbol);
			String validOutput = mcxFeedOutput.replace("var etmarketdata=[", "{\"etmarketdata\":[").concat("}");
			lastTradedPrice = ApiHelper.getListOfAllValues(validOutput, "LastTradedPrice", "Symbol", symbol,
					"etmarketdata", "NewDataSet", "Table").get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Double.parseDouble(lastTradedPrice);
	}

}
