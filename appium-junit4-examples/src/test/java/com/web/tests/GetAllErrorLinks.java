package com.web.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.HTTPResponse;
import common.utilities.WaitUtil;
import web.pagemethods.HomePageMethods;
import web.pagemethods.WebBaseMethods;

public class GetAllErrorLinks extends BaseTest {

	HomePageMethods homePageMethods;
	SoftAssert softAssert;
	String urlString;

	@BeforeClass
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		launchBrowser(baseUrl);
		homePageMethods = new HomePageMethods(driver);

	}

	@Test(description = "This test checks for all the broken links on the page", dataProvider = "sheetName")
	public void getAllErrorLinksOnThePage(String sheetName) throws IOException {
		softAssert = new SoftAssert();
		System.out.println(sheetName);
		List<String> urlList = getTheListOfAllUrls(sheetName);
		System.out.println(urlList);

		urlList.forEach(url -> {
			try {
				urlString = baseUrl + url;
				driver.get(urlString);
				WaitUtil.sleep(4000);
				WebBaseMethods.scrollToBottom();
				WebBaseMethods.scrollToMiddle();
				WebBaseMethods.scrollToTop();

				List<String> allUrlList = new LinkedList<>();

				allUrlList = homePageMethods.getAllShowHref();

				Set<String> allHrefSet = new HashSet<String>(allUrlList);
				System.out.println(allHrefSet.size());
				List<String> failedUrls = new LinkedList<>();

				allHrefSet.forEach(href -> {
					try {

						if (!href.contains("javascript") && href.contains("economictimes")) {
							int response = HTTPResponse.checkResponseCode(href);
							System.out.println(href + "-----" + response);
							if (response != 200) {
								failedUrls.add("<br>- <a href=" + href + "> " + href + " </a>" + " is having response= "
										+ response);
							}

						}
					} catch (Exception ee) {
						ee.printStackTrace();
						failedUrls.add("<br>- <a href=" + href + "> " + href + " </a>");

					}
				});
				softAssert.assertTrue(failedUrls.size() == 0, "Few links on page " + urlString
						+ " are not having the response code 200, list of such links:" + failedUrls);
			} catch (Exception ee) {
				ee.printStackTrace();
				softAssert.assertTrue(false, "exception occured while checking for url: " + urlString);

			}
		});

		softAssert.assertAll();
	}

	@DataProvider(name = "sheetName")
	public Object[] sheetName() {
		// String[] sectionName = { "HomePage", "Markets", "News", "Industry",
		// "SmallBiz", "Wealth", "MutualFunds", "Tech",
		// "Opinion", "Nri", "Panache", "EtNow", "Jobs", "Prime" };
		String[] sectionName = { "HomePage", "Prime", "ETL1Pages", "ETPrimeL1Pages" };
		return sectionName;
	}

	public List<String> getTheListOfAllUrls(String sheetName) {
		List<String> urlList = new LinkedList<>();
		try {
			String sectionsName = "./src/main/resources/testdata/web/UrlSectionWise.xlsx";
			File file = new File(sectionsName);
			ExcelUtil excelUtil = new ExcelUtil(file);
			int index = excelUtil.getWorkbook().getSheetIndex(sheetName);
			excelUtil.setSheet(excelUtil.getWorkbook().getSheetAt(index));

			excelUtil.extractAsList(0).forEach(li -> {
				String url = li.get(0).toString();
				urlList.add(url);
			});
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return urlList;
	}

}
