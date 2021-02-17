package web.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import web.pagemethods.WebBaseMethods;

public class HindiMarkets extends BaseTest {
	SoftAssert softAssert;

	@BeforeClass
	public void doSetup() throws IOException {
		launchBrowser();
	}

	@Test(dataProvider = "setOfUrls", description = "This test checks broken link on hindi pages")
	public void checkBrokenLinks(String url) {
		WebBaseMethods.navigateTimeOutHandle(baseUrl + url);
		softAssert = new SoftAssert();
		WaitUtil.sleep(2000);
		List<WebElement> allLinks = driver.findElements(By.xpath("//a[contains(@href,'.cms')]"));
		WaitUtil.sleep(2000);
		List<String> getHrefs = VerificationUtil.getLinkHrefList(allLinks);
		System.out.println(getHrefs.size());

		getHrefs.forEach(href -> {
			if (!href.contains("etapp")) {
				int responseCode = HTTPResponse.checkResponseCode(href);
				softAssert.assertTrue(responseCode == 200, "Url <a href=\"" + href + "\">"
						+ href.substring(0, href.length() / 2) + "</a> is throwing " + responseCode + "<br>");
			}
		});
		softAssert.assertAll();
	}

	@DataProvider(name = "setOfUrls")
	public String[] getAllUrls() {
		String urls = Config.fetchConfigProperty("HindiUrls");
		return urls.split(",");
	}
}
