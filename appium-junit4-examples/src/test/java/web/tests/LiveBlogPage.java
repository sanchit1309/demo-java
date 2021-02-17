package web.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import web.pagemethods.LiveBlogPageMethods;
import web.pagemethods.WebBaseMethods;

public class LiveBlogPage extends BaseTest {

	private String baseUrl;
	private LiveBlogPageMethods liveBlogPageMethods;
	DateTime fetchedDate = null;
	String propName = "LastRecordedValue";
	DateTime articleModDate = null;
	Boolean seoFlag = false;
	List<String> urls = new ArrayList<String>();
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		launchBrowser(baseUrl);
		WaitUtil.sleep(5000);
		liveBlogPageMethods = new LiveBlogPageMethods(driver);
		// seo check will run only once in an hr
		// clear file every day
		if (new DateTime().getHourOfDay() == 8 && new DateTime().getMinuteOfHour() < 55)
			liveBlogPageMethods.truncateTable();
		if ((new DateTime().getHourOfDay() < 11 && new DateTime().getHourOfDay() > 9)
				&& new DateTime().getMinuteOfHour() < 5)
			seoFlag = true;
	}


	@Test(description = "verify first live blog", priority = 0, dataProvider = "sourceUrls")
	public void verifyLiveBlog(String url) {
		softAssert = new SoftAssert();
		WebBaseMethods.navigateTimeOutHandle(url);
		String action = driver.getCurrentUrl();

		System.out.println(new DateTime().getMinuteOfDay());
		String getCurrentDate = VerificationUtil.convertDateToGivenFormat(new Date(), "yyyy-MM-dd");

		if ((action.contains("sensex") && !action.contains(getCurrentDate)) && new DateTime().getMinuteOfDay() < 930) {
			System.out.println("not running:" + action);
			return;
		}

		try {
			try {
				driver.get(action);
			} catch (TimeoutException e) {
				driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
			}

			int responseCode = HTTPResponse.checkResponseCode(action);
			softAssert.assertEquals(responseCode, 200,
					"Live Blog url" + action + " is throwing " + responseCode + "error code, exprected 200");
			String articleVal = liveBlogPageMethods.getLiveBlogDate();
			System.out.println("articleVal1:" + articleVal);
			articleVal = articleVal.replaceAll("[,\\s]", "").replaceAll("[.]", ":");
			articleVal = articleVal.replaceAll("\\|", "").replaceAll(" ", "").replaceAll(",", "");
			articleVal = articleVal.replace("IST", "");
			System.out.println("articleVal2:" + articleVal);
			articleModDate = new DateTime(VerificationUtil.convertDateMethod(articleVal));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Assert.assertTrue(articleModDate!=null, "article modified date found null");
		String[] arr = action.split("/");
		String propKey = "Web_" + arr[arr.length - 1].replace(".cms", "");

		String fetchedDateVal = liveBlogPageMethods.getKeyValueFromDb(propKey);
		System.out.println("liveblog fetched value:" + fetchedDateVal);
		if (fetchedDateVal.length() == 0) {
			softAssert.assertTrue(false, "the key was not found in the properties file, has been now set in it");
			System.out.println(propKey + "<< the key was not found in the properties file, has been now set in it");
			liveBlogPageMethods.writeIntoTable(propKey, articleModDate.toString());
			return;
		}

		else if (articleModDate != null && fetchedDateVal.length() > 0  ) {
			liveBlogPageMethods.updateTable(propKey, articleModDate.toString());
		}
		if (fetchedDateVal.length() > 0) {

			DateTime fetchedDate = new DateTime(fetchedDateVal);
			softAssert.assertTrue(articleModDate != null, "Article date not fetched from the liveblog page");

			System.out.println(articleModDate + "<< article mod date " + fetchedDate + "<< prop file fetched date");
			if ((new DateTime().getMillis() - articleModDate.getMillis()) > 7200000) {
				return;
			}

			softAssert.assertTrue(articleModDate.isAfter(fetchedDate),
					"<span> Liveblog is not getting updated, last value "
							+ articleModDate.toString().split("\\+")[0].replace(".000", "").replace("T", " ") + "<br>");
		}
		Assert.assertTrue(liveBlogPageMethods.isLiveBlogTodays(), "Live Blog is not of Today");
		softAssert.assertAll();
	}

	

	@DataProvider(name = "sourceUrls")
	private Object[] getAllLiveBlogs() {
		driver.get(baseUrl + Config.fetchConfigProperty("SiteMap"));
		String pageSource = driver.getPageSource();
		Pattern pattern = Pattern.compile("\\bhttp[^>]+\\bliveblog/\\d+\\b.cms");
		Matcher m = pattern.matcher(pageSource);
		while (m.find()) {
			urls.add(m.group());
		}
		return urls.toArray(new Object[urls.size()]);
	}

}
