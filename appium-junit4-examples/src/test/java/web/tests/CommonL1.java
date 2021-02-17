package web.tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.WaitUtil;
import web.pagemethods.ETSharedMethods;
import web.pagemethods.HomePageMethods;
import web.pagemethods.WebBaseMethods;

public class CommonL1 extends BaseTest {

	private String baseUrl;
	SoftAssert softAssert;
	HomePageMethods homePageMethods;
	ETSharedMethods etSharedMethods;
	String pollQuestion;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		launchBrowser(baseUrl);
		homePageMethods = new HomePageMethods(driver);
		etSharedMethods = new ETSharedMethods();
		WebBaseMethods.scrollToBottom();
		getHomepagePollQuestion();

	}

	@DataProvider(name = "pages")
	private Object[] pages() {
		String pageUrls = Config.fetchConfigProperty("L1List");
		List<String> listOfPages = Arrays.asList(pageUrls.split("\\s*,\\s*"));
		return listOfPages.toArray();
	}

	@Test(description = "Verifies presence of http Urls", dataProvider = "pages")
	public void httpCheck(String pageUrl) throws IOException {
		List<String> listUnique = new LinkedList<String>();
		softAssert = new SoftAssert();
		pageUrl = baseUrl + pageUrl;
		driver.get(pageUrl);
		WebBaseMethods.scrollToBottom();
		String bodyHtml = (String) ((JavascriptExecutor) driver).executeScript("return document.body.innerHTML;");
		Matcher matcher = Pattern.compile("<a[^>]*href=\"http://[^>//]*economictimes[^>]+\">").matcher(bodyHtml);
		while (matcher.find()) {
			String temp = matcher.group();
			try {
				temp = temp.replaceAll("<a [^>]*href=", "").replaceAll("\"[\\s].*", "").replace("\"", "")
						.replace(">", "").replace(" ", "%20");
			} catch (IllegalStateException e) {
				// do nothing
			}
			if (!listUnique.contains(temp)) {
				listUnique.add(temp);
				softAssert.assertTrue(false, "<br>HTTP Url found:" + temp);
			}
		}
		softAssert.assertAll();
	}

	@Test(description = "verifies the poll question on all L1 pages", dataProvider = "pages")
	public void verifyPollQuestion(String pageUrl) throws IOException {
		softAssert = new SoftAssert();
		//String pollQuestion = homePageMethods.getHomepagePollQuestion();
		System.out.println(pollQuestion);
		pageUrl = baseUrl + pageUrl;
		driver.get(pageUrl);
		WaitUtil.sleep(2000);
		WebBaseMethods.scrollToBottom();
		if (etSharedMethods.getPollQuestion().size()>0) {
			String l1PollQuestion = etSharedMethods.getPollQuestion().get(0).getText();
			softAssert.assertTrue(l1PollQuestion.equalsIgnoreCase(pollQuestion), "The poll question " + l1PollQuestion
					+ " on the page " + pageUrl + " is not matching with homepage poll question " + pollQuestion);

		}
		else {
			
			System.out.println("The element is not present on"+ pageUrl);
		}

	//	driver.navigate().back();
		WaitUtil.sleep(1000);
		softAssert.assertAll();

	}
	
	
	public void getHomepagePollQuestion() {
		pollQuestion = homePageMethods.getHomepagePollQuestion();
				
				
	}
	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}
