package com.web.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.xalan.xsltc.cmdline.Compile;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.WaitUtil;
import web.pagemethods.AdTechMethods;
import web.pagemethods.ArticleMethods;
import web.pagemethods.HeaderPageMethods;
import web.pagemethods.HomePageMethods;
import web.pagemethods.LoginPageMethods;
import web.pagemethods.PrimeShowStoryPageMethods;
import web.pagemethods.WebBaseMethods;

public class PrimeShowStoryPage extends BaseTest {

	private String baseUrl;
	HomePageMethods homePageMethods;
	LoginPageMethods loginPageMethods;
	HeaderPageMethods headerPageMethods;
	PrimeShowStoryPageMethods primeShowStoryPageMethods;
	WebBaseMethods webBaseMethods;
	ArticleMethods articleMethods;
	SoftAssert softAssert;
	Map<String, String> TestData = new HashMap<>();
	String email;
	String password;
	AdTechMethods adTechMethods;
	int count = 0;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		launchBrowser(baseUrl);
		homePageMethods = new HomePageMethods(driver);
		articleMethods = new ArticleMethods(driver);
		loginPageMethods = new LoginPageMethods(driver);
		headerPageMethods = new HeaderPageMethods(driver);
		primeShowStoryPageMethods = new PrimeShowStoryPageMethods(driver);
		TestData = ExcelUtil.getTestDataRow("TestDataSheet", "VerifyETPremium", 1);
		email = TestData.get("Email");
		password = TestData.get("Password");
	}

	@Test(description = "this test is to verify the content body of the primeshow story on web")
	public void verifyContentOfPrimeArticles() {
		softAssert = new SoftAssert();
		Assert.assertTrue(headerPageMethods.clickOnSignInLink(), "Unable to find sign in button");

		Assert.assertTrue(loginPageMethods.registeredUserLogin(TestData.get("Email"), TestData.get("Password")),
				"Unable to fill entries in the login window");
		Assert.assertTrue(headerPageMethods.getLoggedInUserImage(), "Logged in user image not shown");

		driver.get("https://prime.economictimes.indiatimes.com/news/153/et-premium");
		WaitUtil.waitForLoad(driver);
		WebBaseMethods.scrollMultipleTimes(15, "bottom", 6000);
		List<String> allPrimeshowUrlsList = primeShowStoryPageMethods.getAllETPrimeShowLinks();
		System.out.println("size of the list: " + allPrimeshowUrlsList.size());
		Assert.assertTrue(allPrimeshowUrlsList.size() > 0, "The primeshow articles are not found on the ET homepage");

		allPrimeshowUrlsList.forEach(url -> {
			System.out.println("counter: " + count++);
			driver.get(url);
			WaitUtil.waitForLoad(driver);
			WebBaseMethods.scrollToBottom();
			WaitUtil.sleep(3000);

			try {
				String bodyContent = primeShowStoryPageMethods.getPrimeShowBodyContentText();
				System.out.println(bodyContent);
				softAssert.assertTrue(
						bodyContent.length() > 0 && !bodyContent.contains("The primeshow body content is not found"),
						"PrimeShow story with url: " + url + " is blank");

				List<String> htmlTags = new LinkedList<>();

				Pattern p = Pattern.compile("<[^<^>]+>");
				Matcher m = p.matcher(bodyContent);
				while (m.find()) {
					htmlTags.add(m.group());
				}
				softAssert.assertTrue(htmlTags.size() == 0, "The html tags: <b>" + htmlAsPlainText(htmlTags)
						+ " </b>are <b>found</b> in the body content on the primeshow url: " + url + "<br />");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				softAssert.assertTrue(false, "The body content is not loaded on the primeshow url: " + url + "<br />");
			}

		});
		softAssert.assertAll();
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

	public List<String> htmlAsPlainText(List<String> li) {
		List<String> list = new LinkedList<>();
		List<String> listOfTags = li;
		listOfTags.forEach(tag -> {
			list.add(tag.replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
		});
		return list;
	}

}
