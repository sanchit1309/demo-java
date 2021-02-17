package wap.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.HTTPResponse;
import wap.pagemethods.AmpMethods;
import web.pagemethods.WebBaseMethods;

public class AMP extends BaseTest {
	static AmpMethods ampMethods;
	SoftAssert softAssert;
	String[] arr;

	@BeforeClass
	public void doSetup() throws IOException {
		launchBrowser();
		ampMethods = PageFactory.initElements(driver, AmpMethods.class);
		driver.get(baseUrl);
		arr = ampMethods.getArticleRefs().stream().toArray(String[]::new);
	}

	@DataProvider(name = "dataSource")
	public String[] getData() {
		return arr;
	}

	@Test(description = "This test verifies landing page of download ET App", dataProvider = "dataSource")
	public void checkAMPHealth(String url) {
		softAssert = new SoftAssert();
		WebBaseMethods.navigateTimeOutHandle(url);
		softAssert.assertTrue(ampMethods.checkDownloadAppLink(), "Download App CTA is not landing on to expected page");

		List<String> hrefs = ampMethods.getEmbeddedLinks();
		hrefs.forEach(href -> {
			int responseCode = HTTPResponse.checkResponseCode(href);
			softAssert.assertTrue(responseCode == 200, "Embedded link <a href=\"" + href + "\">"
					+ href.substring(0, href.length() / 2) + "</a> is throwing " + responseCode);
		});

		List<String> getRelatedArticle = ampMethods.getRelatedArticles();
		getRelatedArticle.forEach(article -> {
			int responseCode = HTTPResponse.checkResponseCode(article);
			softAssert.assertTrue(responseCode == 200, "Embedded link <a href=\"" + article + "\">"
					+ article.substring(0, article.length() / 2) + "</a> is throwing " + responseCode);
		});

		List<String> getPrimeWidgetLinks = ampMethods.getETPrimeWidgetLinks();
		getPrimeWidgetLinks.forEach(href -> {
			int responseCode = HTTPResponse.checkResponseCode(href);
			softAssert.assertTrue(responseCode == 200, "Embedded link <a href=\"" + href + "\">"
					+ href.substring(0, href.length() / 2) + "</a> is throwing " + responseCode);
		});
		softAssert.assertAll();
	}

}
