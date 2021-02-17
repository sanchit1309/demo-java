package wap.tests;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.HTTPResponse;
import common.utilities.WaitUtil;
import wap.pagemethods.Budget2019PageMethods;

public class BudgetPage2019 extends BaseTest {

	private String budgetUrl;
	private Budget2019PageMethods budget2019PageMethods;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		budgetUrl = BaseTest.baseUrl + Config.fetchConfigProperty("BudgetUrl");
		launchBrowser(budgetUrl);
		budget2019PageMethods = new Budget2019PageMethods(driver);

	}

	@Test(description = "This test verifies if there is any broken link on the budget homepage")
	public void verifyBrokenLinks() {
		softAssert = new SoftAssert();
		WaitUtil.sleep(5000);
		List<String> allUrls = budget2019PageMethods.getAllUrls();
		System.out.println(allUrls.size());
		allUrls.forEach(url -> {
			if (url.contains("economictimes")) {
				int responseCode = HTTPResponse.checkResponseCode(url);
				softAssert.assertTrue(responseCode == 200,
						"For " + url + " the response code is:" + responseCode + ". ");
			}

		});

		softAssert.assertAll();
	}

	@AfterClass
	public void teardown() {
		driver.quit();
	}

}
