package web.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.launchsetup.BaseTest;
import common.launchsetup.Config;
import common.utilities.ExcelUtil;
import common.utilities.HTTPResponse;
import common.utilities.VerificationUtil;
import common.utilities.WaitUtil;
import web.pagemethods.AdTechMethods;
import web.pagemethods.ArticleMethods;
import web.pagemethods.HomePageMethods;
import web.pagemethods.LoginPageMethods;
import web.pagemethods.WebBaseMethods;

public class ArticlesPage extends BaseTest {
	private String baseUrl;
	HomePageMethods homePageMethods;
	LoginPageMethods loginPageMethods;

	WebBaseMethods webBaseMethods;
	ArticleMethods articleMethods;
	SoftAssert softAssert;
	Map<String, String> TestData = new HashMap<>();
	String email;
	String password;
	int counter;
	AdTechMethods adTechMethods;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		baseUrl = BaseTest.baseUrl;
		launchBrowser(baseUrl);
		homePageMethods = new HomePageMethods(driver);
		articleMethods = new ArticleMethods(driver);
		loginPageMethods = new LoginPageMethods(driver);
		adTechMethods = new AdTechMethods(driver);
		TestData = ExcelUtil.getTestDataRow("TestDataSheet", "VerifyLogin", 1);
		email = TestData.get("Email");
		password = TestData.get("Password");
	}

	@Test(description = "This test verifies text font size of articleshow page")
	public void verifyArticleShowPageFontSize() throws InterruptedException {
		boolean flag = homePageMethods.clickFirstNews();
		//WebBaseMethods.switchChildIfPresent();
		boolean flagFont = articleMethods.verifyArticleShowPageFontSize();
		WebBaseMethods.switchToParentClosingChilds();
		Assert.assertTrue(flag, "Unable to open first article");
		Assert.assertTrue(flagFont, "The article show font size is not proper i.e. 18px");

	}

	@Test(description = " This test verifies the ads on article show page",enabled=false)
	public void verifyAds() {
		SoftAssert softAssert = new SoftAssert();
		List<String> articleUrls = homePageMethods.getFirstFewHeadlines();
		for (String url : articleUrls) {
			if (url.contains("articleshow")) {
				driver.get(url);
				WaitUtil.sleep(2000);
				Map<String, String> expectedIdsMap = Config.getAllKeys("adIdLocation");
				softAssert.assertTrue(adTechMethods.getDisplayedAdIds().size() > 0,
						"No google ads shown for article url <a href='" + url + "'>" + url + "</a>");
				if (adTechMethods.getDisplayedAdIds().size() > 0) {
					expectedIdsMap.forEach((K, V) -> {
						if (K.contains("_AS_") && !(K.contains("_SS_"))) {
							softAssert.assertTrue(adTechMethods.matchIdsWithRegex(K),
									"Following ad(s) is/are not shown " + expectedIdsMap.get(K)
											+ " for article url <a href='" + url + "'>" + url + "</a>");
						}
					});
				}
				List<String> ctnAd = adTechMethods.getMissingColumbiaAds();
			
					softAssert.assertTrue(adTechMethods.aroundWebIsDisplayed(),
							"From around web section is not displayed for article url <a href='" + url + "'>" + url
									+ "</a>");
				
			}
		}
		softAssert.assertAll();
	}

	@Test(description = "This test verifies twitter sharing of articleshow")
	public void verifyAticleShowSharingWithTwitter() throws InterruptedException {
		boolean flag = homePageMethods.clickFirstNews();
		Assert.assertTrue(flag, "Unable to open first article");
		String title = articleMethods.getArticleTitle();
		Assert.assertTrue(articleMethods.navigateToTwitterSharing(),"Unable to navigate to twitter sharing");
		String sharedTitle = articleMethods.getTwitterSharedLinkTitle().replace("...", "");
		WebBaseMethods.switchToParentClosingChilds();
		boolean flagTitle = title.contains(sharedTitle);
		Assert.assertTrue(flagTitle, "Headline is not matching on Twitter, <b>expected</b> " + title + " <b>found</b> "
				+ sharedTitle + " .");
	}

	@Test(description = "This test verifies google sharing of articleshow", enabled = false)
	public void verifyArticleShowSharingWithGoogle() throws InterruptedException {
		boolean flag = homePageMethods.clickFirstNews();
		Assert.assertTrue(flag, "Unable to open first article");
		String title = articleMethods.getArticleTitle();
		Assert.assertTrue(articleMethods.clickGoogleSharingBtn(),"Unable to click g sharing btn");
		WebBaseMethods.switchToChildWindow(5);
		boolean loginFlag = loginPageMethods.googlePlusLogin(email, password);
		String sharedTitle = articleMethods.getGoogleSharedLinkTitle();
		WebBaseMethods.switchToParentClosingChilds();
		Assert.assertTrue(loginFlag, "Unable to login through Google Plus");
		Assert.assertTrue(title.trim().contains(sharedTitle.replace("- The Economic Times", "").trim()),
				"Headline is not matching on Google, <b>expected</b> " + title + " <b>found</b> " + sharedTitle + " .");
	}

	@Test(description = "This test verifies facebook sharing of articleshow")
	public void verifyArticleShowSharingWithFacebook() throws InterruptedException {
		boolean flag = homePageMethods.clickFirstNews();
		Assert.assertTrue(flag, "Unable to open first article");
		String title = articleMethods.getArticleTitle();
		Assert.assertTrue(articleMethods.clickFacebookSharingBtn(),"Unable to click fb sharing btn");
		WebBaseMethods.switchToChildWindow(5);
		boolean loginFlag = loginPageMethods.facebookLogin(email, password);
		Assert.assertTrue(loginFlag, "Unable to login through facebook");
		String sharedTitle = articleMethods.getFacebookSharedLinkTitle();
		WebBaseMethods.switchToParentClosingChilds();
		Assert.assertTrue(title.trim().contains(sharedTitle.replace("- The Economic Times", "").trim()),
				"Headline is not matching on Facebook, <b>expected</b> " + title + " <b>found</b> " + sharedTitle
						+ " .");
	}

	@Test(description = "This test verifies Also read section on articleshow")
	public void verifyStreamingArticleShow() {
		softAssert = new SoftAssert();
		boolean flag = homePageMethods.clickFirstNews();
		Assert.assertTrue(flag, "Unable to open first article");
		//WebBaseMethods.switchToChildWindow(1);
		WebBaseMethods.scrollToBottom();
		counter = 0;
		List<WebElement> articleLink = articleMethods.getStreamBandArticleLink();
		List<String> headingList = articleMethods.articleHeading();
		System.out.println(headingList.size());
		for (int i = 0; i < articleLink.size(); i++) {
			String heading = articleMethods.clickArticleShowReturnHeadline(articleLink.get(i));
			softAssert.assertTrue(heading.equalsIgnoreCase(headingList.get(counter)),
					heading + " and " + headingList.get(counter) + " Do not match");
			counter++;
			List<String> alsoReadLinks = articleMethods.getAlsoReadStories(i);
			System.out.println("alsoReadLinks " + alsoReadLinks);
			if (!alsoReadLinks.isEmpty()) {
				alsoReadLinks.forEach(href -> {
					int response = HTTPResponse.checkResponseCode(href);
					softAssert.assertEquals(response, 200,
							"<br>- <a href=" + href + "> Story</a> in also read section is throwing " + response
									+ " on " + articleMethods.getHref());
				});
				List<String> alsoReadDup = VerificationUtil.isListUnique(alsoReadLinks);
				softAssert.assertTrue(alsoReadDup.isEmpty(),
						"<br>- Also Read section is having duplicate stories, repeating story(s)->" + alsoReadDup
								+ " on " + articleMethods.getHref());
			}
		}
		softAssert.assertAll();
	}

	@AfterMethod
	public void afterMethod() {
		WebBaseMethods.closeAllExceptParentWindow();
	}

}
