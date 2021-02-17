package web.tests;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.WaitUtil;
import web.pagemethods.HomePageMethods;
import web.pagemethods.WebBaseMethods;

public class CheckByCrawlingTemplatesForCommodities extends BaseTest {

	private String webUrl;
	int count = 0;

	private String filePath = "./src/main/resources/testdata/web/UrlRepol1l2l3.xlsx";

	private HomePageMethods homePageMethods;

	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		webUrl = BaseTest.baseUrl;
		launchBrowser(webUrl);
		homePageMethods = new HomePageMethods(driver);
	}

	@Test(description = "This test check the small case symbol value for commodities by checking all the links on the page", enabled = true)
	public void smallCaseSymbolCommoditiesUrlFromHref() {
		count = 0;
		softAssert = new SoftAssert();
		List<String> urlList = getAllUrlList();
		// List<String> urlList = new LinkedList<>();
		// urlList.add("https://economictimes.indiatimes.com/marketstats/pageno-1,pid-81,sortby-percentchange,sortorder-desc.cms");
		// urlList.add("https://economictimes.indiatimes.com/markets/commodities");

		urlList.forEach(url -> {
			List<String> failedUrlList = new LinkedList<>();
			try {
				driver.get(url);

				WebBaseMethods.scrollToBottom();
				WaitUtil.sleep(2000);
				count++;
				System.out.println(count + ". " + url);

				List<String> allHref = homePageMethods.getAllHref();
				System.out.println(allHref);
				Set<String> allHrefSet = new HashSet<String>(allHref);

				System.out.println(allHref.size());
				System.out.println(allHrefSet.size());

				allHrefSet.forEach(href -> {
					Pattern regexForLiveblog = Pattern
							.compile("https://economictimes.indiatimes.com/commoditysummary/symbol-[a-z]+.cms");
					Matcher m = regexForLiveblog.matcher(href);

					while (m.find()) {
						failedUrlList.add(m.group());
					}

				});

			} catch (Exception ee) {
				ee.printStackTrace();

			}
			System.out.println(failedUrlList);
			softAssert.assertTrue(failedUrlList.size() == 0,
					"Commodities link with smallcase symbol in url is found on URL:" + url + " with duplicate links: "
							+ failedUrlList);

		});
		softAssert.assertAll();
	}

	@Test(description = "This test check the small case symbol value for commodities by checking all the links on the page", enabled = true)
	public void smallCaseSymbolCommoditiesUrlFromPageSource() {
		count = 0;

		softAssert = new SoftAssert();
		List<String> urlList = getAllUrlList();
		// List<String> urlList = new LinkedList<>();
		// urlList.add("https://economictimes.indiatimes.com/marketstats/pageno-1,pid-81,sortby-percentchange,sortorder-desc.cms");
		// urlList.add("https://economictimes.indiatimes.com/markets/commodities");

		urlList.forEach(url -> {
			count++;
			System.out.println(count + ". " + url);
			try {
				driver.get(url);
				WebBaseMethods.scrollToBottom();
				WaitUtil.waitForLoad(driver);
				String pageSource = driver.getPageSource();

				List<String> failedUrlList = new LinkedList<>();

				Pattern regexForLiveblog = Pattern.compile("/commoditysummary/symbol-[a-z]+.cms");
				Matcher m = regexForLiveblog.matcher(pageSource);

				while (m.find()) {
					failedUrlList.add(m.group());
				}

				System.out.println(failedUrlList);

				softAssert.assertTrue(failedUrlList.size() == 0,
						"Commodities link with smallcase symbol in url is found on URL:" + url + " with links: "
								+ failedUrlList);
			} catch (Exception ee) {

			}

		});
		softAssert.assertAll();
	}

	/*
	 * public List<String> getAllLinksFromNav() { List<String> getNavLinks = new
	 * LinkedList<>(); List<WebElement> li =
	 * driver.findElements(By.xpath("//div[contains(@class,'navElement')]//a"));
	 * getNavLinks = WebBaseMethods.getListHrefUsingJSE(li);
	 * System.out.println(getNavLinks); return getNavLinks;
	 * 
	 * }
	 */

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

	@Test(description = "", enabled = false)
	public void getCommoditiesLink() {
		List<String> urls = new LinkedList<>();
		List<String> allUrls = new LinkedList<>();
		String[] arr = { "https://economictimes.indiatimes.com/markets", "https://economictimes.indiatimes.com/news",
				"https://economictimes.indiatimes.com/industry", "https://economictimes.indiatimes.com/small-biz",
				"https://economictimes.indiatimes.com/news/politics-nation",
				"https://economictimes.indiatimes.com/personal-finance",
				"https://economictimes.indiatimes.com/mutual-funds", "https://economictimes.indiatimes.com/tech",
				"https://economictimes.indiatimes.com/jobs", "https://economictimes.indiatimes.com/opinion",
				"https://economictimes.indiatimes.com/nri", "https://economictimes.indiatimes.com/multimedia",
				"https://economictimes.indiatimes.com/panache", "https://economictimes.indiatimes.com/et-now" };

		urls = Arrays.asList(arr);

		System.out.println(urls.size());
		// urls.forEach(url -> {

		driver.get("https://economictimes.indiatimes.com/panache");
		WebBaseMethods.scrollToBottom();
		WaitUtil.waitForLoad(driver);
		// List<WebElement> eleList =
		// driver.findElements(By.xpath("//div[@class='flt navElement
		// relNav']//a[contains(@class,'subsec')]"));
		List<WebElement> eleList = driver.findElements(By.xpath("//nav[@class='clr panacheNav']//div[@class='flt']/a"));
		List<String> urlList = WebBaseMethods.getListHrefUsingJSE(eleList);
		allUrls.addAll(urlList);
		System.out.println(urlList.size());
		System.out.println(urlList);

		// });
		System.out.println(allUrls);
	}

	public List<String> getAllUrlList() {
		List<String> urlList = new LinkedList<>();
		File file = new File(filePath);
		ExcelUtil excelUtil = new ExcelUtil(file);

		excelUtil.extractAsList(1).forEach(li -> {
			String url = li.get(0).toString();
			urlList.add(url);
		});

		System.out.println(urlList.size() + "  ----- " + urlList);
		return urlList;

	}

}
