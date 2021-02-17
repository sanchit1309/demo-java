package wap.tests;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.HTTPResponse;
import common.utilities.WaitUtil;
import wap.pagemethods.HomePageMethods;
import wap.pagemethods.WapListingPageMethods;
import web.pagemethods.WebBaseMethods;

public class CheckBrokenLinks extends BaseTest {
	private String baseUrl;
	HomePageMethods homePageMethods;
	private WapListingPageMethods wapListingMethods;

	SoftAssert softAssert;

	@BeforeClass
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		launchBrowser(baseUrl);
		homePageMethods = new HomePageMethods(driver);
		wapListingMethods = new WapListingPageMethods(driver);
		WebBaseMethods.scrollToBottom();

	}

	@Test(description = "this test verifies the broken links by checking all the links")
	public void verifyBrokenLinks() {
		softAssert = new SoftAssert();
		List<String> allRelNavlist = new LinkedList<>();
		allRelNavlist = wapListingMethods.getTopSectionsLink();
		System.out.println(allRelNavlist);
		allRelNavlist.forEach(navLink -> {
			if (!navLink.contains("javascript:void(0)")) {
				WebBaseMethods.navigateToUrl(driver, navLink);
				WaitUtil.sleep(4000);
				// homePageMethods.

				List<String> allUrlList = new LinkedList<>();

				allUrlList = homePageMethods.getAllHref();

				Set<String> allHrefSet = new HashSet<String>(allUrlList);

				System.out.println(allUrlList.size());
				System.out.println(allHrefSet.size() + "--------" + allHrefSet);

				allHrefSet.forEach(href -> {
					if (!href.contains("javascript") && href.contains("economictimes")) {
						int response = HTTPResponse.checkResponseCode(href);
						if (!(response == 0))
							softAssert.assertEquals(response, 200, "<br>- <a href=" + href + "> " + href
									+ " </a> on page " + navLink + " is throwing " + response);
					}
				});

			}
		});

		softAssert.assertAll();
	}
}
