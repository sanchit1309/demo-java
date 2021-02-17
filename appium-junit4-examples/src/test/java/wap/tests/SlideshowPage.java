package wap.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import web.pagemethods.WebBaseMethods;

public class SlideshowPage extends BaseTest {
	SoftAssert softAssert;

	@BeforeClass
	public void doSetup() throws IOException {
		launchBrowser();
		String slideshowPageUrl = BaseTest.baseUrl + Config.fetchConfigProperty("SlideshowUrl");
		WebBaseMethods.navigateTimeOutHandle(slideshowPageUrl);
	}

	@Test(description = "This test verifies the link under section heading")
	public void checkSectionHeadings() {
		softAssert = new SoftAssert();
		List<WebElement> sectionLinks = driver.findElements(By.xpath("//h2/a"));
		List<String> hrefLinks = VerificationUtil.getLinkHrefList(sectionLinks);
		List<String> textLinks = VerificationUtil.getLinkTextList(driver.findElements(By.xpath("//h2")));
		for (int i = 0; i < hrefLinks.size(); i++) {
			String href = hrefLinks.get(i);
			String linkText = textLinks.get(i);
			int tempCode = HTTPResponse.checkResponseCode(href);
			softAssert.assertTrue(tempCode == 200,
					"<a href =" + linkText + ">" + href + "</a> is throwing " + tempCode);
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies the links on slideshow page")
	public void checkSlideShows() {
		softAssert = new SoftAssert();
		List<WebElement> slideshowLinks = driver.findElements(By.xpath("//a[contains(@href,'slideshow')]"));
		List<String> hrefLinks = VerificationUtil.getLinkHrefList(slideshowLinks);
		System.out.println(hrefLinks);
		hrefLinks.forEach(href -> {
			if (href.contains("m.economictimes")) {
				int tempCode = HTTPResponse.checkResponseCode(href);
				softAssert.assertTrue(tempCode == 200, "<a href =" + href + ">" + href.substring(0, href.length() / 2)
						+ "...</a> is throwing " + tempCode);
			}
		});
		softAssert.assertAll();
	}
}
