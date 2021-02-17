package com.web.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.HTTPResponse;

public class CheckUrlRedirections extends BaseTest {
	public String platform = "Web";
	File redirectionsUrlCheckFile;
	ExcelUtil redirectionsUrlCheckUtil;
	int i = 0;

	public Map<String, String> urlsMap = new LinkedHashMap<>();
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		platform = BaseTest.platform;
		String redirectionsUrlCheck;

		if (platform.equalsIgnoreCase("wap")) {
			redirectionsUrlCheck = "./src/main/resources/testdata/wap/CheckUrlRedirectionsWAP.xlsx";

		} else {
			redirectionsUrlCheck = "./src/main/resources/testdata/web/CheckUrlRedirections.xlsx";
		}
		redirectionsUrlCheckFile = new File(redirectionsUrlCheck);
		redirectionsUrlCheckUtil = new ExcelUtil(redirectionsUrlCheckFile);

	}

	public Map<String, String> getUrlsData() {
		Map<String, String> urlsMapFromExcel = new LinkedHashMap<>();
		List<ArrayList<Object>> actualUrlList = redirectionsUrlCheckUtil.extractAsList(0);
		List<ArrayList<Object>> expectedRedirectedUrlList = redirectionsUrlCheckUtil.extractAsList(1);

		i = 0;
		actualUrlList.forEach(url -> {
			String actualUrl = url.get(0).toString();
			String expectedRedirectedUrl = expectedRedirectedUrlList.get(i).get(0).toString();
			i++;
			urlsMapFromExcel.put(actualUrl, expectedRedirectedUrl);
		});
		return urlsMapFromExcel;
	}

	@Test(description = "This test verifies the redirection of urls")
	public void checkRedirections() {
		softAssert = new SoftAssert();
		urlsMap = getUrlsData();
		urlsMap.forEach((actualUrl, expectedRedirectedUrl) -> {
			System.out.println("actualUrl: " + actualUrl + "---expectedRedirectedUrl: " + expectedRedirectedUrl);
			String url = HTTPResponse.getLocationFor301Or302StatusCode(actualUrl);
			int response = HTTPResponse.checkResponseCode(url);
			System.out.println("actualUrl: " + actualUrl + "---expectedRedirectedUrl: " + expectedRedirectedUrl
					+ "--url: " + url + " --response: " + response);

			softAssert.assertTrue(url.equalsIgnoreCase(expectedRedirectedUrl),
					"The expected redirected url: " + expectedRedirectedUrl + " for the given actual url: " + actualUrl
							+ " is not matching instead found: " + url);
			softAssert.assertTrue(response == 200, "The redirected url: " + url + "for the actual url: " + actualUrl
					+ " is not having repsonse code 200 instead is having: " + response);

		});

		softAssert.assertAll();
	}

}
