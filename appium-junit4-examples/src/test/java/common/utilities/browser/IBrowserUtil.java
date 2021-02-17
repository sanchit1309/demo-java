package common.utilities.browser;

import java.io.IOException;
import java.net.MalformedURLException;

import org.openqa.selenium.remote.RemoteWebDriver;

public interface IBrowserUtil {

	public RemoteWebDriver launchBrowser()  throws MalformedURLException, IOException;

	public void killServices();
}
