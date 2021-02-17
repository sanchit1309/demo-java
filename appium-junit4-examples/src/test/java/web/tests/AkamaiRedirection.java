package web.tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.HTTPResponse;
import common.utilities.WaitUtil;

public class AkamaiRedirection extends BaseTest {
	// static URL url = null;
	// private String excelPath =
	// "./src/main/resources/testdata/web/AkamaiRedirectionETPWA.xlsx";
	// private String excelPath =
	// "./src/main/resources/testdata/web/AkamaiRedirection.xlsx";
	private String excelPathForWAPtoWEBUrl = "./src/main/resources/testdata/web/WapToWeb.xlsx";
	private File WAPtoWEBurlFile = new File(excelPathForWAPtoWEBUrl);
	ExcelUtil excelUtilWAPtoWEB = new ExcelUtil(WAPtoWEBurlFile);

	private String excelPathWapToWebResult = "./src/main/resources/testdata/web/AkamaiRedirectionResult2.xlsx";
	private File redirectedWAPtoWEBfile = new File(excelPathWapToWebResult);
	ExcelUtil excelUtilRedirectedWAPtoWEB = new ExcelUtil(redirectedWAPtoWEBfile);

	private String excelPathForWEBtoWAPUrl = "./src/main/resources/testdata/web/WEBtoWAP.xlsx";
	private File WEBtoWebUrlFile = new File(excelPathForWEBtoWAPUrl);
	ExcelUtil excelUtilWebToWap = new ExcelUtil(WEBtoWebUrlFile);

	private String excelPathWEBtoWAPResult = "./src/main/resources/testdata/web/AkamaiRedirectionResult.xlsx";
	private File redirectedWEBtoWAPfile = new File(excelPathWEBtoWAPResult);
	ExcelUtil excelUtilRedirectedWEBtoWAP = new ExcelUtil(redirectedWEBtoWAPfile);

	int rowCount = 2;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
	}

	@Test(description = "this test verifies the redirection code from Web to WAP in mobile browser")
	public void checkRedirectionWEBtoWAP() throws IOException {
		ArrayList<ArrayList<Object>> list = excelUtilWebToWap.extractAsList(3);

		list.forEach(li -> {

			try {
				System.out.println(li.get(0));
				String url = li.get(0).toString();
				excelUtilRedirectedWEBtoWAP.setCellData("StatusSheet", "RequestedURL", rowCount, url);
				String currentUrl = HTTPResponse.getLocationFor301Or302StatusCode(url);
				System.out.println("url checked: ---" + url);
				System.out.println("redirected URL: ----" + currentUrl);
				if (currentUrl == null) {
					currentUrl = "";
				}

				if (currentUrl.length() > 5) {
					excelUtilRedirectedWEBtoWAP.setCellData("StatusSheet", "RedirectedURL", rowCount, currentUrl);
					url = url.replace("https://economictimes.indiatimes.com", "");

					try {
						int response = HTTPResponse.checkResponseCode(currentUrl);
						String updatedCurrentUrl = currentUrl.replace("https://m.economictimes.com", "");

						if (response == 200 && url.contentEquals(updatedCurrentUrl)
								&& currentUrl.contains("https://m.economictimes.com")) {
							excelUtilRedirectedWEBtoWAP.setCellData("StatusSheet", "Status", rowCount, "Pass");
						} else if (response == 200) {
							excelUtilRedirectedWEBtoWAP.setCellData("StatusSheet", "Status", rowCount,
									"FAIL, redirected url is not proper");
						} else {
							excelUtilRedirectedWEBtoWAP.setCellData("StatusSheet", "Status", rowCount,
									"FAIL, reponse code of redirected url is: " + response);

						}
					} catch (Exception e) {
						System.out.println("failed for " + currentUrl);
						excelUtilRedirectedWEBtoWAP.setCellData("StatusSheet", "Status", rowCount, "FAIL");
						e.printStackTrace();
					}

				} else {
					excelUtilRedirectedWEBtoWAP.setCellData("StatusSheet", "RedirectedURL", rowCount, currentUrl);
					excelUtilRedirectedWEBtoWAP.setCellData("StatusSheet", "Status", rowCount, "FAIL, NO REDIRECTION");

				}
				System.out.println("*****" + rowCount);
				rowCount++;

			} catch (Exception ee) {
				ee.printStackTrace();
				excelUtilRedirectedWEBtoWAP.setCellData("StatusSheet", "RedirectedURL", rowCount,
						"FAIL, exception encountered");
				excelUtilRedirectedWEBtoWAP.setCellData("StatusSheet", "Status", rowCount, "FAIL, NO REDIRECTION");
				System.out.println("*****" + rowCount);
				rowCount++;
			}
		});
	}

	@Test(description = "this test verifies the redirection code from wap to web in desktop browser")
	public void checkRedirectionWAPtoWEB() throws IOException {

		// String url = "https://m.economictimes.com";
		ArrayList<ArrayList<Object>> list = excelUtilWAPtoWEB.extractAsList(3);
		list.forEach(li -> {
			try {
				System.out.println(li.get(0));
				String url = li.get(0).toString();
				excelUtilRedirectedWAPtoWEB.setCellData("StatusSheet", "RequestedURL", rowCount, url);
				String currentUrl = HTTPResponse.getLocationFor301Or302StatusCode(url);
				System.out.println(url);
				System.out.println(currentUrl);

				if (currentUrl.length() > 5) {
					excelUtilRedirectedWAPtoWEB.setCellData("StatusSheet", "RedirectedURL", rowCount, currentUrl);
					url = url.replace("https://m.economictimes.com", "");

					try {
						int response = HTTPResponse.checkResponseCode(currentUrl);
						String updatedCurrentUrl = currentUrl.replace("https://economictimes.indiatimes.com", "")
								.replace("?from=mdr", "");

						if (response == 200 && url.contentEquals(updatedCurrentUrl)
								&& currentUrl.contains("https://economictimes.indiatimes.com")) {
							excelUtilRedirectedWAPtoWEB.setCellData("StatusSheet", "Status", rowCount, "Pass");
						} else if (response == 200) {
							excelUtilRedirectedWAPtoWEB.setCellData("StatusSheet", "Status", rowCount,
									"FAIL, redirected url is not proper");
						} else {
							excelUtilRedirectedWAPtoWEB.setCellData("StatusSheet", "Status", rowCount,
									"FAIL, reponse code of redirected url is: " + response);

						}
					} catch (Exception e) {
						System.out.println("failed for " + currentUrl);
						excelUtilRedirectedWAPtoWEB.setCellData("StatusSheet", "Status", rowCount, "FAIL");
						e.printStackTrace();
					}

				} else {
					excelUtilRedirectedWAPtoWEB.setCellData("StatusSheet", "RedirectedURL", rowCount, url);
					excelUtilRedirectedWAPtoWEB.setCellData("StatusSheet", "Status", rowCount, "FAIL, NO REDIRECTION");

				}
				System.out.println("*****" + rowCount);
				rowCount++;
			} catch (Exception ee) {
				excelUtilRedirectedWAPtoWEB.setCellData("StatusSheet", "RedirectedURL", rowCount, "Fail");
				excelUtilRedirectedWAPtoWEB.setCellData("StatusSheet", "Status", rowCount, "FAIL, NO REDIRECTION");

			}
		});
	}

	@Test(description = "this test verifies the redirection code", enabled = false)
	public void checkRedirection() throws IOException {

		// String url = "https://m.economictimes.com";
		ArrayList<ArrayList<Object>> list = excelUtilWAPtoWEB.extractAsList(3);
		list.forEach(li -> {
			System.out.println(li.get(0));
			String url = li.get(0).toString();
			String currentUrl = HTTPResponse.getLocationFor301Or302StatusCode(url);
			url = url.replace("https://m.economictimes.com", "");
			System.out.println(url);
			System.out.println(currentUrl);
			excelUtilRedirectedWEBtoWAP.setCellData("StatusSheet", "RedirectedURL", rowCount, currentUrl);

			if (currentUrl.length() > 5) {

				try {
					int response = HTTPResponse.checkResponseCode(currentUrl);
					String updatedCurrentUrl = currentUrl.replace("https://economictimes.indiatimes.com", "")
							.replace("?from=mdr", "");

					if (response == 200 && url.contentEquals(updatedCurrentUrl)
							&& currentUrl.contains("https://economictimes.indiatimes.com")) {
						excelUtilRedirectedWEBtoWAP.setCellData("StatusSheet", "Status", rowCount, "Pass");
					} else {

						excelUtilRedirectedWEBtoWAP.setCellData("StatusSheet", "Status", rowCount, "FAIL");
					}
				} catch (Exception e) {
					System.out.println("failed for " + currentUrl);
					excelUtilRedirectedWEBtoWAP.setCellData("StatusSheet", "Status", rowCount, "FAIL");
					e.printStackTrace();
				}

			}
			rowCount++;
		});
	}

	@Test(description = "this test verifies the redirection code", enabled = false)
	public void checkRedirectionInBrowser() throws IOException {

		// String url = "https://m.economictimes.com";
		ArrayList<ArrayList<Object>> list = excelUtilWAPtoWEB.extractAsList(3);
		list.forEach(li -> {
			System.out.println(li.get(0));
			String url = li.get(0).toString();
			try {
				launchBrowser(url);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			WaitUtil.sleep(1000);
			String currentUrl = driver.getCurrentUrl();
			url = url.replace("https://m.economictimes.com", "");
			System.out.println(url);
			System.out.println(currentUrl);
			excelUtilRedirectedWEBtoWAP.setCellData("StatusSheet", "RedirectedURL", rowCount, currentUrl);

			if (currentUrl.length() > 5) {

				try {
					// int response =
					// HTTPResponse.checkResponseCode(currentUrl);
					String updatedCurrentUrl = currentUrl.replace("https://economictimes.indiatimes.com", "")
							.replace("?from=mdr", "");
					System.out.println(updatedCurrentUrl);
					if (url.contentEquals(updatedCurrentUrl)
							&& currentUrl.contains("https://economictimes.indiatimes.com")) {
						excelUtilRedirectedWEBtoWAP.setCellData("StatusSheet", "Status", rowCount, "Pass");
					} else {

						excelUtilRedirectedWEBtoWAP.setCellData("StatusSheet", "Status", rowCount, "FAIL");
					}
				} catch (Exception e) {
					System.out.println("failed for " + currentUrl);
					excelUtilRedirectedWEBtoWAP.setCellData("StatusSheet", "Status", rowCount, "FAIL");
					e.printStackTrace();
				}

			}
			rowCount++;
			driver.quit();
		});
	}

	@Test(description = "this test verifies the redirection code", enabled = false)
	public void checkRedirectionInBrowserWebToWap() throws IOException {

		// String url = "https://m.economictimes.com";
		ArrayList<ArrayList<Object>> list = excelUtilWAPtoWEB.extractAsList(3);
		list.forEach(li -> {
			System.out.println(li.get(0));
			String url = li.get(0).toString();
			try {
				driver.get(url);

			} catch (Exception e1) {
				e1.printStackTrace();
			}
			WaitUtil.sleep(5000);
			String currentUrl = driver.getCurrentUrl();

			url = url.replace("https://economictimes.indiatimes.com", "");
			System.out.println(url);
			System.out.println(currentUrl);
			excelUtilRedirectedWEBtoWAP.setCellData("StatusSheet", "RedirectedURL", rowCount, currentUrl);

			if (currentUrl.length() > 5) {

				try {
					// int response =
					// HTTPResponse.checkResponseCode(currentUrl);
					String updatedCurrentUrl = currentUrl.replace("https://m.economictimes.com", "");
					System.out.println(updatedCurrentUrl);
					if (url.contentEquals(updatedCurrentUrl) && currentUrl.contains("https://m.economictimes.com")) {
						excelUtilRedirectedWEBtoWAP.setCellData("StatusSheet", "Status", rowCount, "Pass");
					} else {

						excelUtilRedirectedWEBtoWAP.setCellData("StatusSheet", "Status", rowCount, "FAIL");
					}
				} catch (Exception e) {
					System.out.println("failed for " + currentUrl);
					excelUtilRedirectedWEBtoWAP.setCellData("StatusSheet", "Status", rowCount, "FAIL");
					e.printStackTrace();
				}

			}
			rowCount++;

		});
	}

}
