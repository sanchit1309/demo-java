package common.utilities.browser.localbrowser;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.ExecuteException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import app.common.AndroidCommonMethods;
import app.common.IOSCommonMethods;
import common.launchsetup.AppiumService;
import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.ADBUtil;
import common.utilities.browser.IBrowserUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class LocalBrowserUtilApp implements IBrowserUtil {

	private RemoteWebDriver driver;

	private DesiredCapabilities desiredCapabilities = null;
	private AppiumService appiumService;
	private static String resetValue = null;
	ADBUtil adbUtil;

	/*
	 * Launches the browser, browser name need to provided via argument
	 */
	@Override
	public RemoteWebDriver launchBrowser() throws IOException {
		desiredCapabilities = new DesiredCapabilities();
		appiumService = new AppiumService();
		appiumService.startAppiumService();
		if (BaseTest.platform.equals("androidApp")) {
			adbUtil = new ADBUtil();
			setDesiredCapibilitiesAndroid();
			driver = new AndroidDriver<AndroidElement>(new URL(Config.fetchConfigProperty("baseURL")),
					desiredCapabilities);
			BaseTest.iAppCommonMethods = new AndroidCommonMethods((AndroidDriver<?>) driver);
		} else if (BaseTest.platform.equals("iosApp")) {
			setDesiredCapibilitiesIOS();
			driver = new IOSDriver<IOSElement>(new URL(Config.fetchConfigProperty("baseURL")), desiredCapabilities);
			BaseTest.iAppCommonMethods = new IOSCommonMethods((IOSDriver<?>) driver);

		}
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		BaseTest.iAppCommonMethods.handleWelcomeScreenAlerts();
		return driver;

	}

	private void setDesiredCapibilitiesAndroid() throws IOException {
		resetValue = resetValue == null ? Config.fetchConfigProperty("ResetMode") : resetValue;

		Map<String, String> keyValue = Config.fetchMatchingProperty("cap");
		Iterator<Entry<String, String>> it = keyValue.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> pair = (Entry<String, String>) it.next();
			desiredCapabilities.setCapability((String) pair.getKey(), pair.getValue());
		}
		List<String> liDeviceId = adbUtil.getConnectedDevices().get("deviceId");
		List<String> liDeviceName = adbUtil.getConnectedDevices().get("deviceName");

		if (liDeviceId.size() != 0 && liDeviceName.size() != 0) {
			System.out.println("Found device by adb");
			String deviceName = liDeviceName.get(0);
			String deviceID = liDeviceId.get(0);
			desiredCapabilities.setCapability("deviceName", deviceName);
			desiredCapabilities.setCapability("udid", deviceID);
		} else {
			System.out.println("Setting device as " + desiredCapabilities.getCapability("udid"));
		}
		String platformName = adbUtil.getDevicePlatform();
		if (platformName != null)
			desiredCapabilities.setCapability("platformVersion", platformName);
		if (resetValue.equals("fullReset")) {
			System.out.println("Driver DO FULL-RESET");
			desiredCapabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
			desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, false);
			resetValue = "normalReset";
		} else if (resetValue.equals("fastReset")) {
			// clears cache and settings without reinstall
			System.out.println("Driver DO FAST-RESET");
			desiredCapabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
			desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, false);
		} else {
			// just start client
			System.out.println("Driver DO NORMAL start");
			desiredCapabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
			desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, true);
		}

		desiredCapabilities.setCapability("newCommandTimeout", 60000);
		desiredCapabilities.setCapability("showChromedriverLog", true);
		desiredCapabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
	}

	private void setDesiredCapibilitiesIOS() throws IOException {
		Map<String, String> keyValue = Config.fetchMatchingProperty("cap");
		Iterator<Entry<String, String>> it = keyValue.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> pair = (Entry<String, String>) it.next();
			desiredCapabilities.setCapability((String) pair.getKey(), pair.getValue());
		}
		desiredCapabilities.setCapability("newCommandTimeout", 60000);
		desiredCapabilities.setCapability("nativeInstrumentsLib", true);
	}

	public void killAppium() throws ExecuteException, IOException {
		appiumService.stopAppiumService();
	}

	@Override
	public void killServices() {
		if (driver != null)
			driver.quit();
		appiumService.stopAppiumService();

	}

}