package common.utilities;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.launchsetup.BaseTest;
import common.utilities.browser.ProxyBrowserUtil;
import de.sstoehr.harreader.model.HarRequest;
import de.sstoehr.harreader.model.HarResponse;

public class NetworkCallsUtil {

	static File file;
	static Map<String, String> valuesFetched = new HashMap<>();
	static Map<String, String> durationValues = new HashMap<>();
	static Map<HarRequest, HarResponse> reqRespMap = new HashMap<>();
	static ProxyBrowserUtil proxyBrowserUtil;
	static BaseTest basetest = new BaseTest();
	static Map<String, Map<String, String>> valuesWithRequestMap = new HashMap<>();
	/*
	 * this function grabs all the request from particular domain(key) on the
	 * given source url and return a map of the grabbed requests and values
	 * found for the values to be fetched(valuestobefethced) on that particular
	 * request
	 */

	public static Map<String, Map<String, String>> getHeaderValuesFromResponse(String sourceUrl, String key,
			String... valuesToBeFetched) {

		Map<String, Map<String, String>> valuesMap = new HashMap<>();
		Map<HarRequest, HarResponse> reqRespMap = new HashMap<>();
		proxyBrowserUtil = ProxyBrowserUtil.getInstance();
		try {
			basetest.launchBrowser();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		file = null;
		file = proxyBrowserUtil.returnHARFileAfterScrolling(sourceUrl, 10, "bottom", 1000);
		if (file == null) {
			return valuesMap;
		}
		String keyForNetworkCalls = key;
		reqRespMap = HARUtil.getAllEntriesWithResponse(file, keyForNetworkCalls);

		if (reqRespMap.size() > 0) {

			reqRespMap.forEach((request, response) -> {
				durationValues = new HashMap<String, String>();
				valuesFetched = new HashMap<String, String>();
				System.out.println(request.getUrl());

				durationValues = HARUtil.getAllMatchingHeadersValue(response, valuesToBeFetched);
				System.out.println(durationValues);
				for (String value : valuesToBeFetched) {
					valuesFetched.put(value, durationValues.get(value));
				}

				valuesMap.put(request.getUrl(), valuesFetched);
			});
		}
		proxyBrowserUtil.killServices();
		return valuesMap;

	}

	/*
	 * this function grabs all the request from particular domain(key) on the
	 * given list of source urls and return a map of the grabbed requests and
	 * values found for the values to be fetched(valuestobefethced) on that
	 * particular request with the sourceurls given
	 */
	public static Map<String, Map<String, Map<String, String>>> getHeaderValuesFromResponse(List<String> sourceUrls,
			String key, String... valuesToBeFetched) {

		Map<String, Map<String, Map<String, String>>> valuesMap = new HashMap<>();

		sourceUrls.forEach(sourceUrl -> {
			proxyBrowserUtil = ProxyBrowserUtil.getInstance();
			try {
				basetest.launchBrowser();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			reqRespMap = new HashMap<>();
			valuesWithRequestMap = new HashMap<>();
			file = null;
			file = proxyBrowserUtil.returnHARFileAfterScrolling(sourceUrl, 10, "bottom", 1000);
			if (file == null) {
				valuesMap.put(sourceUrl, valuesWithRequestMap);

			}
			String keyForNetworkCalls = key;
			reqRespMap = HARUtil.getAllEntriesWithResponse(file, keyForNetworkCalls);

			if (reqRespMap.size() > 0) {

				reqRespMap.forEach((request, response) -> {
					durationValues = new HashMap<String, String>();
					valuesFetched = new HashMap<String, String>();
					System.out.println(request.getUrl());

					durationValues = HARUtil.getAllMatchingHeadersValue(response, valuesToBeFetched);
					System.out.println(durationValues);
					for (String value : valuesToBeFetched) {
						String valueFetch = durationValues.get(value);
						if (valueFetch != null) {
							valuesFetched.put(value, durationValues.get(value));
						} else {
							valuesFetched.put(value, "value not found in the response for " + value);
						}
					}

					valuesWithRequestMap.put(request.getUrl(), valuesFetched);
				});
			}
			valuesMap.put(sourceUrl, valuesWithRequestMap);
			proxyBrowserUtil.killServices();
		});
		return valuesMap;

	}

}
