package wap.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.WaitUtil;
import wap.pagemethods.HomePageMethods;
import wap.pagemethods.MenuPageMethods;
import web.pagemethods.WebBaseMethods;

public class CheckSingleLiveblogUrl extends BaseTest {

	private String wapUrl;
	int count = 0;
	
	private String filePath = "./src/main/resources/testdata/wap/UrlRepo.xlsx";

	private HomePageMethods homePageMethods;
	private MenuPageMethods menuPageMethods;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl = BaseTest.baseUrl;
		launchBrowser(wapUrl);
		homePageMethods = new HomePageMethods(driver);
		menuPageMethods = new MenuPageMethods(driver);
	}

	@Test(description = "This test check the single liveblog domain by checking all the links on the page")
	public void checkSingleLiveBlogUrlFromHref() {
		count = 0;
		softAssert = new SoftAssert();
		List<String> urlList = getAllUrlList();
		urlList.forEach(url -> {
			driver.get(url);
			WebBaseMethods.scrollToBottom();
			// WaitUtil.sleep(2000);
			count++;
			System.out.println(count + ". " + url);
			WaitUtil.sleep(5000);
			homePageMethods.jqueryInjForReactPages();
			WaitUtil.sleep(2000);

			List<String> allHref = homePageMethods.getAllHref();
			Set<String> allHrefSet = new HashSet<String>(allHref);

			System.out.println(allHref.size());
			System.out.println(allHrefSet.size());

			List<String> failedUrlList = new LinkedList<>();
			allHrefSet.forEach(href -> {
				if (href.contains("https://m.economictimes.com/") && href.contains("/liveblog/")) {

					failedUrlList.add(href);
				}

			});
			System.out.println(failedUrlList);
			softAssert.assertTrue(failedUrlList.size() == 0,
					"Liveblog url with domain m.economictimes.com is found on URL:" + url + " with liveblog links: "
							+ failedUrlList);

		});
		softAssert.assertAll();
	}

	@Test(description = "This test check the single liveblog domain by checking the page source of all the pages")
	public void checkSingleLiveBlogUrlFromPageSource() {
		count = 0;
		softAssert = new SoftAssert();
		List<String> urlList = getAllUrlList();

		urlList.forEach(url -> {
			count++;
			System.out.println(count + ". " + url);
			driver.get(url);
			WaitUtil.sleep(5000);
			String pageSource = driver.getPageSource();
			List<String> failedUrlList = new LinkedList<>();

			Pattern regexForLiveblog = Pattern.compile("https://m.economictimes.com/\\S+/liveblog/[0-9]+.cms");
			Matcher m = regexForLiveblog.matcher(pageSource);

			while (m.find()) {
				failedUrlList.add(m.group());
			}

			System.out.println(failedUrlList);

			softAssert.assertTrue(failedUrlList.size() == 0,
					"Liveblog url with domain m.economictimes.com is found on URL:" + url + " with liveblog links: "
							+ failedUrlList);

		});
		softAssert.assertAll();
	}

	@Test(description = "this test check the single liveblog domain by checking all the unique links from all the page", enabled = false)
	public void checkSingleLiveBlogUrlFromHrefUnique() {
		count = 0;
		softAssert = new SoftAssert();
		List<String> urlList = getAllUrlList();
		LinkedHashMap<String, String> failedUrlList = new LinkedHashMap<>();
		LinkedHashMap<String, String> urlTemplateMap = new LinkedHashMap<>();
		urlList.forEach(url -> {
			driver.get(url);
			count++;
			System.out.println(count + ". " + url);
			WaitUtil.sleep(5000);
			homePageMethods.jqueryInjForReactPages();
			WaitUtil.sleep(2000);

			List<String> allHref = homePageMethods.getAllHref();
			Set<String> allHrefSet = new HashSet<String>(allHref);

			System.out.println(allHref.size());
			System.out.println(allHrefSet.size());

			allHrefSet.forEach(href -> {
				urlTemplateMap.put(href, url);
			});
		});

		System.out.println(urlTemplateMap.size());
		urlTemplateMap.forEach((url, template) -> {

			if (url.contains("https://m.economictimes.com/") && url.contains("/liveblog/")) {

				failedUrlList.put(url, template);
			}

		});
		System.out.println(failedUrlList);
		softAssert.assertTrue(failedUrlList.size() == 0,
				"Liveblog url with domain m.economictimes.com is found:" + failedUrlList);

		softAssert.assertAll();
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
