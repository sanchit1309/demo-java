package common.utilities;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.Har;
import de.sstoehr.harreader.model.HarEntry;
import de.sstoehr.harreader.model.HarHeader;
import de.sstoehr.harreader.model.HarRequest;
import de.sstoehr.harreader.model.HarResponse;

public class HARUtil {

	public static Map<HarRequest, HarResponse> getAllEntriesWithResponse(File file, String api) {
		Map<HarRequest, HarResponse> getAllEntries = new LinkedHashMap<>();
		HarReader harReader = new HarReader();
		Har harRead = null;
		try {
			harRead = harReader.readFromFile(file);
		} catch (HarReaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<HarEntry> hentry = harRead.getLog().getEntries();
		for (HarEntry entry : hentry) {
			if (entry.getRequest().getUrl().contains(api)) {
				getAllEntries.put(entry.getRequest(), entry.getResponse());
				System.out.println("Request with key:" + api + " ->" + entry.getRequest().getUrl());
			}
		}
		System.out.println("getAllEntries:" + getAllEntries.size());
		return getAllEntries;
	}

	public static Map<HarRequest, HarResponse> get200EntriesWithResponse(File file, String api) {
		Map<HarRequest, HarResponse> getAllEntries = new LinkedHashMap<>();
		HarReader harReader = new HarReader();
		Har harRead = null;
		try {
			harRead = harReader.readFromFile(file);
		} catch (HarReaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<HarEntry> hentry = harRead.getLog().getEntries();
		for (HarEntry entry : hentry) {
			if (entry.getRequest().getUrl().contains(api)
					&& Pattern.compile("20.*").matcher(entry.getResponse().getStatus() + "").matches())
				getAllEntries.put(entry.getRequest(), entry.getResponse());
		}
		System.out.println("getAllEntries:" + getAllEntries.size());
		return getAllEntries;
	}

	public static Map<HarRequest, HarResponse> getNon200Entries(File file) {
		Map<HarRequest, HarResponse> getAllEntries = new LinkedHashMap<>();
		HarReader harReader = new HarReader();
		Har harRead = null;
		try {
			harRead = harReader.readFromFile(file);
		} catch (HarReaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<HarEntry> hentry = harRead.getLog().getEntries();
		for (HarEntry entry : hentry) {
			if ((Pattern.compile("40.*").matcher(entry.getResponse().getStatus() + "").matches())
					|| ((Pattern.compile("50.*").matcher(entry.getResponse().getStatus() + "").matches())))
				getAllEntries.put(entry.getRequest(), entry.getResponse());
		}
		System.out.println("getAllEntries:" + getAllEntries.size());
		return getAllEntries;
	}

	public static Map<List<String>, List<String>> getAllMatchingHeaders(HarResponse response, String... toFilter) {
		List<String> filterLi = Arrays.asList(toFilter);
		Map<List<String>, List<String>> getAllEntries = new LinkedHashMap<>();
		List<String> headerName = new LinkedList<String>();
		List<String> headerValue = new LinkedList<String>();
		List<HarHeader> headerList = response.getHeaders();
		for (HarHeader header : headerList) {
			System.out.println(header.getName());
			if (filterLi.stream()
					.anyMatch(p -> p.toLowerCase().contains(header.getName().split("-")[0].toLowerCase()))) {
				headerName.add(header.getName());
				headerValue.add(header.getValue());
			}
		}
		getAllEntries.put(headerName, headerValue);
		return getAllEntries;
	}

	public static Map<String, String> getAllMatchingHeadersValue(HarResponse response, String... toFilter) {
		List<String> filterLi = Arrays.asList(toFilter);
		Map<String, String> getAllEntries = new LinkedHashMap<>();

		// List<String> headerValue = new LinkedList<String>();
		List<HarHeader> headerList = response.getHeaders();
		for (HarHeader header : headerList) {

			String headerName = new String();
			String headerValue = new String();
			System.out.println(header.getName());
			if (filterLi.stream()
					.anyMatch(p -> p.toLowerCase().contains(header.getName().split("-")[0].toLowerCase()))) {
				headerName = header.getName();
				headerValue = header.getValue();
				getAllEntries.put(headerName, headerValue);
			}
		}
		return getAllEntries;
	}

	public static List<HarRequest> getListMatchingRequests(Map<HarRequest, HarResponse> reqRespMap, String requestDomain,
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

	public static String requestPatternMatcher(String toBeSearched, String requestDomain, String entireRequest) {
		String request = "";
		Pattern pattern = Pattern.compile(String.format(".*%s.*%s.*", requestDomain, toBeSearched));
		Matcher matcher = pattern.matcher(entireRequest);
		while (matcher.find())
			request = matcher.group();
		return request;
	}
}
