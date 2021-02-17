package web.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.ExcelUtil;
import common.utilities.HARUtil;
import common.utilities.browser.ProxyBrowserUtil;
import common.utilities.browser.localbrowser.LocalBrowserUtilWeb;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.HarRequest;
import de.sstoehr.harreader.model.HarResponse;

public class AnalyticsTest extends BaseTest {

	ProxyBrowserUtil proxyBrowserUtil;
	private String filePath = "./src/main/resources/testdata/web/UrlRepo.xlsx";
	private Map<String, String> expectedIdsMap;
	File file = null;
	boolean isRequestPresent = false;
	List<String> queryParamValue = new LinkedList<>();
	String browserAction = "";
	SoftAssert softAssert = new SoftAssert();
	List<HarRequest> listMatchedRequests = new LinkedList<>();
	Map<String, Boolean> requestFlags = new HashMap<>();
	Map<HarRequest, HarResponse> reqRespMap = new HashMap<>();
	List<String> dlURLList = new LinkedList<>();
	String requestIdentifier = "lazyload";
	private static int countShowPage;

	@BeforeClass
	public void setUp() throws IOException {
		proxyBrowserUtil = ProxyBrowserUtil.getInstance();
		expectedIdsMap = Config.getAllKeys("analyticsEvents");
		launchBrowser();
	}

	@Test(dataProvider = "Page Data", description = "This test verifies analytics calls on ,")
	public void verifyAnalytics(String pageName, String pageUrl, String comScoreFlag, String chartbeatFlag)
			throws IOException, HarReaderException {
		SoftAssert softAssert = new SoftAssert();
		proxyBrowserUtil = ProxyBrowserUtil.getInstance();
		System.out.println("pageUrl:" + pageUrl);
		// Verifying requests on page load
		browserAction = "initial page load";
		file = proxyBrowserUtil.returnHARFile(pageUrl);
		System.out.println("browserAction:" + browserAction);
		if (file == null)
			return;
		expectedIdsMap.forEach((key, value) -> {
			dlURLList = new LinkedList<>();
			queryParamValue = new LinkedList<>();
			System.out.println("key:" + key);
			if (key.equals("scorecard") && !(Boolean.parseBoolean(comScoreFlag)))
				return;
			if (key.equals("chartbeat") && !(Boolean.parseBoolean(chartbeatFlag)))
				return;

			reqRespMap = HARUtil.getAllEntriesWithResponse(file, key);

			List<HarRequest> listMatchedRequests = getListMatchingRequests(reqRespMap, key, value);
			System.out.println("listMatchedRequests:" + listMatchedRequests.size());
			isRequestPresent = listMatchedRequests.size() > 0;
			// requestFlags.put(key, isRequestPresent);
			if (listMatchedRequests.size() > 1 && key.contains("google-analytics")) {
				listMatchedRequests.forEach(request -> {
					Map<String, String> qKeyVal = getQueryStringMapforRequest(request);
					queryParamValue.add(qKeyVal.get("tid"));
				});
				queryParamValue.forEach(paramValue -> {
					// System.out.println(paramValue + "\n" + pageUrl);

					if (paramValue.equals("UA-198011-5")) {
						dlURLList.add(paramValue);
					}

				});
				int actualCount = dlURLList.size();
				// int count =
				// VerificationUtil.isListUnique(analyticsDlParam).size();
				System.out.println("gAnalytics:" + actualCount);
				softAssert.assertTrue(actualCount == 1,
						"<br>- On <a href='" + pageUrl + "'>" + pageUrl.substring(0, pageUrl.length() / 2) + "..</a> "
								+ key + " is not having <b>1</b> entry on " + browserAction
								+ " instead request count is " + actualCount);

			} else {
				int actualCount = listMatchedRequests.size();
				if (!(pageUrl.contains("morning-briefs") && actualCount == 2)) {
					softAssert.assertTrue(actualCount > 0,
							"<br>- On <a href='" + pageUrl + "'>" + pageUrl.substring(0, pageUrl.length() / 2)
									+ "..</a> for " + key + " no request found on " + browserAction);
				}
			}
		});
		/*
		 * // Verifying after scrolling to certain height
		 * System.out.println("*****AFTER SCROLL*******");
		 * 
		 * file = proxyBrowserUtil.returnAfterScrollHARFile(20, "bottom"); if
		 * (file == null) return; browserAction = "page scroll down";
		 * 
		 * Map<HarRequest, HarResponse> lazyLoadCounter =
		 * HARUtil.get200EntriesWithResponse(file, requestIdentifier); // to
		 * exclude requests made for widgets. if
		 * (!pageUrl.contains("https://m.economictimes")) {
		 * lazyLoadCounter.keySet().removeIf(p ->
		 * p.getUrl().contains("widget")); } if (lazyLoadCounter.size() < 1) {
		 * String identifier = getNextChangeIdentifier(pageUrl); if (identifier
		 * != null) { requestIdentifier = identifier; lazyLoadCounter =
		 * HARUtil.get200EntriesWithResponse(file, requestIdentifier); } } int
		 * expectedCount = lazyLoadCounter.size(); expectedIdsMap.forEach((key,
		 * value) -> { dlURLList = new LinkedList<>(); queryParamValue = new
		 * LinkedList<>(); System.out.println("key: " + key); reqRespMap =
		 * HARUtil.get200EntriesWithResponse(file, key); listMatchedRequests =
		 * getListMatchingRequests(reqRespMap, key, value); // if
		 * (requestFlags.get(key)) { if (key.contains("google-analytics") ||
		 * key.contains("scorecard")) { if (key.contains("google-analytics")) {
		 * listMatchedRequests.forEach(request -> { Map<String, String> qKeyVal
		 * = getQueryStringMapforRequest(request); String param = "dp"; if
		 * (qKeyVal.get(param) == null) param = "dl";
		 * queryParamValue.add(qKeyVal.get(param)); });
		 * 
		 * int actualCount = 0;
		 * 
		 * if (requestIdentifier.contains("articleshow") ||
		 * requestIdentifier.contains("slideshow")) {
		 * 
		 * queryParamValue.forEach(paramValue -> { Matcher matcher =
		 * Pattern.compile(".*economictimes.*cms$").matcher(paramValue); if
		 * (matcher.matches()) { dlURLList.add(matcher.group()); }
		 * 
		 * }); actualCount = dlURLList.size(); System.out.println("actualCount:"
		 * + actualCount); if (expectedCount == 0) { // if no ajax then no
		 * analytics request should be // present System.out.
		 * println("*****G ANALYTICS No AJAX ARTICLE SHOW SLIDE SHOW*****");
		 * softAssert.assertTrue(actualCount == 0, "<br>- On <a href='" +
		 * pageUrl + "'>" + pageUrl.substring(0, pageUrl.length() / 2) +
		 * "..</a> for " + key + "<b>no</b> entry is expected on " +
		 * browserAction); } else { System.out.
		 * println("*****G ANALYTICS AJAX PRESENT ARTICLE SHOW SLIDE SHOW*****"
		 * ); // if ajax is fired and request is for article // show or slide
		 * show, checks actual count to be // at-least 1
		 * softAssert.assertTrue(actualCount > 0, "<br>- On <a href='" + pageUrl
		 * + "'>" + pageUrl.substring(0, pageUrl.length() / 2) + "..</a> for " +
		 * key + " entry count on " + browserAction +
		 * " should be greater than 0 as ajax is fired");
		 * dlURLList.add(pageUrl); // checks if duplicate request is sent
		 * List<String> dupList = VerificationUtil.isListUnique(dlURLList);
		 * softAssert.assertTrue(dupList.size() == 0, "<br>- On <a href='" +
		 * pageUrl + "'>" + pageUrl.substring(0, pageUrl.length() / 2) +
		 * "..</a> duplicate entries found for " + key + " on " + browserAction
		 * + "->" + dupList); }
		 * 
		 * } else { queryParamValue.forEach(paramValue -> { //
		 * System.out.println(paramValue + "\n" + pageUrl); if
		 * (paramValue.equalsIgnoreCase(pageUrl)) { dlURLList.add(paramValue); }
		 * 
		 * }); actualCount = dlURLList.size(); // if no ajax then no analytics
		 * request should be // present System.out.println("expectedCount:" +
		 * expectedCount); if (expectedCount == 0) {
		 * System.out.println("*****G ANALYTICS No AJAX LISTING PAGE*****");
		 * System.out.println("actualCount:" + actualCount);
		 * softAssert.assertTrue(actualCount == 0, "<br>- On <a href='" +
		 * pageUrl + "'>" + pageUrl.substring(0, pageUrl.length() / 2) +
		 * "..</a> for " + key + "<b>no</b> entry is expected on " +
		 * browserAction + " ,instead found " + actualCount); } else {
		 * System.out.println("*****G ANALYTICS AJAX PRESENT LISTING PAGE*****"
		 * ); System.out.println("actualCount:" + actualCount); // if ajax is
		 * fired and request type is just for // listings then actual should be
		 * expected softAssert.assertTrue(actualCount == expectedCount,
		 * "<br>- On <a href='" + pageUrl + "'>" + pageUrl.substring(0,
		 * pageUrl.length() / 2) + "..</a> " + key + " on " + browserAction +
		 * " entries count is:" + actualCount + " while expected is:" +
		 * expectedCount);
		 * 
		 * } }
		 * 
		 * } else if (key.contains("scorecard")) { if (expectedCount == 0) { //
		 * softAssert.assertTrue(listMatchedRequests.size() == // 0,
		 * "<br>- On <a href='" + pageUrl + "'>" + // pageUrl.substring(0, //
		 * pageUrl.length() / 2) + "..</a>" + key + " entry // count on " +
		 * browserAction // + " should be <b>zero</b> as no AJAX request is
		 * fired // ,instead found " + listMatchedRequests.size()); } else {
		 * softAssert.assertTrue(listMatchedRequests.size() > 0,
		 * "<br>- On <a href='" + pageUrl + "'>" + pageUrl.substring(0,
		 * pageUrl.length() / 2) + "..</a>" + key +
		 * " no entry was found after AJAX request is fired");
		 * 
		 * } } } /* else { System.out.println("listMatchedRequests:" +
		 * listMatchedRequests.size());
		 * softAssert.assertTrue(listMatchedRequests.size() > 0,
		 * "<br>- On <a href='" + pageUrl + "'>" + pageUrl.substring(0,
		 * pageUrl.length() / 2) + "..</a> for " + key + " no request found on "
		 * + browserAction); }
		 */
		// }

		// });

		// Verifying on scrolling back up again
		/*
		 * System.out.println("*********SCROLL UP***********");dlURLList=new
		 * LinkedList<>();file=proxyBrowserUtil.returnAfterScrollHARFile(20,
		 * "top");browserAction="page scroll up";if(file==null)return;
		 * expectedIdsMap.forEach((key,value)->
		 * 
		 * { System.out.println("key:" + key); reqRespMap =
		 * HARUtil.get200EntriesWithResponse(file, key); listMatchedRequests =
		 * getListMatchingRequests(reqRespMap, key, value);
		 * System.out.println("listMatchedRequests:" +
		 * listMatchedRequests.size()); if (key.contains("google-analytics") ||
		 * key.contains("scorecard")) {
		 * softAssert.assertTrue(listMatchedRequests.size() == 0,
		 * "<br>- On <a href='" + pageUrl + "'>" + pageUrl.substring(0,
		 * pageUrl.length() / 2) + "..</a> " + key + " entry count on " +
		 * browserAction + " is  " + listMatchedRequests.size() +
		 * ", instead <b>no</b> entry was expected."); }
		 * 
		 * });
		 */softAssert.assertAll();

	}

	private String getNextChangeIdentifier(String pageUrl) {
		String nextCallChange = null;
		if (!pageUrl.contains("m.economictimes")) {
			if (pageUrl.contains("articleshow"))
				nextCallChange = "article";
			else if (pageUrl.contains("slideshow"))
				nextCallChange = "slideshow";
		} else {
			if (pageUrl.contains("articleshow"))
				nextCallChange = "articleshowajax";
			else if (pageUrl.contains("slideshow"))
				nextCallChange = "slideshowajax";
		}
		return nextCallChange;

	}

	private Map<String, String> getQueryStringMapforRequest(HarRequest harRequest) {
		Map<String, String> queryParam = new HashMap<>();
		harRequest.getQueryString().forEach(HARQueryParam -> {
			queryParam.put(HARQueryParam.getName(), HARQueryParam.getValue());
		});

		return queryParam;
	}

	private List<HarRequest> getListMatchingRequests(Map<HarRequest, HarResponse> reqRespMap, String requestDomain,
			String toBeSearched) {
		LinkedList<HarRequest> requestList = new LinkedList<HarRequest>();
		reqRespMap.forEach((key, value) -> {
			String url = key.getUrl();
			System.out.println("Before filtering:->" + url);
			if (requestPatternMatcher(toBeSearched, requestDomain, url).length() > 1) {
				requestList.add(key);
			}

		});
		return requestList;
	}

	private String requestPatternMatcher(String toBeSearched, String requestDomain, String entireRequest) {
		String request = "";
		Pattern pattern = Pattern.compile(String.format(".*%s.*%s.*", requestDomain, toBeSearched));
		Matcher matcher = pattern.matcher(entireRequest);
		while (matcher.find())
			request = matcher.group();
		return request;
	}

	@DataProvider(name = "Page Data")
	public String[][] getExcelData() {
		String[][] temp = getShowPageUrlFromSiteMap();
		countShowPage = temp.length;
		File file = new File(filePath);
		ExcelUtil excelUtil = new ExcelUtil(file);
		String sheetName = "Sheet1";
		String[][] dataArray = new String[(excelUtil.getRowCount(sheetName) - 1) + countShowPage][4];
		for (int i = 2; i <= excelUtil.getRowCount(sheetName); i++) {
			for (int j = 0; j < 4; j++)
				if (j >= 2)
					dataArray[i - 2][j] = excelUtil.getCellData(sheetName, j, i).trim().length() > 0
							? excelUtil.getCellData(sheetName, j, i) : "true";
				else
					dataArray[i - 2][j] = excelUtil.getCellData(sheetName, j, i).trim();
		}

		int counter = 0;
		int endCounter = dataArray.length;
		for (int l = endCounter - countShowPage; l < endCounter; l++) {
			for (int m = 0; m < 4; m++) {
				if (m >= 2)
					dataArray[l][m] = "true";
				else {
					System.out.println("Putting:" + temp[counter][m]);
					dataArray[l][m] = temp[counter][m];
				}
			}
			counter++;
		}
		return dataArray;
	}

	@DataProvider(name = "Page Data WAP")
	public String[][] getExcelDataWAP() {
		String filePath = "./src/main/resources/testdata/wap/UrlRepo.xlsx";
		File file = new File(filePath);
		ExcelUtil excelUtil = new ExcelUtil(file);
		String sheetName = "Sheet1";
		String[][] dataArray = new String[excelUtil.getRowCount(sheetName) - 1][2];
		for (int i = 2; i <= excelUtil.getRowCount(sheetName); i++) {
			for (int j = 0; j < 2; j++)

				dataArray[i - 2][j] = excelUtil.getCellData(sheetName, j, i).trim();
		}
		return dataArray;
	}

	@Test(dataProvider = "Page Data WAP", description = "This test verifies network calls on WAP", enabled = false)
	public void verifyNetworkCalls(String pageName, String pageUrl) {
		try {
			proxyBrowserUtil = ProxyBrowserUtil.getInstance();
			List<String> allRequests = new LinkedList<>();
			file = proxyBrowserUtil.returnAfterScrollHARFile(20, "bottom");
			reqRespMap = HARUtil.get200EntriesWithResponse(file, "http:");
			reqRespMap.forEach((K, V) -> {
				String url = K.getUrl();
				System.out.println(url);
				if (!url.contains("ciscobinary"))
					allRequests.add(url);
			});
			Assert.assertTrue(allRequests.size() == 0,
					"HTTP entry found in network calls of " + pageUrl + "-> " + allRequests);
		} catch (Exception e) {

		}
	}

	private static String[][] getShowPageUrlFromSiteMap() {
		LocalBrowserUtilWeb localBrowserUtilWeb = new LocalBrowserUtilWeb();
		String[][] urlList = new String[5][2];
		WebDriver driver = null;
		int count = 1;
		try {
			driver = localBrowserUtilWeb.launchBrowser();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (driver == null)
			return urlList;
		driver.get("https://economictimes.indiatimes.com/sitemap/today");
		String pageSource = driver.getPageSource();
		Pattern pattern = Pattern.compile("(https://economictimes.indiatimes.com/[^.]+show/\\d+.cms)"
				+ "|(https://economictimes.indiatimes.com/[^.]+liveblog/\\d+.cms)");
		Matcher m = pattern.matcher(pageSource);
		while (m.find() && count < 6) {
			urlList[count - 1][0] = "ShowPage_" + count;
			urlList[count - 1][1] = m.group();
			count++;

		}

		driver.quit();

		return urlList;
	}

}
