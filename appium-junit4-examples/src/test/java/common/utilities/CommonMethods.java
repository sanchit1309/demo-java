package common.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CommonMethods {
	private WebDriver driver;

	public CommonMethods(WebDriver driver) {
		this.driver = driver;

	}

	public boolean checkIfElementIsVisible(WebElement we) {
		boolean flag = false;
		try {
			WaitUtil.explicitWaitByVisibilityOfElement(driver, 5, we);

			flag = we.isDisplayed();
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return flag;
	}

	public List<String> getLinksRedirectingToWeb(List<String> urlList) {
		List<String> failedList = new LinkedList<>();
		urlList.forEach(url -> {
			if (url.contains("economictimes.indiatimes.com")) {
				failedList.add(url);
			}

		});
		return failedList;

	}

	public boolean jqueryInjectionOnPage() {
		boolean flag = false;
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String jqScript = "return (typeof jQuery)";
			String type = js.executeScript(jqScript, "").toString();

			if (type.equalsIgnoreCase("undefined")) {
				String script = "var headID = document.getElementsByTagName('head')[0];"
						+ "var newScript = document.createElement(\"script\");"
						+ "newScript.type = \"text/javascript\";"
						+ "newScript.src = \"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\";"
						+ "headID.appendChild(newScript);";
				js.executeScript(script, "");
				System.out.println(" jquery loaded successfully");
				flag = true;
			}
		} catch (Exception ee) {
			ee.printStackTrace();

		}

		return flag;
	}

	public static long getCurrentEpocTime() {
		long epochTime = 0;
		Date today = Calendar.getInstance().getTime();
		SimpleDateFormat crunchifyFormat = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz");
		String currentTime = crunchifyFormat.format(today);
		try {

			// parse() parses text from the beginning of the given string to
			// produce a date.
			Date date = crunchifyFormat.parse(currentTime);

			// getTime() returns the number of milliseconds since January 1,
			// 1970, 00:00:00 GMT represented by this Date object.
			epochTime = date.getTime();

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return epochTime / 1000;
	}

	public static List<String> getLinksRedirectingToWebByHttpResponse(List<String> urlList) {
		List<String> failedList = new LinkedList<>();

		urlList.forEach(url -> {
			try {
				String redirectedUrl = HTTPResponse.getLocationFor301Or302StatusCode(url);
				if (redirectedUrl.contains("economictimes.indiatimes.com")) {
					failedList.add(url);
				}
			} catch (Exception ee) {
				ee.printStackTrace();
				failedList.add(url);
			}
		});

		return failedList;

	}

}
