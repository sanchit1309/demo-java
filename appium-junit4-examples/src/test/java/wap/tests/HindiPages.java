package wap.tests;

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
import web.pagemethods.WebBaseMethods;

public class HindiPages extends BaseTest {
	SoftAssert softAssert;

	@BeforeClass
	public void doSetup() throws IOException {
		launchBrowser();
	}

	@Test(dataProvider = "setOfUrls")
	public void checkBrokenLinks(String url) {
		WebBaseMethods.navigateTimeOutHandle(baseUrl+url);
		softAssert = new SoftAssert();
		List<WebElement> allLinks = driver.findElements(By.xpath("//a[contains(@href,'.cms')]"));
		allLinks.addAll(driver.findElements(By.xpath("//a[contains(@href,'.cms')]")));
		List<String> getHrefs=VerificationUtil.getLinkHrefList(allLinks);
		getHrefs.forEach(href -> {
//			String href = el.getAttribute("href");
			if(!href.contains("etapp")){
			int responseCode = HTTPResponse.checkResponseCode(href);
			softAssert.assertTrue(responseCode == 200, "Url " + href + " is throwing " + responseCode+"<br>");
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
