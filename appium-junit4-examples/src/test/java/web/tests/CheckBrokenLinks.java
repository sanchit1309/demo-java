package web.tests;

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
import web.pagemethods.HomePageMethods;
import web.pagemethods.WebBaseMethods;

public class CheckBrokenLinks extends BaseTest {

	private String baseUrl;
	HomePageMethods homePageMethods;
	SoftAssert softAssert;

	@BeforeClass
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		launchBrowser(baseUrl);
		homePageMethods = new HomePageMethods(driver);
		WebBaseMethods.scrollToBottom();

	}

	@Test(description = "this test verifies the broken links by checking all the links", priority = 0)
	public void verifyBrokenLinks() {
		softAssert = new SoftAssert();

		WaitUtil.sleep(6000);

		List<String> allUrlListFromThePage = new LinkedList<>();

		allUrlListFromThePage = homePageMethods.getAllHref();

		Set<String> allHrefSetFromThePage = new HashSet<String>(allUrlListFromThePage);

		System.out.println(allUrlListFromThePage.size());
		allHrefSetFromThePage.forEach(href -> {
			if (!href.contains("javascript") && href.contains("economictimes")) {
				int response = HTTPResponse.checkResponseCode(href);
				if (!(response == 0))
					softAssert.assertEquals(response, 200, "<br>- <a href=" + href + "> " + href + " </a> on page "
							+ baseUrl + " is throwing " + response);
			}
		});

		softAssert.assertAll();
	}

	@Test(description = "this test verifies the broken links on the subsections of the page", priority = 1)
	public void verifyBrokenLinksOnSubsec() {

		List<String> allRelNavlist = new LinkedList<>();
		allRelNavlist = homePageMethods.getAllRelNavLinks();
		System.out.println(allRelNavlist);
		WaitUtil.sleep(6000);

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

	}

}
