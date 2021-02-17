package wap.tests;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.utilities.ExcelUtil;
import common.utilities.WaitUtil;
import wap.pagemethods.HomePageMethods;
import wap.pagemethods.LoginPageMethods;
import wap.pagemethods.PrimeShowPageMethods;
import web.pagemethods.WebBaseMethods;

public class PrimeShowPage extends BaseTest {

	private String wapUrl;
	private HomePageMethods homePageMethods;
	private LoginPageMethods loginPageMethods;
	private PrimeShowPageMethods primeShowPageMethods;
	private SoftAssert softAssert;
	Map<String, String> TestData;
	int count = 0;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		wapUrl = BaseTest.baseUrl;
		launchBrowser(wapUrl);
		homePageMethods = new HomePageMethods(driver);
		loginPageMethods = new LoginPageMethods(driver);
		primeShowPageMethods = new PrimeShowPageMethods(driver);
		TestData = ExcelUtil.getTestDataRow("TestDataSheet", "VerifyETPremium", 1);
		homePageMethods.jqueryInjForReactPages();
	}

	@Test(description = "this test is to verify the content body of the primeshow story on Msite")
	public void verifyContentOfPrimeArticles() {
		softAssert = new SoftAssert();
		Assert.assertTrue(homePageMethods.clickFooterMenuICon(), "Unable to click menu icon");
		Assert.assertTrue(loginPageMethods.clickFooterMenuSignIn(), "Unable to click Sign In button from menu");
		Assert.assertTrue(loginPageMethods.clickContinueEmailMobile(),
				"SSO:Unable to click continue Email/Mobile button on login screen");
		Assert.assertTrue(loginPageMethods.findEmailSetValue(TestData.get("Email")), "SSO:Unable to find Email Field");
		Assert.assertTrue(loginPageMethods.clickContinueButton(),
				"Unable to click continue button on the email screen");
		Assert.assertTrue(loginPageMethods.findPasswordSetValue(TestData.get("Password")),
				"SSO:Unable to find Password Field");
		Assert.assertTrue(loginPageMethods.clickContinueButtonFinal(),
				"Unable to click continue button on the password screen");
		WaitUtil.waitForLoad(driver);
		homePageMethods.jqueryInjForReactPages();

		driver.get("https://prime.economictimes.indiatimes.com/news/153/et-premium");
		WaitUtil.waitForLoad(driver);
		WebBaseMethods.scrollMultipleTimes(15, "bottom", 6000);
		List<String> allPrimeshowUrlsList = primeShowPageMethods.getAllETPrimeShowLinks();
		System.out.println("size of the list: " + allPrimeshowUrlsList.size());

		Assert.assertTrue(allPrimeshowUrlsList.size() > 0, "The primeshow articles are not found on the ET homepage");
		allPrimeshowUrlsList.forEach(url -> {

			System.out.println("counter: " + count++);
			driver.get(url);
			homePageMethods.jqueryInjForReactPages();
			WaitUtil.waitForLoad(driver);
			WebBaseMethods.scrollToBottom();
			
			try {
				String bodyContent = primeShowPageMethods.getPrimeShowBodyContentText();
				System.out.println(bodyContent);
				softAssert.assertTrue(
						bodyContent.length() > 0 && !bodyContent.contains("The primeshow body content is not found"),
						"PrimeShow story with url: " + url + " is blank");

				List<String> htmlTags = new LinkedList<>();
				System.out.println(htmlTags);

				Pattern p = Pattern.compile("<[^<^>]+>");
				Matcher m = p.matcher(bodyContent);
				while (m.find()) {
					htmlTags.add(m.group());
				}
				softAssert.assertTrue(htmlTags.size() == 0, "The html tags: " + htmlAsPlainText(htmlTags)
						+ " are <b>found</b> in the body content on the primeshow url: " + url);
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
