package common.utilities.browser.localbrowser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.ExecuteException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import common.launchsetup.AppiumService;
import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.browser.IBrowserUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class LocalBrowserUtilWap implements IBrowserUtil {

	private final String pathAppender = "/src/main/resources/drivers/";
	private RemoteWebDriver driver;

	private String browser;
	private DesiredCapabilities desiredCapabilities = null;
	private AppiumService appiumService;

	/*
	 * Launches the browser, browser name need to provided via argument
	 */
	@Override
	public RemoteWebDriver launchBrowser() throws IOException {
		String browser_driver_path = System.getProperty("user.dir") + pathAppender;
		browser = BaseTest.browserName == null
				? new Config().fetchConfig(new File("./suiterun.properties"), "browserName") : BaseTest.browserName;
		switch (browser) {

		case ("androidChrome"):
			new AppiumService().startAppiumService();
			setDesiredCapabilitiesWAP("androidChrome");
			System.setProperty("webdriver.chrome.driver", browser_driver_path + "chromedriver.exe");
			driver = new AndroidDriver<>(new URL(Config.fetchConfigProperty("baseUrl")), desiredCapabilities);
			break;

		case ("macSafari"):
			new AppiumService().startAppiumService();
			setDesiredCapabilitiesWAP("macSafari");
			driver = new IOSDriver<>(new URL(Config.fetchConfigProperty("baseUrl")), desiredCapabilities);
			break;

		case ("pwa"):
			String pwaBrowserDriver = !(Platform.getCurrent().toString().equalsIgnoreCase("MAC")) ? "chromedriver.exe"
					: "chromedriver_mac";
			System.setProperty("webdriver.chrome.driver", browser_driver_path + pwaBrowserDriver);
			ChromeOptions pwaMobileOptions = new ChromeOptions();
			Map<String, String> pwaMobileEmulation = new HashMap<String, String>();
			pwaMobileEmulation.put("deviceName", "Nexus 5");
			Map<String, Object> pwaChromeOptions = new HashMap<String, Object>();
			pwaChromeOptions.put("mobileEmulation", pwaMobileEmulation);
			pwaMobileOptions.setExperimentalOption("mobileEmulation", pwaMobileEmulation);
			driver = new ChromeDriver(pwaMobileOptions);
			break;
		case ("specificDevice"):
			String deviceBrowserDriver = !(Platform.getCurrent().toString().equalsIgnoreCase("MAC"))
					? "chromedriver.exe" : "chromedriver_mac";
			System.setProperty("webdriver.chrome.driver", browser_driver_path + deviceBrowserDriver);
			ChromeOptions deviceMobileOptions = new ChromeOptions();
			Map<String, String> deviceMobileEmulation = new HashMap<String, String>();
			deviceMobileEmulation.put("deviceName", BaseTest.deviceName);
			Map<String, Object> deviceChromeOptions = new HashMap<String, Object>();
			deviceChromeOptions.put("mobileEmulation", deviceMobileEmulation);
			deviceMobileOptions.setExperimentalOption("mobileEmulation", deviceMobileEmulation);
			driver = new ChromeDriver(deviceMobileOptions);
			break;

		case ("specificUserAgent"):
			String userAgentBrowserDriver = !(Platform.getCurrent().toString().equalsIgnoreCase("MAC"))
					? "chromedriver.exe" : "chromedriver_mac";
			System.setProperty("webdriver.chrome.driver", browser_driver_path + userAgentBrowserDriver);
			ChromeOptions userAgentMobileOptions = new ChromeOptions();
			Map<String, Object> userAgentMobileEmulation = new HashMap<String, Object>();
			Map<String, Object> deviceMetrics = new HashMap<>();
			deviceMetrics.put("width", 375);
			deviceMetrics.put("height", 700);
			deviceMetrics.put("pixelRatio", 3.0);
			userAgentMobileEmulation.put("deviceMetrics", deviceMetrics);
			userAgentMobileEmulation.put("userAgent", BaseTest.userAgent);
			Map<String, Object> userAgentChromeOptions = new HashMap<String, Object>();
			userAgentChromeOptions.put("mobileEmulation", userAgentMobileEmulation);
			userAgentMobileOptions.setExperimentalOption("mobileEmulation", userAgentMobileEmulation);
			driver = new ChromeDriver(userAgentMobileOptions);
			break;

		default:

			String browserDriver = !(Platform.getCurrent().toString().equalsIgnoreCase("MAC")) ? "chromedriver.exe"
					: "chromedriver_mac";
			System.setProperty("webdriver.chrome.driver", browser_driver_path + browserDriver);
			ChromeOptions mobileOptions = new ChromeOptions();
			Map<String, String> mobileEmulation = new HashMap<String, String>();
			mobileEmulation.put("deviceName", "iPhone 6");
			Map<String, Object> chromeOptions = new HashMap<String, Object>();
			chromeOptions.put("mobileEmulation", mobileEmulation);
			mobileOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
			driver = new ChromeDriver(mobileOptions);
		}
		if (Config.fetchConfigProperty("ElementWaitTime") != null)
			driver.manage().timeouts().implicitlyWait(Long.parseLong(Config.fetchConfigProperty("ElementWaitTime")),
					TimeUnit.SECONDS);

		return driver;

	}

	private void setDesiredCapabilitiesWAP(String platformBrowser) {
		desiredCapabilities = new DesiredCapabilities();
		Map<String, String> keyValue = new LinkedHashMap<>();
		if (platformBrowser.equalsIgnoreCase("androidChrome")) {
			keyValue = Config.fetchMatchingProperty("aChrome");
		} else if (platformBrowser.equalsIgnoreCase("macSafari")) {
			desiredCapabilities.setCapability("nativeInstrumentsLib", true);
			desiredCapabilities.setCapability("nativeWebTap", true);
			keyValue = Config.fetchMatchingProperty("mSafari");
		}
		Iterator<Entry<String, String>> it = keyValue.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> pair = (Entry<String, String>) it.next();
			desiredCapabilities.setCapability((String) pair.getKey(), pair.getValue());
		}
	}

	public void killAppium() throws ExecuteException, IOException {
		appiumService.stopAppiumService();
	}

	@Override
	public void killServices() {
		if (driver != null)
			driver.quit();
		if (BaseTest.platform.contains("App"))
			appiumService.stopAppiumService();

	}
}