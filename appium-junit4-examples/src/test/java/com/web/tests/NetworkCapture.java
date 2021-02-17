package com.web.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.HARUtil;
import common.utilities.browser.ProxyBrowserUtil;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.HarRequest;
import de.sstoehr.harreader.model.HarResponse;

public class NetworkCapture extends BaseTest {
	private ProxyBrowserUtil proxyBrowserUtil;
	private String pathAppender = "./src/main/resources/testdata/web/";
	// private String filePath = pathAppender+"UrlRepo.xlsx";
	private String excelPath = pathAppender + "ListofURLs(ET).xlsx";
	private Map<HarRequest, HarResponse> reqRespMap = new HashMap<>();
	private File file = new File(excelPath);
	private ExcelUtil excelUtil;
	private String nameVal;

	private String sheetName = "img.etimg.com";

	@BeforeClass
	public void setUp() throws IOException {
		excelUtil = new ExcelUtil(file);
		proxyBrowserUtil = ProxyBrowserUtil.getInstance();
		launchBrowser();
	}

	@Test(dataProvider = "Page Data", description = "This test verifies analytics calls on ,")
	public void verifyAkamaiHeaders(String pageName, String pageUrl) throws IOException, HarReaderException {
		proxyBrowserUtil = ProxyBrowserUtil.getInstance();
		System.out.println("pageUrl:" + pageUrl);
		// Verifying requests on page load

		file = proxyBrowserUtil.returnHARFile(pageUrl);
		if (file == null)
			return;
		reqRespMap = HARUtil.getAllEntriesWithResponse(file, pageUrl);
		HarResponse response = reqRespMap.values().iterator().next();
		Map<List<String>, List<String>> headersMap = HARUtil.getAllMatchingHeaders(response, "content,strict-transport-security,vary,server,status,imageMagick_im4java,appgn");
		headersMap.forEach((K, V) -> {
			nameVal = "";
			for (int i = 0; i < K.size(); i++) {
				nameVal += K.get(i) + ":" + V.get(i) + " | ";
				System.out.println(nameVal);
			}
		});
		excelUtil.setCellData(sheetName, "Headers1", Integer.parseInt(pageName) + 1, nameVal);

	}

	@DataProvider(name = "Page Data")
	public String[][] getExcelData() {
		String[][] dataArray = new String[excelUtil.getRowCount(sheetName) - 1][2];
		for (int i = 2; i <= excelUtil.getRowCount(sheetName); i++) {
			for (int j = 0; j < 2; j++) {
				dataArray[i - 2][j] = excelUtil.getCellData(sheetName, j, i).trim();
			}
		}
		return dataArray;
	}
}
