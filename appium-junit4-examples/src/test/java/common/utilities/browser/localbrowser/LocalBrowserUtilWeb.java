package common.utilities.browser.localbrowser;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.browser.IBrowserUtil;

public class LocalBrowserUtilWeb implements IBrowserUtil {

	private final String pathAppender = "/src/main/resources/drivers/";
	private RemoteWebDriver driver;

	private String browser;

	/*
	 * Launches the browser, browser name need to provided via argument
	 */
	@Override
	public RemoteWebDriver launchBrowser() throws IOException {
		String browser_driver_path = System.getProperty("user.dir") + pathAppender;
		browser = BaseTest.browserName == null
				? new Config().fetchConfig(new File("./suiterun.properties"), "browserName") : BaseTest.browserName;
		if (browser != null && browser.equalsIgnoreCase("chrome")) {
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
			driver = new ChromeDriver(options);
		} else if (browser != null && browser.equalsIgnoreCase("ie")) {
			System.setProperty("cap_webdriver.ie.driver", browser_driver_path + "IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		} else {
			String browserDriver = !(Platform.getCurrent().toString().equalsIgnoreCase("MAC")) ? "geckodriver.exe"
					: "geckodriver_mac";
			FirefoxOptions firefoxoptions = new FirefoxOptions();
			System.setProperty("webdriver.gecko.driver", browser_driver_path + browserDriver);
			firefoxoptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			firefoxoptions.setCapability("webdriver.log.driver", "OFF");
			driver = new FirefoxDriver(firefoxoptions);
		}
		driver.manage().window().maximize();
		if (Config.fetchConfigProperty("PageLoadTime") != null)
			driver.manage().timeouts().pageLoadTimeout(Long.parseLong(Config.fetchConfigProperty("PageLoadTime")),
					TimeUnit.SECONDS);
		if (Config.fetchConfigProperty("ElementWaitTime") != null)
			driver.manage().timeouts().implicitlyWait(Long.parseLong(Config.fetchConfigProperty("ElementWaitTime")),
					TimeUnit.SECONDS);
		return driver;

	}

	@Override
	public void killServices() {
		if (driver != null)
			driver.quit();

	}

}