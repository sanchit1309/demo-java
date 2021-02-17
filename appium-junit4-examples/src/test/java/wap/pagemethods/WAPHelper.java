package wap.pagemethods;

import java.util.Set;

import io.appium.java_client.AppiumDriver;

public class WAPHelper {
	private static int currentWebViewCount;

	public static int getWebViewCount(AppiumDriver<?> appDriver) {
		currentWebViewCount = appDriver.getContextHandles().size();
		return currentWebViewCount;
	}

	public static void genericSwitchToWebView(AppiumDriver<?> appDriver) {
		Set<String> availableContext = appDriver.getContextHandles();
		for (String context : availableContext) {
			if (context.contains("WEBVIEW")) {
				appDriver.context(context);
			}
		}
	}

	public static int waitForOpenWebContextChange(int maxWaitSec,AppiumDriver<?>appDriver) {
		int count = maxWaitSec * 10;
		int currentCount = currentWebViewCount;
		while (count > 0) {
			try {
				genericSwitchToWebView(appDriver);
				currentCount = appDriver.getContextHandles().size();
			} catch (Exception e) {

			}

			if (!(currentCount == currentWebViewCount)) {
				break;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count--;
		}
		try {
			currentWebViewCount = appDriver.getContextHandles().size();
		} catch (Exception e) {

		}
		return currentWebViewCount;
	}

	/**
	 * Method to switch to Native APP
	 * 
	 * @param appDriver
	 */
	public static void switchToNativeApp(AppiumDriver<?> appDriver) {
		Set<String> availableContext = appDriver.getContextHandles();
		for (String context : availableContext) {
			if (context.contains("NATIVE")) {
				appDriver.context(context);
				break;
			}
		}
	}
}
