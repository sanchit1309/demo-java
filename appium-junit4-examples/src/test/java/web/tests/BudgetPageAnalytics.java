package web.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.HARUtil;
import common.utilities.browser.ProxyBrowserUtil;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.HarRequest;
import de.sstoehr.harreader.model.HarResponse;
import web.pagemethods.BudgetPageMethods;

public class BudgetPageAnalytics extends BaseTest {

	private String budgetUrl;
	BudgetPageMethods budgetPageMethods;
	SoftAssert softAssert;
	private ProxyBrowserUtil proxyBrowserUtil;
	private Map<String, String> expectedIdsMap;
	private File file;
	private Map<HarRequest, HarResponse> reqRespMap;
	private boolean isRequestPresent;
	private LinkedList<String> queryParamValue;
	private LinkedList<String> dlURLList;
	private Map<HarRequest, HarResponse> reqRespMapFail;

	@BeforeClass
	public void setUp() throws IOException {
		proxyBrowserUtil = ProxyBrowserUtil.getInstance();
		expectedIdsMap = Config.getAllKeys("analyticsEvents");
		launchBrowser();
	}

	@Test(description = "This test verifies analytics calls on budget page")
	public void verifyAnalytics() throws IOException, HarReaderException {
		budgetUrl = BaseTest.baseUrl + Config.fetchConfigProperty("BudgetUrl");
		SoftAssert softAssert = new SoftAssert();
		proxyBrowserUtil = ProxyBrowserUtil.getInstance();
		System.out.println("pageUrl:" + budgetUrl);
		// Verifying requests on page load
		String browserAction = "pageload";
		file = proxyBrowserUtil.returnHARFile(budgetUrl);
		if (file == null)
			return;
		expectedIdsMap.forEach((key, value) -> {
			dlURLList = new LinkedList<>();
			queryParamValue = new LinkedList<>();
			System.out.println("key:" + key);
			if (key.equals("scorecard") && !(Boolean.parseBoolean("true")))
				return;
			if (key.equals("chartbeat") && !(Boolean.parseBoolean("true")))
				return;
			reqRespMapFail = HARUtil.getNon200Entries(file);
			reqRespMapFail.forEach((K, V) -> {
				softAssert.assertTrue(false, "Request: " + K.getUrl() + " is giving response: " + V.getStatus());
			});
			reqRespMap = HARUtil.get200EntriesWithResponse(file, key);
			List<HarRequest> listMatchedRequests = getListMatchingRequests(reqRespMap, key, value);
			System.out.println("listMatchedRequests:" + listMatchedRequests.size());
			isRequestPresent = listMatchedRequests.size() > 0;
			// requestFlags.put(key, isRequestPresent);
			if (listMatchedRequests.size() > 1 && key.contains("google-analytics")) {
				listMatchedRequests.forEach(request -> {
					Map<String, String> qKeyVal = getQueryStringMapforRequest(request);
					queryParamValue.add(qKeyVal.get("dl"));
				});
				queryParamValue.forEach(paramValue -> {
					// System.out.println(paramValue + "\n" + pageUrl);
					if (paramValue.equalsIgnoreCase(budgetUrl)) {
						dlURLList.add(paramValue);
					}

				});
				int actualCount = dlURLList.size();
				// int count =
				// VerificationUtil.isListUnique(analyticsDlParam).size();
				System.out.println("gAnalytics:" + actualCount);
				softAssert.assertTrue(actualCount == 1,
						"<br>- On <a href='" + budgetUrl + "'>" + budgetUrl + "..</a> " + key
								+ " is not having <b>1</b> entry on " + browserAction + " instead request count is "
								+ actualCount);

			} else {
				softAssert.assertTrue(reqRespMap.size() > 0, "<br>- On <a href='" + budgetUrl + "'>" + budgetUrl
						+ "..</a> for " + key + " no request found on " + browserAction);
			}
		});

		softAssert.assertAll();

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

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}
