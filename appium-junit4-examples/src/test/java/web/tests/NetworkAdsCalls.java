package web.tests;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.google.common.collect.Lists;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.HARUtil;
import common.utilities.WaitUtil;
import common.utilities.browser.ProxyBrowserUtil;
import common.utilities.browser.localbrowser.LocalBrowserUtilWeb;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.HarRequest;
import de.sstoehr.harreader.model.HarResponse;

public class NetworkAdsCalls extends BaseTest {

	ProxyBrowserUtil proxyBrowserUtil;
	private String filePath = "./src/main/resources/testdata/web/MobileAdsCallWebURL.xlsx";

	File file = null;
	boolean isRequestPresent = false;
	List<String> queryParamValue = new LinkedList<>();
	String browserAction = "";
	SoftAssert softAssert = new SoftAssert();
	// Map<List<String>, List<String>> durationValues = new HashMap<>();
	Map<String, String> durationValues = new HashMap<>();
	List<HarRequest> listMatchedRequests = new LinkedList<>();
	Map<String, Boolean> requestFlags = new HashMap<>();
	Map<HarRequest, HarResponse> reqRespMap = new HashMap<>();
	String expiryDate = "";
	String lastModifiedDate = "";
	int urlCounter = 0;

	@BeforeClass
	public void setUp() throws IOException {
		proxyBrowserUtil = ProxyBrowserUtil.getInstance();
	}

	@BeforeMethod
	public void browserSetUp() throws IOException {
		proxyBrowserUtil = ProxyBrowserUtil.getInstance();
		launchBrowser();

	}

	@Test(dataProvider = "Url List", description = "This verifies the network calls on web from mobileads.indiatimes.com on, ")
	public void verifyMobileAdsNetworkCall(String pageName, String url) throws IOException, HarReaderException {
		SoftAssert softAssert = new SoftAssert();

		file = proxyBrowserUtil.returnHARFileAfterScrolling(url, 10, "bottom", 1000);
		if (file == null) {
			Assert.assertTrue(false, "the Network file is not created for url: " + url);
			return;
		}
		String key = "mobileads.indiatimes.com";
		reqRespMap = HARUtil.getAllEntriesWithResponse(file, key);
		// Assert.assertTrue(reqRespMap.size() > 0, "<b>No mobileads network
		// call is found on the url:</b> " + url);
		if (reqRespMap.size() > 0) {
			durationValues = new HashMap<String, String>();

			reqRespMap.forEach((request, response) -> {
				try {
					System.out.println(request.getUrl());

					durationValues = HARUtil.getAllMatchingHeadersValue(response, "last-modified", "expires",
							"content-type", "content-length");
					System.out.println(durationValues);

					String lastModified = durationValues.get("Last-Modified");
					String expiry = durationValues.get("Expires");
					String contentType = durationValues.get("Content-Type");

					if (lastModified != null && expiry != null) {
						System.out.println("last-modified: " + lastModified);
						DateTime lastModifiedDate = getDateTime(lastModified);
						System.out.println("expiry: " + expiry);
						DateTime expiryDate = getDateTime(expiry);
						softAssert.assertTrue(
								Days.daysBetween(lastModifiedDate.toLocalDate(), expiryDate.toLocalDate())
										.getDays() >= 1,
								"<b>The ttl for the request:</b> " + request.getUrl() + " on page <b>URL:</b> " + url
										+ " is less than <b>one day</b> as the <b>last-modified date is:</b> "
										+ lastModified + " and the <b>expiration date</b> set is " + expiry + "<br />");
					} else {
						softAssert.assertTrue(false,
								"<b>The Expiry date field is not found in the response header for the request:</b> "
										+ request.getUrl() + "<br /><b>for url:</b> " + url + "<br />");
					}

					if (contentType.contains("image/")) {
						double contentLength = Integer.valueOf(durationValues.get("Content-Length"));
						double creativeSize = contentLength / 1024;
						System.out.println("creative Size: " + creativeSize);
						softAssert.assertTrue(creativeSize <= 100,
								" <b>The creative size is " + creativeSize + " kb for the request:</b> "
										+ request.getUrl() + "<br /><b>for url:</b> " + url
										+ "<br /><b> is more than 100 kb</b> <br />");

					}

					if (contentType.contains("video/")) {

						double contentLength = Integer.valueOf(durationValues.get("Content-Length"));
						double creativeSize = contentLength / 1024;
						creativeSize = creativeSize / 1024;

						System.out.println("creative Size: " + creativeSize);
						softAssert.assertTrue(creativeSize <= 1.20,
								" <b>The creative size is " + creativeSize + " mb for the request:</b> "
										+ request.getUrl() + "<br /><b>for url:</b> " + url
										+ "<br /><b> is more than 1.20 mb</b> <br />");

					}
				} catch (Exception ee) {
					ee.printStackTrace();
					softAssert.assertTrue(false, "Exception occured for the network call: " + request.getUrl());
				}
			});

		}
		softAssert.assertAll();

	}

	@Test(dataProvider = "Url List", description = "This verifies the network calls on web from static.clmbtech.com on, ", enabled = false)
	public void verifyAdsNetworkCallFromClmbtech(String pageName, String url) throws IOException, HarReaderException {
		SoftAssert softAssert = new SoftAssert();

		file = proxyBrowserUtil.returnHARFileAfterScrolling(url, 10, "bottom", 1000);
		if (file == null) {
			Assert.assertTrue(false, "the Network file is not created for url: " + url);
			return;
		}
		String key = "static.clmbtech.com";
		reqRespMap = HARUtil.getAllEntriesWithResponse(file, key);

		if (reqRespMap.size() > 0) {
			durationValues = new HashMap<String, String>();

			reqRespMap.forEach((request, response) -> {

				System.out.println(request.getUrl());

				durationValues = HARUtil.getAllMatchingHeadersValue(response, "content-type");
				System.out.println(durationValues);

				String contentType = durationValues.get("Content-Type");
				System.out.println(contentType);

				softAssert.assertTrue(contentType.contentEquals("image/webp"),
						"<b>The content type: " + contentType + " for the request:</b> " + request.getUrl()
								+ " on page <b>URL:</b> " + url
								+ " is not mathcing with default content type <b>image/webp</b> <br />");

			});

		}
		softAssert.assertAll();

	}

	@Test(dataProvider = "Url List Aricleshow", description = "This verifies the X/Y cordinates for the network calls from google ads on, ")
	public void verifyAdsCordinateFromGoogle(String pageName, String url) throws IOException, HarReaderException {
		SoftAssert softAssert = new SoftAssert();

		file = proxyBrowserUtil.returnHARFileAfterScrolling(url, 15, "bottom", 1800);
		if (file == null) {
			Assert.assertTrue(false, "the Network file is not created for url: " + url);
			return;
		}
		String key = "gampad/ads?";
		reqRespMap = HARUtil.getAllEntriesWithResponse(file, key);
		System.out.println(reqRespMap);
		if (reqRespMap.size() > 0) {

			Set<HarRequest> requestList = reqRespMap.keySet();
			System.out.println(requestList.size());
			requestList.forEach(req -> {
				String sourceRequestUrl = req.getUrl();
				System.out.println(sourceRequestUrl);
				Pattern adxP = Pattern.compile("adxs?=[0-9-,.%C]+");
				Matcher mX = adxP.matcher(sourceRequestUrl);
				while (mX.find()) {
					List<String> xCordinates = Arrays.asList(mX.group().split("=")[1].split("%2C"));
					List<Integer> xCord = Lists.transform(xCordinates, Integer::parseInt);
					xCord.forEach(cord -> {
						System.out.println(cord);
						softAssert.assertTrue(cord >= 0, "The x cordinate: " + cord + " in the request: "
								+ sourceRequestUrl + " on url: " + url + " is found negative");
					});

				}
				Pattern adyP = Pattern.compile("adys?=[0-9-,.%C]+");
				Matcher mY = adyP.matcher(sourceRequestUrl);
				while (mY.find()) {

					List<String> yCordinates = Arrays.asList(mY.group().split("=")[1].split("%2C"));
					System.out.println(yCordinates);
					List<Integer> yCord = Lists.transform(yCordinates, Integer::parseInt);
					yCord.forEach(cord -> {
						System.out.println(cord);
						softAssert.assertTrue(cord >= 0, "The y cordinate: " + cord + " in the request: "
								+ sourceRequestUrl + " on url: " + url + " is found negative");
					});

				}
			});
		}
		softAssert.assertAll();
	}

	@AfterMethod
	public void driverTakeDown() {

		proxyBrowserUtil.killServices();
	}

	private DateTime getDateTime(String date) {

		DateTimeFormatter dateFormat = DateTimeFormat.forPattern("EEE dd MMM yyy HH:mm:ss");
		String dateObject = date.replace(",", "").replace(" GMT", "");
		DateTime dateTimeObject = dateFormat.parseDateTime(dateObject);
		System.out.println(dateObject + "  ::  " + dateTimeObject);
		return dateTimeObject;
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
		WaitUtil.sleep(5000);
		String pageSource = driver.getPageSource();
		Pattern pattern = Pattern.compile("(https://economictimes.indiatimes.com/[^.]+articleshow/\\d+.cms)");
		Matcher m = pattern.matcher(pageSource);
		while (m.find() && count < 6) {
			Pattern patternMsid = Pattern.compile("[0-9]{8}");
			System.out.println(m.group());
			Matcher mMsid = patternMsid.matcher(m.group());
			String msid = "";
			while (mMsid.find()) {
				msid = mMsid.group();
			}
			urlList[count - 1][0] = "Articleshowpage_msid_" + msid;
			urlList[count - 1][1] = m.group();
			count++;

		}

		driver.quit();

		return urlList;
	}

	@DataProvider(name = "Url List Aricleshow")
	public String[][] getArticleshowLinkWEB() {

		String[][] dataArray = getShowPageUrlFromSiteMap();
		return dataArray;

	}

	@DataProvider(name = "Url List")
	public String[][] getExcelDataWEB() {
		String[][] articleShowLinks = getShowPageUrlFromSiteMap();
		int countShowPage = articleShowLinks.length;
		File file = new File(filePath);
		ExcelUtil excelUtil = new ExcelUtil(file);
		String sheetName = "Sheet1";

		String[][] dataArray = new String[(excelUtil.getRowCount(sheetName) - 1) + countShowPage][2];
		for (int i = 0; i < excelUtil.getRowCount(sheetName) - 1; i++) {

			dataArray[i][0] = excelUtil.getCellData(sheetName, 0, i + 2);
			dataArray[i][1] = excelUtil.getCellData(sheetName, 1, i + 2);
		}

		int counter = 0;
		int endCounter = dataArray.length;
		for (int l = endCounter - countShowPage; l < endCounter; l++) {
			dataArray[l][0] = articleShowLinks[counter][0];
			dataArray[l][1] = articleShowLinks[counter][1];
			counter++;
		}

		for (int a = 0; a < dataArray.length; a++) {

			System.out.println(dataArray[a][0] + "------" + dataArray[a][1]);
		}
		return dataArray;
	}

}